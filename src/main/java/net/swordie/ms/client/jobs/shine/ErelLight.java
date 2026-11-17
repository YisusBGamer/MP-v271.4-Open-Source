package net.swordie.ms.client.jobs.shine;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.JobPathModule;
import net.swordie.ms.client.character.info.HitInfo;
import net.swordie.ms.client.character.items.BodyPart;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.skills.ExtraSkillInfo;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.SkillStat;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.ext.ServerHooks;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Position;

import java.util.ArrayList;
import java.util.List;

import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Erel Light (18001, 18100-18112): its summons; the buffs come from the data (SkillBuffs).
 */
public class ErelLight extends Job {
    public static final int MAPLE_WARRIOR = 181121012;
    private static final int START_MAP = 693007000; // hardcode-ok: map with no String.wz name
    private static final int CREATION_MP = 5;
    private static final int FIRST_JOB_LEVEL = 10;
    private static final int QUEST_GRACE_LEVELS = 10;
    public static final int KEIR = 1352880;

    public ErelLight(Char chr) {
        super(chr);
    }

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        super.handleAttack(c, attackInfo);
        switch (attackInfo.skillId) {
            case SPEAR_OF_LUGH -> onSpearOfLugh();
            case ETERNAL_LIGHT -> {
                if (chr.hasSkill(RADIANT_SENTINEL_ETERNAL_LIGHT)) {
                    chr.write(FieldPacket.registerExtraSkill(chr, ETERNAL_LIGHT, List.of(RADIANT_SENTINEL_ETERNAL_LIGHT)));
                }
            }
            default -> {
            }
        }
    }

    public static final int SHINING_SENTINEL_FURY = 181101002; // Shining Sentinel (Fury of Roan)
    public static final int SHINING_SENTINEL_STING = 181121003; // Shining Sentinel (Sting of Roan)
    public static final int PATH_OF_FIONN = 181121009;
    public static final int PATH_OF_FIONN_FOLLOW_UP = 181121010;
    public static final int SPEAR_OF_LIGHT = 181121014;
    public static final int SPEAR_OF_LUGH = 181141000; // SHINE Spear of Lugh
    public static final int STELLAR_STRIKE = 181141001;
    public static final int ETERNAL_LIGHT = 400011165;
    public static final int RADIANT_SENTINEL_ETERNAL_LIGHT = 400011166;
    public static final int ETERNAL_GUARDIAN = 400011168;
    public static final int GUARDIANS_GAZE = 400011169;
    private static final int LUGH_MAX_STACKS = 8;

    /**
     * As GMS: every Spear of Lugh hit adds a stack (stat 1094, no end time); the hit after the 8th gives Stellar Strike
     * and starts over. Each hit also offers Eternal Light, which the client fires when it is off cooldown.
     */
    private void onSpearOfLugh() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option cur = tsm.getOptByCTSAndSkill(ShineWarriorSixthSpearOfLugh, SPEAR_OF_LUGH);
        int stacks = cur == null ? 0 : cur.nOption;
        List<Integer> extras = new ArrayList<>();
        if (stacks >= LUGH_MAX_STACKS) {
            extras.add(STELLAR_STRIKE);
        }
        if (chr.hasSkill(ETERNAL_LIGHT)) {
            extras.add(ETERNAL_LIGHT);
        }
        for (int extra : extras) {
            chr.write(FieldPacket.registerExtraSkill(SPEAR_OF_LUGH,
                    new ExtraSkillInfo(extra, new Position(0, 0), false, 0, 1)));
        }
        if (stacks >= LUGH_MAX_STACKS) {
            tsm.removeStat(ShineWarriorSixthSpearOfLugh);
        } else {
            tsm.putCharacterStatValue(ShineWarriorSixthSpearOfLugh, new Option(stacks + 1, SPEAR_OF_LUGH, 0));
            tsm.sendSetStatPacket();
        }
    }

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        super.handleSkill(chr, tsm, skillId, slv, inPacket, skillUseInfo);
        switch (skillId) {
            // summons as GMS creates them: the sentinels walk the footholds and attack, the spear stands and attacks
            case SHINING_SENTINEL_FURY, SHINING_SENTINEL_STING -> spawn(skillId, slv,
                    net.swordie.ms.enums.MoveAbility.WalkAcrossFootHold);
            case SPEAR_OF_LIGHT -> spawn(skillId, slv, net.swordie.ms.enums.MoveAbility.Stop);
            case RADIANT_SENTINEL_ETERNAL_LIGHT -> spawn(skillId, slv,
                    net.swordie.ms.enums.MoveAbility.WalkAcrossFootHold);
            case PATH_OF_FIONN -> {
                // GMS: x uses in all, the cast being the first; each follow-up takes one, the stat goes with the last
                SkillInfo si = SkillData.getSkillInfoById(PATH_OF_FIONN);
                int uses = si == null ? 0 : si.getValue(SkillStat.x, slv) - 1;
                if (uses > 0) {
                    tsm.putCharacterStatValue(ShineWarriorPathOfFionn, new Option(uses, PATH_OF_FIONN, 0));
                    tsm.sendSetStatPacket();
                }
            }
            case PATH_OF_FIONN_FOLLOW_UP -> {
                Option o = tsm.getOption(ShineWarriorPathOfFionn);
                if (o != null && o.nOption > 1) {
                    tsm.putCharacterStatValue(ShineWarriorPathOfFionn, new Option(o.nOption - 1, PATH_OF_FIONN_FOLLOW_UP, 0));
                    tsm.sendSetStatPacket();
                } else if (tsm.hasStat(ShineWarriorPathOfFionn)) {
                    tsm.removeStat(ShineWarriorPathOfFionn);
                }
            }
            case ETERNAL_GUARDIAN -> {
                spawn(skillId, slv, net.swordie.ms.enums.MoveAbility.WalkAcrossFootHold,
                        net.swordie.ms.enums.AssistType.None); // GMS: assist 0
                // the Guardian's Gaze stat GMS sends with the cast (n 1, the int after the Indie section is 1)
                SkillInfo si = SkillData.getSkillInfoById(GUARDIANS_GAZE);
                int time = si == null ? 0 : si.getValue(SkillStat.time, slv);
                if (time > 0) {
                    Option o = new Option(1, GUARDIANS_GAZE, time);
                    o.xOption = 1;
                    tsm.putCharacterStatValue(ShineWarriorEternalGuardian, o);
                    tsm.sendSetStatPacket();
                }
            }
            default -> {
            }
        }
    }

    private void spawn(int skillId, int slv, net.swordie.ms.enums.MoveAbility moveAbility) {
        spawn(skillId, slv, moveAbility, net.swordie.ms.enums.AssistType.Attack);
    }

    private void spawn(int skillId, int slv, net.swordie.ms.enums.MoveAbility moveAbility,
                       net.swordie.ms.enums.AssistType assistType) {
        if (chr.getField() == null) {
            return;
        }
        var summon = net.swordie.ms.life.Summon.getSummonBy(chr, skillId, slv);
        summon.setMoveAbility(moveAbility);
        summon.setAssistType(assistType);
        summon.setFlyMob(false);
        chr.getField().spawnSummonAndRemoveOld(summon);
    }

    @Override
    public void handleHit(Client c, InPacket inPacket, HitInfo hitInfo) {
        super.handleHit(c, inPacket, hitInfo);
    }

    /** GMS starts Erel Light in Helia (Erel's Office), where the story choice comes first; MP 5 as GMS makes it. */
    @Override
    public void setCharCreationStats(Char chr) {
        super.setCharCreationStats(chr);
        var cs = chr.getAvatarData().getCharacterStat();
        cs.setPosMap(START_MAP);
        cs.setMp(CREATION_MP);
        cs.setMaxMp(CREATION_MP);
    }

    /**
     * GMS: the 1st job comes with the story's first scene (enter_693007000), the 2nd-4th with quests 61740-61742
     * (q6174Xs). A character that hasn't taken the quest 10 levels later, and bots, advance here.
     */
    @Override
    public void handleLevelUp() {
        super.handleLevelUp();
        if (JobPathModule.isManaged(chr)) {
            return;
        }
        int level = chr.getLevel();
        if (chr.getJob() == JobConstants.JobEnum.EREL_LIGHT.getJobId() && level >= FIRST_JOB_LEVEL) {
            chr.getScriptManager().jobAdvance((short) JobConstants.JobEnum.EREL_LIGHT_1.getJobId());
        }
        int grace = ServerHooks.isAutomated(chr) ? 0 : QUEST_GRACE_LEVELS;
        for (Advancement a : ADVANCEMENTS) {
            if (chr.getJob() == a.from().getJobId() && level >= a.level() + grace) {
                chr.getScriptManager().questJobAdvance(a.quest(), a.from().getJobId(), a.to().getJobId());
                giveAdvancementExtras(chr, a.to().getJobId());
            }
        }
    }

    /** A 2nd-4th job's quest, level, Keir and medal (the 3rd/4th Keir and medal come from the data, not a GMS log). */
    private record Advancement(JobConstants.JobEnum from, JobConstants.JobEnum to, int level, int quest, int keir,
                               int medal) {
    }

    private static final List<Advancement> ADVANCEMENTS = List.of(
            new Advancement(JobConstants.JobEnum.EREL_LIGHT_1, JobConstants.JobEnum.EREL_LIGHT_2, 30, 61740,
                    1352881, 1143541),
            new Advancement(JobConstants.JobEnum.EREL_LIGHT_2, JobConstants.JobEnum.EREL_LIGHT_3, 60, 61741,
                    1352882, 1143542),
            new Advancement(JobConstants.JobEnum.EREL_LIGHT_3, JobConstants.JobEnum.EREL_LIGHT_4, 100, 61742,
                    1352883, 1143543));

    /**
     * As GMS q61740s: the new Keir replaces the worn one, and the job's medal (its medal quest completes from the
     * client). Used by the quests, the level-up fallback, the story skip and job paths.
     */
    public static void giveAdvancementExtras(Char chr, int job) {
        for (Advancement a : ADVANCEMENTS) {
            if (a.to().getJobId() != job) {
                continue;
            }
            var sm = chr.getScriptManager();
            if (!sm.isEquipped(a.keir()) && !sm.hasItem(a.keir())) {
                Item worn = chr.getEquippedItemByBodyPart(BodyPart.Shield);
                if (worn != null && isKeir(worn.getItemId())) {
                    chr.consumeItemFull(worn);
                }
                sm.giveAndEquip(a.keir());
            }
            if (!sm.isEquipped(a.medal()) && !sm.hasItem(a.medal())) {
                sm.giveItem(a.medal());
            }
        }
    }

    private static boolean isKeir(int itemId) {
        return itemId == KEIR || ADVANCEMENTS.stream().anyMatch(a -> a.keir() == itemId);
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isErelLight(id);
    }
}
