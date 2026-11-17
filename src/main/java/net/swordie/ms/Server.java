package net.swordie.ms;

import net.swordie.ms.client.AuthInfo;
import net.swordie.ms.client.User;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.dailies.DailiesManager;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.skills.vmatrix.MatrixRecord;
import net.swordie.ms.client.rankings.RankingModule;
import net.swordie.ms.connection.api.ApiAcceptor;
import net.swordie.ms.connection.crypto.MapleCrypto;
import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.connection.db.IdAssigner;
import net.swordie.ms.connection.netty.ChannelAcceptor;
import net.swordie.ms.connection.netty.ChannelHandler;
import net.swordie.ms.connection.netty.LoginAcceptor;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.DefinitionsExport;
import net.swordie.ms.constants.ServerFeatures;
import net.swordie.ms.constants.ServerRates;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.Familiar;
import net.swordie.ms.loaders.*;
import net.swordie.ms.scripts.ScriptManagerImpl;
import net.swordie.ms.util.ErrorLogger;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.Loader;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.Channel;
import net.swordie.ms.world.TransferInfo;
import net.swordie.ms.world.World;
import net.swordie.ms.world.WorldConfig;
import net.swordie.ms.world.auction.AuctionItem;
import net.swordie.ms.world.shop.cashshop.CashItemInfo;
import net.swordie.ms.world.shop.cashshop.CashShop;
import net.swordie.orm.dao.SworDaoFactory;
import net.swordie.orm.dao.UserDao;
import net.swordie.orm.migration.MigrationManager;
import net.swordie.webapi.WebApi;
import net.swordie.webapi.routes.TradeLogRoute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2/18/2017.
 */
public class Server extends Properties {
	private static final UserDao userDao = (UserDao) SworDaoFactory.getByClass(User.class);

	private static final Logger log = LogManager.getRootLogger();
	private static final Server server = new Server();

	public static final boolean SHOW_EXCEPTIONS = true;
	public static boolean DEBUG = true;
	public static boolean DEBUG_MOVEMENT = false;
	public static boolean DEBUG_PACKET_TIMES = true;
	public static boolean OPCODE_ENCRYPTION = false; // v265: the reference server never sends the key; turn on only after testing with the client

	// Shutdown fields
	private boolean isInShutdown = false;
	private int minutesRemaining;
	private int shutdownScheduledMinutes;

	private List<World> worldList = new ArrayList<>();
	private static Map<String, AuthInfo> authTokens = new ConcurrentHashMap<>();
	private Map<Integer, User> users = new ConcurrentHashMap<>(); // also read by the web API's threads
	private CashShop cashShop;
	private Set<ScheduledFuture> shutdownFutures = new HashSet<>();
	private boolean opcodeEnc = false;
	private WebApi webApi;
	private ScheduledFuture itemExpiryFuture;
	private ScheduledFuture clearCacheFuture;
	private ScheduledFuture burningFieldsFuture;
	private ScheduledFuture fieldUpdateFuture;
	private ScheduledFuture fieldGenerateMobFuture;
	private ScheduledFuture fieldSpawnHarvestFuture;
	private ScheduledFuture charUpdateFuture;
	private ScheduledFuture serverNewDayFuture;
	private ScheduledFuture rankingRefreshFuture;
	private ScheduledFuture surpriseMissionFuture;
	private LocalDateTime startedTime;
	private DailiesManager dailiesManager = new DailiesManager();


	public static Server getInstance() {
		return server;
	}

	public List<World> getWorlds() {
		return worldList;
	}

	public World getWorldById(int id) {
		return Util.findWithPred(getWorlds(), w -> w.getWorldId().getVal() == id);
	}

