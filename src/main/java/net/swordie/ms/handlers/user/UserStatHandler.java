package net.swordie.ms.handlers.user;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.ProgressionLog;
import net.swordie.ms.client.character.ExtendSP;
import net.swordie.ms.client.character.hyperstats.HyperStatsModule;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.InstanceTableType;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.loaders.containerclasses.MakingSkillRecipe;
import net.swordie.ms.world.field.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;

import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.CapDebuff;

public class UserStatHandler {

    private static final Logger log = LogManager.getLogger();
    private static final long REST_HEAL_MIN_INTERVAL_MS = 2000;
    private static final Map<Integer, Long> lastRestHeal = new ConcurrentHashMap<>();
    private static final long ITEM_OPTION_HEAL_MIN_INTERVAL_MS = 3000;
    private static final Map<Integer, Long> lastItemOptionHeal = new ConcurrentHashMap<>();

    @Handler(op = InHeader.USER_SKILL_UP_REQUEST)
    public static void handleUserSkillUpRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        inPacket.decodeInt(); // tick
        int skillID = inPacket.decodeInt();
        int amount = inPacket.decodeInt();

        if (amount < 1) {
            chr.dispose();
            return;
        }
        // seperate skill/current skills for adding stuff to the base cache if everything is succesful
        Skill skill = SkillData.getSkillDeepCopyById(skillID);
        Skill curSkill = chr.getSkill(skillID);
        byte jobLevel = (byte) JobConstants.getJobLevel((short) skill.getRootId());
        if (JobConstants.isZero((short) skill.getRootId())) {
            jobLevel = JobConstants.getJobLevelByZeroSkillID(skillID);
        }
        Map<Stat, Object> stats;
        int rootId = skill.getRootId();
        boolean beginnerJob = JobConstants.isBeginnerJob((short) rootId);

