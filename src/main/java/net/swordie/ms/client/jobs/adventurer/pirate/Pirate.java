package net.swordie.ms.client.jobs.adventurer.pirate;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.info.HitInfo;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.SkillStat;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.client.jobs.adventurer.Beginner;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.Effect;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.life.AffectedArea;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Util;

import java.util.Random;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Created on 12/14/2017.
 */
public class Pirate extends Beginner {

    //Pirate
    public static final int DASH = 5001005; //Buff


    // V skills
    public static final int PIRATES_BANNER = 400001017;

    protected static final int DICE_ENHANCE_PROP = 40; // hardcode-ok: data prop=5 is not in the tooltip
    public static final int HEXA_PIRATES_BANNER = 500001036;
    public static final int HEXA_PIRATES_BANNER_2 = 500001037;
    public static final int HEXA_PIRATES_BANNER_3 = 500001038;


    public Pirate(Char chr) {
        super(chr);
    }

    protected int savingGraceProp() {
        return Math.max(chr.getSkillStatValue(prop, Buccaneer.ROLL_OF_THE_DICE_BUCC_SAVING_GRACE),
                chr.getSkillStatValue(prop, Corsair.ROLL_OF_THE_DICE_SAIR_SAVING_GRACE));
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        // 501 is the Cannoneer's first job: Cannoneer (listed after Pirate) handles it
        return id == JobConstants.JobEnum.PIRATE.getJobId();
    }

    // Attack related methods ------------------------------------------------------------------------------------------

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        Char chr = c.getChr();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Skill skill = chr.getSkill(attackInfo.skillId);
        int skillID = 0;
        SkillInfo si = null;
        boolean hasHitMobs = attackInfo.mobAttackInfo.size() > 0;
        int slv = 0;
        if (skill != null) {
            si = SkillData.getSkillInfoById(skill.getSkillId());
            slv = (byte) skill.getCurrentLevel();
            skillID = skill.getSkillId();
        }
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();

