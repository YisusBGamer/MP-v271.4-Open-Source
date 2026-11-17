package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.connection.packet.field.*;
import net.swordie.ms.connection.packet.field.kaling.*;
import net.swordie.ms.world.field.fieldskill.KalingSpiritParade;
import net.swordie.ms.world.field.fieldskill.HundunDarkCloud;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.enums.WeatherEffNoticeType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.world.field.Field;

import java.util.Set;

/**
 * Kaling (maplestorywiki.net, GMS): Easy, Normal, Hard, Extreme; level 275, 30 minutes.
 * <ul>
 *     <li>Phase 1: the Three Perils, each in its own season map (Qiongqi in Spring, Taowu in Summer, Hundun in
 *     Autumn). After a Peril falls the players pick the next one through the season portals; if nobody does, they
 *     are taken to the next one. The last Peril takes everyone to phase 2.</li>
 *     <li>Phase 2: Kaling.</li>
 *     <li>Phase 3: Frenzied Kaling with the Death-Gripped Perils on the left, centre and right. Her HP only drops
 *     25% at a time; each Death-Gripped Peril killed unlocks the next 25%.</li>
 * </ul>
 * There's no death count: the party shares 1000 Willpower; a death costs 160/100/80/70/60/50 (1-6 members), and the
 * fight fails at 0. The Willpower gauge is shown with KALING_FIELD_RESULT type 0.
 * <p>
 * Seasonal balance: Spring, Summer and Fall start at 500. A hit by an attack whose Mob.wz info/attack entry has
 * goongi (Spring), dool (Summer) or hondon (Fall) raises that season by the value and lowers its victim (Spring lowers
 * Fall, Summer lowers Spring, Fall lowers Summer). A season at 0 or 1000 is lost: -350 Willpower, the seasons start
 * again at 500, and the +300 for the three Perils is gone. Shown with KALING_FIELD_RESULT type 1. Kaling's Scroll
 * (attack 4 of Kaling, mob skill 277: 5 s) costs 10 Willpower a second, a Vengeful Spirit (attack 4 of the second
 * Kaling id) 30. Not run: the phase 3 area sigils and Extreme's extra damage past 250/750.
 * <p>
 * Attackers (hidden, summonType 12, all attacks onlyFsm): the Four Gracious Plants are hits of +3 (Qiongqi's map),
 * +4 (Taowu's), +5 (Hundun's), +8 (Kaling) and +13 (Frenzied Kaling). The wiki's flower sides pick the attack: phase 1
 * left half / right half = attack 0 / 1 (the other two seasons); phase 2 thirds = 0 Spring, 1 Summer, 2 Fall; phase 3
 * thirds = 0 Spring, 2 Fall, 1 Summer. Each flower is a short-lived attacker on the ground, made to swing by
 * MOB_NEXT_ATTACK to its controller (value = attack index + 1: CMobPool::OnMobNextAttack 0x14360aab0 -> 0x1435aad10,
 * where an onlyFsm attack needs that forced index). How often they come and how many make a string isn't in the data
 * (the wiki only says periodically). Vengeful Spirits (phase 2, every minute) are field skill 100029: its level data
 * (typeInfo, two presets of 72 spots) refers to +8 attack 4, and the client only plays them while a +8 is in the map,
 * so one stays there for the phase. Bots have no client: their hits are worked out here from the same boxes.
 * <p>
 * Client-drawn patterns (each needs its refMobID attacker in the map, so one is kept there):
 * <ul>
 *     <li>Shock zones (Taowu's map, 15 s; phase 2, 8 s): KALING_ELECTRIC_EVENT 9/10 place and fade them, the
 *     KALING_FIELD_EVENT 0x3ec rules make the client hit its user each second with +4 attack 2 / +8 attack 6 and
 *     ask for the stun (KALING_FIELD_REQUEST 0x3ed) after 4 hits in a row.</li>
 *     <li>Black flames along the floor (0x3e9 set, 0x3ea states, 0x3eb strike): Hundun's light up every minute
 *     for 12 s and strike as +5 attack 3; phase 2's stay lit, strike as Kaling's attack 5 when a player's client
 *     asks (KALING_FIELD_REQUEST 1) and rest 2 s.</li>
 *     <li>MobSkill 278 (time, subTime): an explosion where the player stands every subTime ms; phase 2 on a random
 *     player every minute (+8 attack 3), phase 3 from the purple buttons (+17 attack 0, +13 attack 4).</li>
 *     <li>Death's Grasp (MobSkill 279, every 30 s): a claw where a player stands (+8 attack 5, which moves them;
 *     +13 attack 3 in phase 3).</li>
 * </ul>
 * Tracking Projectiles are Hundun's own attack 1; for bots the five chains (attack 5) are added here.
 * <p>
 * Taowu's map (KALING_ELECTRIC_EVENT 0-7, KALING_ELECTRIC_REQUEST): six tesla coils, three to a half; an attack next
 * to one switches it on, three on a half make a current for 30 s, then they switch off. Taowu takes half damage unless
 * he stands in a current. Mini coils (Inducing Current, the harvest key) take in the shock zones around them, rest
 * 10 s and turn Taowu on the user for 20 s. While Taowu lives, Qiongqi and Hundun get a white shield (1%, 2% in
 * Extreme) every 45 s; Taowu shields himself with the zones near his target (0.25% each). Shields are HP that must be
 * lost first (no shield gauge is sent).
 * <p>
 * Hundun's map: Black Flame Amplification (12 s) puts a damage bar over him (Out 2242); filling it breaks it early. The Dark Cloud is field skill 100028
 * (Skill.wz FieldSkill.img: box, 30 s, five spots): it lights the flames near it (they strike when someone comes
 * close), and Hundun inside heals 1% every 5 s. The bar above him that ends his runs to the cloud isn't sent.
 */
public class KalingInstanceHelper extends BossMechanicsHelper {

    public static final String DEAD_PERIL_KEY = "kalingDeadPeril"; // + 0/1/2, set when that Peril falls
    private static final int[] WILLPOWER_COST = {160, 100, 80, 70, 60, 50};
    private static final int[] QIONGQI_DRAIN = {20, 20, 25, 30}; // Willpower every 30 s while Qiongqi lives
    private volatile long nextQiongqiDrain;
    /**
     * Qiongqi's disturbance (GMS): 60 s after he appears, and again 60 s after each one ends, the Torn Spring
     * (+6, beside him) and three of its shards (+14) come; not cleared in 15 s, everyone in the map dies.
     */
    private static final int TORN_SPRING_OFF = 6, TORN_SHARD_OFF = 14, TORN_SHARDS = 3;
    private static final int DISTURBANCE_MS = 60_000, TORN_SPRING_LIMIT_MS = 15_000;
    private volatile long nextDisturbance, tornDeadline;
    private static final int ALL_PERILS_BONUS = 300;
    private static final int[][] DEATH_GRIPPED_POS = {{-1800, 398}, {-600, 398}, {600, 398}};
    private static final int SEASON_LOST_COST = 350;
    private static final String[] SEASON_NAMES = {"Spring", "Summer", "Fall"};
    /** each Peril's map shows its notices in its season's style (GMS) */
    private static final WeatherEffNoticeType[] SEASON_NOTICE = {WeatherEffNoticeType.KalingSpring,
            WeatherEffNoticeType.KalingSummer, WeatherEffNoticeType.KalingFall};
    private static final int SEASON_NOTICE_MS = 5000;
    private static final String PERILS_FIRST = "You first need to defeat the Perils that attacked each season of Shangri-La in order to go after Kaling.";
    private static final int SCROLL_COST = 10, SCROLL_SECONDS = 5, SPIRIT_COST = 30;
    private static final int SCROLL_OFF = 7, SCROLL_ATTACK = 4, SPIRIT_OFF = 8, SPIRIT_ATTACK = 4;
    /**
     * Mob.wz info/attack season values (v271 data) per mode (Easy, Normal, Hard, Extreme), monster (id - mode base) and attack
     * index: season * 1000 + amount (season 0 Spring, 1 Summer, 2 Fall); 0 = none.
     */
    private static final int[][][] SEASON_HITS = {
            {{50, 50, 65, 30, 15}, {1070, 1055, 1055, 1040}, {2050, 2040, 2040, 2055, 2055, 2005}, {1055, 2055}, {55, 2055, 1020}, {55, 1055, 2045, 2030}, {}, {130, 1130, 20, 2130, 0, 2060}, {100, 1100, 2100, 0, 0, 0, 1020}, {60}, {1060}, {2060, 2005}, {0, 0, 0, 0, 0}, {65, 1065, 2065, 0, 0}},
            {{60, 60, 70, 30, 20}, {1070, 1060, 1060, 1040}, {2050, 2050, 2040, 2060, 2060, 2005}, {1060, 2060}, {60, 2060, 1020}, {60, 1060, 2050, 2030}, {}, {150, 1150, 20, 2150, 0, 2070}, {110, 1110, 2110, 0, 0, 0, 1020}, {60}, {1060}, {2060, 2005}, {0, 0, 0, 0, 0}, {70, 1070, 2070, 0, 0}},
            {{65, 65, 75, 30, 20}, {1070, 1060, 1060, 1040}, {2050, 2050, 2040, 2060, 2060, 2005}, {1070, 2070}, {70, 2070, 1020}, {70, 1070, 2050, 2030}, {}, {160, 1160, 20, 2160, 0, 2070}, {110, 1110, 2110, 0, 0, 0, 1020}, {75}, {1075}, {2075, 2005}, {0, 0, 0, 0, 0}, {90, 1090, 2090, 0, 0}},
            {{70, 70, 80, 30, 25}, {1080, 1070, 1070, 1050}, {2050, 2070, 2060, 2070, 2080, 2005}, {1085, 2085}, {85, 2085, 1020}, {85, 1085, 2050, 2030}, {}, {180, 1180, 30, 2180, 0, 2080}, {130, 1130, 2130, 0, 0, 0, 1020}, {90}, {1090}, {2090, 2005}, {0, 0, 0, 0, 0}, {110, 1110, 2110, 0, 0}},
    };

    private volatile int mode = -1;
    private volatile int stage = 0;
    private volatile int willpower = 1000;
    private int perilsDead;
    private int deathGrippedDead;
    private final int[] seasons = {500, 500, 500};
    private volatile boolean seasonLost;
    private final java.util.Map<Integer, Long> scrollUntil = new java.util.concurrent.ConcurrentHashMap<>();

    /** Attacker offset per stage (0-2 the Perils' maps, 3 Kaling, 4 Frenzied Kaling). */
    private static final int[] ATTACKER_OFF = {3, 4, 5, 8, 13};
    /** Flower attack per region of the map, left to right, per stage. */
    private static final int[][] FLOWER_REGIONS = {{0, 1}, {0, 1}, {0, 1}, {0, 1, 2}, {0, 2, 1}};
    private static final int FLOWER_INTERVAL_MS = 12_000, FLOWERS_PER_STRING = 3;
    private static final int FLOWER_SWING_MS = 1200, FLOWER_LIFE_MS = 5000;
    private static final int SPIRIT_INTERVAL_MS = 60_000;
    private static final long ATTACKER_HP = 1_000_000_000_000_000_000L;
    private final java.util.Map<Integer, int[]> groundSpan = new java.util.concurrent.ConcurrentHashMap<>();
    private int spiritSerial;

    /** Hidden attacker kept in a stage's map while its patterns run (the client's refMobID): stage -> offset. */
    private static final java.util.Map<Integer, Integer> HOLDER_OFF = java.util.Map.of(1, 4, 2, 5, 3, SPIRIT_OFF);
    private final java.util.Map<Integer, Mob> holders = new java.util.concurrent.ConcurrentHashMap<>();

