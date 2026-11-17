package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.MobData;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import java.util.Set;

/**
 * Crimson Queen (Root Abyss, Normal and Chaos). She cycles through her four expressions (Simmering, Joyous, Wrathful,
 * Sorrowful; v271 data has no Sorrowful); each is a separate mob with its own attacks, and HP carries over between them.
 * <p>
 * The fight starts in the garden and the Queen spawns in the castle map when her reactor is hit, so the helper
 * follows the map she spawns in.
 */
public class CrimsonQueenInstanceHelper extends BossMechanicsHelper {

    private static final int FACE_CHANGE_MS = 30_000;

    private static final int[] NORMAL_FACES = {
            BossConstants.NORMAL_CRIMSON_QUEEN_SIMMERING, BossConstants.NORMAL_CRIMSON_QUEEN_JOYOUS,
            BossConstants.NORMAL_CRIMSON_QUEEN_WRATHFUL, BossConstants.NORMAL_CRIMSON_QUEEN_SORROWFUL};
    private static final int[] CHAOS_FACES = {
            BossConstants.CHAOS_CRIMSON_QUEEN_SIMMERING, BossConstants.CHAOS_CRIMSON_QUEEN_JOYOUS,
            BossConstants.CHAOS_CRIMSON_QUEEN_WRATHFUL, BossConstants.CHAOS_CRIMSON_QUEEN_SORROWFUL};
    private static final String[] FACE_NAMES = {"Simmering", "Joyous", "Wrathful", "Sorrowful"};

    private volatile Field bossField;
    private boolean[] present;

    @Override
    protected Field getField() {
        return bossField;
    }

    @Override
    protected Set<Integer> getBossIds() {
        return Set.of(NORMAL_FACES[0], NORMAL_FACES[1], NORMAL_FACES[2], NORMAL_FACES[3],
                CHAOS_FACES[0], CHAOS_FACES[1], CHAOS_FACES[2], CHAOS_FACES[3]);
    }

    @Override
    public void onMobSpawn(Field field, Mob mob) {
        super.onMobSpawn(field, mob);
        if (bossField == null && getBossIds().contains(mob.getTemplateId())) {
            bossField = field;
        }
    }

    @Override
    protected void startMechanics() {
        every(FACE_CHANGE_MS, this::changeFace);
    }

    private static int indexOf(int[] faces, int templateId) {
        for (int i = 0; i < faces.length; i++) {
            if (faces[i] == templateId) {
                return i;
            }
        }
        return -1;
    }

    private void changeFace() {
        Mob boss = getBoss();
        if (boss == null || boss.getHp() <= 0) {
            return;
        }
        int[] faces = indexOf(CHAOS_FACES, boss.getTemplateId()) >= 0 ? CHAOS_FACES : NORMAL_FACES;
        int current = indexOf(faces, boss.getTemplateId());
        if (present == null) {
            // v271 data dropped Sorrowful: only faces the mob data has
            present = new boolean[faces.length];
            for (int i = 0; i < faces.length; i++) {
                present[i] = MobData.getMobInfoById(faces[i]) != null;
            }
        }
        java.util.List<Integer> options = new java.util.ArrayList<>();
        for (int i = 0; i < faces.length; i++) {
            if (i != current && present[i]) {
                options.add(i);
            }
        }
        if (options.isEmpty()) {
            return;
        }
        int next = options.get(Util.getRandom(options.size() - 1));
        long hp = boss.getHp();
        long maxHp = boss.getMaxHp();
        Position pos = boss.getPosition();
        despawn(boss);
        Mob queen = getField().spawnMob(faces[next], pos.getX(), pos.getY(), false, maxHp);
        if (queen != null) {
            queen.setHp(hp);
        }
        notice("The Crimson Queen's expression changes: " + FACE_NAMES[next] + ".");
    }
}
