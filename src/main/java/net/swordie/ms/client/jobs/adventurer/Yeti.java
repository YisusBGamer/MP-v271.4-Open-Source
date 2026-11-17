package net.swordie.ms.client.jobs.adventurer;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.MobAttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.MoveAbility;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.life.AffectedArea;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.Summon;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.MobTemporaryStat;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Rect;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Supplier;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Yeti (13500, beginner skills 13001). No SP: skills unlock by level (Skill.wz reqLev), the hidden halves of the
 * attacks and passives scale with the character's level. Rage (YetiFuryGauge tail x) fills from Snack Dash kills,
 * boss hits, Yeti Smarts hits, Pepes and Snowman kills; Yeti Rage spends it for the Enraged state (YetiFuryMode),
 * in which the client swaps Snack Dash for Yeti Smash and the jetpack for the super one.
 */
public class Yeti extends Job {

    public static final int SNACK_DASH = 135001000;
    public static final int SNACK_DASH_2 = 135001001;
    public static final int SNACK_DASH_3 = 135001002;
    public static final int SNACK_DASH_DMG = 135002000;
    public static final int YETI_RAGE = 135001005;
    public static final int YETI_SMASH = 135001006;
    public static final int YETI_SMASH_DMG = 135002006;
    public static final int YETI_SMASH_2 = 135001014;
    public static final int BOOK_TOSS = 135001008;
    public static final int BOOK_TOSS_DMG = 135002008;
    public static final int BOMB_DISPOSAL = 135001011;
    public static final int BOMB_DISPOSAL_DMG = 135002011;
    public static final int SCIENTIFIC_RESEARCH = 135001016;
    public static final int SCIENTIFIC_RESEARCH_DMG = 135002016;
    public static final int VIOLIN_SOLO = 135001019;
    public static final int VIOLIN_SOLO_DMG = 135002019;
    public static final int HEY_PEPE = 135001015;
    public static final int PEPE_ATTACK = 135002015;
    public static final int YETI_MASTER_CHEF = 135001009;
    public static final int HERO_OF_EL_NATH = 135001013;
    public static final int HERO_OF_EL_NATH_STAT = 135002013;
    public static final int HOMEMADE_SNOWMAN = 135001012;
    public static final int HOMEMADE_SNOWMAN_DMG = 135002012;
    public static final int SPICE_OVERLOAD = 135001017;
    public static final int FURIOUS_ROAR = 135001020;
    public static final int FURIOUS_ROAR_DMG = 135002020;
    public static final int SPIRIT_OF_EL_NATH = 135002023;
    public static final int HOMEMADE_JETPACK = 135001007;
    public static final int HOMEMADE_SUPER_JETPACK = 135001010;
    public static final int HOMEMADE_JETPACK_2 = 135003007;

    /** Skill and the level it unlocks at (yeti-skills.tsv unlock rows); all fixed at level 1. */
    private static final Supplier<int[][]> LEVEL_SKILLS = skills("unlock");
    /** The hidden halves (damage x = level, Yeti Power's passives) and the level their skill unlocks at. */
    private static final Supplier<int[][]> SCALING_SKILLS = skills("scaling");

    private static Supplier<int[][]> skills(String kind) {
        return DataTable.cached("yeti-skills", t -> t.rows().stream()
                .filter(r -> r.str("kind").trim().equals(kind))
                .map(r -> new int[]{r.i("skill"), r.i("level")}).toArray(int[][]::new));
    }
    /** Buffs whose time doesn't run down while Enraged. */
    private static final int[] RAGE_PAUSED = {HEY_PEPE, YETI_MASTER_CHEF, HERO_OF_EL_NATH};

    private static final Set<Integer> SNACK_DASHES = Set.of(SNACK_DASH, SNACK_DASH_2, SNACK_DASH_3, SNACK_DASH_DMG);
    private static final Set<Integer> SMARTS = Set.of(BOOK_TOSS, BOOK_TOSS_DMG, BOMB_DISPOSAL, BOMB_DISPOSAL_DMG,
            SCIENTIFIC_RESEARCH, SCIENTIFIC_RESEARCH_DMG, VIOLIN_SOLO, VIOLIN_SOLO_DMG);

    private int rage;
    private long lastSmartsCast;
    private int lastSmartsSkill;
    private long lastViolin;
    private final Set<Integer> violinHit = new HashSet<>();
    private long pepeWindow;
    private int pepesInWindow;

    public Yeti(Char chr) {
        super(chr);
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isYeti(id);
    }