	private void init(String[] args) {
		log.info(String.format("Starting server. Version: %s.%s", ServerConstants.VERSION, ServerConstants.MINOR_VERSION));
		long startNow = System.currentTimeMillis();
		net.swordie.ms.handlers.header.HeaderCheck.run();
		DatabaseManager.init();

		initIdAssigners();

		log.info("Initialized database in " + (System.currentTimeMillis() - startNow) + "ms.");

		// sql/migration files above the newest one in migration_history (db.migrateOnStart, on unless test.readMostly)
		if (net.swordie.ms.ServerSettings.getBoolean("db.migrateOnStart", !TEST_READ_MOSTLY) && !MigrationManager.start()) {
			log.error("Stopped Server start due to Migration Failure.");
			return;
		}
		//
		ServerRates.load(); // the rates in the server_rates table
		ServerFeatures.load(); // the quality-of-life settings in the server_features table (re-read on reloadFeatures)
		net.swordie.ms.util.ResetClock.init(); // the reset time zone (reset_time_zone), before anything reads a day
		if (!TEST_READ_MOSTLY) {
			DefinitionsExport.writeAtBoot(); // control/definitions.json + features-active.json for Server Control
			net.swordie.ms.connection.db.QueuedAccountChanges.applyAtBoot(); // Server Control's queued account edits, before logins open
		}

		try {
			try {
				checkAndCreateDat();
			} catch (IllegalStateException e) {
				log.error("Stopped Server start: " + e.getMessage(), e);
				return;
			}
			QuestData.ensureDatUpToDate(); // before players can log in: rebuilding it while quests are read isn't safe
			loadWzData();
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error("Server.init", e);
		}
		StringData.load();
        EventListData.load();
		FieldData.loadWorldMap();
		ChannelHandler.initHandlers(false);


		MapleCrypto.initialize(ServerConstants.VERSION);
		if (ServerConstants.CLIENT_API_ENABLED) {
			new Thread(new ApiAcceptor(), "launcher-api").start(); // net.clientApiEnabled
		}
		new Thread(new LoginAcceptor()).start();
		loadAndAddWorlds();
        long start = System.currentTimeMillis();
        VCoreData.loadVCoreData();
        log.info("Loaded VCore data in " + (System.currentTimeMillis() - start) + "ms");

        long startCashShop = System.currentTimeMillis();
		initCashShop();
		log.info("Loaded Cash Shop in " + (System.currentTimeMillis() - startCashShop) + "ms");

		long startFamData = System.currentTimeMillis();
		FamiliarData.loadFamiliarDataInfo();
		log.info("Loaded Familiar Data in " + (System.currentTimeMillis() - startFamData) + "ms");

		for (World world : getWorlds()) {
			for (Channel channel : world.getChannels()) {
				ChannelAcceptor ca = new ChannelAcceptor();
				ca.channel = channel;
				new Thread(ca, channel.getName()).start();
			}
		}
		if (TEST_READ_MOSTLY) {
			// v271 test server: no extensions (bots, probe, Discord, Codex, website), no periodic writers; with test.webApi
			// only the launcher routes
			addTestLaunchToken();
			if (net.swordie.ms.ServerSettings.getBoolean("test.webApi", false)) {
				// the launcher's sign up / log in / game token routes only, on test.bindAddress
				webApi = new WebApi();
				webApi.initLauncherOnly(net.swordie.ms.ServerSettings.getString("test.bindAddress", ""));
			}
			initFieldTimers();
			if (TEST_BOTS) {
				// journey bots and the probe only (test.bots): no Discord, sheet, Codex or website
				net.swordie.ms.ext.ServerHooks.loadOnly("net.swordie.ms.socialbot.SocialBotHooks");
			}
		} else {
			net.swordie.ms.ext.ServerHooks.load();
			webApi = new WebApi();
			webApi.init();

			initTimers();
		}
		// test mode too: a test session's progress is real progress
		net.swordie.ms.world.AutoSave.start();
		net.swordie.ms.connection.db.DbBackup.start(); // db.backup.hours
		new Thread(() -> {
			// starts the script engines in the background, so the first script, login or skill use after a restart
			// doesn't wait seconds for them (SCRIPT_ENGINE_NAME alone is a constant and never started anything)
			log.info(String.format("Starting %s script engine.", ScriptManagerImpl.SCRIPT_ENGINE_NAME));
			ScriptManagerImpl.warmUp();
			net.swordie.ms.client.character.skills.info.SkillInfo.warmUp();
		}, "script-warm-up").start();

		if (!TEST_READ_MOSTLY) {
			dailiesManager.initialize();
			log.info("Initialized Dailies Manager");
		}
		log.info(String.format("Finished loading server in %dms. Version: %s.%s", System.currentTimeMillis() - startNow, ServerConstants.VERSION, ServerConstants.MINOR_VERSION));
		net.swordie.ms.ext.ServerHooks.serverStarted();

		setStartedTime(LocalDateTime.now());
		logStartupBanner(System.currentTimeMillis() - startNow);
	}

	/**
	 * The last lines of startup, so it's easy to see in the console that the server is up and with which settings.
	 */
	private void logStartupBanner(long startupMillis) {
		int channels = 0;
		for (World world : getWorlds()) {
			channels += world.getChannels().size();
		}
		String exp = net.swordie.ms.constants.MobExpConstants.describe();
		String line = "=".repeat(64);
		log.info(line);
		//log.info(String.format("  %s is ONLINE (v%s.%s, started in %.1fs)", ServerConstants.SERVER_NAME,
		//		ServerConstants.VERSION, ServerConstants.MINOR_VERSION, startupMillis / 1000.0));
		//log.info(String.format("  Worlds: %d   Channels: %d   Login port: %d", getWorlds().size(), channels,
		//		ServerConstants.LOGIN_PORT));
		//log.info("  EXP rate:  " + exp);
		//log.info(String.format("  Drop rate: x%d   Meso rate: x%d   NX rate: x%s", ServerRates.getDropRate(),
		//		ServerRates.getMesoRate(), ServerRates.getNxRate()));
		//log.info("  Scrolls always succeed: " + (ServerFeatures.on(ServerFeatures.Feature.SCROLLS_ALWAYS_SUCCEED)
		//		? "yes" : "no") + "   Star Force always succeeds: "
		//		+ (ServerFeatures.on(ServerFeatures.Feature.STAR_FORCE_ALWAYS_SUCCEEDS) ? "yes" : "no"));
		//log.info(line);
	}

	private void loadAndAddWorlds() {
		var worldResourcesDir = ServerConstants.RESOURCES_DIR + "/world";
		for (var file : Objects.requireNonNull(new File(worldResourcesDir).listFiles())) {
			var cfg = WorldConfig.byConfigFile(file);
			if (cfg != null) {
				var world = cfg.toWorld();
				worldList.add(world);
			}
		}
	}

	/**
	 * Wraps a timer task: an exception (or Error) escaping a periodic task cancels it for good, silently (e.g. no more
	 * mob spawns, daily resets or ranking refreshes until a restart).
	 */
	private static Runnable safely(Runnable task, String name) {
		return () -> {
			long perf = net.swordie.ms.util.PerfLog.start();
			try {
				task.run();
			} catch (Throwable t) {
				log.error("Error in timer task " + name, t);
			} finally {
				net.swordie.ms.util.PerfLog.end("timer", name, perf);
			}
		};
	}