    /** Shock zones: Taowu's map (+4 attack 2) and phase 2 (+8 attack 6); Etc/BossKaring.img/electricTerrain boxes. */
    private static final int[][] ZONE_BOX = {{-50, -33, 41, 12}, {-79, -41, 76, 12}, {-90, -48, 88, 12}};
    private static final int ZONE_TICK_MS = 1000, ZONE_HITS_TO_STUN = 4, STUN_SKILL = 123, ZONE_STUN_LEVEL = 150;
    private static final int TAOWU_ZONE_MS = 15_000, P2_ZONE_MS = 8_000;
    private record Zone(int id, int type, int x, int y, int stage, long until) {
    }
    private final java.util.List<Zone> zones = new java.util.concurrent.CopyOnWriteArrayList<>();
    private final java.util.Map<Integer, Integer> botZoneHits = new java.util.concurrent.ConcurrentHashMap<>();
    private final java.util.Map<Integer, Long> zoneStunAt = new java.util.concurrent.ConcurrentHashMap<>();
    private int zoneSerial;

    /** Black flames: Hundun's map (+5 attack 2, amplified attack 3) and phase 2 (Kaling's attack 5). */
    private static final int[] FLAME_TRIGGER_BOX = {-80, -150, 80, 20}, FLAME_HIT_BOX = {-70, -200, 70, 20};
    private static final int FLAME_HIT_MS = 1000, FLAME_STEP = 260, FLAME_REST_MS = 2000;
    private static final int FLAME_OUT = 0, FLAME_LIT = 3, FLAME_ENHANCED = 0x11;
    private static final int AMPLIFY_INTERVAL_MS = 60_000, AMPLIFY_MS = 12_000;
    private static final int[] AMPLIFY_WAVES_MS = {1500, 5500, 9500};
    private static final class Flame {
        final int id, x, y, st;
        volatile boolean ready;
        /** Token the client stores per flame and sends back with its trigger request; new with every strike. */
        volatile int value;