    /** Gives the level-unlocked skills and keeps the hidden halves at the character's level (max 300). */
    public static void giveLevelSkills(Char chr) {
        if (chr.getJob() != JobConstants.JobEnum.YETI.getJobId()) {
            return;
        }
        int level = chr.getLevel();
        for (int[] s : LEVEL_SKILLS.get()) {
            if (level >= s[1] && chr.getSkillLevel(s[0]) <= 0) {
                chr.addSkill(s[0], 1, 1);
            }
        }
        int slv = Math.min(300, level);
        for (int[] s : SCALING_SKILLS.get()) {
            if (level >= s[1] && chr.getSkillLevel(s[0]) != slv) {
                chr.addSkill(s[0], slv, 300);
            }
        }
    }

    // Rage -----------------------------------------------------------------------------------------------------------

    private int getMaxRage() {
        SkillInfo si = SkillData.getSkillInfoById(YETI_RAGE);
        int cost = si == null ? 0 : si.getValue(y, 1);
        return cost > 0 ? cost : 300;
    }

    public int getRage() {
        return rage;
    }

    public void addRage(int amount) {
        if (amount == 0) {
            return;
        }
        setRage(rage + amount);
    }

    private void setRage(int value) {
        int next = Math.max(0, Math.min(getMaxRage(), value));
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (next == rage && tsm.hasStat(YetiFuryGauge)) {
            return;
        }
        rage = next;
        Option o = new Option(1, YETI_RAGE, 0);
        o.xOption = rage;
        tsm.putCharacterStatValue(YetiFuryGauge, o, true);
        tsm.sendSetStatPacket();
    }

    private boolean isEnraged() {
        return chr.getTemporaryStatManager().getOptByCTSAndSkill(YetiFuryMode, YETI_RAGE) != null;
    }

