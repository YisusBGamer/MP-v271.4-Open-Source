package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.enums.WeatherEffNoticeType;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;

import java.util.List;
import java.util.Set;

/**
 * Guard Captain Darknell (maplestorywiki.net, GMS):
 * <ul>
 *     <li>Corrupted Warriors are summoned continuously (10b HP Normal, 30b Hard).</li>
 *     <li>Elite Bosses (Black Knight, Mad Mage, Vicious Hunter, Rampant Cyborg, Bad Brawler) appear next to players,
 *     cast their attacks and disappear; the lower Darknell's HP, the more often, up to 3 at a time.</li>
 * </ul>
 * Darknell's own attacks (slashes, charge, slam, pillars of light, meteors, Ultimate Sword Technique) are his skills
 * in the game data.
 */
public class DarknellInstanceHelper extends BossMechanicsHelper {

    private static final int[] ELITES = {8645004, 8645005, 8645006, 8645007, 8645008};
    private static final int MAX_WARRIORS = 6;
    private static final int ENTRY_NOTICE_MS = 5000; // GMS
    private static final long ELITE_STAY_MS = 7000;
    private static final int CORRUPTED_WARRIOR_MOB = 8645003;
    private static final int CORRUPTED_WARRIOR_HARD_MOB = 8645069;

    private long nextEliteTime = 0;

    @Override
    protected Set<Integer> getBossIds() {
        return Set.of(BossConstants.DARKNELL_NORMAL, BossConstants.DARKNELL_HARD);
    }

    private int warriorId() {
        return isHardMode() ? CORRUPTED_WARRIOR_HARD_MOB : CORRUPTED_WARRIOR_MOB;
    }

    @Override
    protected void startMechanics() {
        bossNotice(WeatherEffNoticeType.Darknell,
                "Guard Captain Darknell: As long as my army stands, you will not trouble my master!", ENTRY_NOTICE_MS);
        nextEliteTime = System.currentTimeMillis() + 30_000;
        every(8000, () -> {
            if (countAlive(warriorId()) < MAX_WARRIORS) {
                spawnAdd(warriorId(), randomGround(), 0);
            }
        });
        every(1000, this::eliteTick);
    }

    private void eliteTick() {
        double hp = getBossHpRatio();
        if (hp < 0 || System.currentTimeMillis() < nextEliteTime) {
            return;
        }
        int interval = hp > 0.7 ? 45 : hp > 0.4 ? 30 : 20;
        int count = hp > 0.6 ? 1 : hp > 0.3 ? 2 : 3;
        nextEliteTime = System.currentTimeMillis() + interval * 1000L;
        List<Char> chars = getLivingChars();
        if (chars.isEmpty()) {
            return;
        }
        notice("Darknell calls his Elite Bosses!");
        for (int i = 0; i < count; i++) {
            Char target = Util.getRandomFromCollection(chars);
            Position pos = target.getPosition();
            int offset = Util.getRandomBool() ? -120 : 120;
            Mob elite = spawnAdd(ELITES[Util.getRandom(ELITES.length - 1)], new Position(pos.getX() + offset, pos.getY()),
                    1_000_000_000_000L);
            if (elite != null) {
                later(ELITE_STAY_MS, () -> despawn(elite)); // they only appear to cast an attack
            }
        }
    }
}
