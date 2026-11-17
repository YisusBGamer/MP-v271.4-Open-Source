package net.swordie.ms.connection.packet;

import net.swordie.ms.client.jobs.nova.Kain;
import net.swordie.ms.client.jobs.resistance.Blaster;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.client.jobs.resistance.demon.DemonSlayer;
import net.swordie.ms.client.jobs.resistance.Mechanic;
import net.swordie.ms.client.jobs.cygnus.NightWalker;
import net.swordie.ms.client.jobs.cygnus.Mihile;
import net.swordie.ms.client.jobs.anima.Ren;
import net.swordie.ms.client.jobs.adventurer.thief.BladeMaster;
import net.swordie.ms.client.jobs.adventurer.magician.IceLightning;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.Gun;
import net.swordie.ms.client.character.b2body.B2Body;
import net.swordie.ms.client.character.damage.DamageSkinType;
import net.swordie.ms.client.character.familiar.FamiliarCodexManager;
import net.swordie.ms.client.character.familiar.FamiliarCodexUpdateMask;
import net.swordie.ms.client.character.familiar.FamiliarModifiedMask;
import net.swordie.ms.client.character.familiar.FamiliarModifiedType;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.skills.PsychicArea;
import net.swordie.ms.client.character.skills.PsychicLockBall;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.shootobject.ShootObject;
import net.swordie.ms.client.character.skills.shootobject.ShootObjectSkillInfo;
import net.swordie.ms.client.jobs.cygnus.ThunderBreaker;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.enums.*;
import net.swordie.ms.handlers.PsychicLock;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.Familiar;
import net.swordie.ms.life.Wreckage;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.movement.MovementInfo;
import net.swordie.ms.life.pet.Pet;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.util.container.Tuple;
import org.jboss.resteasy.spi.NotImplementedYetException;

import java.util.*;

/**
 * Created on 1/2/2018.
 */
public class UserLocal {
    public static OutPacket noticeMsg(String msg, boolean autoSeparated) {
        OutPacket outPacket = new OutPacket(OutHeader.NOTICE_MSG);

        outPacket.encodeString(msg);
        outPacket.encodeByte(autoSeparated);
        outPacket.encodeByte(0); // v271 0x145470b60 reads a second byte

        return outPacket;
    }

    public static OutPacket chatMsg(ChatType colour, String msg) {
        OutPacket outPacket = new OutPacket(OutHeader.CHAT_MSG);

        outPacket.encodeShort(colour.getVal());
        outPacket.encodeString(msg);

        return outPacket;
    }

    /** No second record id for the mob (GMS sends one for few bosses). */
    private static final int NO_SECOND_BOSS_RECORD = 0;
    /** The last int of a boss record entry: 0 for every boss in GMS. */
    private static final int BOSS_RECORD_TAIL = 0;
    private static final int MAPLE_PLANNER_OK = 0;

    /**
     * The client's mobId -> record id table the boss window resolves its clear mobs through (BossClearRecords); without
     * it no boss window check paints. Found by Twoal (from the Discord).
     */
    public static OutPacket bossClearRecordTable(Map<Integer, Integer> recordByMob) {
        OutPacket outPacket = new OutPacket(OutHeader.BOSS_CLEAR_RECORD_TABLE);

        outPacket.encodeInt(recordByMob.size());
        for (Map.Entry<Integer, Integer> entry : recordByMob.entrySet()) {
            outPacket.encodeInt(entry.getKey());
            outPacket.encodeInt(entry.getValue());
            outPacket.encodeInt(NO_SECOND_BOSS_RECORD);
            outPacket.encodeInt(BOSS_RECORD_TAIL);
        }

        return outPacket;
    }

    /** The answer to a Maple Planner request (MaplePlannerHandler): the request type it answers, then the result. */
    public static OutPacket maplePlannerUpdate(int request) {
        OutPacket outPacket = new OutPacket(OutHeader.MAPLE_PLANNER_UPDATE);

        outPacket.encodeInt(request);
        outPacket.encodeInt(MAPLE_PLANNER_OK);

        return outPacket;
    }

    public static OutPacket videoByScript(String videoPath, boolean isMuted) {
        OutPacket outPacket = new OutPacket(OutHeader.VIDEO_BY_SCRIPT);

        outPacket.encodeString(videoPath);
        outPacket.encodeByte(isMuted);

        return outPacket;
    }

    public static OutPacket jaguarActive(boolean active) {
        OutPacket outPacket = new OutPacket(OutHeader.JAGUAR_ACTIVE);

        outPacket.encodeByte(active);

        return outPacket;
    }

    public static OutPacket jaguarSkill(int skillID) {
        OutPacket outPacket = new OutPacket(OutHeader.JAGUAR_SKILL);

        outPacket.encodeInt(skillID);

        return outPacket;
    }

    public static OutPacket addPopupSay(int npcID, int duration, String message, String effect) {
        OutPacket outPacket = new OutPacket(OutHeader.ADD_POPUP_SAY);

        outPacket.encodeInt(npcID);
        outPacket.encodeInt(duration);
        outPacket.encodeString(message);
        outPacket.encodeString(effect);
        outPacket.encodeByte(false);
        // v265 tail, as the reference server's packets show it (its source lacks the last byte)
        outPacket.encodeInt(300);
        outPacket.encodeByte(1);
        outPacket.encodeByte(0);
        outPacket.encodeInt(0);
        outPacket.encodeByte(0);

        return outPacket;
    }

    public static OutPacket royalGuardAttack(boolean attack) {
        OutPacket outPacket = new OutPacket(OutHeader.ROYAL_GUARD_ATTACK);

        outPacket.encodeByte(attack);

        return outPacket;
    }

