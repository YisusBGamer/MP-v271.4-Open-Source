package net.swordie.ms.client.jobs;

import net.swordie.ms.client.jobs.adventurer.pirate.Buccaneer;
import net.swordie.ms.client.jobs.resistance.demon.DemonAvenger;
import net.swordie.ms.client.jobs.adventurer.archer.BowMaster;
import net.swordie.ms.client.jobs.adventurer.archer.Marksman;
import net.swordie.ms.client.jobs.adventurer.archer.Pathfinder;
import net.swordie.ms.client.jobs.adventurer.magician.Bishop;
import net.swordie.ms.client.jobs.adventurer.magician.FirePoison;
import net.swordie.ms.client.jobs.adventurer.magician.IceLightning;
import net.swordie.ms.client.jobs.adventurer.pirate.Cannoneer;
import net.swordie.ms.client.jobs.adventurer.pirate.Corsair;
import net.swordie.ms.client.jobs.adventurer.thief.BladeMaster;
import net.swordie.ms.client.jobs.adventurer.thief.NightLord;
import net.swordie.ms.client.jobs.adventurer.thief.Shadower;
import net.swordie.ms.client.jobs.adventurer.warrior.DarkKnight;
import net.swordie.ms.client.jobs.adventurer.warrior.Hero;
import net.swordie.ms.client.jobs.adventurer.warrior.Paladin;
import net.swordie.ms.client.jobs.cygnus.BlazeWizard;
import net.swordie.ms.client.jobs.cygnus.DawnWarrior;
import net.swordie.ms.client.jobs.cygnus.Mihile;
import net.swordie.ms.client.jobs.cygnus.NightWalker;
import net.swordie.ms.client.jobs.cygnus.ThunderBreaker;
import net.swordie.ms.client.jobs.cygnus.WindArcher;
import net.swordie.ms.client.jobs.flora.Adele;
import net.swordie.ms.client.jobs.flora.Ark;
import net.swordie.ms.client.jobs.flora.Illium;
import net.swordie.ms.client.jobs.flora.Khali;
import net.swordie.ms.client.jobs.jianghu.Lynn;
import net.swordie.ms.client.jobs.jianghu.MoXuan;
import net.swordie.ms.client.jobs.legend.Aran;
import net.swordie.ms.client.jobs.legend.Evan;
import net.swordie.ms.client.jobs.legend.Luminous;
import net.swordie.ms.client.jobs.legend.Mercedes;
import net.swordie.ms.client.jobs.legend.Phantom;
import net.swordie.ms.client.jobs.legend.Shade;
import net.swordie.ms.client.jobs.sengoku.Hayato;
import net.swordie.ms.client.jobs.sengoku.Kanna;
import net.swordie.ms.client.jobs.shine.ErelLight;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.SkillStat;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.loaders.SkillData;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Buffs that are only indie stats in Skill.wz: each indie value of the skill becomes its Indie stat for the skill's
 * time. Mass spells (massSpell) also go to the party members in the same map.
 */
public final class SkillBuffs {
    public static final int SHINE_TREE_OF_STARS = 500001143;
    public static final int SHINE_TREE_OF_STARS_2 = 500001146;

    private static final Map<SkillStat, CharacterTemporaryStat> INDIE = Map.ofEntries(
            Map.entry(indiePad, IndiePAD),
            Map.entry(indieMad, IndieMAD),
            Map.entry(indieDamR, IndieDamR),
            Map.entry(indiePMdR, IndiePMdR),
            Map.entry(indieBDR, IndieBDR),
            Map.entry(indieIgnoreMobpdpR, IndieIgnoreMobpdpR),
            Map.entry(indieCr, IndieCr),
            Map.entry(indieCD, IndieCrDmg),
            Map.entry(indieBooster, IndieBooster),
            Map.entry(indieStance, IndieStance),
            Map.entry(indieAsrR, IndieAsrR),
            Map.entry(indieAllStat, IndieAllStat),
            Map.entry(indieMhpR, IndieMHPR)
    );

    private static final Logger log = LogManager.getLogger(SkillBuffs.class);

    private SkillBuffs() {
    }