        Flame(int id, int x, int y, int st) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.st = st;
        }
    }
    private final java.util.Map<Integer, java.util.List<Flame>> flames = new java.util.concurrent.ConcurrentHashMap<>();
    private volatile boolean amplifying;
    // GMS (Easy): an amplification puts a 250B damage bar over Hundun (Out 2242 type 2), filled by the damage
    // he takes (type 6); filled, it ends the amplification early, otherwise he plays skill1/sleep at the end (type 4)
    private static final double AMPLIFY_BAR_SHARE = 250_000_000_000.0 / 96_600_000_000_000.0; // of a Peril's HP
    private volatile long ampDamage, ampBar, lastBarSend, nextAmplifyAt;
    private volatile int ampRun;

    /** MobSkill 278 (a debuff: an explosion at the player every subTime ms for time s), level per mode. */
    private static final int CONFUSION_SKILL = 278;
    private static final int[] CONFUSION_P2_LEVEL = {3, 1, 4, 5}, CONFUSION_P3_LEVEL = {6, 2, 7, 8};
    private static final int[] CONFUSION_BOX = {-51, -231, 48, 0};
    private static final int CONFUSION_INTERVAL_MS = 60_000, CONFUSION_P2_ATTACK = 3, CONFUSION_P3_ATTACK = 4;
    private final java.util.Map<Integer, Long> confusedUntil = new java.util.concurrent.ConcurrentHashMap<>();
    /** Phase 3 purple buttons (+17: attack 0 applies 278, selfDestruction after 5 s). */
    private static final int BUTTON_OFF = 17, BUTTON_INTERVAL_MS = 20_000, BUTTON_LIFE_MS = 5000, BUTTONS = 2;
    private static final int[] BUTTON_BOX = {-38, -56, 45, -1};
    /** Death's Grasp (MobSkill 279: every 30 s, moves the player): +8 attack 5 in phase 2, +13 attack 3 in phase 3. */
    private static final int GRASP_INTERVAL_MS = 30_000, GRASP_DELAY_MS = 2000, GRASP_P2_ATTACK = 5,
            GRASP_P3_ATTACK = 3;
    private static final int[] GRASP_BOX = {-100, -200, 100, 10};
    private static final String GRASP_MSG = "You are moving to another location due to the Clutch of Death.";
    /** Hundun's Tracking Projectiles (attack 1, bulletNumber 6): the chains after the first hit are attack 5. */
    private static final int TRACKING_ATTACK = 1, TRACKING_CHAIN_ATTACK = 5, TRACKING_CHAINS = 5,
            TRACKING_CHAIN_MS = 1600;

    private int mode() {
        if (mode < 0) {
            Object prop = getInstance() == null ? null : getInstance().getProperty(BossConstants.KALING_MODE_KEY);
            int m = prop instanceof Number n ? n.intValue() : -1;
            if (m < 0 && getInstance() != null) {
                int[] st = BossConstants.getKalingStage(getInstance().getEnterFieldId());
                m = st == null ? 1 : st[0];
            }
            mode = Math.max(0, Math.min(3, m));
        }
        return mode;
    }

    private int base() {
        return BossConstants.KALING_BASE[mode()];
    }

    private int fieldOf(int st) {
        return BossConstants.KALING_FIELDS[mode()][st];
    }

    @Override
    protected Field getField() {
        int st = Math.min(stage, 4);
        return getInstance() == null ? null : getInstance().getField(fieldOf(st < 3 ? 3 : st));
    }

    @Override
    protected Set<Integer> getBossIds() {
        return Set.of(base() + BossConstants.KALING_FRENZIED);
    }

    @Override
    protected void startMechanics() {
        // GMS: the drain runs only while the party fights a Peril: first 20 s after entering its map, then every
        // 30 s (nothing in the season picker)
        every(1000, () -> {
            if (stage >= 3 || getInstance() == null || getInstance().hasProperty(DEAD_PERIL_KEY + 0)) {
                nextQiongqiDrain = 0;
                return;
            }
            boolean fighting = false;
            for (int st = 0; st < 3 && !fighting; st++) {
                Field f = getInstance().getFields().get(fieldOf(st));
                fighting = f != null && !f.getChars().isEmpty();
            }
            long now = System.currentTimeMillis();
            if (!fighting) {
                nextQiongqiDrain = 0;
            } else if (nextQiongqiDrain == 0) {
                nextQiongqiDrain = now + 20_000;
            } else if (now >= nextQiongqiDrain) {
                nextQiongqiDrain = now + 30_000;
                changeWillpower(-QIONGQI_DRAIN[mode()], "Qiongqi still lives");
            }
        });
        every(1000, this::scrollTick);
        every(1000, this::tornSpringTick);
        every(FLOWER_INTERVAL_MS, this::flowerTick);
        every(5000, this::keepHolders);
        every(SPIRIT_INTERVAL_MS, this::vengefulSpirits);
        every(ZONE_TICK_MS, this::zoneTick);
        every(500, this::taowuAttackTick);
        every(10_000, () -> {
            if (stage == 3) {
                spawnZones(3, 3, P2_ZONE_MS);
            }
        });
        every(500, this::botFlameTriggers);
        every(AMPLIFY_INTERVAL_MS, this::amplifyFlames);
        every(CLOUD_INTERVAL_MS, this::spawnCloud);
        every(1000, this::cloudTick);
        every(500, this::currentTick);
        every(WHITE_SHIELD_INTERVAL_MS, this::whiteShields);
        every(TAOWU_ABSORB_INTERVAL_MS, this::taowuAbsorb);
        every(CONFUSION_INTERVAL_MS, () -> {
            if (stage == 3) {
                Char target = randomChar(fieldOf(3));
                if (target != null) {
                    confuse(target, CONFUSION_P2_LEVEL[mode()], SPIRIT_OFF, CONFUSION_P2_ATTACK);
                }
            }
        });
        every(GRASP_INTERVAL_MS, this::deathsGrasp);
        every(BUTTON_INTERVAL_MS, this::spawnButtons);
    }

    /** Qiongqi's map: the disturbance timer, the Torn Spring and its 15 s limit. */
    private void tornSpringTick() {
        Field field = getInstance() == null ? null : getInstance().getFields().get(fieldOf(0));
        Mob qiongqi = null;
        if (field != null && stage < 3) {
            for (Mob mob : new java.util.ArrayList<>(field.getMobs())) {
                if (mob.getTemplateId() == base() && !mob.isDead()) {
                    qiongqi = mob;
                    break;
                }
            }
        }
        if (qiongqi == null || field.getChars().isEmpty()) {
            if (field != null && tornDeadline != 0) {
                clearTornSpring(field);
            }
            nextDisturbance = 0;
            tornDeadline = 0;
            return;
        }
        long now = System.currentTimeMillis();
        if (nextDisturbance == 0) {
            nextDisturbance = now + DISTURBANCE_MS;
            field.broadcastPacket(KalingFieldPacket.qiongqiResult(QiongqiResult.disturbanceTimer(DISTURBANCE_MS)));
            return;
        }
        if (tornDeadline == 0) {
            if (now >= nextDisturbance) {
                long hp = Math.max(1, BossConstants.KALING_PERIL_HP[mode()] / 200);
                net.swordie.ms.util.Position at = qiongqi.getPosition();
                field.spawnMob(base() + TORN_SPRING_OFF, at.getX(), at.getY(), false, hp);
                for (int i = 0; i < TORN_SHARDS; i++) {
                    net.swordie.ms.util.Position p = field.getRandomPosOnWalkableFoothold(40, true);
                    if (p != null) {
                        field.spawnMob(base() + TORN_SHARD_OFF, p.getX(), p.getY(), false, hp);
                    }
                }
                tornDeadline = now + TORN_SPRING_LIMIT_MS;
                seasonNotice(field, 0, "Qiongqi hides within the drawing. You need to pull it out to stop it.");
            }
            return;
        }
        if (clearTornSpring(null) == 0) {
            nextDisturbance = now + DISTURBANCE_MS;
            tornDeadline = 0;
            field.broadcastPacket(KalingFieldPacket.qiongqiResult(QiongqiResult.disturbanceTimer(DISTURBANCE_MS)));
        } else if (now >= tornDeadline) {
            for (Char chr : new java.util.ArrayList<>(field.getChars())) {
                if (chr.getHP() > 0) {
                    chr.chatMessage("The Torn Spring withers everything around it.");
                    chr.damage(chr.getMaxHP());
                }
            }
            clearTornSpring(field);
            nextDisturbance = now + DISTURBANCE_MS;
            tornDeadline = 0;
            field.broadcastPacket(KalingFieldPacket.qiongqiResult(QiongqiResult.disturbanceTimer(DISTURBANCE_MS)));
        }
    }

    /** @return how many Torn Spring pieces are left in Qiongqi's map; with a field given, removes them */
    private int clearTornSpring(Field removeFrom) {
        Field field = getInstance() == null ? null : getInstance().getFields().get(fieldOf(0));
        if (field == null) {
            return 0;
        }
        int left = 0;
        for (Mob mob : new java.util.ArrayList<>(field.getMobs())) {
            int off = mob.getTemplateId() - base();
            if ((off == TORN_SPRING_OFF || off == TORN_SHARD_OFF) && !mob.isDead()) {
                left++;
                if (removeFrom != null) {
                    field.broadcastPacket(net.swordie.ms.connection.packet.MobPool.leaveField(mob.getObjectId(),
                            net.swordie.ms.life.DeathType.ANIMATION_DEATH));
                    field.removeLife(mob.getObjectId());
                }
            }
        }
        return left;
    }

    /** a Peril map's weather notice in its season's style */
    private void seasonNotice(Field field, int peril, String text) {
        if (field != null) {
            field.broadcastPacket(WvsContext.weatherEffectNotice(SEASON_NOTICE[peril], text, SEASON_NOTICE_MS));
        }
    }

    private boolean perilAlive(int peril) {
        return getInstance() != null && !getInstance().hasProperty(DEAD_PERIL_KEY + peril);
    }

    private Char randomChar(int fieldId) {
        Field field = getInstance() == null ? null : getInstance().getFields().get(fieldId);
        if (field == null) {
            return null;
        }
        java.util.List<Char> alive = new java.util.ArrayList<>();
        for (Char c : field.getChars()) {
            if (c.getHP() > 0 && c.getPosition() != null) {
                alive.add(c);
            }
        }
        return alive.isEmpty() ? null : alive.get(net.swordie.ms.util.Util.getRandom(alive.size() - 1));
    }

    private static boolean inBox(Char chr, int x, int y, int[] box) {
        net.swordie.ms.util.Position p = chr.getPosition();
        // a character's position is its feet: its body reaches about 60 above
        return p != null && p.getX() >= x + box[0] && p.getX() <= x + box[2] && p.getY() >= y + box[1]
                && p.getY() - 60 <= y + box[3];
    }

    private static net.swordie.ms.util.Rect rect(net.swordie.ms.util.Position at, int[] box) {
        return new net.swordie.ms.util.Rect(at.getX() + box[0], at.getY() + box[1] - 60, at.getX() + box[2],
                at.getY() + box[3]);
    }

    private static void stun(Char chr, int level) {
        var ms = new net.swordie.ms.life.mob.skill.MobSkill();
        ms.setSkillID(STUN_SKILL);
        ms.setLevel(level);
        net.swordie.ms.life.mob.skill.MobSkillModule.applyEffect(chr, ms);
    }

    /** The lowest walkable ground at x (the map's floor), or null. */
    private static net.swordie.ms.util.Position floorAt(Field field, int x) {
        net.swordie.ms.world.field.Foothold best = null;
        for (var fh : field.getNonWallFootholds()) {
            if (Math.min(fh.getX1(), fh.getX2()) <= x && Math.max(fh.getX1(), fh.getX2()) >= x
                    && (best == null || fh.getYFromX(x) > best.getYFromX(x))) {
                best = fh;
            }
        }
        return best == null ? null : new net.swordie.ms.util.Position(x, best.getYFromX(x));
    }

    // --- Taowu's attacks (GMS protocol) ----------------------------------------------------------------------------

    private boolean humanIn(Field field) {
        for (Char c : field.getChars()) {
            if (!net.swordie.ms.ext.ServerHooks.isAutomated(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Every 3-6 s Taowu attacks (KALING_ELECTRIC_EVENT 2); the clients place what it leaves. With only bots there,
     * nobody answers, so the zones and coil hits are made here.
     */
    private void taowuAttackTick() {
        if (stage >= 3 || !perilAlive(1) || getInstance() == null || willpower <= 0) {
            return;
        }
        Field field = getInstance().getFields().get(fieldOf(1));
        if (field == null || field.getChars().isEmpty() || holders.get(1) == null) {
            return;
        }
        long now = System.currentTimeMillis();
        if (nextTaowuTimer == 0) {
            nextTaowuTimer = now + TAOWU_TIMER_INTERVAL_MS / 2;
        } else if (now >= nextTaowuTimer) {
            nextTaowuTimer = now + TAOWU_TIMER_INTERVAL_MS;
            field.broadcastPacket(KalingFieldPacket.taowuEvent(TaowuEvent.timer(1470)));
        }
        if (now < nextTaowuAttack) {
            return;
        }
        nextTaowuAttack = now + net.swordie.ms.util.Util.getRandom(TAOWU_ATTACK_MIN_MS, TAOWU_ATTACK_MAX_MS);
        if (net.swordie.ms.util.Util.getRandom(99) < 20) {
            field.broadcastPacket(KalingFieldPacket.taowuEvent(TaowuEvent.attack(false, TAOWU_SPECIAL_ATTACK, false)));
        } else {
            field.broadcastPacket(KalingFieldPacket.taowuEvent(TaowuEvent.attack(true, net.swordie.ms.util.Util.getRandom(0, 3),
                    net.swordie.ms.util.Util.getRandomBool())));
        }
        if (!humanIn(field)) {
            if (net.swordie.ms.util.Util.getRandom(99) < 30) {
                lightCoil(field, net.swordie.ms.util.Util.getRandom(0, GMS_COILS.length - 1));
            } else {
                spawnZones(1, net.swordie.ms.util.Util.getRandom(1, 2), TAOWU_ZONE_MS);
            }
        }
    }

    /** KALING_ELECTRIC_REQUEST 2, byte 0: where Taowu's attack left shock zones (key, {type, x, y}). */
    public void onTaowuZonesReported(Char chr, int key, int[][] spots) {
        if (!inTaowuFight(chr) || spots.length == 0) {
            return;
        }
        pendingZones.put(key, spots);
        chr.getField().broadcastPacket(KalingFieldPacket.taowuEvent(TaowuEvent.zoneAck(key)));
    }

    /** KALING_ELECTRIC_REQUEST 8: the client confirms the key; the zones appear (KALING_ELECTRIC_EVENT 9). */
    public void onTaowuZonesConfirmed(Char chr, int key) {
        int[][] spots = pendingZones.remove(key);
        if (spots == null || !inTaowuFight(chr)) {
            return;
        }
        long until = System.currentTimeMillis() + TAOWU_ZONE_MS;
        java.util.List<int[]> made = new java.util.ArrayList<>();
        for (int[] s : spots) {
            int id;
            synchronized (this) {
                id = zoneSerial++;
            }
            int type = Math.max(0, Math.min(ZONE_BOX.length - 1, s[0]));
            Zone z = new Zone(id, type, s[1], s[2], 1, until);
            zones.add(z);
            made.add(new int[]{z.id(), z.type(), z.x(), z.y()});
        }
        chr.getField().broadcastPacket(KalingFieldPacket.taowuEvent(TaowuEvent.zonesAdd(made.toArray(new int[0][]))));
    }

    /** KALING_ELECTRIC_REQUEST 12 (byte, x, y; after event 11's timer): GMS answers event 13 with 0. */
    public void onTaowuTimerReport(Char chr) {
        if (inTaowuFight(chr)) {
            chr.write(KalingFieldPacket.taowuEvent(TaowuEvent.timerResult(false)));
        }
    }

    // --- Shock zones ----------------------------------------------------------------------------------------------

    private int[] zoneRules(int st) {
        int off = HOLDER_OFF.get(st);
        int attack = st == 1 ? 2 : 6;
        return new int[]{0, ZONE_TICK_MS, 0, ZONE_HITS_TO_STUN, base() + off, attack, STUN_SKILL, ZONE_STUN_LEVEL};
    }

    /** Zones on the ground near the players of that stage's map (the wiki: some of Taowu's attacks leave them). */
    private void spawnZones(int st, int count, int lifeMs) {
        Field field = getInstance() == null ? null : getInstance().getFields().get(fieldOf(st));
        if (field == null || field.getChars().isEmpty() || holders.get(st) == null || willpower <= 0) {
            return;
        }
        java.util.List<int[]> made = new java.util.ArrayList<>();
        long until = System.currentTimeMillis() + lifeMs;
        for (int i = 0; i < count; i++) {
            Char near = randomChar(fieldOf(st));
            if (near == null) {
                break;
            }
            int x = near.getPosition().getX() + net.swordie.ms.util.Util.getRandom(-300, 300);
            net.swordie.ms.world.field.Foothold fh = field.findFootHoldBelow(
                    new net.swordie.ms.util.Position(x, near.getPosition().getY() - 30));
            if (fh == null || Math.min(fh.getX1(), fh.getX2()) > x || Math.max(fh.getX1(), fh.getX2()) < x) {
                continue;
            }
            int id;
            synchronized (this) {
                id = ++zoneSerial;
            }
            Zone z = new Zone(id, net.swordie.ms.util.Util.getRandom(ZONE_BOX.length - 1), x, fh.getYFromX(x), st,
                    until);
            zones.add(z);
            made.add(new int[]{z.id(), z.type(), z.x(), z.y()});
        }
        if (!made.isEmpty()) {
            field.broadcastPacket(KalingFieldPacket.taowuEvent(TaowuEvent.zonesAdd(made.toArray(new int[0][]))));
        }
    }

    /** Fades spent zones; bots in a zone take its hit each tick and are stunned after a few in a row. */
    private void zoneTick() {
        if (getInstance() == null) {
            return;
        }
        long now = System.currentTimeMillis();
        java.util.Map<Integer, java.util.List<Integer>> ended = new java.util.HashMap<>();
        for (Zone z : zones) {
            boolean over = now >= z.until() || (z.stage() == 1 && (!perilAlive(1) || stage >= 3))
                    || (z.stage() == 3 && stage != 3);
            if (over) {
                zones.remove(z);
                ended.computeIfAbsent(z.stage(), k -> new java.util.ArrayList<>()).add(z.id());
            }
        }
        ended.forEach((st, ids) -> {
            Field field = getInstance().getFields().get(fieldOf(st));
            if (field != null) {
                field.broadcastPacket(KalingFieldPacket.taowuEvent(TaowuEvent.zonesEnd(
                        ids.stream().mapToInt(Integer::intValue).toArray())));
            }
        });
        for (int st : new int[]{1, 3}) {
            Field field = getInstance().getFields().get(fieldOf(st));
            Mob holder = holders.get(st);
            if (field == null || holder == null) {
                continue;
            }
            int attack = zoneRules(st)[5];
            for (Char chr : new java.util.ArrayList<>(field.getChars())) {
                if (!net.swordie.ms.ext.ServerHooks.isAutomated(chr) || chr.getHP() <= 0) {
                    continue;
                }
                boolean inside = false;
                for (Zone z : zones) {
                    if (z.stage() == st && inBox(chr, z.x(), z.y(), ZONE_BOX[z.type()])) {
                        inside = true;
                        break;
                    }
                }
                if (!inside) {
                    botZoneHits.remove(chr.getId());
                    continue;
                }
                if (st == 1) {
                    botHarvest(chr);
                }
                net.swordie.ms.socialbot.journey.JourneyBots.mobHit(chr, holder, attack);
                if (botZoneHits.merge(chr.getId(), 1, Integer::sum) >= ZONE_HITS_TO_STUN) {
                    botZoneHits.remove(chr.getId());
                    stun(chr, ZONE_STUN_LEVEL);
                }
            }
        }
    }

    /** The client counted enough zone hits in a row: the stun its rules name. */
    public void onZoneStun(Char chr) {
        Field field = chr.getField();
        long now = System.currentTimeMillis();
        Long last = zoneStunAt.get(chr.getId());
        if (field == null || chr.getHP() <= 0 || (last != null && now - last < 2500)
                || (field.getId() != fieldOf(1) && field.getId() != fieldOf(3))) {
            return;
        }
        zoneStunAt.put(chr.getId(), now);
        stun(chr, ZONE_STUN_LEVEL);
    }

    // --- Black flames ---------------------------------------------------------------------------------------------

    /** The flames along the floor of Hundun's map (2) or Kaling's (3), made once. */
    private java.util.List<Flame> flamesOf(int st) {
        Field field = getInstance() == null ? null : getInstance().getFields().get(fieldOf(st));
        if (field == null) {
            return java.util.List.of();
        }
        return flames.computeIfAbsent(st, k -> {
            java.util.List<Flame> res = new java.util.concurrent.CopyOnWriteArrayList<>();
            int[] span = groundSpan(field);
            if (span != null) {
                int id = 1;
                for (int x = span[0] + FLAME_STEP / 2; x < span[1]; x += FLAME_STEP) {
                    net.swordie.ms.util.Position p = floorAt(field, x);
                    if (p != null) {
                        Flame f = new Flame(id++, p.getX(), p.getY(), st);
                        f.ready = st == 3;
                        res.add(f);
                    }
                }
            }
            return res;
        });
    }

    private int[] flameConfig(int st) {
        int refMob = st == 2 ? base() + HOLDER_OFF.get(2) : base() + BossConstants.KALING_P2;
        int attack = st == 2 ? 2 : 5, enhanced = st == 2 ? 3 : 5;
        int[] c = new int[24];
        c[0] = FLAME_HIT_MS;
        c[2] = FLAME_HIT_MS;
        System.arraycopy(FLAME_TRIGGER_BOX, 0, c, 5, 4);
        System.arraycopy(FLAME_HIT_BOX, 0, c, 9, 4);
        System.arraycopy(FLAME_TRIGGER_BOX, 0, c, 13, 4);
        System.arraycopy(FLAME_HIT_BOX, 0, c, 17, 4);
        c[21] = refMob;
        c[22] = attack;
        c[23] = enhanced;
        return c;
    }

    private void sendFlames(Char chr, int st) {
        java.util.List<Flame> list = flamesOf(st);
        if (list.isEmpty()) {
            return;
        }
        int[][] rows = new int[list.size()][];
        for (int i = 0; i < rows.length; i++) {
            Flame f = list.get(i);
            rows[i] = new int[]{f.id, f.x, f.y, f.ready ? FLAME_LIT : FLAME_OUT, f.value};
        }
        chr.write(KalingFieldPacket.flameEvent(KalingFlameEvent.flames(flameConfig(st), rows)));
    }

    /**
     * A player's client saw a lit flame near it (phase 2, or Hundun's map while the Dark Cloud lights them): the
     * flame strikes, then rests. A value from before the flame's last strike is a stale request.
     */
    public void onFlameTrigger(Char chr, int flameId, int value) {
        Field field = chr.getField();
        int st = field == null ? -1 : field.getId() == fieldOf(3) && stage == 3 ? 3
                : field.getId() == fieldOf(2) && stage < 3 ? 2 : -1;
        if (st < 0) {
            return;
        }
        for (Flame f : flamesOf(st)) {
            if (f.id == flameId) {
                if (f.value == value) {
                    flameStrike(field, f);
                }
                return;
            }
        }
    }

    private void flameStrike(Field field, Flame f) {
        int value;
        synchronized (f) {
            if (!f.ready) {
                return;
            }
            f.ready = false;
            value = ++f.value;
        }
        field.broadcastPacket(KalingFieldPacket.flameEvent(KalingFlameEvent.attack(f.id, value)));
        later(FLAME_HIT_MS, () -> {
            Mob attacker = f.st == 3 ? mobIn(field, base() + BossConstants.KALING_P2) : holders.get(2);
            if (attacker != null) {
                hitBotsIn(field, attacker, f.st == 3 ? 5 : 2, f.x, f.y, FLAME_HIT_BOX);
            }
        });
        later(FLAME_HIT_MS + 800, () -> field.broadcastPacket(
                KalingFieldPacket.flameEvent(KalingFlameEvent.states(new int[][]{{f.id, FLAME_OUT}}))));
        later(FLAME_HIT_MS + 800 + FLAME_REST_MS, () -> {
            if (f.st == 3 ? stage == 3 : stage < 3 && !amplifying && nearCloud(f)) {
                f.ready = true;
                field.broadcastPacket(KalingFieldPacket.flameEvent(KalingFlameEvent.states(new int[][]{{f.id, FLAME_LIT}})));
            }
        });
    }

    /** Bots have no client to see a flame: a lit one strikes when a bot walks into its trigger box. */
    private void botFlameTriggers() {
        if (getInstance() == null) {
            return;
        }
        int st = stage == 3 ? 3 : stage < 3 && cloudUntil > System.currentTimeMillis() ? 2 : -1;
        Field field = st < 0 ? null : getInstance().getFields().get(fieldOf(st));
        if (field == null) {
            return;
        }
        for (Char chr : new java.util.ArrayList<>(field.getChars())) {
            if (!net.swordie.ms.ext.ServerHooks.isAutomated(chr) || chr.getHP() <= 0) {
                continue;
            }
            for (Flame f : flamesOf(st)) {
                if (f.ready && inBox(chr, f.x, f.y, FLAME_TRIGGER_BOX)) {
                    flameStrike(field, f);
                }
            }
        }
    }

    /** Black Flame Amplification: every minute while Hundun lives, all his flames light up and strike for 12 s. */
    private void amplifyFlames() {
        if (stage >= 3 || !perilAlive(2) || getInstance() == null || amplifying) {
            return;
        }
        Field field = getInstance().getFields().get(fieldOf(2));
        java.util.List<Flame> list = flamesOf(2);
        if (field == null || field.getChars().isEmpty() || list.isEmpty() || holders.get(2) == null) {
            return;
        }
        amplifying = true;
        int run = ++ampRun;
        nextAmplifyAt = System.currentTimeMillis() + AMPLIFY_INTERVAL_MS;
        ampDamage = 0;
        ampBar = Math.max(1, (long) (BossConstants.KALING_PERIL_HP[mode()] * AMPLIFY_BAR_SHARE));
        field.broadcastPacket(KalingFieldPacket.hundunResult(HundunResult.bar(true, ampBar)));
        for (Flame f : list) {
            f.ready = false;
        }
        seasonNotice(field, 2, "Hundun goes into a frenzy and amplifies the dark flame. Avoid getting caught in the blaze.");
        field.broadcastPacket(KalingFieldPacket.flameEvent(KalingFlameEvent.states(states(list, FLAME_ENHANCED))));
        Mob hundun = mobIn(field, base() + 2);
        for (int wave : AMPLIFY_WAVES_MS) {
            later(wave, () -> {
                if (!perilAlive(2) || stage >= 3 || ampRun != run || !amplifying) {
                    return;
                }
                for (Flame f : list) {
                    field.broadcastPacket(KalingFieldPacket.flameEvent(KalingFlameEvent.attack(f.id, ++f.value)));
                }
                later(FLAME_HIT_MS, () -> {
                    Mob holder = holders.get(2);
                    if (holder != null) {
                        for (Flame f : list) {
                            hitBotsIn(field, holder, 3, f.x, f.y, FLAME_HIT_BOX);
                        }
                    }
                });
            });
        }
        later(AMPLIFY_MS, () -> {
            if (ampRun != run || !amplifying) {
                return; // broken early
            }
            amplifying = false;
            if (hundun != null && !hundun.isDead()) {
                field.broadcastPacket(KalingFieldPacket.hundunResult(HundunResult.animation(hundun.getObjectId(), hundun.getTemplateId(), "skill1", "sleep")));
            }
            field.broadcastPacket(KalingFieldPacket.flameEvent(KalingFlameEvent.states(states(list, FLAME_OUT))));
            field.broadcastPacket(KalingFieldPacket.hundunResult(HundunResult.amplifyTimer((int) Math.max(0, nextAmplifyAt - System.currentTimeMillis()))));
            lightCloudFlames(field);
        });
    }

    /** Damage on Hundun during an amplification fills the bar; full, the amplification breaks. */
    private void amplifyDamage(Field field, long damage) {
        if (!amplifying) {
            return;
        }
        long total = ampDamage = ampDamage + damage;
        long now = System.currentTimeMillis();
        if (total >= ampBar) {
            amplifying = false;
            field.broadcastPacket(KalingFieldPacket.hundunResult(HundunResult.barValue(ampBar)));
            field.broadcastPacket(KalingFieldPacket.hundunResult(HundunResult.bar(false, 0)));
            field.broadcastPacket(KalingFieldPacket.flameEvent(KalingFlameEvent.states(states(flamesOf(2), FLAME_OUT))));
            notice("The black flames die down!");
            lightCloudFlames(field);
        } else if (now - lastBarSend >= 100) {
            lastBarSend = now;
            field.broadcastPacket(KalingFieldPacket.hundunResult(HundunResult.barValue(total)));
        }
    }

    // --- Dark Cloud -----------------------------------------------------------------------------------------------

    /** Field skill 100028 (Skill.wz FieldSkill.img): the cloud's box around its point and how long it stays. */
    private static final int CLOUD_SKILL = 100028, CLOUD_MS = 30_000, CLOUD_INTERVAL_MS = 50_000;
    private static final int[] CLOUD_BOX = {-339, -399, 375, 21};
    private static final int[][] CLOUD_POINTS = {{-197, 97}, {442, 97}, {992, 97}, {1442, 97}, {1870, 97}};
    /** Field skill level per mode (Easy, Normal, Hard, Extreme), as for 100029. */
    private static final int[] CLOUD_SLV = {2, 1, 3, 4};
    private static final int CLOUD_HEAL_MS = 5000, CLOUD_FLAME_REACH = 150;
    private volatile long cloudUntil;
    private volatile int cloudX, cloudY, cloudKey;
    private volatile boolean hundunInCloud, cloudReportedIn;
    private long lastCloudHeal;

    private boolean nearCloud(Flame f) {
        return cloudUntil > System.currentTimeMillis() && f.x >= cloudX + CLOUD_BOX[0] - CLOUD_FLAME_REACH
                && f.x <= cloudX + CLOUD_BOX[2] + CLOUD_FLAME_REACH;
    }

    /** Every so often while Hundun lives a Dark Cloud settles on one of the map's cloud spots. */
    private void spawnCloud() {
        if (stage >= 3 || !perilAlive(2) || getInstance() == null || amplifying || willpower <= 0
                || cloudUntil > System.currentTimeMillis()) {
            return;
        }
        Field field = getInstance().getFields().get(fieldOf(2));
        Mob hundun = field == null ? null : mobIn(field, base() + 2);
        if (hundun == null || field.getChars().isEmpty()) {
            return;
        }
        int[] pt = CLOUD_POINTS[net.swordie.ms.util.Util.getRandom(CLOUD_POINTS.length - 1)];
        synchronized (this) {
            cloudKey++;
            cloudX = pt[0];
            cloudY = pt[1];
            cloudReportedIn = false;
            hundunInCloud = false;
            cloudUntil = System.currentTimeMillis() + CLOUD_MS;
        }
        field.broadcastPacket(FieldPacket.fieldSkill(new HundunDarkCloud(CLOUD_SLV[mode()], cloudKey, hundun.getObjectId(),
                cloudX, cloudY, CLOUD_MS)));
        seasonNotice(field, 2, "An ominous dark cloud swirls. Stop Hundun from entering it.");
        lightCloudFlames(field);
    }

    /** The black flames near the cloud light up and strike whoever comes close. */
    private void lightCloudFlames(Field field) {
        if (amplifying || stage >= 3) {
            return;
        }
        java.util.List<int[]> lit = new java.util.ArrayList<>();
        for (Flame f : flamesOf(2)) {
            if (nearCloud(f) && !f.ready) {
                f.ready = true;
                lit.add(new int[]{f.id, FLAME_LIT});
            }
        }
        if (!lit.isEmpty()) {
            field.broadcastPacket(KalingFieldPacket.flameEvent(KalingFlameEvent.states(lit.toArray(new int[0][]))));
        }
    }

    /** The boss's controller saw it enter or leave the cloud. */
    public void onCloudReport(Char chr, int key, int mobObjectId, boolean in) {
        if (key == cloudKey && chr.getField() != null && chr.getField().getId() == fieldOf(2)) {
            Mob hundun = mobIn(chr.getField(), base() + 2);
            if (hundun != null && hundun.getObjectId() == mobObjectId) {
                cloudReportedIn = in;
            }
        }
    }

    /** Hundun in the cloud heals 1% every 5 s; bots inside take its small continuous damage; the cloud fades. */
    private void cloudTick() {
        if (getInstance() == null || cloudUntil == 0) {
            return;
        }
        Field field = getInstance().getFields().get(fieldOf(2));
        long now = System.currentTimeMillis();
        if (field == null || now >= cloudUntil || stage >= 3 || !perilAlive(2)) {
            cloudUntil = 0;
            hundunInCloud = false;
            cloudReportedIn = false;
            if (field != null) {
                java.util.List<int[]> out = new java.util.ArrayList<>();
                for (Flame f : flamesOf(2)) {
                    if (f.ready && !amplifying) {
                        f.ready = false;
                        out.add(new int[]{f.id, FLAME_OUT});
                    }
                }
                if (!out.isEmpty()) {
                    field.broadcastPacket(KalingFieldPacket.flameEvent(KalingFlameEvent.states(out.toArray(new int[0][]))));
                }
            }
            return;
        }
        Mob hundun = mobIn(field, base() + 2);
        net.swordie.ms.util.Position p = hundun == null ? null : hundun.getPosition();
        boolean in = p != null && (cloudReportedIn || (p.getX() >= cloudX + CLOUD_BOX[0]
                && p.getX() <= cloudX + CLOUD_BOX[2] && p.getY() >= cloudY + CLOUD_BOX[1]
                && p.getY() <= cloudY + CLOUD_BOX[3] + 30));
        if (in && !hundunInCloud) {
            notice("Hundun enters the dark cloud and draws power from it!");
            lastCloudHeal = now;
        }
        hundunInCloud = in;
        if (in && now - lastCloudHeal >= CLOUD_HEAL_MS) {
            lastCloudHeal = now;
            hundun.setHp(Math.min(hundun.getMaxHp(), hundun.getHp() + hundun.getMaxHp() / 100));
            field.broadcastPacket(net.swordie.ms.connection.packet.field.FieldPacket.fieldEffect(
                    net.swordie.ms.world.field.fieldeffect.FieldEffect.mobHPTagFieldEffect(hundun)));
        }
        for (Char chr : new java.util.ArrayList<>(field.getChars())) {
            if (net.swordie.ms.ext.ServerHooks.isAutomated(chr) && chr.getHP() > 0
                    && inBox(chr, cloudX, cloudY, CLOUD_BOX)) {
                chr.damage((int) Math.max(1, chr.getMaxHP() / 200));
            }
        }
    }

    // --- Taowu: tesla coils, mini coils, shields ------------------------------------------------------------------

    /** Etc/BossKaring.img/teslaCoil: coil/lt2-rb2 (where an attack switches it on), miniCoil/lt-rb 60 wider (reach). */
    private static final int[] COIL_ACTIVATE_BOX = {-138, -100, 137, 0}, MINI_COIL_BOX = {-111, -179, 111, 6};
    private static final int CURRENT_MS = 30_000, MINI_COIL_COOLDOWN_MS = 10_000, HARVEST_COOLDOWN_MS = 30_000,
            AGGRO_MS = 20_000, MINI_COIL_REACH = 300;
    private static final int WHITE_SHIELD_INTERVAL_MS = 45_000, TAOWU_ABSORB_INTERVAL_MS = 30_000,
            TAOWU_ABSORB_REACH = 500;
    // GMS's layout (KALING_ELECTRIC_EVENT 0, Easy): five coils {x, y, kind}, links {a, b, drawn},
    // two triangles sharing coil 0, mini coils {x, y} keyed 0-5, and 1000 for the mini coils' layer
    private static final int[][] GMS_COILS = {{577, 405, 1}, {-85, -45, 2}, {-747, 405, 1}, {1253, -45, 2}, {1915, 405, 1}};
    private static final int[][] GMS_MINI_COILS = {{-65, 405}, {1215, 405}, {-436, -45}, {280, -45}, {879, -45}, {1611, -45}};
    private static final int MINI_COIL_PARAM = 1000;
    private static final int[][] TRIANGLES = {{0, 1, 2}, {0, 3, 4}};
    private static final int[][] LINKS = {{0, 1, 1}, {1, 2, 1}, {2, 0, 0}, {0, 3, 1}, {3, 4, 1}, {4, 0, 0}};
    /** Taowu's attacks (KALING_ELECTRIC_EVENT 2): every 3-6 s in GMS; the client asks for the zones it leaves. */
    private static final int TAOWU_ATTACK_MIN_MS = 3000, TAOWU_ATTACK_MAX_MS = 6000, TAOWU_TIMER_INTERVAL_MS = 46_000;
    private static final int TAOWU_SPECIAL_ATTACK = 0x007100AA;
    private volatile long nextTaowuAttack, nextTaowuTimer;
    /** Shock zones a client reported (request 2), placed when it confirms them (request 8): key -> {type, x, y}. */
    private final java.util.Map<Integer, int[][]> pendingZones = new java.util.concurrent.ConcurrentHashMap<>();
    private volatile int[][] coilPos, miniCoilPos;
    private final boolean[] coilOn = new boolean[6];
    private final long[] currentUntil = new long[TRIANGLES.length];
    private volatile boolean taowuInCurrent;
    private final java.util.Map<Integer, Long> miniCoilReadyAt = new java.util.concurrent.ConcurrentHashMap<>();
    private final java.util.Map<Integer, Long> harvestReadyAt = new java.util.concurrent.ConcurrentHashMap<>();
    /** Extra HP a monster must lose first (Taowu's white shields, his own zone shield): object id -> HP. */
    private final java.util.Map<Integer, Long> shields = new java.util.concurrent.ConcurrentHashMap<>();

    private synchronized void layoutCoils(Field field) {
        if (coilPos != null) {
            return;
        }
        if (field != null) {
            int[][] coils = new int[GMS_COILS.length][];
            for (int i = 0; i < coils.length; i++) {
                coils[i] = new int[]{GMS_COILS[i][0], GMS_COILS[i][1]};
            }
            miniCoilPos = GMS_MINI_COILS;
            coilPos = coils;
            return;
        }
        int[] span = groundSpan(field);
        if (span == null) {
            return;
        }
        int mid = (span[0] + span[1]) / 2;
        int[][] coils = new int[6][];
        int[][] minis = new int[4][];
        int[][] halves = {{span[0], mid}, {mid, span[1]}};
        for (int h = 0; h < 2; h++) {
            int lo = halves[h][0], w = halves[h][1] - halves[h][0];
            coils[h * 3] = floorPoint(field, lo + w * 15 / 100);
            coils[h * 3 + 1] = floorPoint(field, lo + w * 85 / 100);
            int cx = lo + w / 2;
            net.swordie.ms.util.Position top = topAt(field, cx);
            int[] floor = floorPoint(field, cx);
            coils[h * 3 + 2] = top != null && top.getY() < floor[1] - 100 ? new int[]{cx, top.getY()}
                    : new int[]{cx, floor[1] - 300};
            minis[h * 2] = floorPoint(field, lo + w * 33 / 100);
            minis[h * 2 + 1] = floorPoint(field, lo + w * 67 / 100);
        }
        miniCoilPos = minis;
        coilPos = coils;
    }

    private static int[] floorPoint(Field field, int x) {
        net.swordie.ms.util.Position p = floorAt(field, x);
        return p == null ? new int[]{x, 0} : new int[]{x, p.getY()};
    }

    /** The highest walkable ground at x. */
    private static net.swordie.ms.util.Position topAt(Field field, int x) {
        net.swordie.ms.world.field.Foothold best = null;
        for (var fh : field.getNonWallFootholds()) {
            if (Math.min(fh.getX1(), fh.getX2()) <= x && Math.max(fh.getX1(), fh.getX2()) >= x
                    && (best == null || fh.getYFromX(x) < best.getYFromX(x))) {
                best = fh;
            }
        }
        return best == null ? null : new net.swordie.ms.util.Position(x, best.getYFromX(x));
    }

    private void sendCoils(Char chr) {
        Field field = chr.getField();
        if (field == null) {
            return;
        }
        layoutCoils(field);
        int[][] cp = coilPos, mp = miniCoilPos;
        if (cp == null || mp == null) {
            return;
        }
        long now = System.currentTimeMillis();
        int[][] coils = new int[cp.length][];
        int[][] tris = new int[TRIANGLES.length][];
        int[][] minis = new int[mp.length][];
        synchronized (this) {
            for (int i = 0; i < cp.length; i++) {
                coils[i] = new int[]{cp[i][0], cp[i][1], i < GMS_COILS.length ? GMS_COILS[i][2] : 0, coilOn[i] ? 1 : 0};
            }
            for (int t = 0; t < TRIANGLES.length; t++) {
                tris[t] = new int[]{TRIANGLES[t][0], TRIANGLES[t][1], TRIANGLES[t][2], currentUntil[t] > now ? 1 : 0};
            }
        }
        int[][] links = new int[LINKS.length][];
        for (int i = 0; i < LINKS.length; i++) {
            links[i] = new int[]{LINKS[i][0], LINKS[i][1], LINKS[i][2]};
        }
        for (int i = 0; i < mp.length; i++) {
            Long ready = miniCoilReadyAt.get(i);
            minis[i] = new int[]{i, mp[i][0], mp[i][1], ready != null && ready > now ? 1 : 0};
        }
        chr.write(KalingFieldPacket.taowuEvent(TaowuEvent.teslaCoils(base() + 1, coils, links, tris, MINI_COIL_PARAM, minis)));
        if (taowuInCurrent) {
            chr.write(KalingFieldPacket.taowuEvent(TaowuEvent.inCurrent(true)));
        }
    }

    private boolean inTaowuFight(Char chr) {
        return stage < 3 && perilAlive(1) && chr != null && chr.getField() != null
                && chr.getField().getId() == fieldOf(1);
    }

    /** GMS: Taowu's attack hit a coil (the client reports it); a lit triangle makes a current for 30 s. */
    public void onTaowuAttackCoil(Char chr, int coil) {
        if (!inTaowuFight(chr)) {
            return;
        }
        lightCoil(chr.getField(), coil);
    }

    private void lightCoil(Field field, int coil) {
        layoutCoils(field);
        int[][] cp = coilPos;
        if (cp == null || coil < 0 || coil >= cp.length) {
            return;
        }
        java.util.List<Integer> on = new java.util.ArrayList<>();
        java.util.List<Integer> currents = new java.util.ArrayList<>();
        synchronized (this) {
            if (!coilOn[coil]) {
                coilOn[coil] = true;
                on.add(coil);
            }
            if (on.isEmpty()) {
                return;
            }
            long now = System.currentTimeMillis();
            for (int t = 0; t < TRIANGLES.length; t++) {
                int[] tri = TRIANGLES[t];
                if (currentUntil[t] <= now && coilOn[tri[0]] && coilOn[tri[1]] && coilOn[tri[2]]) {
                    currentUntil[t] = now + CURRENT_MS;
                    currents.add(t);
                }
            }
        }
        field.broadcastPacket(KalingFieldPacket.taowuEvent(TaowuEvent.coilStates(true,
                on.stream().mapToInt(Integer::intValue).toArray())));
        for (int t : currents) {
            field.broadcastPacket(KalingFieldPacket.taowuEvent(TaowuEvent.current(t, true)));
            seasonNotice(field, 1, "The charged Tesla coil creates a weakened current. You need to lure Taowu inside the current.");
        }
    }

    /** Spent currents switch their coils off; Taowu standing in a live one is weakened (no 50% damage penalty). */
    private void currentTick() {
        if (getInstance() == null || coilPos == null) {
            return;
        }
        Field field = getInstance().getFields().get(fieldOf(1));
        if (field == null) {
            return;
        }
        long now = System.currentTimeMillis();
        boolean taowuAlive = stage < 3 && perilAlive(1);
        java.util.List<Integer> off = new java.util.ArrayList<>();
        java.util.List<Integer> ended = new java.util.ArrayList<>();
        boolean[] live = new boolean[TRIANGLES.length];
        synchronized (this) {
            for (int t = 0; t < TRIANGLES.length; t++) {
                if (currentUntil[t] != 0 && (currentUntil[t] <= now || !taowuAlive)) {
                    currentUntil[t] = 0;
                    ended.add(t);
                    for (int c : TRIANGLES[t]) {
                        if (coilOn[c]) {
                            coilOn[c] = false;
                            off.add(c);
                        }
                    }
                }
                live[t] = currentUntil[t] > now;
            }
        }
        for (int t : ended) {
            field.broadcastPacket(KalingFieldPacket.taowuEvent(TaowuEvent.current(t, false)));
        }
        if (!off.isEmpty()) {
            field.broadcastPacket(KalingFieldPacket.taowuEvent(TaowuEvent.coilStates(false,
                    off.stream().mapToInt(Integer::intValue).toArray())));
        }
        Mob taowu = taowuAlive ? mobIn(field, base() + 1) : null;
        boolean in = false;
        if (taowu != null && taowu.getPosition() != null) {
            // his body's middle, about 100 above his feet
            int x = taowu.getPosition().getX(), y = taowu.getPosition().getY() - 100;
            int[][] cp = coilPos;
            for (int t = 0; t < TRIANGLES.length && !in; t++) {
                int[] tri = TRIANGLES[t];
                in = live[t] && inTriangle(x, y, cp[tri[0]], cp[tri[1]], cp[tri[2]]);
            }
        }
        if (in != taowuInCurrent) {
            taowuInCurrent = in;
            field.broadcastPacket(KalingFieldPacket.taowuEvent(TaowuEvent.inCurrent(in)));
            if (in) {
                notice("Taowu is caught in the current and weakened!");
            }
        }
    }

    private static boolean inTriangle(int x, int y, int[] a, int[] b, int[] c) {
        long d1 = (long) (x - b[0]) * (a[1] - b[1]) - (long) (a[0] - b[0]) * (y - b[1]);
        long d2 = (long) (x - c[0]) * (b[1] - c[1]) - (long) (b[0] - c[0]) * (y - c[1]);
        long d3 = (long) (x - a[0]) * (c[1] - a[1]) - (long) (c[0] - a[0]) * (y - a[1]);
        boolean neg = d1 < 0 || d2 < 0 || d3 < 0, pos = d1 > 0 || d2 > 0 || d3 > 0;
        return !(neg && pos);
    }

    /**
     * Inducing Current (the harvest key next to a mini coil): the coil takes in the shock zones around it and rests
     * 10 s; Taowu turns on the harvester for 20 s; the key itself has a 30 s cooldown.
     */
    public void onMiniCoilHarvest(Char chr, int key) {
        int[][] mp = miniCoilPos;
        if (!inTaowuFight(chr) || chr.getHP() <= 0 || mp == null || key < 0 || key >= mp.length) {
            return;
        }
        long now = System.currentTimeMillis();
        int[] coil = mp[key];
        Long readyAt = miniCoilReadyAt.get(key), mine = harvestReadyAt.get(chr.getId());
        if ((readyAt != null && readyAt > now) || (mine != null && mine > now)
                || !inBox(chr, coil[0], coil[1], MINI_COIL_BOX)) {
            return;
        }
        miniCoilReadyAt.put(key, now + MINI_COIL_COOLDOWN_MS);
        harvestReadyAt.put(chr.getId(), now + HARVEST_COOLDOWN_MS);
        java.util.List<Integer> taken = new java.util.ArrayList<>();
        for (Zone z : zones) {
            if (z.stage() == 1 && Math.abs(z.x() - coil[0]) <= MINI_COIL_REACH && Math.abs(z.y() - coil[1]) <= 200) {
                zones.remove(z);
                taken.add(z.id());
            }
        }
        Field field = chr.getField();
        field.broadcastPacket(KalingFieldPacket.taowuEvent(TaowuEvent.miniCoilUsed(key,
                taken.stream().mapToInt(Integer::intValue).toArray())));
        chr.chatMessage("The mini coil absorbs the current. Taowu turns on you for " + AGGRO_MS / 1000 + " seconds!");
        later(MINI_COIL_COOLDOWN_MS, () -> {
            if (inTaowuMap(field)) {
                field.broadcastPacket(KalingFieldPacket.taowuEvent(TaowuEvent.miniCoilReady(key)));
            }
        });
    }

    private boolean inTaowuMap(Field field) {
        return field != null && stage < 3 && perilAlive(1) && field.getId() == fieldOf(1);
    }

    /** A bot stuck in a zone next to a mini coil uses it, as a player would. */
    private void botHarvest(Char chr) {
        int[][] mp = miniCoilPos;
        if (mp == null) {
            return;
        }
        for (int i = 0; i < mp.length; i++) {
            if (inBox(chr, mp[i][0], mp[i][1], MINI_COIL_BOX)) {
                onMiniCoilHarvest(chr, i);
                return;
            }
        }
    }

    /** While Taowu lives, Qiongqi and Hundun get a white shield of 1% of their HP (2% in Extreme) every 45 s. */
    private void whiteShields() {
        if (stage >= 3 || !perilAlive(1) || getInstance() == null || willpower <= 0) {
            return;
        }
        for (int p : new int[]{0, 2}) {
            Field field = perilAlive(p) ? getInstance().getFields().get(fieldOf(p)) : null;
            Mob peril = field == null ? null : mobIn(field, base() + p);
            if (peril == null) {
                continue;
            }
            shields.put(peril.getObjectId(), peril.getMaxHp() * (mode() == 3 ? 2 : 1) / 100);
            for (Char chr : field.getChars()) {
                chr.chatMessage("Taowu's power shields " + (p == 0 ? "Qiongqi" : "Hundun") + ".");
            }
        }
    }

    /** Shield Generation: Taowu takes in the shock zones near his target, 0.25% of his HP each. */
    private void taowuAbsorb() {
        if (stage >= 3 || !perilAlive(1) || getInstance() == null || willpower <= 0) {
            return;
        }
        Field field = getInstance().getFields().get(fieldOf(1));
        Mob taowu = field == null ? null : mobIn(field, base() + 1);
        Char target = taowu == null ? null : randomChar(fieldOf(1));
        if (target == null) {
            return;
        }
        java.util.List<Integer> taken = new java.util.ArrayList<>();
        for (Zone z : zones) {
            if (z.stage() == 1 && Math.abs(z.x() - target.getPosition().getX()) <= TAOWU_ABSORB_REACH) {
                zones.remove(z);
                taken.add(z.id());
            }
        }
        if (taken.isEmpty()) {
            return;
        }
        field.broadcastPacket(KalingFieldPacket.taowuEvent(TaowuEvent.zonesEnd(taken.stream().mapToInt(Integer::intValue)
                .toArray())));
        shields.merge(taowu.getObjectId(), taowu.getMaxHp() / 400 * taken.size(), Long::sum);
        for (Char chr : field.getChars()) {
            chr.chatMessage("Taowu absorbs " + taken.size() + " shock zone" + (taken.size() == 1 ? "" : "s")
                    + " and shields himself.");
        }
    }

    @Override
    public void onAttack(Char chr, net.swordie.ms.client.character.skills.info.AttackInfo attackInfo) {
    }

    /** Shields soak up damage first; Taowu outside a current takes half. */
    @Override
    public void onMobDamaged(Char attacker, Mob mob, long damage, long oldHp, long newHp) {
        if (damage <= 0 || mob.getField() == null || stage >= 3) {
            return;
        }
        int off = mob.getTemplateId() - base();
        if (off < 0 || off > 2 || mob.getField().getId() != fieldOf(off)) {
            return;
        }
        if (off == 2) {
            amplifyDamage(mob.getField(), damage);
        }
        if (newHp <= 0) {
            return;
        }
        long refund = 0;
        Long shield = shields.get(mob.getObjectId());
        if (shield != null && shield > 0) {
            refund = Math.min(shield, damage);
            if (shield - refund <= 0) {
                shields.remove(mob.getObjectId());
            } else {
                shields.put(mob.getObjectId(), shield - refund);
            }
        }
        if (off == 1 && !taowuInCurrent) {
            refund += (damage - refund) / 2;
        }
        if (refund > 0) {
            mob.setHp(Math.min(mob.getMaxHp(), mob.getHp() + refund));
        }
    }

    private static int[][] states(java.util.List<Flame> list, int state) {
        int[][] res = new int[list.size()][];
        for (int i = 0; i < res.length; i++) {
            res[i] = new int[]{list.get(i).id, state};
        }
        return res;
    }

    private static Mob mobIn(Field field, int templateId) {
        for (Mob m : field.getMobs()) {
            if (m.getTemplateId() == templateId && !m.isDead()) {
                return m;
            }
        }
        return null;
    }

    private static void hitBotsIn(Field field, Mob mob, int attackIdx, int x, int y, int[] box) {
        for (Char chr : new java.util.ArrayList<>(field.getChars())) {
            if (chr.getHP() > 0 && net.swordie.ms.ext.ServerHooks.isAutomated(chr) && inBox(chr, x, y, box)) {
                net.swordie.ms.socialbot.journey.JourneyBots.mobHit(chr, mob, attackIdx);
            }
        }
    }

    // --- Confusion, buttons, Death's Grasp ------------------------------------------------------------------------

    /** MobSkill 278: an explosion (the attacker's attack) where the player stands, every subTime ms for time s. */
    private void confuse(Char chr, int level, int attackerOff, int attackIdx) {
        var msi = net.swordie.ms.loaders.SkillData.getMobSkillInfoByIdAndLevel(CONFUSION_SKILL, level);
        long now = System.currentTimeMillis();
        Long until = confusedUntil.get(chr.getId());
        if (msi == null || (until != null && until > now)) {
            return;
        }
        int timeMs = msi.getSkillStatIntValue(net.swordie.ms.life.mob.skill.MobSkillStat.time) * 1000;
        int every = Math.max(1000, msi.getSkillStatIntValue(net.swordie.ms.life.mob.skill.MobSkillStat.subTime));
        confusedUntil.put(chr.getId(), now + timeMs);
        Field field = chr.getField();
        chr.chatMessage("You are confused: explosions follow you for " + timeMs / 1000 + " seconds.");
        for (int t = every; t <= timeMs; t += every) {
            later(t, () -> {
                if (chr.getField() == field && chr.getHP() > 0 && chr.getPosition() != null && willpower > 0) {
                    strikeAt(field, base() + attackerOff, attackIdx, chr.getPosition(), CONFUSION_BOX,
                            FLOWER_SWING_MS);
                }
            });
        }
    }

    /** A short-lived hidden attacker swings once at a spot; bots in the box take it. */
    private void strikeAt(Field field, int templateId, int attackIdx, net.swordie.ms.util.Position at, int[] box,
                          int delayMs) {
        Mob mob = spawnAttacker(field, templateId, new net.swordie.ms.util.Position(at.getX(), at.getY()));
        if (mob == null) {
            return;
        }
        later(delayMs, () -> swing(field, mob, attackIdx, rect(mob.getPosition(), box)));
        EventManager.addEvent(() -> removeAttacker(field, mob), delayMs + FLOWER_LIFE_MS);
    }

    /** Death's Grasp on a random player: a claw where they stand, after a short warning. */
    private void deathsGrasp() {
        if (getInstance() == null || willpower <= 0 || (stage != 3 && stage != 4)) {
            return;
        }
        Char target = randomChar(fieldOf(stage));
        Field field = target == null ? null : target.getField();
        if (field == null) {
            return;
        }
        boolean p2 = stage == 3;
        strikeAt(field, base() + (p2 ? SPIRIT_OFF : ATTACKER_OFF[4]), p2 ? GRASP_P2_ATTACK : GRASP_P3_ATTACK,
                target.getPosition(), GRASP_BOX, GRASP_DELAY_MS);
    }

    /** Disease 279: moved to a random spot of the map. */
    private void graspMove(Char chr) {
        Field field = chr.getField();
        net.swordie.ms.util.Position to = field == null ? null : field.getRandomPosOnWalkableFoothold(40, true);
        if (to == null) {
            return;
        }
        chr.chatMessage(GRASP_MSG);
        if (!net.swordie.ms.socialbot.journey.JourneyBots.placeAt(chr, to.getX(), to.getY())) {
            chr.write(net.swordie.ms.connection.packet.field.FieldPacket.teleport(to, chr));
        }
    }

    /** Phase 3: purple buttons that seal whoever sets them off with 278, then burst on their own. */
    private void spawnButtons() {
        if (stage != 4 || getInstance() == null || willpower <= 0) {
            return;
        }
        Field field = getInstance().getFields().get(fieldOf(4));
        int[] span = field == null ? null : groundSpan(field);
        if (span == null || field.getChars().isEmpty()) {
            return;
        }
        for (int i = 0; i < BUTTONS; i++) {
            net.swordie.ms.util.Position pos = groundIn(field, span[0], span[1]);
            if (pos == null) {
                continue;
            }
            Mob button = field.spawnMob(base() + BUTTON_OFF, pos.getX(), pos.getY(), false, ATTACKER_HP);
            if (button == null) {
                continue;
            }
            for (int t = 1000; t <= BUTTON_LIFE_MS; t += 1000) {
                later(t, () -> {
                    if (field.getLifeByObjectID(button.getObjectId()) != null) {
                        hitBotsIn(field, button, 0, button.getPosition().getX(), button.getPosition().getY(),
                                BUTTON_BOX);
                    }
                });
            }
            EventManager.addEvent(() -> removeAttacker(field, button), BUTTON_LIFE_MS);
        }
    }

    /** Bots: the chains that follow Tracking Projectiles' first hit (a player's client plays them itself). */
    private void trackingChains(Char chr) {
        Field field = chr.getField();
        for (int i = 1; i <= TRACKING_CHAINS; i++) {
            later((long) i * TRACKING_CHAIN_MS, () -> {
                Mob hundun = chr.getField() == field ? mobIn(field, base() + 2) : null;
                if (hundun != null && chr.getHP() > 0) {
                    net.swordie.ms.socialbot.journey.JourneyBots.mobHit(chr, hundun, TRACKING_CHAIN_ATTACK);
                }
            });
        }
    }

    // --- Four Gracious Plants -------------------------------------------------------------------------------------

    private void flowerTick() {
        if (getInstance() == null || willpower <= 0) {
            return;
        }
        if (stage < 3) {
            for (int p = 0; p < 3; p++) {
                if (!getInstance().hasProperty(DEAD_PERIL_KEY + p)) {
                    flowerString(p);
                }
            }
        } else if (stage <= 4) {
            flowerString(stage);
        }
    }

    /** One string of flowers in a random region of that stage's map, if anyone is there. */
    private void flowerString(int st) {
        Field field = getInstance().getFields().get(fieldOf(st));
        if (field == null || field.getChars().isEmpty()) {
            return;
        }
        int[] span = groundSpan(field);
        if (span == null) {
            return;
        }
        int[] regions = FLOWER_REGIONS[st];
        int r = net.swordie.ms.util.Util.getRandom(regions.length - 1);
        int width = (span[1] - span[0]) / regions.length;
        int lo = span[0] + r * width, hi = lo + width;
        for (int i = 0; i < FLOWERS_PER_STRING; i++) {
            net.swordie.ms.util.Position pos = groundIn(field, lo, hi);
            if (pos != null) {
                flower(field, base() + ATTACKER_OFF[st], regions[r], pos);
            }
        }
    }

    /** Leftmost and rightmost walkable ground x of the map (sampled once). */
    private int[] groundSpan(Field field) {
        return groundSpan.computeIfAbsent(field.getId(), id -> {
            int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
            for (int i = 0; i < 120; i++) {
                net.swordie.ms.util.Position p = field.getRandomPosOnWalkableFoothold(40, true);
                if (p != null) {
                    min = Math.min(min, p.getX());
                    max = Math.max(max, p.getX());
                }
            }
            return min < max ? new int[]{min, max} : null;
        });
    }

    private static net.swordie.ms.util.Position groundIn(Field field, int lo, int hi) {
        for (int i = 0; i < 40; i++) {
            net.swordie.ms.util.Position p = field.getRandomPosOnWalkableFoothold(40, true);
            if (p != null && p.getX() >= lo && p.getX() <= hi) {
                return p;
            }
        }
        return null;
    }

    private void flower(Field field, int templateId, int attackIdx, net.swordie.ms.util.Position pos) {
        Mob mob = spawnAttacker(field, templateId, pos);
        if (mob == null) {
            return;
        }
        later(FLOWER_SWING_MS, () -> swing(field, mob, attackIdx));
        EventManager.addEvent(() -> removeAttacker(field, mob), FLOWER_LIFE_MS);
    }

    private static Mob spawnAttacker(Field field, int templateId, net.swordie.ms.util.Position pos) {
        Mob mob = field.spawnMob(templateId, pos.getX(), pos.getY(), false, ATTACKER_HP);
        if (mob != null) {
            // server-side only: no random attacks or skills, and bots don't take it for a target
            mob.setBlockEverything(true);
            mob.setBlockAttack(true);
        }
        return mob;
    }

    private static void removeAttacker(Field field, Mob mob) {
        if (mob == null || mob.getField() != field || field.getLifeByObjectID(mob.getObjectId()) == null) {
            return;
        }
        field.broadcastPacket(net.swordie.ms.connection.packet.MobPool.leaveField(mob.getObjectId(),
                net.swordie.ms.life.DeathType.ANIMATION_DEATH));
        field.removeLife(mob.getObjectId());
    }

    /** The attacker swings: its controller's client plays the attack for everyone; bots are hit by its range box. */
    private void swing(Field field, Mob mob, int attackIdx) {
        swing(field, mob, attackIdx, null);
    }

    /** @param box the bots' hit box, or null for the attack's Mob.wz range */
    private void swing(Field field, Mob mob, int attackIdx, net.swordie.ms.util.Rect box) {
        if (mob.isDead() || field.getLifeByObjectID(mob.getObjectId()) == null) {
            return;
        }
        Char controller = field.getLifeToControllers().get(mob);
        if (controller != null && !net.swordie.ms.ext.ServerHooks.isAutomated(controller)) {
            controller.write(net.swordie.ms.connection.packet.MobPool.nextAttack(mob.getObjectId(), attackIdx + 1));
        }
        if (box == null) {
            var attack = mob.getAttackById(attackIdx);
            net.swordie.ms.util.Rect r = attack == null || attack.getRange() == null
                    ? new net.swordie.ms.util.Rect(-125, -225, 125, 30) : attack.getRange();
            net.swordie.ms.util.Position at = mob.getPosition();
            box = new net.swordie.ms.util.Rect(at.getX() + r.getLeft(), at.getY() + r.getTop(),
                    at.getX() + r.getRight(), at.getY() + r.getBottom());
        }
        hitBots(field, mob, attackIdx, box);
    }

    private static void hitBots(Field field, Mob mob, int attackIdx, net.swordie.ms.util.Rect box) {
        for (Char chr : new java.util.ArrayList<>(field.getChars())) {
            net.swordie.ms.util.Position p = chr.getPosition();
            if (p != null && chr.getHP() > 0 && net.swordie.ms.ext.ServerHooks.isAutomated(chr)
                    && p.getX() >= box.getLeft() && p.getX() <= box.getRight()
                    && p.getY() >= box.getTop() && p.getY() <= box.getBottom()) {
                net.swordie.ms.socialbot.journey.JourneyBots.mobHit(chr, mob, attackIdx);
            }
        }
    }

    // --- Vengeful Spirits -----------------------------------------------------------------------------------------

    /**
     * Keeps one hidden attacker in a map while its client-drawn patterns run there: the client only plays them while
     * a monster of their refMobID is in the map (+4 Taowu's zones, +5 Hundun's flames, +8 phase 2's spirits/zones).
     */
    private void keepHolders() {
        if (getInstance() == null) {
            return;
        }
        HOLDER_OFF.forEach((st, off) -> {
            Field field = getInstance().getFields().get(fieldOf(st));
            Mob holder = holders.get(st);
            boolean alive = holder != null && field != null && field.getLifeByObjectID(holder.getObjectId()) != null;
            boolean needed = field != null && willpower > 0
                    && (st == 3 ? stage == 3 : stage < 3 && perilAlive(st));
            if (!needed) {
                if (alive) {
                    removeAttacker(field, holder);
                }
                holders.remove(st);
                return;
            }
            if (!alive) {
                int[] span = groundSpan(field);
                net.swordie.ms.util.Position pos = span == null ? null : groundIn(field, span[0], span[1]);
                Mob made = pos == null ? null : spawnAttacker(field, base() + off, pos);
                if (made != null) {
                    holders.put(st, made);
                } else {
                    holders.remove(st);
                }
            }
        });
    }

    private void vengefulSpirits() {
        if (stage != 3 || getInstance() == null || willpower <= 0) {
            return;
        }
        Field field = getInstance().getFields().get(fieldOf(3));
        Mob holder = holders.get(3);
        SpiritParade parade = spiritParades().get(base() + SPIRIT_OFF);
        if (field == null || holder == null || parade == null || field.getChars().isEmpty()) {
            return;
        }
        int[][] points = parade.presets[net.swordie.ms.util.Util.getRandom(parade.presets.length - 1)];
        int serial;
        synchronized (this) {
            serial = ++spiritSerial;
        }
        notice("Kaling's vengeful spirits are stirring. You must avoid getting devoured.");
        field.broadcastPacket(FieldPacket.fieldSkill(new KalingSpiritParade(parade.slv, serial, parade.startDelay,
                parade.types, points)));
        for (int[] p : points) {
            int type = p[3];
            if (type < 0 || type >= parade.types.length) {
                continue;
            }
            int[] t = parade.types[type];
            net.swordie.ms.util.Rect box = new net.swordie.ms.util.Rect(p[0] + t[0], p[1] + t[1], p[0] + t[2],
                    p[1] + t[3]);
            later(parade.startDelay + p[2] + t[4], () -> {
                if (stage == 3) {
                    hitBots(field, holder, t[7], box);
                }
            });
        }
    }

    /** Field skill 100029 level data: types {lt.x, lt.y, rb.x, rb.y, delay, attackCount, refMobID, refMobAttack}. */
    private record SpiritParade(int slv, int startDelay, int[][] types, int[][][] presets) {
    }

    private static volatile java.util.Map<Integer, SpiritParade> spiritParades;

    /** Skill.wz FieldSkill.img/100029, by refMobID (one level per difficulty). */
    private static java.util.Map<Integer, SpiritParade> spiritParades() {
        java.util.Map<Integer, SpiritParade> res = spiritParades;
        if (res != null) {
            return res;
        }
        synchronized (KalingInstanceHelper.class) {
            if (spiritParades != null) {
                return spiritParades;
            }
            res = new java.util.HashMap<>();
            try {
                var img = new net.swordie.ms.jwzlib.WzFile(net.swordie.ms.ServerConstants.WZ_DIR + "/Skill.wz")
                        .getChild("FieldSkill.img");
                net.swordie.ms.jwzlib.WzObject<?, ?> levels = img == null ? null : sub(sub(img, "100029"), "level");
                if (levels != null) {
                    for (net.swordie.ms.jwzlib.WzObject<?, ?> lv : levels) {
                        var info = sub(lv, "areaWarninParadeInfo");
                        var typeInfo = sub(info, "typeInfo");
                        var preset = sub(info, "preset");
                        if (typeInfo == null || preset == null) {
                            continue;
                        }
                        int[][] types = new int[size(typeInfo)][];
                        for (net.swordie.ms.jwzlib.WzObject<?, ?> t : typeInfo) {
                            java.awt.Point lt = vec(sub(t, "lt")), rb = vec(sub(t, "rb"));
                            types[Integer.parseInt(t.getName())] = new int[]{lt.x, lt.y, rb.x, rb.y,
                                    num(sub(t, "delay")), num(sub(t, "attackCount")), num(sub(t, "refMobID")),
                                    num(sub(t, "refMobAttack"))};
                        }
                        int[][][] presets = new int[size(preset)][][];
                        for (net.swordie.ms.jwzlib.WzObject<?, ?> ps : preset) {
                            int[][] pts = new int[size(ps)][];
                            for (net.swordie.ms.jwzlib.WzObject<?, ?> pt : ps) {
                                java.awt.Point xy = vec(sub(pt, "pt"));
                                pts[Integer.parseInt(pt.getName())] = new int[]{xy.x, xy.y, num(sub(pt, "delay")),
                                        num(sub(pt, "type"))};
                            }
                            presets[Integer.parseInt(ps.getName())] = pts;
                        }
                        int refMob = num(sub(info, "refMobID"));
                        res.put(refMob, new SpiritParade(Integer.parseInt(lv.getName()), num(sub(info, "startDelay")),
                                types, presets));
                    }
                }
            } catch (Exception e) {
                log.error("Could not read FieldSkill.img 100029 (Kaling's Vengeful Spirits)", e);
            }
            spiritParades = res;
            return res;
        }
    }

    private static net.swordie.ms.jwzlib.WzObject<?, ?> sub(net.swordie.ms.jwzlib.WzObject<?, ?> node, String name) {
        return node == null ? null : (net.swordie.ms.jwzlib.WzObject<?, ?>) node.getChild(name);
    }

    private static int size(net.swordie.ms.jwzlib.WzObject<?, ?> node) {
        int n = 0;
        for (net.swordie.ms.jwzlib.WzObject<?, ?> c : node) {
            n = Math.max(n, Integer.parseInt(c.getName()) + 1);
        }
        return n;
    }

    private static int num(net.swordie.ms.jwzlib.WzObject<?, ?> node) {
        return node instanceof net.swordie.ms.jwzlib.WzProperty<?> p ? p.getIntValue() : 0;
    }

    private static java.awt.Point vec(net.swordie.ms.jwzlib.WzObject<?, ?> node) {
        return node instanceof net.swordie.ms.jwzlib.WzProperty<?> p && p.getValue() instanceof java.awt.Point pt
                ? pt : new java.awt.Point();
    }

    @Override
    public void onChrHitByMobAttack(Char chr, int mobTemplateId, int attackIdx, int dmgAmount) {
        int off = mobTemplateId - base();
        if (attackIdx < 0 || off < 0 || chr.getField() == null
                || BossConstants.getKalingStage(chr.getField().getId()) == null) {
            return;
        }
        if (off == BUTTON_OFF && attackIdx == 0) {
            confuse(chr, CONFUSION_P3_LEVEL[mode()], ATTACKER_OFF[4], CONFUSION_P3_ATTACK);
            return;
        }
        if (off >= SEASON_HITS[mode()].length) {
            return;
        }
        if (off == SCROLL_OFF && attackIdx == SCROLL_ATTACK) {
            scrollUntil.put(chr.getId(), System.currentTimeMillis() + SCROLL_SECONDS * 1000L);
        } else if (off == SPIRIT_OFF && attackIdx == SPIRIT_ATTACK) {
            changeWillpower(-SPIRIT_COST, chr.getName() + " was devoured by a vengeful spirit");
        } else if (off == SPIRIT_OFF && attackIdx == GRASP_P2_ATTACK) {
            graspMove(chr);
        } else if (off == 2 && attackIdx == TRACKING_ATTACK && net.swordie.ms.ext.ServerHooks.isAutomated(chr)) {
            trackingChains(chr);
        }
        int[] row = SEASON_HITS[mode()][off];
        if (attackIdx < row.length && row[attackIdx] > 0) {
            shiftSeason(row[attackIdx] / 1000, row[attackIdx] % 1000);
        }
    }

    /** Willpower drain for everyone still sealed in Kaling's Scroll. */
    private void scrollTick() {
        long now = System.currentTimeMillis();
        int sealed = 0;
        for (var e : scrollUntil.entrySet()) {
            if (now > e.getValue()) {
                scrollUntil.remove(e.getKey());
            } else {
                sealed++;
            }
        }
        if (sealed > 0) {
            changeWillpower(-SCROLL_COST * sealed, "Sealed in Kaling's Scroll");
        }
    }

    /** Raises a season and lowers the one it preys on; a season at 0 or 1000 is lost. */
    private synchronized void shiftSeason(int season, int amount) {
        if (isBossDead() || willpower <= 0) {
            return;
        }
        int victim = (season + 2) % 3;
        seasons[season] = Math.min(1000, seasons[season] + amount);
        seasons[victim] = Math.max(0, seasons[victim] - amount);
        int lost = seasons[season] >= 1000 ? season : seasons[victim] <= 0 ? victim : -1;
        if (lost >= 0) {
            seasonLost = true;
            java.util.Arrays.fill(seasons, 500);
            sendSeasons(null);
            changeWillpower(-SEASON_LOST_COST, "The balance of " + SEASON_NAMES[lost] + " was lost");
        } else {
            sendSeasons(null);
        }
    }

    /** Shows the season gauges to one character, or to everyone in the fight (null). */
    private void sendSeasons(Char target) {
        if (getInstance() == null) {
            return;
        }
        int[] copy;
        synchronized (this) {
            copy = seasons.clone();
        }
        boolean[] flags = new boolean[3];
        for (Char chr : target != null ? java.util.List.of(target) : new java.util.ArrayList<>(getInstance().getChars())) {
            if (chr.getField() != null && BossConstants.getKalingStage(chr.getField().getId()) != null) {
                chr.write(KalingFieldPacket.fieldResult(KalingResult.seasons(copy, flags)));
            }
        }
    }

    @Override
    public void onChrDeath(Char chr) {
        int members = Math.max(1, Math.min(6, getInstance() == null ? 1 : getInstance().getChars().size()));
        changeWillpower(-WILLPOWER_COST[members - 1], chr.getName() + " fell");
        super.onChrDeath(chr);
    }

    @Override
    public boolean canReviveInFight(Char chr) {
        return willpower > 0;
    }

    /** Changes the shared Willpower (0-1000); at 0 the fight fails. */
    private synchronized void changeWillpower(int delta, String why) {
        if (isBossDead() || willpower <= 0) {
            return;
        }
        willpower = Math.max(0, Math.min(1000, willpower + delta));
        if (willpower <= 0) {
            notice("The party's Willpower is spent. The Lost Light of Four Seasons fades...");
            EventManager.addEvent(() -> {
                if (getInstance() != null) {
                    getInstance().clear();
                }
            }, 3000);
        } else {
            notice(why + ": Willpower " + (delta > 0 ? "+" : "") + delta + " (" + willpower + " / 1000)");
        }
        sendWillpower(null);
    }

    /** Shows the Willpower to one character, or to everyone in the fight (null). */
    private void sendWillpower(Char target) {
        if (getInstance() == null) {
            return;
        }
        for (Char chr : target != null ? java.util.List.of(target) : new java.util.ArrayList<>(getInstance().getChars())) {
            if (chr.getField() != null && BossConstants.getKalingStage(chr.getField().getId()) != null) {
                chr.write(KalingFieldPacket.fieldResult(KalingResult.willpower(willpower)));
            }
        }
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        super.onWarp(chr, oldField, newField);
        if (newField != null && BossConstants.getKalingStage(newField.getId()) != null) {
            int fieldId = newField.getId();
            EventManager.addEvent(() -> {
                sendWillpower(chr);
                sendSeasons(chr);
                if (chr.getField() == null || chr.getField().getId() != fieldId) {
                    return;
                }
                for (int st = 0; st < SEASON_NOTICE.length; st++) {
                    if (fieldId == fieldOf(st) && stage < 3 && perilAlive(st)) {
                        chr.write(WvsContext.weatherEffectNotice(SEASON_NOTICE[st], PERILS_FIRST, SEASON_NOTICE_MS));
                    }
                }
                if (fieldId == fieldOf(1) && stage < 3 && perilAlive(1)) {
                    sendCoils(chr);
                }
                if (fieldId == fieldOf(2) && stage < 3 && perilAlive(2)) {
                    // GMS on entering Hundun's map: time to the next amplification, his template, the bar off
                    long next = nextAmplifyAt > 0 ? nextAmplifyAt - System.currentTimeMillis() : AMPLIFY_INTERVAL_MS;
                    chr.write(KalingFieldPacket.hundunResult(HundunResult.amplifyTimer((int) Math.max(0, next))));
                    chr.write(KalingFieldPacket.hundunResult(HundunResult.init(base() + 2)));
                    chr.write(KalingFieldPacket.hundunResult(HundunResult.bar(false, 0)));
                }
                if (fieldId == fieldOf(2) && cloudUntil > System.currentTimeMillis()) {
                    Mob hundun = mobIn(chr.getField(), base() + 2);
                    if (hundun != null) {
                        chr.write(FieldPacket.fieldSkill(new HundunDarkCloud(CLOUD_SLV[mode()], cloudKey, hundun.getObjectId(),
                                cloudX, cloudY, (int) (cloudUntil - System.currentTimeMillis()))));
                    }
                }
                for (int st : new int[]{1, 3}) {
                    if (fieldId == fieldOf(st)) {
                        chr.write(KalingFieldPacket.flameEvent(KalingFlameEvent.zoneRules(zoneRules(st))));
                        java.util.List<int[]> live = new java.util.ArrayList<>();
                        for (Zone z : zones) {
                            if (z.stage() == st) {
                                live.add(new int[]{z.id(), z.type(), z.x(), z.y()});
                            }
                        }
                        if (!live.isEmpty()) {
                            chr.write(KalingFieldPacket.taowuEvent(TaowuEvent.zonesAdd(live.toArray(new int[0][]))));
                        }
                    }
                }
                if ((fieldId == fieldOf(2) && stage < 3) || (fieldId == fieldOf(3) && stage == 3)) {
                    sendFlames(chr, fieldId == fieldOf(2) ? 2 : 3);
                }
            }, 1000); // after the field is set up on the client
        }
    }

    @Override
    public void onMobDeath(Field field, Mob mob) {
        int id = mob.getTemplateId() - base();
        if (id >= 0 && id <= 2 && field.getId() == fieldOf(id)) {
            perilDied(id);
            return;
        }
        if (id == BossConstants.KALING_P2 && field.getId() == fieldOf(3)) {
            notice("Kaling absorbs the Perils' power and goes into a frenzy!");
            EventManager.addEvent(() -> {
                stage = 4;
                getInstance().warp(fieldOf(4));
                later(2000, this::startPhase3);
            }, 3000);
            return;
        }
        if (id >= BossConstants.KALING_DEATH_GRIPPED && id <= BossConstants.KALING_DEATH_GRIPPED + 2) {
            Mob boss = getBoss();
            if (boss != null && !boss.getHpTriggerBlockPercs().isEmpty()) {
                boss.getHpTriggerBlockPercs().poll();
            }
            deathGrippedDead++;
            notice("A Death-Gripped Peril falls: Kaling's defenses weaken.");
        }
        boolean bossDied = getBossIds().contains(mob.getTemplateId()) && !isBossDead();
        super.onMobDeath(field, mob);
        if (bossDied) {
            notice("Kaling has been defeated! Peace blooms again.");
            EventManager.addEvent(() -> {
                stage = 5;
                getInstance().warp(fieldOf(5));
            }, 4000);
        }
    }

    private synchronized void perilDied(int peril) {
        if (getInstance() == null || getInstance().hasProperty(DEAD_PERIL_KEY + peril)) {
            return;
        }
        getInstance().addProperty(DEAD_PERIL_KEY + peril, true);
        perilsDead++;
        if (perilsDead >= 3) {
            if (!seasonLost) {
                changeWillpower(ALL_PERILS_BONUS, "The Three Perils fell in balance");
            }
            notice("The Three Perils have fallen. Kaling appears!");
            EventManager.addEvent(() -> {
                stage = 3;
                getInstance().warp(fieldOf(3));
            }, 3000);
            return;
        }
        notice("A Peril has fallen. Choose the next season through its portal.");
        int from = fieldOf(peril);
        EventManager.addEvent(() -> {
            int next = -1;
            for (int p = 0; p < 3; p++) {
                if (!getInstance().hasProperty(DEAD_PERIL_KEY + p)) {
                    next = p;
                    break;
                }
            }
            if (next < 0 || stage >= 3) {
                return;
            }
            // whoever is still in the cleared season goes to the next Peril
            for (Char chr : new java.util.ArrayList<>(getInstance().getChars())) {
                if (chr.getField() != null && chr.getField().getId() == from) {
                    chr.warp(getInstance().getField(fieldOf(next)));
                }
            }
        }, 15000);
    }

    private void startPhase3() {
        Field field = getField();
        if (field == null || field.getId() != fieldOf(4) || getBoss() != null) {
            return;
        }
        int m = mode();
        Mob boss = field.spawnMob(base() + BossConstants.KALING_FRENZIED, -600, 398, false,
                BossConstants.KALING_P3_HP[m]);
        if (boss != null) {
            boss.addHpTriggerBlockPerc(75);
            boss.addHpTriggerBlockPerc(50);
            boss.addHpTriggerBlockPerc(25);
        }
        for (int i = 0; i < 3; i++) {
            spawnAdd(base() + BossConstants.KALING_DEATH_GRIPPED + i,
                    new net.swordie.ms.util.Position(DEATH_GRIPPED_POS[i][0], DEATH_GRIPPED_POS[i][1]),
                    BossConstants.KALING_DEATH_GRIPPED_HP[m]);
        }
    }
}
