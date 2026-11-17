package net.swordie.ms.client.jobs.jianghu;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.MobAttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.client.party.Party;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.ActorPacket;
import net.swordie.ms.connection.packet.Effect;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.JobConstants.JobEnum;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.world.field.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Lynn (Jianghu magician, Memorial Staff). Job ids 17001, 17200-17212.
 * <p>
 * The spirit guides live in the client's ALynnController actor (see {@link ActorPacket}): Peck's bird, Sneak Attack,
 * Beak Strike and the Focal Target are its replicated properties, sent in the SET_FIELD buffer, the remote user's
 * enter-field block and USER_ACTOR_REPLICATION. The bird and Paeon's follow-ups attack through the normal attack
 * packets. Slots (AActor base 1-19): 20 FocusTargetID, 21 bEnableFocus, 22 GuardianFlags (u16),
 * 23 ActivatingPeckingSkillID, 24 ActivatingPeckingTargetMobID, 25 ActivatingPeckingSkillLevel,
 * 26 PeckingBladeAssaultEndTime (double, replication clock), 27 PeckingBladeAssaultLevel, 28 ActivatingAmbushSkillID,
 * 29-31 BlockCategorySkillTime (double), 32-34 BlockCategorySkillID, 35 FocusBuffList (not sent).
 * RPCs (50 base): 50 SetPeckingTarget(int mob), 51 OnPeckingDive(int mob, byte), 52 MulticastPeckingDive(int mob),
 * 53 CancelActivatingAmbush(), 54 RefreshSkillEnable(), 55 RequestRefreshFocusBuffList(), 56 CancelActivatingPecking(),
 * 57 Client_ActivateAmbush(int mob, int skill).
 */
public class Lynn extends Job {
    private static final Logger log = LogManager.getLogger(Lynn.class);

    public static final int STRIKE_1 = 172001000;
    public static final int FOCUS_HEAL = 172001001;
    public static final int FOCUS_HEAL_2 = 172001003; // Resonant: Heal
    public static final int PAN_UNITY = 172000001;
    public static final int PAN = 172000011; // Spirit Guide Unity: Pan (stack buff)
    public static final int PENNI_UNITY = 172100001;
    public static final int PENNI = 172100011; // Spirit Guide Unity: Penni
    public static final int STRIKE_2 = 172101000;
    public static final int SWEEP = 172101001;
    public static final int PECK = 172101003;
    public static final int PAEON_UNITY = 172110001;
    public static final int PAEON = 172110011; // Spirit Guide Unity: Paeon
    public static final int STRIKE_3 = 172111000;
    public static final int EARTH_PULVERIZATION = 172111001;
    public static final int EARTH_PULVERIZATION_FISSURE = 172111002;
    public static final int PECK_III = 172111003;
    public static final int SNEAK_ATTACK = 172111006;
    public static final int SNEAK_ATTACK_FOLLOW_UP = 172111007;
    public static final int PURIFY = 172111008;
    public static final int NATURE_PROVIDENCE = 172111009;
    public static final int FOCUS_HEAL_BOOST = 172120001;
    public static final int STRIKE_4 = 172121000;
    public static final int ROAR = 172121001;
    public static final int PECK_IV = 172121003;
    public static final int WHIRLWIND = 172121004;
    public static final int PREDATOR_BLOW = 172121006;
    public static final int MOTHER_NATURE_TOUCH = 172121008;
    public static final int HEROS_WILL = 172121011;
    public static final int RAID = 172121041;
    public static final int AURA_OF_DESTINY = 172121042; // indieDamR
    public static final int FOREST_PROTECTION = 172121043;
    public static final int FOREST_PROTECTION_LOCKOUT = 172121044;
    public static final int FOREST_PROTECTION_RESONANT = 172121045;
    public static final int HEXA_STRIKE = 172141000;
    public static final int HEXA_SNEAK_ATTACK = 172141001;
    public static final int HEXA_SNEAK_ATTACK_FOLLOW_UP = 172141002;
    public static final int HEXA_RAID = 172141003;
    public static final int HEXA_PECK = 172141004;
    public static final int HEXA_FOCUS_HEAL = 172141005; // HEXA [Focus] Heal
    public static final int HEXA_FOCUS_HEAL_RESONANT = 172141006;
    public static final int HEXA_FOREST_PROTECTION = 172141007;
    public static final int HEXA_FOREST_PROTECTION_RESONANT = 172141008;
    public static final int HEXA_FOREST_PROTECTION_LOCKOUT = 172141009;
    public static final int HEXA_MOTHER_NATURE_TOUCH = 172141010;
    public static final int SOURCE_FLOW = 172141500;