        if ((!beginnerJob && !SkillConstants.isValidSkillForJobAndJobLevel(skillID, chr.getJob(), jobLevel))
                || beginnerJob && !JobConstants.isMatchingForBeginnerjob(chr.getJob(), rootId)
                || SkillConstants.isSkillFromItem(skillID)) {
            chr.getOffenseManager().addOffense(String.format("Character %d tried adding an invalid skill (job %d, skill id %d)",
                    chr.getId(), rootId, skillID));
            return;
        }
        if (JobConstants.isBeginnerJob((short) rootId)) {
            stats = new HashMap<>();
            int spentSp = 0;
            for (Skill s : chr.getSkills()) {
                if (SkillConstants.isBeginnerSpAddableSkill(s.getSkillId())) {
                    int currentLevel = s.getCurrentLevel();
                    spentSp += currentLevel;
                }
            }
            int totalSp;
            if (JobConstants.isResistance((short) skill.getRootId())) {
                totalSp = Math.min(chr.getLevel(), GameConstants.RESISTANCE_SP_MAX_LV) - 1; // sp gained from 2~10
            } else {
                totalSp = Math.min(chr.getLevel(), GameConstants.BEGINNER_SP_MAX_LV) - 1; // sp gained from 2~7
            }
            if (totalSp - spentSp >= amount) {
                int curLevel = curSkill == null ? 0 : curSkill.getCurrentLevel();
                int max = curSkill == null ? skill.getMaxLevel() : curSkill.getMaxLevel();
                if (max == 0) {
                    // some beginner skills have no max level, default is 3
                    max = 3;
                }
                var newLevel = curLevel + amount > max ? max : curLevel + amount;
                skill.setCurrentLevel(newLevel);
                for (int extraLvSkill : SkillConstants.getAutoLevelSkillsBySkillID(skillID)) {
                    Skill extraSkill = chr.getSkill(extraLvSkill, true);
                    if (extraSkill == null) {
                        continue;
                    }
                    extraSkill.setCurrentLevel(newLevel);
                }
            }
        } else if (JobConstants.isExtendSpJob(chr.getJob())) {
            ExtendSP esp = chr.getAvatarData().getCharacterStat().getExtendSP();
            int currentSp = esp.getSpByJobLevel(jobLevel);
            if (currentSp >= amount) {
                int curLevel = curSkill == null ? 0 : curSkill.getCurrentLevel();
                int max = spMaxLevel(skill, curSkill);
                var newLevel = curLevel + amount > max ? max : curLevel + amount;
                if (newLevel <= curLevel) {
                    chr.dispose(); // already at max level: don't take SP
                    return;
                }
                skill.setCurrentLevel(newLevel);
                for (int extraLvSkill : SkillConstants.getAutoLevelSkillsBySkillID(skillID)) {
                    Skill extraSkill = chr.getSkill(extraLvSkill, true);
                    if (extraSkill == null) {
                        continue;
                    }
                    extraSkill.setCurrentLevel(newLevel);
                }
                esp.setSpToJobLevel(jobLevel, currentSp - (newLevel - curLevel));
                stats = new HashMap<>();
                stats.put(Stat.sp, esp);
            } else {
                chr.getOffenseManager().addOffense(String.format("Character %d tried adding a skill without having the required amount of sp"
                                + " (required %d, has %d)",
                        chr.getId(), amount, currentSp));
                return;
            }
        } else {
            int currentSp = chr.getAvatarData().getCharacterStat().getSp();
            if (currentSp >= amount) {
                int curLevel = curSkill == null ? 0 : curSkill.getCurrentLevel();
                int max = spMaxLevel(skill, curSkill);
                var newLevel = curLevel + amount > max ? max : curLevel + amount;
                if (newLevel <= curLevel) {
                    chr.dispose(); // already at max level: don't take SP
                    return;
                }
                skill.setCurrentLevel(newLevel);
                for (int extraLvSkill : SkillConstants.getAutoLevelSkillsBySkillID(skillID)) {
                    Skill extraSkill = chr.getSkill(extraLvSkill, true);
                    if (extraSkill == null) {
                        continue;
                    }
                    extraSkill.setCurrentLevel(newLevel);
                }
                chr.getAvatarData().getCharacterStat().setSp(currentSp - (newLevel - curLevel));
                stats = new HashMap<>();
                stats.put(Stat.sp, chr.getAvatarData().getCharacterStat().getSp());
            } else {
                chr.getOffenseManager().addOffense(String.format("Character %d tried adding a skill without having the required amount of sp"
                                + " (required %d, has %d)",
                        chr.getId(), amount, currentSp));
                return;
            }
        }
        if (stats != null) {
            c.write(WvsContext.statChanged(stats));
            chr.addSkill(skill);
            c.write(WvsContext.changeSkillRecordResult(skill));
        } else {
            chr.getOffenseManager().addOffense(String.format("skill stats are null (%d)", skillID));
            chr.dispose();
        }

