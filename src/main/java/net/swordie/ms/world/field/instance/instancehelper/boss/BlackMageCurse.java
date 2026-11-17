package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.connection.packet.Effect;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.life.mob.skill.MobSkill;
import net.swordie.ms.life.mob.skill.MobSkillModule;
import net.swordie.ms.loaders.containerclasses.MobSkillInfo;
import net.swordie.ms.life.mob.skill.MobSkillStat;

import static net.swordie.ms.constants.BossConstants.*;

/**
 * The Black Mage's curses (mob skill 249, the disease on most of the fight's attacks): Creation or Destruction, x as
 * the stat's value and y as its extra int. A curse landing while the other one is on costs a life (GMS): both go,
 * the death count drops by one without dying (the last one kills), and a Seal follows.
 */
public final class BlackMageCurse {

    private BlackMageCurse() {
    }

    public static void apply(Char chr, int level, MobSkillInfo msi) {
        if (chr == null || msi == null) {
            return;
        }
        boolean creation = level == BLACK_MAGE_CURSE_CREATION_LEVEL;
        CharacterTemporaryStat cts = creation ? CharacterTemporaryStat.CurseOfCreation
                : CharacterTemporaryStat.CurseOfDestruction;
        CharacterTemporaryStat other = creation ? CharacterTemporaryStat.CurseOfDestruction
                : CharacterTemporaryStat.CurseOfCreation;
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (tsm.hasStat(other)) {
            loseLife(chr);
            return;
        }
        int ms = msi.getSkillStatIntValue(MobSkillStat.time) * 1000 * BLACK_MAGE_CURSE_TIME_PERCENT / 100;
        Option o = new Option(msi.getSkillStatIntValue(MobSkillStat.x), BLACK_MAGE_CURSE_SKILL, level, ms);
        o.xOption = msi.getSkillStatIntValue(MobSkillStat.y);
        o.setInMillis(true);
        tsm.putCharacterStatValueFromMobSkill(cts, o);
        tsm.sendSetStatPacket();
    }

    private static void loseLife(Char chr) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        tsm.removeStat(CharacterTemporaryStat.CurseOfCreation);
        tsm.removeStat(CharacterTemporaryStat.CurseOfDestruction);
        tsm.sendResetStatPacket();
        Effect hit = Effect.mobSkillHit(BLACK_MAGE_LIFE_LOSS_HIT[0], BLACK_MAGE_LIFE_LOSS_HIT[1]);
        chr.write(UserPacket.effect(hit));
        if (chr.getField() != null) {
            chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), hit), chr);
        }
        int left = chr.getDeathCount() - 1;
        if (left <= 0) {
            chr.die();
            return;
        }
        chr.setDeathCount(left);
        chr.write(UserLocal.deathCountInfo2(chr.getId(), left));
        MobSkill seal = new MobSkill();
        seal.setSkillID(BLACK_MAGE_LIFE_LOSS_SEAL[0]);
        seal.setLevel(BLACK_MAGE_LIFE_LOSS_SEAL[1]);
        MobSkillModule.applyEffect(chr, seal);
    }
}
