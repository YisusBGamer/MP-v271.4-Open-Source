package net.swordie.ms;

/**
 * Created on 2/18/2017.
 */
public class ServerConstants {
	public static final String DIR = System.getProperty("user.dir");
	public static final byte LOCALE = 8;
	public static final String BAT_DIR = DIR + "/bat";
	/** Game data (data.wzDir in server.properties): a copy of the client's Data folder, an XML export with one folder
	 *  per .wz, or binary .wz files. */
	public static final String WZ_DIR = ServerSettings.getString("data.wzDir", DIR + "/wz");
	/** Caches built from the game data (data.datDir in server.properties). */
	public static final String DAT_DIR = ServerSettings.getString("data.datDir", DIR + "/dat");
	public static final String LOG_DIR = DIR + "/logs";
	public static final String SQL_DIR = DIR + "/sql";
	public static final String LOADINS_DIR = DIR + "/loadins";
	public static final int MAX_CHARACTERS = 54;
	public static final String SCRIPT_DIR = DIR + "/scripts";
	/** Journey bot data (bots.journeyDir in server.properties; relative paths are under DIR). */
	public static final String JOURNEY_DIR = java.nio.file.Path.of(DIR)
			.resolve(ServerSettings.getString("bots.journeyDir", "journey")).toString();
	/** Script probe data (bots.probeDir in server.properties; relative paths are under DIR). */
	public static final String PROBE_DIR = java.nio.file.Path.of(DIR)
			.resolve(ServerSettings.getString("bots.probeDir", "probe")).toString();
	public static final String RESOURCES_DIR = DIR + "/resources";
	public static final String HANDLERS_DIR = DIR + "/src/main/java/net/swordie/ms/handlers";
	public static final String BASE_API_PATH = "/api";
	public static final short VERSION = 271;
	public static final short VERSION_FOR_WZ = 777;
	public static final String MINOR_VERSION = "4";
	/** Opcode placeholder for packets with no known value in this version (see InHeader/OutHeader). */
	public static final short UNVERIFIED_OP = -1;
	/** Hotfix data sent to the client at login and laid over the .wz data; none if the file is missing. */
	public static final String HOTFIX_FILE = RESOURCES_DIR + "/Data" + VERSION + ".wz";
	// channel ports follow the login port (LOGIN_PORT + 100 * world + channel)
	public static final int LOGIN_PORT = ServerSettings.getInt("net.loginPort", 8484);
	public static final int CLIENT_API_PORT = ServerSettings.getInt("net.clientApiPort", 8483);
	public static final int WEB_API_PORT = ServerSettings.getInt("net.webApiPort", 3000); // Gets proxied via nginx
	/** The launcher's TCP API on CLIENT_API_PORT (login, account creation, account overview; see
	 *  connection.api.ApiHandler). Off unless server.properties turns it on (net.clientApiEnabled). */
	public static final boolean CLIENT_API_ENABLED = ServerSettings.getBoolean("net.clientApiEnabled", false);
	/** false: the launcher API refuses account creation (the web API has its own switch). */
	public static final boolean CLIENT_API_REGISTRATION = ServerSettings.getBoolean("net.clientApiRegistration", true);
	/** Minutes a launcher login token may wait for the game to start with it (0 = the web Play now limit). */
	public static final int CLIENT_API_TOKEN_MINUTES = ServerSettings.getInt("net.clientApiTokenMinutes", 60);
	/** Minutes without a request before a launcher connection is closed (0 = never). */
	public static final int CLIENT_API_IDLE_MINUTES = ServerSettings.getInt("net.clientApiIdleMinutes", 60);
	/** Open launcher connections allowed per address. */
	public static final int CLIENT_API_MAX_PER_ADDRESS = ServerSettings.getInt("net.clientApiMaxPerAddress", 4);

	public static final String SERVER_NAME = "MPSV MapleFire";
	/** Leave staff accounts (GMs, admins, testers) and their characters out of public rankings, online lists and
	 *  totals. */
	public static final boolean WEB_HIDE_STAFF = false;
	/** Ask search engines not to index the web API: robots.txt and an X-Robots-Tag header. */
	public static final boolean WEB_NO_SEARCH_ENGINES = true;
	/** false (web.registration): nobody can create accounts through the web API or the launcher */
	public static final boolean WEB_REGISTRATION_ENABLED = ServerSettings.getBoolean("web.registration", true);
	/** How many accounts one address may create per hour (web.accountsPerHour, 0: no limit). */
	public static final int WEB_ACCOUNTS_PER_ADDRESS_PER_HOUR = ServerSettings.getInt("web.accountsPerHour", 3);
	public static final short CHAT_PORT = 0;
	/** Timing log of packets, timer tasks, maps and database statements (slow ones right away, a summary every
	 *  10 minutes in logs/perf.log). See net.swordie.ms.util.PerfLog. */
	public static final boolean PERF_LOG = true;
	/** The Discord webhook for server announcements (first line: the webhook address; empty or missing: off).
	 *  See net.swordie.ms.world.DiscordRelay. */
	public static final String DISCORD_WEBHOOK_FILE = DIR + "/discord-webhook.txt";
	/** BCrypt cost (log2 of the rounds). Passwords hashed with a lower cost are re-hashed at their next login. */
	public static final int BCRYPT_ITERATIONS = 12;
	/** true: logins, registrations and password changes through the web API are only accepted over HTTPS (from a
	 *  proxy such as Cloudflare Tunnel or nginx that says X-Forwarded-Proto: https), from programs on this PC, or
	 *  (WEB_ALLOW_HTTP_FROM_LAN) from the local network. Set in server.properties (web.requireHttps). */
	public static final boolean WEB_REQUIRE_HTTPS = ServerSettings.getBoolean("web.requireHttps", true);
	public static final boolean WEB_ALLOW_HTTP_FROM_LAN = ServerSettings.getBoolean("web.allowHttpFromLan", true);
	// Contributed by Arixal (from GitLab).
	/** true only with a proxy on this PC in front of the web API (nginx, Cloudflare Tunnel): a request from this PC
	 *  then counts as the address the proxy appended to X-Forwarded-For. Off, any local program could pick the address
	 *  the login limits are kept for. Set in server.properties (web.trustForwardedFor). */
	public static final boolean WEB_TRUST_FORWARDED_FOR = ServerSettings.getBoolean("web.trustForwardedFor", false);
	public static final long TOKEN_EXPIRY_TIME = 60 * 24; // 24h
	public static final long CLEAR_CACHE_RATE = 4; // 24h
	public static boolean CHECK_WZ_ON_LOGIN = false;

	// Tespia
	public static final boolean IS_TESPIA = false; // set true if Tespia instance of server.
	public static final String SCRIPT_DIR_TESPIA = SCRIPT_DIR + "_tespia";

    public static final byte[] WZ_HASH = new byte[]{
			(byte) 0x75,
			(byte) 0x8D,
			(byte) 0xCC,
			(byte) 0x19,
			(byte) 0xDA,
			(byte) 0xE2,
			(byte) 0x21,
			(byte) 0x8E,
			(byte) 0xDD,
			(byte) 0x66,
			(byte) 0xA2,
			(byte) 0x36,
			(byte) 0xD4,
			(byte) 0xF5,
			(byte) 0x64,
			(byte) 0x10,
	}; // generated by SHA1
}