	/** v271 test server: shares the live database, so it runs nothing that writes much (server.properties). */
	public static final boolean TEST_READ_MOSTLY = net.swordie.ms.ServerSettings.getBoolean("test.readMostly", false);

	/** Test server: journey bots and the probe run in-process (test.bots; dirs bots.journeyDir / bots.probeDir). */
	public static final boolean TEST_BOTS = TEST_READ_MOSTLY
			&& net.swordie.ms.ServerSettings.getBoolean("test.bots", false);

	/**
	 * v271 test server: a fixed launch token (test.launchToken) for one login (test.launchUser), so the test launcher
	 * can start the client without the web API. It is used up on first use like any launch token, then lasts as a login
	 * token for this run.
	 */
	private void addTestLaunchToken() {
		String token = net.swordie.ms.ServerSettings.getString("test.launchToken", "");
		String name = net.swordie.ms.ServerSettings.getString("test.launchUser", "");
		if (token.isEmpty() || name.isEmpty()) {
			log.warn("[Test] test.launchToken / test.launchUser not set: no login possible");
			return;
		}
		var userDao = (net.swordie.orm.dao.UserDao) net.swordie.orm.dao.SworDaoFactory.getByClass(net.swordie.ms.client.User.class);
		var user = userDao.getByName(name);
		if (user == null && net.swordie.ms.ServerSettings.getBoolean("test.createLaunchUser", false)) {
			// a fresh login made as the launcher's account creation makes it (no characters, no world accounts)
			user = new User(name);
			user.setCharacterSlots(net.swordie.ms.client.character.MaxSlotsModule.newAccountCharacterSlots());
			user.setRegisterIp("127.0.0.1");
			userDao.saveOrUpdate(user, null);
			log.info("[Test] created login " + name + " (test.createLaunchUser)");
		}
		if (user == null) {
			log.warn("[Test] login " + name + " not found: no login possible");
			return;
		}
		addLaunchToken(token, user.getId(), false, 24 * 60);
		log.info("[Test] launch token ready for login " + name + " (id " + user.getId() + ")");
	}

	/** The field and character timers only (v271 test server). */
	private void initFieldTimers() {
		fieldUpdateFuture = EventManager.addFixedRateEvent(safely(this::updateFields, "updateFields"), GameConstants.FIELD_UPDATE_FREQ, GameConstants.FIELD_UPDATE_FREQ, TimeUnit.MILLISECONDS);
		fieldGenerateMobFuture = EventManager.addFixedRateEvent(safely(this::generateMobs, "generateMobs"), GameConstants.BASE_MOB_RESPAWN_RATE, GameConstants.BASE_MOB_RESPAWN_RATE, TimeUnit.MILLISECONDS);
		charUpdateFuture = EventManager.addFixedRateEvent(safely(this::updateChars, "updateChars"), GameConstants.CHAR_UPDATE_FREQ, GameConstants.CHAR_UPDATE_FREQ, TimeUnit.MILLISECONDS);
	}

	private void initTimers() {
		// server
		clearCacheFuture = EventManager.addFixedRateEvent(safely(this::clearCache, "clearCache"), ServerConstants.CLEAR_CACHE_RATE, ServerConstants.CLEAR_CACHE_RATE, TimeUnit.HOURS);

		// fields
		burningFieldsFuture = EventManager.addFixedRateEvent(safely(this::updateBurningFieldLevel, "updateBurningFieldLevel"), GameConstants.BURNING_FIELD_TIMER, GameConstants.BURNING_FIELD_TIMER, TimeUnit.MINUTES);
		fieldUpdateFuture = EventManager.addFixedRateEvent(safely(this::updateFields, "updateFields"), GameConstants.FIELD_UPDATE_FREQ, GameConstants.FIELD_UPDATE_FREQ, TimeUnit.MILLISECONDS);
		fieldGenerateMobFuture = EventManager.addFixedRateEvent(safely(this::generateMobs, "generateMobs"), GameConstants.BASE_MOB_RESPAWN_RATE, GameConstants.BASE_MOB_RESPAWN_RATE, TimeUnit.MILLISECONDS);
		fieldSpawnHarvestFuture = EventManager.addFixedRateEvent(safely(this::generateHarvests, "generateHarvests"), GameConstants.HARVEST_TIMER, GameConstants.HARVEST_TIMER, TimeUnit.MINUTES);

		// char
		charUpdateFuture = EventManager.addFixedRateEvent(safely(this::updateChars, "updateChars"), GameConstants.CHAR_UPDATE_FREQ, GameConstants.CHAR_UPDATE_FREQ, TimeUnit.MILLISECONDS);
		scheduleNewDay();
		scheduleClockResend();
		itemExpiryFuture = EventManager.addFixedRateEvent(safely(this::checkForItemExpiries, "checkForItemExpiries"), GameConstants.EXPIRY_TIME_CHECK, GameConstants.EXPIRY_TIME_CHECK, TimeUnit.MINUTES);

		// rankings
		rankingRefreshFuture = EventManager.addFixedRateEvent(safely(RankingModule::refresh, "refresh"), 0, GameConstants.RANKING_REFRESH_FREQ, TimeUnit.HOURS);

		// surprise missions
		surpriseMissionFuture = EventManager.addFixedRateEvent(safely(this::giveSurpriseMissions, "giveSurpriseMissions"), GameConstants.SURPRISE_MISSION_TRY_FREQUENCY, GameConstants.SURPRISE_MISSION_TRY_FREQUENCY, TimeUnit.MINUTES);

		// timed item replacements, every minute
		EventManager.addFixedRateEvent(safely(net.swordie.ms.world.SeasonalEvents::tick, "seasonalEvents"), 1, 1, TimeUnit.MINUTES);
	}