    public static OutPacket setOffStateForOffSkill(int skillID) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_OFF_STATE_FOR_OFF_SKILL);

        outPacket.encodeInt(skillID);

        return outPacket;
    }

    public static OutPacket setActionBar(boolean show, ActionBarType type){
      OutPacket outPacket = new OutPacket(OutHeader.ACTION_BAR_RESULT);
      outPacket.encodeInt(show == true ? 5 : 6); //5 for show, 6 for hide
      outPacket.encodeInt(type.getVal()); //type?

      return outPacket;
    }

    public static OutPacket resetStateForOffSkill() {
        OutPacket outPacket = new OutPacket(OutHeader.RESET_STATE_FOR_OFF_SKILL);

        return outPacket;
    }

    public static OutPacket modHayatoCombo(int amount) {
        OutPacket outPacket = new OutPacket(OutHeader.MOD_HAYATO_COMBO);

        outPacket.encodeShort(amount);

        return outPacket;
    }

    public static OutPacket stackOverTimeSkillResult(int skillId) {
        OutPacket outPacket = new OutPacket(OutHeader.STACK_OVER_TIME_SKILL_RESULT);
        outPacket.encodeInt(skillId);
        outPacket.encodeByte(1);
        outPacket.encodeInt(-1);
        return outPacket;
    }

    public static OutPacket scrollResultNotice(boolean success, int scrollId, int itemId) {
        OutPacket outPacket = new OutPacket(OutHeader.SCROLL_RESULT_NOTICE);
        outPacket.encodeByte(success);
        outPacket.encodeInt(scrollId);
        outPacket.encodeInt(itemId);
        outPacket.encodeByte(0);
        return outPacket;
    }

    public static OutPacket cubeResultNotice(int cubeId, int itemId) {
        OutPacket outPacket = new OutPacket(OutHeader.CUBE_RESULT_NOTICE);
        outPacket.encodeByte(1);
        outPacket.encodeInt(cubeId);
        outPacket.encodeInt(0);
        outPacket.encodeInt(itemId);
        outPacket.encodeByte(1);
        return outPacket;
    }

    public static OutPacket npcSpeechDone() {
        return new OutPacket(OutHeader.NPC_SPEECH_DONE);
    }

    public static OutPacket questExRunActResult(int questId) {
        OutPacket outPacket = new OutPacket(OutHeader.QUEST_EX_RUN_ACT_RESULT);
        outPacket.encodeInt(questId);
        return outPacket;
    }

    public static OutPacket incJudgementStack(byte amount) {
        OutPacket outPacket = new OutPacket(OutHeader.INC_JUDGEMENT_STACK_RESPONSE);

        outPacket.encodeByte(false);
        outPacket.encodeByte(amount);

        return outPacket;
    }

    public static OutPacket changeStealMemoryResult(StealMemoryType type, int stealManagerJobID, int position, int skillid, int stealSkillLv, int stealSkillMaxLv) {
        OutPacket outPacket = new OutPacket(OutHeader.CHANGE_STEAL_MEMORY_RESULT);

        outPacket.encodeByte(1); //Set Excl Request
        outPacket.encodeByte(type.getVal());    //Type

        switch (type) {
            case STEAL_SKILL:
                outPacket.encodeInt(stealManagerJobID); //jobId  1~5 | 1 = 1stJob , 2 = 2ndJob ... ..
                outPacket.encodeInt(position); //impecMemSkillID // nPOS  0,1,2,3
                outPacket.encodeInt(skillid); //skill
                outPacket.encodeInt(stealSkillLv);   //StealSkill Lv
                outPacket.encodeInt(stealSkillMaxLv);   //StealSkill Max Lv
                break;
            case NO_TARGETS:
            case FAILED_UNK_REASON:
                break;
            case REMOVE_STEAL_MEMORY:
                outPacket.encodeInt(stealManagerJobID);
                outPacket.encodeInt(position);
                break;
            case REMOVE_MEMORY_ALL_SLOT:
                outPacket.encodeInt(skillid);
                break;
            case REMOVE_ALL_MEMORY:
                break;
        }

        return outPacket;
    }

    public static OutPacket resultSetStealSkill(boolean set, int impecMemSkilLID, int skillId) {
        OutPacket outPacket = new OutPacket(OutHeader.RESULT_SET_STEAL_SKILL);

        outPacket.encodeByte(1); //Set Excl Request
        outPacket.encodeByte(set); //bSet
        outPacket.encodeInt(impecMemSkilLID); //impecMemSkilLID
        if (set) {
            outPacket.encodeInt(skillId); //skill Id
        }
        return outPacket;
    }

    public static OutPacket resultStealSkillList(Set<Skill> targetSkillsList, int phantomStealResult, int targetChrId, int targetJobId) {
        OutPacket outPacket = new OutPacket(OutHeader.RESULT_STEAL_SKILL_LIST);
        outPacket.encodeByte(1); // GMS sends 1, as for RESULT_SET_STEAL_SKILL
        outPacket.encodeInt(targetChrId);
        outPacket.encodeInt(phantomStealResult); //   Gets a check  if == 4,   else:   nPhantomStealWrongResult
        if (phantomStealResult == 4) {
            outPacket.encodeInt(targetJobId);
            outPacket.encodeInt(targetSkillsList.size());

            for (Skill skills : targetSkillsList) {
                outPacket.encodeInt(skills.getSkillId());
            }
        }

        return outPacket;
    }

    public static OutPacket setFuncKeyByScript(boolean add, int action, int key) {
        OutPacket outPacket = new OutPacket(OutHeader.FUNCKEY_SET_BY_SCRIPT);
        outPacket.encodeByte(add);
        outPacket.encodeInt(action);
        if (add) { // v271 (0x145ab4f40) reads the key only when adding
            outPacket.encodeInt(key);
        }

        return outPacket;
    }

    /**
     * v265 reader 0x145517f30: byte type; 4 = the collection (the character data block), anything else = a request's
     * answer: byte status (0 ok and the window redraws from its copy of the collection, else a message).
     */
    public static OutPacket damageSkinSaveResult(DamageSkinType req, DamageSkinType res, Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.DAMAGE_SKIN_SAVE_RESULT);
        outPacket.encodeByte(req.getVal());
        outPacket.encodeByte(damageSkinStatus(res));
        return outPacket;
    }

    public static OutPacket damageSkinList(Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.DAMAGE_SKIN_SAVE_RESULT);
        outPacket.encodeByte(4);
        net.swordie.ms.client.character.CharEncoder.encodeDamageSkins(chr, outPacket);
        return outPacket;
    }

    /** The answer to a damage skin request; on success the refreshed collection goes first. */
    public static void sendDamageSkinResult(Char chr, DamageSkinType req, DamageSkinType res) {
        if (res == DamageSkinType.Res_Success) {
            chr.write(damageSkinList(chr));
        }
        chr.write(damageSkinSaveResult(req, res, chr));
    }

    // client 0x145517e50: 1 error, 2 no free slot, 3 already stored, 4 the applied skin can't be stored,
    // 5 unavailable, 6 already applied, 7 nothing saved, 13 not enough mesos
    private static int damageSkinStatus(DamageSkinType res) {
        return switch (res) {
            case Res_Success -> 0;
            case Res_Fail_SlotCount -> 2;
            case Res_Fail_AlreadyExist -> 3;
            case Res_Fail_NotSave -> 4;
            case Res_Fail_ServerValueBlock -> 5;
            case Res_Fail_AlreadyActive -> 6;
            default -> 1;
        };
    }

    public static OutPacket explosionAttack(int skillID, Position position, int mobID, int count) {
        OutPacket outPacket = new OutPacket(OutHeader.EXPLOSION_ATTACK);

        outPacket.encodeInt(skillID);
        outPacket.encodePositionInt(position);
        outPacket.encodeInt(mobID);
        outPacket.encodeInt(count);

        return outPacket;
    }

    public static OutPacket setNextShootExJablin() {
        OutPacket outPacket = new OutPacket(OutHeader.SET_NEXT_SHOOT_EX_JABLIN);

        return outPacket;
    }

    public static OutPacket cadenaVoidStrikeRequest(Position position) {
        OutPacket outPacket = new OutPacket(OutHeader.CADENA_VOID_STRIKE_REQUEST);

        outPacket.encodePositionInt(position);

        return outPacket;
    }

    public static OutPacket userRandAreaAttackRequest(Mob mob, int skillID) {
        OutPacket outPacket = new OutPacket(OutHeader.USER_RAND_AREA_ATTACK_REQUEST);

        outPacket.encodeInt(skillID);
        outPacket.encodeInt(1); //# of mobs to attack

        outPacket.encodePositionInt(mob.getPosition());
        outPacket.encodeInt(mob.getObjectId());
        if (skillID == SkillConstants.LIBERATE_THE_RUNE_OF_THUNDER_2) { // the v265 reader (0x145060720) reads one more int for the Thunder Rune
            outPacket.encodeInt(0);
        }

        return outPacket;
    }

    /**
     * Per-monster stack marks (Paladin's Divine Brand). stacks: mob object id -> {stack, time left in ms}.
     */
    public static OutPacket setMonsterDebuffMark(int originalSkillId, int skillId, int maxStacks, int durationMs,
                                                 Map<Integer, int[]> stacks) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_MONSTER_DEBUFF_MARK);
        outPacket.encodeByte(true);
        outPacket.encodeInt(stacks.size());
        for (int oid : stacks.keySet()) {
            outPacket.encodeInt(oid);
        }
        outPacket.encodeInt(maxStacks);
        outPacket.encodeInt(durationMs);
        outPacket.encodeInt(originalSkillId);
        outPacket.encodeInt(skillId);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(stacks.size());
        for (Map.Entry<Integer, int[]> e : stacks.entrySet()) {
            outPacket.encodeInt(e.getKey());
            outPacket.encodeInt(e.getValue()[0]);
            outPacket.encodeInt(0);
            outPacket.encodeInt(e.getValue()[1]);
        }
        outPacket.encodeInt(0); // GMS ends with an int (0)
        return outPacket;
    }

    /** Detonates full stack marks: the client attacks each listed monster with skillId. */
    public static OutPacket attackMonsterDebuffMark(int skillId, int maxStacks, List<Integer> mobObjIds, int attackSkillId) {
        OutPacket outPacket = new OutPacket(OutHeader.ATTACK_MONSTER_DEBUFF_MARK);
        outPacket.encodeInt(skillId);
        outPacket.encodeInt(maxStacks);
        outPacket.encodeInt(0);
        outPacket.encodeInt(mobObjIds.size());
        for (int oid : mobObjIds) {
            outPacket.encodeInt(oid);
            outPacket.encodeInt(Util.getRandom(200, 300)); // delay
        }
        outPacket.encodeInt(attackSkillId);
        return outPacket;
    }

    public static OutPacket userBonusAttackRequest(int skillId) {
        return userBonusAttackRequest(skillId, new ArrayList<>(), 0);
    }

    public static OutPacket userBonusAttackRequest(int skillId, List<Integer> mobObjIdList) {
        return userBonusAttackRequest(skillId, mobObjIdList, 0);
    }

    public static OutPacket userBonusAttackRequest(int skillId, List<Integer> mobObjIdList, int hits) {
        return userBonusAttackRequest(skillId, mobObjIdList, hits, 0, null, true);
    }

    public static OutPacket userBonusAttackRequest(int skillId, List<Integer> mobObjIdList, int hits, int lightningStrikes, Rect rect, boolean left) {
        return userBonusAttackRequest(skillId, mobObjIdList, hits, lightningStrikes, rect, left, 0);
    }

    /** A bonus attack the client starts after delayMs. */
    public static OutPacket delayedBonusAttackRequest(int skillId, int delayMs) {
        return userBonusAttackRequest(skillId, new ArrayList<>(), 0, 0, null, true, delayMs);
    }

    public static OutPacket userBonusAttackRequest(int skillId, List<Integer> mobObjIdList, int hits, int lightningStrikes,
                                                   Rect rect, boolean left, int delayMs) {
        OutPacket outPacket = new OutPacket(OutHeader.USER_BONUS_ATTACK_REQUEST);

        outPacket.encodeInt(skillId);
        outPacket.encodeInt(mobObjIdList.size()); // mobs hit
        outPacket.encodeByte(mobObjIdList.size() <= 0); // true ? placeOnChrPosition : placeOnMobPosition
        outPacket.encodeInt(delayMs);
        outPacket.encodeInt(hits);
        for (int mobObjId : mobObjIdList) { // v271: two ints per entry, no per-skill extra
            outPacket.encodeInt(mobObjId);
            outPacket.encodeInt(skillId == ThunderBreaker.LIGHTNING_SPEAR_MULTISTRIKE_THUNDERBOLT_2 || skillId == ThunderBreaker.LIGHTNING_SPEAR_MULTISTRIKE_THUNDERBOLT_1 ? 134 : 0);
        }
        // v271 0x145600050 per-skill extras, in the client's order
        if (skillId == Mechanic.AP_SALVO_PLUS_FA || skillId == Mechanic.HEXA_AP_SALVO_PLUS || skillId == Kain.TOTAL_ANNIHILATION_2) {
            outPacket.encodeLong(0);
        }
        if (skillId == SkillConstants.HEXA_SHADOW_WALKER || skillId == SkillConstants.HEXA_SHADOW_WALKER_2 || skillId == SkillConstants.HEXA_SHADOW_WALKER_3) {
            outPacket.encodeInt(0);
        }

        if (skillId == ThunderBreaker.LIGHTNING_SPEAR_MULTISTRIKE_THUNDERBOLT_2 || skillId == ThunderBreaker.LIGHTNING_SPEAR_MULTISTRIKE_THUNDERBOLT_1) {
            outPacket.encodeInt(lightningStrikes); // count
            outPacket.encodeRectInt(rect);
            outPacket.encodeByte(left);
        }
        // client 0x14505edd0 reads one more int for these
        if (skillId == Blaster.AFTERIMAGE_SHOCK_3 || skillId == BladeMaster.KARMA_BLADE_BUFF || skillId == DemonSlayer.NIGHTMARE_FLAMES || skillId == Ren.THOUSAND_BLOSSOM_FLURRY_EX) {
            outPacket.encodeInt(0); // AfterImage Shock, Karma Blade, Nightmare Flames, Thousand Blossom Flurry EX
        }
        if (skillId == NightWalker.ABYSSAL_DARKNESS) { // Abyssal Darkness: Mark of Darkness stacks
            outPacket.encodeInt(lightningStrikes);
        } else if (skillId == IceLightning.CRYO_SHOCK) { // Cryo Shock
            outPacket.encodeInt(0);
        }
        if (skillId == Mihile.OFFENSIVE_DEFENSE_WAVE || skillId == Mihile.HEXA_OFFENSIVE_DEFENSE_WAVE) { // Offensive Defense Wave family (client family check)
            outPacket.encodeByte(0);
        }

        return outPacket;
    }

    public static OutPacket petActivateChange(Pet pet, boolean active, byte removedReason) {
        OutPacket outPacket = new OutPacket(OutHeader.PET_ACTIVATED);

        outPacket.encodeInt(pet.getOwnerID());
        outPacket.encodeInt(pet.getIdx());
        outPacket.encodeByte(active);
        if (active) {
            outPacket.encodeByte(true); // init
            pet.encode(outPacket);
        } else {
            outPacket.encodeByte(removedReason);
        }

        return outPacket;
    }

    public static OutPacket setDead(boolean tremble) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_DEAD);

        outPacket.encodeByte(tremble);
        outPacket.encodeInt(0); // v265, unknown

        return outPacket;
    }

    public static OutPacket openUIOnDead(boolean onDeadRevive, boolean onDeadProtectForBuff, boolean onDeadProtectExpMaplePoint,
                                         boolean onDeadProtectBuffMaplePoint, boolean anniversary, ReviveType reviveType) {
        OutPacket outPacket = new OutPacket(OutHeader.OPEN_UI_DEAD);

        int reviveMask = 0;
        if (onDeadRevive) {
            reviveMask |= 0x1;
        }
        if (onDeadProtectForBuff) {
            reviveMask |= 0x2;
        }
        if (onDeadProtectBuffMaplePoint) {
            reviveMask |= 0x4;
        }
        if (onDeadProtectExpMaplePoint) {
            reviveMask |= 0x8;
        }
        // v265 (23 bytes): int mask, int type, int, byte, int, int, byte, byte
        outPacket.encodeInt(reviveMask);
        outPacket.encodeInt(reviveType.getVal());
        outPacket.encodeInt(0);
        boolean boss = reviveType == ReviveType.BOSS;
        // boss revive window: byte 1, int 30, int 5 (reference server)
        outPacket.encodeByte(boss);
        outPacket.encodeInt(boss ? 30 : 0);
        outPacket.encodeInt(boss ? 5 : 0);
        outPacket.encodeByte(0);
        outPacket.encodeByte(0);

        return outPacket;
    }

    public static OutPacket skillCooltimeSetM(int skillID, int cdMS) {
        Map<Integer, Integer> cds = new HashMap<>();
        cds.put(skillID, cdMS);
        return skillCooltimeSetM(cds);
    }

    /**
     * Cooldowns in milliseconds; the reference sends every cooldown this way (int count, then skill id, ms).
     */
    public static OutPacket skillCooltimeSetM(Map<Integer, Integer> cooldowns) {
        if (net.swordie.ms.ServerSettings.getBoolean("test.cooltimeV271", false)) {
            OutPacket outPacket = new OutPacket(OutHeader.COOLTIME_SET_MS);
            outPacket.encodeInt(cooldowns.size());
            cooldowns.forEach((skillID, cooldown) -> {
                outPacket.encodeInt(skillID);
                outPacket.encodeInt(cooldown == null || cooldown <= 0 ? 0 : cooldown);
                outPacket.encodeInt(0);
            });
            return outPacket;
        }
        // 1646 takes seconds (the client multiplies by 1000), so ms are rounded up (0 still clears the cooldown)
        OutPacket outPacket = new OutPacket(OutHeader.SKILL_COOLTIME_SET);

        outPacket.encodeInt(cooldowns.size());
        cooldowns.forEach((skillID, cooldown) -> {
            outPacket.encodeInt(skillID);
            outPacket.encodeInt(cooldown == null || cooldown <= 0 ? 0 : (int) ((cooldown + 999L) / 1000));
        });

        return outPacket;
    }

    public static OutPacket skillCooltimeSet(Map<Integer, Integer> cooldowns) {
        OutPacket outPacket = new OutPacket(OutHeader.SKILL_COOLTIME_SET);

        outPacket.encodeInt(cooldowns.size());
        cooldowns.forEach((skillID, cooldown) -> {
            outPacket.encodeInt(skillID);
            outPacket.encodeInt(cooldown);
        });

        return outPacket;
    }

    public static OutPacket skillCooltimeReduce(List<Integer> skillIDs, int cooltimeR) {
        OutPacket outPacket = new OutPacket(OutHeader.SKILL_COOLTIME_REDUCE);

        outPacket.encodeInt(cooltimeR);
        outPacket.encodeByte(skillIDs.size());
        for (int skillID : skillIDs) {
            outPacket.encodeInt(skillID);
        }

        return outPacket;
    }

    public static OutPacket setBuffProtector(int itemID, boolean active) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_BUFF_PROTECTOR);

        outPacket.encodeInt(itemID);
        outPacket.encodeByte(active);

        return outPacket;
    }

    public static OutPacket deathCountInfo(int deathCount) {
        OutPacket outPacket = new OutPacket(OutHeader.DEATH_COUNT_INFO);

        outPacket.encodeInt(deathCount);

        return outPacket;
    }

    public static OutPacket deathCountInfo2(int deathCount) { // Used for 'KnockOut counter' in Commerci
        return deathCountInfo2(0, deathCount);
    }

    /** with the character's id (the Epic Dungeon stage window only takes its own) */
    public static OutPacket deathCountInfo2(int charId, int deathCount) {
        OutPacket outPacket = new OutPacket(OutHeader.DEATH_COUNT_INFO_2);

        outPacket.encodeInt(charId);
        outPacket.encodeInt(deathCount);

        return outPacket;
    }

    public static OutPacket codexUpdate(FamiliarCodexManager fcm, int mask) {
        return codexUpdate(fcm, mask, fcm.getActiveFamiliarPreset(), fcm.getActiveFamiliars(),
                fcm.getActiveBadgePreset(), fcm.getActiveBadges());
    }

    /**
     * CODEX_UPDATE (v271 0x14551b500): no mask. B23 GW_FamiliarUser (int cid, short fatigue = summon gauge,
     * short inven = slots, int, short version, 8 last fatigue update, byte preset), then the 3 familiar SNs and 8 badges
     * of that preset. One preset index serves familiars and badges. The client ignores the packet until CharacterData
     * F85 bit 1 created its record. Mask and preset arguments are kept for the callers (v265 form).
     */
    public static OutPacket codexUpdate(FamiliarCodexManager fcm, int mask, int famPreset, int[] presetFamiliars,
                                        int badgePreset, int[] presetBadges) {
        OutPacket outPacket = new OutPacket(OutHeader.CODEX_UPDATE);
        encodeCodexRecordV271(outPacket, fcm);
        encodeCodexFamiliars(outPacket, fcm.getActiveFamiliars());
        encodeCodexBadges(outPacket, fcm.getActiveBadges());
        return outPacket;
    }

    /** The 23-byte v271 codex record (also CharacterData F85 bit 1). */
    public static void encodeCodexRecordV271(OutPacket outPacket, FamiliarCodexManager fcm) {
        outPacket.encodeInt(fcm.getChr().getId());
        outPacket.encodeShort(fcm.getSummonGauge());
        outPacket.encodeShort(fcm.getFamiliarSlots());
        outPacket.encodeInt(fcm.getFamiliars().size());
        outPacket.encodeShort(0);
        outPacket.encodeFT(FileTime.currentTime());
        outPacket.encodeByte(fcm.getActiveFamiliarPreset());
    }

    public static void encodeCodexFamiliarsV271(OutPacket outPacket, int[] familiars) {
        encodeCodexFamiliars(outPacket, familiars);
    }

    public static void encodeCodexBadgesV271(OutPacket outPacket, int[] badges) {
        encodeCodexBadges(outPacket, badges);
    }

    private static void encodeCodexFamiliars(OutPacket outPacket, int[] familiars) {
        for (int i = 0; i < 3; i++) {
            outPacket.encodeInt(familiars != null && i < familiars.length ? familiars[i] : 0);
        }
    }

    private static void encodeCodexBadges(OutPacket outPacket, int[] badges) {
        for (int i = 0; i < 8; i++) {
            outPacket.encodeByte(badges != null && i < badges.length ? badges[i] : -1);
        }
    }

    public static OutPacket familiarAddResult(List<Familiar> familiars) {
        return familiarAddResult(familiars, FamiliarModifiedMask.All.getVal());
    }

    public static OutPacket familiarAddResult(Familiar familiar, int mask) {
        return familiarAddResult(Collections.singletonList(familiar), mask);
    }

    public static OutPacket familiarRemoveResult(Familiar familiar) {
        return familiarRemoveResult(Collections.singletonList(familiar));
    }

    public static OutPacket familiarRemoveResult(List<Familiar> familiars) {
        for (int i = 0; i < familiars.size(); i++) {
            Familiar f = familiars.get(i);
            if (f != null) {
                f.setFamiliarID(0); // -> removed
            }
        }
        return familiarAddResult(familiars, 1);
    }

    public static OutPacket familiarAddResult(List<Familiar> familiars, int mask) {
        OutPacket outPacket = new OutPacket(OutHeader.FAMILIAR_ADD_RESULT);

        // v271 (0x14551b5d0): every entry is the whole 56-byte record (a removed familiar has FID 0); the mask is unused
        outPacket.encodeInt(familiars.size());
        for (Familiar f : familiars) {
            f.encodeRecord(outPacket);
        }

        return outPacket;
    }

    public static OutPacket familiarModifyResult(FamiliarModifiedType type, Object o) {
        OutPacket outPacket = new OutPacket(OutHeader.FAMILIAR_MODIFY_RESULT);

        outPacket.encodeByte(type.getVal());
        switch (type) {
            case Extract:
                int points = (int) o;
                outPacket.encodeInt(points);
                break;

            case Fuse:
            case ToggleLock:
                break;

            case Upgrade:
            case Rename:
            case ReleaseOption:
            case FamiliarSlotExpanded:
                outPacket.encodeByte((boolean) o);
                break;

            // reader 0x144f6d570: 7 on success reads one more byte (0 = a further result body), and types 7-10
            // read a reason byte after a failure
            case RankIncreased:
            case MaxLvIncreased:
                boolean success = (boolean) o;
                outPacket.encodeByte(success);
                if (success && type == FamiliarModifiedType.RankIncreased) {
                    outPacket.encodeByte(1);
                }
                if (!success) {
                    outPacket.encodeByte(0);
                }
                break;
        }

        return outPacket;
    }

    public static OutPacket familiarBadgeAddResult(Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.FAMILIAR_BADGE_ADD_RESULT);

        var badgePageInfo = chr.getFamiliarCodexManager().getBadgePageInfo();
        outPacket.encodeInt(badgePageInfo.size());

        for (var entry : badgePageInfo.entrySet()) {
            var page = entry.getKey();
            var info = entry.getValue();

            encodeBadgePageV271(outPacket, chr.getId(), page, info.getCollectedMask());
        }

        return outPacket;
    }

    /** v271 badge page record (21 bytes; v265 had a short page): int owner, byte page, 4 x int collected mask. */
    public static void encodeBadgePageV271(OutPacket outPacket, int chrId, int page, int[] collectedMask) {
        outPacket.encodeInt(chrId);
        outPacket.encodeByte(page);
        for (int i = 0; i < 4; i++) {
            outPacket.encodeInt(collectedMask[i]);
        }
    }

    public static OutPacket petMove(int id, int petID, MovementInfo movementInfo) {
        OutPacket outPacket = new OutPacket(OutHeader.PET_MOVE);

        outPacket.encodeInt(id);
        outPacket.encodeInt(petID);
        movementInfo.encodeWithoutKeyPads(outPacket); // GMS sends the path without the key pad part

        return outPacket;
    }

    public static OutPacket setDressChanged(boolean on, boolean dressInfinity) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_DRESS_CHANGED);

        // 0x144ea0480 reads two bytes
        outPacket.encodeByte(on);
        outPacket.encodeByte(dressInfinity);

        return outPacket;
    }

    public static OutPacket setDirectionMode(boolean show, int unk) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_DIRECTION_MODE);

        outPacket.encodeByte(show); // v271 0x1453a96b0 reads only this byte (GMS sends 00)

        return outPacket;
    }

    public static OutPacket setInGameDirectionMode(boolean lockUI, boolean blackFrame, boolean forceMouseOver) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_IN_GAME_DIRECTION_MODE);

        encodeInGameDirectionMode(outPacket, lockUI, blackFrame, forceMouseOver, !lockUI);

        return outPacket;
    }

    public static OutPacket setInGameDirectionMode(boolean lockUI, boolean blackFrame, boolean forceMouseOver, boolean showUI) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_IN_GAME_DIRECTION_MODE);

        encodeInGameDirectionMode(outPacket, lockUI, blackFrame, forceMouseOver, showUI);

        return outPacket;
    }

    // v271 0x1453a96f0: leave = mode 0, exit flag; enter = mode, 4 flags, string. The 4th flag 0 shows the skip
    // button (outside some maps) whose click sends the string back (In SCENE_SKIP_REQUEST); 1 = no button.
    private static void encodeInGameDirectionMode(OutPacket outPacket, boolean lockUI, boolean blackFrame,
                                                  boolean forceMouseOver, boolean showUI) {
        encodeInGameDirectionMode(outPacket, lockUI, blackFrame, forceMouseOver, showUI, null);
    }

    private static void encodeInGameDirectionMode(OutPacket outPacket, boolean lockUI, boolean blackFrame,
                                                  boolean forceMouseOver, boolean showUI, String skipKey) {
        outPacket.encodeByte(lockUI);
        outPacket.encodeByte(blackFrame);
        if (lockUI) {
            outPacket.encodeByte(forceMouseOver);
            outPacket.encodeByte(showUI);
            outPacket.encodeByte(skipKey == null);
            outPacket.encodeString(skipKey == null ? "" : skipKey);
        }
    }

    /** GMS story scenes: mode 1, flags 0 0 0, the skip button with its key ("skip_<id>"). */
    public static OutPacket setInGameDirectionModeWithSkip(boolean blackFrame, String skipKey) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_IN_GAME_DIRECTION_MODE);

        encodeInGameDirectionMode(outPacket, true, blackFrame, false, false, skipKey);

        return outPacket;
    }

    /** The skip's first answer (GMS: direction event 37 with three zero bytes, then stand-alone mode off). */
    public static OutPacket inGameDirectionSkipped() {
        OutPacket outPacket = new OutPacket(OutHeader.IN_GAME_DIRECTION_EVENT);

        outPacket.encodeByte(37);
        outPacket.encodeByte(0);
        outPacket.encodeByte(0);
        outPacket.encodeByte(0);

        return outPacket;
    }

    public static OutPacket setStandAloneMode(boolean on) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_STAND_ALONE_MODE);

        outPacket.encodeByte(on);

        return outPacket;
    }

    public static OutPacket inGameDirectionEvent(InGameDirectionEvent igdr) {
        OutPacket outPacket = new OutPacket(OutHeader.IN_GAME_DIRECTION_EVENT);

        outPacket.encode(igdr);

        return outPacket;
    }

    public static OutPacket hireTutor(boolean set) {
        OutPacket outPacket = new OutPacket(OutHeader.HIRE_TUTOR);

        outPacket.encodeByte(set);

        return outPacket;
    }

    public static OutPacket tutorMsg(int id, int duration) {
        OutPacket outPacket = new OutPacket(OutHeader.TUTOR_MSG);

        boolean automated = true;
        outPacket.encodeByte(automated);
        outPacket.encodeInt(id);
        outPacket.encodeInt(duration);

        return outPacket;
    }

    public static OutPacket tutorMsg(String message, int width, int duration) {
        OutPacket outPacket = new OutPacket(OutHeader.TUTOR_MSG);

        boolean automated = false;
        outPacket.encodeByte(automated);
        outPacket.encodeString(message);
        outPacket.encodeInt(width);
        outPacket.encodeInt(duration);

        return outPacket;
    }

    public static OutPacket emotion(int emotion, int duration, boolean byItemOption) {
        OutPacket outPacket = new OutPacket(OutHeader.EMOTION);

        outPacket.encodeInt(emotion);
        outPacket.encodeInt(duration);
        outPacket.encodeByte(byItemOption);

        return outPacket;
    }

    public static OutPacket questResult(QuestType type, int questID, int npcTemplateID, int secondQuestID, boolean startNavigation) {
        OutPacket outPacket = new OutPacket(OutHeader.QUEST_RESULT);

        outPacket.encodeByte(type.getVal());
        outPacket.encodeInt(questID);
        outPacket.encodeInt(npcTemplateID);

        outPacket.encodeInt(secondQuestID); // starts a second quest
        outPacket.encodeByte(startNavigation);

        return outPacket;
    }

    public static OutPacket medalReissueResult(MedalReissueResultType medalReissueResultType, int itemId) {
        OutPacket outPacket = new OutPacket(OutHeader.MEDAL_REISSUE_RESULT);

        outPacket.encodeByte(medalReissueResultType.getVal());
        outPacket.encodeInt(itemId);

        return outPacket;
    }

    public static OutPacket stopKeyDownSkillRequest(int skillID) {
        OutPacket outPacket = new OutPacket(OutHeader.STOP_KEY_DOWN_SKILL_REQUEST);

        outPacket.encodeInt(skillID);

        return outPacket;
    }

    public static OutPacket userConsumeItemCooltime() {
        return new OutPacket(OutHeader.USER_CONSUME_ITEM_COOLTIME);
    }

    public static OutPacket moveParticleEff(String type, Position startPoint, Position endPoint, int moveTime, int totalCount, int oneSprayMin, int oneSprayMax) {
        OutPacket outPacket = new OutPacket(OutHeader.MOVE_PARTICLE_EFF);

        outPacket.encodeString(type);
        outPacket.encodePosition(startPoint);
        outPacket.encodePosition(endPoint);
        outPacket.encodeShort(moveTime);
        outPacket.encodeShort(totalCount);
        outPacket.encodeShort(oneSprayMin);
        outPacket.encodeShort(oneSprayMax);

        return outPacket;
    }

    public static OutPacket balloonMsg(String message, int width, int timeOut, Position position) {
        OutPacket outPacket = new OutPacket(OutHeader.BALLOON_MSG);

        outPacket.encodeString(message);
        outPacket.encodeShort(width);// 100
        outPacket.encodeShort(timeOut);// 3
        outPacket.encodeByte(position == null);
        if (position != null) {
            outPacket.encodePosition(position);
        }
        return outPacket;
    }

    public static OutPacket lightningUnionSubAttack(int skillId, int slv) {
        OutPacket outPacket = new OutPacket(OutHeader.LIGHTNING_UNION_SUB_ATTACK);

        outPacket.encodeInt(skillId);
        outPacket.encodeInt(ThunderBreaker.LIGHTNING_CASCADE);
        outPacket.encodeInt(slv);

        return outPacket;
    }

    public static OutPacket giantShadowSpearAttack(Position position) {
        OutPacket outPacket = new OutPacket(OutHeader.GIANT_SHADOW_SPEAR_ATTACK);

        outPacket.encodePositionInt(position);

        return outPacket;
    }

    public static OutPacket shootObjectCreated(ShootObjectSkillInfo shootObjectSkillInfo) {
        OutPacket outPacket = new OutPacket(OutHeader.SHOOT_OBJECT_CREATED);

        outPacket.encodeByte(true);
        outPacket.encodeInt(shootObjectSkillInfo.getRequestKey());
        outPacket.encodeInt(shootObjectSkillInfo.getSkillId());
        outPacket.encodeInt(shootObjectSkillInfo.getSlv());
        outPacket.encodeInt(shootObjectSkillInfo.getShootObjects().size());
        for (ShootObject shootObject : shootObjectSkillInfo.getShootObjects()) {
            outPacket.encodeInt(shootObject.getId());
        }

        return outPacket;
    }

    public static OutPacket doActivePsychicArea(PsychicArea pa) {
        OutPacket outPacket = new OutPacket(OutHeader.DO_ACTIVE_PSYCHIC_AREA);

        outPacket.encodeInt(pa.localPsychicAreaKey);
        outPacket.encodeInt(pa.psychicAreaKey);

        return outPacket;
    }

    public static OutPacket dodgeSkillReady() {
        return new OutPacket(OutHeader.DODGE_SKILL_READY);
    }

    public static OutPacket enterFieldPsychicInfo(int userID, PsychicLock pl, List<PsychicArea> psychicAreas) {
        OutPacket outPacket = new OutPacket(OutHeader.ENTER_FIELD_PSYCHIC_INFO);
        // v271 (0x1454a8610) reads only this flag; the lock and area lists of older versions are gone
        outPacket.encodeByte(true);
        return outPacket;
    }

    public static OutPacket b2BodyResult(short requestType, B2Body b2Body) {
        OutPacket outPacket = new OutPacket(OutHeader.B2_BODY_RESULT);
        Char chr = b2Body.getChr();
        outPacket.encodeShort(requestType);
        outPacket.encodeInt(chr.getId()); // owner Id
        outPacket.encodeInt(chr.getFieldID());
        short loopsize = 1;

        switch (requestType) {
            case 0:
                outPacket.encodeShort(loopsize); // loop size
                for (int i = 0; i < loopsize; i++) {
                    outPacket.encodeInt(b2Body.getBodyId());
                    outPacket.encodeByte(b2Body.getType());
                    outPacket.encodeByte(true); // redraw
                    outPacket.encodePosition(b2Body.getPosition());
                    if (b2Body.getType() == 5) {
                        outPacket.encodeShort(b2Body.getnRadius());
                        outPacket.encodeShort(b2Body.getfRadius());
                    } else if (b2Body.getType() == 6) {
                        outPacket.encodeInt(0); // unk
                    }
                    outPacket.encodeShort(b2Body.getDuration());
                    outPacket.encodeShort(b2Body.getScale());
                    outPacket.encodeShort(0); // as GMS
                    outPacket.encodeShort(10); // as GMS
                    outPacket.encodeInt(b2Body.getSkillId());
                    outPacket.encodeShort(b2Body.getSlv());
                    outPacket.encodeByte(chr.isLeft());
                }
                break;
            case 3:
                outPacket.encodeInt(chr.getId());
                outPacket.encodeInt(b2Body.getSkillId());
                outPacket.encodeInt(b2Body.getMaxSpeedX());
                outPacket.encodeInt(b2Body.getMaxSpeedY());
                outPacket.encodeInt(b2Body.getBodyId()); // v265
                break;
            case 4:
                outPacket.encodeShort(loopsize);
                for (int i = 0; i < loopsize; i++) {
                    outPacket.encodeByte(true); // redraw
                    outPacket.encodePosition(b2Body.getPosition());
                    outPacket.encodeInt(b2Body.getDuration());
                    outPacket.encodeShort(0); // unk
                    outPacket.encodeShort(0); // unk
                    outPacket.encodeShort(0); // unk
                    outPacket.encodeInt(b2Body.getSkillId());
                    outPacket.encodeByte(chr.isLeft());
                    outPacket.encodeInt(b2Body.getMaxSpeedX());
                    outPacket.encodeInt(b2Body.getMaxSpeedY());
                }
                break;
            case 5:
                outPacket.encodeInt(b2Body.getSkillId()); // mob Skill Id
                outPacket.encodeInt(b2Body.getSlv()); // mob Skill Lv
                break;
        }

        return outPacket;
    }

    public static OutPacket b2BodyResultNew(short requestType, B2Body b2Body) {
        OutPacket outPacket = new OutPacket(OutHeader.B2_BODY_RESULT_NEW);

        outPacket.encodeInt(requestType);
        outPacket.encodeInt(b2Body.getBodyId());

        return outPacket;
    }

    public static OutPacket registerFadeInOutAnimation(int beginFadeTime, int shadeDuration, int endFadeTime, int shadeIntensity) {
        OutPacket outPacket = new OutPacket(OutHeader.REGISTER_FADE_IN_OUT_ANIMATION);

        outPacket.encodeInt(beginFadeTime);
        outPacket.encodeInt(shadeDuration);
        outPacket.encodeInt(endFadeTime);
        outPacket.encodeInt(shadeIntensity);

        return outPacket;
    }

    /** Hyper Teleport Rock rental prices (GMS): int n, n x (int minutes, int, int mesos). */
    public static OutPacket hyperRockPriceList(int[][] options) {
        OutPacket outPacket = new OutPacket(OutHeader.HYPER_ROCK_PRICE_LIST);

        outPacket.encodeInt(options.length);
        for (int[] o : options) {
            outPacket.encodeInt(o[0]);
            outPacket.encodeInt(0);
            outPacket.encodeInt(o[1]);
        }

        return outPacket;
    }

    /** 1 success, 4, 4 minutes bought; on success the client shows the end time from quest-ex 1953 "time". */
    public static OutPacket hyperRockBuyResult(boolean success, int minutes) {
        OutPacket outPacket = new OutPacket(OutHeader.HYPER_ROCK_BUY_RESULT);

        outPacket.encodeByte(success);
        outPacket.encodeInt(0);
        outPacket.encodeInt(minutes);

        return outPacket;
    }

    /** Interim form: moves the user to the servant and leaves the servant where it is. */
    public static OutPacket greaterDarkServantSwapResult(Position servantPos) {
        return greaterDarkServantSwapResult(servantPos, servantPos, false, true);
    }

    /**
     * v271 0x1454c8040: user goes to userPos, the servant to servantPos (normally the user's old spot).
     * skipCheck 0 makes the client test the spot first (message 0x3b60 if it can't stand there);
     * keepServant 1 leaves the servant in place.
     */
    public static OutPacket greaterDarkServantSwapResult(Position userPos, Position servantPos, boolean skipCheck,
                                                         boolean keepServant) {
        OutPacket outPacket = new OutPacket(OutHeader.GREATER_DARK_SERVANT_SWAP_RESULT);

        outPacket.encodePositionInt(userPos);
        outPacket.encodePositionInt(servantPos);
        outPacket.encodeByte(skipCheck);
        outPacket.encodeByte(keepServant);

        return outPacket;
    }

    public static OutPacket skillRequestRequest(int skillId) {
        OutPacket outPacket = new OutPacket(OutHeader.SKILL_REQUEST_REQUEST);

        outPacket.encodeInt(skillId);
        outPacket.encodeInt(0);

        return outPacket;
    }

    public static OutPacket aceInTheHoleFinisher(int skillId, int slv, Position position) {
        OutPacket outPacket = new OutPacket(OutHeader.ACE_IN_THE_HOLE_FINISHER);

        outPacket.encodeInt(skillId);
        outPacket.encodeInt(slv);
        outPacket.encodeInt(1);
        outPacket.encodePositionInt(position);

        return outPacket;
    }

    public static OutPacket bonusAttackDelayRequest(int skillId, int delay) {
        return bonusAttackDelayRequest(Collections.singletonList(new Tuple<>(skillId, delay)));
    }

    public static OutPacket bonusAttackDelayRequest(List<Tuple<Integer, Integer>> bonusAttackTuples) {
        OutPacket outPacket = new OutPacket(OutHeader.BONUS_ATTACK_DELAY_REQUEST);

        outPacket.encodeInt(bonusAttackTuples.size());
        for (var entry : bonusAttackTuples) {
            outPacket.encodeInt(entry.getLeft());    // skill Id
            outPacket.encodeInt(entry.getRight());   // delay
        }

        return outPacket;
    }

    public static OutPacket aggroRankInfoName(List<Tuple<Long, String>> chars) {
        OutPacket outPacket = new OutPacket(OutHeader.AGGRO_RANK_INFO_NAME);

        outPacket.encodeInt(chars.size());
        for (var chr : chars) {
            outPacket.encodeString(chr.getRight());
        }

        return outPacket;
    }

    public static OutPacket gatherRequestResult(int objId, HarvestResultType harvestResultType) {
        OutPacket outPacket = new OutPacket(OutHeader.GATHER_REQUEST_RESULT);

        outPacket.encodeInt(objId);
        outPacket.encodeInt(harvestResultType.getValue());

        return outPacket;
    }

    public static OutPacket harvestResult(int objId, HarvestResultType harvestResultType) {
        OutPacket outPacket = new OutPacket(OutHeader.HARVEST_RESULT);

        outPacket.encodeInt(objId);
        outPacket.encodeInt(harvestResultType.getValue());

        return outPacket;
    }

    public static OutPacket groupChairInvite(int inviterChrId) {
        OutPacket outPacket = new OutPacket(OutHeader.GROUP_CHAIR_INVITE);

        outPacket.encodeInt(inviterChrId);

        return outPacket;
    }

    public static OutPacket sitDummyPortableChair(int itemId) {
        OutPacket outPacket = new OutPacket(OutHeader.SIT_DUMMY_PORTABLE_CHAIR);

        outPacket.encodeInt(itemId);
        outPacket.encodeInt(0);

        return outPacket;
    }

    public static OutPacket bagItemUseResult(int bagItemIndex, int itemId, boolean firstTime) {
        OutPacket outPacket = new OutPacket(OutHeader.BAG_ITEM_USE_RESULT);

        outPacket.encodeInt(bagItemIndex);
        outPacket.encodeInt(itemId);
        outPacket.encodeByte(0);
        outPacket.encodeByte(firstTime); // !reOpen

        return outPacket;
    }

    public static OutPacket sendClientResolution() {
        OutPacket outPacket = new OutPacket(OutHeader.SEND_CLIENT_RESOLUTION);

        return outPacket;
    }

    public static OutPacket poolmaker(boolean enable, int skillId, int remaining, int time) {
        OutPacket outPacket = new OutPacket(OutHeader.POOLMAKER);

        outPacket.encodeByte(enable);
        if (enable) {
            outPacket.encodeInt(skillId); // v265
            outPacket.encodeInt(remaining);
            outPacket.encodeInt(time);
        }

        return outPacket;
    }

    public static OutPacket egoWeapon(int skillId, Position position) {
        OutPacket outPacket = new OutPacket(OutHeader.EGO_WEAPON);

        outPacket.encodeInt(skillId);
        outPacket.encodePositionInt(position);

        return outPacket;
    }

    public static OutPacket lightOfCourage(boolean active, Position position, List<Integer> mobObjIdList) {
        // OutHeader.LIGHT_OF_COURAGE
        throw new NotImplementedYetException("LightOfCourage");

//        OutPacket outPacket = new OutPacket(0);
//
//        outPacket.encodeByte(active);
//
//        if (active) {
//            outPacket.encodePosition(position);
//
//            outPacket.encodeInt(mobObjIdList.size());
//            for (var mobObjId : mobObjIdList) {
//                outPacket.encodeInt(mobObjId);
//            }
//
//            outPacket.encodeInt(mobObjIdList.size());
//            for (var mobObjId : mobObjIdList) {
//                outPacket.encodeInt(mobObjId);
//            }
//        }
//        return outPacket;
    }


    public static OutPacket kainStackOverTimeSkillIncreaseResult(int skillId, int curStack, int maxStack, int requestInterval) {
        OutPacket outPacket = new OutPacket(OutHeader.KAIN_STACK_OVER_TIME_SKILL_INCREASE_RESULT);

        outPacket.encodeInt(skillId);
        outPacket.encodeInt(curStack);
        outPacket.encodeInt(maxStack);
        outPacket.encodeInt(requestInterval); // request interval
        outPacket.encodeByte(0);

        return outPacket;
    }


    public static OutPacket deathBlessing(int skillId, Map<Integer, Integer> deathBlessingMap, int duration) {
        OutPacket outPacket = new OutPacket(OutHeader.DEATH_BLESSING);

        outPacket.encodeInt(skillId); // client uses it for the stack effect
        outPacket.encodeInt(0); // read and ignored by the client
        outPacket.encodeInt(deathBlessingMap.size());
        for (var entry : deathBlessingMap.entrySet()) {
            outPacket.encodeInt(entry.getKey()); // mob object id
            outPacket.encodeInt(entry.getValue()); // stack
            outPacket.encodeInt(duration);
        }
        return outPacket;
    }


    public static OutPacket deathBlessingMaterialisation(int chrId, Map<Integer, Integer> deathBlessingMap, int skillId, int delay) {
        OutPacket outPacket = new OutPacket(OutHeader.DEATH_BLESSING_MATERIALISATION);

        outPacket.encodeInt(skillId);
        outPacket.encodeInt(chrId); // client drops the packet unless this is its own id
        outPacket.encodeInt(deathBlessingMap.size());
        for (var entry : deathBlessingMap.entrySet()) {
            var mobObjId = entry.getKey();
            var consumed = entry.getValue();

            outPacket.encodeInt(mobObjId);
            outPacket.encodeInt(consumed); // amount consumed
            outPacket.encodeInt(delay);
        }

        return outPacket;
    }


    public static OutPacket remainIncenseActivation(List<Wreckage> incenseList) {
        OutPacket outPacket = new OutPacket(OutHeader.REMAIN_INCENSE_ACTIVATION);

        outPacket.encodeInt(incenseList.size());
        for (var incense : incenseList) {
            outPacket.encodeInt(incense.getObjectId());
            outPacket.encodeInt(0); // time offset
        }

        return outPacket;
    }

    public static OutPacket recipeCooltimeResult(int recipeId, int cooltimeMs) {
        OutPacket outPacket = new OutPacket(OutHeader.USER_RECIPE_COOLTIME_RESULT);

        outPacket.encodeInt(recipeId);
        outPacket.encodeInt(cooltimeMs);

        return outPacket;
    }

    public static OutPacket clearGun() {
        OutPacket outPacket = new OutPacket(OutHeader.CLEAR_GUN);

        return outPacket;
    }

    public static OutPacket createGun() {
        OutPacket outPacket = new OutPacket(OutHeader.CREATE_GUN);

        return outPacket;
    }

    public static OutPacket setGun(Gun gun) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_GUN);

        outPacket.encode(gun);

        return outPacket;
    }

    public static OutPacket setAmmo(int ammo) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_AMMO);

        outPacket.encodeInt(ammo);

        return outPacket;
    }

    public static OutPacket resultShootAttackInFPSMode() {
        OutPacket outPacket = new OutPacket(OutHeader.RESULT_SHOOT_ATTACK_IN_FPS_MODE);

        outPacket.encodeByte(true);

        return outPacket;
    }

    public static OutPacket doLotteryUI(Item item, List<Integer> rewardPool) {
        OutPacket outPacket = new OutPacket(OutHeader.DO_LOTTERY_UI);

        outPacket.encodeShort(item.getBagIndex());
        outPacket.encodeInt(item.getItemId());
        outPacket.encodeByte(0);
        outPacket.encodeInt(rewardPool.size()); // size
        rewardPool.forEach(outPacket::encodeInt);

        return outPacket;
    }

    public static OutPacket setSlowDown(int glideCoefficient, int timeMS) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_SLOW_DOWN);

        outPacket.encodeShort(glideCoefficient); // 30
        outPacket.encodeShort(timeMS); // 360

        return outPacket;
    }

    // Aran's Adrenal Awakening gauge; full (cur == max > 0) lets Adrenaline Rush Activate be pressed during the Rush
    public static OutPacket incComboResponseByComboRecharge(int cur, int max) {
        OutPacket outPacket = new OutPacket(OutHeader.INC_COMBO_RESPONSE_BY_COMBO_RECHARGE);

        outPacket.encodeInt(cur);
        outPacket.encodeInt(max);

        return outPacket;
    }

    public static OutPacket trueNobilityShield(int shieldAmount) {
        OutPacket outPacket = new OutPacket(OutHeader.TRUE_NOBILITY_SHIELD);

        outPacket.encodeInt(shieldAmount);

        return outPacket;
    }

    public static OutPacket kannaSoulBomb(int delay, List<Position> positions) {
        OutPacket outPacket = new OutPacket(OutHeader.KANNA_SOUL_BOMB);

        outPacket.encodeShort(delay);
        outPacket.encodeByte(positions.size());
        positions.forEach(outPacket::encodePosition);

        return outPacket;
    }

    public static OutPacket serverAckMobZoneStateChange(boolean ack) {
        OutPacket outPacket = new OutPacket(OutHeader.SERVER_ACK_MOB_ZONE_STATE_CHANGE);

        outPacket.encodeByte(ack);

        return outPacket;
    }

    public static OutPacket summonSequenceAttackInstructions(int summonSkillId, List<Integer> sequence) {
        OutPacket outPacket = new OutPacket(OutHeader.SUMMON_SEQUENCE_ATTACK_INSTRUCTIONS);

        outPacket.encodeInt(summonSkillId);

        outPacket.encodeInt(sequence.size());
        sequence.forEach(outPacket::encodeInt);

        return outPacket;
    }

    public static OutPacket ursusSurvivorsCounter(int total, int survivors) {
        OutPacket outPacket = new OutPacket(OutHeader.URSUS_SURVIVORS_COUNTER);

        outPacket.encodeInt(total); // total players in field
        outPacket.encodeInt(survivors); // survivors

        return outPacket;
    }

    /**
     * Marks one mob (0x1454f05d0). markSkill is the mark object's key: the client's factory only builds one for
     * 3221007 (Snipe), not for the skill that hits. Set: n x (mob, int, ms since marked, int, int, byte, int).
     */
    public static OutPacket setMobAdvMark(int markSkillId, int mobObjectId, int elapsedMs) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_MOB_ADVENTURER_MARK);
        outPacket.encodeInt(markSkillId);
        outPacket.encodeByte(true);
        outPacket.encodeInt(1);
        outPacket.encodeInt(mobObjectId);
        outPacket.encodeInt(1);
        outPacket.encodeInt(elapsedMs);
        outPacket.encodeInt(0);
        outPacket.encodeInt(659);
        outPacket.encodeByte(1);
        outPacket.encodeInt(0);
        return outPacket;
    }

    /** Clears every mark of a skill. */
    public static OutPacket clearMobAdvMarks(int skillId) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_MOB_ADVENTURER_MARK);
        outPacket.encodeInt(skillId);
        outPacket.encodeByte(false);
        return outPacket;
    }

    /** The marked mob's bonus hit (0x1454f1410: int attack skill, int mark skill, int (unused), int n, n x (int mob, int)). */
    public static OutPacket attackAdvMark(int attackSkillId, int markSkillId, int mobObjectId) {
        OutPacket outPacket = new OutPacket(OutHeader.ATTACK_ADVENTURER_MARK);
        outPacket.encodeInt(attackSkillId);
        outPacket.encodeInt(markSkillId);
        outPacket.encodeInt(0);
        outPacket.encodeInt(1);
        outPacket.encodeInt(mobObjectId);
        outPacket.encodeInt(659);
        return outPacket;
    }

    /**
     * A bomb going off on this character (client 0x144dea880): the player skill's effect at (x, y) if it has one, a
     * knock-back of knockback% (the stance check is the client's), and the damage shown.
     */
    public static OutPacket timeBombAttack(int skillId, int x, int y, int knockback, int damage) {
        OutPacket outPacket = new OutPacket(OutHeader.TIME_BOMB_ATTACK);
        outPacket.encodeInt(skillId);
        outPacket.encodeInt(x);
        outPacket.encodeInt(y);
        outPacket.encodeInt(knockback);
        outPacket.encodeInt(damage);
        return outPacket;
    }


    /**
     * The Epic Dungeon stage window (UI.wz EpicDungeonUI.img/stageUI/uiType). uiType 0 = stage (number, time, mission),
     * 1 = seal (number, time, deaths), 2 = final boss (time, deaths); Angler 3 = stage (number, time), 4 = middle
     * boss (number, time, deaths), 5 = final boss (time, deaths). Flags are a byte + padding.
     */
    public static OutPacket epicDungeonStageUi(int uiType, int stageNumber, boolean time, int missionMax, int maxDeaths) {
        OutPacket out = new OutPacket(OutHeader.EPIC_DUNGEON_STAGE_UI);
        out.encodeInt(uiType);
        out.encodeInt(0);
        out.encodeInt(0);
        out.encodeInt(stageNumber);
        out.encodeInt(time ? 1 : 0);
        out.encodeInt(missionMax > 0 ? 1 : 0);
        out.encodeInt(Math.max(missionMax, 0));
        out.encodeInt(maxDeaths > 0 ? 1 : 0);
        out.encodeInt(Math.max(maxDeaths, 0));
        return out;
    }

    /** the Epic Dungeon mission gauge */
    public static OutPacket epicDungeonMission(int count) {
        OutPacket out = new OutPacket(OutHeader.EPIC_DUNGEON_MISSION);
        out.encodeInt(count);
        return out;
    }

    /** answers the Mitra morph request */
    public static OutPacket epicDungeonMorphResult() {
        return new OutPacket(OutHeader.EPIC_DUNGEON_MORPH_RESULT);
    }

    public static OutPacket mirrorDungeonUnitCleared(boolean reset, boolean noEffect, Collection<Integer> indexes) {
        OutPacket out = new OutPacket(OutHeader.MIRROR_DUNGEON_UNIT_CLEARED);
        out.encodeByte(reset);
        out.encodeByte(noEffect);
        out.encodeInt(indexes.size());
        for (int i : indexes) {
            out.encodeInt(i);
        }
        return out;
    }

    /** the clear window: seconds, best seconds, -, EXP, WP, -, level cap */
    public static OutPacket mirrorDungeonRecord(int seconds, int bestSeconds, long exp, int wp, int levelCap) {
        OutPacket out = new OutPacket(OutHeader.MIRROR_DUNGEON_RECORD);
        out.encodeInt(seconds);
        out.encodeInt(bestSeconds);
        out.encodeInt(0);
        out.encodeLong(exp);
        out.encodeInt(wp);
        out.encodeString("");
        out.encodeInt(levelCap);
        return out;
    }

    public static OutPacket setUtilDlg(String msg, int npcID) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_UTIL_DLG);

        outPacket.encodeString(msg);
        outPacket.encodeInt(npcID);

        return outPacket;
    }
}
