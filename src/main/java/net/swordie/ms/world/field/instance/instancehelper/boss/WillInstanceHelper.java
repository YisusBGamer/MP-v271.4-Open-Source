package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.boss.will.WillModule;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;

/**
 * Created on 03/07/2021.
 *
 * @author Asura
 */
public class WillInstanceHelper extends InstanceHelper {

    /** The Lost Light Mirror in Fading Light (maplestorywiki.net reward stage), Easy / Normal / Hard. */
    private static final int[] MIRROR = BossConstants.WILL_MIRROR;

    @Override
    public void onMobDamaged(Char attacker, Mob mob, long damage, long oldHp, long newHp) {
        if (WillModule.isPupil(mob)) {
            WillModule.handleBeholderEyeHit(mob.getField(), mob);
        }
    }

    private static Mob copyIn(Field f, int mode, boolean blue) {
        var life = f.getLifeByTemplateId(blue ? BossConstants.willBlueP1(mode) : BossConstants.willPurpleP1(mode));
        return life instanceof Mob m ? m : null;
    }

    /** Bots cross to the safe dimension when Will destroys one, and catch the light orb in his test. */
    @Override
    public BotHint botHint(Char chr) {
        Field f = chr.getField();
        if (f == null || chr.getPosition() == null || !net.swordie.ms.constants.FieldConstants.isWillP1Field(f.getId())) {
            return null;
        }
        int x = chr.getPosition().getX(), y = chr.getPosition().getY();
        if (WillModule.isInDoomedDimension(f, chr)) {
            return new BotHint(x, y, () -> WillModule.switchDimension(chr), "crosses to the other dimension");
        }
        Mob eye = WillModule.canCatchLight(f) ? WillModule.eyeInDimensionOf(f, chr) : null;
        if (eye != null && eye.getPosition() != null) {
            // hit the Beholder's eye in its own dimension, then catch the light orb it sends
            return new BotHint(eye.getPosition().getX(), eye.getPosition().getY(), () -> {
                WillModule.handleBeholderEyeHit(f, eye);
                WillModule.catchLight(f);
            }, "hits the Beholder's eye and catches the light orb");
        }
        // the shared HP is the higher of the two Wills': a player alone takes turns in both dimensions
        int mode = BossConstants.getWillMode(f.getId());
        boolean blue = WillModule.isInBlueDimension(chr.getPosition());
        Mob here = mode < 0 ? null : copyIn(f, mode, blue);
        Mob there = mode < 0 ? null : copyIn(f, mode, !blue);
        if (here != null && there != null && there.getHp() - here.getHp() > here.getMaxHp() / 20) {
            return new BotHint(x, y, () -> WillModule.switchDimension(chr), "crosses to the Will with more HP left");
        }
        return null;
    }

    @Override
    public void onChrDeath(Char chr) {
        if (chr.getField().hasProperty(BossConstants.WILL_FIELD_FAIL_KEY)) {
            chr.getField().setProperty(BossConstants.WILL_FIELD_FAIL_KEY, true);
        }
    }

    @Override
    public void onMobDeath(Field field, Mob mob) {
        super.onMobDeath(field, mob);
        int mode = BossConstants.getWillMode(field.getId());
        if (mode < 0 || BossConstants.getWillPhase(field.getId()) != 3 || mob.getTemplateId() != BossConstants.willP3(mode)) {
            return;
        }
        // Will falls: on to Fading Light, where the Lost Light Mirror holds the rest of the rewards
        int rewardField = field.getId() + 30;
        for (Char c : field.getChars()) {
            c.chatMessage("Will has been defeated! The light gathers in the mirror...");
        }
        EventManager.addEvent(() -> {
            if (getInstance() == null) {
                return;
            }
            getInstance().warp(rewardField);
            EventManager.addEvent(() -> {
                Field reward = getInstance() == null ? null : getInstance().getField(rewardField);
                if (reward != null) {
                    reward.spawnMob(MIRROR[mode], 0, 247, false, 100_000);
                }
            }, 1500);
        }, 6000);
    }
}
