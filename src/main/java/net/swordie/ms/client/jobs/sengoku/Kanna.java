package net.swordie.ms.client.jobs.sengoku;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.CharacterStat;
import net.swordie.ms.client.character.items.BodyPart;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.ActorPacket;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.client.character.skills.debuff.DebuffUtil;
import net.swordie.ms.life.Haku;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Kanna as rewritten in v266 (v271 data): Soul-Shatter Talisman attacks, the Shikigami Oni / Tengu / Orochi and their
 * [Order] skills, Haku the Familiar, barriers, Call of Unity; one-handed Spirit Walker Fan (1254xxx) with a Talisman
 * secondary (1354300+).
 * The Shikigami and Haku are actors of the client's WZ2 system (see {@link ActorPacket}): the server creates an
 * AKannaController and one AKannaShikigami_* per summoned Shikigami on the user's channel; the owner's client gets
 * them as autonomous, everyone else as simulated. Haku (fox) is always there. The v271 client only plays the
 * replicated Location, so the server walks them beside the summoner itself ({@link #followTick()}).
 * <p>
 * AKannaController slots: 20-24 Shikigamis[EKannaShikigami] (object refs), 25 bEnabledHakumanFinalAttack. RPCs:
 * 50 Client_SetHakumanFinalAttack(byte), 51 Client_ProcessHakumanAssistAttack(), 52 Client_PlayResonanceScreenEffect(byte),
 * 53 Broadcast_PlayResonanceCharEffect(byte), 55 Server_SetHakumanFinalAttack(byte),
 * 56 Server_BindBarrierProjectileDestroyed(object ref, 2 floats).
 * Slot 25 is the "Hakumenkonmou Juubi is up" flag: while it is set the client turns the Juubi key into the Final Blow
 * (400021157), fires the Final Blow by itself once the Juubi buff (IndieLoopEffect from 400021017) is gone, and runs
 * each Client_ProcessHakumanAssistAttack as a Light Shockwave (400021018).
 * AKannaShikigami slots (ACharacter base 1-56): 57 bDrawDebug, 58 ShikigamiType, 59 ShikigamiName, 60 KannaController,
 * 61 bForcedStandBy, 62 SummonerLastAttackTime (double), 63 TargetMobID, 64 bReadyToWaitAction, 65 StandStartedTime
 * (float), 66 ActiveBuffEffects. RPCs: 59 SendServerInput, 60 SendServerMove, 64 MulticastServerMove,
 * 65 BroadcastServerMove, 74 Broadcast_PrepareTeleport(byte), 89 Server_SetShikigamiVisibility,
 * 90 Server_SetShikigamiLeft, 91 Server_PrepareTeleport(byte), 92 Server_TeleportToSummoner(),
 * 93 Server_TeleportToTargetMob, 94 Server_TeleportByReservedAttackSkill, 95 Server_NotifyReserveAttackSkill(20 bytes),
 * 96 Server_ProcessAttackSkill(), 97 Server_StartMoveAction(); Haku 98-100 Broadcast_PlayAttackAnim / PlayHitAnim /
 * PlayModeChange, 101 Client_HideHaku(int); Oni 98 Broadcast_FuriousRushStart(int, 2 floats, 2 floats),
 * 99 Server_FuriousRushStart(int); Orochi 98-100 Client_VenomContagionAttack / NotifyVenomContagion /
 * NotifyVenomStackChanged. Every Server_* call is the owner's client reporting what its AI did; for a solo Kanna none
 * of them needs an answer. The bind barrier projectile is spawned by the client itself.
 */
public class Kanna extends Job {

    private static final Logger log = LogManager.getLogger(Kanna.class);

    // Beginner
    public static final int BLESSING_OF_THE_FIVE_ELEMENTS = 40020000;
    public static final int HAND_OF_FATE = 40020001;
    public static final int ELEMENTALISM = 40020002; // link skill
    public static final int HAKU_THE_FAMILIAR = 40021109;
    public static final int RETURN_OF_THE_FIVE_PLANETS = 40021227;

    // 1st
    public static final int SOUL_SHATTER_SCORES = 42001000;
    public static final int COMMUNE = 42001001;
    public static final int LEAP_TALISMAN = 42001002;
    public static final int SUMMON_ONI = 42001003;
    public static final int CELESTIAL_STRIKE = 42001004;
    public static final int LEAPING_STRIKE = 42001005;

    // 2nd
    public static final int SOUL_SHATTER_ICON = 42101000;
    public static final int EVILS_END_TALISMAN = 42101001;
    public static final int ORDER_SPINNING_STRIKE = 42101002;
    public static final int PEACOCKS_PLUMAGE = 42101004;

    // 3rd
    public static final int SOUL_SHATTER_SPIRIT = 42111000;
    public static final int SUMMON_TENGU = 42111001;
    public static final int PIERCING_SHOT = 42111002;
    public static final int SHIKIGAMI_CHARM_JUMP = 42111003;
    public static final int CURSE_WARD = 42111004;
    public static final int ORDER_STANDBY = 42111005;
    public static final int EXPLOSIVE_SOUL = 42111008;

    // 4th
    public static final int SOUL_SHATTER_DANCE = 42121000;
    public static final int SPIRIT_CHAINS = 42121001;
    public static final int HEART_WRECK_TALISMAN = 42121002;
    public static final int SUMMON_OROCHI = 42121003;
    public static final int CALL_OF_UNITY = 42121008;
    public static final int ORDER_EXECUTE = 42121009;
    public static final int ORDER_SHADE_FLETCHED_ARROW = 42121011;

    // Hyper
    public static final int ORDER_PULVERIZING_STRIKE = 42121052;
    public static final int AKATSUKI_BLESSING = 42121053;
    public static final int TRUE_NAME_REVELATION = 42121054;

    // HEXA / 6th
    public static final int HEXA_SUMMON_ONI = 42141003;
    public static final int HEXA_SUMMON_TENGU = 42141013;
    public static final int HEXA_SUMMON_OROCHI = 42141019;
    public static final int ADVENT_OF_CRIMSON = 42141500;
    public static final int FINAL_REST = 42141501;

    // V
    public static final int HAKUMENKONMOU_JUUBI_V = 400021017;
    public static final int HAKUMENKONMOU_LIGHT_SHOCKWAVE = 400021018;
    public static final int HAKUMENKONMOU_FINAL_BLOW = 400021157;
    public static final int UNLEASH_RADIANT_FLAME = 400021054;
    public static final int UNLEASH_BLACK_WINGED_DESTRUCTION = 400021078;
    public static final int UNLEASH_SOUL_SEARING_VENOM = 400021155;

    // v265 names other files still use. Most of these ids are gone from v271 or are other skills now (see above).
    public static final int AKATSUKI_HERO_KANNA = 42121006;
    public static final int BLOSSOMING_DAWN = 42121007;
    public static final int PRINCESS_VOW_KANNA = AKATSUKI_BLESSING;
    public static final int CIRCLE_OF_SUPRESSION = TRUE_NAME_REVELATION;
    public static final int SHIKIGAMI_HAUNTING_1_3 = 42001006;
    public static final int SHIKIGAMI_HAUNTING_2_3 = 42101102;
    public static final int SHIKIGAMI_HAUNTING_3_3 = 42111112;
    public static final int SHIKIGAMI_HAUNTING_4_3 = 42120028;
    public static final int SHIKIGAMI_CHARM = 42101104;
    public static final int EXORCIST_CHARM = 42101005;
    public static final int FALLING_SAKURA = 42121002;
    public static final int VANQUISHER_CHARM = 42121000;
    public static final int OROCHI_UNBOUND = 42121100;
    public static final int GHOST_YAKSHA_TRAINEE = 42001100;
    public static final int GHOST_YAKSHA_BROTHER = 42100000;
    public static final int GHOST_YAKSHA_LIEUTENANT = 42110000;
    public static final int GHOST_YAKSHA_BOSS = 42120001;
    public static final int KISHIN_SHOUKAN = 42111003;
    public static final int HAKU_REBORN = 42101002;
    public static final int SOUL_BOMB = 42100007;
    public static final int SOUL_SHEAR = 42111002;
    public static final int MANA_VEIN = 42001101;
    public static final int BLOSSOM_BARRIER = 42111004;
    public static final int WARDING_BARRIER = 42110001;
    public static final int BELLFLOWER_BARRIER = 42121005;
    public static final int BELLFLOWER_BARRIER_BOSS_RUSH_H = 42120051;
    public static final int YUKI_MUSUME_SHOUKAN = HAKUMENKONMOU_JUUBI_V;
    public static final int SPIRITS_DOMAIN = UNLEASH_RADIANT_FLAME;
    public static final int LIBERATED_SPIRIT_CIRCLE_SMALL = UNLEASH_BLACK_WINGED_DESTRUCTION;
    public static final int YAKSHA_GHOST_GREAT_ONI_LORD = 400021114;

    private static final int[] addedSkills = new int[]{
            RETURN_OF_THE_FIVE_PLANETS,
            BLESSING_OF_THE_FIVE_ELEMENTS,
            HAND_OF_FATE,
            HAKU_THE_FAMILIAR
    };

    /** Shikigami summon skill -> the Shikigami (its normal and HEXA skill count as one). */
    private static final Map<Integer, Integer> SHIKIGAMI = Map.of(
            SUMMON_ONI, SUMMON_ONI, HEXA_SUMMON_ONI, SUMMON_ONI,
            SUMMON_TENGU, SUMMON_TENGU, HEXA_SUMMON_TENGU, SUMMON_TENGU,
            SUMMON_OROCHI, SUMMON_OROCHI, HEXA_SUMMON_OROCHI, SUMMON_OROCHI
    );

    /** Shikigami -> when its summon time runs out. */
    private final Map<Integer, Long> shikigamiUntil = new HashMap<>();

    // Actors

    public static final int CONTROLLER_HASH = 0xa5c63793;
    private static final int ACTOR_ID_BASE = 0x30000000;
    // EKannaShikigami: HakuFox 0, HakuHuman 1, Oni 2, Tengu 3, Orochi 4 (Shikigamis[] index and ShikigamiType)
    private static final int HAKU_FOX = 0, ONI = 2, TENGU = 3, OROCHI = 4;
    private static final int[] SHIKIGAMI_HASH = {0xf21a3e0f, 0xf21a3e0f, 0x4c2eee9c, 0x1663d3c8, 0xad0d008a};
    private static final int SLOT_SHIKIGAMIS = 20;
    private static final int SLOT_HAKUMAN_FINAL_ATTACK = 25;
    private static final int SLOT_SHIKIGAMI_TYPE = 58;
    private static final int SLOT_KANNA_CONTROLLER = 60;
    private static final int SLOT_FORCED_STAND_BY = 61;
    private static final int SLOT_SUMMONER_LAST_ATTACK = 62;
    private static final int SLOT_TARGET_MOB = 63;
    /** Attacks closer together than this do not need another SummonerLastAttackTime update. */
    private static final int SUMMONER_ATTACK_INTERVAL = 250;
    private static final int RPC_CLIENT_SET_HAKUMAN_FINAL_ATTACK = ActorPacket.BASE_RPC_COUNT;
    private static final int RPC_CLIENT_PROCESS_HAKUMAN_ASSIST_ATTACK = ActorPacket.BASE_RPC_COUNT + 1;
    private static final int RPC_SERVER_SET_HAKUMAN_FINAL_ATTACK = ActorPacket.BASE_RPC_COUNT + 5;
    private static final int RPC_SERVER_BIND_BARRIER_DESTROYED = ActorPacket.BASE_RPC_COUNT + 6;
    // Final Blow data (400021157): dummyStr9 ms between Light Shockwaves, dummyStr10 of them; SkillInfo does not read
    // dummyStr, so the values are kept here
    private static final int JUUBI_SHOCKWAVE_INTERVAL = 1000;
    private static final int JUUBI_SHOCKWAVES = 15;
    /** The client fires the Final Blow itself when the buff ends; the flag is cleared then, or after this at most. */
    private static final int JUUBI_FINAL_BLOW_GRACE = 3000;
    private static final int RPC_BROADCAST_PREPARE_TELEPORT = 74;
    private static final int RPC_SERVER_PREPARE_TELEPORT = 91;
    private static final int RPC_SERVER_TELEPORT_TO_SUMMONER = 92;
    /** The actors stand this far behind the summoner (opposite their facing). */
    private static final int FOLLOW_DISTANCE = 45;
    private static final int FOLLOW_INTERVAL = 150;
    public static final int TENGU_STRIKE = 42111100;
    public static final int BELLFLOWER_BARRIER_2 = 80001839;
    public static final int BLOSSOM_BARRIER_2 = 80001840;
    public static final int HONNOU_JI_WESTERN_GROUNDS_FIELD = 807100100;
    /** Summon skill's Shikigami -> EKannaShikigami. */
    private static final Map<Integer, Integer> SHIKIGAMI_TYPE = Map.of(SUMMON_ONI, ONI, SUMMON_TENGU, TENGU,
            SUMMON_OROCHI, OROCHI);

    /** EKannaShikigami -> where it stands (the summon spot, or the summoner after a teleport). */
    private final Map<Integer, Position> actorPos = new HashMap<>();
    /** Where the controller was last replicated: the owner's client anchors the Shikigami AI on it. */
    private Position controllerPos;
    private final Map<Integer, ScheduledFuture> actorTimers = new HashMap<>();
    private ScheduledFuture followTimer;
    /** When the summoner last attacked, on the actors' replication clock; the Shikigami AI stands by if this is stale. */
    private double summonerLastAttack;
    /** The mob the summoner last attacked: the Shikigami AI walks to it. */
    private int targetMobID;
    private boolean hakumanFinalAttack;
    private boolean loggedRpc;
    /** EKannaShikigami -> when its summon time runs out. */
    private final Map<Integer, Long> actorUntil = new HashMap<>();
    private long juubiUntil;
    private ScheduledFuture juubiShockwaves;
    private ScheduledFuture juubiEnd;
    private int juubiShockwavesLeft;

    public Kanna(Char chr) {
        super(chr);
        if (isHandlerOfJob(chr.getJob())) {
            for (int id : addedSkills) {
                if (!chr.hasSkill(id) || chr.getSkill(id).getCurrentLevel() == 0) {
                    Skill skill = SkillData.getSkillDeepCopyById(id);
                    if (skill != null) {
                        skill.setCurrentLevel(skill.getMasterLevel());
                        chr.addSkill(skill);
                    }
                }
            }
            if (!chr.getQuestManager().hasQuestCompleted(57439)) {
                chr.getQuestManager().completeQuest(57439, false); // required to job adv
            }
        }
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isKanna(id);
    }

    /** Haku is a client actor in v271 (AKannaShikigami_Haku); the v265 skill-pet / fox-man Haku is not used. */
    public Haku getHaku() {
        return null;
    }

    // v265 Haku actions (FOX_MAN_ACTION_SET_USE_REQUEST): no such skills in v271
    public void hakuFoxFire() {
    }

    public void hakuHakuBlessing() {
    }

    public void hakuBreathUnseen() {
    }

    public void hakuHakusGift() {
    }

    /** v265 Great Oni Lord bookkeeping (Summon.onRemoved); v271 Kanna has no Ghost Yaksha summons. */
    public void updateGreatOniLord(int time) {
    }

    /** Shikigami whose summon time has not run out. */
    public int getActiveShikigamiCount() {
        long now = System.currentTimeMillis();
        shikigamiUntil.values().removeIf(t -> t <= now);
        return shikigamiUntil.size();
    }


    private int actorId(int type) {
        return ACTOR_ID_BASE | ((chr.getId() & 0x00FFFFFF) << 3) | type;
    }

    public int getControllerId() {
        return actorId(7);
    }

    public boolean isActorId(int objectId) {
        return (objectId & ~7) == (ACTOR_ID_BASE | ((chr.getId() & 0x00FFFFFF) << 3));
    }

    /** Shikigami on the channel: Haku always, the others while summoned. */
    private boolean isActorUp(int type) {
        return type == HAKU_FOX || actorTimers.containsKey(type);
    }

    private void encodeShikigami(OutPacket outPacket, int type, byte role) {
        ActorPacket.beginObject(outPacket, actorId(type), SHIKIGAMI_HASH[type]);
        ActorPacket.encodeActorBase(outPacket, chr.getUserId(), chr.getId(), role);
        Position pos = actorPos.getOrDefault(type, chr.getPosition());
        if (pos != null) {
            ActorPacket.encodeLocation(outPacket, pos.getX(), pos.getY());
        }
        outPacket.encodeShort(SLOT_SHIKIGAMI_TYPE);
        outPacket.encodeByte(type);
        ActorPacket.encodeObjectRef(outPacket, SLOT_KANNA_CONTROLLER, getControllerId());
        outPacket.encodeShort(SLOT_FORCED_STAND_BY);
        outPacket.encodeByte(0);
        outPacket.encodeShort(SLOT_TARGET_MOB);
        outPacket.encodeInt(targetMobID);
        ActorPacket.endObject(outPacket);
    }

    private void encodeController(OutPacket outPacket, byte role, boolean refs) {
        ActorPacket.beginObject(outPacket, getControllerId(), CONTROLLER_HASH);
        ActorPacket.encodeActorBase(outPacket, chr.getUserId(), chr.getId(), role);
        Position pos = chr.getPosition();
        if (pos != null) {
            ActorPacket.encodeLocation(outPacket, pos.getX(), pos.getY());
        }
        if (refs) {
            for (int type = 0; type < SHIKIGAMI_HASH.length; type++) {
                ActorPacket.encodeObjectRef(outPacket, SLOT_SHIKIGAMIS + type, isActorUp(type) ? actorId(type) : 0);
            }
        }
        outPacket.encodeShort(SLOT_HAKUMAN_FINAL_ATTACK);
        outPacket.encodeByte(hakumanFinalAttack);
        ActorPacket.endObject(outPacket);
    }

    /**
     * The controller and every Shikigami that is up, without the stream's closing 0. The controller comes first (the
     * Shikigami point at it) and again after them with its Shikigami references.
     */
    public void encodeActors(OutPacket outPacket, byte role) {
        encodeController(outPacket, role, false);
        for (int type = 0; type < SHIKIGAMI_HASH.length; type++) {
            if (isActorUp(type)) {
                encodeShikigami(outPacket, type, role);
            }
        }
        encodeController(outPacket, role, true);
    }

    /** The actors for the remote enter-field block, if {@code chr} is a Kanna. */
    public static boolean encodeActorsFor(Char chr, OutPacket outPacket) {
        if (chr != null && JobConstants.isKanna(chr.getJob()) && chr.getJobHandler() instanceof Kanna kanna) {
            kanna.encodeActors(outPacket, ActorPacket.ROLE_SIMULATED);
            return true;
        }
        return false;
    }

    private byte[] actorBytes(byte role) {
        OutPacket buffer = new OutPacket();
        encodeActors(buffer, role);
        return buffer.getData();
    }

    /** Only the controller (Shikigamis[] and slot 25): the Shikigami keep the spot their AI moved them to. */
    private void syncController(boolean toOwner) {
        Field field = chr.getField();
        if (field == null || chr.getClient() == null) {
            return;
        }
        if (toOwner) {
            OutPacket own = new OutPacket();
            encodeController(own, ActorPacket.ROLE_AUTONOMOUS, true);
            chr.write(ActorPacket.userActorUpdate(chr.getId(), own.getData()));
        }
        OutPacket others = new OutPacket();
        encodeController(others, ActorPacket.ROLE_SIMULATED, true);
        field.broadcastPacket(ActorPacket.userActorUpdate(chr.getId(), others.getData()), chr);
    }

    /** The owner gets the actors as autonomous, everyone else in the map as simulated. */
    private void syncActors() {
        Field field = chr.getField();
        if (field == null || chr.getClient() == null) {
            return;
        }
        chr.write(ActorPacket.userActorUpdate(chr.getId(), actorBytes(ActorPacket.ROLE_AUTONOMOUS)));
        field.broadcastPacket(ActorPacket.userActorUpdate(chr.getId(), actorBytes(ActorPacket.ROLE_SIMULATED)), chr);
    }

    /**
     * The owner's client runs the Shikigami AI (autonomous role), so its copy is the one that walks; the server only
     * keeps the simulated copies of the other players beside the summoner.
     */
    private void startFollow() {
        if (followTimer != null && !followTimer.isDone()) {
            return;
        }
        followTimer = EventManager.addFixedRateEvent(this::followTick, FOLLOW_INTERVAL, FOLLOW_INTERVAL);
    }

    private void followTick() {
        if (chr.getClient() == null || chr.getField() == null) {
            return;
        }
        Position p = chr.getPosition();
        if (p == null) {
            return;
        }
        int targetX = p.getX() + (chr.isLeft() ? FOLLOW_DISTANCE : -FOLLOW_DISTANCE);
        boolean moved = false;
        for (int type = 0; type < SHIKIGAMI_HASH.length; type++) {
            if (!isActorUp(type)) {
                continue;
            }
            Position cur = actorPos.get(type);
            if (cur == null) {
                actorPos.put(type, new Position(p.getX(), p.getY()));
                moved = true;
                continue;
            }
            int nx = stepToward(cur.getX(), targetX);
            int ny = stepToward(cur.getY(), p.getY());
            if (nx != cur.getX() || ny != cur.getY()) {
                cur.setX(nx);
                cur.setY(ny);
                moved = true;
            }
        }
        if (moved) {
            syncPositions();
        }
    }

    private static int stepToward(int from, int to) {
        int delta = to - from;
        if (Math.abs(delta) <= 2) {
            return to;
        }
        return from + delta / 2;
    }

    /**
     * Location updates: the controller's, as the anchor of the owner's Shikigami AI, and the simulated copies of the
     * other players. The owner's own Shikigami are left alone, since its client walks them.
     */
    private void syncPositions() {
        Field field = chr.getField();
        if (field == null || chr.getClient() == null) {
            return;
        }
        Position p = chr.getPosition();
        if (p != null && !p.equals(controllerPos)) {
            controllerPos = new Position(p.getX(), p.getY());
            OutPacket own = new OutPacket();
            encodeController(own, ActorPacket.ROLE_AUTONOMOUS, false);
            chr.write(ActorPacket.userActorUpdate(chr.getId(), own.getData()));
        }
        if (field.getCharsReadOnly().size() > 1) {
            field.broadcastPacket(ActorPacket.userActorUpdate(chr.getId(),
                    positionBytes(ActorPacket.ROLE_SIMULATED)), chr);
        }
    }

    /**
     * The Shikigami AI follows only while SummonerLastAttackTime (slot 62) is recent; without it the client puts them
     * to stand by after ToStandByTime and they never follow again. The property counts seconds on the replication
     * clock, so it needs a {@link ActorPacket#clockSync()} first. The attacked mob goes in TargetMobID (slot 63), which
     * the AI walks the Shikigami to.
     */
    private void markSummonerAttack(AttackInfo attackInfo) {
        if (chr.getClient() == null || chr.getField() == null) {
            return;
        }
        double now = ActorPacket.clockNow();
        if ((now - summonerLastAttack) * 1000 < SUMMONER_ATTACK_INTERVAL) {
            return;
        }
        List<Integer> mobIds = attackInfo.getMobIds();
        if (!mobIds.isEmpty()) {
            targetMobID = mobIds.get(0);
        }
        summonerLastAttack = now;
        OutPacket buffer = new OutPacket();
        for (int type = 0; type < SHIKIGAMI_HASH.length; type++) {
            if (!isActorUp(type)) {
                continue;
            }
            ActorPacket.beginObject(buffer, actorId(type), SHIKIGAMI_HASH[type]);
            ActorPacket.encodeActorBase(buffer, chr.getUserId(), chr.getId(), ActorPacket.ROLE_AUTONOMOUS);
            buffer.encodeShort(SLOT_SUMMONER_LAST_ATTACK);
            buffer.encodeDouble(summonerLastAttack);
            buffer.encodeShort(SLOT_TARGET_MOB);
            buffer.encodeInt(targetMobID);
            ActorPacket.endObject(buffer);
        }
        if (buffer.getData().length > 0) {
            chr.write(ActorPacket.clockSync());
            chr.write(ActorPacket.userActorUpdate(chr.getId(), buffer.getData()));
        }
    }

    private byte[] positionBytes(byte role) {
        OutPacket buffer = new OutPacket();
        encodeController(buffer, role, false);
        for (int type = 0; type < SHIKIGAMI_HASH.length; type++) {
            if (isActorUp(type)) {
                encodeShikigami(buffer, type, role);
            }
        }
        return buffer.getData();
    }

    private void summonActor(int type, int seconds) {
        ScheduledFuture old = actorTimers.remove(type);
        if (old != null) {
            old.cancel(false);
        }
        Position p = chr.getPosition();
        if (p != null) {
            actorPos.put(type, new Position(p.getX(), p.getY()));
        }
        long ms = Math.max(1, seconds) * 1000L;
        actorUntil.put(type, System.currentTimeMillis() + ms);
        actorTimers.put(type, EventManager.addEvent(() -> unsummonActor(type), ms));
        syncActors();
        startFollow();
    }

    /** Hakumenkonmou Juubi: Shikigami summon time does not run while Haku is awake. */
    private void extendShikigami(long ms) {
        long now = System.currentTimeMillis();
        for (int type : new ArrayList<>(actorTimers.keySet())) {
            ScheduledFuture old = actorTimers.get(type);
            if (old != null) {
                old.cancel(false);
            }
            long until = actorUntil.getOrDefault(type, now) + ms;
            actorUntil.put(type, until);
            actorTimers.put(type, EventManager.addEvent(() -> unsummonActor(type), Math.max(1, until - now)));
        }
        shikigamiUntil.replaceAll((k, t) -> t > now ? t + ms : t);
    }

    private byte[] destroyBytes(int type, byte role) {
        OutPacket buffer = new OutPacket();
        encodeController(buffer, role, true);
        ActorPacket.destroyObject(buffer, actorId(type), SHIKIGAMI_HASH[type]);
        return buffer.getData();
    }

    private void unsummonActor(int type) {
        actorTimers.remove(type);
        actorUntil.remove(type);
        actorPos.remove(type);
        Field field = chr.getField();
        if (field == null || chr.getClient() == null) {
            return;
        }
        chr.write(ActorPacket.userActorUpdate(chr.getId(), destroyBytes(type, ActorPacket.ROLE_AUTONOMOUS)));
        field.broadcastPacket(ActorPacket.userActorUpdate(chr.getId(), destroyBytes(type, ActorPacket.ROLE_SIMULATED)),
                chr);
    }

    /** An RPC the owner's client called on one of this Kanna's actors (USER_ACTOR_RPC). */
    public void handleRpc(int objectId, int rpc, byte[] args) {
        ByteBuffer in = ByteBuffer.wrap(args).order(ByteOrder.LITTLE_ENDIAN);
        int type = objectId & 7;
        if (objectId == getControllerId()) {
            if (rpc == RPC_SERVER_SET_HAKUMAN_FINAL_ATTACK && in.remaining() >= 1) {
                hakumanFinalAttack = in.get() != 0;
                if (!hakumanFinalAttack) {
                    stopShockwaves();
                }
                syncController(false);
                return;
            }
            if (rpc == RPC_SERVER_BIND_BARRIER_DESTROYED) {
                // the client's own projectile reached its target; the bind and the barrier hits come as attacks
                log.debug("[Kanna] {} bind barrier projectile done, {} arg bytes", chr.getName(), args.length);
                return;
            }
        } else if (type < SHIKIGAMI_HASH.length && isActorUp(type)) {
            if (rpc == RPC_SERVER_PREPARE_TELEPORT && in.remaining() >= 1) {
                if (chr.getField() != null) {
                    chr.getField().broadcastPacket(ActorPacket.userActorRpc(chr.getId(), objectId,
                            RPC_BROADCAST_PREPARE_TELEPORT, new byte[]{in.get()}), chr);
                }
                return;
            }
            if (rpc == RPC_SERVER_TELEPORT_TO_SUMMONER) {
                Position p = chr.getPosition();
                if (p != null) {
                    actorPos.put(type, new Position(p.getX(), p.getY()));
                    syncActors();
                }
                return;
            }
        }
        if (!loggedRpc) {
            loggedRpc = true;
            log.info("[Kanna] {} actor RPC: object {}, RPC {}, {} arg bytes (not handled; later ones at debug)",
                    chr.getName(), Integer.toHexString(objectId), rpc, args.length);
        } else {
            log.debug("[Kanna] {} actor RPC: object {}, RPC {}, {} arg bytes", chr.getName(),
                    Integer.toHexString(objectId), rpc, args.length);
        }
    }

    @Override
    public void onWarp(Field oldField, Field newField) {
        super.onWarp(oldField, newField);
        if (newField == null || !isHandlerOfJob(chr.getJob())) {
            return;
        }
        // the client drops the user's actors with the map; the summoned ones come along to the new spot
        targetMobID = 0;
        controllerPos = null;
        for (int type : actorTimers.keySet()) {
            actorPos.remove(type);
        }
        EventManager.addEvent(() -> {
            if (chr.getClient() != null && chr.getField() == newField) {
                syncActors();
                startFollow();
            }
        }, 1000);
    }

    @Override
    public void stopTimers() {
        for (ScheduledFuture f : actorTimers.values()) {
            f.cancel(false);
        }
        actorTimers.clear();
        actorUntil.clear();
        actorPos.clear();
        EventManager.stopTimer(followTimer);
        followTimer = null;
        stopShockwaves();
        if (juubiEnd != null) {
            juubiEnd.cancel(false);
            juubiEnd = null;
        }
        juubiUntil = 0;
        hakumanFinalAttack = false;
        super.stopTimers();
    }


    // Hakumenkonmou Juubi ---------------------------------------------------------------------------------------------

    private void setHakumanFinalAttack(boolean on) {
        hakumanFinalAttack = on;
        if (chr.getClient() != null) {
            chr.write(ActorPacket.userActorRpc(chr.getId(), getControllerId(), RPC_CLIENT_SET_HAKUMAN_FINAL_ATTACK,
                    new byte[]{(byte) (on ? 1 : 0)}));
        }
        syncController(false);
    }

    private void stopShockwaves() {
        if (juubiShockwaves != null) {
            juubiShockwaves.cancel(false);
            juubiShockwaves = null;
        }
        juubiShockwavesLeft = 0;
    }

    /**
     * Haku awakens for time sec: the buff the client checks (IndieLoopEffect, reason 400021017), the slot 25 flag, a
     * Light Shockwave every interval, Shikigami summon time held.
     */
    private void startJuubi(int slv) {
        long now = System.currentTimeMillis();
        SkillInfo si = SkillData.getSkillInfoById(HAKUMENKONMOU_JUUBI_V);
        if (si == null || now < juubiUntil) {
            return;
        }
        int sec = Math.max(1, si.getValue(time, Math.max(1, slv)));
        juubiUntil = now + sec * 1000L;
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        tsm.putCharacterStatValue(IndieLoopEffect, new Option(chr.getId(), HAKUMENKONMOU_JUUBI_V, sec));
        tsm.sendSetStatPacket();
        setHakumanFinalAttack(true);
        extendShikigami(sec * 1000L);
        stopShockwaves();
        juubiShockwavesLeft = JUUBI_SHOCKWAVES;
        juubiShockwaves = EventManager.addFixedRateEvent(this::shockwave, JUUBI_SHOCKWAVE_INTERVAL,
                JUUBI_SHOCKWAVE_INTERVAL);
        if (juubiEnd != null) {
            juubiEnd.cancel(false);
        }
        juubiEnd = EventManager.addEvent(() -> {
            stopShockwaves();
            if (hakumanFinalAttack && System.currentTimeMillis() >= juubiUntil) {
                setHakumanFinalAttack(false);
            }
        }, sec * 1000L + JUUBI_FINAL_BLOW_GRACE);
    }

    private void shockwave() {
        if (!hakumanFinalAttack || juubiShockwavesLeft <= 0 || System.currentTimeMillis() >= juubiUntil
                || chr.getClient() == null || chr.getField() == null) {
            stopShockwaves();
            return;
        }
        juubiShockwavesLeft--;
        chr.write(ActorPacket.userActorRpc(chr.getId(), getControllerId(), RPC_CLIENT_PROCESS_HAKUMAN_ASSIST_ATTACK,
                new byte[0]));
    }

    /** The Final Blow ends the awakening; the flag goes first, or the client would fire a second one. */
    private void finalBlow() {
        stopShockwaves();
        juubiUntil = 0;
        if (hakumanFinalAttack) {
            setHakumanFinalAttack(false);
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (tsm.hasAOptByCTSAndSkill(IndieLoopEffect, HAKUMENKONMOU_JUUBI_V)) {
            tsm.removeStatsBySkill(HAKUMENKONMOU_JUUBI_V);
        }
    }

    /** Barrier: Spirit Chains: its one target (dummyStr4; the client picks the highest Max HP) is bound for time sec. */
    private void spiritChains(AttackInfo attackInfo) {
        SkillInfo si = SkillData.getSkillInfoById(SPIRIT_CHAINS);
        Field field = chr.getField();
        if (si == null || field == null || attackInfo.mobAttackInfo.isEmpty()) {
            return;
        }
        Mob target = null;
        for (int id : attackInfo.getMobIds()) {
            if (field.getLifeByObjectID(id) instanceof Mob m && (target == null || m.getMaxHp() > target.getMaxHp())) {
                target = m;
            }
        }
        if (target == null) {
            return;
        }
        Option o = new Option();
        o.nOption = 1;
        o.rOption = SPIRIT_CHAINS;
        o.tOption = si.getValue(time, Math.max(1, attackInfo.slv));
        DebuffUtil.applyDebuffOnMobs(chr, MobStat.Freeze, o, List.of(target.getObjectId()));
    }


    @Override
    public void handleLevelUp() {
        super.handleLevelUp();
        // v271 Kanna has no advancement quests (the 685xx story is done at creation): the job comes with the level,
        // also with job_advancement_by_quest_only
        advanceByLevel(JobConstants.JobEnum.KANNA_1.getJobId(), JobConstants.JobEnum.KANNA_2.getJobId(),
                JobConstants.JobEnum.KANNA_3.getJobId(), JobConstants.JobEnum.KANNA_4.getJobId());
    }


    // Attack related methods ------------------------------------------------------------------------------------------

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        markSummonerAttack(attackInfo);
        switch (attackInfo.skillId) {
            case HAKUMENKONMOU_JUUBI_V:
                startJuubi(attackInfo.slv);
                break;
            case HAKUMENKONMOU_FINAL_BLOW:
                finalBlow();
                break;
            case SPIRIT_CHAINS:
                spiritChains(attackInfo);
                break;
        }
        super.handleAttack(c, attackInfo);
    }


    // Skill related methods -------------------------------------------------------------------------------------------

    private static final int CRIMSON_FLAME = 42100008;
    private static final int IVORY_FANG = 42120015;
    /** Crimson Flame's boss damage while Oni is out (GMS sends 60; its Skill.wz entry has no values). */
    private static final int CRIMSON_FLAME_NBDR = 60;

    /**
     * The buffs GMS sends with a Shikigami: its icon for the summon's time; Oni: IndieNBDR from Crimson
     * Flame; Orochi: IndieDamReduceR x and, with Ivory Fang, no knock-back.
     */
    private void shikigamiBuffs(int skillId, int shikigami, SkillInfo si, int slv, int dur) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        tsm.putCharacterStatValue(IndieBuffIcon, new Option(1, skillId, dur));
        if (shikigami == SUMMON_ONI && chr.hasSkill(CRIMSON_FLAME)) {
            tsm.putCharacterStatValue(IndieNBDR, new Option(CRIMSON_FLAME_NBDR, CRIMSON_FLAME, dur));
        } else if (shikigami == SUMMON_OROCHI) {
            if (si.getValue(x, slv) > 0) {
                tsm.putCharacterStatValue(IndieDamReduceR, new Option(si.getValue(x, slv), skillId, dur));
            }
            if (chr.hasSkill(IVORY_FANG)) {
                tsm.putCharacterStatValue(IndieNoKnockBack, new Option(1, IVORY_FANG, dur));
            }
        }
        tsm.sendSetStatPacket();
    }

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        super.handleSkill(chr, tsm, skillId, slv, inPacket, skillUseInfo);
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        if (si == null) {
            return;
        }
        if (SHIKIGAMI.containsKey(skillId)) {
            int shikigami = SHIKIGAMI.get(skillId);
            int dur = si.getValue(time, slv);
            shikigamiUntil.put(shikigami, System.currentTimeMillis() + dur * 1000L);
            summonActor(SHIKIGAMI_TYPE.get(shikigami), dur);
            shikigamiBuffs(skillId, shikigami, si, slv, dur);
            return;
        }
        switch (skillId) {
            case HAKUMENKONMOU_JUUBI_V:
                startJuubi(slv);
                break;
            case HAKUMENKONMOU_FINAL_BLOW:
                finalBlow();
                break;
            case CURSE_WARD:
                // invincible for time sec
                tsm.putCharacterStatValue(IndieNotDamaged, new Option(1, skillId, si.getValue(time, slv)), true);
                tsm.sendSetStatPacket();
                break;
            case CALL_OF_UNITY: {
                // a stack per summoned Shikigami (up to 3): Damage x% and Critical Rate y% per stack
                int stacks = Math.max(1, Math.min(3, getActiveShikigamiCount()));
                int dur = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, new Option(si.getValue(x, slv) * stacks, skillId, dur));
                if (si.getValue(y, slv) > 0) {
                    tsm.putCharacterStatValue(IndieCr, new Option(si.getValue(y, slv) * stacks, skillId, dur));
                }
                tsm.sendSetStatPacket();
                break;
            }
        }
    }

    @Override
    public void setCharCreationStats(Char chr) {
        super.setCharCreationStats(chr);
        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        cs.setPosMap(HONNOU_JI_WESTERN_GROUNDS_FIELD); // Kanna's own tutorial (kannaTutoDirection), not Hayato's
        Hayato.startInTownIfNoTutorial(chr, HONNOU_JI_WESTERN_GROUNDS_FIELD, JobConstants.JobEnum.KANNA_1.getJobId());
        if (chr.getEquippedInventory().getItemBySlot(BodyPart.Shield.getVal()) == null) {
            Item item = ItemData.getItemDeepCopy(1354300); // Talisman (v266+ secondary); hardcode-ok
            if (item != null) {
                item.setBagIndex(BodyPart.Shield.getVal());
                chr.getEquippedInventory().addItem(item);
            }
        }
    }
}
