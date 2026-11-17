package net.swordie.ms.world.field.instance;

import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.party.Party;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.CUIHandler;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.constants.OzConstants;
import net.swordie.ms.enums.BossCooldown;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.loaders.FieldData;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.Channel;
import net.swordie.ms.world.field.ClockPacket;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.FieldInstanceType;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelperFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.CapDebuff;
import static net.swordie.ms.world.field.FieldInstanceType.PARTY;
import static net.swordie.ms.world.field.FieldInstanceType.SOLO;

/**
 * Created on 23-4-2019.
 *
 * @author Asura
 */
public class Instance {
    private static final int UNDERSEA_14F_FIELD = 992014000;

    private Char chr;
    private Party party;
    private Channel channel;
    // expedition
    // guild
    private int enterFieldId;
    private int enterPortalId;
    private int forcedReturn;
    /** where a removed player goes when the enter map has no forced return (999999999) */
    private int fallbackReturn = GameConstants.NO_MAP_ID;
    private final Map<Integer, Field> fields = new java.util.concurrent.ConcurrentHashMap<>();
    // entering/leaving players and instance timers use both; scripts may store a null property
    private final Map<String, Object> properties = Collections.synchronizedMap(new HashMap<>());
    private final FieldInstanceType instanceType;
    private ScheduledFuture warpOutTimer;
    private ClockPacket clockPacket;
    private int instanceStartTime; // time in seconds the instance is initialised with
    private long startTime; // currentTimeMillis  when instance is created.
    private long warpOutTimeout;
    private long totalPauseTime;
    private long pauseTime; // currentTimeMillis  when clock is paused.
    private List<Char> chars = new CopyOnWriteArrayList<>(); // current characters (thread-safe: warps and timers loop over it while members leave)
    private Set<Integer> charIds = ConcurrentHashMap.newKeySet(); // characters that entered the instance
    private int forcedReturnPortalId;
    public boolean initialised;
    public boolean isBeingDeleted; // instance is getting deleted
    private InstanceHelper instanceHelper;

    public Instance(Char chr) {
        this.chr = chr;
        this.charIds.add(chr.getId());
        instanceType = SOLO;
    }

    public Instance(Party party) {
        this.party = party;
        instanceType = PARTY;
    }

    // region Getter & Setters
    /**
     * Returns a List of eligible Chars for this Instance.
     *
     * @return list of eligible Chars
     */
    public List<Char> getChars() {
        return chars;
    }

    /**
     * Returns the Char of this Instance, if it is a SOLO instance. Null otherwise.
     *
     * @return Char of this instance
     */
    public Char getChr() {
        return chr;
    }

    /**
     * Returns the Party of this Instance, if it is a PARTY instance. Null otherwise.
     *
     * @return
     */
    public Party getParty() {
        return party;
    }

    /**
     * Returns a List of active Fields in this Instance.
     *
     * @return List of active Fields
     */
    /**
     * @return true if a boss monster has spawned in any of this instance's fields (a boss encounter)
     */
    private volatile boolean bossRun;

    /**
     * Whether a boss entry set a boss cooldown for this instance; it counts as a boss run from the first map, before the
     * boss itself spawns (Hilla's corridors, Zakum's altar, ...).
     */
    public boolean isBossRun() {
        return bossRun || isBossEncounter();
    }

    public void markBossRun() {
        bossRun = true;
        for (Char chr : new ArrayList<>(getChars())) {
            net.swordie.ms.client.jobs.common.SixthJobSkills.onBossEntry(chr); // the boss run's Ascent skill uses
        }
    }

    public boolean isBossEncounter() {
        for (Field field : getFields().values()) {
            if (field != null && field.isBossSpawned()) {
                return true;
            }
        }
        return false;
    }