    // V skills
    public static final int BEAST_RAGE = 400021134;
    public static final int AWAKEN_EXHAUST = 400021136;
    public static final int BEAK_STRIKE = 400021137;
    public static final int FOCUS_AWAKEN = 400021138;
    public static final int NATURE_GRACE = 400021139;

    /** The reference tree's value for Purify's periodical activation. */
    private static final int PURIFY_PERIOD = 10;
    private static final int FATAL_NEGATION_MS = 1000; // hardcode-ok

    /** GuardianFlags as the reference tree sends them: idle, and while Sneak Attack waits (Paeon hidden). */
    private static final short GUARDIANS_IDLE = 113;
    private static final short GUARDIANS_AMBUSH = 125;
    private static final int CONTROLLER_ID_BASE = 0x20000000;
    private static final int RPC_SET_PECKING_TARGET = ActorPacket.BASE_RPC_COUNT + 0;
    private static final int RPC_ON_PECKING_DIVE = ActorPacket.BASE_RPC_COUNT + 1;
    private static final int RPC_MULTICAST_PECKING_DIVE = ActorPacket.BASE_RPC_COUNT + 2;
    private static final int RPC_CANCEL_AMBUSH = ActorPacket.BASE_RPC_COUNT + 3;
    private static final int RPC_REFRESH_SKILL_ENABLE = ActorPacket.BASE_RPC_COUNT + 4;
    private static final int RPC_REFRESH_FOCUS_BUFFS = ActorPacket.BASE_RPC_COUNT + 5;
    private static final int RPC_CANCEL_PECKING = ActorPacket.BASE_RPC_COUNT + 6;
    private static final int RPC_ACTIVATE_AMBUSH = ActorPacket.BASE_RPC_COUNT + 7;
    public static final int SUPPORT_FLIGHT = 172121005;

    /** Forest Protection zones by owner; party members inside get damage reduction and fatal damage negation. */
    private static final List<ForestZone> forestZones = new CopyOnWriteArrayList<>();
    /** Character id -> time the fatal damage negation can fire again. */
    private static final Map<Integer, Long> fatalNegationLockout = new ConcurrentHashMap<>();
    /** Character id -> time Awaken can be received again (Exhaust). */
    private static final Map<Integer, Long> awakenExhaust = new ConcurrentHashMap<>();

    private int focusTargetId;
    private short guardianFlags = GUARDIANS_IDLE;
    private int peckSkill;
    private int peckLevel;
    private int peckTargetMob;
    private double beakStrikeEnd;
    private int beakStrikeLevel;
    private boolean beakStrikeTurnedPeckOn;
    private int ambushSkill;
    private long ambushReadyAt;

    private ScheduledFuture focusHealTimer;
    private ScheduledFuture resonantHealTimer;
    private ScheduledFuture natureTouchTimer;
    private ScheduledFuture beakStrikeTimer;
    private ScheduledFuture forestTimer;

    public Lynn(Char chr) {
        super(chr);
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isLynn(id);
    }

    // Controller actor ------------------------------------------------------------------------------------------------

    public int getControllerId() {
        return CONTROLLER_ID_BASE | (chr.getId() & 0x0FFFFFFF);
    }

    /**
     * Writes the ALynnController object (without the stream's closing 0). The Beak Strike end time is only meaningful
     * after {@link ActorPacket#clockSync()}, so it is left out unless {@code withClock}.
     */
    public void encodeController(OutPacket outPacket, boolean withClock) {
        ActorPacket.beginObject(outPacket, getControllerId(), ActorPacket.LYNN_CONTROLLER_HASH);
        ActorPacket.encodeActorBase(outPacket, chr.getUserId(), chr.getId());
        outPacket.encodeShort(20);
        outPacket.encodeInt(getFocusTargetId());
        outPacket.encodeShort(21);
        outPacket.encodeByte(1);
        outPacket.encodeShort(22);
        outPacket.encodeShort(guardianFlags);
        outPacket.encodeShort(23);
        outPacket.encodeInt(peckSkill);
        outPacket.encodeShort(24);
        outPacket.encodeInt(peckTargetMob);
        outPacket.encodeShort(25);
        outPacket.encodeInt(peckLevel);
        boolean beak = withClock && beakStrikeEnd > ActorPacket.clockNow();
        outPacket.encodeShort(26);
        outPacket.encodeDouble(beak ? beakStrikeEnd : 0);
        outPacket.encodeShort(27);
        outPacket.encodeInt(beak ? beakStrikeLevel : 0);
        outPacket.encodeShort(28);
        outPacket.encodeInt(ambushSkill);
        for (int slot = 29; slot <= 31; slot++) {
            outPacket.encodeShort(slot);
            outPacket.encodeDouble(0);
        }
        for (int slot = 32; slot <= 34; slot++) {
            outPacket.encodeShort(slot);
            outPacket.encodeInt(0);
        }
        ActorPacket.endObject(outPacket);
    }

