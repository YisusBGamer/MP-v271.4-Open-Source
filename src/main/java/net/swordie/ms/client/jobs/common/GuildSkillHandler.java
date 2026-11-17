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
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.world.field.Field;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Created on 09/07/2021.
 *
 * @author Asura
 */
public class GuildSkillHandler implements ICommonSkillHandler {


    // Guild Skill
    public static final int SMALL_CHANGE = 91000011;
    public static final int BOSS_SLAYERS = 91001022;
    public static final int UNDETERRED = 91001023;
    public static final int FOR_THE_GUILD = 91001024;
    public static final int HARD_HITTER = 91001025;
    public static final int MERCHANDISING = 91000008;
    public static final int ENHANCEMENT_MASTERY = 91000012;
    public static final int MAYBE_ITS_STILL_OKAY = 91000013;
    public static final int UPGRADE_SALVATION = 91000014;
    public static final int FEARLESS = 91000015;
    public static final int UNITED_FRONT = 91000026;
    public static final int GUILD_EXPERTISE = 91000027;
    public static final int GUILD_ON_FIRE_I = 91000028;
    public static final int GUILD_ON_FIRE_II = 91000029;
    public static final int GUILD_ON_FIRE_III = 91000030;
    public static final int BANNER_OF_PLENTY_II = 91000031;
    public static final int BANNER_OF_PLENTY_III = 91000032;
    public static final int TEAM_PLAYERS = 91000033;
    public static final int WELL_ROUNDED = 91000034;
    public static final int ARCANE_FORCE = 91000035;
    public static final int AMIDST_THE_STARS = 91000038;
    public static final int AMIDST_THE_STARS_2 = 91000041;
    public static final int GUILD_ON_FIRE_I_2 = 91000042;
    public static final int GUILD_ON_FIRE_II_2 = 91000043;
    public static final int SHARENIAN_DEMON_MOUNT = 91001036;
    public static final int SPECIAL_POWER = 91000016;
    public static final int GUILD_EXPERTISE_VI = 91000017;


    private Char chr;

    public GuildSkillHandler(Char chr) {
        this.chr = chr;
    }

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Option o4 = new Option();
        Option o5 = new Option();
        Option o6 = new Option();
        Field field = chr.getField();

        switch (skillId) {
            case BOSS_SLAYERS:
                o1.nOption = si.getValue(indieBDR, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieBDR, o1);
                break;
            case UNDETERRED:
                o1.nOption = si.getValue(indieIgnoreMobpdpR, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieIgnoreMobpdpR, o1);
                break;
            case FOR_THE_GUILD:
                o1.nOption = si.getValue(indieDamR, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, o1);
                break;
            case HARD_HITTER:
                o1.nOption = si.getValue(indieCD, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieCrDmg, o1);
                break;
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
}
