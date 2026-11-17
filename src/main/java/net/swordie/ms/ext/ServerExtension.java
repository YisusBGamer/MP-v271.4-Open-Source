package net.swordie.ms.ext;

/**
 * An optional add-on loaded with {@link java.util.ServiceLoader} from
 * META-INF/services/net.swordie.ms.ext.ServerExtension. See {@link ServerHooks}.
 */
public interface ServerExtension {

    /** Called early in startup, before the web API and the worlds start. Register hooks here. */
    default void install() {
    }

    /** Called once the server has finished loading. */
    default void onServerStarted() {
    }
}