    /** The race of party members a race buff (Epic Adventure and the like) also goes to, or null if the skill isn't one. */
    private static java.util.function.IntPredicate raceOf(int skillId) {
        return switch (skillId) {
            case Hero.EPIC_ADVENTURE_HERO, Paladin.EPIC_ADVENTURE_PALA, DarkKnight.EPIC_ADVENTURE_DRK, FirePoison.EPIC_ADVENTURE_FP, IceLightning.EPIC_ADVENTURE_IL, Bishop.EPIC_ADVENTURE_BISH, BowMaster.EPIC_ADVENTURE_BOW, Marksman.EPIC_ADVENTURE_XBOW, Pathfinder.EPIC_ADVENTURE_ABOW, NightLord.EPIC_ADVENTURE_NL, Shadower.EPIC_ADVENTURE_SHAD,
                 BladeMaster.EPIC_ADVENTURE_DB, Buccaneer.EPIC_ADVENTURER_BUCC, Corsair.EPIC_ADVENTURER_SAIR, Cannoneer.EPIC_ADVENTURER_CANNON -> net.swordie.ms.constants.JobConstants::isExplorer; // Epic Adventure
            case DawnWarrior.GLORY_OF_THE_GUARDIANS_DW, BlazeWizard.GLORY_OF_THE_GUARDIANS_BW, WindArcher.GLORY_OF_THE_GUARDIANS_WA, NightWalker.GLORY_OF_THE_GUARDIANS_NW, ThunderBreaker.GLORY_OF_THE_GUARDIANS_TB, Mihile.QUEEN_OF_TOMORROW -> // Glory of the Guardians, Queen of Tomorrow
                    job -> net.swordie.ms.constants.JobConstants.isCygnusKnight(job)
                            || net.swordie.ms.constants.JobConstants.isMihile(job);
            case Aran.HEROIC_MEMORIES_ARAN, Evan.HEROIC_MEMORIES_EVAN, Mercedes.HEROIC_MEMORIES_MERC, Phantom.HEROIC_MEMORIES_PH, Shade.HEROIC_MEMORIES_SH, Luminous.HEROIC_MEMORIES_LUMI -> // Heroic Memories
                    net.swordie.ms.constants.JobConstants::isLegends;
            case Adele.RACE_OF_GOD, Illium.DIVINE_WRATH, Khali.DIVINE_WRATH, Ark.DIVINE_WRATH -> net.swordie.ms.constants.JobConstants::isFlora; // Divine Wrath
            case Lynn.AURA_OF_DESTINY, MoXuan.AURA_OF_DESTINY -> net.swordie.ms.constants.JobConstants::isJianghu; // Aura of Destiny
            case Hayato.AKATSUKI_BLESSING, Kanna.AKATSUKI_BLESSING -> net.swordie.ms.constants.JobConstants::isSengoku; // Princess's Vow
            default -> null;
        };
    }

    /**
     * Race buffs: "applies to any Explorer (Cygnus, hero...) class in the party". The caster's own buff is the job
     * handler's; this gives the same Damage % to the party members of that race in the map.
     */
    public static void giveRaceBuffToParty(Char chr, int skillId, int slv) {
        var race = raceOf(skillId);
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        if (race == null || si == null || chr.getParty() == null || slv <= 0) {
            return;
        }
        int dam = si.getValue(indieDamR, slv);
        int duration = si.getValue(time, slv);
        if (dam <= 0 || duration <= 0) {
            return;
        }
        for (Char member : chr.getParty().getPartyMembersInSameField(chr)) {
            if (member == chr || !race.test(member.getJob())) {
                continue;
            }
            TemporaryStatManager tsm = member.getTemporaryStatManager();
            tsm.putCharacterStatValue(IndieDamR, new Option(dam, skillId, duration));
            tsm.sendSetStatPacket();
        }
    }

    /**
     * For a cast that no handler gave anything for: if the skill's data is a timed buff of indie stats, give those.
     * Logged, so the skills still without their own handling can be found.
     */
    // v271 invincibility skills without their own handling: time in sec, Cannon-Proof's in ms
    private static final Map<Integer, Boolean> INVINCIBLE_MS = Map.of(1321004, false, 3111020, false, 5311016, true,
            DemonAvenger.SHIELD_OF_BLOOD, false, ErelLight.MAPLE_WARRIOR, false); // Shield of Chulainn (Erel Light): 10 s, as GMS