        if (SkillConstants.isTimerSkill(skillID)) {
            chr.getJobHandler().updateTimerSkill();
        }
    }


    /** The game data's max level: a learned skill keeps the max it had when learned, which a newer client may raise. */
    private static int spMaxLevel(Skill data, Skill learned) {
        if (data.getMaxLevel() > 0 || learned == null) {
            return data.getMaxLevel();
        }
        return learned.getMaxLevel();
    }

    private static boolean isAbilityStat(Stat stat) {
        return stat == Stat.str || stat == Stat.dex || stat == Stat.inte || stat == Stat.luk || stat == Stat.mhp
                || stat == Stat.mmp;
    }

    /** Server-side Auto Assign: as a mass AP request, clamped at the stat limits. */
    public static boolean assignAbilityPoints(Char chr, Map<Stat, Integer> allocations) {
        return applyAbilityPoints(chr, allocations, true);
    }

    /**
     * Applies one AP request only after every entry has been validated. This keeps a malformed mass request from
     * spending AP on only part of the requested stats.
     */
    private static boolean applyAbilityPoints(Char chr, Map<Stat, Integer> allocations, boolean clampAtStatCap) {
        int availableAp = chr.getStat(Stat.ap);
        if (availableAp <= 0) {
            chr.chatMessage("You do not have any Ability Points to assign.");
            chr.dispose();
            return false;
        }

        long requestedPoints = 0;
        Map<Stat, Integer> applicableAllocations = new LinkedHashMap<>();
        for (Map.Entry<Stat, Integer> entry : allocations.entrySet()) {
            Stat stat = entry.getKey();
            int points = entry.getValue();
            if (!isAbilityStat(stat) || points < 0) {
                chr.dispose();
                return false;
            }
            requestedPoints += points;
            if (requestedPoints > Integer.MAX_VALUE) {
                chr.dispose();
                return false;
            }

            int pointsPerAp = stat == Stat.mhp || stat == Stat.mmp ? 20 : 1;
            int maximum = stat == Stat.mhp || stat == Stat.mmp ? GameConstants.MAX_HP_MP : Short.MAX_VALUE;
            int roomInAp = Math.max(0, (maximum - chr.getStat(stat)) / pointsPerAp);
            if (!clampAtStatCap && points > roomInAp) {
                chr.chatMessage("That stat has reached the client limit. Your unspent AP was preserved.");
                chr.dispose();
                return false;
            }
            applicableAllocations.put(stat, clampAtStatCap ? Math.min(points, roomInAp) : points);
        }
        if (requestedPoints <= 0 || requestedPoints > availableAp) {
            chr.dispose();
            return false;
        }

        int appliedPoints = applicableAllocations.values().stream().mapToInt(Integer::intValue).sum();
        if (appliedPoints <= 0) {
            chr.chatMessage("The selected stats have reached the client limit. Your unspent AP was preserved.");
            chr.dispose();
            return false;
        }

        Map<Stat, Object> changed = new HashMap<>();
        for (Map.Entry<Stat, Integer> entry : applicableAllocations.entrySet()) {
            Stat stat = entry.getKey();
            int points = entry.getValue();
            if (points == 0) {
                continue;
            }
            int addedStat = (stat == Stat.mhp || stat == Stat.mmp) ? points * 20 : points;
            chr.addStat(stat, addedStat);
            // Keep the branches separate: a numeric ternary promotes the short branch to int, which makes STR/DEX/
            // INT/LUK an Integer and causes WvsContext.statChanged to reject the packet before AP is deducted.
            if (stat == Stat.mhp || stat == Stat.mmp) {
                changed.put(stat, chr.getStat(stat));
            } else {
                changed.put(stat, (short) chr.getStat(stat));
            }
        }
        chr.addStat(Stat.ap, -appliedPoints);
        changed.put(Stat.ap, (short) chr.getStat(Stat.ap));
        chr.write(WvsContext.statChanged(changed));
        if (appliedPoints < requestedPoints) {
            chr.chatMessage(String.format("Assigned %d AP. %d AP could not fit under the client stat limit and was preserved.",
                    appliedPoints, requestedPoints - appliedPoints));
        }
        chr.dispose();
        chr.initBaseStats();
        return true;
    }

    @Handler(op = InHeader.USER_ABILITY_UP_REQUEST)
    public static void handleUserAbilityUpRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        if (inPacket.getUnreadAmount() < 6) {
            chr.dispose();
            return;
        }
        inPacket.decodeInt(); // tick
        // the reference server reads the stat as a short; the rest of an 8-byte stat flag may follow
        Stat charStat = Stat.getByVal(inPacket.getUnreadAmount() >= 8 ? inPacket.decodeInt() : inPacket.decodeShort());
        if (inPacket.getUnreadAmount() >= 4) {
            inPacket.decodeInt();
        }
        Map<Stat, Integer> allocation = new HashMap<>();
        allocation.put(charStat, 1);
        applyAbilityPoints(chr, allocation, false);
    }

    @Handler(op = InHeader.USER_ABILITY_MASS_UP_REQUEST)
    public static void handleUserAbilityMassUpRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        if (inPacket.getUnreadAmount() < 8) {
            chr.dispose();
            return;
        }
        inPacket.decodeInt(); // tick
        int count = inPacket.decodeInt();
        if (count < 1 || count > 6 || inPacket.getUnreadAmount() < count * 12) {
            chr.dispose();
            return;
        }

        // v232 sends count entries of (stat mask:int, reserved:int, AP amount:int). The normal auto-assign window
        // sends both primary-stat rows, including the row whose amount is zero.
        Map<Stat, Integer> allocations = new LinkedHashMap<>();
        for (int i = 0; i < count; i++) {
            Stat stat = Stat.getByVal(inPacket.decodeInt());
            inPacket.decodeInt(); // reserved
            int amount = inPacket.decodeInt();
            if (!isAbilityStat(stat) || amount < 0) {
                chr.dispose();
                return;
            }
            long combined = (long) allocations.getOrDefault(stat, 0) + amount;
            if (combined > Integer.MAX_VALUE) {
                chr.dispose();
                return;
            }
            allocations.put(stat, (int) combined);
        }
        // Auto Assign can ask for more than one selected stat can hold. Apply everything that fits and leave the
        // remainder as AP instead of rejecting the whole request or losing points.
        applyAbilityPoints(chr, allocations, true);
    }

    @Handler(op = InHeader.USER_CHANGE_STAT_REQUEST)
    public static void handleUserChangeStatRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        inPacket.decodeInt(); // tick
        // v271: no stat mask, always hp then mp
        HashMap<Stat, Short> hashMap = new HashMap();
        short hpInc = inPacket.decodeShort();
        short mpInc = inPacket.decodeShort();
        if (hpInc > 0) {
            hashMap.put(Stat.hp, hpInc);
        }
        if (mpInc > 0) {
            hashMap.put(Stat.mp, mpInc);
        }
        byte option = inPacket.decodeByte();
        // The client asks for its own resting recovery (every few seconds, more on chairs and with recovery
        // skills). Don't trust the amount: at most one request every 2 seconds, and at most a fifth of the max plus a
        // little per request, so an edited client can't heal itself fully on demand.
        long now = System.currentTimeMillis();
        Long last = lastRestHeal.get(chr.getId());
        if (last != null && now - last < REST_HEAL_MIN_INTERVAL_MS) {
            return;
        }
        lastRestHeal.put(chr.getId(), now);
        if (hashMap.containsKey(Stat.hp)) {
            int cap = chr.getMaxHP() / 5 + 1000;
            int amount = Math.max(0, hashMap.get(Stat.hp));
            if (amount > cap) {
                log.warn(String.format("%s asked to recover %d HP while resting (cap %d)", chr.getName(), amount, cap));
                amount = cap;
            }
            chr.heal(amount);
        }
        if (hashMap.containsKey(Stat.mp)) {
            int cap = chr.getMaxMP() / 5 + 1000;
            int amount = Math.max(0, hashMap.get(Stat.mp));
            if (amount > cap) {
                log.warn(String.format("%s asked to recover %d MP while resting (cap %d)", chr.getName(), amount, cap));
                amount = cap;
            }
            chr.healMP(amount);
        }
