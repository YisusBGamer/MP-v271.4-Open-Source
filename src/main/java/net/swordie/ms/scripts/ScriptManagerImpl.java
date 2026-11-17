package net.swordie.ms.scripts;

import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.client.character.MenuModule;
import net.swordie.ms.constants.FieldConstants;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.swordie.ms.Server;
import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.AccountBossCooldown;
import net.swordie.ms.client.Client;
import net.swordie.ms.client.alliance.Alliance;
import net.swordie.ms.client.alliance.AllianceResult;
import net.swordie.ms.client.character.BeautyModule;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.ProgressionLog;
import net.swordie.ms.client.character.MonsterPark;
import net.swordie.ms.client.character.avatar.AvatarLook;
import net.swordie.ms.client.character.commerce.voyage.Voyage;
import net.swordie.ms.client.character.items.*;
import net.swordie.ms.client.character.modules.InventoryModule;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.client.character.scene.Scene;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatBase;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.character.skills.vmatrix.MatrixRecord;
import net.swordie.ms.client.character.skills.vmatrix.NodestoneModule;
import net.swordie.ms.client.character.union.Union;
import net.swordie.ms.client.character.union.UnionMember;
import net.swordie.ms.client.character.union.UnionRaid;
import net.swordie.ms.client.guild.Guild;
import net.swordie.ms.client.guild.GuildMember;
import net.swordie.ms.client.guild.result.GuildResult;
import net.swordie.ms.client.guild.result.GuildType;
import net.swordie.ms.client.jobs.legend.Shade;
import net.swordie.ms.client.party.Party;
import net.swordie.ms.client.party.PartyMember;
import net.swordie.ms.client.party.PartyResult;
import net.swordie.ms.client.trunk.TrunkDlg;
import net.swordie.ms.connection.packet.*;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.connection.packet.field.LucidFieldPacket;
import net.swordie.ms.connection.packet.field.PapulatusFieldPacket;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.*;
import net.swordie.ms.enums.*;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.DeathType;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.Reactor;
import net.swordie.ms.life.drop.Drop;
import net.swordie.ms.life.drop.DropInfo;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.boss.papulatus.PapulatusFieldObject;
import net.swordie.ms.life.mob.boss.papulatus.PapulatusLaserInfo;
import net.swordie.ms.life.mob.boss.papulatus.PapulatusTweezerInfo;
import net.swordie.ms.life.mob.boss.will.WillModule;
import net.swordie.ms.life.mob.skill.MobSkillID;
import net.swordie.ms.life.npc.Npc;
import net.swordie.ms.life.npc.NpcMessageType;
import net.swordie.ms.life.npc.NpcScriptInfo;
import net.swordie.ms.loaders.*;
import net.swordie.ms.loaders.containerclasses.ItemInfo;
import net.swordie.ms.util.*;
import net.swordie.ms.util.container.Tuple;
import net.swordie.ms.world.World;
import net.swordie.ms.world.field.*;
import net.swordie.ms.world.field.bosses.gollux.FallingCatcher;
import net.swordie.ms.world.field.bosses.gollux.GolluxMiniMapFieldClearType;
import net.swordie.ms.world.field.fieldeffect.FieldEffect;
import net.swordie.ms.world.field.fieldeffect.GreyFieldType;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.ms.world.field.obstacleatom.ObstacleAtomFactory;
import net.swordie.ms.world.field.obstacleatom.ObstacleAtomInfo;
import net.swordie.ms.world.field.obstacleatom.ObstacleInRowInfo;
import net.swordie.ms.world.field.obstacleatom.ObstacleRadianInfo;
import net.swordie.ms.world.shop.NpcShopDlg;
import net.swordie.orm.dao.AllianceDao;
import net.swordie.orm.dao.CharDao;
import net.swordie.orm.dao.SworDaoFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.python.core.PyDictionary;
import org.python.core.PyTuple;

import javax.script.*;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.PapulatusTimeLock;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.RideVehicle;
import static net.swordie.ms.enums.ChatType.*;
import static net.swordie.ms.life.npc.NpcMessageType.*;

/**
 * Created on 2/19/2018.
 *
 * @see ScriptManager
 */
public class ScriptManagerImpl implements ScriptManager {
    private static final ExecutorService service = Executors.newVirtualThreadPerTaskExecutor();

    private static final int HENESYS_HAIR_COLOR_COUPON_REG = 5151000;
    private static final int HALF_AND_HALF_COLORBLEND_HAIR_DYE_COUPON = 5151040;
    private static final int HALF_AND_HALF_COLORBLEND_HAIR_DYE_COUPON_2 = 5151042;
    private static final int ONE_TIME_EYE_COLOR = 5152100;
    private static final int HENESYS_SKIN_COUPON = 5153000;
    private static final int BLACK_HEAVEN_CORE_MOB = 8950000;
    private static final int BLACK_HEAVEN_CORE_MOB_2 = 8950100;
    private static final int GOLDEN_WYVERN_MOB = 9833111;
    private static final int MU_LUNG_DOJO_ATRIUM_FIELD = 925070000;

    private static final AllianceDao allianceDao = (AllianceDao) SworDaoFactory.getByClass(Alliance.class);
    private static final CharDao charDao = (CharDao) SworDaoFactory.getByClass(Char.class);

    public static final String SCRIPT_ENGINE_NAME = "python";
    public static final String QUEST_COMPLETE_SCRIPT_END_TAG = "e";
    public static final String QUEST_START_SCRIPT_END_TAG = "s";
    public static final String QUEST_RESIGN_SCRIPT_END_TAG = "x";

    private static final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("script-%d").build();
    private static final ExecutorService SCRIPT_EXECUTOR_SERVICE = Executors.newCachedThreadPool(namedThreadFactory);
    private static final Pattern listPattern = Pattern.compile("(.)*#[lL][0-9]+#(.)*");
    private static final String SCRIPT_ENGINE_EXTENSION = ".py";
    private static final String DEFAULT_SCRIPT = "undefined";
    public static final String INTENDED_NPE_MSG = "Intended NPE by forceful script stop.";
    // shared by every script thread; a null value marks a script file that doesn't exist
    private static final Map<String, CompiledScript> scriptCache = Collections.synchronizedMap(new HashMap<>());
    public static final Logger log = LogManager.getRootLogger();
    private static final Lock fileReadLock = new ReentrantLock();

    private static final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName(SCRIPT_ENGINE_NAME);
    private static final Random random = new Random();

    /**
     * Starts the script engine and its compiler at server start, so the first NPC, portal or quest script after a
     * restart doesn't make that player wait seconds for it.
     */
    public static void warmUp() {
        try {
            long start = System.currentTimeMillis();
            ((Compilable) scriptEngine).compile("warm_up = 1 + 1").eval(scriptEngine.createBindings());
            log.info(String.format("Started the %s script engine in %dms.", SCRIPT_ENGINE_NAME,
                    System.currentTimeMillis() - start));
        } catch (Exception e) {
            log.warn("Could not start the script engine ahead of time", e);
        }
    }

    private static final String[] fieldEventMethodNames = new String[]{
            "createObstacleAtom",
            "createObstacleAtomToFoothold",
            "giveUnityTrainingExp",
    };

    private static boolean isFieldEventMethodName(String methodName) {
        return Arrays.asList(fieldEventMethodNames).contains(methodName);
    }

    private final Char chr;
    private final Field field;
    private final boolean isField;
    private final NpcScriptInfo npcScriptInfo;
    private final Map<ScriptType, ScriptInfo> scripts;
    private final FieldTransferInfo fieldTransferInfo;

    private int returnField = 0;
    private ScriptType lastActiveScriptType;
    // scripts add events from their own threads while a map change iterates them
    private Map<ScheduledFuture, Boolean> events = new java.util.concurrent.ConcurrentHashMap<>();
    private ScriptMemory memory = new ScriptMemory();
    /** Properties the next instance this script warps into starts with (see {@link #setNextInstanceProperty}). */
    private final Map<String, Object> nextInstanceProperties = new HashMap<>();
    private boolean curNodeEventEnd;

    private ScriptManagerImpl(Char chr, Field field) {
        this.chr = chr;
        this.field = field;
        this.npcScriptInfo = new NpcScriptInfo();
        this.scripts = new ConcurrentHashMap<>(); // scripts start and finish on other threads
        this.isField = chr == null;
        this.lastActiveScriptType = ScriptType.None;
        this.fieldTransferInfo = new FieldTransferInfo();
        fieldTransferInfo.setField(isField);
    }

    public ScriptManagerImpl(Char chr) {
        this(chr, null);
    }

    public ScriptManagerImpl(Field field) {
        this(null, field);
    }

    private Bindings getBindingsByType(ScriptType scriptType) {
        ScriptInfo si = getScriptInfoByType(scriptType);
        return si == null ? null : si.getBindings();
    }

    public ScriptInfo getScriptInfoByType(ScriptType scriptType) {
        return scripts.getOrDefault(scriptType, null);
    }

    @Override
    public Char getChr() {
        var charr = chr;
        if (charr == null && getField().getChars().size() > 0) {
            charr = getField().getChars().get(0);
        }
        return charr;
    }

    public String getScriptNameByType(ScriptType scriptType) {
        return getScriptInfoByType(scriptType).getScriptName();
    }

    public Invocable getInvocableByType(ScriptType scriptType) {
        return getScriptInfoByType(scriptType).getInvocable();
    }

    public int getParentIDByScriptType(ScriptType scriptType) {
        return getScriptInfoByType(scriptType) != null ? getScriptInfoByType(scriptType).getParentID() : 2007;
    }

    public int getObjectIDByScriptType(ScriptType scriptType) {
        return getScriptInfoByType(scriptType) != null ? getScriptInfoByType(scriptType).getObjectID() : 0;
    }

    public void startScript(int parentID, ScriptType scriptType) {
        startScript(parentID, 0, scriptType);
    }

    public void startScript(int parentID, String scriptName, ScriptType scriptType) {
        startScript(parentID, 0, scriptName, scriptType, null);
    }

    public void startScript(int parentID, String scriptName, ScriptType scriptType, Map<String, Object> customBindings) {
        startScript(parentID, 0, scriptName, scriptType, customBindings);
    }

    public void startScript(int parentID, int objID, ScriptType scriptType) {
        startScript(parentID, objID, parentID + ".py", scriptType, null);
    }

    public void startScript(int parentId, int objId, String scriptName, ScriptType scriptType) {
        startScript(parentId, objId, scriptName, scriptType, null);
    }

    public void startScript(int parentID, int objID, String scriptName, ScriptType scriptType, String key, Object value) {
        Map<String, Object> props = new HashMap<>();
        props.put(key, value);
        startScript(parentID, objID, scriptName, scriptType, props);
    }

    public synchronized void startScript(int parentID, int objID, String scriptName, ScriptType scriptType, Map<String, Object> customBindings) {
        if (scriptType == ScriptType.None || (scriptType == ScriptType.Quest && !isQuestScriptAllowed())) {
            log.debug(String.format("Did not allow script %s to go through (type %s)  |  Active Script Type: %s", scriptName, scriptType, getLastActiveScriptType()));
            return;
        }
        // no portal or reactor script while a dialog is open (an NPC or quest chat, or a field scene waiting for an
        // answer): its stop() would reset the shared dialog state and the open chat would never get the player's answer.
        // The official client can't use portals while talking either.
        if ((scriptType == ScriptType.Portal || scriptType == ScriptType.Reactor) && getChr() != null
                && (isWaitingForAnswer() || isActive(ScriptType.Npc) || isActive(ScriptType.Quest))) {
            log.debug(String.format("Did not start %s script %s: a dialog is open (%s)", scriptType, scriptName,
                    getLastActiveScriptType()));
            getChr().dispose();
            return;
        }

        var activeScriptType = getScriptInfoByType(scriptType);

        if (isActive(scriptType) && (scriptType != ScriptType.Field && scriptType != ScriptType.FirstEnterField)) { // because Field Scripts don't get disposed.
            if (activeScriptType != null && parentID != Shade.LIVER) { // Liver to prevent chat spam
                getChr().chatMessage(String.format("Already running a script of the same type (%s, id %d)! " +
                                "Type @stuck if this is not intended.",
                        scriptType.getDir(),
                        activeScriptType.getParentID()));
                log.debug(String.format("Could not run script %s because one of the same type is already running (%s, type %s)",
                        scriptName,
                        activeScriptType.getScriptName(),
                        scriptType));
            } else if (activeScriptType == null) {
                getChr().chatMessage(String.format("Already running a script of the same type (%s)! Type @stuck if this" +
                                " is not intended.",
                        scriptType.getDir()));
                log.debug(String.format("Could not run script %s because one of the same type is already running (type %s)",
                        scriptName,
                        scriptType));
            }
            return;
        }
        // one lookup for the check and the binding: another player's hit can remove the reactor in between
        Life hitReactor = scriptType == ScriptType.Reactor ? getField().getLifeByObjectID(objID) : null;
        if (scriptType == ScriptType.Reactor && !(hitReactor instanceof Reactor)) {
            // the reactor was removed (another hit finished it) before this hit's script started
            log.debug(String.format("Skipped reactor script %s: reactor %d is gone", scriptName, objID));
            return;
        }
        setLastActiveScriptType(scriptType);

        if (!isField()) {
            getChr().debugMessage(String.format("Starting script %s, scriptType %s.", scriptName, scriptType));
            log.debug(String.format("Starting script %s, scriptType %s.", scriptName, scriptType));
        }

        resetParam();

        Bindings bindings = getBindingsByType(scriptType);
        if (bindings == null) {
            bindings = scriptEngine.createBindings();
            bindings.put("sm", this);
            bindings.put("chr", getChr());
        }
        bindings.put("field", getChr() == null ? field : getField());
        bindings.put("parentID", parentID);
        bindings.put("scriptType", scriptType);
        bindings.put("objectID", objID);

        if (customBindings != null) {
            bindings.putAll(customBindings);
        }

        if (scriptType == ScriptType.Reactor) {
            bindings.put("reactor", hitReactor);
        }

        if (scriptType == ScriptType.Quest) {
            // QuestHandler passes these as custom bindings; otherwise guess from the script name's last letter
            if (customBindings == null || !customBindings.containsKey("startQuest")) {
                bindings.put("startQuest",
                        scriptName.charAt(scriptName.length() - 1) == QUEST_START_SCRIPT_END_TAG.charAt(0)); // biggest hack eu
            }
            if (customBindings == null || !customBindings.containsKey("resignQuest")) {
                bindings.put("resignQuest",
                        scriptName.charAt(scriptName.length() - 1) == QUEST_RESIGN_SCRIPT_END_TAG.charAt(0));
            }
        }

        ScriptInfo scriptInfo = new ScriptInfo(scriptType, bindings, parentID, scriptName);
        scriptInfo.setActive(true);
        if (scriptType == ScriptType.Npc) {
            getNpcScriptInfo().setTemplateID(parentID);
        }
        scriptInfo.setObjectID(objID);
        getScripts().put(scriptType, scriptInfo);
        SCRIPT_EXECUTOR_SERVICE.execute(() -> startScript(scriptName, scriptType)); // makes the script execute async
    }

    private boolean isQuestScriptAllowed() {
        return getLastActiveScriptType() == ScriptType.None;
    }

    public void notifyMobDeath(Mob mob) {
        for (ScriptType type : new ScriptType[]{ScriptType.FirstEnterField, ScriptType.Field, ScriptType.Quest}) {
            if (isActive(type)) {
                ScriptInfo si = getScriptInfoByType(type);
                if (si != null) {
                    si.addResponseMob(mob);
                }
            }
        }
    }

    public Mob waitForMobDeath() {
        for (ScriptType type : new ScriptType[]{ScriptType.FirstEnterField, ScriptType.Field, ScriptType.Quest}) {
            if (isActive(type)) {
                ScriptInfo si = getScriptInfoByType(type);
                Mob mob = si == null ? null : si.awaitResponseMob(30 * 60 * 1000L);
                if (mob == null) {
                    throw new NullPointerException(INTENDED_NPE_MSG);
                }
                return mob;
            }
        }
        throw new NullPointerException(INTENDED_NPE_MSG);
    }

    public Mob waitForMobDeath(int... possibleMobs) {
        while (true) {
            Mob mob = waitForMobDeath();
            for (int mobID : possibleMobs) {
                if (mob.getTemplateId() == mobID) {
                    return mob;
                }
            }
        }
    }

    private String getScriptDir(String name, ScriptType scriptType) {
        var isTespia = ServerConstants.IS_TESPIA;

        // First check if script is in scripts_tespia directory
        if (isTespia) {
            String dir = String.format("%s/%s/%s%s", ServerConstants.SCRIPT_DIR_TESPIA,
                    scriptType.getDir().toLowerCase(), name, SCRIPT_ENGINE_EXTENSION);
            boolean exists = scriptCache.containsKey(dir) || new File(dir).exists();
            if (exists && scriptCache.containsKey(dir)) {
                exists = scriptCache.get(dir) != null;
            }
            if (exists) {
                return dir;
            }
        }

        // If script doesn't exist in scripts_tespia directory. Look into normal scripts directory
        String dir = String.format("%s/%s/%s%s", ServerConstants.SCRIPT_DIR,
                scriptType.getDir().toLowerCase(), name, SCRIPT_ENGINE_EXTENSION);
        boolean exists = scriptCache.containsKey(dir) || new File(dir).exists();
        if (exists && scriptCache.containsKey(dir)) {
            exists = scriptCache.get(dir) != null;
        }

        // v265 data renamed boss first-enter scripts to <name>pre; without a <name>pre script, run <name>
        if (!exists && (scriptType == ScriptType.Field || scriptType == ScriptType.FirstEnterField)
                && name.length() > 3 && name.endsWith("pre")) {
            return getScriptDir(name.substring(0, name.length() - 3), scriptType);
        }

        // If script doesn't exist in normal scripts directory either. return the default script
        if (!exists) {
            log.debug(String.format("[Error] Could not find script %s/%s", scriptType.getDir().toLowerCase(), name));
            net.swordie.ms.ext.ServerHooks.onScriptMissing(getChr(), scriptType, name);
            if (getChr() != null) {
                getChr().debugMessage(String.format("[Script] Could not find script %s/%s", scriptType.getDir().toLowerCase(), name));
            }
            scriptCache.put(dir, null);
            dir = String.format("%s/%s/%s%s", ServerConstants.SCRIPT_DIR,
                    scriptType.getDir().toLowerCase(), DEFAULT_SCRIPT, SCRIPT_ENGINE_EXTENSION);
        }
        return dir;
    }

    // the script type whose code runs on this thread (scripts run on pooled threads, one script at a time per thread)
    private static final ThreadLocal<ScriptType> RUNNING_SCRIPT_TYPE = new ThreadLocal<>();
    // the file of the script whose code runs on this thread
    private static final ThreadLocal<String> RUNNING_SCRIPT_DIR = new ThreadLocal<>();

    private void startScript(String name, ScriptType scriptType) {
        ScriptType outerType = RUNNING_SCRIPT_TYPE.get();
        String outerDir = RUNNING_SCRIPT_DIR.get();
        RUNNING_SCRIPT_TYPE.set(scriptType);
        try {
            runScript(name, scriptType);
        } finally {
            RUNNING_SCRIPT_TYPE.set(outerType);
            RUNNING_SCRIPT_DIR.set(outerDir);
        }
    }

    private void runScript(String name, ScriptType scriptType) {
        String dir = getScriptDir(name, scriptType); // Grab directory. if tespia, first look in tespia folder. otherwise normal folder. otherwise default script
        ScriptInfo si = getScriptInfoByType(scriptType);
        if (si == null) {
            return;
        }

        getScriptInfoByType(scriptType).setFileDir(dir);
        RUNNING_SCRIPT_DIR.set(dir);
        StringBuilder script = new StringBuilder();
        ScriptEngine se = scriptEngine;
        Bindings bindings = getBindingsByType(scriptType);
        si.setInvocable((Invocable) se);
        if (!scriptCache.containsKey(dir)) {
            try {
                fileReadLock.lock();
                script.append(Util.readFile(dir, Charset.defaultCharset()));
            } catch (IOException e) {
                log.error("ScriptManagerImpl.runScript", e);
                lockInGameUI(false); // so players don't get stuck if a script fails
            } finally {
                fileReadLock.unlock();
            }
        }
        Object[] hookTokens = null;
        try {
            hookTokens = net.swordie.ms.ext.ServerHooks.onScriptStart(getChr());
            var cs = scriptCache.getOrDefault(dir, null);
            if (cs == null) {
                long compileStart = PerfLog.start();
                cs = ((Compilable) se).compile(script.toString());
                PerfLog.end("script", "compile", compileStart);
                scriptCache.put(dir, cs);
            }
            // CPU time of this thread, not wall time: scripts wait for dialog answers and sleep through scenes
            boolean timed = PerfLog.start() != 0;
            var threads = ManagementFactory.getThreadMXBean();
            long cpuStart = timed ? threads.getCurrentThreadCpuTime() : 0;
            cs.eval(bindings);
            if (timed) {
                // end() records now minus start, so a start of (now - cpu) records the CPU time
                long cpu = threads.getCurrentThreadCpuTime() - cpuStart;
                PerfLog.end("script", scriptType.name() + " " + name, System.nanoTime() - cpu);
            }
        } catch (ScriptException e) {
            if (e.getMessage() != null && !e.getMessage().contains(INTENDED_NPE_MSG)) {
                net.swordie.ms.ext.ServerHooks.onScriptError(getChr(), name, scriptType, e.getMessage());
            }
            if (uiLocked) {
                // the script stopped (escape pressed, or an error) while it had the UI locked: unlock it, the rest
                // of the scene won't run
                lockInGameUI(false);
            }
            if (e.getMessage() != null && !e.getMessage().contains(INTENDED_NPE_MSG) && Server.DEBUG) {
                log.error(String.format("Unable to compile script %s!", name));
                log.error("ScriptManagerImpl.runScript", e);
                if (getChr() != null) {
                    getChr().debugMessage(String.format("Unable to compile script %s!", name));
                    getChr().debugMessage(e.getMessage());
                }
                lockInGameUI(false); // so players don't get stuck if a script fails
            }
        } finally {
            net.swordie.ms.ext.ServerHooks.onScriptEnd(hookTokens);
            ScriptAdvancement adv = lastAdvancement;
            if (adv != null && adv.thread == Thread.currentThread()) {
                // the script that advanced the job is over: a later script on this (pooled) thread may give SP again
                lastAdvancement = null;
            }
            if (si.isActive() && name.equals(si.getScriptName()) &&
                    ((scriptType != ScriptType.Field && scriptType != ScriptType.FirstEnterField)
                            || (getChr() != null && getChr().getFieldID() == si.getParentID()))) {
                // gracefully stop script if it's still active with the same script info (scriptName, or scriptName +
                // current chr fieldID == fieldscript's fieldID if scriptType == Field).
                // This makes it so field scripts won't cancel new field scripts when having a warp() in them.
                stop(scriptType);
            }
            FieldTransferInfo fti = getFieldTransferInfo();
            if (!fti.isInit()) {
                if (fti.isField()) {
                    fti.warp(field);
                } else {
                    fti.warp(getChr());
                }
            }
        }
    }

    public void stop(ScriptType scriptType) {
        setSpeakerID(0);
        // the dialog state belongs to the last started script: a portal script ending after its warp started a
        // field scene must leave that scene's state alone
        boolean ownsDialog = getLastActiveScriptType() == scriptType || !isActive(getLastActiveScriptType());
        if (getLastActiveScriptType() == scriptType) {
            setLastActiveScriptType(ScriptType.None);
        }
        ScriptInfo si = getScriptInfoByType(scriptType);
        if (si != null) {
            si.reset();
        }
        if (ownsDialog) {
            getNpcScriptInfo().reset();
            getMemory().clear();
        }
        getScripts().remove(scriptType);
        if (getChr() != null) {
            getChr().dispose();
        }
    }

    public void handleAction(NpcMessageType lastType, byte response, int answer) {
        handleAction(getLastActiveScriptType(), lastType, response, answer, null);
    }

    public void handleAction(NpcMessageType lastType, byte response, String text) {
        handleAction(getLastActiveScriptType(), lastType, response, 0, text);
    }

    public void handleAction(ScriptType scriptType, NpcMessageType lastType, byte response, int answer, String text) {
        switch (response) {
            case -1:
            case 5:
                stop(scriptType);
                break;
            default:
                ScriptMemory sm = getMemory();
                if (lastType.isPrevPossible() && response == 0) {
                    // back button pressed
                    NpcScriptInfo prev = sm.getPreviousScriptInfo();
                    getChr().write(ScriptMan.scriptMessage(prev, prev.getMessageType()));
                } else {
                    if (getMemory().isInMemory()) {
                        NpcScriptInfo next = sm.getNextScriptInfo();
                        getChr().write(ScriptMan.scriptMessage(next, next.getMessageType()));
                    } else {
                        ScriptInfo si = getScriptInfoByType(scriptType);
                        if (isActive(scriptType)) {
                            switch (lastType.getResponseType()) {
                                case Response:
                                    si.addResponse((int) response);
                                    break;
                                case Answer:
                                    si.addResponse(answer);
                                    break;
                                case Text:
                                    si.addResponse(text);
                                    break;
                            }
                        }
                    }
                }
        }
    }

    public boolean isActive(ScriptType scriptType) {
        return scriptType != null && getScriptInfoByType(scriptType) != null && getScriptInfoByType(scriptType).isActive();
    }

    public boolean hasClashingScriptTypeActive(ScriptType scriptType) {
        return Arrays.stream(scriptType.getClashingScriptTypes()).anyMatch(this::isActive);
    }

    public NpcScriptInfo getNpcScriptInfo() {
        return npcScriptInfo;
    }

    public Map<ScriptType, ScriptInfo> getScripts() {
        return scripts;
    }

    public int getParentID() {
        int res = 0;
        for (ScriptType type : ScriptType.values()) {
            if (getScriptInfoByType(type) != null) {
                res = getScriptInfoByType(type).getParentID();
            }
        }
        return res;
    }

    public boolean isField() {
        return isField;
    }

    public Field getField() {
        return isField ? field : getChr().getField();
    }

    public ScriptType getLastActiveScriptType() {
        return lastActiveScriptType;
    }