	/** Runs onNewDay at each daily reset (midnight in the reset time zone, ResetClock), rescheduled each time. */
	private void scheduleNewDay() {
		long delay = Math.max(1000, net.swordie.ms.util.ResetClock.nextDailyReset() - System.currentTimeMillis() + 1000);
		Runnable newDay = safely(this::onNewDay, "onNewDay");
		serverNewDayFuture = EventManager.addEvent(() -> {
			try {
				newDay.run();
			} finally {
				scheduleNewDay();
			}
		}, delay, TimeUnit.MILLISECONDS);
	}

	/**
	 * At each daylight-saving change of the reset zone, sends the clock again to everyone online: the client counts
	 * forward from the wall time it was last given, so it would stay an hour off until its next map change.
	 */
	private void scheduleClockResend() {
		long at = net.swordie.ms.util.ResetClock.nextOffsetChange();
		if (at <= 0) {
			return;
		}
		long delay = Math.max(1000, at - System.currentTimeMillis() + 1000);
		Runnable resend = safely(this::resendClock, "resendClock");
		EventManager.addEvent(() -> {
			try {
				resend.run();
			} finally {
				scheduleClockResend();
			}
		}, delay, TimeUnit.MILLISECONDS);
	}

	private void resendClock() {
		int sent = 0;
		for (var world : getWorlds()) {
			for (Char chr : world.getChars()) {
				try {
					chr.write(net.swordie.ms.connection.packet.WvsContext.serverTime());
					sent++;
				} catch (RuntimeException e) {
					log.debug("clock resend to " + chr.getId() + " failed", e);
				}
			}
		}
		log.info("[Resets] Reset zone offset changed; clock sent again to " + sent + " characters.");
	}

	/**
	 * Invoked on the start of each new day (ResetClock)
	 */
	private void onNewDay() {
		dailiesManager.onNewDayGlobal();
		for (var world : getWorlds()) {
			world.onNewDay();
		}
	}

	private void updateChars() {
		getWorlds().forEach(World::updateChars);
	}

	private void updateFields() {
		getWorlds().forEach(World::updateFields);
	}

	private void generateMobs() {
		getWorlds().forEach(World::generateMobs);
	}

	private void generateHarvests() {
		getWorlds().forEach(World::generateHarvests);
	}

	private void checkForItemExpiries() {
		for (var world : getWorlds()) {
			world.checkForItemExpiry();
		}
	}

	private void updateBurningFieldLevel() {
		for (var world : getWorlds()) {
			world.updateBurningFieldLevel();
		}
	}

	private void giveSurpriseMissions() {
		for (var world : getWorlds()) {
			world.giveSurpriseMissions();
		}
	}

	private void initIdAssigners() {
		Item.setIdAssigner(new IdAssigner<>(Item.class));
		CashItemInfo.setIdAssigner(new IdAssigner<>(CashItemInfo.class));
		MatrixRecord.setIdAssigner(new IdAssigner<>(MatrixRecord.class));
		AuctionItem.setIdAssigner(new IdAssigner<>(AuctionItem.class));
		Familiar.setIdAssigner(new IdAssigner<>(Familiar.class));
	}

	/**
	 * Version of the dat files as a whole. Bump it when a change to the WZ reading affects every dat file (e.g. the
	 * fix of the Unicode string decoding in WzInputStream, version 1), so they all get regenerated once.
	 */
	private static final int DATS_VERSION = 1;
	private static final String DATS_VERSION_FILE = "dats.version";

	/**
	 * Versions of single parts of the dat files: a generator class of DataClasses.datCreators (its simple name), or a
	 * dat that is made on first load (its Loader varName, e.g. setItemInfos). Raise a part's version when its layout
	 * or the way it's read from the WZ files changes: on the next start only that part is regenerated (instead of all
	 * dat files). Parts not listed are version 1. A part whose generation failed is stored as version 0, so it's
	 * tried again on the next start.
	 * <ul>
	 *     <li>SkillData 2: Skill.wz notCooltimeReduce</li>
	 *     <li>ItemData 2: meso prizes of reward boxes; 3: cosmetic coupons, EXP items, coupon charm EXP</li>
	 *     <li>VCoreData 2: the validtime of V core options</li>
	 *     <li>setItemInfos 2: all stat and boss damage set effects added up correctly</li>
	 *     <li>ItemData 4: equip IUCMax (golden hammer limit) and accountSharable</li>
	 *     <li>ItemData 5: one-of-a-kind (only) and expireOnLogout of non-equip items; 6: equip reqSpecJob/reqJob2</li>
	 *     <li>MobData 2: the map each ban (banish) destination is for; 3: info/ex; 4: HitParts</li>
	 *     <li>FieldData 2: linked maps (info/link), reactor names/respawn times, needSkillForFly, limitedName;
	 *     3: Gollux heart exit portal script; 4: barrierAut (Authentic Force)</li>
	 *     <li>ReactorData 2: reactor info read for every reactor</li>
	 *     <li>StringData 2: names trimmed</li>
	 * </ul>
	 */
	private static final Map<String, Integer> DAT_PART_VERSIONS = Map.of(
			"SkillData", 7, // 3: every affectedOtherSkill of a mob skill; 4: SkillStat basicStatUp, w3, w4; 5: info/mes; 6: keyFlags; 7: summon buff icon flag
			"ItemData", 14, // 8: v265 chair types; 9: ridingSkillID, damageSkinID, slotExpand, incHonorExp, meso, maplepoint; 10: rate coupons, cash pet food pets; 11: equip exceptUpgrade; 12: addTime, maxDays, protectTime, stateChangeItem, limitMin, spec cures; 13: info/exp, consumeItem (raise items); 14: equip Etuc
			"VCoreData", 2,
			"setItemInfos", 2,
			"FieldData", 4,
			"ReactorData", 2,
			"StringData", 2,
			"MobData", 4, // 3: info/ex; 4: HitParts
			"NpcData", 2 // 2: trunkGet/trunkPut -1 when absent (v265 keepers have 0)
	);
	private static final String DAT_PARTS_FILE = "dats.parts";

