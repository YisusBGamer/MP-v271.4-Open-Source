package net.swordie.ms.client.minigames;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.Gun;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.connection.packet.MobPool;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import java.util.Map;

/**
 * Fritto's Eagle Hunt (993000200, fieldType 131): 30 seconds to shoot eagles with the mouse. The client turns on FPS
 * mode by itself in this field, shoots with the gun the server hands out and draws the score from quest-ex 15141
 * "point". A shot eagle shows its points over it (MOB_DEAD_FPS_MODE). The score pays EXP in Fritto's reward map.
 */
public class EagleHunt {

    public static final int FIELD = 993000200;
    public static final int REWARD_FIELD = 993000601;
    private static final int SCORE_QR = 15141;
    private static final String SCORE_KEY = "point";
    private static final int MISSION_QR = 31359; // a "mission" value here blocks the client's shots
    private static final int TIME = 30;
    private static final int ON_SCREEN = 8;
    private static final int EAGLE_MOB = 9833000;
    private static final int BLACK_EAGLE_MOB = 9833001;
    private static final int GOLD_EAGLE_MOB = 9833002;
    private static final int BALD_EAGLE_MOB = 9833003;

    // Mob.wz pointFPSMode
    private static final Map<Integer, Integer> POINTS = Map.of(
            EAGLE_MOB, 50,   // Eagle
            BLACK_EAGLE_MOB, 100,  // Black Eagle
            GOLD_EAGLE_MOB, 200,  // Gold Eagle
            BALD_EAGLE_MOB, -50   // Bald Eagle
    );
    private static final int[][] SPAWN_ODDS = {{9833000, 50}, {9833001, 25}, {9833002, 10}, {9833003, 15}};

    // a full score pays this share of the level's EXP
    private static final int FULL_SCORE = 3000;
    private static final int FULL_SCORE_EXP_PERCENT = 5;

    public static boolean isField(Field field) {
        return field != null && field.getId() == FIELD;
    }

    public static void start(Char chr) {
        Field field = chr.getField();
        if (!isField(field)) {
            return;
        }
        var sm = chr.getScriptManager();
        sm.setQRValue(SCORE_QR, SCORE_KEY, "0");
        Quest mission = chr.getQuestManager().getQuestById(MISSION_QR);
        if (mission != null && !mission.getQRValue().isEmpty()) {
            sm.setQRValue(MISSION_QR, "");
        }

        Gun gun = Gun.getByName("shotgun");
        chr.setGun(gun);
        gun.updateGunToChar(chr);
        gun.updateAmmoToChar(chr);

        refill(field);
        EventManager.addFixedRateEvent(() -> refill(field), 1000, 1000, TIME);
        EventManager.addEvent(() -> timeUp(chr, field), TIME * 1000L + 1500);
    }

    private static void refill(Field field) {
        if (field.getChars().isEmpty()) {
            return;
        }
        int eagles = (int) field.getMobs().stream().filter(m -> POINTS.containsKey(m.getTemplateId())).count();
        for (int i = eagles; i < ON_SCREEN; i++) {
            field.spawnMob(pickEagle(), Util.getRandom(-450, 450), Util.getRandom(-300, 0), false, 0);
        }
    }

    private static int pickEagle() {
        int roll = Util.getRandom(100);
        for (int[] odds : SPAWN_ODDS) {
            roll -= odds[1];
            if (roll < 0) {
                return odds[0];
            }
        }
        return SPAWN_ODDS[0][0];
    }

    /** The instance clock normally ends the game first; this covers an entry without one. */
    private static void timeUp(Char chr, Field field) {
        if (chr.getField() != field) {
            return;
        }
        if (chr.getInstance() != null) {
            chr.getInstance().removeChar(chr);
        } else {
            chr.warp(REWARD_FIELD);
        }
    }

    /** A mob hit by an FPS shot in this field: its points, before it dies. */
    public static void onShot(Char chr, Mob mob) {
        Integer point = POINTS.get(mob.getTemplateId());
        if (point == null) {
            return;
        }
        chr.write(MobPool.deadFpsMode(mob.getObjectId(), point));
        int score = Math.max(0, getScore(chr) + point);
        chr.getScriptManager().setQRValue(SCORE_QR, SCORE_KEY, String.valueOf(score));
    }

    private static int getScore(Char chr) {
        Quest quest = chr.getQuestManager().getQuestById(SCORE_QR);
        if (quest == null) {
            return 0;
        }
        try {
            return quest.getIntProperty(SCORE_KEY);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /** ESC + "Forfeit the mission?": leave without the reward. */
    public static void forfeit(Char chr) {
        chr.getScriptManager().setQRValue(SCORE_QR, SCORE_KEY, "0");
        timeUp(chr, chr.getField());
    }

    /** In Fritto's reward map: take the gun back and pay the score. */
    public static void finish(Char chr) {
        Gun gun = chr.getGun();
        if (gun != null) {
            gun.destroyGunToChar(chr);
        }
        int score = getScore(chr);
        if (score <= 0) {
            return;
        }
        chr.getScriptManager().setQRValue(SCORE_QR, SCORE_KEY, "0");
        int level = Math.min(chr.getLevel(), GameConstants.MAX_CHAR_LEVEL - 1);
        long exp = GameConstants.charExp[level] * Math.min(score, FULL_SCORE) / FULL_SCORE
                * FULL_SCORE_EXP_PERCENT / 100;
        chr.chatMessage("Eagle Hunt: " + score + " points.");
        chr.addPolloFritto(Math.max(1, Math.min(score, FULL_SCORE) / 300)); // Fritto_Out pays Swordie Coins from this
        if (exp > 0) {
            chr.addExp(exp);
        }
    }
}