    // Attacks --------------------------------------------------------------------------------------------------------

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        int skillId = attackInfo.skillId;
        int gain = 0;
        boolean bossHit = false;
        Set<Mob> mobs = new LinkedHashSet<>();
        for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
            Life life = chr.getField().getLifeByObjectID(mai.mobId);
            if (life instanceof Mob mob) {
                mobs.add(mob);
                bossHit |= mob.isBoss();
            }
        }
        if (SNACK_DASHES.contains(skillId) && bossHit) {
            gain += value(YETI_RAGE, u2);
        }
        if (SMARTS.contains(skillId) && !mobs.isEmpty()) {
            int per = value(baseSmarts(skillId), x);
            if (skillId == VIOLIN_SOLO || skillId == VIOLIN_SOLO_DMG) {
                // one rage per enemy for the whole solo
                long now = System.currentTimeMillis();
                if (now - lastViolin > 3000) {
                    violinHit.clear();
                }
                lastViolin = now;
                for (Mob mob : mobs) {
                    if (violinHit.add(mob.getObjectId())) {
                        gain += per;
                    }
                }
            } else {
                gain += per * mobs.size();
            }
            gain += spiceBonus(baseSmarts(skillId));
        }
        if (skillId == PEPE_ATTACK) {
            gain += pepeRage();
        }
        if ((skillId == FURIOUS_ROAR || skillId == FURIOUS_ROAR_DMG) && isEnraged()) {
            useUpFuriousRoar();
        }
        if ((skillId == HOMEMADE_SNOWMAN || skillId == HOMEMADE_SNOWMAN_DMG) && !mobs.isEmpty()) {
            snowmanDebuff(mobs);
        }
        addRage(gain);
        super.handleAttack(c, attackInfo);
    }

    private int baseSmarts(int skillId) {
        return switch (skillId) {
            case BOOK_TOSS_DMG -> BOOK_TOSS;
            case BOMB_DISPOSAL_DMG -> BOMB_DISPOSAL;
            case SCIENTIFIC_RESEARCH_DMG -> SCIENTIFIC_RESEARCH;
            case VIOLIN_SOLO_DMG -> VIOLIN_SOLO;
            default -> skillId;
        };
    }

    private int value(int skillId, net.swordie.ms.client.character.skills.SkillStat stat) {
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        return si == null ? 0 : si.getValue(stat, 1);
    }

    /** Spice Overload: y more rage per Yeti Smarts cast (one cast may come as several attack packets). */
    private int spiceBonus(int smartsSkill) {
        if (chr.getTemporaryStatManager().getOptByCTSAndSkill(YetiMushroom, SPICE_OVERLOAD) == null) {
            return 0;
        }
        long now = System.currentTimeMillis();
        if (smartsSkill == lastSmartsSkill && now - lastSmartsCast < 1000) {
            return 0;
        }
        lastSmartsSkill = smartsSkill;
        lastSmartsCast = now;
        return value(SPICE_OVERLOAD, y);
    }

    /** x rage per Pepe; at most the 4 Pepes of one summon per y seconds. */
    private int pepeRage() {
        long now = System.currentTimeMillis();
        int lockMs = Math.max(1, value(HEY_PEPE, y)) * 1000;
        if (now - pepeWindow > lockMs - 500) {
            pepeWindow = now;
            pepesInWindow = 0;
        }
        int maxPepes = Math.max(1, value(PEPE_ATTACK, bulletCount));
        if (pepesInWindow >= maxPepes) {
            return 0;
        }
        pepesInWindow++;
        return value(HEY_PEPE, x);
    }

    private void useUpFuriousRoar() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option mode = tsm.getOptByCTSAndSkill(YetiFuryMode, YETI_RAGE);
        if (mode == null || mode.xOption < 1) {
            return;
        }
        int left = (int) mode.getRemainingTime();
        if (left <= 0) {
            return;
        }
        Option o = new Option(1, YETI_RAGE, left);
        o.setInMillis(true);
        o.xOption = 0; // usable once per Enraged state
        tsm.putCharacterStatValue(YetiFuryMode, o, true);
        tsm.sendSetStatPacket();
    }

    /** Homemade Snowman's hits: Speed z, damage taken +y, for the snowman's time. */
    private void snowmanDebuff(Set<Mob> mobs) {
        SkillInfo si = SkillData.getSkillInfoById(HOMEMADE_SNOWMAN);
        if (si == null) {
            return;
        }
        int dur = Math.max(1, si.getValue(time, 1));
        for (Mob mob : mobs) {
            if (mob.getHp() <= 0) {
                continue;
            }
            MobTemporaryStat mts = mob.getTemporaryStat();
            mts.addStatOptionsAndBroadcast(chr, MobStat.Speed, new Option(si.getValue(z, 1), HOMEMADE_SNOWMAN, dur));
            mts.addStatOptionsAndBroadcast(chr, MobStat.AddDamParty, new Option(si.getValue(y, 1), HOMEMADE_SNOWMAN, dur));
        }
    }

    @Override
    public void handleMobDeath(AttackInfo attackInfo, Mob mob) {
        int gain = 0;
        if (attackInfo != null && SNACK_DASHES.contains(attackInfo.skillId)) {
            gain += value(YETI_RAGE, x);
        }
        if (mob != null && mob.getTemporaryStat().hasCurrentMobStatBySkillId(HOMEMADE_SNOWMAN)) {
            gain += value(HOMEMADE_SNOWMAN, x);
        }
        addRage(gain);
        super.handleMobDeath(attackInfo, mob);
    }

    // Skills ---------------------------------------------------------------------------------------------------------

    @Override
    public void handleSkill(Char caster, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket,
                            SkillUseInfo skillUseInfo) {
        super.handleSkill(caster, tsm, skillId, slv, inPacket, skillUseInfo);
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        if (si == null) {
            return;
        }
        slv = Math.max(1, slv);
        int t = si.getValue(time, slv);
        switch (skillId) {
            case YETI_RAGE -> {
                int cost = si.getValue(y, slv);
                if (rage < cost) {
                    return; // the client checks the gauge itself; a stale gauge gets nothing
                }
                setRage(rage - cost);
                if (tsm.getOptByCTSAndSkill(YetiMushroom, SPICE_OVERLOAD) != null) {
                    tsm.removeStatsBySkill(SPICE_OVERLOAD); // Spice Overload ends on Yeti Rage
                    tsm.sendResetStatPacket();
                }
                Option mode = new Option(1, skillId, t);
                mode.xOption = chr.getSkillLevel(FURIOUS_ROAR) > 0 ? 1 : 0;
                tsm.putCharacterStatValue(YetiFuryMode, mode, true);
                tsm.putCharacterStatValue(IndiePADR, new Option(si.getValue(indiePadR, slv), skillId, t), true);
                tsm.putCharacterStatValue(IndieSpeed, new Option(si.getValue(indieSpeed, slv), skillId, t), true);
                pauseBuffs(tsm, t * 1000);
                tsm.sendSetStatPacket();
            }
            case HEY_PEPE -> {
                tsm.putCharacterStatValue(YetiMyFriendPepe, new Option(1, skillId, t), true);
                tsm.putCharacterStatValue(IndieEXP, new Option(si.getValue(indieExp, slv), skillId, t), true);
                tsm.sendSetStatPacket();
            }
            case YETI_MASTER_CHEF -> {
                for (Char member : partyInRange(si)) {
                    TemporaryStatManager mt = member.getTemporaryStatManager();
                    mt.putCharacterStatValue(IndiePADR, new Option(si.getValue(indiePadR, slv), skillId, t), true);
                    mt.putCharacterStatValue(IndieEXP, new Option(si.getValue(indieExp, slv), skillId, t), true);
                    // the client greys the Master Chef icon (the halved-EXP state) while this x is 0
                    Option cook = new Option(1, skillId, t);
                    cook.xOption = 1;
                    mt.putCharacterStatValue(YetiCook, cook, true);
                    mt.sendSetStatPacket();
                }
            }
            case HERO_OF_EL_NATH -> {
                // all stats +1% per y levels; its own stat, so it stacks with Maple Warrior
                int pct = chr.getLevel() / Math.max(1, si.getValue(y, slv));
                for (Char member : partyInRange(si)) {
                    TemporaryStatManager mt = member.getTemporaryStatManager();
                    mt.putCharacterStatValue(IndieStatR, new Option(pct, skillId, t), true);
                    mt.sendSetStatPacket();
                }
            }
            case SPICE_OVERLOAD -> {
                tsm.putCharacterStatValue(YetiMushroom, new Option(1, skillId, t), true);
                tsm.sendSetStatPacket();
            }
            case HOMEMADE_SNOWMAN -> {
                AffectedArea aa = AffectedArea.getPassiveAA(chr, skillId, slv);
                aa.setFlip(!chr.isLeft());
                aa.setDuration(t * 1000);
                if (si.getFirstRect() != null) {
                    Rect r = chr.getPosition().getRectAround(si.getFirstRect());
                    aa.setRect(chr.isLeft() ? r : r.horizontalFlipAround(chr.getPosition().getX()));
                }
                chr.getField().spawnAffectedAreaAndRemoveOld(aa);
            }
            case SPIRIT_OF_EL_NATH -> {
                Summon guardian = Summon.getSummonBy(chr, skillId, slv);
                guardian.setFlyMob(false);
                guardian.setMoveAbility(MoveAbility.Stop);
                chr.getField().spawnSummonAndRemoveOld(guardian);
            }
            default -> {
            }
        }
    }

    /** The caster and the party members on the map inside the skill's range. */
    private Set<Char> partyInRange(SkillInfo si) {
        Set<Char> res = new LinkedHashSet<>();
        res.add(chr);
        if (chr.getParty() == null) {
            return res;
        }
        Rect range = si.getFirstRect() == null ? null : chr.getRectAround(si.getFirstRect());
        if (range != null && !chr.isLeft()) {
            range = range.horizontalFlipAround(chr.getPosition().getX());
        }
        for (Char member : chr.getParty().getPartyMembersInSameField(chr)) {
            if (member != null && member.getHP() > 0 && (range == null || range.hasPositionInside(member.getPosition()))) {
                res.add(member);
            }
        }
        return res;
    }

    /** While Enraged, Hey Pepe!, Yeti Master Chef and Hero of El Nath don't run down: they get the rage time back. */
    private void pauseBuffs(TemporaryStatManager tsm, int extraMs) {
        CharacterTemporaryStat[][] stats = {
                {YetiMyFriendPepe, IndieEXP}, {IndiePADR, IndieEXP, YetiCook}, {IndieStatR}
        };
        for (int i = 0; i < RAGE_PAUSED.length; i++) {
            int skill = RAGE_PAUSED[i];
            for (CharacterTemporaryStat cts : stats[i]) {
                Option old = tsm.getOptByCTSAndSkill(cts, skill);
                if (old == null || old.tOption == 0) {
                    continue;
                }
                long left = old.getRemainingTime();
                if (left <= 0) {
                    continue;
                }
                Option o = new Option(old.nOption, skill, (int) left + extraMs);
                o.setInMillis(true);
                o.xOption = old.xOption;
                tsm.putCharacterStatValue(cts, o, true);
            }
        }
    }

    // Level up -------------------------------------------------------------------------------------------------------

    @Override
    public void handleLevelUp() {
        super.handleLevelUp();
        // Yeti All Soft Now!: Yeti gets no SP
        if (chr.getAvatarData().getCharacterStat().getSp() != 0) {
            chr.setStatAndSendPacket(Stat.sp, 0);
        }
    }
}