	private static int requiredPartVersion(String part) {
		return DAT_PART_VERSIONS.getOrDefault(part, 1);
	}

	private static Properties readDatParts() {
		Properties props = new Properties();
		File file = new File(ServerConstants.DAT_DIR, DAT_PARTS_FILE);
		if (file.exists()) {
			try (FileReader fr = new FileReader(file)) {
				props.load(fr);
			} catch (IOException e) {
				log.error("Could not read " + DAT_PARTS_FILE, e);
			}
		}
		return props;
	}

	private static void writeDatParts(Properties props) {
		try (FileWriter fw = new FileWriter(new File(ServerConstants.DAT_DIR, DAT_PARTS_FILE))) {
			props.store(fw, "Versions of the parts of the dat files (see Server.DAT_PART_VERSIONS)");
		} catch (IOException e) {
			log.error("Could not write " + DAT_PARTS_FILE, e);
		}
	}

	private static int storedPartVersion(Properties props, String part) {
		try {
			return Integer.parseInt(props.getProperty(part, "1").trim()); // dats made before the parts file: 1
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * Regenerates the dat parts that are outdated or whose generation failed before.
	 *
	 * @return whether anything was regenerated
	 */
	private boolean updateDatParts() {
		Properties props = readDatParts();
		boolean changed = false;
		Set<String> generators = new HashSet<>();
		for (Class c : DataClasses.datCreators) {
			String part = c.getSimpleName();
			generators.add(part);
			int required = requiredPartVersion(part);
			int stored = storedPartVersion(props, part);
			if (stored >= required) {
				continue;
			}
			log.warn(String.format("The %s dat files are outdated (part version %d, expected %d): regenerating them.",
					part, stored, required));
			try {
				c.getMethod("generateDatFiles").invoke(null);
				props.setProperty(part, String.valueOf(required));
			} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
				// its old dat files may have a different layout than the server now reads: don't start with them
				props.setProperty(part, "0");
				writeDatParts(props);
				throw new IllegalStateException("Regenerating the " + part + " dat files failed (see the error). " +
						"The server can't start with the outdated ones; it tries again on the next start.", e);
			}
			changed = true;
		}
		for (Map.Entry<String, Integer> e : DAT_PART_VERSIONS.entrySet()) {
			String part = e.getKey();
			if (generators.contains(part) || storedPartVersion(props, part) >= e.getValue()) {
				continue;
			}
			// a dat made on first load: deleting it makes loadWzData make it again from the WZ files
			File file = new File(ServerConstants.DAT_DIR, part + ".dat");
			if (file.exists() && !file.delete()) {
				log.error("Could not delete the outdated " + file.getPath() + "; delete it by hand.");
				continue;
			}
			log.warn(String.format("%s.dat is outdated: it's made again from the WZ files.", part));
			props.setProperty(part, String.valueOf(e.getValue()));
			changed = true;
		}
		writeDatParts(props);
		return changed;
	}

	private static void clearDataCaches() {
		DropData.clear();
		FieldData.clear();
		ItemData.clear();
		MobData.clear();
		NpcData.clear();
		QuestData.clear();
		SkillData.clear();
		ReactorData.clear();
		EtcData.clear();
	}

	private static int readDatsVersion() {
		File versionFile = new File(ServerConstants.DAT_DIR, DATS_VERSION_FILE);
		if (!versionFile.exists()) {
			return 0;
		}
		try (BufferedReader br = new BufferedReader(new FileReader(versionFile))) {
			return Integer.parseInt(br.readLine().trim());
		} catch (IOException | NumberFormatException | NullPointerException e) {
			return 0;
		}
	}

	private void checkAndCreateDat() {
		File file = new File(ItemData.EQUIPS_FILE);
		boolean exists = file.exists();
		int datsVersion = readDatsVersion();
		if (exists && datsVersion < DATS_VERSION) {
			// The dats were made by an older WZ reader. Keep them as a backup and let everything be regenerated
			// (including the dats that are made on first load, which only happens when their file is missing).
			File datDir = new File(ServerConstants.DAT_DIR);
			File backup = new File(datDir.getAbsoluteFile().getParentFile(),
					datDir.getName() + "_old_" + System.currentTimeMillis());
			if (datDir.renameTo(backup)) {
				log.warn(String.format("The dat files are outdated (dats.version %d, expected %d). They were moved to %s " +
						"and will be regenerated from the WZ files.", datsVersion, DATS_VERSION, backup.getPath()));
				exists = false;
			} else {
				log.error("The dat files are outdated, but the dat folder could not be moved. Delete it to regenerate them.");
			}
		}
		if (!exists) {
			log.info("Dat files cannot be found (at least not the equip dats). All dats will now be generated. This may take a long while.");
			Util.makeDirIfAbsent(ServerConstants.DAT_DIR);
			List<String> failed = new ArrayList<>();
			for (Class c : DataClasses.datCreators) {
				try {
					Method m = c.getMethod("generateDatFiles");
					m.invoke(null);
				} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
					failed.add(c.getSimpleName());
					log.error("Server.checkAndCreateDat", e);
				}
			}
			Properties parts = new Properties();
			for (Class c : DataClasses.datCreators) {
				String part = c.getSimpleName();
				parts.setProperty(part, failed.contains(part) ? "0" : String.valueOf(requiredPartVersion(part)));
			}
			for (Map.Entry<String, Integer> e : DAT_PART_VERSIONS.entrySet()) {
				if (!parts.containsKey(e.getKey())) {
					// made on first load: remove an old one (the dat folder may have been kept) so it's made anew
					File old = new File(ServerConstants.DAT_DIR, e.getKey() + ".dat");
					if (old.exists() && !old.delete()) {
						log.error("Could not delete " + old.getPath() + "; delete it by hand.");
					}
					parts.setProperty(e.getKey(), String.valueOf(e.getValue()));
				}
			}
			writeDatParts(parts);
			if (!failed.isEmpty()) {
				log.error("Generating the dat files of " + String.join(", ", failed) + " failed (see the errors above). "
						+ "They are generated again on the next start.");
			}
			// only once they're all made, so an interrupted generation is redone on the next start
			try (PrintWriter pw = new PrintWriter(new FileWriter(new File(ServerConstants.DAT_DIR, DATS_VERSION_FILE)))) {
				pw.print(DATS_VERSION);
			} catch (IOException e) {
				log.error("Could not write " + DATS_VERSION_FILE, e);
			}
			// The generation leaves the data it read from the WZ files in the loaders' caches, and that isn't the
			// same as what's read back from the dats: the fields read from the WZ files have no MobGens (their mobs
			// are only made into MobGens when a field is read from fields.dat), so no mob would spawn until the
			// server restarts. Clear the caches so everything is read from the new dats, as on a normal start.
			clearDataCaches();
			log.info("Generated all dat files; the server now uses them.");
		} else if (updateDatParts()) {
			clearDataCaches();
			log.info("Regenerated the outdated dat files; the server now uses them.");
		}
		if (!new File(StringData.NPC_DEFAULT_LINES_FILE).exists()) {
			try {
				StringData.saveNpcDefaultLines();
			} catch (RuntimeException e) {
				log.error("Could not make the default NPC dialogue from the game data", e);
			}
		}
	}