    public Map<Integer, Field> getFields() {
        return fields;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public FieldInstanceType getInstanceType() {
        return instanceType;
    }

    /**
     * Returns the forced return Field of this Instance, for when a Char is forced out of this Instance.
     *
     * @return the forced return Field
     */
    public int getForcedReturn() {
        return forcedReturn;
    }

    private static boolean isExitMap(int fieldId) {
        return fieldId > 0 && fieldId != GameConstants.NO_MAP_ID && FieldData.getFieldInfoById(fieldId) != null;
    }

    /** The forced return, or (enter map without one) its return map / the map the players entered from. */
    public int getExitFieldId() {
        return isExitMap(forcedReturn) ? forcedReturn : fallbackReturn;
    }

    /**
     * Where a player out of the fight goes from the given map of this instance (as GMS: the map's forced return, else
     * its return map), when that is a map outside the instance; otherwise {@link #getExitFieldId()}.
     */
    public int exitFor(net.swordie.ms.loaders.containerclasses.FieldInfo info) {
        if (info != null) {
            for (int id : new int[]{info.getForcedReturn(), info.getReturnMap()}) {
                if (isExitMap(id) && id != info.getId() && !getFields().containsKey(id)) {
                    return id;
                }
            }
        }
        return getExitFieldId();
    }

    /**
     * Returns the initial enter Field of this Instance.
     *
     * @return the initial enter Field of this Instance.
     */
    public int getEnterFieldId() {
        return enterFieldId;
    }

    /**
     * Returns the initial enter Portal of this Instance.
     *
     * @return the initial enter Portal of this Instance.
     */
    public int getEnterPortalId() {
        return enterPortalId;
    }

    /**
     * Returns the timer of the event that will warp everyone out of this Instance.
     *
     * @return warp out event timer
     */
    public ScheduledFuture getWarpOutTimer() {
        return warpOutTimer;
    }

    /**
     * Adds a property to this Instance.
     *
     * @param key   the property's key
     * @param value the property's value
     */
    public void addProperty(String key, Object value) {
        getProperties().put(key, value);
    }

    /**
     * Checks if this Instance has a property.
     *
     * @param key the key to check
     * @return whether or not this Instance has the property
     */
    public boolean hasProperty(String key) {
        return getProperties().containsKey(key);
    }

    /**
     * Gets the value of a property, or null if there is none.
     *
     * @param key the key of the property
     * @return the value of the property, or null if there is none
     */
    public Object getProperty(String key) {
        return getProperties().getOrDefault(key, null);
    }

    /**
     * Returns whether or not this is a party's instance.
     *
     * @return whether or not this is a party's instance.
     */
    public boolean isParty() {
        return getInstanceType() == PARTY;
    }

    /**
     * Returns whether or not this is a solo instance.
     *
     * @return whether or not this is a solo instance.
     */
    public boolean isSolo() {
        return getInstanceType() == SOLO;
    }

    public ClockPacket getClockPacket() {
        return clockPacket;
    }

    /**
     * Returns the Field according to the given field id. If there is currently no such Field, tries to make one and
     * add it to the active Field list.
     *
     * @param fieldID the field's id to get
     * @return the corresponding active Field
     */
    public Field getField(int fieldID) {
        Field field = getFields().get(fieldID);
        if (field != null) {
            return field;
        }
        synchronized (fields) { // party members entering together must share one copy of the map
            field = getFields().get(fieldID);
            if (field == null) {
                field = FieldData.getFieldCopyById(fieldID);
                if (field == null) {
                    return null;
                }
                field.setInstance(this);
                getFields().put(field.getId(), field);
            }
        }
        return field;
    }

    /**
     * Sets the forced return Field of this Instance.
     *
     * @param forcedReturn the forced return field's id
     */
    public void setForcedReturn(int forcedReturn) {
        this.forcedReturn = forcedReturn;
    }

    /**
     * Sets the enter Field of this Instance
     *
     * @param enterFieldId the enter field's id
     */
    public void setEnterFieldId(int enterFieldId) {
        this.enterFieldId = enterFieldId;
    }

    /**
     * Sets the enter Portal of this Instance
     *
     * @param enterPortalId the enter portal's id
     */
    public void setEnterPortalId(int enterPortalId) {
        this.enterPortalId = enterPortalId;
    }

    public Set<Integer> getCharIds() {
        return charIds;
    }

    public int getForcedReturnPortalId() {
        return forcedReturnPortalId;
    }

    public void setForcedReturnPortalId(int forcedReturnPortalId) {
        this.forcedReturnPortalId = forcedReturnPortalId;
    }

    public InstanceHelper getInstanceHelper() {
        return instanceHelper;
    }

    public void setInstanceHelper(InstanceHelper instanceHelper) {
        this.instanceHelper = instanceHelper;
    }

    // endregion

    // region ReEntrance
    /**
     * Makes a Char reenter in this instance.
     *
     * @param chr the Char that should enter this instance
     */
    public void reEnter(Char chr) {
        if (canReEnter(chr)) {

            var enterFieldId = getEnterFieldId();

            if (FieldConstants.isTowerOfOzField(enterFieldId)) {
                var currentFloor = (int) getProperty(OzConstants.CURRENT_FLOOR); // For Oz Tower. Spawn in the current floor. not the entrance floor
                enterFieldId = OzConstants.OZ_LOBBY + (currentFloor * 1000);

                if (enterFieldId == UNDERSEA_14F_FIELD) {
                    enterFieldId = UNDERSEA_14F_FIELD + 1;
                }
            }

            chr.warp(enterFieldId, getEnterPortalId());
            getChars().add(chr); // Add chr back. as they have returned to the instance.
        }
    }

    /**
     * Returns whether or not the given Char can re-enter this Instance.
     *
     * @param chr the Char to check
     * @return whether or not the given Char can re-enter this Instance.
     */
    public boolean canReEnter(Char chr) {
        return getCharIds().contains(chr.getId());
    }
    // endregion

    /**
     * Sets up this Instance. Creates a List of Chars for this Instance, and warps them to the given Field.
     *
     * @param fieldId  the Field's id to warp to
     * @param portalId the Portal's id to warp to
     */
    public void setup(int fieldId, int portalId) {
        //
        // Check Field Info
        //
        var checkField = FieldData.getFieldInfoById(fieldId);
        if (checkField == null) {
            throw new IllegalArgumentException("Invalid Field id " + fieldId);
        }

        //
        // Create PARTY Instance
        //
        if (party != null) {
            chars = new CopyOnWriteArrayList<>(party.getPartyMembersInField(party.getPartyLeader().getChr()));
            for (var chr : chars) {
                charIds.add(chr.getId());
            }
            party.setInstance(this);
        //
        // Create SOLO Instance
        //
        } else {
            chars = new CopyOnWriteArrayList<>();
            chars.add(chr);
            charIds.add(chr.getId());
        }

        //
        // Set Instance variables (before the warp: the helper is picked by the enter field, and its onWarp and
        // onMobSpawn must already see the first map)
        //
        setEnterFieldId(fieldId);
        setEnterPortalId(portalId);
        setForcedReturn(checkField.getForcedReturn());
        int ret = checkField.getReturnMap();
        int from = chars.isEmpty() ? 0 : chars.get(0).getFieldID();
        fallbackReturn = isExitMap(ret) && ret != fieldId ? ret : isExitMap(from) ? from : GameConstants.NO_MAP_ID;

        startTime = Util.getCurrentTimeLong();

        var instanceHelper = InstanceHelperFactory.getHelperByInstance(this);

        //
        // Warp all chars to the field
        //
        for (Char chr : getChars()) {
            chr.setInstance(this);
            chr.warp(fieldId, portalId);
        }

        // Add Instance to Channel. Allowing users to reenter only if Instance is Non Solo
        if (!getInstanceType().equals(SOLO)) {
            var c = getChars().get(0);
            channel = c.getWorld().getChannelById(c.getChannel());
            channel.addInstance(getEnterFieldId(), this);
        }

        if (instanceHelper != null) {
            instanceHelper.setup(this);
        }
    }

    public void removeCharNoReentrance(Char chr) {
        removeChar(chr, getForcedReturn(), getForcedReturnPortalId(), true, false);
    }

    public void removeChar(Char chr) {
        removeChar(chr, true);
    }

    public void removeChar(Char chr, boolean warp) {
        removeChar(chr, getForcedReturn(), getForcedReturnPortalId(), warp, true);
    }

    public void removeChar(Char chr, int returnFieldId, int portalId, boolean warp, boolean reentranceAllowed) {
        // leaving a boss encounter resets every skill cooldown; checked here because the character's instance is
        // cleared below, before the warp (so Char.warp can no longer see that it came from a boss)
        boolean resetCooldowns = net.swordie.ms.constants.ServerFeatures.on(net.swordie.ms.constants.ServerFeatures.Feature.RESET_COOLDOWNS_AFTER_BOSS) && isBossEncounter();

        //
        // Remove from instance 'chars' list
        //
        getChars().remove(chr);

        if (chr.getInstance() != null && chr.getInstance() != this) {
            // already in another instance (this old one is being cleaned up late): leave that one alone, so its
            // instance, death count and script timers (scene warps) stay
            if (!reentranceAllowed) {
                charIds.remove(chr.getId());
            }
            if (chars.size() == 0) {
                clear(warp);
            }
            return;
        }

        //
        // set Instance of chr to null
        //
        chr.setInstance(null); // Allowed usage of 'deprecated' method.
        chr.setHealReceivedPercent(100); // a boss's healing cut (Seren's Dusk) ends with the instance

        //
        // Stop Events in their scriptManager
        //
        chr.getScriptManager().stopEvents();

        //
        // set DeathCount to -1
        //
        chr.setDeathCount(-1);

        //
        // warp character to the return field
        //
        if (warp) {
            if (!isExitMap(returnFieldId)) {
                returnFieldId = getExitFieldId();
            }
            chr.warp(returnFieldId, portalId);
        }
        if (resetCooldowns) {
            chr.resetAllSkillCoolTimes();
        }

        //
        // no re-entrance allowed due to the way the character left the instance
        //
        if (!reentranceAllowed) {
            charIds.remove(chr.getId());
        }

        //Cleanse of all debuffs
        chr.getTemporaryStatManager().removeAllDebuffs();

        if (chr.getTemporaryStatManager().hasStat(CapDebuff)) {
            chr.getTemporaryStatManager().removeStat(CapDebuff);
        }

        // If instance is now empty, clear
        if (chars.size() == 0) {
            clear(warp);
        }

        var instanceHelper = InstanceHelperFactory.getHelperByInstance(this);
        if (instanceHelper != null) {
            instanceHelper.onChrRemoved(chr);
        }
    }

    public void clear() {
        clear(true);
    }

    public void clear(boolean warp) {
        //
        // Deletion process already called
        // -> Don't clear a second time
        //
        if (isBeingDeleted) {
            return;
        }

        //
        // Deletion process begins, thus set 'being deleted' as true.
        //
        isBeingDeleted = true;

        //
        // Trigger 'onInstanceCleared' event for things that have to be done upon clearing an instance
        //
        var instanceHelper = InstanceHelperFactory.getHelperByInstance(this);
        if (instanceHelper != null) {
            instanceHelper.onInstanceCleared(this);
        }

        //
        // Remove each remaining character from the instance
        //
        for (var chr : new HashSet<>(getChars())) {
            removeChar(chr, warp);
        }

        //
        // Clear chars list
        //
        getChars().clear();

        // a party a boss entry made for a lone player is only for the fight (officially no party is made)
        if (party != null && party.isBossSolo() && party.getMembers().size() <= 1 && party.getWorld() != null) {
            party.disband();
        }

        //
        // Stop Instance events
        //
        stopEvents();

        //
        // Clear fields
        //
        fields.values().forEach(Field::clear);
        fields.clear();

        //
        // Remove instance from channel
        //
        if (channel != null) {
            channel.removeInstance(this);
        }
    }

    /**
     * Stops all events of this Instance, and each of the eligible Char's ScriptManager's events.
     */
    public void stopEvents() {
        EventManager.stopTimer(getWarpOutTimer());
        for (Char chr : getChars()) {
            chr.getScriptManager().stopEvents();
        }
    }

    /**
     * Sets the timeout of this Instance, after which every Char will be forced out. Creates a Clock for everyone.
     *
     * @param seconds the amount of seconds until every Char is forced out
     */
    public void setTimeout(int seconds) {
        instanceStartTime = seconds;
        if (warpOutTimer != null) {
            warpOutTimer.cancel(true);
        }
        warpOutTimer = EventManager.addEvent(() -> clear(), seconds, TimeUnit.SECONDS);
        warpOutTimeout = System.currentTimeMillis() + seconds * 1000L;


        // Don't broadcast clock
        if (FieldConstants.isTowerOfOzField(getEnterFieldId())) {
            broadcast(CUIHandler.towerOfOzUpdate(false, (int) getRemainingTime(), instanceStartTime * 1000));
        } else {
            clockPacket = ClockPacket.secondsClock(seconds);
            broadcast(FieldPacket.clock(clockPacket));
        }
    }
    public void setTimeoutNoBroadcast(int seconds) {
        instanceStartTime = seconds;
        if (warpOutTimer != null) {
            warpOutTimer.cancel(true);
        }
        warpOutTimer = EventManager.addEvent(() -> clear(), seconds, TimeUnit.SECONDS);
        warpOutTimeout = System.currentTimeMillis() + seconds * 1000L;
    }

    public void setDojoTimer(int seconds) {
        if (warpOutTimer != null) {
            warpOutTimer.cancel(true);
        }
        warpOutTimer = EventManager.addEvent(() -> clear(), seconds, TimeUnit.SECONDS);
        forcedReturn = FieldConstants.MU_LUNG_DOJO_EXIT_FIELD;
        warpOutTimeout = System.currentTimeMillis() + seconds * 1000L;
        clockPacket = ClockPacket.DojoTimer(seconds, 0);
        broadcast(FieldPacket.clock(clockPacket));
    }

    /**
     * Returns the amount of seconds until this Instance closes.
     *
     * @return the remaining time
     */
    public long getRemainingTime() {
        var now = System.currentTimeMillis();
        if (pauseTime > 0L) { // if timer is currently paused
            return (warpOutTimeout - now) + (now - pauseTime);
        }
        return warpOutTimeout - now;
    }

    /**
     * Total amount the instance lasted. Taking into account when the timer was paused.
     *
     * @return
     */
    public long getTotalInstanceDuration() {
        var now = System.currentTimeMillis();
        var pausedTime = totalPauseTime + (pauseTime > 0 ? (now - pauseTime) : 0); // grab total paused time. and if currently paused also grab the current amount currently paused

        return (now - startTime) - pausedTime;
    }

    /**
     * Updates the warpOut Timer. To be used after resuming the timer.
     *
     */
    public void updateWarpOutTimer() {
        EventManager.stopTimer(warpOutTimer);
        warpOutTimer = EventManager.addEvent(() -> clear(), getRemainingTime(), TimeUnit.MILLISECONDS);
    }

    /**
     * Broadcasts a Packet to everyone in this Instance.
     *
     * @param outPacket the Packet to send
     */
    public void broadcast(OutPacket outPacket) {
        for (Char chr : getChars()) {
            chr.write(outPacket);
        }
    }

    /**
     * Warps all chars in the Instance.
     *
     * @param fieldId
     */
    public void warp(int fieldId) {
        for (Char chr : getChars()) {
            chr.warp(fieldId);
        }
    }

    /**
     * Broadcast a message to all chars in the instance.
     *
     * @param msg
     */
    public void chatMessage(String msg) {
        chatMessage(ChatType.SystemNotice, msg);
    }

    /**
     * Broadcast a message to all chars in the instance.
     *
     * @param type
     * @param msg
     */
    public void chatMessage(ChatType type, String msg) {
        for (Char chr : getChars()) {
            chr.chatMessage(type, msg);
        }
    }

    public void setBossCooldown(BossCooldown bossCooldown) {
        markBossRun();
        for (Char chr : getChars()) {
            chr.getAccount().setBossCooldown(bossCooldown, true);
        }
    }

    public void pauseTimer() {
        if (pauseTime > 0L) { // Clock is already paused
            return;
        }
        pauseTime = Util.getCurrentTimeLong(); // save the start of the pause. Later to be calculated to know how much time has been added to the instance.
        EventManager.stopTimer(warpOutTimer); // stop the warpout timer.
    }

    public void resumeTimer() {
        // Clock is not paused
        if (pauseTime == 0L) {
            return;
        }

        var now = Util.getCurrentTimeLong(); // calculate the duration paused (ms)
        var pausedTime = now - pauseTime; // calculate the duration paused (ms)

        warpOutTimeout += pausedTime; // add amount of time (ms) that the clock has been paused to the warpOutTimeout
        totalPauseTime += pausedTime;

        // important order!
        // first signify as 'back running' before updateWarpOutTimer; as it uses getRemainingTime. which adds the paused time if it's paused.
        pauseTime = 0L; // signify the clock is not paused

        updateWarpOutTimer();
    }

    public void update() {
        new HashSet<>(getFields().values()).forEach(Field::update);
    }
}