    public void setLastActiveScriptType(ScriptType lastActiveScriptType) {
        this.lastActiveScriptType = lastActiveScriptType;
    }

    public FieldTransferInfo getFieldTransferInfo() {
        return fieldTransferInfo;
    }

    // Start of the sends/asks -----------------------------------------------------------------------------------------

    @Override
    public int sendSay(String text) {
        if (getLastActiveScriptType() == ScriptType.None) {
            return 0;
        }
        return sendGeneralSay(text, Say);
    }

    /**
     * Helper function that ensures that selections have the appropriate type (AskMenu).
     *
     * @param text
     * @param nmt
     */
    private int sendGeneralSay(String text, NpcMessageType nmt) throws NullPointerException {
        if (sceneSkipping && (nmt == Say || nmt == SayNext || nmt == SayPrev)
                && !listPattern.matcher(text.replaceAll("[\r\n]", "")).matches()) {
            return 1;
        }
        var npcScriptInfo = getNpcScriptInfo();
        npcScriptInfo.setText(text);
        String checkText = text.replaceAll("[\r\n]", "");
        if (listPattern.matcher(checkText).matches()) {
            nmt = AskMenu;
        }
        npcScriptInfo.setMessageType(nmt);
        getChr().write(ScriptMan.scriptMessage(npcScriptInfo, nmt));
        getMemory().addMemoryInfo(npcScriptInfo);
        Object response = null;
        var lastActiveScriptType = getLastActiveScriptType();
        if (isActive(lastActiveScriptType)) {
            response = getScriptInfoByType(lastActiveScriptType).awaitResponse();
        }
        if (response == null) {
            throw new NullPointerException(INTENDED_NPE_MSG);
        }
        return (int) response;
    }

    @Override
    public int sendNext(String text) {
        return sendGeneralSay(text, SayNext);
    }

    @Override
    public int sendPrev(String text) {
        return sendGeneralSay(text, SayPrev);
    }

    @Override
    public int sendSayOkay(String text) {
        return sendGeneralSay(text, SayOk);
    }

    @Override
    public int sendSayImage(String image) {
        return sendSayImage(new String[]{image});
    }

    @Override
    public int sendSayImage(String[] images) {
        getNpcScriptInfo().setImages(images);
        getNpcScriptInfo().setMessageType(SayImage);
        return sendGeneralSay("", SayImage);
    }

    @Override
    public boolean sendAskYesNo(String text) {
        return sendGeneralSay(text, AskYesNo) != 0;
    }

    @Override
    public boolean sendAskAccept(String text) {
        return sendGeneralSay(text, AskAccept) != 0;
    }

    public boolean sendAskAccept2(String text) {
        return sendGeneralSay(text, AskAccept2) != 0;
    }

    @Override
    public String sendAskText(String text, String defaultText, short minLength, short maxLength) throws NullPointerException {
        var npcScriptInfo = getNpcScriptInfo();
        npcScriptInfo.setMin(minLength);
        npcScriptInfo.setMax(maxLength);
        npcScriptInfo.setDefaultText(defaultText);
        npcScriptInfo.setText(text);
        npcScriptInfo.setMessageType(AskText);
        getChr().write(ScriptMan.scriptMessage(npcScriptInfo, AskText));
        getMemory().addMemoryInfo(npcScriptInfo);
        Object response = null;
        var lastActiveScriptType = getLastActiveScriptType();
        if (isActive(lastActiveScriptType)) {
            response = getScriptInfoByType(lastActiveScriptType).awaitResponse();
        }
        if (response == null) {
            throw new NullPointerException(INTENDED_NPE_MSG);
        }
        return (String) response;
    }

    @Override
    public int sendAskNumber(String text, int defaultNum, int min, int max) {
        getNpcScriptInfo().setDefaultNumber(defaultNum);
        getNpcScriptInfo().setMin(min);
        getNpcScriptInfo().setMax(max);
        var num = sendGeneralSay(text, AskNumber);

        if (num < min || num > max) {
            throw new IllegalArgumentException(String.format("%d is outside of bounds (%d, %d)", num, min, max));
        }

        return num;
    }

    @Override
    public int sendInitialQuiz(byte type, String title, String problem, String hint, int min, int max, int time) {
        NpcScriptInfo nsi = getNpcScriptInfo();
        nsi.setType(type);
        if (type != 1) {
            nsi.setTitle(title);
            nsi.setProblemText(problem);
            nsi.setHintText(hint);
            nsi.setMin(min);
            nsi.setMax(max);
            nsi.setTime(time);
        }
        return sendGeneralSay(title, InitialQuiz);
    }

    @Override
    public int sendInitialSpeedQuiz(byte type, int quizType, int answer, int correctAnswers, int remaining, int time) {
        NpcScriptInfo nsi = getNpcScriptInfo();
        nsi.setType(type);
        if (type != 1) {
            nsi.setQuizType(quizType);
            nsi.setAnswer(answer);
            nsi.setCorrectAnswers(correctAnswers);
            nsi.setRemaining(remaining);
            nsi.setTime(time);
        }
        return sendGeneralSay("", InitialSpeedQuiz);
    }

    @Override
    public int sendICQuiz(byte type, String text, String hintText, int time) {
        getNpcScriptInfo().setType(type);
        getNpcScriptInfo().setHintText(hintText);
        getNpcScriptInfo().setTime(time);
        return sendGeneralSay(text, ICQuiz);
    }

    /**
     * The client picks AskAvatar's preview mode from the coupon id (the first int). With none it takes the mode from
     * the first option: style modes redraw every option in the current colour, and skin 0 gets no preview. So colour
     * lists of one style and skin lists get a dye, lens or skin coupon; style lists keep 0.
     */
    private static int previewCoupon(List<Integer> options) {
        if (options.stream().allMatch(o -> o < 100)) {
            return HENESYS_SKIN_COUPON;
        }
        if (options.size() < 2) {
            return 0;
        }
        if (options.stream().allMatch(ItemConstants::isHair)
                && options.stream().map(o -> o / 10).distinct().count() == 1) {
            return HENESYS_HAIR_COLOR_COUPON_REG;
        }
        if (options.stream().allMatch(ItemConstants::isFace)
                && options.stream().map(o -> o - o / 100 % 10 * 100).distinct().count() == 1) {
            return ONE_TIME_EYE_COLOR;
        }
        return 0;
    }

    /** Hair dye coupons (not the mix-dye ones): the client's dye preview takes colour indices 0-7, not hair ids */
    private static boolean isDyeCoupon(int coupon) {
        return coupon / 1000 == 5151 && (coupon < HALF_AND_HALF_COLORBLEND_HAIR_DYE_COUPON || coupon > HALF_AND_HALF_COLORBLEND_HAIR_DYE_COUPON_2);
    }

    @Override
    public int sendAskAvatar(String text, boolean angelicBuster, boolean zeroBeta, int... options) {
        List<Integer> safeOptions = new ArrayList<>();
        for (var opt : options) {
            boolean exists = opt < 100 ? ItemConstants.isSkin(opt)
                    : (ItemConstants.isHair(opt) || ItemConstants.isFace(opt)) && ItemData.getEquipInfoById(opt) != null;
            if (exists && !safeOptions.contains(opt) && safeOptions.size() < 255) { // the client's count is a byte
                safeOptions.add(opt);
            }
        }

        if (safeOptions.size() > 0) {
            getNpcScriptInfo().setAngelicBuster(angelicBuster || BeautyModule.isDressedUp(getChr()));
            getNpcScriptInfo().setZeroBeta(zeroBeta);
            int[] shown = safeOptions.stream().mapToInt(i -> i).toArray();
            int coupon = getNpcScriptInfo().getIndex();
            int preview = coupon != 0 ? coupon : previewCoupon(safeOptions);
            if (isDyeCoupon(preview) && safeOptions.stream().allMatch(ItemConstants::isHair)) {
                if (safeOptions.stream().allMatch(o -> o % 10 < 8)) {
                    shown = safeOptions.stream().mapToInt(o -> o % 10).toArray();
                } else {
                    preview = 0; // colours 8/9 can't be shown that way
                }
            }
            getNpcScriptInfo().setOptions(shown);
            getNpcScriptInfo().setIndex(preview);
            int safeIdx;
            try {
                safeIdx = sendGeneralSay(text, AskAvatar);
            } finally {
                getNpcScriptInfo().setIndex(coupon);
            }
            if (safeIdx < 0 || safeIdx >= safeOptions.size()) {
                return FieldConstants.HENESYS_FIELD_ID; // cancelled or invalid preview response; do not change the look
            }
            return safeOptions.get(safeIdx);
        } else {
            sendGeneralSay("We don't have any options here, sorry.", SayOk);
            return FieldConstants.HENESYS_FIELD_ID; // makes setCharacterLook not do anything
        }
    }

    // ---- Android Beautician (npc/1012123) ----

    /**
     * @return the player's current Android, or null if they don't have one out
     */
    public net.swordie.ms.life.android.Android getAndroid() {
        return getChr().getAndroid();
    }

    /**
     * @return the hairs (full ids) the player's Android can have, from its own Etc/Android options
     */
    public int[] getAndroidHairs() {
        var android = getChr().getAndroid();
        if (android == null || android.getAndroidInfo() == null) {
            return new int[0];
        }
        return android.getAndroidInfo().getHairs().stream().mapToInt(i -> i).toArray();
    }

    /**
     * @return the faces (full ids) the player's Android can have, from its own Etc/Android options
     */
    public int[] getAndroidFaces() {
        var android = getChr().getAndroid();
        if (android == null || android.getAndroidInfo() == null) {
            return new int[0];
        }
        return android.getAndroidInfo().getFaces().stream().mapToInt(i -> i).toArray();
    }

    /**
     * @return the skins (0-99) the player's Android can have, from its own Etc/Android options
     */
    public int[] getAndroidSkins() {
        var android = getChr().getAndroid();
        if (android == null || android.getAndroidInfo() == null) {
            return new int[0];
        }
        return android.getAndroidInfo().getSkins().stream().mapToInt(i -> i % 1000).distinct().toArray();
    }

    /**
     * Shows the Android style preview (like sendAskAvatar, but on the player's Android).
     *
     * @return the chosen option, or 100000000 (does nothing in changeAndroidLook) if there was nothing to show
     */
    public int sendAskAndroid(String text, int... options) {
        List<Integer> safeOptions = new ArrayList<>();
        for (var opt : options) {
            if (opt < 100 || ItemData.getItemDeepCopy(opt) != null) {
                safeOptions.add(opt);
            }
        }
        if (safeOptions.size() > 0) {
            var android = getChr().getAndroid();
            getNpcScriptInfo().setAndroidName(android != null ? android.getName() : "");
            getNpcScriptInfo().setAndroids(safeOptions.stream().mapToInt(i -> i).toArray());
            var idx = sendGeneralSay(text, AskAndroid);
            if (idx < 0 || idx >= safeOptions.size()) {
                return FieldConstants.HENESYS_FIELD_ID;
            }
            return safeOptions.get(idx);
        } else {
            sendGeneralSay("We don't have any options for your Android here, sorry.", SayOk);
            return FieldConstants.HENESYS_FIELD_ID;
        }
    }

    /**
     * Changes the player's Android skin (0-99), hair or face (full ids), saves it and shows it to the map.
     */
    public void changeAndroidLook(int look) {
        var android = getChr().getAndroid();
        if (android == null || look == 100000000) { // hardcode-ok
            return;
        }
        android.changeLook(look);
    }

    public int sendAskStoryUI(String text, String key, byte type) {
        NpcScriptInfo nsi = getNpcScriptInfo();
        nsi.setText(text);
        nsi.setHintText(key);
        nsi.setType(type);
        return sendGeneralSay(text, AskStoryUI);
    }

    public int sendAskCustomMixHair(String text, boolean zeroBeta) {
        NpcScriptInfo nsi = getNpcScriptInfo();
        nsi.setAngelicBuster(JobConstants.isAngelicBuster(chr.getJob()));
        nsi.setZeroBeta(zeroBeta);
        return sendGeneralSay(text, OnAskCustomMixHair);
    }

    public int sendAskSlideMenu(int dlgType) {
        getNpcScriptInfo().setDlgType(dlgType);
        return sendGeneralSay("", AskSlideMenu);
    }

    public int sendAskSelectMenu(int dlgType, int defaultSelect) {
        return sendAskSelectMenu(dlgType, defaultSelect, new String[]{});
    }

    public int sendAskSelectMenu(int dlgType, int defaultSelect, String[] text) {
        getNpcScriptInfo().setDlgType(dlgType);
        getNpcScriptInfo().setDefaultSelect(defaultSelect);
        getNpcScriptInfo().setSelectText(text);
        return sendGeneralSay("", AskSelectMenu);
    }

    // Start of param methods ------------------------------------------------------------------------------------------

    public void setParam(int param) {
        getNpcScriptInfo().setParam((short) param);
    }

    public void setColor(int color) {
        getNpcScriptInfo().setColor((byte) color);
    }

    public void resetParam() {
        getNpcScriptInfo().resetParam();
    }

    public void removeEscapeButton() {
        getNpcScriptInfo().addParam(NpcScriptInfo.Param.NotCancellable);
    }

    public void addEscapeButton() {
        if (getNpcScriptInfo().hasParam(NpcScriptInfo.Param.NotCancellable)) {
            getNpcScriptInfo().removeParam(NpcScriptInfo.Param.NotCancellable);
        }
    }

    public void flipSpeaker() {
        getNpcScriptInfo().addParam(NpcScriptInfo.Param.FlipSpeaker);
    }

    public void flipDialogue() {
        getNpcScriptInfo().addParam(NpcScriptInfo.Param.OverrideSpeakerID);
    }

    public void setNpcBoxChat(int npcID) {
        setSpeakerID(npcID);
        getNpcScriptInfo().setColor((byte) 1);
        getNpcScriptInfo().addParam(NpcScriptInfo.Param.BoxChatOverrideSpeakerNoEndChat);
    }

    public void setPlayerBoxChat() {
        setBoxChat();
        flipBoxChat();
        flipBoxChatPlayerAsSpeaker();
    }

    public boolean hasMobById(int templateID) {
        return getField().getLifeByTemplateId(templateID) != null;
    }

    public int getActivatedDamageSkin() {
        var skin = getChr().getActiveDamageSkin();
        return skin == null ? 0 : skin.getItemID();
    }

    public boolean hasDamageSkin(int itemID) {
        return getChr().getAccount().getDamageSkinByItemID(itemID) != null;
    }

    public net.swordie.ms.world.field.instance.Instance getInstance() {
        Char c = getChr();
        return c.getParty() != null ? c.getParty().getInstance() : c.getInstance();
    }

    public void flipDialoguePlayerAsSpeaker() {
        getNpcScriptInfo().addParam(NpcScriptInfo.Param.PlayerAsSpeakerFlip);
    }

    public void setPlayerAsSpeaker() {
        getNpcScriptInfo().addParam(NpcScriptInfo.Param.PlayerAsSpeaker);
    }

    public void setBoxChat() {
        setBoxChat(true);
    }

    public void setBoxChat(boolean color) { // true = Standard BoxChat  |  false = Zero BoxChat
        getNpcScriptInfo().setColor((byte) (color ? 1 : 0));
        getNpcScriptInfo().addParam(NpcScriptInfo.Param.BoxChat);
    }

    public void flipBoxChat() {
        getNpcScriptInfo().addParam(NpcScriptInfo.Param.FlipBoxChat);
    }

    public void boxChatPlayerAsSpeaker() {
        getNpcScriptInfo().addParam(NpcScriptInfo.Param.BoxChatAsPlayer);
    }

    public void flipBoxChatPlayerAsSpeaker() {
        getNpcScriptInfo().addParam(NpcScriptInfo.Param.FlipBoxChatAsPlayer);
    }

    public void flipBoxChatPlayerNoEscape() {
        getNpcScriptInfo().addParam(NpcScriptInfo.Param.FlipBoxChatAsPlayerNoEscape);
    }

    public void sendOK(String text, int npcTemplateID) {
        chr.write(UserLocal.setUtilDlg(text, npcTemplateID));
    }

    // Start helper methods for scripts --------------------------------------------------------------------------------

    @Override
    public void dispose() {
        dispose(true);
    }

    public void dispose(boolean stop) {
        if (getChr() != null) {
            getChr().setTalkingToNpc(false);
            if (getChr().isSkipPromptProtected()) {
                // the Arcane River skip prompt was closed (answered, Esc, ...): its protection ends with it
                net.swordie.ms.client.character.ArcaneRiverSkipModule.endPrompt(getChr());
            }
        }
        ScriptType caller = RUNNING_SCRIPT_TYPE.get();
        boolean sceneRunning = getLastActiveScriptType() == ScriptType.Field && isActive(ScriptType.Field)
                && caller != null && caller != ScriptType.Field;
        if (!sceneRunning) {
            getNpcScriptInfo().reset();
            getMemory().clear();
        }
        stop(ScriptType.Npc);
        stop(ScriptType.Portal);
        stop(ScriptType.Item);
        stop(ScriptType.Quest);
        stop(ScriptType.Reactor);
        // a field script that warped away: the field script running now is the next map's, not the caller's
        ScriptInfo fieldSi = getScriptInfoByType(ScriptType.Field);
        boolean nextMapScript = caller == ScriptType.Field && fieldSi != null && fieldSi.getFileDir() != null
                && !fieldSi.getFileDir().equals(RUNNING_SCRIPT_DIR.get());
        if (getLastActiveScriptType() == ScriptType.Field && !sceneRunning && !nextMapScript) {
            // a portal or npc script whose warp started a field script (a boss intro) must not stop that one
            // only fields are able to stop themselves, otherwise things like npcs would stop field scripts
            // like magnus leave script would make the orbs disappear if you don't actually leave
            stop(ScriptType.Field);
        }
        if (stop) {
            throw new NullPointerException(INTENDED_NPE_MSG); // makes the underlying script stop
        }
        setCurNodeEventEnd(false);
    }

    public void dispose(ScriptType scriptType) {
        getMemory().clear();
        stop(scriptType);
    }

    public Position getPosition(int objId) {
        Life life = getField().getLifeByObjectID(objId);
        if (life == null) {
            throw new NullPointerException(INTENDED_NPE_MSG); // the life is gone: stop the script quietly
        }
        return life.getPosition();
    }


    // Character Stat-related methods ----------------------------------------------------------------------------------

    @Override
    public void setJob(short jobID) {
        advanceJob(jobID);
    }

    /**
     * Changes the job like a job advancement does ({@link Char#advanceJob(int)}): a higher job gets the SP its
     * advancement gives on live servers, so scripts don't add SP for job advancements themselves.
     *
     * @return whether the job changed
     */
    @Override
    public boolean advanceJob(int jobID) {
        Char chr = getChr();
        int sp = chr.advanceJob(jobID);
        if (sp < 0) {
            return false;
        }
        Map<Stat, Object> stats = new HashMap<>();
        stats.put(Stat.job, chr.getJob());
        chr.write(WvsContext.statChanged(stats, chr.getSubJob()));
        if (sp > 0) {
            lastAdvancement = new ScriptAdvancement(chr.getJob(), Thread.currentThread(), System.currentTimeMillis());
        }
        return true;
    }

    public void incInsightEXP(short amount) {
        chr.addTraitExp(Stat.insightEXP, amount);
    }

    public void incWillEXP(short amount) {
        chr.addTraitExp(Stat.willEXP, amount);
    }

    public void incCraftEXP(short amount) {
        chr.addTraitExp(Stat.craftEXP, amount);
    }

    public void incSenseEXP(short amount) {
        chr.addTraitExp(Stat.senseEXP, amount);
    }

    public void incCharmEXP(short amount) {
        chr.addTraitExp(Stat.charmEXP, amount);
    }

    /**
     * The last job advancement a script did that gave SP. Older scripts add the advancement SP themselves right after
     * changing the job; while the same script (thread) is still on that job, such addSP calls are ignored so the SP
     * isn't given twice.
     */
    private static final class ScriptAdvancement {
        private final int job;
        private final Thread thread;
        private final long time;

        private ScriptAdvancement(int job, Thread thread, long time) {
            this.job = job;
            this.thread = thread;
            this.time = time;
        }
    }

    private volatile ScriptAdvancement lastAdvancement;

    private boolean isAdvancementSp() {
        ScriptAdvancement adv = lastAdvancement;
        return adv != null && adv.job == getChr().getJob() && adv.thread == Thread.currentThread()
                && System.currentTimeMillis() - adv.time < 10 * 60 * 1000;
    }

    public void addSP(int amount) {
        addSP(amount, false);
    }

    @Override
    public void addSP(int amount, boolean jobAdv) {
        if (amount > 0 && isAdvancementSp()) {
            log.warn(String.format("Ignored addSP(%d) right after the job advancement to %d (the advancement already " +
                    "gave its SP), char %d.", amount, getChr().getJob(), getChr().getId()));
            return;
        }
        byte jobLevel = (byte) JobConstants.getJobLevel(getChr().getJob());
        int currentSP = getChr().getAvatarData().getCharacterStat().getExtendSP().getSpByJobLevel(jobLevel);
        setSP(currentSP + amount);
        if (jobAdv) {
            getChr().write(WvsContext.message(MessagePacket.incSpMessage(getChr().getJob(), (byte) amount)));
        }
    }

    @Override
    public void setSP(int amount) {
        getChr().setSpToCurrentJob(amount);
        Map<Stat, Object> stats = new HashMap<>();
        stats.put(Stat.sp, getChr().getAvatarData().getCharacterStat().getExtendSP());
        getChr().write(WvsContext.statChanged(stats));
    }

    @Override
    public void addAP(int amount) {
        int currentAP = getChr().getAvatarData().getCharacterStat().getAp();
        setAP(currentAP + amount);
    }

    @Override
    public void setAP(int amount) {
        getChr().setStat(Stat.ap, (short) amount);
        Map<Stat, Object> stats = new HashMap<>();
        stats.put(Stat.ap, (short) amount);
        getChr().write(WvsContext.statChanged(stats));
    }

    @Override
    public void setSTR(short amount) {
        getChr().setStat(Stat.str, amount);
        Map<Stat, Object> stats = new HashMap<>();
        stats.put(Stat.str, amount);
        getChr().write(WvsContext.statChanged(stats));
    }

    @Override
    public void setINT(short amount) {
        getChr().setStat(Stat.inte, amount);
        Map<Stat, Object> stats = new HashMap<>();
        stats.put(Stat.inte, amount);
        getChr().write(WvsContext.statChanged(stats));
    }

    @Override
    public void setDEX(short amount) {
        getChr().setStat(Stat.dex, amount);
        Map<Stat, Object> stats = new HashMap<>();
        stats.put(Stat.dex, amount);
        getChr().write(WvsContext.statChanged(stats));
    }

    @Override
    public void setLUK(short amount) {
        getChr().setStat(Stat.luk, amount);
        Map<Stat, Object> stats = new HashMap<>();
        stats.put(Stat.luk, amount);
        getChr().write(WvsContext.statChanged(stats));
    }

    public void addMaxHP(int amount) {
        getChr().addStatAndSendPacket(Stat.mhp, amount);
    }

    @Override
    public void setMaxHP(int amount) {
        getChr().setStat(Stat.mhp, amount);
        getChr().setStat(Stat.hp, amount);
        Map<Stat, Object> stats = new HashMap<>();
        stats.put(Stat.mhp, amount);
        stats.put(Stat.hp, amount);
        getChr().write(WvsContext.statChanged(stats));
    }

    public void addMaxMP(int amount) {
        getChr().addStatAndSendPacket(Stat.mmp, amount);
    }

    @Override
    public void setMaxMP(int amount) {
        getChr().setStat(Stat.mmp, amount);
        getChr().setStat(Stat.mp, amount);
        Map<Stat, Object> stats = new HashMap<>();
        stats.put(Stat.mmp, amount);
        stats.put(Stat.mp, amount);
        getChr().write(WvsContext.statChanged(stats));
    }

    /**
     * The official job advancement quests that advance the job themselves (Kain's "Second Malice", Adele's "Second
     * Sword's Calling", Evan's "Dragon Master 2nd Job Advancement", ...): if the character is on fromJob, advances it
     * to toJob (with the advancement's SP and AP, like jobAdvance) and shows the job advancement effect, then completes
     * the quest with its Quest.wz rewards (starting it first if needed).
     *
     * @return whether the character is (now) on toJob
     */
    public boolean questJobAdvance(int questId, int fromJob, int toJob) {
        return questJobAdvance(questId, fromJob, fromJob, toJob);
    }