	public void loadWzData() throws IllegalAccessException, InvocationTargetException {
		String datFolder = ServerConstants.DAT_DIR;
		for (Class c : DataClasses.dataClasses) {
			for (Method method : c.getMethods()) {
				String name;
				Loader annotation = method.getAnnotation(Loader.class);
				if (annotation != null) {
					name = annotation.varName();
					File file = new File(datFolder, name + ".dat");
					boolean exists = file.exists();
					long start = System.currentTimeMillis();
					try {
						method.invoke(c, file, exists);
					} catch (InvocationTargetException e) {
						// one bad loader shouldn't leave every loader after it unloaded
						log.error("Could not load " + name + " with " + c.getSimpleName() + "." + method.getName(),
								e.getCause());
						continue;
					}
					long total = System.currentTimeMillis() - start;
					if (exists) {
						log.info(String.format("Took %dms to load from %s", total, file.getName()));
					} else {
						log.info(String.format("Took %dms to load using %s", total, method.getName()));
					}
				}
			}
		}
	}

	public TransferInfo getTransferInfo(int charId, int worldId) {
		for (Channel c : getWorldById(worldId).getChannels()) {
			if (c.getTransfers().containsKey(charId)) {
				return c.getTransfers().get(charId);
			}
		}
		return null;
	}

	public void clearCache() {
		try {
			DropData.clear();
			FieldData.clear();
			ItemData.clear();
			MobData.clear();
			NpcData.clear();
			QuestData.clear();
			SkillData.clear();
			ReactorData.clear();
			EtcData.clear();
			net.swordie.ms.loaders.DataTable.clear();
			ScriptManagerImpl.clear();
			for (World world : getWorlds()) {
				world.clearCache();
			}
			purgeExpiredTokens();
			ChannelHandler.initHandlers(true);
			TradeLogRoute.usernames.clear();
		} catch (Exception e) {
			ErrorLogger.logAsClientError(e);
		}
	}

	private void purgeExpiredTokens() {
		var tokens = new HashMap<>(getAuthTokens());
		var toRemove = new HashSet<String>();
		for (var entry : tokens.entrySet()) {
			var key = entry.getKey();
			var info = entry.getValue();
			if (info == null || info.isExpired()) {
				toRemove.add(key);
			}
		}
		toRemove.forEach(this::removeUserFromAuthToken);
	}

	public void initCashShop() {
		cashShop = new CashShop();
	}

	public CashShop getCashShop() {
		return this.cashShop;
	}

	public void addUser(User user) {
		users.put(user.getId(), user);
	}

	public void removeUser(User user) {
		if (user == null) {
			return;
		}
		// Remove from transfer
		for (var world : getWorlds()) {
			world.removeUserFromTransfer(user);
		}

		users.remove(user.getId());
		removeCharsByUser(user);
	}