    public static void giveUnhandledIndieBuff(Char chr, int skillId, int slv) {
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        if (si == null || slv <= 0 || si.isPsd() || si.getValue(time, slv) <= 0) {
            return;
        }
        Boolean ms = INVINCIBLE_MS.get(skillId);
        if (ms != null) {
            Option o = new Option(1, skillId, si.getValue(time, slv));
            o.setInMillis(ms);
            chr.getTemporaryStatManager().putCharacterStatValue(IndieNotDamaged, o, true);
            chr.getTemporaryStatManager().sendSetStatPacket();
            return;
        }
        boolean any = false;
        for (SkillStat stat : INDIE.keySet()) {
            if (si.getValue(stat, slv) != 0) {
                any = true;
                break;
            }
        }
        if (!any) {
            // a timed skill that gave nothing and has no indie stats to fall back on: worth a look
            if (si.getInfoType() == 10) {
                log.info("[Skills] {} cast skill {} with no handling and no indie stats: nothing given", chr.getName(), skillId);
            }
            return;
        }
        log.info("[Skills] {} cast skill {} with no handling: gave its indie stats from the data", chr.getName(), skillId);
        if (skillId >= SHINE_TREE_OF_STARS && skillId <= SHINE_TREE_OF_STARS_2) {
            // SHINE Tree of Stars: GMS sends its own stat with the indie stats
            chr.getTemporaryStatManager().putCharacterStatValue(ShineHexaTreeOfStarlight,
                    new Option(1, skillId, si.getValue(time, slv)));
        }
        giveIndieBuff(chr, skillId, slv);
    }

    /** Gives the skill's indie stats (plus extra damage % from damR when asked) for time + extraTime seconds. */
    public static void giveIndieBuff(Char chr, int skillId, int slv, boolean damRAsIndie, int extraTime, int extraPmdr) {
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        if (si == null || slv <= 0) {
            return;
        }
        int duration = si.getValue(time, slv) + extraTime;
        if (duration <= 0) {
            return;
        }
        Set<Char> targets = new LinkedHashSet<>();
        targets.add(chr);
        if (si.isMassSpell() && chr.getParty() != null) {
            targets.addAll(chr.getParty().getPartyMembersInSameField(chr));
        }
        for (Char target : targets) {
            TemporaryStatManager tsm = target.getTemporaryStatManager();
            for (var e : INDIE.entrySet()) {
                int value = si.getValue(e.getKey(), slv);
                if (e.getKey() == indiePMdR) {
                    value += extraPmdr;
                }
                if (value != 0) {
                    tsm.putCharacterStatValue(e.getValue(), new Option(value, skillId, duration));
                }
            }
            if (damRAsIndie && si.getValue(damR, slv) != 0) {
                tsm.putCharacterStatValue(IndieDamR, new Option(si.getValue(damR, slv), skillId, duration));
            }
            tsm.sendSetStatPacket();
        }
    }

    public static void giveIndieBuff(Char chr, int skillId, int slv) {
        giveIndieBuff(chr, skillId, slv, false, 0, 0);
    }

    /**
     * A summon whose skill has showSummonedBuffIcon gets a buff icon for its time, as GMS sends it (IndieBuffIcon,
     * value 1, the summon's skill).
     */
    public static void giveSummonBuffIcon(Char chr, int skillId, int slv) {
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        if (si == null || (si.getKeyFlags() & net.swordie.ms.client.character.keys.FuncKeyRules.SUMMON_BUFF_ICON) == 0
                || chr.getField() == null || chr.getField().getSummonByChrAndSkillId(chr, skillId) == null) {
            return;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (tsm.getOptByCTSAndSkill(IndieBuffIcon, skillId) != null) {
            return;
        }
        int dur = si.getValue(time, slv);
        if (dur <= 0) {
            return;
        }
        tsm.putCharacterStatValue(IndieBuffIcon, new Option(1, skillId, dur));
        tsm.sendSetStatPacket();
    }

    /** Booster: x (negative) attack speed for the skill's time. */
    public static void giveBooster(Char chr, int skillId, int slv) {
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        if (si == null) {
            return;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        tsm.putCharacterStatValue(IndieBooster, new Option(si.getValue(x, slv), skillId, si.getValue(time, slv)));
        tsm.sendSetStatPacket();
    }
}