    /** The controller for the SET_FIELD buffer and the remote enter-field block, if {@code chr} is a Lynn. */
    public static boolean encodeControllerFor(Char chr, OutPacket outPacket) {
        if (chr != null && JobConstants.isLynn(chr.getJob()) && chr.getJobHandler() instanceof Lynn lynn) {
            lynn.encodeController(outPacket, false);
            return true;
        }
        return false;
    }

    private byte[] controllerBytes(boolean withClock) {
        OutPacket buffer = new OutPacket();
        encodeController(buffer, withClock);
        return buffer.getData();
    }

    /** Sends the controller's state to everyone in the map (the owner included). */
    private void syncController() {
        Field field = chr.getField();
        if (field == null) {
            return;
        }
        boolean withClock = beakStrikeEnd > ActorPacket.clockNow();
        if (withClock) {
            field.broadcastPacket(ActorPacket.clockSync());
        }
        field.broadcastPacket(ActorPacket.userActorUpdate(chr.getId(), controllerBytes(withClock)));
    }

    private void sendControllerToOwner() {
        boolean withClock = beakStrikeEnd > ActorPacket.clockNow();
        if (withClock) {
            chr.write(ActorPacket.clockSync());
        }
        chr.write(ActorPacket.userActorUpdate(chr.getId(), controllerBytes(withClock)));
    }

    public boolean isControllerId(int objectId) {
        return objectId == getControllerId();
    }

    /** An RPC the client called on this Lynn's controller (USER_ACTOR_RPC). */
    public void handleRpc(int rpc, byte[] args) {
        ByteBuffer in = ByteBuffer.wrap(args).order(ByteOrder.LITTLE_ENDIAN);
        switch (rpc) {
            case RPC_SET_PECKING_TARGET -> {
                // the owner's bird already has it; others see the dives through MulticastPeckingDive
                if (in.remaining() >= 4) {
                    peckTargetMob = in.getInt();
                }
            }
            case RPC_ON_PECKING_DIVE -> {
                if (in.remaining() >= 4 && chr.getField() != null) {
                    int mob = in.getInt();
                    byte[] dive = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(mob).array();
                    chr.getField().broadcastPacket(ActorPacket.userActorRpc(chr.getId(), getControllerId(),
                            RPC_MULTICAST_PECKING_DIVE, dive), chr);
                }
            }
            case RPC_CANCEL_AMBUSH -> setAmbush(0);
            case RPC_CANCEL_PECKING -> setPeck(0, 0);
            case RPC_REFRESH_SKILL_ENABLE, RPC_REFRESH_FOCUS_BUFFS -> sendControllerToOwner();
            default -> log.debug("[Lynn] {} controller RPC {} ({} bytes)", chr.getName(), rpc, args.length);
        }
    }

    // Focal Target ------------------------------------------------------------------------------------------------

    /** The replicated Focal Target: a party member's id, or Lynn's own (the client wants one of those in a party). */
    public int getFocusTargetId() {
        if (focusTargetId != 0 && chr.getParty() != null && chr.getParty().getPartyMemberByID(focusTargetId) != null) {
            return focusTargetId;
        }
        return chr.getId();
    }

    /** Sets the Focal Target (a party member, or 0 for Lynn herself). */
    public void setFocusTarget(int charId) {
        focusTargetId = charId == chr.getId() ? 0 : charId;
        syncController();
    }

    /** The Focal Target if it is a party member in this map, otherwise Lynn herself. */
    private Char focalTarget() {
        int id = getFocusTargetId();
        if (id != chr.getId() && chr.getField() != null) {
            Char target = chr.getField().getCharByID(id);
            if (target != null && !target.isDead()) {
                return target;
            }
        }
        return chr;
    }