	public boolean isUserLoggedIn(User user) {
		return users.containsKey(user.getId()) || isInTransfer(user);
	}

	public boolean isInShutdown() {
		return isInShutdown;
	}

	public void setInShutdown(boolean inShutdown) {
		isInShutdown = inShutdown;
	}

	public int getMinutesRemaining() {
		return minutesRemaining;
	}

	public void setMinutesRemaining(int minutesRemaining) {
		this.minutesRemaining = minutesRemaining;
	}

	public int getShutdownScheduledMinutes() {
		return shutdownScheduledMinutes;
	}

	public void setShutdownScheduledMinutes(int shutdownScheduledMinutes) {
		this.shutdownScheduledMinutes = shutdownScheduledMinutes;
	}

	public LocalDateTime getStartedTime() {
		return startedTime;
	}

	public void setStartedTime(LocalDateTime startedTime) {
		this.startedTime = startedTime;
	}

	private Map<String, AuthInfo> getAuthTokens() {
		return authTokens;
	}

	public AuthInfo getAuthInfo(String key) {
		return key == null ? null : getAuthTokens().get(normalizeToken(key));
	}

	/**
	 * Login tokens are compared without case and without anything but letters and digits: the game client can hand the
	 * token ("WebStart <token>") back in another form (e.g. upper case, no dashes), and then the
	 * login fell back to the first account on this PC.
	 */
	public static String normalizeToken(String token) {
		return token == null ? "" : token.replaceAll("[^A-Za-z0-9]", "").toLowerCase(java.util.Locale.ROOT);
	}

	public void addAuthToken(byte[] token, int userID, boolean isPlayer) {
		String tokenStr = new String(token);
		FileTime expiryDate = FileTime.fromDate(LocalDateTime.now().plusMinutes(ServerConstants.TOKEN_EXPIRY_TIME));
		boolean needsToVerifyWz = isPlayer && ServerConstants.CHECK_WZ_ON_LOGIN;
		AuthInfo entry = new AuthInfo(userID, expiryDate, false, needsToVerifyWz);
		getAuthTokens().put(normalizeToken(tokenStr), entry);
	}

	/** How long a launch token (see {@link #addLaunchToken}) may wait for the game to use it. */
	public static final long LAUNCH_TOKEN_MINUTES = Math.max(1,
			net.swordie.ms.ServerSettings.getInt("web.launchTokenMinutes", 10));

	/**
	 * Adds a token from a launch token request. It only works for starting the game (not as a
	 * web API login), and only within {@link #LAUNCH_TOKEN_MINUTES} minutes; once the game has used it, it lasts as
	 * long as a login token, so going back to the login screen in that game keeps working.
	 */
	public void addLaunchToken(String token, int userID, boolean isPlayer) {
		FileTime expiryDate = FileTime.fromDate(LocalDateTime.now().plusMinutes(LAUNCH_TOKEN_MINUTES));
		boolean needsToVerifyWz = isPlayer && ServerConstants.CHECK_WZ_ON_LOGIN;
		AuthInfo entry = new AuthInfo(userID, expiryDate, false, needsToVerifyWz);
		entry.setLaunchToken(true);
		getAuthTokens().put(normalizeToken(token), entry);
	}

	/**
	 * {@link #addLaunchToken(String, int, boolean)} with its own waiting time, for the launcher (which logs in first
	 * and starts the game later).
	 *
	 * @return false if the token is already in use (nothing added)
	 */
	public boolean addLaunchToken(String token, int userID, boolean isPlayer, long minutes) {
		FileTime expiryDate = FileTime.fromDate(LocalDateTime.now().plusMinutes(minutes));
		boolean needsToVerifyWz = isPlayer && ServerConstants.CHECK_WZ_ON_LOGIN;
		AuthInfo entry = new AuthInfo(userID, expiryDate, false, needsToVerifyWz);
		entry.setLaunchToken(true);
		return getAuthTokens().putIfAbsent(normalizeToken(token), entry) == null;
	}

	/**
	 * @return the login id a token (launch or login) belongs to without using it up, or 0 if it's unknown or expired
	 */
	public int peekUserIdFromAuthToken(String token) {
		AuthInfo authInfo = getAuthInfo(token);
		if (isAuthInfoNotValid(authInfo) || authInfo.getExpiryTime().isExpired()) {
			return 0;
		}
		return authInfo.getUserId();
	}

	public int getUserIdFromAuthToken(String token, boolean needsWzVerified) {
		return getUserIdFromAuthToken(token, needsWzVerified, null);
	}

	/** The login id a token started the game for, or GAME_TOKEN_WRONG_ADDRESS when it comes from another address. */
	public static final int GAME_TOKEN_WRONG_ADDRESS = -1;

