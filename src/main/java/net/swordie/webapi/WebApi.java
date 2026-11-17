package net.swordie.webapi;

import com.sun.net.httpserver.HttpServer;
import jakarta.ws.rs.Path;
import net.swordie.ms.ServerConstants;
import net.swordie.ms.util.Util;
import net.swordie.webapi.exceptions.CorsExceptionHandler;
import net.swordie.webapi.exceptions.ExceptionHandler;
import net.swordie.webapi.interceptor.CorsPreflightFilter;
import net.swordie.webapi.interceptor.RequestInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.core.AsynchronousDispatcher;
import org.jboss.resteasy.core.providerfactory.ResteasyProviderFactoryImpl;
import org.jboss.resteasy.plugins.providers.ByteArrayProvider;
import org.jboss.resteasy.plugins.providers.StringTextStar;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;
import org.jboss.resteasy.plugins.server.sun.http.HttpContextBuilder;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.jar.JarFile;

public class WebApi {

    private static final Logger log = LogManager.getLogger();
    private static final String ROUTES_PACKAGE = "net.swordie.webapi.routes";
    public static final String ROUTES_DIR = ServerConstants.DIR + "/src/main/java/net/swordie/webapi/routes";

    public void init() {
        log.info("Initializing web API.");
        long start = System.currentTimeMillis();

        // A connection that sends its request too slowly is closed after 30 s, and no single reply
        // may take longer than an hour, so slow or stalled clients can't hold the web API's threads
        System.setProperty("sun.net.httpserver.maxReqTime", "30");
        System.setProperty("sun.net.httpserver.maxRspTime", "3600");
        HttpServer server = createHttpServer();

        // several threads, so one slow request doesn't hold up the others, such as logins
        server.setExecutor(java.util.concurrent.Executors.newFixedThreadPool(16));
        server.start();

        log.info(String.format("Initialied web API on port %d in %dms.", ServerConstants.WEB_API_PORT, System.currentTimeMillis() - start));
    }

    /** Route paths served by {@link #initLauncherOnly}: what the launcher uses (sign up, log in, game token, status), plus admin (rates). */
    private static final Set<String> LAUNCHER_PATHS = Set.of("login", "launch", "users", "public", "admin",
            "admin/features");

    /** null = every route and the website; else only these route paths, no website. */
    private Set<String> onlyPaths;
    private String bindHost = "";

    /**
     * Test server: only the launcher's routes, on one address (e.g. 127.0.0.1), without the website or extensions.
     */
    public void initLauncherOnly(String host) {
        onlyPaths = LAUNCHER_PATHS;
        bindHost = host == null ? "" : host.trim();
        log.info("Initializing the launcher web API (" + (bindHost.isEmpty() ? "all addresses" : bindHost) + ").");
        System.setProperty("sun.net.httpserver.maxReqTime", "30");
        System.setProperty("sun.net.httpserver.maxRspTime", "3600");
        HttpServer server = createHttpServer();
        if (server == null) {
            return;
        }
        server.setExecutor(java.util.concurrent.Executors.newFixedThreadPool(4));
        server.start();
        log.info(String.format("Launcher web API on %s:%d (routes %s).", bindHost.isEmpty() ? "*" : bindHost,
                ServerConstants.WEB_API_PORT, String.join(", ", new TreeSet<>(onlyPaths))));
    }

    private HttpServer createHttpServer() {
        HttpServer httpServer = null;

        try {
            InetSocketAddress address = bindHost.isEmpty() ? new InetSocketAddress(ServerConstants.WEB_API_PORT)
                    : new InetSocketAddress(bindHost, ServerConstants.WEB_API_PORT);
            httpServer = HttpServer.create(address, 0);

            var contextBuilder = new HttpContextBuilder();
            contextBuilder.setPath("/api");
            initRoutes(contextBuilder);
            initProviders(contextBuilder);
            contextBuilder.bind(httpServer);
            if (onlyPaths == null) {
                net.swordie.ms.ext.ServerHooks.onWebServer(httpServer);
            }
        } catch (Exception e) {
            log.info("Failed to create HTTPS server on port " + ServerConstants.WEB_API_PORT + " of localhost");
            log.error("WebApi.createHttpServer", e);
        }

        return httpServer;
    }