        switch (attackInfo.skillId) {
        }
        if (!JobConstants.isPhantom(chr.getJob())) {
            super.handleAttack(c, attackInfo);
        }
    }

    @Override
    public int getFinalAttackSkill() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        SkillInfo si = SkillData.getSkillInfoById(Cannoneer.BARREL_ROULETTE);
        if (tsm.hasStat(Roulette) && tsm.getOption(Roulette).nOption == 1 && Util.succeedProp(si.getValue(z, chr.getSkill(Cannoneer.BARREL_ROULETTE).getCurrentLevel()))) {
            return Cannoneer.BARREL_ROULETTE_FA;
        } else if (chr.hasSkill(Corsair.MAJESTIC_PRESENCE)) {
            return Corsair.MAJESTIC_PRESENCE;
        } else if (chr.hasSkill(Buccaneer.NAUTILUS_STRIKE_BUCC) && tsm.hasStatBySkillId(Buccaneer.NAUTILUS_STRIKE_BUCC) && chr.hasSkillOnCooldown(Buccaneer.NAUTILUS_STRIKE_BUCC)) {
            return Buccaneer.NAUTILUS_STRIKE_BUCC_FA;
        }
        return super.getFinalAttackSkill();
    }


    // Skill related methods -------------------------------------------------------------------------------------------

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        if (!JobConstants.isPhantom(chr.getJob())) {
            super.handleSkill(chr, tsm, skillId, slv, inPacket, skillUseInfo);
        }
        Skill skill = chr.getSkill(skillId);
        SkillInfo si = null;
        if (skill != null) {
            si = SkillData.getSkillInfoById(skillId);
        }
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        switch (skillId) {
            case DASH:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Speed, o1);
                o2.nOption = si.getValue(y, slv);
                o2.rOption = skillId;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Jump, o2);
                o3.nOption = si.getValue(z, slv);
                o3.rOption = skillId;
                o3.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieStance, o3);
                break;
            case Buccaneer.KNUCKLE_BOOSTER:
            case Cannoneer.CANNON_BOOSTER:
            case Jett.GALACTIC_MIGHT:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieBooster, o1); // Galactic Might: attack speed only in v271 (no mhpR)
                break;
            case Buccaneer.ROLL_OF_THE_DICE_BUCC:
            case Corsair.ROLL_OF_THE_DICE_SAIR:
            case Cannoneer.LUCK_OF_THE_DIE:
                int upbound = 6;
                if ((chr.hasSkill(Buccaneer.ROLL_OF_THE_DICE_BUCC_DD) && chr.hasSkill(Buccaneer.ROLL_OF_THE_DICE_BUCC_ADDITION)) ||
                        (chr.hasSkill(Corsair.ROLL_OF_THE_DICE_SAIR_DD) && chr.hasSkill(Corsair.ROLL_OF_THE_DICE_SAIR_ADDITION))) {
                    upbound = 7;
                }
                int diceThrow1 = new Random().nextInt(upbound) + 1;

                if ((chr.hasSkill(Buccaneer.ROLL_OF_THE_DICE_BUCC_ENHANCE) || chr.hasSkill(Corsair.ROLL_OF_THE_DICE_SAIR_ENHANCE)) && Util.succeedProp(DICE_ENHANCE_PROP)) {
                    diceThrow1 = new Random().nextInt(4) + 4;
                }

                chr.write(UserPacket.effect(Effect.skillAffectedSelect(skillId, slv, diceThrow1, false)));
                chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.skillAffectedSelect(skillId, slv, diceThrow1, false)));

                if (diceThrow1 < 2) {
                    chr.reduceSkillCoolTime(skillId, (1000 * si.getValue(cooltime, slv)) / 2);
                    return;
                }

                o1.nOption = diceThrow1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);

                o1.dOption = tsm.throwDice(diceThrow1);
                tsm.putCharacterStatValue(Dice, o1);
                break;
            case Corsair.ROLL_OF_THE_DICE_SAIR_DD:
            case Cannoneer.LUCK_OF_THE_DIE_DD:
                upbound = 6;
                if ((chr.hasSkill(Buccaneer.ROLL_OF_THE_DICE_BUCC_DD) && chr.hasSkill(Buccaneer.ROLL_OF_THE_DICE_BUCC_ADDITION)) ||
                        (chr.hasSkill(Corsair.ROLL_OF_THE_DICE_SAIR_DD) && chr.hasSkill(Corsair.ROLL_OF_THE_DICE_SAIR_ADDITION))) {
                    upbound = 7;
                }
                diceThrow1 = new Random().nextInt(upbound) + 1;
                int diceThrow2 = new Random().nextInt(upbound) + 1;

                if ((chr.hasSkill(Buccaneer.ROLL_OF_THE_DICE_BUCC_ENHANCE) || chr.hasSkill(Corsair.ROLL_OF_THE_DICE_SAIR_ENHANCE)) && Util.succeedProp(DICE_ENHANCE_PROP)) {
                    diceThrow1 = new Random().nextInt(4) + 4;
                    diceThrow2 = new Random().nextInt(4) + 4;
                }

                int diceThrow3 = 1;
                if (chr.hasSkill(Job.LOADED_DICE)) {
                    if (chr.getQuestManager().getQuestById(GameConstants.LOADED_DICE_SELECTION) == null) {
                        chr.getScriptManager().createQuestWithQRValue(GameConstants.LOADED_DICE_SELECTION, "1");
                    }
                    diceThrow3 = Integer.parseInt(chr.getScriptManager().getQRValue(GameConstants.LOADED_DICE_SELECTION));

                    chr.write(UserPacket.effect(Effect.skillAffectedSelect(LOADED_DICE, chr.getSkillLevel(LOADED_DICE), -1, 1, false)));
                    chr.write(UserPacket.effect(Effect.skillAffectedSelect(LOADED_DICE, chr.getSkillLevel(LOADED_DICE), diceThrow3, -1, false)));
                    chr.write(UserPacket.effect(Effect.skillAffectedSelect(LOADED_DICE, chr.getSkillLevel(LOADED_DICE), diceThrow1, 0, true)));
                    chr.write(UserPacket.effect(Effect.skillAffectedSelect(LOADED_DICE, chr.getSkillLevel(LOADED_DICE), diceThrow2, -1, true)));
                    chr.write(UserPacket.effect(Effect.skillAffectedSelect(LOADED_DICE, chr.getSkillLevel(LOADED_DICE), -1, 2, false)));

                    o1.nOption = (diceThrow1 * 100) + (diceThrow2 * 10) + diceThrow3; // if rolled: 3, 5, and 6 the LoadedDice nOption = 356
                    o1.dOption = tsm.throwDice(diceThrow1, diceThrow2, diceThrow3);
                } else {
                    chr.write(UserPacket.effect(Effect.skillAffectedSelect(skillId, slv, diceThrow1, false)));
                    chr.write(UserPacket.effect(Effect.skillAffectedSelect(skillId, slv, diceThrow2, true)));
                    chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.skillAffectedSelect(skillId, slv, diceThrow1, false)));
                    chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.skillAffectedSelect(skillId, slv, diceThrow2, true)));

                    o1.nOption = (diceThrow1 * 10) + diceThrow2; // if rolled: 3 and 5, the DoubleDown nOption = 35
                    o1.dOption = tsm.throwDice(diceThrow1, diceThrow2);
                }
                if (diceThrow1 == 1 || diceThrow2 == 1 || diceThrow3 == 1) {
                    chr.reduceSkillCoolTime(skillId, (1000 * si.getValue(cooltime, slv)) / 2);
                }
                // Saving Grace Hyper Passive Skill
                if ((chr.hasSkill(Buccaneer.ROLL_OF_THE_DICE_BUCC_SAVING_GRACE) || (chr.hasSkill(Corsair.ROLL_OF_THE_DICE_SAIR_SAVING_GRACE))) && diceThrow1 != diceThrow2) {
                    if (Util.succeedProp(savingGraceProp())) {
                        chr.resetSkillCoolTime(skillId);
                    }
                }
                if (diceThrow1 == 1 && diceThrow2 == 1 && diceThrow3 == 1) {
                    return;
                }
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Dice, o1);
                break;

            //Hyper
            case Buccaneer.EPIC_ADVENTURER_BUCC:
            case Cannoneer.EPIC_ADVENTURER_CANNON:
            case Jett.EPIC_ADVENTURER_JETT:
            case Corsair.EPIC_ADVENTURER_SAIR:
                o1.rOption = skillId;
                o1.nOption = si.getValue(indieDamR, slv);
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, o1);
                break;
            case PIRATES_BANNER:
            case HEXA_PIRATES_BANNER: // HEXA Pirate's Banner: same banner area from the HEXA data
            case HEXA_PIRATES_BANNER_2:
            case HEXA_PIRATES_BANNER_3:
                // v271 sends the banner's spot (user position + skill offset, put on a foothold) as 2 shorts
                net.swordie.ms.util.Position spot = inPacket != null && inPacket.getUnreadAmount() >= 4 ? inPacket.decodePosition() : null;
                AffectedArea aa = AffectedArea.getPassiveAA(chr, skillId, slv, spot);
                aa.setFlip(!chr.isLeft());
                chr.getField().spawnAffectedArea(aa);
                break;
        }
        tsm.sendSetStatPacket();
    }


    // Hit related methods ---------------------------------------------------------------------------------------------

    @Override
    public void handleHit(Client c, InPacket inPacket, HitInfo hitInfo) {
        if (chr.hasSkill(Buccaneer.PIRATE_REVENGE_BUCC) || chr.hasSkill(Corsair.PIRATE_REVENGE_SAIR)) {
            applyPirateRevenge();
        }
        super.handleHit(c, inPacket, hitInfo);
    }

    private void applyPirateRevenge() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Skill skill = getPirateRevenge();
        if (skill == null || tsm.getOptByCTSAndSkill(IndieDamR, getPirateRevenge().getSkillId()) != null) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        int slv = skill.getCurrentLevel();
        Option o2 = new Option();
        int prop = si.getValue(SkillStat.prop, slv);
        if (Util.succeedProp(prop)) {
            o2.rOption = getPirateRevenge().getSkillId();
            o2.nOption = si.getValue(indieDamR, slv);
            o2.tOption = si.getValue(time, slv);
            tsm.putCharacterStatValue(IndieDamR, o2);
            int dmgReduce = si.getValue(y, slv);
            if (dmgReduce > 0) {
                tsm.putCharacterStatValue(DamageReduce, new Option(dmgReduce, getPirateRevenge().getSkillId(), o2.tOption));
            }
            tsm.sendSetStatPacket();
        }
    }

    private Skill getPirateRevenge() {
        Skill skill = null;
        if (chr.hasSkill(Corsair.PIRATE_REVENGE_SAIR)) {
            skill = chr.getSkill(Corsair.PIRATE_REVENGE_SAIR);
        }
        if (chr.hasSkill(Buccaneer.PIRATE_REVENGE_BUCC)) {
            skill = chr.getSkill(Buccaneer.PIRATE_REVENGE_BUCC);
        }
        return skill;
    }

}
