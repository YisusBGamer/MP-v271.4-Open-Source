package net.swordie.ms.client.jobs.common;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.info.HitInfo;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.client.jobs.adventurer.magician.Bishop;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.enums.TSIndex;
import net.swordie.ms.loaders.SkillData;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Created on 09/07/2021.
 *
 * @author Asura
 */
public class DecentSkillHandler implements ICommonSkillHandler {



    public static final int DECENT_SPEED_INFUSION_BASE = 8006;
    public static final int DECENT_ADV_BLESSING_BASE = 8005;
    public static final int DECENT_COMBAT_ORDERS_BASE = 8004;
    public static final int DECENT_HYPER_BODY_BASE = 8003;
    public static final int DECENT_SHARP_EYES_BASE = 8002;
    public static final int DECENT_MYSTIC_DOOR_BASE = 8001;
    public static final int DECENT_HASTE_BASE = 8000;


    private Char chr;

    public DecentSkillHandler(Char chr) {
        this.chr = chr;
    }

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        // 5th job Decent skills (4000010xx) are handled with WZ values in Job.handleSkill
        if (skillId >= 400000000) { // hardcode-ok
            return;
        }

        SkillInfo si = SkillData.getSkillInfoOrLog(skillId, "decent skill");
        if (si == null) {
            return;
        }
        // Decent Skills
        if (SkillConstants.isDecentAdvBlessing(skillId)) {
            giveDecentAdvBlessing(skillId, si);
        } else if (SkillConstants.isDecentCombatOrders(skillId)) {
            giveDecentCombatOrders(skillId, si);
        } else if (SkillConstants.isDecentHaste(skillId)) {
            giveDecentHaste(skillId, si);
        } else if (SkillConstants.isDecentHyperBody(skillId)) {
            giveDecentHyperBody(skillId, si);
        } else if (SkillConstants.isDecentMysticDoor(skillId)) {
            giveDecentMysticDoor(skillId);
        } else if (SkillConstants.isDecentSharpEyes(skillId)) {
            giveDecentSharpEyes(skillId, si);
        } else if (SkillConstants.isDecentSpeedInfusion(skillId)) {
            giveDecentSpeedInfusion(skillId, si);
        }

        tsm.sendSetStatPacket();
    }

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {

    }

    @Override
    public void handleHit(Client c, HitInfo hitInfo) {

    }

    @Override
    public void handleRemoveCTS(CharacterTemporaryStat cts, Option o) {

    }

    @Override
    public void handleChangeHP(int curHP, int newHP) {

    }

    @Override
    public void handleChangeMP(int curMP, int newMP) {

    }


    private void giveDecentAdvBlessing(int skillId, SkillInfo si) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();

        if (tsm.hasStatBySkillId(Bishop.ADV_BLESSING) || tsm.hasStatBySkillId(Job.DECENT_ADV_BLESSING_V)) {
            tsm.removeStatsBySkill(Bishop.ADV_BLESSING);
            tsm.removeStatsBySkill(Job.DECENT_ADV_BLESSING_V);
        }

        int slv = 1;
        int sec = si.getValue(time, slv);
        tsm.putCharacterStatValue(DEF, new Option(si.getValue(z, slv), skillId, sec));
        tsm.putCharacterStatValue(IndiePAD, new Option(si.getValue(x, slv), skillId, sec));
        tsm.putCharacterStatValue(IndieMAD, new Option(si.getValue(y, slv), skillId, sec));
        tsm.putCharacterStatValue(IndieMHP, new Option(si.getValue(indieMhp, slv), skillId, sec));
        tsm.putCharacterStatValue(IndieMMP, new Option(si.getValue(indieMmp, slv), skillId, sec));
    }

    private void giveDecentCombatOrders(int skillId, SkillInfo si) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        tsm.putCharacterStatValue(CombatOrders, new Option(si.getValue(x, 1), skillId, si.getValue(time, 1)));
    }

    private void giveDecentHaste(int skillId, SkillInfo si) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int sec = si.getValue(time, 1);
        tsm.putCharacterStatValue(Speed, new Option(si.getValue(speed, 1), skillId, sec));
        tsm.putCharacterStatValue(Jump, new Option(si.getValue(jump, 1), skillId, sec));
    }

    private void giveDecentHyperBody(int skillId, SkillInfo si) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int sec = si.getValue(time, 1);
        tsm.putCharacterStatValue(MaxHP, new Option(si.getValue(x, 1), skillId, sec));
        tsm.putCharacterStatValue(MaxMP, new Option(si.getValue(y, 1), skillId, sec));
    }

    private void giveDecentMysticDoor(int skillId) {
        chr.chatMessage("Not Implemented yet."); // xd
    }

    private void giveDecentSharpEyes(int skillId, SkillInfo si) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        var cr = si.getValue(x, 1);
        var crDmg = si.getValue(y, 1);
        Option o = new Option((cr << 8) + crDmg, skillId, si.getValue(time, 1));
        o.xOption = cr;
        o.yOption = crDmg;
        tsm.putCharacterStatValue(SharpEyes, o);
    }

    private void giveDecentSpeedInfusion(int skillId, SkillInfo si) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        net.swordie.ms.client.character.skills.PartyBooster pb = (net.swordie.ms.client.character.skills.PartyBooster) tsm.getTSBByTSIndex(TSIndex.PartyBooster);
        pb.setNOption(si.getValue(x, 1));
        pb.setROption(skillId);
        pb.setDynamicTermSet(true);
        pb.setHasPartyBooster(tsm.hasStat(PartyBooster));
        pb.setExpireTerm((int) (si.getValue(time, 1) * (chr.getTotalStat(BaseStat.buffTimeR) / 100D)));
        tsm.putCharacterStatValue(PartyBooster, pb.getOption());
    }

}