    // Attacks ---------------------------------------------------------------------------------------------------------

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        int skillId = attackInfo.skillId;
        if (skillId == SWEEP) {
            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            int count = tsm.hasStat(StackOverTimeSkill) ? tsm.getOption(StackOverTimeSkill).nOption : 0;
            setSweep(Math.max(count - 1, 0));
        }
        super.handleAttack(c, attackInfo);
        List<MobAttackInfo> hits = attackInfo.mobAttackInfo;
        if (hits == null || hits.isEmpty()) {
            return;
        }
        // Peck goes after what Earth Pulverization hit (the reference tree's slot 23)
        if ((skillId == EARTH_PULVERIZATION) && peckSkill != 0) {
            MobAttackInfo mai = hits.get((int) (Math.random() * hits.size()));
            peckTargetMob = mai.mobId;
            syncController();
        }
        int unity = unityOf(skillId);
        if (unity != 0) {
            addUnityStack(unity);
            if (ambushSkill != 0 && !isSneakAttack(skillId)) {
                activateAmbush(hits.get(0).mobId);
            }
        }
    }

    /** The Spirit Guide Unity passive a skill counts for (Pan, Penni or Paeon), 0 for none. */
    private static int unityOf(int skillId) {
        return switch (skillId) {
            case STRIKE_1, STRIKE_2, STRIKE_3, STRIKE_4, HEXA_STRIKE, EARTH_PULVERIZATION,
                 EARTH_PULVERIZATION_FISSURE, ROAR -> PAN_UNITY;
            case SWEEP, PECK, PECK_III, PECK_IV, HEXA_PECK, WHIRLWIND, BEAK_STRIKE -> PENNI_UNITY;
            case SNEAK_ATTACK, SNEAK_ATTACK_FOLLOW_UP, HEXA_SNEAK_ATTACK, HEXA_SNEAK_ATTACK_FOLLOW_UP, PREDATOR_BLOW,
                 RAID, HEXA_RAID, BEAST_RAGE -> PAEON_UNITY;
            default -> 0;
        };
    }

    private static boolean isSneakAttack(int skillId) {
        return skillId == SNEAK_ATTACK || skillId == SNEAK_ATTACK_FOLLOW_UP || skillId == HEXA_SNEAK_ATTACK
                || skillId == HEXA_SNEAK_ATTACK_FOLLOW_UP;
    }

    /** Spirit Guide Unity: each hit with that guide's skill adds a Final Damage +w% stack (max v) for time sec. */
    private void addUnityStack(int unity) {
        int slv = chr.getSkillLevel(unity);
        SkillInfo si = SkillData.getSkillInfoById(unity);
        if (slv <= 0 || si == null) {
            return;
        }
        int perStack = si.getValue(w, slv);
        int max = Math.max(1, si.getValue(v, slv));
        int duration = si.getValue(time, slv);
        if (perStack <= 0 || duration <= 0) {
            return;
        }
        int buffId = unity + 10;
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option cur = tsm.getOptByCTSAndSkill(IndiePMdR, buffId);
        int stacks = cur == null ? 1 : Math.min(max, cur.nOption / perStack + 1);
        tsm.putCharacterStatValue(IndiePMdR, new Option(perStack * stacks, buffId, duration), true);
        tsm.sendSetStatPacket();
    }

    // Skills ----------------------------------------------------------------------------------------------------------

    /** Sweep gains a charge when the client asks (up to y). */
    public void increaseSweep() {
        if (!chr.hasSkill(SWEEP)) {
            return;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int max = chr.getSkillStatValue(y, SWEEP);
        int count = tsm.hasStat(StackOverTimeSkill) ? tsm.getOption(StackOverTimeSkill).nOption : 0;
        if (count < max) {
            setSweep(count + 1);
        }
    }

    private void setSweep(int count) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option();
        o.nOption = count;
        o.rOption = SWEEP;
        tsm.putCharacterStatValue(StackOverTimeSkill, o);
        tsm.sendSetStatPacket();
        chr.write(WvsContext.stackSkillRequestResult(-2, 1));
    }

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        super.handleSkill(chr, tsm, skillId, slv, inPacket, skillUseInfo);
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        if (si == null) {
            return;
        }
        switch (skillId) {
            case SUPPORT_FLIGHT: // Support Flight: the party in the map comes to you
                if (chr.getParty() != null) {
                    for (Char member : chr.getParty().getPartyMembersInSameField(chr)) {
                        if (member != chr) {
                            member.write(net.swordie.ms.connection.packet.field.FieldPacket.teleport(
                                    chr.getPosition().deepCopy(), member));
                        }
                    }
                }
                break;
            case FOCUS_HEAL:
            case HEXA_FOCUS_HEAL:
                focusHeal(si, skillId, slv);
                break;
            case NATURE_PROVIDENCE:
            case AURA_OF_DESTINY:
                net.swordie.ms.client.jobs.SkillBuffs.giveIndieBuff(chr, skillId, slv);
                break;
            case HEROS_WILL:
                tsm.removeAllDebuffs();
                break;
            case MOTHER_NATURE_TOUCH:
            case HEXA_MOTHER_NATURE_TOUCH:
                motherNatureTouch(si, skillId, slv);
                break;
            case PECK:
            case PECK_III:
            case PECK_IV:
            case HEXA_PECK:
                // toggle: the same Peck again turns it off (the skill icon's off click is RPC 23)
                if (peckSkill == skillId) {
                    setPeck(0, 0);
                } else {
                    setPeck(skillId, slv);
                }
                break;
            case SNEAK_ATTACK:
            case HEXA_SNEAK_ATTACK:
                setAmbush(ambushSkill == skillId ? 0 : skillId);
                break;
            case BEAK_STRIKE:
                beakStrike(si, slv);
                break;
            case FOREST_PROTECTION:
            case HEXA_FOREST_PROTECTION:
                forestProtection(si, skillId, slv);
                break;
            case FOCUS_AWAKEN:
                focusAwaken(si, slv);
                break;
            case NATURE_GRACE: {
                // Excess Healing for time, then heals the party w% of Max HP (that heal counts too)
                tsm.putCharacterStatValue(IndieBuffIcon, new Option(1, skillId, si.getValue(time, slv)));
                tsm.sendSetStatPacket();
                for (Char t : partyInField()) {
                    healTarget(t, (int) ((long) t.getMaxHP() * si.getValue(w, slv) / 100));
                }
                break;
            }
            case PURIFY:
                tsm.putCharacterStatValue(IndieLoopEffect, new Option(chr.getId(), skillId, si.getValue(time, slv)));
                tsm.putCharacterStatValue(IndiePeriodicalSkillActivation, new Option(PURIFY_PERIOD, skillId,
                        si.getValue(time, slv)));
                break;
        }
        tsm.sendSetStatPacket();
    }

    private Set<Char> partyInField() {
        Set<Char> targets = new LinkedHashSet<>();
        targets.add(chr);
        if (chr.getParty() != null) {
            targets.addAll(chr.getParty().getPartyMembersInSameField(chr));
        }
        return targets;
    }

    /** Peck on (a Peck skill id) or off (0): the client's bird follows ActivatingPeckingSkillID. */
    private void setPeck(int skillId, int slv) {
        if (peckSkill == skillId && peckLevel == slv) {
            return;
        }
        peckSkill = skillId;
        peckLevel = skillId == 0 ? 0 : slv;
        peckTargetMob = 0;
        if (skillId == 0) {
            beakStrikeTurnedPeckOn = false;
        }
        syncController();
    }

    /** Sneak Attack on (its skill id) or off (0); Paeon hides while it waits (GuardianFlags 125). */
    private void setAmbush(int skillId) {
        if (ambushSkill == skillId) {
            return;
        }
        ambushSkill = skillId;
        guardianFlags = skillId == 0 ? GUARDIANS_IDLE : GUARDIANS_AMBUSH;
        syncController();
    }

    /** Another guide skill hit a mob while Sneak Attack waits: Paeon follows up (client RPC 24), every w sec. */
    private void activateAmbush(int mobObjectId) {
        long now = System.currentTimeMillis();
        if (now < ambushReadyAt) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(ambushSkill);
        int slv = chr.getSkillLevel(ambushSkill);
        int cooldown = si == null || slv <= 0 ? 2 : Math.max(1, si.getValue(w, slv));
        ambushReadyAt = now + cooldown * 1000L;
        int followUp = ambushSkill == HEXA_SNEAK_ATTACK ? HEXA_SNEAK_ATTACK_FOLLOW_UP : SNEAK_ATTACK_FOLLOW_UP;
        byte[] args = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putInt(mobObjectId).putInt(followUp).array();
        chr.write(ActorPacket.userActorRpc(chr.getId(), getControllerId(), RPC_ACTIVATE_AMBUSH, args));
    }

    /**
     * Beak Strike: for its time the bird becomes Beak Strike (PeckingBladeAssaultEndTime/Level on the replication
     * clock). The bird only exists while Peck is on, so the best Peck turns on for the duration if it was off.
     */
    private void beakStrike(SkillInfo si, int slv) {
        int duration = si.getValue(time, slv);
        if (duration <= 0) {
            return;
        }
        if (peckSkill == 0) {
            int best = 0;
            for (int id : new int[]{HEXA_PECK, PECK_IV, PECK_III, PECK}) {
                if (chr.getSkillLevel(id) > 0) {
                    best = id;
                    break;
                }
            }
            if (best == 0) {
                return;
            }
            peckSkill = best;
            peckLevel = chr.getSkillLevel(best);
            beakStrikeTurnedPeckOn = true;
        }
        beakStrikeEnd = ActorPacket.clockNow() + duration;
        beakStrikeLevel = slv;
        syncController();
        EventManager.stopTimer(beakStrikeTimer);
        beakStrikeTimer = EventManager.addEvent(() -> {
            beakStrikeEnd = 0;
            beakStrikeLevel = 0;
            if (beakStrikeTurnedPeckOn) {
                beakStrikeTurnedPeckOn = false;
                peckSkill = 0;
                peckLevel = 0;
                peckTargetMob = 0;
            }
            if (chr.getClient() != null) {
                syncController();
            }
        }, duration * 1000L);
    }

    /**
     * [Focus] Heal: the Focal Target recovers hp% of Max HP every second for time sec (Heal Boost: +x% healing and its
     * attack / magic attack). Used on someone else, Lynn gets Resonant: Heal, s2% every second for s sec.
     */
    private void focusHeal(SkillInfo si, int skillId, int slv) {
        Char target = focalTarget();
        int duration = si.getValue(time, slv);
        int boostR = 0;
        if (chr.hasSkill(FOCUS_HEAL_BOOST)) {
            int boostLevel = chr.getSkillLevel(FOCUS_HEAL_BOOST);
            SkillInfo boost = SkillData.getSkillInfoById(FOCUS_HEAL_BOOST);
            if (boost != null) {
                boostR = boost.getValue(x, boostLevel);
                TemporaryStatManager ttsm = target.getTemporaryStatManager();
                ttsm.putCharacterStatValue(IndiePAD, new Option(boost.getValue(pad, boostLevel), skillId, duration));
                ttsm.putCharacterStatValue(IndieMAD, new Option(boost.getValue(mad, boostLevel), skillId, duration));
                ttsm.sendSetStatPacket();
            }
        }
        int healR = si.getValue(hp, slv) * (100 + boostR) / 100;
        EventManager.stopTimer(focusHealTimer);
        focusHealTimer = healOverTime(target, skillId, healR, duration, true);
        var effect = Effect.skillSpecialAffected(skillId, slv, chr.getLevel());
        chr.write(UserPacket.effect(effect));
        chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), effect), chr);

        if (target != chr) {
            int resonantId = skillId == HEXA_FOCUS_HEAL ? HEXA_FOCUS_HEAL_RESONANT : FOCUS_HEAL_2;
            EventManager.stopTimer(resonantHealTimer);
            resonantHealTimer = healOverTime(chr, resonantId, si.getValue(s2, slv), si.getValue(s, slv), false);
        }
    }

    /** Heals healR% of the target's Max HP every second for duration sec (IndieDotHealHP shows it). */
    private ScheduledFuture healOverTime(Char target, int reasonId, int healR, int duration, boolean loopEffect) {
        if (healR <= 0 || duration <= 0) {
            return null;
        }
        TemporaryStatManager ttsm = target.getTemporaryStatManager();
        int tick = (int) ((long) target.getMaxHP() * healR / 100);
        if (loopEffect) {
            ttsm.putCharacterStatValue(IndieLoopEffect, new Option(chr.getId(), reasonId, duration));
        }
        ttsm.putCharacterStatValue(IndieDotHealHP, new Option(tick, reasonId, duration));
        ttsm.sendSetStatPacket();
        healTarget(target, tick);
        return EventManager.addFixedRateEvent(() -> {
            if (target.getClient() != null && target.getField() != null && !target.isDead()
                    && target.getTemporaryStatManager().hasStatBySkillId(reasonId)) {
                healTarget(target, (int) ((long) target.getMaxHP() * healR / 100));
            }
        }, 1000, 1000, duration);
    }

    /**
     * Mother Nature's Touch: party members in the map lose their debuffs, get hp% of Max HP at once and y% every
     * second for time sec; the HEXA version adds x% damage reduction.
     */
    private void motherNatureTouch(SkillInfo si, int skillId, int slv) {
        Set<Char> targets = partyInField();
        int duration = si.getValue(time, slv);
        for (Char target : targets) {
            TemporaryStatManager ttsm = target.getTemporaryStatManager();
            ttsm.removeAllDebuffs();
            healTarget(target, (int) ((long) target.getMaxHP() * si.getValue(hp, slv) / 100));
            int tick = (int) ((long) target.getMaxHP() * si.getValue(y, slv) / 100);
            if (tick > 0 && duration > 0) {
                ttsm.putCharacterStatValue(IndieDotHealHP, new Option(tick, skillId, duration));
            }
            if (si.getValue(x, slv) > 0) {
                ttsm.putCharacterStatValue(IndieDamReduceR, new Option(si.getValue(x, slv), skillId, duration));
            }
            ttsm.sendSetStatPacket();
        }
        // the per-second heal itself is server side (IndieDotHealHP only shows it)
        int healR = si.getValue(y, slv);
        EventManager.stopTimer(natureTouchTimer);
        if (healR > 0 && duration > 0) {
            natureTouchTimer = EventManager.addFixedRateEvent(() -> {
                for (Char target : targets) {
                    if (target.getClient() != null && target.getField() != null && !target.isDead()
                            && target.getTemporaryStatManager().hasStatBySkillId(skillId)) {
                        healTarget(target, (int) ((long) target.getMaxHP() * healR / 100));
                    }
                }
            }, 1000, 1000, duration);
        }
    }

    /**
     * [Focus] Awaken: Final Damage v% for the Focal Target, +1% per q INT up to v2%, halved (z%) with fewer than 2 in
     * the party. Everyone in the party gets Exhaust (no Awaken again for delay sec). Used on someone else, Lynn gets
     * Resonant: s2% growing the same way up to v2 - level (the data's s4) for s sec.
     */
    private void focusAwaken(SkillInfo si, int slv) {
        Char target = focalTarget();
        long now = System.currentTimeMillis();
        if (awakenExhaust.getOrDefault(target.getId(), 0L) > now) {
            target = chr;
        }
        int perInt = Math.max(1, si.getValue(q, slv));
        int intBonus = chr.getTotalStat(BaseStat.inte) / perInt;
        int fd = Math.min(si.getValue(v2, slv), si.getValue(v, slv) + intBonus);
        boolean smallParty = chr.getParty() == null || chr.getParty().getPartyMembersInSameField(chr).size() < 2;
        if (smallParty) {
            fd = fd * (100 - si.getValue(z, slv)) / 100;
        }
        int duration = si.getValue(time, slv);
        if (awakenExhaust.getOrDefault(target.getId(), 0L) <= now) {
            TemporaryStatManager ttsm = target.getTemporaryStatManager();
            ttsm.putCharacterStatValue(IndiePMdR, new Option(fd, FOCUS_AWAKEN, duration));
            ttsm.sendSetStatPacket();
        }
        if (target != chr) {
            int resonant = Math.min(si.getValue(v2, slv) - slv, si.getValue(s2, slv) + intBonus);
            if (resonant > 0 && si.getValue(s, slv) > 0) {
                TemporaryStatManager tsm = chr.getTemporaryStatManager();
                tsm.putCharacterStatValue(IndiePMdR, new Option(resonant, AWAKEN_EXHAUST, si.getValue(s, slv)));
                tsm.sendSetStatPacket();
            }
        }
        int exhaust = si.getValue(delay, slv) > 0 ? si.getValue(delay, slv) : 60;
        for (Char member : partyInField()) {
            awakenExhaust.put(member.getId(), now + exhaust * 1000L);
        }
    }

    /**
     * [Focus] Forest Protection: a zone at the Focal Target for time sec. Party members inside take x% less damage
     * (refreshed every second) and a fatal hit is negated once, then not again for z sec. Used on someone else,
     * Lynn gets the Resonant damage reduction.
     */
    private void forestProtection(SkillInfo si, int skillId, int slv) {
        Char target = focalTarget();
        int duration = si.getValue(time, slv);
        if (duration <= 0 || chr.getField() == null) {
            return;
        }
        Rect base = si.getFirstRect() != null ? si.getFirstRect() : new Rect(-300, -200, 300, 50);
        Position at = target.getPosition().deepCopy();
        ForestZone zone = new ForestZone(chr.getId(), chr.getField(), at.getRectAround(base), skillId, slv,
                si.getValue(x, slv), si.getValue(z, slv), System.currentTimeMillis() + duration * 1000L);
        forestZones.removeIf(zz -> zz.ownerId == chr.getId());
        forestZones.add(zone);
        EventManager.stopTimer(forestTimer);
        forestTimer = EventManager.addFixedRateEvent(() -> tickForest(zone), 0, 1000, duration);

        if (target != chr) {
            int resonantId = skillId == HEXA_FOREST_PROTECTION ? HEXA_FOREST_PROTECTION_RESONANT
                    : FOREST_PROTECTION_RESONANT;
            SkillInfo rsi = SkillData.getSkillInfoById(resonantId);
            if (rsi != null && rsi.getValue(x, slv) > 0) {
                TemporaryStatManager tsm = chr.getTemporaryStatManager();
                tsm.putCharacterStatValue(IndieDamReduceR, new Option(rsi.getValue(x, slv), resonantId,
                        rsi.getValue(time, slv)));
                tsm.sendSetStatPacket();
            }
        }
    }

    private void tickForest(ForestZone zone) {
        if (System.currentTimeMillis() >= zone.until || chr.getClient() == null || chr.getField() != zone.field) {
            forestZones.remove(zone);
            return;
        }
        for (Char member : partyInField()) {
            if (zone.rect.hasPositionInside(member.getPosition())) {
                TemporaryStatManager ttsm = member.getTemporaryStatManager();
                ttsm.putCharacterStatValue(IndieDamReduceR, new Option(zone.reduceR, zone.skillId, 2), true);
                ttsm.sendSetStatPacket();
            }
        }
    }

    /**
     * Forest Protection's Fatal Damage Negation, called for a lethal hit on anyone: inside a live zone of a Lynn in
     * the same party (or her own), the hit is negated (a moment of invincibility the revive check counts as absorbing
     * it) and the negation can't fire again for z sec.
     */
    public static boolean negateFatalDamage(Char victim) {
        if (forestZones.isEmpty() || victim == null || victim.getField() == null) {
            return false;
        }
        long now = System.currentTimeMillis();
        if (fatalNegationLockout.getOrDefault(victim.getId(), 0L) > now) {
            return false;
        }
        for (ForestZone zone : forestZones) {
            if (zone.until <= now || zone.field != victim.getField() || !zone.rect.hasPositionInside(victim.getPosition())) {
                continue;
            }
            if (zone.ownerId != victim.getId()) {
                Char owner = victim.getField().getCharByID(zone.ownerId);
                Party party = owner == null ? null : owner.getParty();
                if (party == null || party.getPartyMemberByID(victim.getId()) == null) {
                    continue;
                }
            }
            fatalNegationLockout.put(victim.getId(), now + Math.max(1, zone.lockoutSec) * 1000L);
            TemporaryStatManager tsm = victim.getTemporaryStatManager();
            Option o = new Option(1, zone.skillId, FATAL_NEGATION_MS);
            o.setInMillis(true);
            tsm.putCharacterStatValue(IndieNotDamaged, o, true);
            tsm.sendSetStatPacket();
            return true;
        }
        return false;
    }

    private record ForestZone(int ownerId, Field field, Rect rect, int skillId, int slv, int reduceR, int lockoutSec,
                              long until) {
    }

    /**
     * Lynn's heals. Under Nature's Grace's Excess Healing, healing past Max HP becomes a shield on the target (up to
     * x% of its Max HP, i.e. healing up to y% in all) that ends with the Excess Healing effect.
     */
    private void healTarget(Char target, int amount) {
        if (amount <= 0) {
            return;
        }
        int missing = Math.max(0, target.getMaxHP() - target.getHP());
        int excess = amount - missing;
        target.heal(amount);
        if (excess <= 0 || target.isDead()) {
            return;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        long left = tsm.getRemainingTime(IndieBuffIcon, NATURE_GRACE);
        int slv = chr.getSkillLevel(NATURE_GRACE);
        SkillInfo si = SkillData.getSkillInfoById(NATURE_GRACE);
        if (left <= 0 || slv <= 0 || si == null) {
            return;
        }
        int cap = (int) ((long) target.getMaxHP() * si.getValue(x, slv) / 100);
        TemporaryStatManager ttsm = target.getTemporaryStatManager();
        Option cur = ttsm.getOptByCTSAndSkill(IndieProtectiveShield, NATURE_GRACE);
        int have = cur == null ? 0 : cur.nOption;
        int shield = (int) Math.min(cap, (long) have + excess);
        if (shield <= have) {
            return;
        }
        Option o = new Option(shield, NATURE_GRACE, (int) left);
        o.setInMillis(true);
        ttsm.putCharacterStatValue(IndieProtectiveShield, o, true);
        ttsm.sendSetStatPacket();
    }

    @Override
    public void onWarp(Field oldField, Field newField) {
        super.onWarp(oldField, newField);
        // SET_FIELD carries the controller without the Beak Strike time (or not at all while its actor stream is
        // off); send it once the map is up
        if (beakStrikeEnd > ActorPacket.clockNow()
                || !net.swordie.ms.ServerSettings.getBoolean("test.fieldActorStream", false)) {
            EventManager.addEvent(() -> {
                if (chr.getClient() != null && chr.getField() == newField) {
                    syncController();
                }
            }, 1000);
        }
    }

    @Override
    public void stopTimers() {
        EventManager.stopTimer(focusHealTimer);
        focusHealTimer = null;
        EventManager.stopTimer(resonantHealTimer);
        resonantHealTimer = null;
        EventManager.stopTimer(natureTouchTimer);
        natureTouchTimer = null;
        EventManager.stopTimer(beakStrikeTimer);
        beakStrikeTimer = null;
        EventManager.stopTimer(forestTimer);
        forestTimer = null;
        forestZones.removeIf(zz -> zz.ownerId == chr.getId());
        super.stopTimers();
    }

    @Override
    public void setCharCreationStats(Char chr) {
        super.setCharCreationStats(chr);
        setFirstJobCreationStats(chr, JobEnum.LYNN_1.getJobId(), 4, 4, 25, 4);
    }

    @Override
    public void handleLevelUp() {
        super.handleLevelUp();
        advanceByLevel(JobEnum.LYNN_1.getJobId(), JobEnum.LYNN_2.getJobId(), JobEnum.LYNN_3.getJobId(),
                JobEnum.LYNN_4.getJobId());
        // the skipped tutorial (through the 2nd job) and the later job quests, as the reference tree completes them
        int level = chr.getLevel();
        if (level >= 30) {
            completeQuestRangeIfNeeded(66908, 66920);
        }
        if (level >= 60) {
            completeQuestRangeIfNeeded(66921, 66925);
            completeQuestsIfNeeded(66941, 66954);
        }
        if (level >= 100) {
            completeQuestRangeIfNeeded(66926, 66934);
        }
        if (level >= 200) {
            completeQuestsIfNeeded(66935);
        }
    }
}