	/**
	 * @param address the game connection's address: a game token is tied to the address it was first used from, and
	 *                lasts {@link ServerConstants#TOKEN_EXPIRY_TIME} minutes from its last use (the client sends it
	 *                again whenever it goes back to the login screen)
	 */
	public int getUserIdFromAuthToken(String token, boolean needsWzVerified, String address) {
		if (token == null) {
			return 0;
		}

		AuthInfo authInfo = getAuthTokens().getOrDefault(normalizeToken(token), null);

		if (isAuthInfoNotValid(authInfo) || authInfo.getExpiryTime().isExpired()) {
			removeUserFromAuthToken(token);
			return 0;
		}

		if (authInfo.isLaunchToken()) {
			if (!needsWzVerified) {
				return 0; // a launch token only starts the game; it's not a web API login
			}
			// first use by the game: from now on it lasts like a login token, from this address only
			authInfo.setLaunchToken(false);
			// Contributed by Arixal (from GitLab).
			authInfo.setGameOnly(true);
			authInfo.setGameAddress(address);
		} else if (!needsWzVerified && authInfo.isGameOnly()) {
			return 0; // a used launch token still only starts the game: it's never a web API login
		} else if (needsWzVerified && authInfo.getGameAddress() != null && address != null
				&& !authInfo.getGameAddress().equals(address)) {
			return GAME_TOKEN_WRONG_ADDRESS;
		}
		if (needsWzVerified) {
			authInfo.setExpiryTime(FileTime.fromDate(LocalDateTime.now().plusMinutes(ServerConstants.TOKEN_EXPIRY_TIME)));
		}

		int userId = 0;
		if (!needsWzVerified || authInfo.isWzVerified() || !authInfo.isNeedsWzVerified()) {
			userId = authInfo.getUserId();
		}

		return userId;
	}

	private static boolean isAuthInfoNotValid(AuthInfo authInfo) {
		return authInfo == null || authInfo.getExpiryTime() == null;
	}

	public User getUserFromAuthToken(String token) {
		var userId = getUserIdFromAuthToken(token, false);

		User user = null;
		if (userId > 0) {
			user = getUserById(userId);
			if (user == null) {
				user = userDao.getById(userId);
			}
		}

		return user;
	}

	public void removeUserFromAuthToken(String token) {
		if (token != null) {
			getAuthTokens().remove(normalizeToken(token));
		}
	}

	public void shutdown(int minutes) {
		setShutdownScheduledMinutes(minutes);
		setInShutdown(true);
		saveTimeInfo();
		if (minutes != 0) {
			for (World world : getWorlds()) {
				for (int i = 0; i < minutes; i++) {
					int minsLeft = minutes - i;
					String msg = "[Notice] Server is shutting down in " + minsLeft + " minutes. " +
							"Please log off safely before the server shuts down.";
					shutdownFutures.add(
							EventManager.addEvent(() -> {
										setMinutesRemaining(minsLeft);
										world.broadcastPacket(UserLocal.chatMsg(ChatType.Notice2, msg));
										log.warn(msg);
									}
									, i, TimeUnit.MINUTES));
				}
			}
			shutdownFutures.add(EventManager.addEvent(this::startWorldShutdown, minutes, TimeUnit.MINUTES));
		} else {
			startWorldShutdown();
		}
	}

	private void saveTimeInfo() {
		log.info("Writing time info");
		try (var writer = new FileWriter(ServerConstants.LOG_DIR + "/timeinfo.csv", true)){
			writer.write("header;count;avgMs\r\n");
			for (var info : ChannelHandler.timeInfo.values()) {
				writer.write(String.format("%s", info.toString()));
				writer.write("\r\n");
			}
		} catch (IOException e) {
			log.error("Server.saveTimeInfo", e);
		}
		log.info("Wrote time info");
	}

	public void cancelShutdown() {
		setInShutdown(false);
		if (!shutdownFutures.isEmpty()) {
			for (ScheduledFuture sf : shutdownFutures) {
				sf.cancel(false);
			}
			for (World world : getWorlds()) {
				world.broadcastPacket(UserLocal.chatMsg(ChatType.Notice2, "[Notice] Cancelled shutdown."));
			}
		}
	}

	public static void main(String[] args) {
		ConsoleLog.start(); // logs/console, for the launcher's Logs page
		getInstance().init(args);
	}

	public void removeCharsByUser(User user) {
		if (user != null) {
			for (World world : getWorlds()) {
				world.removeCharsOfUser(user);
			}
		}
	}

	public User getUserById(int userId) {
		return users.get(userId);
	}

	public int getPlayerCount() {
		int total = 0;
		for (var world : getWorlds()) {
			total += world.getPlayerCount();
		}
		return total;
	}

	public Set<AuctionItem> getAuctionsByAccountID(int accountId) {
		var items = new HashSet<AuctionItem>();
		for (var world : getWorlds()) {
			items.addAll(world.getAuctionsByAccountID(accountId));
		}
		return items;
	}

	public User getActiveUserByIdOrDb(int userId) {
		var user = getUserById(userId);
		if (user == null) {
			user = userDao.getById(userId);
		}
		return user;
	}

	public boolean isInTransfer(User user) {
		return getUserFromTransfer(user.getId()) != null;
	}

	public User getUserFromTransfer(int userId) {
		for (var w : getWorlds()) {
			var user = w.getUserFromTransfer(userId);
			if (user != null) {
				return user;
			}
		}

		return null;
	}

	public DailiesManager getDailiesManager() {
		return dailiesManager;
	}

	public Set<Char> getAllOnlineCharacters() {
		var chars = new HashSet<Char>();
		for (var world : getWorlds()) {
			chars.addAll(world.getChars());
		}
		return chars;
	}

	private void startWorldShutdown() {
		log.warn("Starting shutdown.");
		var start = System.currentTimeMillis();
		for (World world : getWorlds()) {
			world.shutdown();
		}
		net.swordie.ms.world.AutoSave.markShutdownSaved();
		log.warn(String.format("Shutdown complete, took %dms", System.currentTimeMillis() - start));
		System.exit(0);
	}
}
