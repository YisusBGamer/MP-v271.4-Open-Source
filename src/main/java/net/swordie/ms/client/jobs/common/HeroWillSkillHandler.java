package net.swordie.ms.client.jobs.common;

import net.swordie.ms.client.jobs.sengoku.Hayato;
import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.info.HitInfo;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.loaders.SkillData;

/**
 * Created on 08/12/2021.
 *
 * @author Asura
 */
public class HeroWillSkillHandler implements ICommonSkillHandler {

    private Char chr;

    public HeroWillSkillHandler(Char chr) {
        this.chr = chr;
    }

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        Skill skill = chr.getSkill(skillId);
        SkillInfo si = null;
        if (skill != null) {
            si = SkillData.getSkillInfoById(skillId);
        }
        // info type 35 is the Hero's Will kind (every class's "Will" skill); Hayato's (41121004) is type 10
        if (si != null && (SkillConstants.isHeroWillSkill(skillId) || si.getInfoType() == 35 || skillId == Hayato.AKATSUKI_WILL)) {
            tsm.removeAllDebuffs();
            tsm.putCharacterStatValue(CharacterTemporaryStat.IndieBuffIcon,
                    new Option(1, skillId, SkillConstants.HERO_WILL_IMMUNE_SECONDS));
            tsm.sendSetStatPacket();
        }
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
}