    /** {@link #questJobAdvance(int, int, int)} for classes with two possible previous job ids (Evan's old ids). */
    public boolean questJobAdvance(int questId, int fromJob, int fromJob2, int toJob) {
        Char chr = getChr();
        int job = chr.getJob();
        if (job != fromJob && job != fromJob2 && job != toJob) {
            return false;
        }
        if (job != toJob) {
            jobAdvance((short) toJob);
            chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.changeJobEffect(chr)));
            chr.write(UserPacket.effect(Effect.changeJobEffect(chr)));
        }
        QuestManager qm = chr.getQuestManager();
        if (!qm.hasQuestCompleted(questId)) {
            if (!qm.hasQuestInProgress(questId)) {
                startQuestNoCheck(questId);
            }
            completeQuestNoCheck(questId);
        }
        return chr.getJob() == toJob;
    }

    @Override
    public void jobAdvance(short jobID) {
        // gives the advancement's SP. GMS: no AP at the 1st or 2nd job; 3rd/4th keep the +5 (unverified)
        if (advanceJob(jobID) && net.swordie.ms.constants.JobConstants.getAdvancementTier(jobID) >= 3) {
            addAP(5);
            net.swordie.ms.client.character.AutoAbilityModule.onLevelUp(getChr()); // with the toggle on, spent at once
        }
    }

    @Override
    public void giveExp(long expGiven) {
        getChr().addExp(expGiven);
    }

    /** Mu Lung Dojo Unity Training Center (925080000): EXP every 5 seconds, by level (maplestorywiki.net, Lv. 105-219+). */
    private static final int[] UNITY_TRAINING_EXP = {3190, 3307, 3425, 3520, 3640, 4518, 4668, 4785, 4938, 5092, 5247,
            5405, 5565, 5690, 5850, 6013, 6180, 6347, 6517, 6687, 6857, 7087, 7263, 7613, 7968, 8327, 8687, 9053, 9475,
            9852, 10230, 10615, 11005, 11393, 11852, 12253, 12657, 13065, 13545, 13963, 14385, 14880, 15310, 15742,
            16253, 16698, 17143, 18018, 18823, 19720, 20542, 21465, 22302, 23247, 24103, 25068, 25940, 26930, 27932,
            28830, 29850, 30763, 31807, 32862, 33800, 34878, 35965, 37063, 38038, 39158, 40288, 41432, 42585, 43598,
            44773, 45957, 48000, 50075, 52168, 54282, 56425, 58582, 60768, 62972, 65207, 67452, 69727, 72018, 74333,
            76670, 79030, 81413, 83812, 86475, 88928, 91397, 93878, 96390, 99190, 101747, 104317, 106917, 109813,
            112457, 115113, 118097, 120797, 123832, 126575, 129343, 132447, 135258, 138418, 144473, 147367};
    public static final int UNITY_TRAINING_CENTER = 925080000;

    public void giveUnityTrainingExp() {
        Char c = getChr();
        if (c == null || c.getField() == null || c.getField().getId() != UNITY_TRAINING_CENTER || c.getLevel() < 105) {
            return;
        }
        int i = Math.min(UNITY_TRAINING_EXP.length - 1, c.getLevel() - 105);
        c.addExpNoMsg(UNITY_TRAINING_EXP[i]);
    }

    @Override
    public void giveExpNoMsg(long expGiven) {
        getChr().addExpNoMsg(expGiven);
    }

    public void giveExpNoAffectedByExpRate(long expGiven) {
        getChr().addExp(expGiven);
    }

    @Override
    public void changeCharacterLook(int look) {
        if (BeautyModule.isDressedUp(getChr()) && BeautyModule.changeDressUpLook(getChr(), look)) {
            return; // Angelic Buster in her dress-up look: salons style that look
        }
        AvatarLook al = getChr().getAvatarData().getAvatarLook();
        boolean isBeta = getChr().isZeroBeta();
        if (isBeta) {
            al = getChr().getAvatarData().getZeroAvatarLook();
        }
        if (look < 100) { // skin
            al.setSkin(look);
            if (!isBeta) {
                getChr().setStatAndSendPacket(Stat.skin, look);
            } else {
                getChr().getZeroInfo().setSubSkin(look);
            }
        } else if (ItemConstants.isFace(look)) {
            al.setFace(look);

            if (!isBeta) {
                getChr().setStatAndSendPacket(Stat.face, look);
            } else {
                getChr().getZeroInfo().setSubFace(al.getEncodedFace());
            }
        } else if (ItemConstants.isHair(look)) {
            boolean newStyle = al.getHair() / 10 != look / 10; // the last digit is the colour
            al.setHair(look);
            if (!isBeta) {
                getChr().setStatAndSendPacket(Stat.hair, look);
            } else {
                getChr().getZeroInfo().setSubHair(al.getEncodedHair());
            }
            if (newStyle) {
                countHairStyleChange();
            }
        } else {
            log.error(String.format("Tried changing a look with invalid id (%d)", look));
        }
        if (isBeta) {
            getChr().updateZeroInfo();
        } else {
            getChr().broadcastRemoteAvatarModified();
        }
    }

    /** "Dynamic Hair" (medal): hairstyle changes, not colour changes, counted on its own record while it's held. */
    private void countHairStyleChange() {
        final int dynamicHair = 29020;
        Quest quest = getChr().getQuestManager().getQuestById(dynamicHair);
        if (quest == null || !hasQuest(dynamicHair)) {
            return;
        }
        String v = quest.getQRValue();
        int changes = Util.isNumber(v) ? Integer.parseInt(v) : 0;
        quest.setQrValue(String.valueOf(changes + 1));
        updateQRValue(dynamicHair, true);
    }

    public void giveSkill(int skillId) {
        giveSkill(skillId, 1);
    }

    public void giveSkill(int skillId, int slv) {
        giveSkill(skillId, slv, slv);
    }

    @Override
    public void giveSkill(int skillId, int slv, int maxLvl) {
        getChr().addSkill(skillId, slv, maxLvl);
    }

    public void removeBuff(CharacterTemporaryStat cts) {
        TemporaryStatManager tsm = getChr().getTemporaryStatManager();
        tsm.removeStat(cts);
    }

    public void removeAllBuffs() {
        TemporaryStatManager tsm = getChr().getTemporaryStatManager();
        tsm.removeAllStats();
    }

    public void removeSkill(int skillId) {
        if (getChr() != null) {
            getChr().removeSkillAndSendPacket(skillId);
        }
    }

    public int getSkillByItem() {
        return getSkillByItem(getParentID());
    }

    public int getSkillByItem(int itemId) {
        ItemInfo itemInfo = ItemData.getItemInfoByID(itemId);
        return itemInfo.getSkillId();
    }

    public boolean hasSkill(int skillId) {
        return getChr().hasSkill(skillId);
    }

    public void heal() {
        getChr().heal(getChr().getMaxHP());
        getChr().healMP(getChr().getMaxMP());
    }

    public void setLevel(int level) {
        getChr().setStatAndSendPacket(Stat.level, level);
        getChr().setStatAndSendPacket(Stat.exp, 0);
        getChr().getJobHandler().handleLevelUp();
        getField().broadcastPacket(UserRemote.effect(getChr().getId(), Effect.levelUpEffect()));
    }

    public void addLevel(int level) {
        int curLevel = getChr().getLevel();
        for (int i = curLevel + 1; i <= curLevel + level; i++) {
            getChr().setStat(Stat.level, i);
            Map<Stat, Object> stats = new HashMap<>();
            stats.put(Stat.level, i);
            stats.put(Stat.exp, (long) 0);
            getChr().write(WvsContext.statChanged(stats));
            getChr().getOrCreateJobHandler().handleLevelUp();
            getField().broadcastPacket(UserRemote.effect(getChr().getId(), Effect.levelUpEffect()));
        }
    }

    public void lockInGameUI(boolean lock) {
        lockInGameUI(lock, true);
    }

    /**
     * Whether a script last locked the UI (for unlocking it when the script stops halfway).
     */
    private volatile boolean uiLocked;

    /**
     * The first question of every class tutorial (replaced "Would you like to skip the tutorial?"): the play style,
     * asked once per character (PlayStyleModule). Afterwards it answers with the stored choice without asking.
     *
     * @param destinationMap where the tutorial's skip takes the character
     * @return true to skip the tutorial (Express)
     */
    public boolean askTutorialSkip(int destinationMap) {
        boolean skip = net.swordie.ms.client.character.PlayStyleModule.askInTutorial(this, getChr(), destinationMap);
        if (skip) {
            // the skip's completions go out with the CharacterData of its warp, like a character made past the story
            net.swordie.ms.client.character.quest.QuietQuests.holdUntilWarp(getChr());
        }
        return skip;
    }

    /** @return whether any script (NPC, field, quest, ...) is waiting for the player to answer a dialog */
    public boolean isWaitingForAnswer() {
        return getScripts().values().stream().anyMatch(si -> si != null && si.isWaiting());
    }

    /** @return whether a script has the UI locked right now (a cutscene or direction scene) */
    public boolean isUiLocked() {
        return uiLocked;
    }

    public void lockInGameUI(boolean lock, boolean blackFrame) {
        uiLocked = lock;
        if (!lock) {
            sceneSkipping = false;
        }
        if (getChr() != null) {
            if (lock && net.swordie.ms.ServerSettings.getBoolean("scene.skipButton", true)) {
                // as GMS: a locked scene offers the skip button
                getChr().write(UserLocal.setInGameDirectionModeWithSkip(blackFrame, "skip_" + getChr().getFieldID()));
            } else {
                getChr().write(UserLocal.setInGameDirectionMode(lock, blackFrame, false));
            }
        }
    }

    /** Set by the skip button: the rest of the scene runs without its delays and plain lines. */
    private volatile boolean sceneSkipping;

    public boolean isSceneSkipping() {
        return sceneSkipping;
    }

    /**
     * The scene's skip button (In SCENE_SKIP_REQUEST). Like GMS: direction event 37 and stand-alone mode off, then
     * the script fast-forwards to its end (delays and say/next lines return at once), so its warps and quest steps
     * still happen; the script's own unlock ends the scene.
     */
    public void skipScene() {
        if (!uiLocked || sceneSkipping || getChr() == null) {
            return;
        }
        sceneSkipping = true;
        getChr().write(UserLocal.inGameDirectionSkipped());
        getChr().write(UserLocal.setStandAloneMode(false));
        for (var si : getScripts().values()) {
            if (si != null && si.isWaiting()) {
                si.addResponse(1);
            }
        }
    }

    public void setInGameDirectionMode(boolean lockUI, boolean blackFrame, boolean forceMouseOver, boolean showUI) {
        if (getChr() != null) {
            getChr().write(UserLocal.setInGameDirectionMode(lockUI, blackFrame, forceMouseOver, showUI));
        }
    }

    /**
     * Clears the temporary skill bar. Like the reference, the argument is not used as a set index.
     */
    public void setTemporarySkillSet(int skillSet) {
        if (getChr() != null) {
            getChr().write(FieldPacket.setTemporarySkillSet(0));
        }
    }

    public void lockUI() {
        curNodeEventEnd(true);
        setTemporarySkillSet(0);
        lockInGameUI(true, false);
    }

    public void unlockUI() {
        curNodeEventEnd(false);
        setTemporarySkillSet(0);
        lockInGameUI(false, true);
    }

    public void curNodeEventEnd(boolean enable) {
        setCurNodeEventEnd(enable);
        getChr().write(FieldPacket.curNodeEventEnd(enable));
    }

    public void setCurNodeEventEnd(boolean curNodeEventEnd) {
        this.curNodeEventEnd = curNodeEventEnd;
    }

    /** Whether a script turned direction node events on (server-driven characters follow the next node then). */
    public boolean isCurNodeEventEnd() {
        return curNodeEventEnd;
    }

    public void progressMessageFont(int fontNameType, int fontSize, int fontColorType, int fadeOutDelay, String message) {
        ProgressMessageFontType type = ProgressMessageFontType.getByVal(fontNameType);
        ProgressMessageColourType colour = ProgressMessageColourType.getByVal(fontColorType);
        if (colour == null || type == null) {
            log.warn(String.format("Could not find fontType %d or ColourType %d", fontNameType, fontColorType));
            return;
        }
        progressMessageFont(type, fontSize, colour, fadeOutDelay, message);
    }

    public void progressMessageFont(ProgressMessageFontType fontType, int fontSize, ProgressMessageColourType colour, int fadeOutDelay, String msg) {
        getChr().write(UserPacket.progressMessageFont(fontType, fontSize, colour, fadeOutDelay, msg));
    }

    public void progressMessageFont(String msg) {
        progressMessageFont(3, 20, 20, 0, msg);
    }

    /** No-op, as in the reference. */
    public void setStandAloneMode(boolean enable) {
    }

    public void setDirectionMode(boolean show, int unk) {
        getChr().write(UserLocal.setDirectionMode(show, unk));
    }

    public void openUIWithOption(int uiID, int option) {
        getChr().write(FieldPacket.openUIWithOption(uiID, option, new int[0]));
    }

    public void openUIWithOption(UIType uiType, int option) {
        openUIWithOption(uiType.getVal(), option);
    }

    /** Spawns the NPC at the position for this character's direction scene; returns its object id (0 if the NPC is unknown). */
    public int sendNpcController(int npcId, int x, int y) {
        Npc npc = NpcData.getNpcDeepCopyById(npcId);
        if (npc == null) {
            return 0;
        }
        npc.setPosition(new Position(x, y));
        npc.setCy(y);
        npc.setRx0(x + 50);
        npc.setRx1(x - 50);
        Foothold fh = getField().findFootHoldBelow(new Position(x, y - 2));
        if (fh != null) {
            npc.setFh(fh.getId());
        }
        npc.setNotRespawnable(true);
        if (npc.getField() == null) {
            npc.setField(getField());
        }
        getField().spawnLife(npc, getChr());
        return npc.getObjectId();
    }

    /** True hands the NPC to this character, false removes it from the map. */
    public void sendNpcController(int objectId, boolean controller) {
        if (objectId == 0) {
            return;
        }
        Life life = getField().getLifeByObjectID(objectId);
        if (!(life instanceof Npc)) {
            return;
        }
        if (controller) {
            getChr().write(NpcPool.npcChangeController((Npc) life, true));
        } else {
            getField().removeLife(life);
        }
    }

    public void localEmotion(int emotion, int duration, boolean byItemOption) {
        getChr().write(UserLocal.emotion(emotion, duration, byItemOption));
    }


    // Field-related methods -------------------------------------------------------------------------------------------

    public void warpField(int fieldId, int portalId) {
        for (Char chrz : new HashSet<>(getField().getChars())) {
            chrz.warp(fieldId, portalId);
        }
    }

    public void warpField(int fieldId) {
        for (Char chrz : new HashSet<>(getField().getChars())) {
            chrz.warp(fieldId, 0);
        }
    }

    @Override
    public void warp(int fieldId) {
        warp(fieldId, 0);
    }

    public void warp(int fieldId, boolean executeAfterScript) {
        warp(fieldId, 0, executeAfterScript, false);
    }

    @Override
    public void warp(int fieldId, int portalId) {
        warp(fieldId, portalId, true, false);
    }

    public void warp(int fieldId, int portalId, boolean instanceField) {
        warp(fieldId, portalId, true, instanceField);
    }

    public void warp(int fieldId, int portalId, boolean executeAfterScript, boolean instanceField) {
        ScriptType type = getLastActiveScriptType();
        if ((type == ScriptType.Portal || type == ScriptType.Field) && fieldId != FieldConstants.UNKNOWN_FIELD_ID
                && FieldData.getFieldInfoById(fieldId) == null) {
            // a warp to a map the game no longer has stays shut instead of sending players to Henesys; a field script
            // (a boss death script run outside its boss map) just stops
            log.warn(type + " script warp to missing field " + fieldId);
            if (type == ScriptType.Portal) {
                getChr().chatMessage(SystemNotice, "This way is closed.");
            }
            return;
        }
        if (executeAfterScript) {
            FieldTransferInfo fti = getFieldTransferInfo();
            fti.setFieldId(fieldId);
            fti.setPortal(portalId);
            fti.setIsInstanceField(instanceField);
        } else {
            getChr().warp(fieldId, portalId);
        }
    }

    public void changeChannelAndWarp(int channel, int fieldID, boolean executeAfterScript, boolean instanceField) {
        if (executeAfterScript) {
            FieldTransferInfo fti = getFieldTransferInfo();
            fti.setChannel(channel);
            fti.setFieldId(fieldID);
            fti.setIsInstanceField(instanceField);
        } else {
            Client c = getChr().getClient();
            c.setOldChannel(c.getChannel());
            getChr().changeChannelAndWarp((byte) channel, fieldID);
        }
    }

    public void changeChannelAndWarp(int channel, int fieldID) {
        changeChannelAndWarp(channel, fieldID, true, false);
    }

    @Override
    public int getFieldID() {
        return getField().getId();
    }

    public void warpInstanceOut() {
        warpInstance(-1, false, 0, false);
    }

    public void warpInstanceIn(int id, int portal) {
        warpInstance(id, true, portal, false);
    }

    public void warpInstanceIn(int id, int portalId, boolean partyAllowed) {
        warpInstance(id, true, portalId, partyAllowed);
    }

    /**
     * The next instance this script warps into (warpInstanceIn) starts with this property, so the map's first-enter
     * script can read it (e.g. the difficulty, when Normal and Hard share a map). Read it with
     * {@link #hasInstanceProperty(String)}.
     */
    public void setNextInstanceProperty(String key, Object value) {
        nextInstanceProperties.put(key, value);
    }

    /**
     * @return whether the character's current instance has this property
     */
    public boolean hasInstanceProperty(String key) {
        return getChr() != null && getChr().getInstance() != null && getChr().getInstance().hasProperty(key);
    }

    public void warpInstanceOut(int id, int portal) {
        warpInstance(id, false, portal, false);
    }

    @Override
    public void warpInstanceIn(int id) {
        warpInstance(id, true, 0, false);
    }

    @Override
    public void warpInstanceOut(int id) {
        warpInstance(id, false, 0, false);
    }

    /** Quest record value for a key, or null when the record or key is absent. */
    public String getQRValueByKey(int questId, String key) {
        Quest quest = getChr().getQuestManager().getQuestById(questId);
        if (quest == null) {
            return null;
        }
        String v = quest.getProperty(key);
        return v == null || v.isEmpty() ? null : v;
    }

    public void warpNoReturn(int id, int portal) {
        getChr().warp(id, portal);
    }

    private static final int BOSS_PARTY_QR = 102401;

    /**
     * Boss waiting room (ptBossIn): sends the party on to the boss's entrance, where its own NPC starts the fight.
     * Boss order/difficulty come from QR 102401 when set, else the waiting map's return map is used.
     */
    public void startBoss() {
        Char chr = getChr();
        if (startFromWaitingRoom(chr)) {
            return;
        }
        int target = 0;
        String order = getQRValueByKey(BOSS_PARTY_QR, "order");
        String diff = getQRValueByKey(BOSS_PARTY_QR, "diff");
        if (order != null && diff != null && order.matches("\\d+") && diff.matches("\\d+")) {
            int[] entry = net.swordie.ms.client.character.BossUiAccess.getEntry(Integer.parseInt(order), Integer.parseInt(diff));
            if (entry != null) {
                target = entry[0];
            }
        }
        if (target == 0) {
            target = getField().getInfo().getReturnMap();
        }
        setSpeakerID(MenuModule.SPEAKER);
        if (target <= 0 || target >= GameConstants.NO_MAP_ID || target == getFieldID()) {
            sendSayOkay("This boss can't be entered from here. Use the boss window (Boss button) instead.");
            return;
        }
        Party party = chr.getParty();
        if (party != null && party.getPartyLeaderID() != chr.getId()) {
            sendSayOkay("Please have your party leader talk to me.");
            return;
        }
        if (!sendAskYesNo("Head to the boss's entrance? The boss is started by the guardian there.")) {
            return;
        }
        int from = getFieldID();
        if (party != null) {
            for (Char member : party.getPartyMembersInSameField(chr)) {
                if (member.getFieldID() == from) {
                    member.warp(target);
                }
            }
        }
        chr.warp(target);
    }

    /** arrival: the maps where the fight's helper starts (null: the fight map) */
    private record WaitingRoom(int room, int fight, int go, int clock, String name, int[] arrival) {
    }

    /** resources/data/boss-waiting-rooms.tsv: GMS waiting rooms (fieldType 421) */
    private static final java.util.function.Supplier<List<WaitingRoom>> BOSS_WAITING_ROOMS =
            net.swordie.ms.loaders.DataTable.cached("boss-waiting-rooms", t -> t.rows().stream()
                    .map(r -> new WaitingRoom(r.i("room"), r.i("fight"), r.i("go"), r.i("clock"), r.str("name").trim(),
                            r.has("arrival") ? r.ints("arrival") : null)).toList());

    /** resources/data/gollux-maps.tsv: Gollux's maps for the mini map */
    private static final java.util.function.Supplier<List<Integer>> GOLLUX_MAPS =
            net.swordie.ms.loaders.DataTable.cached("gollux-maps", t -> t.rows().stream().map(r -> r.i("map")).toList());

    /** In a known waiting room inside an instance: the GMS entry question, then the party goes in and the clock restarts. */
    private boolean startFromWaitingRoom(Char chr) {
        Instance instance = chr.getInstance();
        if (instance == null) {
            return false;
        }
        for (WaitingRoom room : BOSS_WAITING_ROOMS.get()) {
            if (room.room() == getFieldID()) {
                sendIntoFight(chr, instance, room.fight(), room.go(), room.clock(), room.name(), room.arrival());
                return true;
            }
        }
        // A boss that picks its fight map before the waiting room (e.g. Magnus by difficulty) stores it on the instance.
        if (instance.hasProperty(WR_FIGHT_MAP_KEY)) {
            int fight = (int) instance.getProperty(WR_FIGHT_MAP_KEY);
            int clock = (int) instance.getProperty(WR_FIGHT_CLOCK_KEY);
            String name = String.valueOf(instance.getProperty(WR_FIGHT_NAME_KEY));
            sendIntoFight(chr, instance, fight, fight, clock, name, null);
            return true;
        }
        return false;
    }

    public static final String WR_FIGHT_MAP_KEY = "wrFightMap";
    public static final String WR_FIGHT_CLOCK_KEY = "wrFightClock";
    public static final String WR_FIGHT_NAME_KEY = "wrFightName";

    private void sendIntoFight(Char chr, Instance instance, int fightMap, int goMap, int clock, String name,
                               int[] arrivalMaps) {
        Party party = chr.getParty();
        if (party != null && party.getPartyLeaderID() != chr.getId()) {
            sendSayOkay("Please have your party leader talk to me.");
            return;
        }
        int from = getFieldID();
        if (sendAskYesNo("#b#e<Boss Battle Map Entry>#n#k\r\n\r\nEverything is ready. #k#nDo you want to begin the #b"
                + name + "#k battle?")) {
            // the waiting room has no helper: from here the instance is the fight's, as if entered directly
            instance.setEnterFieldId(fightMap);
            instance.setInstanceHelper(null);
            var helper = net.swordie.ms.world.field.instance.instancehelper.InstanceHelperFactory
                    .getHelperByInstance(instance);
            for (Char member : new java.util.ArrayList<>(instance.getChars())) {
                if (member.getFieldID() == from) {
                    member.warp(goMap);
                }
            }
            instance.setTimeout(clock);
            if (helper != null) {
                if (goMap == fightMap) {
                    helper.setup(instance);
                } else {
                    setupOnArrival(instance, helper, arrivalMaps != null ? arrivalMaps : new int[]{fightMap});
                }
            }
        }
    }

    /**
     * The party first sits through an intro map: the fight's helper starts once someone reaches a fight map (its
     * mechanics would otherwise run on an empty map during the intro), or after a minute if nobody does.
     */
    private static void setupOnArrival(Instance instance, net.swordie.ms.world.field.instance.instancehelper.InstanceHelper helper,
                                       int[] arrivalMaps) {
        long deadline = System.currentTimeMillis() + 60_000;
        var done = new java.util.concurrent.atomic.AtomicBoolean();
        var task = new java.util.concurrent.atomic.AtomicReference<java.util.concurrent.ScheduledFuture<?>>();
        task.set(EventManager.addFixedRateEvent(() -> {
            if (done.get()) {
                return;
            }
            boolean there = instance.getChars().stream().anyMatch(c -> java.util.Arrays.stream(arrivalMaps)
                    .anyMatch(m -> m == c.getFieldID()));
            if (there || System.currentTimeMillis() > deadline || instance.getChars().isEmpty()) {
                if (done.compareAndSet(false, true)) {
                    var t = task.get();
                    if (t != null) {
                        t.cancel(false);
                    }
                    if (!instance.getChars().isEmpty()) {
                        helper.setup(instance);
                    }
                }
            }
        }, 250, 250));
    }

    /** ptBossOut / give-up button: leave to the boss's return map. */
    public void exitBoss() {
        setSpeakerID(MenuModule.SPEAKER);
        if (!sendAskYesNo("Do you want to leave this battle?")) {
            return;
        }
        String mapR = getQRValueByKey(BOSS_PARTY_QR, "mapR");
        int target = mapR != null && mapR.matches("\\d+") ? Integer.parseInt(mapR) : 0;
        if (target <= 0 || target >= GameConstants.NO_MAP_ID) {
            target = getField().getInfo().getReturnMap();
        }
        if (target <= 0 || target >= GameConstants.NO_MAP_ID || target == getFieldID()) {
            target = FieldConstants.HENESYS_FIELD_ID;
        }
        if (getChr().getInstance() != null) {
            warpInstanceOut(target);
        } else {
            getChr().warp(target);
        }
    }

    /** Balrog: body, both arms and the damage sink (the one with the HP bar) at the altar. */
    public void spawnBalrog(boolean easy) {
        int[] spawns = easy ? new int[]{8830007, 8830008, 8830009, 8830010} : new int[]{8830000, 8830001, 8830002, 8830003}; // hardcode-ok
        for (int id : spawns) {
            spawnMob(id, 412, 258, false);
        }
    }

    /** Clears clock, mobs, drops and death counts of a boss map (in the character's current instance). */
    public void resetBossMap(int fieldId) {
        Field map = getChr().getOrCreateFieldByCurrentInstanceType(fieldId);
        if (map == null) {
            return;
        }
        if (map.getClock() != null) {
            map.getClock().removeClock();
        }
        for (Mob m : new ArrayList<>(map.getMobs())) {
            map.removeLife(m);
        }
        for (Drop d : new ArrayList<>(map.getDrops())) {
            map.removeLife(d);
        }
        for (Char c : map.getChars()) {
            c.setDeathCount(0);
            c.write(UserLocal.deathCountInfo(0));
        }
    }

    /** Lotus phase 1 entry: restarts his laser pattern (skill level 5 = four lasers). */
    public void respawnLotusLaser() {
        Field f = getChr().getField();
        Life life = f.getLifeByTemplateIds(BLACK_HEAVEN_CORE_MOB, BLACK_HEAVEN_CORE_MOB_2);
        if (!(life instanceof Mob)) {
            return;
        }
        int laser = MobSkillID.LaserAttack.getVal();
        net.swordie.ms.loaders.containerclasses.MobSkillInfo msi = SkillData.getMobSkillInfoByIdAndLevel(laser, 1);
        net.swordie.ms.life.mob.skill.MobSkill skill = new net.swordie.ms.life.mob.skill.MobSkill();
        skill.setSkillID(laser);
        skill.setLevel(5);
        if (msi != null) {
            skill.setFixDamR(msi.getSkillStatIntValue(net.swordie.ms.life.mob.skill.MobSkillStat.fixDamR));
        }
        skill.applyEffect((Mob) life, false);
    }

    private void warpInstance(int fieldId, boolean in, int portalId, boolean partyAllowed) {
        getChr().getBossInfo().reset();

        Instance instance;
        if (in) {
            // warp party in if there is a party and party is allowed, solo instance otherwise
            Party party = getChr().getParty();
            if (party == null || !partyAllowed) {
                instance = new Instance(getChr());
            } else {
                instance = new Instance(party);
            }
            if (!nextInstanceProperties.isEmpty()) {
                nextInstanceProperties.forEach(instance::addProperty);
                nextInstanceProperties.clear();
            }
            // setup the instance & warp
            instance.setup(fieldId, portalId);
        } else {
            instance = getChr().getInstance();
            stopEvents();
            if (instance == null) {
                // no info, just warp them
                getChr().setDeathCount(-1);
                getChr().warp(fieldId, portalId);
            } else {

                // remove chr from eligible instance members
                int forcedReturn;
                int forcedReturnPortal;
                if (fieldId >= 0) {
                    forcedReturn = fieldId;
                    forcedReturnPortal = portalId;
                } else {
                    forcedReturn = instance.getForcedReturn();
                    forcedReturnPortal = instance.getForcedReturnPortalId();
                }

                instance.removeChar(getChr(), forcedReturn, forcedReturnPortal, true, true);
            }
        }
    }

    public void setInstanceTime(int seconds) {
        setInstanceTime(seconds, 0);
    }

    public void setInstanceTime(int seconds, int forcedReturnFieldId) {
        Instance instance = getChr() == null ? null : getChr().getInstance();
        if (instance != null) {
            if (forcedReturnFieldId != 0) {
                instance.setForcedReturn(forcedReturnFieldId);
            }
            if (instance.getRemainingTime() / 1000 < System.currentTimeMillis()) {
                // don't override old timeout value
                instance.setTimeout(seconds);
            }
        }
    }

    @Override
    public int getReturnField() {
        if (returnField == 0 && getChr() != null) {
            // remembered across logins/channel changes (QoL)
            String saved = getChr().getQuestManager().getHiddenRecord(GameConstants.FREE_MARKET_RETURN_QR);
            if (saved != null && saved.matches("\\d+")) {
                returnField = Integer.parseInt(saved);
            }
        }
        // Do this to prevent infinite returnField Loop
        if (getField().getId() == returnField || returnField < FieldConstants.HENESYS_FIELD_ID) {
            return FieldConstants.HENESYS_FIELD_ID;
        }
        return returnField;
    }

    @Override
    public void setReturnField(int returnField) {
        this.returnField = returnField;
        if (getChr() != null && returnField > 0) {
            getChr().getQuestManager().setHiddenRecord(GameConstants.FREE_MARKET_RETURN_QR, String.valueOf(returnField));
        }
    }

    @Override
    public void setReturnField() {
        setReturnField(getFieldID());
    }

    @Override
    public boolean hasMobsInField() {
        return getAmountOfMobsInField() > 0;
    }

    public boolean hasMobsInField(int fieldId) {
        return getAmountOfMobsInField(fieldId) > 0;
    }

    public boolean hasMobsInField(int fieldId, int templateId) {
        return getAmountOfMobsInField(fieldId, templateId) > 0;
    }

    @Override
    public int getAmountOfMobsInField() {
        return getField().getMobs().size();
    }

    public int getAmountOfMobsInField(int fieldId) {
        var field = getChr().getOrCreateFieldByCurrentInstanceType(fieldId);
        return field.getMobs().size();
    }

    public int getAmountOfMobsInField(int fieldId, int templateId) {
        var field = getChr().getOrCreateFieldByCurrentInstanceType(fieldId);
        return (int) field.getMobs().stream()
                .filter(m -> m.getTemplateId() == templateId)
                .count();
    }

    public void killMobs() {
        List<Mob> mobs = new ArrayList<>(getField().getMobs());
        for (Mob mob : mobs) {
            mob.die(false);
        }
    }

    public void killMobs(int templateId) {
        List<Mob> mobs = new ArrayList<>(getField().getMobs());
        for (Mob mob : mobs) {
            if (mob.getTemplateId() == templateId) {
                mob.die(false);
            }
        }
    }

    public void showWeatherNoticeToField(String text, WeatherEffNoticeType type) {
        showWeatherNoticeToField(text, type, 7000); // 7 seconds
    }

    public void showWeatherNoticeToField(String text, WeatherEffNoticeType type, int duration) {
        Field field = getField();
        field.broadcastPacket(WvsContext.weatherEffectNotice(type, text, duration));
    }

    public void showEffectToField(String dir) {
        showEffectToField(dir, 0);
    }

    public void showEffectToField(String dir, int delay) {
        Field field = getField();
        field.broadcastPacket(UserPacket.effect(Effect.effectFromWZ(dir, false, delay, 4, 0)));
    }

    public void showFieldEffect(String dir) {
        showFieldEffect(dir, 0);
    }

    @Override
    public void showFieldEffect(String dir, int delay) {
        getField().broadcastPacket(FieldPacket.fieldEffect(FieldEffect.getFieldEffectFromWz(dir, delay)));
    }

    @Override
    public void showObjectFieldEffect(String objectEffectName) {
        getField().broadcastPacket(FieldPacket.fieldEffect(FieldEffect.getFieldEffectFromObject(objectEffectName)));
    }

    @Override
    public void setPortalEnabled(String portalName, boolean enabled){
        Portal portal = getField().getInfo().getPortalByName(portalName);
        if(portal != null){
            portal.setEnabled(enabled);
        }
    }

    public void showFieldEffectToField(String dir) {
        showFieldEffect(dir, 0);
    }

    public void showFieldEffectToField(String dir, int delay) {
        Field field = getField();
        field.broadcastPacket(FieldPacket.fieldEffect(FieldEffect.getFieldEffectFromWz(dir, delay)));
    }

    public void showOffFieldEffect(String dir) {
        getChr().write(FieldPacket.fieldEffect(FieldEffect.getOffFieldEffectFromWz(dir, 0)));
    }

    public void changeBGM(String dir, int startTime, int idk) {
        getChr().write(FieldPacket.fieldEffect(FieldEffect.changeBGM(dir, startTime, idk)));
    }

    public void bgmVolumeOnly(boolean volumeOnly) {
        getChr().write(FieldPacket.fieldEffect(FieldEffect.bgmVolumeOnly(volumeOnly)));
    }

    public void bgmVolume(int volume, int fadingDuration) {
        getChr().write(FieldPacket.fieldEffect(FieldEffect.bgmVolume(volume, fadingDuration)));
    }

    public void showFieldBackgroundEffect(String dir) {
        showFieldBackgroundEffect(dir, 0);
    }

    public void showFieldBackgroundEffect(String dir, int delay) {
        getChr().write(FieldPacket.fieldEffect(FieldEffect.getFieldBackgroundEffectFromWz(dir, delay)));
    }

    public void showFadeTransition(int duration, int fadeInTime, int fadeOutTime) {
        getChr().write(FieldPacket.fieldEffect(FieldEffect.takeSnapShotOfClient2(fadeInTime, duration, fadeOutTime, true)));
    }

    public void showFade(int duration) {
        getChr().write(FieldPacket.fieldEffect(FieldEffect.takeSnapShotOfClient(duration)));
    }

    public void setFieldColour(GreyFieldType colorFieldType, short red, short green, short blue, int time) {
        getChr().write(FieldPacket.fieldEffect(FieldEffect.setFieldColor(colorFieldType, red, green, blue, time)));
    }

    public void setFieldGrey(GreyFieldType colorFieldType, boolean show) {
        getChr().write(FieldPacket.fieldEffect(FieldEffect.setFieldGrey(colorFieldType, show)));
    }

    public void removeOverlapScreen(int duration) {
        getChr().write(FieldPacket.fieldEffect(FieldEffect.removeOverlapScreen(duration)));
    }

    public void onLayer(int duration, String key, int x, int y, int z, String origin, int org, boolean postRender, int idk, boolean repeat) {
        getChr().write(FieldPacket.fieldEffect(FieldEffect.onOffLayer(0, duration, key, x, y, z, origin, org, postRender, idk, repeat)));
    }

    public void moveLayer(int duration, String key, int x, int y) {
        getChr().write(FieldPacket.fieldEffect(FieldEffect.onOffLayer(1, duration, key, x, y, 0, null, 0, false, 0, false)));
    }

    public void offLayer(int duration, String key, boolean repeat) {
        getChr().write(FieldPacket.fieldEffect(FieldEffect.onOffLayer(2, duration, key, 0, 0, 0, null, 0, false, 0, repeat)));
    }

    public void spineScreen(boolean binary, boolean loop, boolean postRender, int endDelay, String path,
                            String animationName, String keyName) {
        getChr().write(FieldPacket.fieldEffect(FieldEffect.spineScreen(binary, loop, postRender, endDelay, path, animationName, keyName)));
    }

    /** Kaling's Four Seasons hub: opens the season picker (KalingSeasonPick). */
    public void openKalingSeasonPick() {
        net.swordie.ms.world.field.instance.instancehelper.boss.KalingSeasonPick.open(getChr());
    }

    /** GMS v271 boss intro spine (FIELD_EFFECT 84). */
    public void spineIntro(String key, String path, String animation) {
        getChr().write(FieldPacket.spineIntro(key, path, animation));
    }

    /** GMS v271 boss entrance animation (FIELD_EFFECT 47). */
    public void bossEnterAnimation(String path) {
        getChr().write(FieldPacket.bossEnterAnimation(path));
    }

    /** GMS v271 boss entrance skeleton (FIELD_EFFECT 44). */
    public void bossEnterSkeleton(String path, String animation) {
        getChr().write(FieldPacket.bossEnterSkeleton(path, animation));
    }

    public void offSpineScreen(String keyName, int type, String aniName, int alphaTime) {
        getChr().write(FieldPacket.fieldEffect(FieldEffect.offSpineScreen(keyName, type, aniName, alphaTime)));
    }

    @Override
    public void dropItem(int itemId, int x, int y) {
        dropItem(itemId, 1, x, y);
    }

    public void dropItem(int itemId, int quantity, int x, int y) {
        Field field = getField();
        Drop drop = new Drop(-1);
        drop.setItem(ItemData.getItemDeepCopy(itemId));
        drop.getItem().setQuantity(quantity);
        Position position = new Position(x, y);
        drop.setPosition(position);
        field.drop(drop, position, true);
    }

    @Override
    public void dropItems(Set<Tuple<Integer, Integer>> dropInfos, int x, int y, int ownerId) {
        Set<DropInfo> dropInfoSet = new HashSet<>();
        for (Tuple<Integer, Integer> dropInfo : dropInfos) {
            int itemid = dropInfo.getLeft(); // itemId
            int chance = dropInfo.getRight(); // per 10,000

            DropInfo di = new DropInfo(itemid, chance); // add min/max Quantity if needed
            dropInfoSet.add(di);
        }

        getField().drop(dropInfoSet, new Position(x, y), ownerId);
    }

    public void dropItemsWithQuantity(List<List<Integer>> drops, int x, int y, int ownerId) {
        Set<DropInfo> dropInfoSet = new HashSet<>();
        for (var drop : drops) {
            var itemid = drop.get(0);
            var quantity = drop.get(1);

            DropInfo di = new DropInfo(itemid, 10000);
            di.setMinQuant(quantity);
            di.setMaxQuant(quantity);
            dropInfoSet.add(di);
        }

        getField().drop(dropInfoSet, new Position(x, y), ownerId);
    }

    @Override
    public Set<DropInfo> genDropInfoFromPyTuples(List<PyTuple> tuples) {
        Set<DropInfo> dropInfos = new HashSet<>();
        for (PyTuple tuple : tuples) {
            int itemId = (int) tuple.get(0);
            int chance = (int) tuple.get(1);

            dropInfos.add(new DropInfo(itemId, chance));
        }

        return dropInfos;
    }

    public Set<DropInfo> genDropInfoQuantityFromPyTuples(List<List<Integer>> tuples) {
        Set<DropInfo> dropInfos = new HashSet<>();
        for (var tuple : tuples) {
            int itemId = tuple.get(0);
            int quantity = tuple.get(1);

            dropInfos.add(new DropInfo(itemId, 10000));
        }

        return dropInfos;
    }

    @Override
    public Position newPosition(int x, int y) {
        return new Position(x, y);
    }

    @Override
    public void teleportInField(Position position) {
        getChr().write(FieldPacket.teleport(position, getChr()));
    }

    @Override
    public void teleportInField(int x, int y) {
        teleportInField(new Position(x, y));
    }

    @Override
    public void teleportToPortal(int portalId) {
        Portal portal = getField().getInfo().getPortalByID(portalId);
        if (portal != null) {
            Position position = new Position(portal.getX(), portal.getY());
            getChr().setPosition(position.deepCopy()); // the server knows where it is right away (the client reports later)
            getChr().write(FieldPacket.teleport(position, getChr()));
        }
    }

    public Drop getDropInRect(int itemID, Rect rect) {
        Field field = getField();
        if (field == null) {
            field = getField();
        }
        return field.getDropsInRect(rect).stream()
                .filter(drop -> drop.getItem() != null && drop.getItem().getItemId() == itemID)
                .findAny().orElse(null);
    }

    @Override
    public Drop getDropInRect(int itemID, int rectRange) {
        return getDropInRect(itemID, new Rect(
                new Position(
                        getChr().getPosition().getX() - rectRange,
                        getChr().getPosition().getY() - rectRange),
                new Position(
                        getChr().getPosition().getX() + rectRange,
                        getChr().getPosition().getY() + rectRange))
        );

    }

    public void changeFoothold(String footholdName, boolean show) {
        changeFoothold(footholdName, show, 0, 0);
    }

    public void changeFoothold(String footholdName, boolean show, int x, int y) {
        getChr().getField().broadcastPacket(FieldPacket.footholdAppear(footholdName, show, new Position(x, y)));
    }

    public void createFallingCatcher(String templateStr, int hpR, int amount, int chance) {
        Field field = getField();
        List<Position> positions = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            if (Util.succeedProp(chance)) {
                positions.add(Util.getRandomFromCollection(field.getInfo().getFootholds().values()).getRandomPosition());
            }
        }
        createFallingCatcher(templateStr, hpR, positions);
    }

    public void createFallingCatcher(String templateStr, int hpR, List<Position> positions) {
        FallingCatcher fallingCatcher = new FallingCatcher(templateStr, hpR, positions);
        getField().broadcastPacket(LucidFieldPacket.createFallingCatcher(fallingCatcher));
    }

    // Life-related methods --------------------------------------------------------------------------------------------


    // NPC methods
    @Override
    public void spawnNpc(int npcId, int x, int y) {
        Npc npc = NpcData.getNpcDeepCopyById(npcId);
        if (npc == null) {
            return; // not in this version's Npc.wz (logged by NpcData)
        }
        Position position = new Position(x, y);
        npc.setPosition(position);
        npc.setCy(y);
        npc.setRx0(x + 50);
        npc.setRx1(x - 50);
        Foothold fh = getField().findFootHoldBelow(new Position(x, y - 2));
        if (fh != null) {
            npc.setFh(getField().findFootHoldBelow(new Position(x, y - 2)).getId());
        }
        npc.setNotRespawnable(true);

        getField().spawnLife(npc, getChr());
    }

    /** Spawns the NPC unless the field already has one (for map scripts that run on every entry). */
    public void spawnNpcIfAbsent(int npcId, int x, int y) {
        if (!(getField().getLifeByTemplateId(npcId) instanceof Npc)) {
            spawnNpc(npcId, x, y);
        }
    }

    @Override
    public void spawnNpc(int npcId, int x, int y, boolean flip) {
        spawnNpcGetObjectId(npcId, x, y, flip);
    }

    /** spawnNpc that returns the NPC's object id (0 if not in the data), for scenes with several of one NPC. */
    public int spawnNpcGetObjectId(int npcId, int x, int y, boolean flip) {
        Npc npc = NpcData.getNpcDeepCopyById(npcId);
        if (npc == null) {
            return 0; // not in this version's Npc.wz (logged by NpcData)
        }
        Position position = new Position(x, y);
        npc.setPosition(position);
        npc.setCy(y);
        npc.setRx0(x + 50);
        npc.setRx1(x - 50);
        npc.setFlip(flip);
        Foothold fh = getField().findFootHoldBelow(new Position(x, y - 2));
        if (fh != null) {
            npc.setFh(getField().findFootHoldBelow(new Position(x, y - 2)).getId());
        }
        npc.setNotRespawnable(true);
        if (npc.getField() == null) {
            npc.setField(field);
        }

        getField().spawnLife(npc, getChr());
        return npc.getObjectId();
    }

    @Override
    public void removeNpc(int npcId) {
        var npc = getField().getNpcs().stream()
                .filter(n -> n.getTemplateId() == npcId)
                .findFirst()
                .orElse(null);
        if (npc instanceof Npc) {
            getField().removeLife(npc);
        }
    }

    @Override
    public void openNpc(int npcId) {
        Npc npc = NpcData.getNpcDeepCopyById(npcId);
        String script;
        if (npc.getScripts().size() > 0) {
            script = npc.getScripts().get(0);
        } else {
            script = String.valueOf(npc.getTemplateId());
        }
        getChr().getScriptManager().startScript(npc.getTemplateId(), npcId, script, ScriptType.Npc, null);
    }

    @Override
    public void openShop(int shopID) {
        NpcShopDlg nsd = NpcData.getShopById(shopID);
        if (nsd != null) {
            if (getChr().getShop() == null) {
                getChr().setShop(nsd);
                if(shopID == GameConstants.COLLECTOR_NPC) {
                    getChr().write(ShopDlg.shopCollectorInit());
                }

                getChr().write(ShopDlg.openShop(getChr(), 0, nsd));
            }
        } else {
            chat(String.format("Could not find shop with id %d.", shopID));
            log.error(String.format("Could not find shop with id %d.", shopID));
        }
    }

    @Override
    public void openTrunk(int npcTemplateID) {
        if(getChr() == null || getChr().isOnline() == false) {
            log.error(String.format("[CharId: %d] tried to open trunk while being offline.", chr.getId()));
            return;
        }
        getChr().setInTrunk(true); // storage requests are only accepted while the storage is open
        getChr().write(FieldPacket.trunkDlg(TrunkDlg.open(npcTemplateID, getChr().getAccount().getTrunk())));
    }

    @Override
    public void setSpeakerID(int templateID) {
        NpcScriptInfo nsi = getNpcScriptInfo();
        nsi.removeParam(NpcScriptInfo.Param.PlayerAsSpeaker);
        boolean isNotCancellable = nsi.hasParam(NpcScriptInfo.Param.NotCancellable);
        nsi.setTemplateID(templateID);
        if (isNotCancellable) {
            nsi.addParam(NpcScriptInfo.Param.NotCancellable);
        }
    }

    public void setInnerOverrideSpeakerTemplateID(int templateID) {
        getNpcScriptInfo().setInnerOverrideSpeakerTemplateID(templateID);
    }

    @Override
    public void setSpeakerType(byte speakerType) {
        NpcScriptInfo nsi = getNpcScriptInfo();
        nsi.setSpeakerType(speakerType);
    }

    public void hideNpcByTemplateId(int npcTemplateId, boolean hide) {
        hideNpcByTemplateId(npcTemplateId, hide, hide);
    }

    @Override
    public void hideNpcByTemplateId(int npcTemplateId, boolean hideTemplate, boolean hideNameTag) {
        Field field = getField();
        Life life = field.getLifeByTemplateId(npcTemplateId);
        if (!(life instanceof Npc)) {
            log.debug(String.format("[hideNpcByTemplateId] npc %d is null or not an instance of Npc", npcTemplateId));
            return;
        }
        getChr().write(NpcPool.npcViewOrHide(life.getObjectId(), !hideTemplate, !hideNameTag));
    }

    public void hideNpcByObjectId(int npcObjId, boolean hide) {
        hideNpcByObjectId(npcObjId, hide, hide);
    }

    /** As hideNpcByObjectId, if the character is still in that map (for a delayed call: object ids are per map). */
    public void hideNpcInField(int fieldId, int npcObjId, boolean hide) {
        if (getChr() != null && getChr().getFieldID() == fieldId) {
            hideNpcByObjectId(npcObjId, hide, hide);
        }
    }

    @Override
    public void hideNpcByObjectId(int npcObjId, boolean hideTemplate, boolean hideNameTag) {
        Field field = getField();
        Life life = field.getLifeByObjectID(npcObjId);
        if (!(life instanceof Npc)) {
            log.debug(String.format("[hideNpcByObjectId] npc %d is null or not an instance of Npc", npcObjId));
            return;
        }
        getChr().write(NpcPool.npcViewOrHide(life.getObjectId(), !hideTemplate, !hideNameTag));
    }

    @Override
    public void moveNpcByTemplateId(int npcTemplateId, boolean left, int distance, int speed) {
        Field field = getField();
        Life life = field.getLifeByTemplateId(npcTemplateId);
        if (!(life instanceof Npc)) {
            log.debug(String.format("[moveNpcByTemplateId] npc %d is null or not an instance of Npc", npcTemplateId));
            return;
        }
        getChr().write(NpcPool.npcSetForceMove(life.getObjectId(), left, distance, speed));
    }

    @Override
    public void moveNpcByObjectId(int npcObjId, boolean left, int distance, int speed) {
        Field field = getField();
        Life life = field.getLifeByObjectID(npcObjId);
        if (!(life instanceof Npc)) {
            log.debug(String.format("[moveNpcByObjectId] npc %d is null or not an instance of Npc", npcObjId));
            return;
        }
        getChr().write(NpcPool.npcSetForceMove(life.getObjectId(), left, distance, speed));
    }

    @Override
    public void flipNpcByTemplateId(int npcTemplateId, boolean left) {
        Field field = getField();
        Life life = field.getLifeByTemplateId(npcTemplateId);
        if (!(life instanceof Npc)) {
            log.debug(String.format("[flipNpcByTemplateId] npc %d is null or not an instance of Npc", npcTemplateId));
            return;
        }
        getChr().write(NpcPool.npcSetForceFlip(life.getObjectId(), left));
    }

    @Override
    public void flipNpcByObjectId(int npcObjId, boolean left) {
        Field field = getField();
        Life life = field.getLifeByObjectID(npcObjId);
        if (!(life instanceof Npc)) {
            log.debug(String.format("[flipNpcByObjectId] npc %d is null or not an instance of Npc", npcObjId));
            return;
        }
        getChr().write(NpcPool.npcSetForceFlip(life.getObjectId(), left));
    }

    public void showNpcSpecialActionByTemplateId(int npcTemplateId, String effectName) {
        showNpcSpecialActionByTemplateId(npcTemplateId, effectName, 0);
    }

    @Override
    public void showNpcSpecialActionByTemplateId(int npcTemplateId, String effectName, int duration) {
        Field field = getField();
        Life life = field.getLifeByTemplateId(npcTemplateId);
        if (!(life instanceof Npc)) {
            log.debug(String.format("[showNpcSpecialActionByTemplateId] npc %d is null or not an instance of Npc", npcTemplateId));
            return;
        }
        getChr().write(NpcPool.npcSetSpecialAction(life.getObjectId(), effectName, duration));
    }

    public void showNpcSpecialActionByObjectId(int npcObjId, String effectName) {
        showNpcSpecialActionByObjectId(npcObjId, effectName, 0);

    }

    @Override
    public void showNpcSpecialActionByObjectId(int npcObjId, String effectName, int duration) {
        Field field = getField();
        Life life = field.getLifeByObjectID(npcObjId);
        if (!(life instanceof Npc)) {
            log.debug(String.format("[showNpcSpecialActionByObjectId] npc %d is null or not an instance of Npc", npcObjId));
            return;
        }
        getChr().write(NpcPool.npcSetSpecialAction(life.getObjectId(), effectName, duration));
    }

    public void resetNpcSpecialActionByTemplateId(int templateId) {
        Field field = getField();
        Life life = field.getLifeByTemplateId(templateId);
        if (!(life instanceof Npc)) {
            log.debug(String.format("[resetNpcSpecialActionByTemplateId] npc %d is null or not an instance of Npc", templateId));
            return;
        }
        resetNpcSpecialActionByObjectId(life.getObjectId());
    }

    public void resetNpcSpecialActionByObjectId(int objectId) {
        getChr().write(NpcPool.npcResetSpecialAction(objectId));
    }

    public int getNpcObjectIdByTemplateId(int npcTemplateId) {
        Field field = getField();
        Life life = field.getLifeByTemplateId(npcTemplateId);
        if (!(life instanceof Npc)) {
            log.debug(String.format("[getNpcObjectIdByTemplateId] npc %d is null or not an instance of Npc", npcTemplateId));
            return 0;
        }
        return life.getObjectId();
    }


    // Mob methods
    @Override
    public Mob spawnMob(int id) {
        return spawnMob(id, 0, 0, false);
    }

    @Override
    public Mob spawnMob(int id, boolean respawnable) {
        return spawnMob(id, 0, 0, respawnable);
    }

    @Override
    public Mob spawnMobOnChar(int id) {
        return spawnMob(id, getChr().getPosition().getX(), getChr().getPosition().getY(), false);
    }

    @Override
    public Mob spawnMobOnChar(int id, boolean respawnable) {
        return spawnMob(id, getChr().getPosition().getX(), getChr().getPosition().getY(), respawnable);
    }

    @Override
    public Mob spawnMob(int id, int x, int y, boolean respawnable) {
        return spawnMob(id, x, y, respawnable, 0);
    }

    public Mob spawnMob(int id, int x, int y) {
        return spawnMob(id, x, y, false, 0);
    }

    public Mob spawnMob(int id, int x, int y, long hp) {
        return spawnMob(id, x, y, false, hp);
    }

    public Mob spawnMob(int id, int x, int y, boolean respawnable, long hp) {
        return getField().spawnMob(id, x, y, respawnable, hp);
    }

    /** A boss entering with its summon effect (appearType = Effect.wz Summon.img index). */
    public Mob spawnMobAppearing(int id, int x, int y, int appearType) {
        return getField().spawnMob(id, x, y, false, 0, appearType);
    }

    public Mob spawnMobAppearing(int id, int x, int y, long hp, int appearType) {
        return getField().spawnMob(id, x, y, false, hp, appearType);
    }

    public Mob spawnMobWithAppearType(int id, int x, int y, int appearType, int option) {
        return getField().spawnMobWithAppearType(id, x, y, appearType, option);
    }

    @Override
    public void removeMobByObjId(int id) {
        getField().removeLife(id);
        getField().broadcastPacket(MobPool.leaveField(id, DeathType.ANIMATION_DEATH));
    }

    @Override
    public void removeMobByTemplateId(int id) {
        Field field = getField();
        Life life = field.getLifeByTemplateId(id);
        if (life == null) {
            log.error(String.format("Could not find Mob by template id %d.", id));
            return;
        }
        removeMobByObjId(life.getObjectId());
    }

    public boolean isFinishedEscort(int templateID) {
        Field field = getField();
        Life life = field.getLifeByTemplateId(templateID);
        if (!(life instanceof Mob)) {
            getChr().dispose();
            return false;
        }
        Mob mob = (Mob) life;
        boolean finished = mob.isFinishedEscort();
        if (!finished) {
            getChr().dispose();
        }
        return finished;
    }

    @Override
    public void showHP(int templateID) {
        getField().getMobs().stream()
                .filter(m -> m.getTemplateId() == templateID)
                .findFirst()
                .ifPresent(mob -> getField().broadcastPacket(FieldPacket.fieldEffect(FieldEffect.mobHPTagFieldEffect(mob))));
    }

    @Override
    public void showHP() {
        getField().getMobs().stream()
                .filter(m -> m.getHp() > 0)
                .findFirst()
                .ifPresent(mob -> getField().broadcastPacket(FieldPacket.fieldEffect(FieldEffect.mobHPTagFieldEffect(mob))));
    }


    // Reactor methods
    @Override
    public void removeReactor() {
        Field field = getField();
        Life life = field.getLifeByObjectID(getObjectIDByScriptType(ScriptType.Reactor));
        if (life instanceof Reactor) {
            field.removeLife(life.getObjectId(), false);
        }
    }

    @Override
    public void spawnReactor(int reactorId, int x, int y) {
        Field field = getField();
        Reactor reactor = ReactorData.getReactorByID(reactorId);
        reactor.setPosition(new Position(x, y));
        field.spawnLife(reactor, null);
    }

    @Override
    public boolean hasReactors() {
        Field field = getField();
        return field.getReactors().size() > 0;
    }

    @Override
    public int getReactorQuantity() {
        Field field = getField();
        return field.getReactors().size();
    }


    public int getReactorState(int reactorId) {
        Field field = getField();
        Life life = field.getLifeByTemplateId(reactorId);
        if (life != null && life instanceof Reactor) {
            Reactor reactor = (Reactor) life;
            return reactor.getState();
        }
        return -1;
    }

    public void increaseReactorState(int reactorId, int stateLength) {
        getField().increaseReactorState(getChr(), reactorId, stateLength);
    }

    public void changeReactorStateByTemplateId(int templateId, byte state, short delay, byte stateLength) {
        Field field = getField();
        Set<Reactor> reactors = field.getReactors().stream()
                .filter(r -> r.getTemplateId() == templateId)
                .collect(Collectors.toSet());

        for (var reactor : reactors) {
            reactor.setState(state);
            getField().broadcastPacket(ReactorPool.reactorChangeState(reactor, delay, stateLength));
        }
    }


    // Party-related methods -------------------------------------------------------------------------------------------

    @Override
    public Party getParty() {
        return getChr().getParty();
    }

    @Override
    public Party createSoloParty() {
        Party party = Party.createNewParty(
            false,
            true,
            getChr().getName() + "'s party",
            getChr().getClient().getWorld()
        );
        party.addPartyMember(getChr());
        party.setBossSolo(true); // only for the fight: ended when its instance closes (Instance.clear)
        party.broadcast(WvsContext.partyResult(PartyResult.createNewParty(party)));

        return party;
    }

    @Override
    public int getPartySize() {
        return getParty().getMembers().size();
    }

    @Override
    public boolean isPartyLeader() {
        return getChr().getParty() != null && getChr().getParty().getPartyLeaderID() == getChr().getId();
    }

    public boolean checkParty() {
        return checkParty(null);
    }

    @Override
    public boolean checkParty(BossCooldown bossCooldown) {
        return checkParty(bossCooldown, 0);
    }

    private static final boolean ADMIN_BOSS_BYPASS =
            net.swordie.ms.ServerSettings.getBoolean("test.bossEntryAdminBypass", false);

    @Override
    public boolean checkParty(BossCooldown bossCooldown, int reqLevel) {
        if (getChr().getParty() == null) {
            chat("You are not in a party.");
            return false;
        } else if (!isPartyLeader()) {
            chat("You are not the party leader.");
            return false;
        }
        boolean res = true;
        String prereqMsg = null; // the first member missing the boss's story quest (Classic characters)
        Char leader = getChr().getParty().getPartyLeader().getChr();
        if (leader == null) {
            chat("Your leader is currently offline.");
            res = false;
        } else {
            for (PartyMember partyMember : getChr().getParty().getPartyMembers()) {
                if (partyMember != null) {
                    Char pmChr = partyMember.getChr();
                    if (pmChr == null || !pmChr.isOnline()) {
                        // (an offline member has no Char: its name comes from the party record)
                        chat(partyMember.getCharName() + " is not online.");
                        res = false;
                        continue;
                    }
                    String name = pmChr.getName();
                    if (pmChr.getField() != getChr().getField()) {
                        chat(name + " is not in the same map as you.");
                        res = false;
                    } else if (pmChr.getInstance() != null) {
                        // kinda overlaps with the above check, but to prevent weird stuff from happening
                        chat(name + " is already in their own instance.");
                        res = false;
                    } else if (bossCooldown != null && pmChr.getAccount().isOnBossCooldown(bossCooldown)
                            && !net.swordie.ms.ext.ServerHooks.ignoresBossCooldown(pmChr, leader)) {
                        int totalMinutes = pmChr.getScriptManager().getRemainingBossCooldownMinutes(bossCooldown);
                        int hours = totalMinutes / 60;
                        int minutes = totalMinutes % 60;

                        chat(String.format("%s has recently fought this boss. Time remaining: %s hours and %s minutes.", name, hours, minutes));
                        res = false;
                    }
                    // test.bossEntryAdminBypass: admin characters skip the level and story-quest checks (boss tests)
                    boolean bypass = ADMIN_BOSS_BYPASS && pmChr.getUser().getAccountType().isGmOrAdmin();
                    if (pmChr.getLevel() < reqLevel && !bypass) {
                        chat(name + " doesn't meet the required level to enter.");
                        res = false; // every member's problems are listed, not just the first
                    }
                    int missing = bypass ? 0
                            : net.swordie.ms.client.character.BossPrereqModule.getMissingQuest(pmChr, bossCooldown);
                    if (missing != 0) {
                        chat(name + " hasn't completed " + net.swordie.ms.client.character.BossPrereqModule.questName(missing)
                                + " yet.");
                        if (prereqMsg == null) {
                            prereqMsg = net.swordie.ms.client.character.BossPrereqModule.getMessage(
                                    pmChr == getChr() ? null : name, missing);
                        }
                        res = false;
                    }
                }
            }
        }
        if (prereqMsg != null) {
            sendSayOkay(prereqMsg);
        }
        return res;
    }

    public List<Char> getOnlinePartyMembers() {
        Party party = getParty();
        if (party == null) {
            return new ArrayList<>();
        }
        return party.getOnlineChars();
    }

    public List<Char> getPartyMembersInSameField(Char chr) {
        Party party = getParty();
        if (party == null) {
            return new ArrayList<>();
        }
        List<Char> list = new ArrayList<>(party.getPartyMembersInSameField(chr));
        list.add(chr);
        return new ArrayList<>(list);
    }


    // Guild/Alliance related methods -------------------------------------------------------------------------------------------

    @Override
    public void showGuildCreateWindow() {
        getChr().write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_InputGuildName)));
    }

    @Override
    public boolean checkAllianceName(String name) {
        World world = getChr().getClient().getWorld();
        return world.getAlliance(name) == null;
    }

    public void incrementMaxGuildMembers(int amount) {
        Guild guild = getChr().getGuild();
        guild.setMaxMembers(guild.getMaxMembers() + amount);
        guild.broadcast(WvsContext.guildResult(GuildResult.incMaxMemberNum(guild)));
    }

    public void createAlliance(String name, Char other) {
        Alliance alliance = new Alliance(true);
        alliance.setName(name);
        alliance.addGuild(getChr().getGuild());
        alliance.addGuild(other.getGuild());
        GuildMember chrMember = getChr().getGuild().getMemberByCharID(getChr().getId());
        chrMember.setAllianceGrade(1);
        GuildMember otherMember = other.getGuild().getMemberByCharID(other.getId());
        otherMember.setAllianceGrade(2);
        allianceDao.saveOrUpdate(alliance);
        getChr().getGuild().setAlliance(alliance);
        other.getGuild().setAlliance(alliance);
        alliance.broadcast(net.swordie.ms.connection.packet.AlliancePacket.created(alliance));
        getChr().deductMoney(5000000, ProgressionLog.Sink.FEES); // hardcode-ok

        getChr().getWorld().addAlliance(alliance);
    }


    // Chat-related methods --------------------------------------------------------------------------------------------

    @Override
    public void chat(String text) {
        chatRed(text);
    }

    /**
     * Sends a developer message (e.g. "Not coded") only to Tester/GM accounts, see
     * {@link net.swordie.ms.constants.GameConstants#SHOW_DEBUG_MESSAGES_TO_PLAYERS}.
     */
    public void debugChat(String text) {
        if (getChr() != null && getChr().isDebugViewer()) {
            chatRed(text);
        }
    }

    @Override
    public void chatRed(String text) {
        getChr().chatMessage(SystemNotice, text);
    }

    @Override
    public void chatBlue(String text) {
        getChr().chatMessage(Notice2, text);
    }

    public void systemMessage(String message) {
        getChr().write(WvsContext.message(MessagePacket.systemMessage(message)));
    }

    @Override
    public void chatScript(String text) {
        getChr().chatScriptMessage(text);
    }

    public void showWeatherNotice(String text, WeatherEffNoticeType type) {
        showWeatherNotice(text, type, 7000); // 7 seconds
    }

    @Override
    public void showWeatherNotice(String text, WeatherEffNoticeType type, int duration) {
        getField().broadcastPacket(WvsContext.weatherEffectNotice(type, text, duration));
    }


    // Inventory-related methods ---------------------------------------------------------------------------------------

    @Override
    public void giveMesos(long mesos) {
        getChr().addMoney(mesos);
        getChr().write(WvsContext.message(MessagePacket.incMoneyMessage(mesos)));
    }

    @Override
    public void deductMesos(long mesos) {
        getChr().deductMoney(mesos, ProgressionLog.Sink.FEES);
        getChr().write(WvsContext.message(MessagePacket.incMoneyMessage(-mesos)));
    }

    @Override
    public long getMesos() {
        return getChr().getMoney();
    }

    @Override
    public void giveItem(int id) {
        giveItem(id, 1);
    }

    @Override
    public void giveItem(int id, int quantity) {
        getChr().addItemToInventory(id, quantity);
        String itemName = StringData.getItemStringById(id);
        if (itemName != null) {
            getChr().chatMessage(GameDesc, String.format("You've gained items: %s. (%d)", itemName, quantity));
        }
    }

    public void giveItemWithExpiry(int id, int hours) {
        var item = ItemData.getItemDeepCopy(id);
        item.setDateExpire(FileTime.fromDate(LocalDateTime.now().plusHours(hours)));
        item.setQuantity(1);
        getChr().addItemToInventory(item);

        String itemName = StringData.getItemStringById(id);
        if (itemName != null) {
            getChr().chatMessage(GameDesc, String.format("You've gained items: %s. (Expires in %d hours)", itemName, hours));
        }
    }

    public void giveAndEquip(int id) {
        if (!ItemConstants.isEquip(id)) {
            giveItem(id);
        }
        Item equip = ItemData.getItemDeepCopy(id);
        if (equip == null) {
            return;
        }

        // replace the old equip if there was any
        Inventory equipInv = getChr().getEquippedInventory();
        int bodyPart = ItemConstants.getBodyPartFromItem(id, getChr().getAvatarData().getAvatarLook().getGender());
        Item oldEquip = equipInv.getItemBySlot(bodyPart);
        if (oldEquip != null) {
            int newSlot = getChr().getEquipInventory().getFirstOpenSlot();
            getChr().unequip(oldEquip);
            oldEquip.setBagIndex(newSlot);
            oldEquip.updateToChar(getChr());
        }
        equip.setBagIndex(bodyPart);
        getChr().equip(equip, bodyPart);
        equip.updateToChar(getChr());
    }

    public void giveNewSecondary(int id) {
        if (!ItemConstants.isEquip(id)) {
            giveItem(id);
        }
        Item newEquipItem = ItemData.getItemDeepCopy(id);
        if (newEquipItem == null) {
            return;
        }

        var newEquip = (Equip) newEquipItem;
        // replace the old equip if there was any
        Inventory equipInv = getChr().getEquippedInventory();
        int bodyPart = ItemConstants.getBodyPartFromItem(id, getChr().getAvatarData().getAvatarLook().getGender());
        Item oldEquip = equipInv.getItemBySlot(bodyPart);
        if (oldEquip != null) {
            newEquip.setOptions(new ArrayList<>(((Equip) oldEquip).getOptions()));
            getChr().consumeItemFull(oldEquip);
        }
        newEquip.setBagIndex(bodyPart);
        getChr().equip(newEquip, bodyPart);
        newEquip.updateToChar(getChr());
    }

    public String enumerateInventory(InvType invType) {
        var inv = getChr().getInventoryByType(invType);

        StringBuilder sb = new StringBuilder();
        inv.sortItemsByIndex();
        for (var item : inv.getItemsNotInBag().stream().filter(i -> !i.isBagItemOwner()).collect(Collectors.toList())) {
            sb.append(String.format("#b#L%d##i%d##t%dl#\r\n", item.getBagIndex(), item.getItemId(), item.getItemId()));
        }
        sb.append("#k");

        return sb.toString();
    }

    public String enumerateList(String[] strings) {
        var sb = new StringBuilder();
        var i = 0;
        for (var str : strings) {
            sb.append(String.format("#b#L%d#%s#l\r\n", i++, str));
        }
        sb.append("#k");
        return sb.toString();
    }

    @Override
    public boolean hasItem(int id) {
        return hasItem(id, 1);
    }

    @Override
    public boolean isEquipped(int id) {
        return getChr().getInventoryByType(InvType.EQUIPPED).getItems().stream()
                .anyMatch(item -> item.getItemId() == id);
    }

    @Override
    public boolean hasItem(int id, int quantity) {
        return getQuantityOfItem(id) >= quantity;
    }

    public void consumeItem() {
        consumeItem(getScriptInfoByType(ScriptType.Item).getParentID());
    }

    @Override
    public void consumeItem(int itemID) {
        boolean had = !ItemConstants.isEquip(itemID) && getChr().hasItem(itemID);
        getChr().consumeItem(itemID, 1);
        if (had) {
            getChr().giveUseCharmExp(itemID, 1); // a coupon used by a script (e.g. a hair salon)
        }
        String itemName = StringData.getItemStringById(itemID);
        if (itemName != null) {
            getChr().chatMessage(GameDesc, String.format("You've lost an item: %s.", itemName));
        }
    }

    @Override
    public void consumeItem(int itemID, int amount) {
        int had = ItemConstants.isEquip(itemID) ? 0 : getChr().getInventoryByType(
                ItemConstants.getInvTypeByItemID(itemID)).getQuantity(itemID);
        getChr().consumeItem(itemID, amount);
        getChr().giveUseCharmExp(itemID, Math.min(had, amount)); // coupons used by a script (e.g. a hair salon)
        String itemName = StringData.getItemStringById(itemID);
        if (itemName != null) {
            getChr().chatMessage(GameDesc, String.format("You've lost items: %s. (-%d)", itemName, amount));
        }
    }

    public void consumeItem(Item item, int amount) {
        getChr().consumeItem(item, amount);
        String itemName = StringData.getItemStringById(item.getItemId());
        if (itemName != null) {
            getChr().chatMessage(GameDesc, String.format("You've lost items: %s. (-%d)", itemName, amount));
        }
    }

    public void unequip(Item item) {
        if (item instanceof Equip) {
            var equip = (Equip) item;
            int oldBagIndex = item.getBagIndex();
            getChr().unequip(equip);
            equip.setBagIndex(getChr().getEquipInventory().getFirstOpenSlot());
            equip.updateToChar(getChr());
            getChr().write(WvsContext.inventoryOperation(true, false, InventoryOperation.Move,
                    (short) -oldBagIndex, (short) equip.getBagIndex(), 0, equip));
            getChr().write(WvsContext.inventoryOperation(true, false, InventoryOperation.Remove,
                    (short) -oldBagIndex, (short) 0, 0, equip));
        }
    }

    @Override
    public void useItem(int id) {
        ItemBuffs.giveItemBuffsFromItemID(getChr(), getChr().getTemporaryStatManager(), id);
    }

    @Override
    public int getQuantityOfItem(int id) {
        var item2 = ItemData.getItemDeepCopy(id);
        var invType = item2.getInvType();
        return getChr().getInventoryByType(invType).getQuantity(id);
    }

    @Override
    public boolean canHold(int id) {
        return getChr().canHoldItem(id, 1);
    }

    @Override
    public boolean canHold(int id, int quantity) {
        return getChr().canHold(id, quantity);
    }

    @Override
    public int getEmptyInventorySlots(InvType invType) {
        return getChr().getInventoryByType(invType).getEmptySlots();
    }

    /** Same, by inventory type number (1 = equip ... 5 = cash), as older scripts pass it. */
    public int getEmptyInventorySlots(int invType) {
        InvType type = InvType.getInvTypeByVal(invType);
        return type == null ? 0 : getEmptyInventorySlots(type);
    }

    public boolean hasAnyFullInventory() {
        for (var inv : getChr().getInventories()) {
            if (inv.isFull()) {
                return true;
            }
        }

        return false;
    }

    public boolean itemHasWantedStats(Map<String, Integer> wantedStats, int bagIndex, boolean bonus) {
        if (wantedStats.isEmpty()) {
            return false;
        }

        InvType invType = InvType.EQUIP;
        if (bagIndex >= 1000) {
            bagIndex = bagIndex % 1000;
            invType = InvType.EQUIPPED;
        }

        Equip equip = (Equip) getChr().getInventoryByType(invType).getItemBySlot(bagIndex);
        for (Map.Entry<String, Integer> entry : wantedStats.entrySet()) {
            BaseStat bs = BaseStat.getBaseStatByName(entry.getKey());
            int wantedAmount = entry.getValue();
            int amount = equip.getPotentialStat(bs, bonus);
            switch (bs) {
                case str:
                case dex:
                case inte:
                case luk:
                    amount += equip.getPotentialStat(BaseStat.allStat, bonus);
                    break;
                case strR:
                case dexR:
                case intR:
                case lukR:
                    amount += equip.getPotentialStat(BaseStat.allStatR, bonus);
                    break;
                case allStat:
                    int minAmount = equip.getPotentialStat(BaseStat.str, bonus);
                    for (BaseStat stat : BaseStat.getMainStats()) {
                        int statAmount = equip.getPotentialStat(stat, bonus);
                        if (statAmount < minAmount) {
                            minAmount = statAmount;
                        }
                    }
                    amount = minAmount;
                    break;
                case allStatR:
                    minAmount = equip.getPotentialStat(BaseStat.strR, bonus);
                    for (BaseStat stat : BaseStat.getMainStatsPerc()) {
                        int statAmount = equip.getPotentialStat(stat, bonus);
                        if (statAmount < minAmount) {
                            minAmount = statAmount;
                        }
                    }
                    amount = minAmount;
                    break;
            }
            if (amount < wantedAmount) {
                return false;
            }
        }
        return true;
    }

    public void batchRemoveItems(List<Item> items) {
        InventoryModule.removeItems(chr, items.stream().collect(Collectors.toMap(item -> item, Item::getQuantity)));
    }


    // Quest-related methods -------------------------------------------------------------------------------------------

    @Override
    public boolean completeQuest(int questID) {
        return hasQuest(questID) && isComplete(questID) && completeQuestNoCheck(questID);
    }

    @Override
    public boolean completeQuestNoCheck(int questID) {
        return getChr().getQuestManager().completeQuest(questID);
    }

    @Override
    public void completeQuestNoRewards(int id) {
        QuestManager qm = getChr().getQuestManager();
        Quest quest = qm.getQuestById(id);
        if (quest == null) {
            quest = QuestData.createQuestFromId(id);
        }
        quest.setCompletedTime(FileTime.currentTime());
        quest.setStatus(QuestStatus.Completed);
        qm.addQuest(quest);
        qm.checkAndAddCompletedAccountQuest(id);
        qm.sendRecord(quest);

//        chr.chatMessage(String.format("Quest %d completed by completeQuestNoRewards", id));
    }

    @Override
    public void startQuestNoCheck(int id) {
        QuestManager qm = getChr().getQuestManager();
        qm.addQuest(QuestData.createQuestFromId(id));
//        chr.chatMessage(String.format("Quest %d started by startQuestNoCheck", id));
    }

    @Override
    public void startQuest(int id) {
        QuestManager qm = getChr().getQuestManager();
        if (qm.canStartQuestFromScript(id)) {
            qm.addQuest(QuestData.createQuestFromId(id));
        } else if (refusalDue(id)) {
            chr.chatMessage("You don't fit the requirements to start this quest, if you think this is unintended, please report it to a GM.");
        }
    }

    /** Starts the quest when its requirements are met, without a message otherwise (map-entry auto starts). */
    public boolean startQuestIfAble(int id) {
        QuestManager qm = getChr().getQuestManager();
        if (!qm.canStartQuestFromScript(id)) {
            return false;
        }
        qm.addQuest(QuestData.createQuestFromId(id));
        return true;
    }

    private final Map<Integer, Long> lastRefusal = new ConcurrentHashMap<>();

    /** The refusal message at most once a minute per quest: scripts run on every map entry would repeat it. */
    private boolean refusalDue(int questId) {
        long now = System.currentTimeMillis();
        Long last = lastRefusal.get(questId);
        if (last != null && now - last < 60_000) {
            return false;
        }
        lastRefusal.put(questId, now);
        return true;
    }

    @Override
    public boolean hasQuest(int id) {
        return getChr().getQuestManager().hasQuestInProgress(id);
    }

    @Override
    public boolean hasQuestCompleted(int id) {
        return getChr().getQuestManager().hasQuestCompleted(id);
    }

    /**
     * The official dialogue (Quest.wz/Say.img) of a quest whose script doesn't exist, for quest/undefined.py.
     *
     * @param part   0 = start, 1 = end
     * @param branch "say", "yes" or "no"
     */
    public java.util.List<net.swordie.ms.loaders.QuestSayData.Line> getQuestSay(int questId, int part, String branch) {
        return net.swordie.ms.loaders.QuestSayData.getLines(questId, part, branch);
    }

    private void questSaySpeaker(net.swordie.ms.loaders.QuestSayData.Line line, int npc) {
        final int defaultSpeaker = MenuModule.SPEAKER; // Maple Administrator: lines the player says in quests without an NPC
        if (line.isUserSay()) {
            setSpeakerID(line.getNpcId() > 0 ? line.getNpcId() : npc > 0 ? npc : defaultSpeaker);
            setPlayerAsSpeaker();
        } else if (line.getNpcId() > 0) {
            setSpeakerID(line.getNpcId());
        } else if (npc > 0) {
            setSpeakerID(npc);
        } else {
            // a quest without an NPC: the player's own thoughts
            setSpeakerID(defaultSpeaker);
            setPlayerAsSpeaker();
        }
    }

    private int questSayNpc(int questId, int part, int npc) {
        if (npc > 0) {
            return npc;
        }
        net.swordie.ms.loaders.containerclasses.QuestInfo qi = QuestData.getQuestInfoById(questId);
        return qi == null ? 0 : part == 0 ? qi.getStartNpc() : qi.getEndNpc();
    }

    private void questSayLines(java.util.List<net.swordie.ms.loaders.QuestSayData.Line> lines, int npc, boolean okayLast) {
        for (int i = 0; i < lines.size(); i++) {
            questSaySpeaker(lines.get(i), npc);
            if (okayLast && i == lines.size() - 1) {
                sendSayOkay(lines.get(i).getText());
            } else {
                sendNext(lines.get(i).getText());
            }
        }
    }

    /**
     * Starts a quest the official way: plays its Quest.wz/Say.img start dialogue (the last line asks to accept when
     * the quest has an accept/decline dialogue), starts the quest, then plays the lines said after accepting.
     *
     * @param npc the speaking NPC, or 0 for the quest's start NPC
     * @return whether the quest is (now) in progress
     */
    public boolean questStartDialogue(int questId, int npc) {
        if (hasQuest(questId)) {
            return true;
        }
        if (hasQuestCompleted(questId)) {
            return false;
        }
        npc = questSayNpc(questId, 0, npc);
        var say = net.swordie.ms.loaders.QuestSayData.getLines(questId, 0, "say");
        var yes = net.swordie.ms.loaders.QuestSayData.getLines(questId, 0, "yes");
        var no = net.swordie.ms.loaders.QuestSayData.getLines(questId, 0, "no");
        if (!say.isEmpty()) {
            questSayLines(say.subList(0, say.size() - 1), npc, false);
            var last = say.get(say.size() - 1);
            questSaySpeaker(last, npc);
            if (!yes.isEmpty() || !no.isEmpty()) {
                if (!sendAskAccept(last.getText())) {
                    questSayLines(no, npc, true);
                    return false;
                }
            } else {
                sendNext(last.getText());
            }
        }
        startQuest(questId);
        if (!hasQuest(questId)) {
            return false;
        }
        questSayLines(yes, npc, true);
        return true;
    }

    /** {@link #questStartDialogue(int, int)} with the quest's start NPC. */
    public boolean questStartDialogue(int questId) {
        return questStartDialogue(questId, 0);
    }

    /**
     * Completes a quest the official way: plays its Quest.wz/Say.img end dialogue, completes it (with its rewards),
     * then plays the lines said after completing. Nothing is said if the quest's requirements aren't met.
     *
     * @param npc the speaking NPC, or 0 for the quest's end NPC
     * @return whether the quest was completed
     */
    public boolean questEndDialogue(int questId, int npc) {
        if (!hasQuest(questId) || !isComplete(questId)) {
            return false;
        }
        npc = questSayNpc(questId, 1, npc);
        questSayLines(net.swordie.ms.loaders.QuestSayData.getLines(questId, 1, "say"), npc, false);
        if (!completeQuest(questId)) {
            return false;
        }
        questSayLines(net.swordie.ms.loaders.QuestSayData.getLines(questId, 1, "yes"), npc, true);
        return true;
    }

    /**
     * The official stop lines (Quest.wz/Say.img) of a quest the NPC can't start or hand in: the stop/[reason] branch,
     * else stop/default, the plain stop branch, stop/npc, or any other stop branch.
     *
     * @param part   0 = start, 1 = end
     * @param reason npc, item, mob, quest, info or default (QuestManager#stopReason); null to look it up
     * @return the lines (empty if the quest has none)
     */
    public java.util.List<net.swordie.ms.loaders.QuestSayData.Line> questStopLines(int questId, int part, int npc,
                                                                                 String reason) {
        if (reason == null) {
            reason = getChr().getQuestManager().stopReason(questId, part, npc);
        }
        java.util.List<String> order = new java.util.ArrayList<>();
        if (reason != null) {
            order.add("stop/" + reason);
        }
        java.util.Collections.addAll(order, "stop/default", "stop", "stop/npc", "stop/item", "stop/mob",
                "stop/quest", "stop/info");
        for (String branch : order) {
            var lines = net.swordie.ms.loaders.QuestSayData.getLines(questId, part, branch);
            if (!lines.isEmpty()) {
                return lines;
            }
        }
        return java.util.Collections.emptyList();
    }

    /**
     * Plays {@link #questStopLines}: what the NPC says when the quest can't be started or handed in yet.
     *
     * @return whether anything was said
     */
    public boolean questStopDialogue(int questId, int part, int npc, String reason) {
        var lines = questStopLines(questId, part, npc, reason);
        if (lines.isEmpty()) {
            return false;
        }
        questSayLines(lines, questSayNpc(questId, part, npc), true);
        return true;
    }

    public boolean questStopDialogue(int questId, int part, int npc) {
        return questStopDialogue(questId, part, npc, null);
    }

    /** {@link #questEndDialogue(int, int)} with the quest's end NPC. */
    public boolean questEndDialogue(int questId) {
        return questEndDialogue(questId, 0);
    }

    public boolean hasHadQuest(int id) {
        return hasQuest(id) || hasQuestCompleted(id);
    }

    public void createQuestWithQRValue(int questId, String qrValue, boolean ex) {
        createQuestWithQRValue(getChr(), questId, qrValue, ex);
    }

    public void createQuestWithQRValue(int questId, String qrValue) {
        createQuestWithQRValue(getChr(), questId, qrValue, true);
    }

    public void createQuestWithQRValue(Char chr, int questId, String qrValue) {
        createQuestWithQRValue(chr, questId, qrValue, true);
    }

    public void createQuestWithQRValue(Char character, int questId, String qrValue, boolean ex) {
        QuestManager qm = character.getQuestManager();
        Quest quest = qm.getQuestById(questId);
        if (quest == null) {
            quest = QuestData.createQuestFromId(questId);
            quest.setQrValue(qrValue);
            qm.addCustomQuest(quest);
        }
        quest.setQrValue(qrValue);
        updateQRValue(questId, ex);
    }

    public void deleteQuest(int questId) {
        deleteQuest(getChr(), questId);
    }

    public void deleteQuest(Char chr, int questId) {
        QuestManager qm = chr.getQuestManager();
        Quest quest = qm.getQuestById(questId);
        if (quest == null) {
            return;
        }
        qm.removeQuest(quest.getQRKey());
    }

    public String getQRValue(int questId) {
        return getQRValue(getChr(), questId);
    }

    public String getQRValue(int questId, String questKey) {
        Quest quest = getChr().getQuestManager().getQuestById(questId);
        if (quest == null) {
            return "";
        }
        return quest.getProperty(questKey);
    }

    public String getQRValue(Char chr, int questId) {
        Quest quest = chr.getQuestManager().getQuestById(questId);
        if (quest == null) {
            return "";
        }
        return quest.getQRValue();
    }

    public boolean hasQuestWithValue(int qrKey, String str) {
        Quest quest = getChr().getQuestManager().getQuestById(qrKey);
        if (quest == null) {
            return false;
        }
        return quest.getQRValue().contains(str);
    }

    public void setQRValue(int questId, String qrValue) {
        setQRValue(questId, qrValue, true);
    }

    public void setQRValue(int questId, String key, String value) {
        QuestManager qm = getChr().getQuestManager();
        Quest quest = qm.getQuestById(questId);
        if (quest == null) {
            quest = QuestData.createQuestFromId(questId);
            qm.addQuest(quest);
        }
        quest.setProperty(key, value);
        getChr().write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
    }

    public void setQRValue(int questId, String qrValue, boolean ex) {
        setQRValue(getChr(), questId, qrValue, ex);
    }

    public void setQRValue(Char chr, int questId, String qrValue, boolean ex) {
        Quest quest = chr.getQuestManager().getQuestById(questId);
        if (quest == null) {
            quest = QuestData.createQuestFromId(questId);
            chr.getQuestManager().addQuest(quest);
        }
        quest.setQrValue(qrValue);
        updateQRValue(questId, ex);
    }

    public void addQRValue(int questId, String qrValue) {
        addQRValue(questId, qrValue, true);
    }

    public void addQRValue(int questId, String qrValue, boolean ex) {
        String qrVal = getQRValue(questId);
        if (qrVal.equals("")) {
            createQuestWithQRValue(questId, qrValue);
            return;
        }
        setQRValue(questId, qrValue + ";" + qrVal);
        updateQRValue(questId, ex);
    }

    // ---- Rebirth (RebirthModule), used by the Lawry NPC script ----

    public int getRebirthCount() {
        return net.swordie.ms.client.character.RebirthModule.getRebirthCount(getChr());
    }

    /**
     * @return why the Char can't be reborn right now, or null if it can
     */
    public String getRebirthBlocker() {
        return net.swordie.ms.client.character.RebirthModule.getRebirthBlocker(getChr());
    }

    public String getRebirthBlockerIgnoringEquips() {
        return net.swordie.ms.client.character.RebirthModule.getRebirthBlockerIgnoringEquips(getChr());
    }

    /**
     * @return the names of the equipped items that must come off before a rebirth
     */
    public java.util.List<String> getRebirthBlockingEquipNames() {
        java.util.List<String> res = new java.util.ArrayList<>();
        for (var equip : net.swordie.ms.client.character.RebirthModule.getEquipsBlockingRebirth(getChr())) {
            String name = StringData.getItemStringById(equip.getItemId());
            res.add(name == null ? String.valueOf(equip.getItemId()) : name);
        }
        return res;
    }

    /**
     * @return null if the blocking equipment was unequipped, or why it wasn't
     */
    public String unequipForRebirth() {
        return net.swordie.ms.client.character.RebirthModule.unequipForRebirth(getChr());
    }

    public boolean rebirth() {
        return net.swordie.ms.client.character.RebirthModule.rebirth(getChr());
    }

    // Job paths (@job, see JobPathModule) --------------------------------------------------------------------------

    public java.util.List<net.swordie.ms.client.character.JobPathModule.ClassChoice> getJobPathChoices() {
        return net.swordie.ms.client.character.JobPathModule.getChoices(getChr());
    }

    public String[] getJobPathGroups() {
        return net.swordie.ms.client.character.JobPathModule.GROUPS;
    }

    public net.swordie.ms.client.character.JobPathModule.ClassChoice getPreviousClass() {
        return net.swordie.ms.client.character.JobPathModule.getPreviousClass(getChr());
    }

    public boolean isBeginnerJob() {
        return JobConstants.isBeginnerJob(getChr().getJob());
    }

    public String getJobPathStatus() {
        return net.swordie.ms.client.character.JobPathModule.getStatus(getChr());
    }

    /**
     * @return a message for the player: what happened, or why it didn't
     */
    public String chooseJobPath(int finalJob) {
        return net.swordie.ms.client.character.JobPathModule.choosePath(getChr(), finalJob);
    }

    public boolean isComplete(int questID) {
        return getChr().getQuestManager().isComplete(questID);
    }

    // Arcane River story skip (Chief Legato, see ArcaneRiverSkipModule) ---------------------------------------------

    public java.util.List<net.swordie.ms.client.character.ArcaneRiverSkipModule.Area> getArcaneRiverSkipAreas() {
        return net.swordie.ms.client.character.ArcaneRiverSkipModule.getSkippableAreas(getChr());
    }

    public java.util.List<net.swordie.ms.client.character.ArcaneRiverSkipModule.Area> getArcaneRiverAreas() {
        return net.swordie.ms.client.character.ArcaneRiverSkipModule.AREAS;
    }

    public boolean hasFifthJob() {
        return net.swordie.ms.client.character.ArcaneRiverSkipModule.hasFifthJob(getChr());
    }

    /**
     * @return a message for the player: what was skipped, or why nothing was
     */
    public String skipArcaneRiver(int lastAreaIndex) {
        return net.swordie.ms.client.character.ArcaneRiverSkipModule.skip(getChr(), lastAreaIndex);
    }

    public boolean isSpiegelmannShown() {
        return net.swordie.ms.client.character.ArcaneRiverSkipModule.isSpiegelmannShown(getChr());
    }

    /** Nameless Town's enter script: Chief Legato's one-time offer to skip the Vanishing Journey story */
    public void offerArcaneSkipOnArrival() {
        net.swordie.ms.client.character.ArcaneRiverSkipModule.offerOnArrival(getChr());
    }

    /** @return the area the open level-up prompt offers to go to, or null if it's no longer open */
    public net.swordie.ms.client.character.ArcaneRiverSkipModule.Area getArcaneSkipPromptArea() {
        return net.swordie.ms.client.character.ArcaneRiverSkipModule.getPromptArea(getChr());
    }

    public net.swordie.ms.client.character.ArcaneRiverSkipModule.Area getArcaneSkipPromptSkippedArea() {
        return net.swordie.ms.client.character.ArcaneRiverSkipModule.getPromptSkippedArea(getChr());
    }

    /** @return "Arcane River" or "Grandis" for the open level-up prompt, or null */
    public String getArcaneSkipPromptRegion() {
        return net.swordie.ms.client.character.ArcaneRiverSkipModule.getPromptRegion(getChr());
    }

    public String acceptArcaneSkipPrompt() {
        return net.swordie.ms.client.character.ArcaneRiverSkipModule.acceptPrompt(getChr());
    }

    public void endArcaneSkipPrompt() {
        net.swordie.ms.client.character.ArcaneRiverSkipModule.endPrompt(getChr());
    }

    private static volatile Map<Integer, List<String>> npcDefaultLines;

    /**
     * The default dialogue of an NPC (String.wz/Npc.img d0, d1, ...), read once from the file the server makes from
     * the game data (StringData.NPC_DEFAULT_LINES_FILE, lines of "npc id TAB text"); an older
     * scripts/npc/npc_default_lines.txt is used while that one doesn't exist yet.
     *
     * @param npcID the NPC's template id
     * @return the NPC's default lines, empty if it has none
     */
    public static List<String> getNpcDefaultLines(int npcID) {
        if (npcDefaultLines == null) {
            synchronized (ScriptManagerImpl.class) {
                if (npcDefaultLines == null) {
                    Map<Integer, List<String>> lines = new HashMap<>();
                    File file = new File(StringData.NPC_DEFAULT_LINES_FILE);
                    if (!file.exists()) {
                        file = new File(ServerConstants.SCRIPT_DIR + "/npc/npc_default_lines.txt");
                    }
                    if (file.exists()) {
                        try {
                            for (String line : java.nio.file.Files.readAllLines(file.toPath(),
                                    java.nio.charset.StandardCharsets.UTF_8)) {
                                int tab = line.indexOf('\t');
                                if (line.startsWith("#") || tab <= 0) {
                                    continue;
                                }
                                try {
                                    int id = Integer.parseInt(line.substring(0, tab).trim());
                                    String text = line.substring(tab + 1).replace("\\n", "\r\n");
                                    lines.computeIfAbsent(id, k -> new ArrayList<>()).add(text);
                                } catch (NumberFormatException ignored) {
                                }
                            }
                        } catch (IOException e) {
                            log.error("Could not read " + file.getPath(), e);
                        }
                    }
                    npcDefaultLines = lines;
                }
            }
        }
        return npcDefaultLines.getOrDefault(npcID, Collections.emptyList());
    }

    /**
     * @param npcID the NPC's template id
     * @return one of the NPC's default lines picked at random, or null if it has none
     */
    public String getRandomNpcDefaultLine(int npcID) {
        List<String> lines = getNpcDefaultLines(npcID);
        return lines.isEmpty() ? null : Util.getRandomFromCollection(lines);
    }

    /**
     * Completes a quest the Char has in progress and gives its Quest.wz rewards, if its completion requirements
     * (Check.img/1) are met and the item rewards fit in the inventory.
     *
     * @param questID the quest to complete
     * @return whether the quest got completed
     */
    public boolean tryCompleteQuest(int questID) {
        QuestManager qm = getChr().getQuestManager();
        if (!qm.isComplete(questID) || !qm.meetsCompleteRequirements(questID)) {
            return false;
        }
        return qm.completeQuest(questID);
    }

    public void updateQRValue(int questId, boolean ex) {
        Quest quest = getChr().getQuestManager().getQuestById(questId);
        if (quest == null) {
            log.error(String.format("The user does not have the quest %d.", questId));
            return;
        }
        if (ex) {
            getChr().write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
        } else {
            getChr().write(WvsContext.message(MessagePacket.questRecordMessage(quest)));
        }
    }

    /** @return today's reset day as yy/MM/dd (turns at the daily reset) */
    public String getCurrentDateAsString() {
        return net.swordie.ms.util.ResetClock.todayYYMMDD();
    }

    /** @return today's reset day in a java DateTimeFormatter pattern, e.g. "yyyy-MM-dd" */
    public String getResetDay(String pattern) {
        return net.swordie.ms.util.ResetClock.today(pattern);
    }


    // Party Quest-related methods -------------------------------------------------------------------------------------

    public String getDay() {
        return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
    }

    public int getMPExpByMobId(int templateId) {
        return MonsterPark.getExpByMobId(templateId);
    }

    public int getMPReward() {
        return MonsterPark.getRewardByDay();
    }

    public long getPQExp() {
        return getPQExp(getChr());
    }

    public long getPQExp(Char chr) {
        return GameConstants.PARTY_QUEST_EXP_FORMULA(chr);
    }

    // true if everyone is good. false is someone is above max floors per day
    public boolean checkOzFloorRequirement(Char chr, Party party) {
        if (party == null) {
            var q = net.swordie.ms.client.partyquests.towerofoz.OzTowerModule.record(chr, QuestConstants.TOWER_OF_OZ_DAILY_CLEARS);
            return q.getIntProperty("tf") < OzConstants.MAX_FLOORS_PER_DAY;
        } else {
            for (var c : party.getPartyMembersInField(chr)) {
                var q = net.swordie.ms.client.partyquests.towerofoz.OzTowerModule.record(c, QuestConstants.TOWER_OF_OZ_DAILY_CLEARS);
                if (q.getIntProperty("tf") >= OzConstants.MAX_FLOORS_PER_DAY) {
                    return false;
                }
            }
            return true;
        }
    }


    // Boss-related methods --------------------------------------------------------------------------------------------

    @Override
    public void setDeathCount(int deathCount) {
        Instance info = getChr().getInstance();
        if (info == null) {
            getChr().setDeathCount(deathCount);
            getChr().write(UserLocal.deathCountInfo(deathCount));
        } else {
            for (Char chr : info.getChars()) {
                chr.setDeathCount(deathCount);
                chr.write(UserLocal.deathCountInfo(deathCount));
            }
        }
    }

    public void setDeathCount2(int deathCount) {
        Instance info = getChr().getInstance();
        if (info == null) {
            getChr().setDeathCount(deathCount);
            getChr().write(UserLocal.deathCountInfo2(deathCount));
        } else {
            for (Char chr : info.getChars()) {
                chr.setDeathCount(deathCount);
                chr.write(UserLocal.deathCountInfo2(deathCount));
            }
        }
    }

    public void createObstacleAtom(ObstacleAtomEnum oae, int key, int damage, int velocity, int amount, int proc) {
        createObstacleAtom(oae, key, damage, velocity, 0, amount, proc);
    }

    @Override
    public void createObstacleAtom(ObstacleAtomEnum oae, int key, int damage, int velocity, int angle, int amount, int proc) {
        Field field = getField();
        var fieldInfo = field.getInfo();
        int xLeft = fieldInfo.getVrLeft();
        int yTop = fieldInfo.getVrTop();

        ObstacleInRowInfo obstacleInRowInfo = new ObstacleInRowInfo(4, false, 5000, 0, 0, 0);
        ObstacleRadianInfo obstacleRadianInfo = new ObstacleRadianInfo(4, 0, 0, 0, 0);
        Set<ObstacleAtomInfo> obstacleAtomInfosSet = new HashSet<>();

        for (int i = 0; i < amount; i++) {
            if (Util.succeedProp(proc)) {
                int randomX = new Random().nextInt(fieldInfo.getWidth()) + xLeft;
                Position position = new Position(randomX, yTop);
                Foothold foothold = field.findFootHoldBelow(position);
                if (foothold != null) {
                    int footholdY = foothold.getYFromX(position.getX());
                    int height = position.getY() - footholdY;
                    height = height < 0 ? -height : height;

                    obstacleAtomInfosSet.add(new ObstacleAtomInfo(oae.getType(), key, position, new Position(), oae.getHitBox(),
                            damage, 0, 0, height, 0, velocity, height, angle));
                }
            }
        }

        field.broadcastPacket(FieldPacket.createObstacle(ObstacleAtomCreateType.NORMAL, obstacleInRowInfo, obstacleRadianInfo, obstacleAtomInfosSet));
    }

    public void createObstacleAtomToFoothold(ObstacleAtomEnum oae, Rect allowedFhsRect, int damage, int velocity, int angle, int amount, int chance) {
        var oact = ObstacleAtomCreateType.NORMAL;

        ObstacleInRowInfo oiri = null;
        ObstacleRadianInfo ori = null;

        var field = getField();
        if (field == null) {
            return;
        }
        var allowedFhs = field.getNonWallFootholdsWithinRect(allowedFhsRect);

        Set<ObstacleAtomInfo> atomInfos = ObstacleAtomFactory.createObstacleAtoms(oae, allowedFhs,
                field.getInfo().getVrTop(), damage, velocity, angle, chance, amount);

        getChr().write(FieldPacket.createObstacle(oact, oiri, ori, atomInfos));
    }

    @Override
    public void stopFieldEvents() {
        var events = getEvents();
        if (events.size() <= 0) {
            return;
        }

        var removedTimers = new HashSet<ScheduledFuture>();
        for (var entry : events.entrySet()) {
            var st = entry.getKey();
            var isFieldEvent = entry.getValue();
            if (isFieldEvent) {
                EventManager.stopTimer(st);
                removedTimers.add(st);
            }
        }

        for (var st : removedTimers) {
            events.remove(st);
        }
    }

    public void stopEvents() {
        var events = getEvents();
        for (ScheduledFuture st : events.keySet()) {
            EventManager.stopTimer(st);
        }
        events.clear();
        Field field = getField();
        Arrays.stream(ScriptType.values()).forEach(this::stop);
        if (field != null) {
            field.broadcastPacket(FieldPacket.clock(ClockPacket.removeClock()));
        }
        var chr = getChr();
        if (chr != null) {
            chr.write(FieldPacket.updateTemporarySkillBar(0)); // resets special skill bar
            chr.getTemporaryStatManager().removeStatsBySkill(237); // Damien Stigma
        }
    }

    private Map<ScheduledFuture, Boolean> getEvents() {
        return events;
    }

    public void addEvent(ScheduledFuture event) {
        addEvent(event, false);
    }

    public void addEvent(ScheduledFuture event, boolean isFieldEvent) {
        if (event != null) { // ConcurrentHashMap: no null keys
            getEvents().put(event, isFieldEvent);
        }
    }

    public void showGolluxMiniMap() {
        if (getChr() == null || getChr().getInstance() == null) {
            return; // out of the Gollux instance already (a field script running after a warp out)
        }
        Map<String, GolluxMiniMapFieldClearType> gFieldMap = new HashMap<>();
        gFieldMap.put("clearType", GolluxMiniMapFieldClearType.Defeated);

        for (int gFieldId : GOLLUX_MAPS.get()) {
            Field gField = getChr().getInstance().getFields().get(gFieldId);
            if (gField == null) {
                continue;
            }

            if (gField.hasProperty("Killed") && (boolean) gField.getProperty("Killed")) {
                gFieldMap.put(gFieldId + "", GolluxMiniMapFieldClearType.Defeated);
            } else if (gField.hasProperty("Spawned") && (boolean) gField.getProperty("Spawned")) {
                gFieldMap.put(gFieldId + "", GolluxMiniMapFieldClearType.Attacked);
            } else {
                gFieldMap.put(gFieldId + "", GolluxMiniMapFieldClearType.Unvisited);
            }
        }

        getChr().getInstance().broadcast(GiantBossFieldPacket.golluxMiniMap(gFieldMap));
    }

    public void golluxPortalOpen(String happeningName) {
        getField().broadcastPacket(GiantBossFieldPacket.golluxPortalOpen(happeningName));
    }


    // Union-related methods -------------------------------------------------------------------------------------------

    public int getUnionCoin() {
        return getChr().getUnion().getUnionCoin();
    }

    public void addUnionCoin(int amount, boolean fromRaid) {
        if (fromRaid) {
            if (chr.getUnionRaid() != null) {
                chr.getUnionRaid().addUnclaimedCoins(chr, -amount);
            } else {
                return;
            }
        }
        Union union = getChr().getUnion();
        union.addUnionCoin(amount);
    }

    public int getUnionRank() {
        return getChr().getUnion().getUnionRank();
    }

    private int getUnionRankIndex() {
        int high = getUnionRank() / 100;
        int low = getUnionRank() % 100;
        return (low - 1) + (high - 1) * 5;
    }

    private boolean isMaxUnionRank() {
        return getUnionRank() == 505; // Supreme V
    }

    public String getUnionRankName() {
        return getChr().getUnion().getUnionRankName();
    }

    public String getUnionNextRankName() {
        return getChr().getUnion().getUnionNextRankName();
    }

    public boolean isMaxUnionRankReached() {
        return getChr().getUnion().isMaxRank();
    }

    /** Legion Coins needed to rank up (0 at the highest rank; the old version crashed there). */
    public int getUnionCoinReq() {
        return getChr().getUnion().getRankUpCoinReq();
    }

    /** Legion level needed to rank up (0 at the highest rank; the old version crashed there). */
    public int getUnionLevelReq() {
        return getChr().getUnion().getRankUpLevelReq();
    }

    /** The Legion level: the 40 highest level eligible characters (one Zero) added up, like on live servers. */
    public int getUnionLevel() {
        return getChr().getUnion().getUnionLevel();
    }

    public int getUnionCharacterCount() {
        return getChr().getUnion().getEligibleUnionChars().size();
    }

    public int getUnionAssignedCharacterCount() {
        var active = getChr().getUnion().getActiveUnionChars(getChr().getActiveUnionPreset());
        return active == null ? 0 : active.size();
    }

    public int getUnionAssignedMaxCharacterCount() {
        return getChr().getUnion().getAttackerCount();
    }

    public int getUnionAssignedNextMaxCharacterCount() {
        int idx = getUnionRankIndex();
        if (isMaxUnionRank() || idx < 0) {
            return getUnionAssignedMaxCharacterCount();
        }
        return UnionMember.attackerCount[Math.min(idx + 1, UnionMember.attackerCount.length - 1)];
    }

    public void incrementUnionRank() {
        getChr().incrementUnionRank();
    }

    public void spawnDragonWhelps() {
        if (FieldConstants.isUnionRaidField(getFieldID()) && getChr().getInstance() != null) {
            Field field = getField();

            for (int i = 0; i < 10; i++) {
                if (field.getMobs().size() > 15) {
                    continue;
                }

                int dragon = Util.getRandomFromCollection(UnionRaid.mobs);
                Position position = Util.getRandomFromCollection(UnionRaid.positions);

                if (Util.succeedProp(5) && i == 5) {
                    dragon = GOLDEN_WYVERN_MOB; // Golden Whelp
                }
                Mob mob = field.spawnMob(dragon, position.getX(), position.getY(), false, 50_000_000L);
                if(getChr() != null) {
                    mob.setLevel(getChr().getLevel());
                }
            }
        }
    }


    // Character Temporary Stat-related methods ------------------------------------------------------------------------

    @Override
    public void giveCTS(CharacterTemporaryStat cts, int nOption, int rOption, int time) {
        TemporaryStatManager tsm = getChr().getTemporaryStatManager();
        Option o = new Option();
        o.nOption = nOption;
        o.rOption = rOption;
        o.tOption = time;
        tsm.putCharacterStatValue(cts, o);
        tsm.sendSetStatPacket();
    }

    @Override
    public void removeCTS(CharacterTemporaryStat cts) {
        TemporaryStatManager tsm = getChr().getTemporaryStatManager();
        tsm.removeStat(cts);
    }

    @Override
    public void removeBuffBySkill(int skillId) {
        TemporaryStatManager tsm = getChr().getTemporaryStatManager();
        tsm.removeStatsBySkill(skillId);
    }

    @Override
    public boolean hasCTS(CharacterTemporaryStat cts) {
        TemporaryStatManager tsm = getChr().getTemporaryStatManager();
        return tsm.hasStat(cts);
    }

    @Override
    public int getnOptionByCTS(CharacterTemporaryStat cts) {
        TemporaryStatManager tsm = getChr().getTemporaryStatManager();
        return hasCTS(cts) ? tsm.getOption(cts).nOption : 0;
    }

    @Override
    public void rideVehicle(int mountID) {
        TemporaryStatManager tsm = getChr().getTemporaryStatManager();
        TemporaryStatBase tsb = tsm.getTSBByTSIndex(TSIndex.RideVehicle);

        tsb.setNOption(mountID);
        tsb.setROption(0);
        tsm.putCharacterStatValue(RideVehicle, tsb.getOption());
        tsm.sendSetStatPacket();
    }

    public void consumeLiver() {
        if (JobConstants.isShade(chr.getJob())) {
            ((Shade) chr.getJobHandler()).extendSpiritBondMax();
        }
    }


    // InGameDirectionEvent methods ------------------------------------------------------------------------------------

    @Override
    public int moveCamera(boolean back, int speed, int x, int y) {
        getNpcScriptInfo().setMessageType(NpcMessageType.AskIngameDirection);
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.cameraMove(back, speed, new Position(x, y))));
        Object response = null;
        var lastActiveScriptType = getLastActiveScriptType();
        if (isActive(lastActiveScriptType)) {
            response = getScriptInfoByType(lastActiveScriptType).awaitResponse();
        }
        if (response == null) {
            throw new NullPointerException(INTENDED_NPE_MSG);
        }
        return (int) response;
    }

    public void moveCamera(int speed, int x, int y) {
        moveCamera(false, speed, x, y);
    }

    public void moveCameraBack(int speed) {
        moveCamera(true, speed, getChr().getPosition().getX(), getChr().getPosition().getY());
    }

    public void zoomCameraNoResponse(int time, int scale, int timePos, int x, int y) {
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.cameraZoom(time, scale, timePos, new Position(x, y))));
    }

    public int zoomCamera(int time, int scale, int timePos, int x, int y) {
        getNpcScriptInfo().setMessageType(NpcMessageType.AskIngameDirection);
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.cameraZoom(time, scale, timePos, new Position(x, y))));
        Object response = null;
        var lastActiveScriptType = getLastActiveScriptType();
        if (isActive(lastActiveScriptType)) {
            response = getScriptInfoByType(lastActiveScriptType).awaitResponse();
        }
        if (response == null) {
            throw new NullPointerException(INTENDED_NPE_MSG);
        }
        return (int) response;
    }

    @Override
    public int zoomCamera(int inZoomDuration, int scale, int x, int y) {
        getNpcScriptInfo().setMessageType(NpcMessageType.AskIngameDirection);
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.cameraZoom(inZoomDuration, scale, 1000, new Position(x, y))));
        Object response = null;
        var lastActiveScriptType = getLastActiveScriptType();
        if (isActive(lastActiveScriptType)) {
            response = getScriptInfoByType(lastActiveScriptType).awaitResponse();
        }
        if (response == null) {
            throw new NullPointerException(INTENDED_NPE_MSG);
        }
        return (int) response;
    }

    public void zoomCameraNoResponse(int zoomInDuration, int scale, int x, int y) {
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.cameraZoom(zoomInDuration, scale, 1000, new Position(x, y))));
    }

    @Override
    public void resetCamera() {
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.cameraOnCharacter(0))); // 0 resets the Camera
    }

    public void setCameraOnNpc(int npcTemplateId) {
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.cameraOnCharacter(npcTemplateId)));
    }

    /**
     * GMS boss intros: shows the boss guide page with this key unless the character has seen it (the client keeps
     * that) and waits for the client's answer (when it is closed, 60 s at most; at once if seen).
     */
    public int sendBossGuide(String key) {
        if (sceneSkipping) {
            return 1;
        }
        getNpcScriptInfo().setMessageType(NpcMessageType.AskIngameDirection);
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.bossGuide(key)));
        Object response = null;
        var lastActiveScriptType = getLastActiveScriptType();
        if (isActive(lastActiveScriptType)) {
            response = getScriptInfoByType(lastActiveScriptType).awaitResponse();
        }
        if (response == null) {
            throw new NullPointerException(INTENDED_NPE_MSG);
        }
        return (int) response;
    }

    /**
     * A pause of a locked scene that the server times itself (the client answers nothing for it): ends early at the
     * scene's skip button, and is skipped once the scene is skipping.
     */
    public void sceneWait(int delay) {
        if (sceneSkipping) {
            return;
        }
        var lastActiveScriptType = getLastActiveScriptType();
        if (isActive(lastActiveScriptType)) {
            getScriptInfoByType(lastActiveScriptType).awaitResponse(delay);
        }
    }

    @Override
    public int sendDelay(int delay) {
        if (sceneSkipping) {
            return 1;
        }
        getNpcScriptInfo().setMessageType(NpcMessageType.AskIngameDirection);
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.delay(delay)));
        Object response = null;
        var lastActiveScriptType = getLastActiveScriptType();
        if (isActive(lastActiveScriptType)) {
            response = getScriptInfoByType(lastActiveScriptType).awaitResponse();
        }
        if (response == null) {
            throw new NullPointerException(INTENDED_NPE_MSG);
        }
        return (int) response;
    }

    @Override
    public void doEventAndSendDelay(int delay, String methodName, Object... args) {
        invoke(getChr().getScriptManager(), methodName, args);
        sendDelay(delay);
    }

    @Override
    public void forcedMove(boolean left, int distance) {
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.forcedMove(left, distance)));
    }

    @Override
    public void forcedFlip(boolean left) {
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.forcedFlip(left)));
    }

    @Override
    public void forcedAction(int type, int duration) {
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.forcedAction(type, duration)));
    }

    @Override
    public void forcedInput(int type) {
        ForcedInputType fit = ForcedInputType.getByVal(type);
        if (fit == null) {
            log.error(String.format("Unknown Forced Input Type %d", type));
            return;
        }
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.forcedInput(type)));
    }

    public void patternInputRequest(String pattern, int act, int requestCount, int time) {
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.patternInputRequest(pattern, act, requestCount, time)));
    }

    @Override
    public void hideUser(boolean hide) {
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.vansheeMode(hide)));
    }

    public void showEffect(String path, int duration, int x, int y) {
        showEffect(path, duration, x, y, 0, 0, true, 0);
    }

    @Override
    public void showEffect(String path, int duration, int x, int y, int z, int npcIdForExtend, boolean onUser, int idk2) {
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.effectPlay(path, duration, new Position(x, y), z, npcIdForExtend, onUser, idk2)));
    }

    public void showEffectOnPosition(String path, int duration, int x, int y) {
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.effectPlay(path, duration,
                new Position(x, y), 0, 1, false, 0)));
    }

    public void showBalloonMsgOnNpc(String path, int duration, int x, int y, int templateID) {
        int objectID = getNpcObjectIdByTemplateId(templateID);
        if (objectID == 0) return;
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.effectPlay(path, duration,
                new Position(x, y), 0, objectID, false, 0)));
    }

    public void showBalloonMsgOnNpc(String path, int duration, int templateID) {
        showBalloonMsgOnNpc(path, duration, 0, -100, templateID);
    }

    public void showNpcEffectOnPosition(String path, int x, int y, int templateID) {
        int objectID = getNpcObjectIdByTemplateId(templateID);
        if (objectID == 0) return;
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.effectPlay(path, 0,
                new Position(x, y), 0, objectID, false, 0)));
    }

    public void showBalloonMsg(String path, int duration) {
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.effectPlay(path, duration,
                new Position(0, -100), 0, 0, true, 0)));
    }

    public int sayMonologue(String text, boolean isEnd) {
        getNpcScriptInfo().setMessageType(NpcMessageType.Monologue);
        getNpcScriptInfo().setText(text);
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.monologue(text, isEnd)));
        Object response = null;
        var lastActiveScriptType = getLastActiveScriptType();
        if (isActive(lastActiveScriptType)) {
            response = getScriptInfoByType(lastActiveScriptType).awaitResponse();
        }
        if (response == null) {
            throw new NullPointerException(INTENDED_NPE_MSG);
        }
        return (int) response;
    }

    public void avatarLookSet(int[] equipIDs) {
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.avatarLookSet(equipIDs)));
    }

    public void removeAdditionalEffect() {
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.removeAdditionalEffect()));
    }

    public void faceOff(int faceItemID) {
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.faceOff(faceItemID)));
    }

    public void monologueScroll(String msg, boolean stayModal, short align, int updateSpeedTime, int decTic) {
        getChr().write(UserLocal.inGameDirectionEvent(InGameDirectionEvent.monologueScroll(msg, stayModal, align,
                updateSpeedTime, decTic)));
    }

    // Clock methods ---------------------------------------------------------------------------------------------------

    public Clock createStopWatch(int seconds) {
        return new Clock(ClockType.StopWatch, getField(), seconds);
    }

    public Clock createStopWatchForChrOnly(int seconds) {
        if (chr != null) {
            return new Clock(ClockType.StopWatch, chr, seconds);
        }

        return null;
    }

    public Clock createClock(int seconds) {
        return new Clock(ClockType.SecondsClock, getField(), seconds);
    }

    public void createClock(int hours, int minutes, int seconds) {
        getChr().write(FieldPacket.clock(ClockPacket.hmsClock((byte) hours, (byte) minutes, (byte) seconds)));
        addEvent(EventManager.addEvent(this::removeClock, seconds + minutes * 60 + hours * 3600, TimeUnit.SECONDS));
    }

    public void removeClock() {
        getChr().write(FieldPacket.clock(ClockPacket.removeClock()));
    }

    public void createDojoClock(int seconds) {
        if (getChr().getInstance() == null) {
            return; // out of the Dojo already (a delayed call after leaving)
        }
        if (getChr().getInstance().getClockPacket() == null) {
            getChr().getInstance().setDojoTimer(seconds);
            getChr().getInstance().getField(MU_LUNG_DOJO_ATRIUM_FIELD).setProperty("StartTime", System.currentTimeMillis());
            getChr().getInstance().setForcedReturn(FieldConstants.MU_LUNG_DOJO_EXIT_FIELD);
        }
    }

    public void setDojoClockPaused(boolean isPaused) {
        getChr().write(FieldPacket.clock(ClockPacket.pauseTimer(false, 0)));
    }

    public String getCurClockTime() {
        if (getChr().getInstance().getClockPacket() != null) {
            long time = getChr().getInstance().getClockPacket().getCurDuration();
            long seconds = (time / 1000) % 60;
            long minutes = ((time / 1000) - seconds) / 60;
            return String.format("%d minutes and %d seconds", minutes, seconds);
        }
        return "";
    }

    public long getCurClockTimeSec() {
        long seconds = -1;
        if (getChr().getInstance().getClockPacket() != null) {
            long time = getChr().getInstance().getClockPacket().getCurDuration();
            seconds = (time / 1000);
        }
        return seconds;
    }

    // Other methods ---------------------------------------------------------------------------------------------------

    @Override
    public boolean addDamageSkin(int itemID) {
        return chr.addDamageSkin(itemID);
    }

    @Override
    public void openUI(UIType uiType) {
        int id = uiType.getVal();
        openUI(id);
    }

    public void openUI(int id) {
        getChr().write(FieldPacket.openUI(id));
    }

    @Override
    public void openDimensionalMirror() {
        getChr().write(FieldPacket.openDimensionalMirror());
    }

    @Override
    public void closeUI(UIType uiType) {
        int id = uiType.getVal();
        closeUI(id);
    }

    public void closeUI(int id) {
        getChr().write(FieldPacket.closeUI(id));
    }

    @Override
    public void showClearStageExpWindow(long expGiven) {
        getChr().write(FieldPacket.fieldEffect(FieldEffect.showClearStageExpWindow((int) expGiven)));
        giveExpNoMsg(expGiven);
    }

    public void removeBlowWeather() {
        getChr().write(FieldPacket.removeBlowWeather());
    }

    public void blowWeather(int itemID, String message) {
        removeBlowWeather();// removing old one if exists.
        getChr().write(FieldPacket.blowWeather(itemID, message));
    }

    public void playSound(String sound) {
        playSound(sound, 100);
    }// default

    public void playSound(String sound, int vol) {
        getChr().write(FieldPacket.fieldEffect(FieldEffect.playSound(sound, vol)));
    }

    public void blind(int enable, int x, int color, int time) {
        blind(enable, x, color, 0, 0, time);
    }

    public void blind(int enable, int x, int color, int unk1, int unk2, int time) {
        getChr().write(FieldPacket.fieldEffect(FieldEffect.blind(enable, x, color, unk1, unk2, time)));
    }

    @Override
    public int getRandomIntBelow(int upBound) {
        return random.nextInt(upBound);
    }

    public int getRandomIntBetween(int lowerBound, int inclUpperBound) {
        return random.nextInt((inclUpperBound - lowerBound) + 1) + lowerBound;
    }

    public void showEffect(String dir) {
        showEffect(dir, 0);
    }

    public void showEffect(String dir, int delay) {
        showEffect(dir, 4, delay);
    }

    public void showScene(String xmlPath, String sceneName, String sceneNumber) {
        Scene scene = new Scene(getChr(), xmlPath, sceneName, sceneNumber);
        scene.createScene();
    }

    @Override
    public void showEffect(String dir, int placement, int delay) {
        getChr().write(UserPacket.effect(Effect.effectFromWZ(dir, false, delay, placement, 0)));
    }

    /** An effect over the character for durationMS (GMS tutorial guides, e.g. UI/tutorial.img/Erel/skill). */
    public void avatarOrientedRepeat(String effectPath, int durationMS) {
        getChr().write(UserPacket.effect(Effect.avatarOrientedRepeat(effectPath, durationMS, 0)));
    }

    public void avatarOriented(String effectPath) {
        getChr().write(UserPacket.effect(Effect.avatarOriented(effectPath)));
    }

    public void reservedEffect(String effectPath) {
        getChr().write(UserPacket.effect(Effect.reservedEffect(effectPath)));

        String[] splitted = effectPath.split("/");
        String sceneName = splitted[splitted.length - 2];
        String sceneNumber = splitted[splitted.length - 1];
        String xmlPath = effectPath.replace("/" + sceneName, "").replace("/" + sceneNumber, "").replace("Effect/", "Effect.wz/");

        Scene scene = new Scene(getChr(), xmlPath, sceneName, sceneNumber);
        scene.setTransferField();
    }

    public void reservedEffect(boolean screenCoord, int x, int y, String effectName) {
        getChr().write(UserPacket.effect(Effect.reservedEffect(screenCoord, x, y, effectName)));
    }

    public void reservedEffectRepeat(String effectPath, boolean start) {
        getChr().write(UserPacket.effect(Effect.reservedEffectRepeat(effectPath, start)));
    }

    public void reservedEffectRepeat(String effectName, boolean idk, boolean show, int x, int y, int duration) {
        getChr().write(UserPacket.effect(Effect.reservedEffectRepeat(effectName, idk, show, x, y, duration)));
    }

    public void reservedEffectRepeat(String effectPath) {
        reservedEffectRepeat(effectPath, true);
    }

    public void playExclSoundWithDownBGM(String soundPath, int volume) {
        getChr().write(UserPacket.effect(Effect.playExclSoundWithDownBGM(soundPath, volume)));
    }

    public void blindEffect(boolean blind) {
        getChr().write(UserPacket.effect(Effect.blindEffect(blind)));
    }

    public void fadeInOut(int fadeIn, int delay, int fadeOut, int alpha) {
        getChr().write(UserPacket.effect(Effect.fadeInOut(fadeIn, delay, fadeOut, alpha)));
    }

    public void createFieldTextEffect(String msg, int letterDelay, int showTime, int clientPosition,
                                      int x, int y, int align, int lineSpace, int type,
                                      int enterType, int leaveType) {
        TextEffectType tet = TextEffectType.values()[type];
        getChr().write(UserPacket.effect(Effect.createFieldTextEffect(msg, letterDelay, showTime, clientPosition, new Position(x, y),
                align, lineSpace, tet, enterType, leaveType)));
    }

    public void speechBalloon(boolean normal, int idx, int linkType, String speech, int time, int align, int x,
                              int y, int z, int lineSpace, int npcTemplateId, int idk) {
        getChr().write(UserPacket.effect(Effect.speechBalloon(normal, idx, linkType, speech, time, align, x, y, z, lineSpace, npcTemplateId, idk)));
    }

    public String formatNumber(String number) {
        return Util.formatNumber(number);
    }

    public String formatNumber(int number) {
        return formatNumber(String.valueOf(number));
    }

    public String formatItem(Item item) {
        return String.format("#b#L%d##i%d##z%dl#\r\n", item.getBagIndex(), item.getItemId(), item.getItemId());
    }

    public String formatInlineItem(Item item) {
        return formatInlineItem(item.getItemId());
    }

    public String formatInlineItem(int itemId) {
        return String.format("#i%d# #z%d#", itemId, itemId);
    }

    public String join(String... args) {
        final String[] s = {""};
        Arrays.stream(args).forEach(a -> s[0] += a);
        return s[0];
    }

    public String formatString(String line, PyDictionary dict) {
        var copyLine = line;
        for (var key : dict.keys()) {
            var value = dict.get(key).toString();
            var keyInBraces = "{" + key + "}";
            copyLine = copyLine.replace(keyInBraces, value);
        }

        return copyLine;
    }

    public String selectionString(String line, List<PyDictionary> dicts) {
        StringBuilder str = new StringBuilder();
        var newLine = "\r\n";

        var i = 0;

        for (var dict : dicts) {
            if (!dict.containsKey("i")) {
                dict.put("i", i);
                i++;
            }

            str.append(formatString(line, dict));
            str.append(newLine);

            //Remove the i again, else it'll stay in the dictionary in python since everything is by ref.
            //keeping it in the dictionary will mess up the selection indexes of the list next time it's ran over in the same script.
            if (dict.containsKey("i")) {
                dict.remove("i");
            }
        }

        return str.toString();
    }

    private Object invoke(Object invokeOn, String methodName, Object... args) {
        List<Class<?>> classList = Arrays.stream(args).map(Object::getClass).collect(Collectors.toList());
        Class<?>[] classes = classList.stream().map(Util::convertBoxedToPrimitiveClass).toArray(Class<?>[]::new);
        Method func;
        try {
            func = getClass().getMethod(methodName, classes);
            return func.invoke(invokeOn, args);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("ScriptManagerImpl.invoke", e);
        }
        return null;
    }

    public void invokeForParty(String methodName, Object... args) {
        if (getChr().getParty() == null) {
            // no party (a solo run of Easy Hilla's corridors): the player alone, as a party of one
            invoke(this, methodName, args);
            return;
        }
        for (PartyMember pm : getChr().getParty().getMembers()) {
            boolean fromDB = false;
            Char chr = pm.getChr();
            if (chr == null) {
                chr = charDao.getById(pm.getCharID());
                fromDB = true;
            }
            invoke(chr.getScriptManager(), methodName, args);
            if (fromDB && methodName.equalsIgnoreCase("setQRValue")) {
                // big hack
                charDao.saveOrUpdate(chr);
            }
        }
    }

    /**
     * Warps the character on after a delay if it is still in this field then. The timer isn't one of this script's
     * events, so a script or map change can't cancel it; use it as a backup for scene warps that must happen.
     */
    public void warpLaterIfStillIn(long delay, int fromFieldId, int toFieldId) {
        Char c = getChr();
        if (c == null) {
            return;
        }
        EventManager.addEvent(() -> {
            if (c.isOnline() && c.getField() != null && c.getField().getId() == fromFieldId) {
                if (uiLocked) {
                    lockInGameUI(false);
                }
                c.warp(toFieldId, 0);
            }
        }, delay);
    }

    /**
     * Warps everyone still in this script's field (this instance's copy of it) on after a delay. A plain timer, as
     * above: the backup for a boss phase move made by a field or death script.
     */
    public void warpFieldLaterIfStillIn(long delay, int toFieldId) {
        Field f = getField();
        if (f == null || FieldData.getFieldInfoById(toFieldId) == null) {
            return;
        }
        EventManager.addEvent(() -> {
            for (Char c : new HashSet<>(f.getChars())) {
                if (c.isOnline() && c.getField() == f) {
                    c.warp(toFieldId, 0);
                }
            }
        }, delay);
    }

    public ScheduledFuture invokeAfterDelay(long delay, String methodName, Object... args) {
        Object[] funcArgs = args;
        if ("warp".equals(methodName) || "warpParty".equalsIgnoreCase(methodName)) {
            // executeAfterScript = false: the warp runs when the timer fires. With (id, portal) a single false would
            // pick warp(int, int, boolean instanceField) and queue the warp for a script that has already ended.
            int extra = args.length == 2 ? 2 : 1;
            funcArgs = new Object[args.length + extra];
            System.arraycopy(args, 0, funcArgs, 0, args.length);
            for (int k = args.length; k < funcArgs.length; k++) {
                funcArgs[k] = false;
            }
        }
        Object[] a = funcArgs;
        ScheduledFuture sf = EventManager.addEvent(() -> invoke(this, methodName, a), delay);

        var isFieldEvent = isFieldEventMethodName(methodName);

        addEvent(sf, isFieldEvent);
        return sf;
    }

    public ScheduledFuture invokeAtFixedRate(long initialDelay, long delayBetweenExecutions,
                                             int executes, String methodName, Object... args) {
        ScheduledFuture scheduledFuture;
        if (executes == 0) {
            scheduledFuture = EventManager.addFixedRateEvent(() -> invoke(this, methodName, args), initialDelay,
                    delayBetweenExecutions);
        } else {
            scheduledFuture = EventManager.addFixedRateEvent(() -> invoke(this, methodName, args), initialDelay,
                    delayBetweenExecutions, executes);
        }

        var isFieldEvent = isFieldEventMethodName(methodName);

        addEvent(scheduledFuture, isFieldEvent);
        return scheduledFuture;
    }

    @Override
    public int playVideoByScript(String videoPath) {
        getNpcScriptInfo().setMessageType(NpcMessageType.PlayMovieClip);
        getChr().write(UserLocal.videoByScript(videoPath, false));
        Object response = null;
        var lastActiveScriptType = getLastActiveScriptType();
        if (isActive(lastActiveScriptType)) {
            response = getScriptInfoByType(lastActiveScriptType).awaitResponse();
        }
        if (response == null) {
            throw new NullPointerException(INTENDED_NPE_MSG);
        }
        return (int) response;
    }

    public void setFuncKeyByScript(boolean add, int action, int key) {
        getChr().write(UserLocal.setFuncKeyByScript(add, action, key));
        getChr().getFuncKeyMap().get(0).putKeyBinding(key, add ? (byte) 1 : (byte) 0, action);
    }

    public void setActionBar(boolean show, ActionBarType type){
        getChr().write(UserLocal.setActionBar(show, type));
    }

    public void setMapTaggedObjectVisible(String key, boolean visible, int manual, int delay) {
        getChr().write(MapLoadable.setMapTaggedObjectVisible(new MapTaggedObject(key, visible, manual, delay)));
    }

    public void addPopUpSay(int npcID, int duration, String message, String effect) {
        getChr().write(UserLocal.addPopupSay(npcID, duration, message, effect));
    }

    public void moveParticleEff(String type, int startX, int startY, int endX, int endY, int moveTime, int totalCount, int oneSprayMin, int oneSprayMax) {
        getChr().write(UserLocal.moveParticleEff(type, new Position(startX, startY), new Position(endX, endY), moveTime, totalCount, oneSprayMin, oneSprayMax));
    }

    public void levelUntil(int toLevel) {
        short level = getChr().getLevel();
        if (level >= toLevel) {
            return;
        }
        while (level < toLevel) {
            addLevel(1);
            level++;
        }
    }

    public void balloonMsg(String message) {
        getChr().write(UserLocal.balloonMsg(message, 100, 3, null));
    }

    public boolean openNodestone(int id) {
        if (net.swordie.ms.ServerSettings.getBoolean("test.v271Matrix", true) && net.swordie.ms.client.character.skills.vmatrix.V271Matrix.isGemstone(id)) {
            net.swordie.ms.client.character.skills.vmatrix.V271Matrix.useGemstonesFromItem(getChr(), id, 1); // v271: Nodestones give V points
            getChr().dispose();
            return true;
        }
        if (!getChr().getQuestManager().hasQuestCompleted(QuestConstants.FIFTH_JOB_QUEST)) {
            getChr().chatMessage("You need to be 5th job to open this item.");
            return false;
        }

        if (getChr().getMatrixRecords().size() > MatrixRecord.MAX_RECORDS) {
            getChr().chatMessage("You currently have a lot of nodes. Please disassemble before trying to get new nodes.");
            return false;
        }

        NodestoneModule.openNodestone(getChr(), id, true);
        return true;
    }

    public void openMultiNodestones(int id, int amount) {
        if (net.swordie.ms.ServerSettings.getBoolean("test.v271Matrix", true) && net.swordie.ms.client.character.skills.vmatrix.V271Matrix.isGemstone(id)) {
            net.swordie.ms.client.character.skills.vmatrix.V271Matrix.useGemstonesFromItem(getChr(), id, amount); // v271: Nodestones give V points
            getChr().dispose();
            return;
        }
        if (!getChr().getQuestManager().hasQuestCompleted(QuestConstants.FIFTH_JOB_QUEST)) {
            getChr().chatMessage("You need to be 5th job to open this item.");
            getChr().dispose();
            return;
        }

        if (amount <= 0 || getChr().getMatrixRecords().size() > MatrixRecord.MAX_RECORDS) {
            getChr().chatMessage("You already have the maximum amount of Nodestones in your VMatrix.");
            getChr().dispose();
            return;
        }

        var consumeAmount = 0;
        for (int j = 0; j < amount; j++) {

            if (getChr().getMatrixRecords().size() > MatrixRecord.MAX_RECORDS) {
                continue;
            }

            NodestoneModule.openNodestone(getChr(), id, false);
            consumeAmount++;
        }

        if (consumeAmount < amount) {
            getChr().chatMessage("You have the maximum amount of Nodestones in your VMatrix.");
        }

        getChr().write(WvsContext.matrixUpdate(getChr(), false, 0, 0));
        consumeItem(id, consumeAmount);
    }

    public void hireTutor(boolean set) {
        getChr().hireTutor(set);
    }

    public void tutorAutomatedMsg(int id) {
        tutorAutomatedMsg(id, 10000);
    }

    public void tutorAutomatedMsg(int id, int duration) {
        getChr().tutorAutomatedMsg(id, duration);
    }

    public void tutorCustomMsg(String message, int width, int duration) {
        getChr().tutorCustomMsg(message, width, duration);
    }

    public boolean hasTutor() {
        return getChr().hasTutor();
    }

    public int getMakingSkillLevel(int skillID) {
        return getChr().getMakingSkillLevel(skillID);
    }

    public boolean isAbleToLevelUpMakingSkill(int skillID) {
        int neededProficiency = SkillConstants.getNeededProficiency(getChr().getMakingSkillLevel(skillID));
        if (neededProficiency <= 0) {
            return false;
        }
        return getChr().getMakingSkillProficiency(skillID) >= neededProficiency;
    }

    public void makingSkillLevelUp(int skillID) {
        getChr().makingSkillLevelUp(skillID);
    }

    private ScriptMemory getMemory() {
        return memory;
    }

    public void setBossCooldown(BossCooldown bc) {
        // Only for entry
        if (getChr().getInstance() != null) {
            getChr().getInstance().markBossRun(); // its trash drops like a boss fight's from the first map
        }
        var instance = getChr().getInstance();
        if (instance != null && !instance.getChars().isEmpty()) {
            // everyone who went in with the party (members elsewhere didn't enter and keep their entry)
            for (Char c : instance.getChars()) {
                c.getAccount().setBossCooldown(bc, false);
            }
        } else if (getChr().getParty() != null) {
            for (var pm : getChr().getParty().getOnlineMembers()) {
                if (pm != null && pm.getChr() != null) {
                    pm.getChr().getAccount().setBossCooldown(bc, false);
                }
            }
        } else {
            getChr().getAccount().setBossCooldown(bc, false);
        }
    }

    public boolean isOnBossCooldown(BossCooldown bc) {
        return getChr().getAccount().isOnBossCooldown(bc);
    }

    public int getRemainingBossCooldownMinutes(BossCooldown bc) {
        AccountBossCooldown abc = getChr().getAccount().getBossCooldown(bc);
        FileTime nextEntry = abc == null ? null : abc.getNextEntryTime();
        if (nextEntry == null || nextEntry.isExpired()) {
            return 0;
        } else {
            LocalDateTime cur = FileTime.currentTime().toLocalDateTime();
            LocalDateTime to = nextEntry.toLocalDateTime();
            return (int) ((to.toEpochSecond(ZoneOffset.UTC) - cur.toEpochSecond(ZoneOffset.UTC)) / 60);
        }
    }

    // Will methods

    public void incrementMoonlight() {
        for (var chr : getField().getChars()) {
            var hardMode = FieldConstants.isHardWillField(getFieldID());
            var bossInfo = chr.getBossInfo();
            bossInfo.addMoonlight(hardMode ? 1 : 2);
        }
    }

    public void startWillTriggerTimer() {
        if (BossConstants.getWillPhase(getFieldID()) == 1) {
            // GMS: the first "unlock his power" about a minute in, then every 2 minutes, with the countdown shown
            WillModule.setNextCycle(getField(), 60_000);
            WillModule.sendCycleTimer(getField(), 0);
            addEvent(EventManager.addFixedRateEvent(this::doTriggerBlockCheck, 60_000, WillModule.CYCLE_MS, TimeUnit.MILLISECONDS));
            Field willField = getField();
            addEvent(EventManager.addFixedRateEvent(() -> WillModule.resendP1Hp(willField), WillModule.HP_RESEND_MS,
                    WillModule.HP_RESEND_MS, TimeUnit.MILLISECONDS));
            return;
        }
        addEvent(EventManager.addFixedRateEvent(this::doTriggerBlockCheck, 90, 120, TimeUnit.SECONDS));
    }

    public void startWillWebTimer() {
        addEvent(EventManager.addFixedRateEvent(() -> WillModule.spawnNarrowWeb(getField()), 6, 6, TimeUnit.SECONDS));
    }

    private void doTriggerBlockCheck() {
        switch (BossConstants.getWillPhase(getFieldID())) {
            case 1:
                WillModule.doWillTest1(getField());
                break;
            case 2:
                WillModule.doWillTest2(getField());
                break;
        }
    }

    // Papulatus

    public void startTweezerTimers() {
        getField().broadcastPacket(PapulatusFieldPacket.papulatusFieldObjectChanged(PapulatusFieldObject.tweezers(papulatusTweezers(1))));
    }

    /** GMS has 6 tweezers: state 1 while the fight runs, 0 once it is over. */
    private static List<PapulatusTweezerInfo> papulatusTweezers(int state) {
        var tweezers = new ArrayList<PapulatusTweezerInfo>();
        for (int i = 0; i < BossConstants.PAPULATUS_TWEEZERS; i++) {
            tweezers.add(new PapulatusTweezerInfo(i, state, 0, 0));
        }
        return tweezers;
    }

    private Mob getPapulatus() {
        var papulatusLife = getField().getLifeByTemplateIds(BossConstants.PAPULATUS_EASY_P1, BossConstants.PAPULATUS_EASY_P2,
                BossConstants.PAPULATUS_NORMAL_P1, BossConstants.PAPULATUS_NORMAL_P2,
                BossConstants.PAPULATUS_CHAOS_P1, BossConstants.PAPULATUS_CHAOS_P2
        );
        return papulatusLife instanceof Mob ? (Mob) papulatusLife : null;
    }

    public void startPapulatusAlarmClockTimer() {
        var papulatus = getPapulatus();
        if (papulatus != null) {
            papulatus.addBlockedSkill(papulatus.getSkillByMobSkillInfo(MobSkillID.PapulatusSkill.getVal(), 6));
        }

        getField().broadcastPacket(PapulatusFieldPacket.papulatusFieldObjectChanged(
                PapulatusFieldObject.alarmClockTimer(true, false, BossConstants.PAPULATUS_ALARM_CLOCK_COOLTIME_MILLIS))
        );
        addEvent(EventManager.addEvent(this::papulatusAlarmClockActivate, BossConstants.PAPULATUS_ALARM_CLOCK_COOLTIME_MILLIS));
    }

    public void papulatusAlarmClockActivate() {
        var papulatus = getPapulatus();
        if (papulatus != null) {
            var alarmClockSkill = papulatus.getSkillByMobSkillInfo(MobSkillID.PapulatusSkill.getVal(), 6);
            papulatus.removeBlockedSkill(alarmClockSkill);
            papulatus.setForcedSkill(alarmClockSkill);
        }

        getField().broadcastPacket(PapulatusFieldPacket.papulatusFieldObjectChanged(
                PapulatusFieldObject.alarmClockTimer(true, true, BossConstants.PAPULATUS_ALARM_CLOCK_MILLIS))
        );
        if (papulatus != null) { // GMS: the alarm starts ringing on Papulatus
            getField().broadcastPacket(PapulatusFieldPacket.papulatusFieldObjectChanged(
                    PapulatusFieldObject.alarmState(papulatus.getTemplateId(), true)));
        }

        // 10% health per second drain
        addEvent(EventManager.addFixedRateEvent(() -> {
            for (var chr : getField().getCharsReadOnly()) {
                chr.damagePerc(10);
            }
        }, 0, 1000, (BossConstants.PAPULATUS_ALARM_CLOCK_MILLIS / 1000) - 1));

        // clock keys
        addEvent(EventManager.addFixedRateEvent(() -> createObstacleAtom(ObstacleAtomEnum.PapulatusPurpleKey, 1, 50, 9, 10, 25)
                , 0, 5000, (BossConstants.PAPULATUS_ALARM_CLOCK_MILLIS / 5000) - 1));
        addEvent(EventManager.addFixedRateEvent(() -> createObstacleAtom(ObstacleAtomEnum.PapulatusYellowKey, 1, 50, 12, 10, 50)
                , 0, 5000, (BossConstants.PAPULATUS_ALARM_CLOCK_MILLIS / 5000) - 1));

        addEvent(EventManager.addEvent(() -> {
            var p = getPapulatus();
            if (p != null) {
                getField().broadcastPacket(PapulatusFieldPacket.papulatusFieldObjectChanged(
                        PapulatusFieldObject.alarmState(p.getTemplateId(), false)));
            }
            startPapulatusAlarmClockTimer();
        }, BossConstants.PAPULATUS_ALARM_CLOCK_MILLIS));
    }

    /** The laser pairs GMS uses (index 2k and 2k+1): start angle and turn, as GMS Chaos. */
    private static final double[][][] PAPULATUS_LASERS = {
            {{60, 35}, {270, -10}},
            {{130, -20}, {130, 30}},
            {{0, 30}, {300, -10}},
            {{0, 100}, {300, -10}},
    };

    public void startPapulatusLaserCooltimeTimerInit() {
        addEvent(EventManager.addEvent(this::startPapulatusLaserTimer, BossConstants.PAPULATUS_LASER_INIT_COOLTIME_MILLIS));
    }

    /** Lasers off as GMS: a 2 s fade, then an empty list; back on after a short break. */
    public void startPapulatusLaserCooltimeTimer() {
        getField().broadcastPacket(PapulatusFieldPacket.papulatusFieldObjectChanged(PapulatusFieldObject.laserEnd(2000)));
        getField().broadcastPacket(PapulatusFieldPacket.papulatusFieldObjectChanged(PapulatusFieldObject.lasers(new ArrayList<>())));
        addEvent(EventManager.addEvent(this::startPapulatusLaserTimer, Util.getRandom(
                BossConstants.PAPULATUS_LASER_OFF_MIN_MILLIS, BossConstants.PAPULATUS_LASER_OFF_MAX_MILLIS)));
    }

    public void startPapulatusLaserTimer() {
        int pattern = Util.getRandom(PAPULATUS_LASERS.length - 1);
        double[][] pair = PAPULATUS_LASERS[pattern];
        var list = new ArrayList<PapulatusLaserInfo>();
        list.add(new PapulatusLaserInfo(pattern * 2, true, pair[0][0], pair[0][1]));
        list.add(new PapulatusLaserInfo(pattern * 2 + 1, true, pair[1][0], pair[1][1]));
        getField().broadcastPacket(PapulatusFieldPacket.papulatusFieldObjectChanged(PapulatusFieldObject.lasers(list)));

        addEvent(EventManager.addEvent(this::startPapulatusLaserCooltimeTimer, Util.getRandom(
                BossConstants.PAPULATUS_LASER_ON_MIN_MILLIS, BossConstants.PAPULATUS_LASER_ON_MAX_MILLIS)));
    }

    /** Papulatus is beaten: as GMS, the alarm stops, the tweezers go idle and the lasers go out. */
    public void endPapulatusFight(int templateId) {
        stopEvents();
        var field = getField();
        field.broadcastPacket(PapulatusFieldPacket.papulatusFieldObjectChanged(
                PapulatusFieldObject.alarmClockTimer(false, false, BossConstants.PAPULATUS_ALARM_CLOCK_COOLTIME_MILLIS)));
        field.broadcastPacket(PapulatusFieldPacket.papulatusFieldObjectChanged(PapulatusFieldObject.alarmState(templateId, false)));
        field.broadcastPacket(PapulatusFieldPacket.papulatusFieldObjectChanged(PapulatusFieldObject.tweezers(papulatusTweezers(0))));
        field.broadcastPacket(PapulatusFieldPacket.papulatusFieldObjectChanged(PapulatusFieldObject.lasers(new ArrayList<>())));
    }

    public void changePapulatusTime(boolean divide) {
        var tsm = getChr().getTemporaryStatManager();
        var option = tsm.getOption(PapulatusTimeLock);
        if (option != null && tsm.hasStat(PapulatusTimeLock)) {
            var remainingTime = (int) option.getRemainingTime() / 1000;
            if (divide) {
                remainingTime /= 2;
            } else {
                remainingTime *= 2;
            }
            if (remainingTime > 99) {
                remainingTime = 99;
            }
            var o = new Option();
            o.nOption = 1;
            o.rOption = MobSkillID.PapulatusSkill.getVal();
            o.slv = 3;
            o.tOption = remainingTime;
            o.xOption = remainingTime;
            tsm.putCharacterStatValueFromMobSkill(PapulatusTimeLock, o);
        }
        tsm.sendSetStatPacket();
    }


    // Professions

    public void increaseMasteryAndShowSuccess(int reqLevel, boolean herb) {
        var skillId = herb ? SkillConstants.HERBALISM_SKILL : SkillConstants.MINING_SKILL;
        var slv = getChr().getMakingSkillLevel(skillId);
        var chanceToSucceed = 100 - ((reqLevel - slv) * 10);
        var success = Util.succeedProp(chanceToSucceed);
        var mastery = success ? 20 : 2;
        getChr().addMakingSkillProficiency(skillId, mastery);

        var trait = SkillConstants.getIncStatByMakingSkill(skillId);
        getChr().addTraitExp(trait, GameConstants.getTraitExpByMakingSkillLevel(reqLevel));
        getChr().write(FieldPacket.gatherResult(getChr(), success));
    }


    // San Commerci | Voyage
    public void finishVoyageHorde() {
        Voyage.finishHorde(getChr());
    }


    public static void clear() {
        scriptCache.clear();
    }

    //DailyEntry methods
    public int getRemainingDailyEntries(DailyEntry de){
        return getChr().getAccount().getRemainingEntries(de);
    }

    public void addDailyEntry(DailyEntry de){
        getChr().getAccount().addDailyEntry(de);
    }

    public void reduceDailyEntry(DailyEntry de){
        getChr().getAccount().reduceDailyEntry(de);
    }

    @Override
    public boolean levelArcaneSymbol(BodyPart symbolPart, int levelAmount) {
        Item item = chr.getEquippedInventory().getFirstItemByBodyPart(symbolPart);
        if (item != null) {
            Equip symbol = (Equip)item;
            if (symbol.getSymbolLevel() < ItemConstants.MAX_ARCANE_SYMBOL_LEVEL) {
                symbol.setSymbolLevel((short) (Math.min(ItemConstants.MAX_ARCANE_SYMBOL_LEVEL, symbol.getSymbolLevel() + levelAmount)));
                symbol.initSymbolStats(symbol.getSymbolLevel(), symbol.getSymbolExp(), chr.getJob());
                symbol.updateToChar(chr);
                return true;
            }
        } else {
            chr.chatMessage("Please equip your symbol before you try to complete the weekly.");
        }
        return false;
    }

    @Override
    public boolean levelAuthSymbol(BodyPart symbolPart, int levelAmount) {
        Item item = chr.getEquippedInventory().getFirstItemByBodyPart(symbolPart);
        if (item != null) {
            Equip symbol = (Equip)item;
            if (symbol.getSymbolLevel() < ItemConstants.MAX_AUTH_SYMBOL_LEVEL) {
                symbol.setSymbolLevel((short) (Math.min(ItemConstants.MAX_AUTH_SYMBOL_LEVEL, symbol.getSymbolLevel() + levelAmount)));
                symbol.initSymbolStats(symbol.getSymbolLevel(), symbol.getSymbolExp(), chr.getJob());
                symbol.updateToChar(chr);
                return true;
            }
        } else {
            chr.chatMessage("Please equip your symbol before you try to complete the weekly.");
        }
        return false;
    }

    // ---- Names GMS-style scripts use for things we already have ----

    /** One key of a quest record (Quest.wz infoex/exVariable), "" when absent. */
    public String getQuestEx(int questId, String key) {
        String v = getQRValue(questId, key);
        return v == null ? "" : v;
    }

    public void setQuestEx(int questId, String key, String value) {
        setQRValue(questId, key, value);
    }

    /** A map property that is set and not false. */
    public boolean fieldHasProperty(String key) {
        Object v = getField().getProperty(key);
        return v != null && !Boolean.FALSE.equals(v);
    }

    public void setFieldProperty(String key, Object value) {
        getField().setProperty(key, value);
    }

    /** Sets every reactor of this template in the map to a state and shows it to the map. */
    public void changeReactorState(int templateId, int state) {
        changeReactorState(templateId, state, 0, 0);
    }

    public void changeReactorState(int templateId, int state, int delay, int stateLength) {
        changeReactorStateByTemplateId(templateId, (byte) state, (short) delay, (byte) stateLength);
    }

    public void OnOffLayer_On(int duration, String key, int x, int y, int z, String path, int origin, int postRender,
                              int idk, int repeat) {
        onLayer(duration, key, x, y, z, path, origin, postRender != 0, idk, repeat != 0);
    }

    public void OnOffLayer_Off(int duration, String key, int repeat) {
        offLayer(duration, key, repeat != 0);
    }

    /** Drops one of each item, one after another, spread over 2 * range around (x, y). */
    public void dropItemsAlongLine(int[] items, int range, int x, int y, int delayMs) {
        getField().dropItemsAlongLine(items, range, x, y, delayMs);
    }

    public void dropItemsAlongLine(int[] items, int range, int x, int y, int delayMs, Char owner) {
        dropItemsAlongLine(items, range, x, y, delayMs);
    }

    public boolean startScript(Char chr, int parentID, String scriptName, ScriptType scriptType) {
        return start(chr, parentID, 0, scriptName, scriptType);
    }

    public boolean start(Char chr, int parentID, int objID, String scriptName, ScriptType scriptType) {
        System.out.println("[SCRIPT] " + (chr != null ? chr.getName() : "null") +
                " -> script=" + scriptName +
                ", type=" + scriptType +
                ", field=" + ((chr != null && chr.getField() != null) ? chr.getField().getId() : "null"));
        if (scriptType == ScriptType.None || (scriptType == ScriptType.Quest && !isQuestScriptAllowed())) {
            return false;
        }
        if (scriptType == ScriptType.Item && ScriptHandlers.handleItemScripts(chr, parentID, objID, scriptName, scriptType)) {
            chr.dispose();
            return true;
        }
        boolean exists = new File(String.format("%s/%s/%s%s", ServerConstants.SCRIPT_DIR, scriptType.getDir().toLowerCase(), scriptName, SCRIPT_ENGINE_EXTENSION)).exists();
        if (!exists) {
            undefined(scriptType, parentID);
            return true;
        }
        if (isActive(scriptType) && scriptType != ScriptType.Field) { // because Field Scripts don't get disposed.
            dispose(); // destrabar script anterior colgado en vez de bloquear con "too fast"
        }
        setLastActiveScriptType(scriptType);
        resetParam();
        Bindings bindings = scriptEngine.createBindings();
        bindings.put("sm", chr == null ? this : chr.getScriptManager());
        bindings.put("chr", chr);
        bindings.put("field", chr == null ? getField() : chr.getField());
        bindings.put("parentID", parentID);
        bindings.put("scriptType", scriptType);
        bindings.put("objectID", objID);
        if (scriptType == ScriptType.Reactor) {
            bindings.put("reactor", chr == null ? getField().getLifeByObjectID(objID) : chr.getField().getLifeByObjectID(objID));
        }
        if (scriptType == ScriptType.Quest) {
            bindings.put("startQuest", scriptName.charAt(scriptName.length() - 1) == QUEST_START_SCRIPT_END_TAG.charAt(0)); // biggest hack eu
        }
        ScriptInfo si = new ScriptInfo(scriptType, bindings, parentID, scriptName);
        if (scriptType == ScriptType.Npc || scriptType == ScriptType.Boss) {
            getNpcScriptInfo().setTemplateID(parentID);
        }
        getNpcScriptInfo().setObjectID(objID);
        si.setObjectID(objID);
        getScripts().put(scriptType, si);
        service.execute(() -> start(scriptName, si));
        return true;
    }

    private void start(String name, ScriptInfo si) {
        if (si == null) {
            return;
        }
        final ScriptType scriptType = si.getScriptType();
        final String dir = String.format("%s/%s/%s%s", ServerConstants.SCRIPT_DIR, scriptType.getDir().toLowerCase(), name, SCRIPT_ENGINE_EXTENSION);
        si.setActive(true);
        si.setFileDir(dir);
        CompiledScript cs;
        StringBuilder script = new StringBuilder();
        ScriptEngine se = scriptEngine;
        Bindings bindings = getBindingsByType(scriptType);
        si.setInvocable((Invocable) se);
        try {
            String context = Util.readFile(dir, StandardCharsets.UTF_8);
            if (context.startsWith("\uFEFF")) {
                context = context.substring(1);
            }
            script.append(context);
        } catch (IOException e) {
            DataPrinter.send(DataPrinter.EXCEPTION_CAUGHT, e);
            unlockUI();
        }
        try {
            if (!script.isEmpty()) {
                cs = ((Compilable) se).compile(script.toString());
                cs.eval(bindings);
            }
        } catch (ScriptException e) {
            if (!isIntendedStop(e)) {
                DataPrinter.send(DataPrinter.SCRIPTS,
                        String.format("Cannot run script [%s]! Exception [%s] at line %s.",
                                name, e.getMessage(), e.getLineNumber()), true);
                unlockUI();
                progressMessageFont(3, 20, 9, 1000,
                        "Unknown error! Please type @sualoi or @dispose.");
            }
        } finally {
            stop(scriptType);
            FieldTransferInfo fti = getFieldTransferInfo();
            if (!fti.isInit()) {
                if (fti.isField()) {
                    fti.warp(field);
                } else {
                    fti.warp(chr);
                }
            }
            System.out.printf("%s: Completed script %s, scriptType %s.%n", chr != null ? chr.getName() : "Null", name, scriptType);
            chr.dispose();
        }
    }
    private boolean isIntendedStop(ScriptException e) {
        String msg = e.getMessage();
        if (msg != null && msg.contains(INTENDED_NPE_MSG)) {
            return true;
        }
        Throwable t = e.getCause();
        while (t != null) {
            String m = t.getMessage();
            if (m != null && m.contains(INTENDED_NPE_MSG)) {
                return true;
            }
            t = t.getCause();
        }
        return false;
    }

    public void incCharismaEXP(short amount) {
        chr.addTraitExp(Stat.charismaEXP, amount);
    }

    private void undefined(ScriptType scriptType, int parentID) {
        switch (scriptType) {
            case Item:
                chat("Item not implemented, please report to developer. ID: " + parentID);
                chr.dispose();
                break;
            case Portal:
                chat("Portal not implemented, please report to developer. ID: " + parentID);
                chr.dispose();
                break;
            case Quest:
                chat("Quest not implemented, please report to developer. ID: " + parentID);
                chr.dispose();
                break;
            case Reactor:
                chat("Reactor not implemented, please report to developer. ID: " + parentID);
                chr.dispose();
                break;
            case Field:
                chat("Map not implemented, please report to developer. ID: " + parentID);
                chr.dispose();
                break;
            case Npc:
                String[] dialogues = new String[]{"Watch out! The Slimes these days are stronger than usual.",
                        "Did you see any golden pigs run by? I lost one!",
                        "I just saw a Stone Golem guarding a treasure. Be careful, it's very tough.",
                        "Oh, you seem to be a Bounty Hunter. Can you help me clean up some monsters?",
                        "The Horn Mushrooms are wandering near the edge of town. I hope they don't bother anyone.",
                        "Have you ever seen a Yeti? They're much bigger than I imagined.",
                        "I lost my precious knife near the Magic Tree. Probably a Magic Monster took it.",
                        "Do you have some bullets with you? I used all mine fighting the Stone Dragons.",
                        "Don't underestimate the Blue Slimes. They may be harmless, but when they move in a group...",
                        "Your hat is torn, probably from those forest boars chewing on it?",
                        "I need some Soft Horns from the Green Mushrooms. Can you help me?",
                        "Please help me find Monster Cores from the Green Bears. I'll reward you with a worthy gift.",
                        "Unfortunately I dropped my magic scroll. It might be somewhere near the Magic Forest.",
                        "Do you have enough Snow Powder for me to make some ice cream? It will be hard to find.",
                        "To craft this weapon, I need 10 Crystal Fragments. You can find them in the mine.",
                        "I need some more gems. I heard they only drop from special Stone Golems.",
                        "Bring me a Bat Wing. I need it to complete a secret mission.",
                        "Have you found the ring I lost? It has a Dragon symbol.",
                        "I need 20 Bird Feathers to make a pillow. Can you help me?",
                        "I'll pay you a large sum if you bring me the Seal Spell from Dragon Cave.",
                        "You need a stronger sword to deal with enemies? I can forge one for you.",
                        "Soon, are you planning to change jobs? Think carefully.",
                        "I've crafted a new potion, it can boost your strength for a short time.",
                        "Do you have enough experience to learn a new skill?",
                        "Experience is everything. Don't just focus on making money.",
                        "Learn a lot to become stronger. That's my advice.",
                        "Natural talent only accounts for a small part. Hard work is what's important.",
                        "I used to be a renowned blacksmith. But now it's just the past.",
                        "This world always needs skilled craftsmen. Do you want to learn the trade?",
                        "Leveling up is a long journey. Good luck.",
                        "Do you have plans to go to Victoria Island? There are many secrets there.",
                        "Never go to Lith Harbor. It's too dangerous for a newbie like you.",
                        "I've heard stories about a city in the sky. Maybe you'll discover something.",
                        "Be careful when going to the Tower of Chaos. I heard the monsters there are scary.",
                        "Have you seen the Chain Lightning Castle? I heard it's the place of legendary knights.",
                        "Go to the Snow Land of El Nath, where there's an old man who's very good at magic.",
                        "The town of Perion is always busy with warriors.",
                        "I think you should go to Ant Tunnel Mine. It's a great place to level up.",
                        "There are many places to explore. Prepare your gear well.",
                        "Have you heard of the underwater city Aquario? I heard it's beautiful.",
                        "It's probably because I ate too much forest boar. Now I just want to sleep.",
                        "You have a mushroom head just like a Horn Mushroom!",
                        "I just slipped and fell from a Slime. How embarrassing.",
                        "It seems I'm lost. Can you help me find my way back?",
                        "I already told you, I'm not a quest NPC!",
                        "Your hat looks ridiculous. Is it a mushroom hat?",
                        "The sweet bread here is delicious! Would you like to try it?",
                        "I can't help you, I'm just a passerby.",
                        "I saw you drop an item. Oh no, it's just a rock.",
                        "Oh, I'm busy counting leaves on the tree. Can you come back later?"};
                sendOK(Util.getRandomFromCollection(dialogues), parentID);
                break;
            default:
                chr.dispose();
                break;
        }
    }


}
