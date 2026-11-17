package net.swordie.ms.constants;

import net.swordie.ms.client.character.commerce.voyage.VoyageConstants;
import net.swordie.ms.life.mob.ForcedMobStat;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.container.Tuple;
import net.swordie.ms.world.field.fieldevents.timedfieldevents.elitechampions.BlackCrescendoSlimeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MobConstants {
    public static final int TAMABLE_JAGUAR_MOB = 9304000;
    public static final int ANCIENT_MEGAGOO_MOB = 9601695;

    /** A mob animation as the client names it: "Mob/<template>.img/<action>". */
    public static String actionPath(int templateId, String action) {
        return "Mob/" + templateId + ".img/" + action;
    }

    public static final int QUEST_MOB_START = 9000000;
    public static final int FAKE_STRAW_SCARECROW = 9305655; // the training dummy
    public static final int MOB_SKILL_DEFAULT_DELAY = 7500;

    // filled on mob deaths from any channel thread
    private final static Map<Tuple<Integer, Boolean>, Integer> mobNxBaseAmounts = new java.util.concurrent.ConcurrentHashMap<>();

    public static boolean isZakumBody(int templateId) {
        return templateId == BossConstants.EASY_ZAKUM_BODY
                || templateId == BossConstants.NORMAL_ZAKUM_BODY
                || templateId == BossConstants.HARD_ZAKUM_BODY;
    }

    /** Vellum and Zakum's body dive and resurface (a teleport); a stun mid-dive leaves them stuck above ground. */
    public static boolean isDivingBoss(int templateId) {
        return templateId == BossConstants.VELLUM_NORMAL || templateId == BossConstants.VELLUM_CHAOS
                || isZakumBody(templateId);
    }

    public static boolean isZakumArm(int templateId) {
        final int ZAKUM_ARMS = 8;
        return (templateId > BossConstants.EASY_ZAKUM_BODY && templateId <= BossConstants.EASY_ZAKUM_BODY + ZAKUM_ARMS) ||
                (templateId > BossConstants.NORMAL_ZAKUM_BODY && templateId <= BossConstants.NORMAL_ZAKUM_BODY + ZAKUM_ARMS) ||
                (templateId > BossConstants.HARD_ZAKUM_BODY && templateId <= BossConstants.HARD_ZAKUM_BODY + ZAKUM_ARMS);

    }

    public static boolean isChaosZakumArm(int templateId) {
        final int ZAKUM_ARMS = 8;
        return (templateId > BossConstants.HARD_ZAKUM_BODY && templateId <= BossConstants.HARD_ZAKUM_BODY + ZAKUM_ARMS);

    }

    public static int getSkillDelay(int mobTemplateID){
        switch (mobTemplateID) {
            case BossConstants.VON_BON_CHAOS:
            case BossConstants.VON_BON_NORMAL:
            case BossConstants.CHAOS_CRIMSON_QUEEN_JOYOUS:
            case BossConstants.CHAOS_CRIMSON_QUEEN_SIMMERING:
            case BossConstants.CHAOS_CRIMSON_QUEEN_SORROWFUL:
            case BossConstants.CHAOS_CRIMSON_QUEEN_WRATHFUL:
                return 2000;
            default:
                return MOB_SKILL_DEFAULT_DELAY;
        }
    }

    public static boolean isZakumFlame(int templateId) {
        return templateId == 8800117;
    }

    public static boolean isZakumArmRegen(int templateId) {
        return templateId >= 8800130 && templateId <= 8800137;
    }

    public static boolean isMagnus(int templateId) {
        return templateId == 8880000 || templateId == 8880002 || templateId == 8880010;
    }

    public static boolean isDamien(int templateId) {
        return templateId >= 8880100 && templateId <= 8880131;
    }

    public static double getBuffMultiplierFromRegion(int prefix) {
        double multi = prefix / 100D;
        if (prefix == 410) {
            // end game grandis
            multi *= 2;
        } else if (prefix >= 900) {
            // quest maps
            return 1;
        }
        return 3 * multi;
    }

    public static boolean isUnionRaidBoss(int template) {
        return isUnionRaidNormalBoss(template) || isUnionRaidShieldBoss(template);
    }

    public static boolean isUnionRaidShieldBoss(int template) {
        return template == 9833101 ||
                template == 9833102 ||
                template == 9833103 ||
                template == 9833104 ||
                template == 9833105;
    }

    public static boolean isUnionRaidNormalBoss(int template) {
        return template == 9833201 ||
                template == 9833202 ||
                template == 9833203 ||
                template == 9833204 ||
                template == 9833205;
    }

    public static boolean isInfernoWolf(int templateId) {
        return templateId == 9101078;
    }

    public static boolean isPublicDropsMob(int templateId) {
        return isInfernoWolf(templateId);
    }

    public static boolean isWillBeholderEye(int templateId) {
        return templateId == BossConstants.BEHOLDERS_EYE_BLUE || templateId == BossConstants.BEHOLDERS_EYE_PURPLE;
    }

    /**
     * Method used to remove invisible or additional mobs of a boss. For example invisible mobs in a boss.
     * @param templateId
     * @return
     */
    public static int[] getAttachedMobs(int templateId) {
        switch (templateId) {
            case VoyageConstants
                    .GROSSO_POLPO:
                return new int[]{VoyageConstants.GROSSO_POLPO_ARMS};
        }
        return new int[0];
    }

    /**
     * The most NX a monster gives for one kill (shared by everyone who gets a part of it): what the strongest normal
     * monster (level 274, Mob 8645205) gave with the server's original NX formula, before any rate changes.
     */
    public static final int MAX_NX_PER_KILL = 143_800;
    /**
     * Steepness of the NX curve. 5.1 makes it follow the original formula's amounts by level closely (about 1,100 at
     * level 45, 7,200 at 120, 19,600 at 170, 46,000 at 215, 90,000 at 250).
     */
    private static final double NX_CURVE = 5.1;
    /** Level at which a monster gives the most NX. */
    private static final int MAX_NX_LEVEL = 275;
    /** Bosses give this many times the NX of a normal monster of their level (still at most MAX_NX_PER_KILL). */
    private static final int BOSS_NX_MULTIPLIER = 3;

    /**
     * NX for one kill of a monster of this level: a smooth curve from 1 NX at level 1 through about 160 at level 10,
     * 4,700 at 100, 35,000 at 200 and 90,000 at 250, up to {@link #MAX_NX_PER_KILL} at level 275 and above.
     * Bosses give 3x, still capped. The server's NX rate (server_rates) multiplies it, also capped.
     */
    /** The most NX one boss clear gives (split between the attackers by damage), at {@link #MAX_NX_LEVEL}. */
    public static final int MAX_BOSS_NX_PER_KILL = 50_000;
    /** Steepness of the boss NX curve: about 3k NX at level 110, 14k at 180, 29k at 230, 50k at 275. */
    private static final double BOSS_NX_CURVE = 3;
    /**
     * Final bosses (or reward boxes) that are not in the power crystal list but still end a boss fight:
     * Gollux's and Lucid's reward boxes and Black Mage's last phase.
     */
    private static final Set<Integer> EXTRA_BOSS_NX_MOBS = Set.of(
            BossConstants.GOLLUX_REWARD_EASY, BossConstants.GOLLUX_REWARD_NORMAL,
            BossConstants.GOLLUX_REWARD_HARD, BossConstants.GOLLUX_REWARD_HELL,
            BossConstants.LUCID_REWARD_BOX_EASY, BossConstants.LUCID_REWARD_BOX_NORMAL, BossConstants.LUCID_REWARD_BOX_HARD,
            BossConstants.GUARDIAN_ANGEL_SLIME_NORMAL_CHEST, BossConstants.GUARDIAN_ANGEL_SLIME_CHAOS_CHEST,
            8880504, // Black Mage, phase 4
            BossConstants.KAI_BASE[0] + BossConstants.KAI_CHEST, BossConstants.KAI_BASE[1] + BossConstants.KAI_CHEST
    );

    /**
     * @return whether killing this monster clears a boss fight and so gives the big boss NX
     * ({@link #getBossClearNx}): the bosses that drop a power crystal (the last phase or reward box of each
     * fight) and a few others. Other boss parts and phases (Horntail's heads, Lotus phase 1, ...) use the
     * normal curve, so a fight pays the big amount once.
     */
    public static boolean isBossClearNxMob(int templateId) {
        return BossLootConstants.isDropPowerCrystal(templateId) || EXTRA_BOSS_NX_MOBS.contains(templateId);
    }

    /**
     * NX for clearing a boss of this level (see {@link #isBossClearNxMob}): 1 + 49,999 * x^3 where x goes from 0
     * at level 1 to 1 at {@link #MAX_NX_LEVEL}, so up to {@link #MAX_BOSS_NX_PER_KILL}; never less than what a
     * normal boss of that level gives. The server's NX rate multiplies it, still capped.
     */
    public static int getBossClearNx(int level, double nxRate) {
        if (nxRate <= 0) {
            return 0;
        }
        double x = (Math.max(1, Math.min(MAX_NX_LEVEL, level)) - 1) / (double) (MAX_NX_LEVEL - 1);
        double nx = (1 + (MAX_BOSS_NX_PER_KILL - 1) * Math.pow(x, BOSS_NX_CURVE)) * nxRate;
        int bossClear = (int) Math.max(1, Math.min(MAX_BOSS_NX_PER_KILL, Math.round(nx)));
        return Math.max(bossClear, getNxPerKill(level, true, nxRate));
    }

    public static int getNxPerKill(int level, boolean boss, double nxRate) {
        if (nxRate <= 0) {
            return 0;
        }
        double x = (Math.max(1, Math.min(MAX_NX_LEVEL, level)) - 1) / (double) (MAX_NX_LEVEL - 1);
        double nx = 1 + (MAX_NX_PER_KILL - 1) * (Math.exp(NX_CURVE * x) - 1) / (Math.exp(NX_CURVE) - 1);
        if (boss) {
            nx *= BOSS_NX_MULTIPLIER;
        }
        nx *= nxRate;
        return (int) Math.max(1, Math.min(MAX_NX_PER_KILL, Math.round(nx)));
    }

    public static int getBaseNxDropAmount(Mob mob) {
        // NX stays what it was when every channel was buffed (3 x the region on the monster's HP, and no tenth
        // for ordinary monsters), whether or not the channel is buffed now; outside the channels (instances) as before
        boolean channel = mob.getField() != null && mob.getField().isChannelField();
        var key = new Tuple<>(mob.getTemplateId(), mob.isBuffed() || channel);
        if (mobNxBaseAmounts.containsKey(key)) {
            return mobNxBaseAmounts.get(key);
        }

        // yuno
//        if (getExp() == 0) {
//            return 0;
//        }
//        int base = (int) ((Math.sqrt(getMaxHp() / 100D)) * ((double) getMaxHp() / (getExp() * getLevel())));
//        return Util.getRandom(base, (base + base / 10)); // base + 10% random

        int base = 0;
        if (mob.getTemplateId() == FAKE_STRAW_SCARECROW) {
            // dummy
        } else {
            // sjonnie
            long hp = mob.getMaxHp();
            ForcedMobStat fms = mob.getForcedMobStat();
            boolean boss = mob.getMobInfo().isBoss();
            if (channel && !boss && mob.getMobInfo().getForcedMobStat() != null) {
                hp = (long) (mob.getMobInfo().getForcedMobStat().getMaxHP()
                        * getBuffMultiplierFromRegion(mob.getField().getId() / 1000000));
            }
            base = (int) ((fms.getLevel() / 2D) * (Math.pow(hp, (1 / 5D))));
            if (!mob.isBuffed() && !channel && !boss) {
                base /= 10;
            }
        }

        mobNxBaseAmounts.put(key, base);

        return base; // base + 10% random
    }

    public static int getZakumArmNum(int templateId) {
        if (!isZakumArm(templateId)) {
            return -1;
        }

        final int ZAKUM_ARMS = 8;

        if (templateId > BossConstants.EASY_ZAKUM_BODY && templateId <= BossConstants.EASY_ZAKUM_BODY + ZAKUM_ARMS) {
            return templateId - BossConstants.EASY_ZAKUM_BODY - 1;
        }

        if (templateId > BossConstants.NORMAL_ZAKUM_BODY && templateId <= BossConstants.NORMAL_ZAKUM_BODY + ZAKUM_ARMS) {
            return templateId - BossConstants.NORMAL_ZAKUM_BODY - 1;
        }

        if (templateId > BossConstants.HARD_ZAKUM_BODY && templateId <= BossConstants.HARD_ZAKUM_BODY + ZAKUM_ARMS) {
            return templateId - BossConstants.HARD_ZAKUM_BODY - 1;
        }

        return -1;
    }

    public static Mob alterMobRevive(Mob mob) {
        switch (mob.getTemplateId()) {
            case BossConstants.RANMARU_HARD_BODY_2:
            case BossConstants.RANMARU_HARD_BODY_3:
            case BossConstants.RANMARU_HARD_BODY_4:
            case BossConstants.RANMARU_HARD_BODY_5:
                mob.setMaxHp(BossConstants.RANMARU_HARD_HP);
                mob.setHp(BossConstants.RANMARU_HARD_HP);
                break;
        }

        return mob;
    }

    public static boolean isDoNotReviveMob(int templateId) {
        return templateId == BossConstants.RANMARU_HARD_DEAD_BODY
                || templateId == BossConstants.RANMARU_NORMAL_DEAD_BODY

                // Handled in BlackCrescendoSlimeEvent
                || templateId == BlackCrescendoSlimeEvent.BLACK_CRESCENDO_SLIME_1
                || templateId == BlackCrescendoSlimeEvent.BLACK_CRESCENDO_SLIME_2
                || templateId == BlackCrescendoSlimeEvent.BLACK_CRESCENDO_SLIME_3
                ;
    }

    public static boolean isCantBuffMob(int templateId){
        return templateId == 9601709;
    }

    public static boolean isFriendlyMob(int templateId) {
        return templateId == 9300902; //Spring Bunny for Moon Bunny PQ
    }
}