//        c.write(WvsContext.statChanged(newStats));
    }

    /**
     * Recovery from item options and set effects ("recover x HP every 4 seconds"): tick, hp, mp. The client sends it
     * from the same tick as the resting recovery, at most every 4 seconds; capped the same way.
     */
    @Handler(op = InHeader.USER_CHANGE_STAT_REQUEST_BY_ITEM_OPTION)
    public static void handleUserChangeStatRequestByItemOption(Char chr, InPacket inPacket) {
        if (chr == null || inPacket.getUnreadAmount() < 8 || chr.getHP() <= 0) {
            return;
        }
        inPacket.decodeInt(); // tick
        int hp = inPacket.decodeShort();
        int mp = inPacket.decodeShort();
        long now = System.currentTimeMillis();
        Long last = lastItemOptionHeal.get(chr.getId());
        if (last != null && now - last < ITEM_OPTION_HEAL_MIN_INTERVAL_MS) {
            return;
        }
        lastItemOptionHeal.put(chr.getId(), now);
        if (hp > 0) {
            chr.heal(Math.min(hp, chr.getMaxHP() / 20 + 500));
        }
        if (mp > 0) {
            chr.healMP(Math.min(mp, chr.getMaxMP() / 20 + 500));
        }
    }

    @Handler(op = InHeader.USER_REQUEST_INSTANCE_TABLE)
    public static void handleUserRequestInstanceTable(Char chr, InPacket inPacket) {
        String requestStr = inPacket.decodeString();
        int type = inPacket.decodeInt();
        int subType = inPacket.decodeInt();

        InstanceTableType itt = InstanceTableType.getByStr(requestStr);
        if (itt == null) {
            log.error(String.format("Unknown instance table type request %s, type %d, subType %d", requestStr, type, subType));
            return;
        }
        int value;
        switch (itt) {
            // HyperSkills: both have the same requestStr. level = type * 5
            case HyperActiveSkill:
            case HyperPassiveSkill:
            case HyperBeastTamer:
                if (subType == InstanceTableType.HyperActiveSkill.getSubType()) {
                    value = SkillConstants.getHyperActiveSkillSpByLv(type * 5);
                } else {
                    value = SkillConstants.getHyperPassiveSkillSpByLv(type * 5);
                }
                break;
            case HyperStatIncAmount:
                // type == level (including a reborn character's points from earlier lives at Lv. 140)
                value = net.swordie.ms.client.character.hyperstats.HyperStatsModule.getHyperStatSpForLevel(chr, type);
                break;
            case NeedHyperStatLv:
                // type == skill lv
                value = SkillConstants.getNeededSpForHyperStatSkill(type);
                break;
            case Skill_9200:
            case Skill_9201:
            case Skill_9202:
            case Skill_9203:
            case Skill_9204:
                // type == recommendSkillLevel - 1
                // subType == making skill level
                value = MakingSkillRecipe.getSuccessProb(Integer.parseInt(requestStr), type + 1, chr.getMakingSkillLevel(Integer.parseInt(requestStr)));
                break;
            default:
                log.error(String.format("Unhandled instance table type request %s, type %d, subType %d", itt, type, subType));
                return;
        }

        chr.write(WvsContext.resultInstanceTable(requestStr, type, subType, true, value));
    }


    @Handler(op = InHeader.USER_HYPER_SKILL_UP_REQUEST)
    public static void handleUserHyperSkillUpRequest(Char chr, InPacket inPacket) {
        // TODO: verify hyper skill is of the character's class
        inPacket.decodeInt(); // tick
        int skillID = inPacket.decodeInt();
        SkillInfo si = SkillData.getSkillInfoById(skillID);
        if (si == null) {
            log.error(String.format("Character %d attempted assigning hyper SP to a skill with null skillinfo (%d).", chr.getId(), skillID));
            chr.dispose();
            return;
        }
        if (si.getHyper() == 0 && si.getHyperStat() == 0 ||
                (!SkillConstants.isMatching(si.getRootId(), chr.getJob()) && !SkillConstants.isHyperstatSkill(skillID))) {
            log.error(String.format("Character %d attempted assigning hyper SP to a wrong skill (skill id %d, player job %d)", chr.getId(), skillID, chr.getJob()));
            chr.dispose();
            return;
        }
        Skill skill = chr.getSkill(skillID, true);
        short level = chr.getLevel();
        if (si.getHyper() != 0) { // Passive/Active hyper
            boolean active = si.getHyper() == 2;
            int totalHyperSp;
            int spentSp;
            if (active) {
                totalHyperSp = SkillConstants.getTotalActiveHyperSpByLevel(level);
                spentSp = chr.getSpentActiveHyperSkillSp();
            } else {
                totalHyperSp = SkillConstants.getTotalPassiveHyperSpByLevel(level);
                spentSp = chr.getSpentPassiveHyperSkillSp();
            }
            int availableSp = totalHyperSp - spentSp;
            if (availableSp <= 0 || skill.getCurrentLevel() != 0) {
                log.error(String.format("Character %d attempted assigning hyper skill SP without having it, or too much. Available SP %d, current %d (%d, job %d)",
                        chr.getId(), availableSp, skill.getCurrentLevel(), skillID, chr.getJob()));
                chr.dispose();
                return;
            }
        } else if (si.getHyperStat() != 0) {
            int totalHyperSp = net.swordie.ms.client.character.hyperstats.HyperStatsModule.getTotalHyperStatSp(chr);
            int spentSp = chr.getSpentHyperSp();
            int availableSp = totalHyperSp - spentSp;
            int neededSp = SkillConstants.getNeededSpForHyperStatSkill(skill.getCurrentLevel() + 1);
            if (skill.getCurrentLevel() >= skill.getMaxLevel() || availableSp < neededSp) {
                log.error(String.format("Character %d attempted assigning too many hyper stat levels. Available SP %d, needed %d, current %d (%d, job %d)",
                        chr.getId(), availableSp, neededSp, skill.getCurrentLevel(), skillID, chr.getJob()));
                chr.dispose();
                return;
            }
        } else { // not hyper stat and not hyper skill
            log.error(String.format("Character %d attempted assigning hyper stat to an improper skill. (%d, job %d)", chr.getId(), skillID, chr.getJob()));
            chr.dispose();
            return;
        }
        chr.removeFromBaseStatCache(skill);
        skill.setCurrentLevel(skill.getCurrentLevel() + 1);
        chr.addToBaseStatCache(skill);
        chr.addSkill(skill);
        chr.write(WvsContext.changeSkillRecordResult(skill));
    }

    @Handler(op = InHeader.USER_HYPER_SKILL_RESET_REQUEST)
    public static void handleUserHyperSkillResetRequest(Char chr, InPacket inPacket) {
        if (chr.getMoney() < 100_000) { // Min reset cost 100,000 meso
            chr.chatMessage("You don't have enough mesos.");
            chr.dispose();
            return;
        }
        if (chr.getSpentActiveHyperSkillSp() == 0 && chr.getSpentPassiveHyperSkillSp() == 0) {
            chr.chatMessage("Your Hyper Skills have already been reset.");
            chr.dispose();
            return;
        }
        chr.deductMoney(100_000, ProgressionLog.Sink.FEES);
        chr.resetHyperSkills();
    }


    @Handler(op = InHeader.USER_HYPER_STAT_SKILL_UP_REQUEST)
    public static void handleUserHyperStatSkillUpRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        var presetIndex = inPacket.decodeInt();
        var skillId = inPacket.decodeInt();
        // v265 adds the level asked for: one more for "+", several for Max
        if (inPacket.getUnreadAmount() >= 4) {
            HyperStatsModule.increaseHyperStatSkill(chr, presetIndex, skillId, inPacket.decodeInt());
        } else {
            HyperStatsModule.increaseHyperStatSkill(chr, presetIndex, skillId);
        }
    }

    @Handler(op = InHeader.USER_HYPER_STAT_SKILL_RESET_REQUEST)
    public static void handleUserHyperStatSkillResetRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        var presetIndex = inPacket.decodeInt();
        if (chr.getMoney() < GameConstants.HYPER_STAT_RESET_COST) {
            chr.chatMessage("Not enough money for this operation.");
            chr.write(WvsContext.receiveHyperStatSkillResetResult(chr.getId(), true, false));
            return;
        }

        chr.deductMoney(GameConstants.HYPER_STAT_RESET_COST, ProgressionLog.Sink.FEES);
        HyperStatsModule.resetPreset(chr, presetIndex);
        chr.write(WvsContext.receiveHyperStatSkillResetResult(chr.getId(), true, true));
    }

    @Handler(op = InHeader.USER_HYPER_SKILL_PRESET_REQUEST)
    public static void handleUserHyperskillPresetRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        var presetIndex = inPacket.decodeInt();
        if (chr.getMoney() < GameConstants.HYPER_STAT_PRESET_SWITCH_COST) {
            chr.chatMessage("Not enough money for this operation.");
            return;
        }

        chr.deductMoney(GameConstants.HYPER_STAT_PRESET_SWITCH_COST, ProgressionLog.Sink.FEES);
        HyperStatsModule.switchPreset(chr, presetIndex);
    }

    @Handler(op = InHeader.USER_REVIVE_REQUEST)
    public static void handleUserReviveRequest(Char chr, InPacket inPacket) {
/*        if (chr.getHP() <= 0) {
*//*            chr.write(UserLocal.openUIOnDead(true, chr.getBuffProtectorItem() != null,
                    false, false, false,
                    ReviveType.NORMAL, 0));*//*
            chr.runCustomReviveScript();
        }*/
    }

    @Handler(op = InHeader.USER_DOT_HEAL)
    public static void handleUserDotHeal(Char chr, InPacket inPacket) {
        var tsm = chr.getTemporaryStatManager();
        int count = inPacket.decodeInt();
        count = Math.min(count, inPacket.getUnreadAmount() / 8);
        for (int i = 0; i < count; i++) {
            int stat = inPacket.decodeInt();
            int amount = inPacket.decodeInt();
            if (stat == Stat.hp.getVal()) {
                if (tsm.hasStat(CharacterTemporaryStat.DotHealHPPerSecond)) {
                    amount = (int) (tsm.getTotalNOptionOfStat(CharacterTemporaryStat.DotHealHPPerSecond)
                            * chr.getMaxHP() / 100.0D);
                }
                chr.heal(amount);
            } else if (stat == Stat.mp.getVal()) {
                if (tsm.hasStat(CharacterTemporaryStat.DotHealMPPerSecond)) {
                    amount = (int) (tsm.getTotalNOptionOfStat(CharacterTemporaryStat.DotHealMPPerSecond)
                            * chr.getMaxMP() / 100.0D);
                }
                chr.healMP(amount);
            }
        }
    }

    @Handler(op = InHeader.USER_IN_BOSS_REVIVE_REQUEST)
    public static void handleUserInBossReviveRequest(Char chr, InPacket inPacket) {
        // byte unk = inPacket.decodeByte();
        reviveInBoss(chr);
    }

    /** The boss revive: in place while a death count or fight gauge allows it, otherwise out of the fight. */
    public static void reviveInBoss(Char chr) {
        // buff freezer logic
        var buffProtector = chr.getBuffProtectorItem();
        if (buffProtector != null) {
            chr.setBuffProtector(true);
            chr.consumeItem(buffProtector);
            if (chr.getTemporaryStatManager().hasStat(CapDebuff)) {
                chr.getTemporaryStatManager().removeStat(CapDebuff);
            }
            chr.write(UserLocal.setBuffProtector(buffProtector.getItemId(), true));
        } else {
            chr.getTemporaryStatManager().removeAllStats();
            chr.initSoulMP(); // a Soul Weapon's gauge starts over at 0 (removeAllStats took it away for good)
        }

        int deathcount = chr.getDeathCount();
        if (deathcount > 0) {
            deathcount--;
            chr.setDeathCount(deathcount);
            chr.write(UserLocal.deathCountInfo(deathcount));
            chr.write(FieldPacket.teleport(chr.getPosition(), chr));
        } else if (chr.reviveByFightGauge()) {
            chr.write(FieldPacket.teleport(chr.getPosition(), chr)); // the gauge took the death: back up in place
        } else {
            var inst = chr.getInstance();
            if (inst != null) {
                // out of the fight to the death map's forced return / return map, as GMS (not the enter map's)
                chr.getScriptManager().stopEvents();
                inst.removeChar(chr, inst.exitFor(chr.getField().getInfo()), 0, true, true);
            } else if (chr.getTransferField() != 0) {
                Field toField = chr.getOrCreateFieldByCurrentInstanceType(chr.getTransferField());
                if (toField != null && chr.getTransferField() > 0) {
                    chr.warp(toField);
                }
                chr.setTransferField(0);
                return;
            } else {
                // forcedReturn, else returnMap (999999999 means none)
                var info = chr.getField().getInfo();
                int back = info.getForcedReturn() > 0 && info.getForcedReturn() != GameConstants.NO_MAP_ID
                        ? info.getForcedReturn() : info.getReturnMap();
                Field backField = back > 0 && back != GameConstants.NO_MAP_ID
                        ? chr.getOrCreateFieldByCurrentInstanceType(back) : null;
                chr.warp(backField, false); // null: Henesys
            }
        }
        chr.heal(chr.getMaxHP(), false, true);
        chr.setBuffProtector(false);
        chr.setNextAvailableConsumeItemTime(0);
        if (chr.getJobHandler() != null) {
            chr.getJobHandler().handleRevive();
        }
    }
}