    private void initProviders(HttpContextBuilder contextBuilder) {
        contextBuilder.getDeployment().setDispatcher(new AsynchronousDispatcher(new ResteasyProviderFactoryImpl()));
        var providerFactory = contextBuilder.getDeployment().getDispatcher().getProviderFactory();
        providerFactory.registerProvider(ExceptionHandler.class);
        providerFactory.registerProvider(CorsExceptionHandler.class);
        providerFactory.registerProvider(ResteasyJackson2Provider.class);
        // raw bytes, e.g. the launcher's slide images
        providerFactory.registerProvider(ByteArrayProvider.class);
        // raw String bodies (Jackson refuses String), e.g. the launcher's settings save
        providerFactory.registerProvider(StringTextStar.class);
        providerFactory.registerProvider(RequestInterceptor.class);
        providerFactory.registerProvider(CorsPreflightFilter.class);
    }

    /**
     * A route that is only bound when no other route class serves the same @Path, so a server's own version of a
     * route replaces the built-in one.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface FallbackRoute {
    }

    private void initRoutes(HttpContextBuilder contextBuilder) {
        Map<String, List<Class<?>>> byPath = new TreeMap<>();
        for (Class<?> clazz : findRouteClasses()) {
            var pathAnnotation = clazz.getAnnotation(Path.class);
            if (pathAnnotation == null || Modifier.isAbstract(clazz.getModifiers())) {
                log.info(String.format("Not binding class %s, as no @Path annotation could be found.", clazz.getName()));
                continue;
            }
            String key = pathAnnotation.value().replaceAll("^/+|/+$", "");
            if (onlyPaths != null && !onlyPaths.contains(key)) {
                continue;
            }
            byPath.computeIfAbsent(key, k -> new ArrayList<>()).add(clazz);
        }
        for (List<Class<?>> classes : byPath.values()) {
            boolean hasMain = classes.stream().anyMatch(c -> !c.isAnnotationPresent(FallbackRoute.class));
            for (Class<?> clazz : classes) {
                if (hasMain && clazz.isAnnotationPresent(FallbackRoute.class)) {
                    log.info(String.format("Not binding %s: another route serves its path.", clazz.getSimpleName()));
                    continue;
                }
                contextBuilder.getDeployment().getActualResourceClasses().add(clazz);
            }
        }
    }

    /** The route classes: from the source folder when the server runs from its source tree, else from the classpath. */
    private List<Class<?>> findRouteClasses() {
        Set<String> names = new TreeSet<>();
        Set<File> files = new HashSet<>();
        Util.findAllFilesInDirectory(files, new File(ROUTES_DIR));
        for (File file : files) {
            if (file.getName().endsWith(".java")) {
                // strip the path to the package name and remove the .java extension
                names.add(file.getPath()
                        .replaceAll("[\\\\|/]", ".")
                        .split("src\\.main\\.java\\.")[1]
                        .replaceAll("\\.java", ""));
            }
        }
        if (names.isEmpty()) {
            names.addAll(routeClassNamesOnClasspath());
            log.info(String.format("No route sources in %s: using the %d route classes on the classpath.",
                    ROUTES_DIR, names.size()));
        }
        List<Class<?>> classes = new ArrayList<>();
        for (String className : names) {
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException e) {
                log.error("WebApi.findRouteClasses", e);
            }
        }
        return classes;
    }

    private static Set<String> routeClassNamesOnClasspath() {
        String pkg = ROUTES_PACKAGE.replace('.', '/');
        Set<String> names = new TreeSet<>();
        try {
            Enumeration<URL> urls = WebApi.class.getClassLoader().getResources(pkg);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if ("file".equals(url.getProtocol())) {
                    File[] list = new File(url.toURI()).listFiles();
                    if (list != null) {
                        for (File f : list) {
                            addRouteClassName(names, f.getName());
                        }
                    }
                } else if ("jar".equals(url.getProtocol())) {
                    var conn = (JarURLConnection) url.openConnection();
                    conn.setUseCaches(false);
                    try (JarFile jar = conn.getJarFile()) {
                        var entries = jar.entries();
                        while (entries.hasMoreElements()) {
                            String entry = entries.nextElement().getName();
                            if (entry.startsWith(pkg + "/") && entry.indexOf('/', pkg.length() + 1) < 0) {
                                addRouteClassName(names, entry.substring(pkg.length() + 1));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Could not list the route classes on the classpath", e);
        }
        return names;
    }

    private static void addRouteClassName(Set<String> names, String fileName) {
        if (fileName.endsWith(".class") && !fileName.contains("$")) {
            names.add(ROUTES_PACKAGE + "." + fileName.substring(0, fileName.length() - ".class".length()));
        }
    }
}
