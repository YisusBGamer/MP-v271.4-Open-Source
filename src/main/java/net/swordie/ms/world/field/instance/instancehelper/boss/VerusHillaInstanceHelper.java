package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.enums.WeatherEffNoticeType;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.connection.packet.field.VerusHillaFieldPacket;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.skill.MobSkillID;
import net.swordie.ms.loaders.containerclasses.MobSkillInfo;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Verus Hilla (maplestorywiki.net, GMS): four HP bars, and a death count of Spirit Stones.
 * <ul>
 *     <li>75%: her second set of attacks; 50%: Necro Lotus; 25%: Necro Damien (her summons are her own skills in
 *     the game data, Skill.wz MobSkill 201, as are the Greed Echoes).</li>
 *     <li>Spirit Stones: each player has 5 (the death count). A Red Thread (the Evil Hand) that catches a player
 *     turns one red and lights a candle; with every stone red the player is out. When all candles are lit a Soul
 *     Altar appears: pressing the Interact key on it enough times turns every red stone green again.</li>
 *     <li>Potions and healing are blocked (Potion Block) except for a while after the altar breaks, which also gives
 *     everyone +15% final damage for 6 seconds (Altar Destruction, skill 80002544).</li>
 *     <li>The Red Threads (Evil Hand) and the poison mist (Mold) are invisible, invincible monsters with their own
 *     skills in the game data (246, 248), placed at the start.</li>
 *     <li>Soul Split: when the hourglass fills (3:00 Normal / 2:30 Hard, 30 / 25 s less per broken bar, at least
 *     2:00 / 1:40) she uses it (mob skill 247) and the red stones are gone for good; the candles start over.</li>
 * </ul>
 */
public class VerusHillaInstanceHelper extends BossMechanicsHelper {

    private static final int ALTAR_TAPS = 10; // GMS
    // her map notices (GMS): the candles at the start, the altar, the necro summons
    private static final int CANDLES_NOTICE_MS = 8000, ALTAR_NOTICE_MS = 6000, NECRO_NOTICE_MS = 5000;
    private static final int ALTAR_Y = 266;
    private static final int ALTAR_DESTRUCTION = 80002544; // indiePMdR 15, time 6
    private static final long POTION_FREE_MS = 15_000;
    private static final int EVIL_HAND_MOB_3 = 8880401;
    private static final int EVIL_HAND_MOB_2 = 8880406;
    private static final int EVIL_HAND_HARD_MOB = 8880411;
    private static final int MOLD_HARD_MOB = 8880412;
    private static final int EVIL_HAND_MOB = 8880451;
    private static final int MOLD_NORMAL_MOB = 8880452;
    private static final int NECRO_DAMIEN_MOB = 8880454;
    private static final int NECRO_LOTUS_MOB = 8880461;
    private static final int NECRO_LOTUS_MOB_2 = 8880462;

    private volatile long potionFreeUntil = 0;

    /** Red (taken) stones per player; the green ones are the death count left. */
    private final Map<Integer, Integer> red = new ConcurrentHashMap<>();
    private final Map<Integer, String> lastSent = new ConcurrentHashMap<>();
    private volatile int candles = 0;
    private volatile int lit = 0;
    private volatile int altarTaps = 0; // > 0 while the Soul Altar is up
    private volatile int altarX = 0;
    private volatile int bars = 0;
    // Soul Split comes when the hourglass runs out (the skill's own interval would cast it too early)
    private static final int SOUL_SPLIT_SKILL = 247, SOUL_SPLIT_LEVEL = 1;
    private static final long SPLIT_FALLBACK_MS = 6_000, ALTAR_RESPAWN_MS = 2_300, ALTAR_HIT_RANGE = 120;
    private volatile long splitAt = 0, splitFallbackAt = 0, altarBackAt = 0;
    /** The most stones each player has had this fight: the ones lost since show as gone. */
    private final Map<Integer, Integer> startStones = new ConcurrentHashMap<>();

    @Override
    protected Set<Integer> getBossIds() {
        return Set.of(BossConstants.VERUS_HILLA, BossConstants.VERUS_HILLA_HARD);
    }

    private boolean isEvilHand(int templateId) {
        return templateId == EVIL_HAND_MOB || templateId == EVIL_HAND_HARD_MOB || templateId == EVIL_HAND_MOB_2 || templateId == EVIL_HAND_MOB_3;
    }

    private boolean isHard() {
        return getBoss() != null && getBoss().getTemplateId() == BossConstants.VERUS_HILLA_HARD;
    }

    @Override
    protected void startMechanics() {
        bossNotice(WeatherEffNoticeType.Labyrinth_2,
                "Hilla will periodically cut down the Soul Candles. Make sure you don't lose your soul when she does!", CANDLES_NOTICE_MS);
        resetCandles();
        sendHourglass();
        if (getBoss() != null) {
            getBoss().addBlockedSkill(MobSkillID.TrueHillaScreenSwipe, SOUL_SPLIT_LEVEL);
        }
        boolean hard = isHard();
        spawnAdd(hard ? EVIL_HAND_HARD_MOB : EVIL_HAND_MOB, new net.swordie.ms.util.Position(0, 266), 0); // Evil Hand: the Red Threads
        spawnAdd(hard ? MOLD_HARD_MOB : MOLD_NORMAL_MOB, new net.swordie.ms.util.Position(0, 266), 0); // Mold: the poison mist
        every(1000, this::tick);
        every(5000, this::blockPotions);
    }

    /** Potion Block on everyone, except right after the altar broke. */
    private void blockPotions() {
        Field field = getField();
        if (field == null || System.currentTimeMillis() < potionFreeUntil) {
            return;
        }
        for (Char chr : field.getChars()) {
            if (chr.isDead()) {
                continue;
            }
            Option o = new Option(1, MobSkillID.StopPortion.getVal(), 10);
            o.slv = 1;
            var tsm = chr.getTemporaryStatManager();
            tsm.putCharacterStatValueFromMobSkill(CharacterTemporaryStat.StopPortion, o);
            tsm.sendSetStatPacket();
        }
    }

    private void tick() {
        long now = System.currentTimeMillis();
        if (splitAt > 0 && now >= splitAt && getBoss() != null) {
            splitAt = 0;
            splitFallbackAt = now + SPLIT_FALLBACK_MS;
            getBoss().removeBlockedSkill(MobSkillID.TrueHillaScreenSwipe, SOUL_SPLIT_LEVEL);
            getBoss().setForcedSkill(getBoss().getSkillByMobSkillInfo(SOUL_SPLIT_SKILL, SOUL_SPLIT_LEVEL));
        }
        if (splitFallbackAt > 0 && now >= splitFallbackAt) {
            soulSplit(); // the skill never came through
        }
        altarTick(now);
        if (crossedThreshold(75)) {
            bars++;
            notice("Verus Hilla's second soul bar breaks. Her scythe grows wilder!");
        }
        if (crossedThreshold(50)) {
            bars++;
            bossNotice(WeatherEffNoticeType.Labyrinth_2, "Hilla can be heard tugging Lotus's departed soul from the depths of death.", NECRO_NOTICE_MS);
        }
        if (crossedThreshold(25)) {
            bars++;
            bossNotice(WeatherEffNoticeType.Labyrinth_2, "Hilla can be heard tugging Damien's departed soul from the depths of death.", NECRO_NOTICE_MS);
        }
        sendStonesIfChanged();
    }

    private int green(Char chr) {
        return Math.max(0, chr.getDeathCount() - red.getOrDefault(chr.getId(), 0));
    }

    private void sendStonesIfChanged() {
        Field field = getField();
        if (field == null) {
            return;
        }
        Map<Integer, Integer> counts = new LinkedHashMap<>();
        boolean changed = false;
        for (Char chr : field.getChars()) {
            int r = red.getOrDefault(chr.getId(), 0);
            int total = Math.max(0, chr.getDeathCount());
            int start = startStones.merge(chr.getId(), total, Math::max);
            String key = total + "/" + r + "/" + start;
            counts.put(chr.getId(), green(chr));
            if (!key.equals(lastSent.get(chr.getId()))) {
                lastSent.put(chr.getId(), key);
                List<Boolean> stones = new ArrayList<>();
                for (int i = 0; i < total; i++) {
                    stones.add(i >= r);
                }
                for (int i = total; i < start; i++) {
                    stones.add(null); // lost for good
                }
                chr.write(VerusHillaFieldPacket.spiritStones(stones));
                changed = true;
            }
        }
        if (changed) {
            field.broadcastPacket(VerusHillaFieldPacket.deathCounts(counts));
        }
    }

    private void resetCandles() {
        Field field = getField();
        if (field == null) {
            return;
        }
        int stones = 0;
        for (Char chr : field.getChars()) {
            stones += green(chr);
        }
        candles = Math.max(1, (int) Math.round(stones * 0.5));
        lit = 0;
        field.broadcastPacket(VerusHillaFieldPacket.candles(candles));
        field.broadcastPacket(VerusHillaFieldPacket.litCandles(0));
    }

    private void sendHourglass() {
        Field field = getField();
        if (field == null) {
            return;
        }
        boolean hard = getBoss() != null && getBoss().getTemplateId() == BossConstants.VERUS_HILLA_HARD;
        int seconds = hard ? Math.max(100, 150 - 25 * bars) : Math.max(120, 180 - 30 * bars);
        splitAt = System.currentTimeMillis() + seconds * 1000L;
        field.broadcastPacket(VerusHillaFieldPacket.hourglass(seconds * 1000));
    }

    /** The Necro Lotus / Necro Damien her data summons have no HP of their own in the v265 data. */
    private static final Set<Integer> NECROS = Set.of(8880403, 8880404, 8880408, 8880409, 8880413, 8880414, 8880453,
            NECRO_DAMIEN_MOB, NECRO_LOTUS_MOB, NECRO_LOTUS_MOB_2);

    @Override
    public void onMobSpawn(Field field, Mob mob) {
        super.onMobSpawn(field, mob);
        if (NECROS.contains(mob.getTemplateId())) {
            boolean hard = getBoss() != null && getBoss().getTemplateId() == BossConstants.VERUS_HILLA_HARD;
            long hp = hard ? 3_000_000_000_000L : 1_000_000_000_000L;
            mob.getForcedMobStat().setMaxHP(hp);
            mob.setMaxHp(hp);
            mob.setHp(hp);
        }
    }

    @Override
    public void onChrHitByMob(Char chr, int mobTemplateId, int dmgAmount) {
        super.onChrHitByMob(chr, mobTemplateId, dmgAmount);
        // a player's client reports the catch itself (VERUS_HILLA_RED_THREAD_CATCH); a bot has no client, so the Evil
        // Hand's hit on it stands for the catch
        if (!net.swordie.ms.socialbot.SocialBots.isBot(chr) || !isEvilHand(mobTemplateId)) {
            return;
        }
        Mob hand = null;
        Field field = getField();
        for (Mob m : field == null ? List.<Mob>of() : new ArrayList<>(field.getMobs())) {
            if (isEvilHand(m.getTemplateId())) {
                hand = m;
                break;
            }
        }
        takeStone(chr, hand, chr.getPosition(), null);
    }

    private final Map<Integer, Long> lastCatch = new ConcurrentHashMap<>();
    private volatile int lastThreadLevel = 1;

    /**
     * A Red Thread caught the player (their client's VERUS_HILLA_RED_THREAD_CATCH): the thread's Stun (246's
     * affectedOtherSkill, 123 level 83) and one Spirit Stone turns red.
     */
    public void onRedThreadCatch(Char chr, int handObjectId, net.swordie.ms.util.Position pos, List<Integer> fragments) {
        Field field = getField();
        if (field == null || chr.getField() != field || chr.isDead()) {
            return;
        }
        Mob hand = field.getLifeByObjectID(handObjectId) instanceof Mob m && isEvilHand(m.getTemplateId()) ? m : null;
        if (hand == null) {
            return;
        }
        long now = System.currentTimeMillis();
        Long last = lastCatch.put(chr.getId(), now);
        if (last != null && now - last < 1000) {
            return; // the same catch reported twice
        }
        net.swordie.ms.life.mob.skill.MobSkillModule.onMobSkillHit(chr, handObjectId,
                MobSkillID.TrueHillaRedStringHand.getVal(), lastThreadLevel);
        takeStone(chr, hand, pos, fragments);
    }

    /** One of the player's Spirit Stones turns red; the soul's fragments are shown falling where they were caught. */
    private void takeStone(Char chr, Mob hand, net.swordie.ms.util.Position pos, List<Integer> fragments) {
        if (isBossDead() || chr.getDeathCount() <= 0) {
            return;
        }
        int r = red.merge(chr.getId(), 1, Integer::sum);
        chr.chatMessage("A Red Thread takes one of your Spirit Stones!");
        Field field = getField();
        if (field != null) {
            if (hand != null) {
                int n = fragments != null && !fragments.isEmpty() ? fragments.size() : Util.getRandom(4, 6);
                if (fragments == null || fragments.isEmpty()) {
                    fragments = new ArrayList<>();
                    for (int i = 0; i < n; i++) {
                        fragments.add(pos.getX() + Util.getRandom(-100, 100));
                    }
                }
                List<net.swordie.ms.util.Rect> rects = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    rects.add(new net.swordie.ms.util.Rect(Util.getRandom(-100, 150), -80, Util.getRandom(10, 15) * 5, 640));
                }
                field.broadcastPacket(VerusHillaFieldPacket.spirit(hand.getObjectId(), chr.getId(), pos,
                        lastThreadLevel, 250 * n, rects, fragments));
            }
        }
        if (field != null && lit < candles && altarTaps == 0) {
            lit++;
            field.broadcastPacket(VerusHillaFieldPacket.litCandles(lit));
            if (lit >= candles) {
                placeAltar(field);
                bossNotice(WeatherEffNoticeType.Labyrinth_2,
                        "Collect the soul by reaching the altar and mashing the Harvesting key before Hilla grabs it.", ALTAR_NOTICE_MS);
            }
        }
        if (r >= chr.getDeathCount()) {
            // every stone red: the player is out
            chr.setDeathCount(0);
            red.remove(chr.getId());
            chr.chatMessage("All your Spirit Stones are red. Your soul is lost.");
            if (getInstance() != null) {
                getInstance().removeChar(chr);
            }
        }
        sendStonesIfChanged();
    }

    private void placeAltar(Field field) {
        altarTaps = ALTAR_TAPS;
        altarX = Util.getRandom(-700, 700);
        field.broadcastPacket(VerusHillaFieldPacket.altarCreate(altarX, ALTAR_Y, ALTAR_TAPS));
    }

    /** Hilla walking into the Soul Altar breaks it; a new one rises elsewhere a moment later (GMS). */
    private void altarTick(long now) {
        Field field = getField();
        Mob boss = getBoss();
        if (field == null || boss == null || isBossDead()) {
            return;
        }
        if (altarBackAt > 0 && now >= altarBackAt) {
            altarBackAt = 0;
            if (lit >= candles && altarTaps == 0) {
                placeAltar(field);
            }
        }
        if (altarTaps > 0 && boss.getPosition() != null && Math.abs(boss.getPosition().getX() - altarX) <= ALTAR_HIT_RANGE) {
            altarTaps = 0;
            field.broadcastPacket(VerusHillaFieldPacket.altarRemove(false));
            altarBackAt = now + ALTAR_RESPAWN_MS;
        }
    }

    @Override
    public BotHint botHint(Char chr) {
        if (altarTaps <= 0 || isBossDead()) {
            return null;
        }
        return new BotHint(altarX, ALTAR_Y, () -> onAltarTap(chr), "presses the Soul Altar");
    }

    /** A press of the Interact key on the Soul Altar (VERUS_HILLA_SOUL_ALTAR_REQUEST; the client checks the area). */
    public void onAltarTap(Char chr) {
        if (altarTaps <= 0 || isBossDead()) {
            return;
        }
        altarTaps--;
        Field field = getField();
        if (field == null) {
            return;
        }
        field.broadcastPacket(VerusHillaFieldPacket.altarTapsLeft(altarTaps));
        if (altarTaps == 0) {
            field.broadcastPacket(VerusHillaFieldPacket.altarRemove(true));
            red.clear();
            potionFreeUntil = System.currentTimeMillis() + POTION_FREE_MS;
            for (Char c : field.getChars()) {
                var tsm = c.getTemporaryStatManager();
                tsm.removeStat(CharacterTemporaryStat.StopPortion);
                Option fd = new Option();
                fd.nOption = 15;
                fd.rOption = ALTAR_DESTRUCTION;
                fd.tOption = 6;
                tsm.putCharacterStatValue(CharacterTemporaryStat.IndiePMdR, fd);
                tsm.sendSetStatPacket();
            }
            notice("The Soul Altar breaks! Your souls return.");
            resetCandles();
            sendStonesIfChanged();
        }
    }

    /**
     * Skill.wz MobSkill 246 (TrueHillaRedStringHand) levels 1 and 3-7, variableAttack/pos: seven hands at y -260, the
     * i-th coming down after 250 * (i + 1) ms, at these x in this order per level.
     */
    private static final Map<Integer, int[]> THREAD_X = Map.of(
            1, new int[]{0, 280, -560, 560, -280, -840, 840},
            3, new int[]{560, -280, -840, 0, 280, 840, -560},
            4, new int[]{-280, 560, -560, 280, -840, 840, 0},
            5, new int[]{840, -280, -560, 280, 560, 0, -840},
            6, new int[]{280, 560, -280, 0, -840, 840, -560},
            7, new int[]{-560, 280, 560, -280, -840, 0, 840});
    /** The same data's strings (start x, end x; y -80 to 560) of hands 0/3, 1/4 and 2/5/6. */
    private static final int[][][] THREAD_STRINGS = {
            {{-75, -25}, {13, -47}, {83, 155}, {83, 155}},
            {{-81, 9}, {-59, -79}, {-25, -12}, {123, 154}, {138, 84}},
            {{-78, -50}, {-13, -63}, {42, 123}, {75, 57}, {133, 137}}};
    private static final int[] HAND_STRINGS = {0, 1, 2, 0, 1, 2, 2};

    /** The Evil Hand's skill: its level's seven hands, each with its Red Threads (start, then end minus start). */
    private void sendRedThreads(Mob hand, int slv) {
        Field field = getField();
        int[] xs = THREAD_X.get(slv);
        if (field == null || xs == null) {
            return;
        }
        lastThreadLevel = slv;
        List<int[]> hands = new ArrayList<>();
        List<List<net.swordie.ms.util.Rect>> rects = new ArrayList<>();
        for (int i = 0; i < xs.length; i++) {
            hands.add(new int[]{xs[i], -260, (i + 1) * 250});
            List<net.swordie.ms.util.Rect> rs = new ArrayList<>();
            for (int[] s : THREAD_STRINGS[HAND_STRINGS[i]]) {
                rs.add(new net.swordie.ms.util.Rect(s[0], -80, s[1] - s[0], 560 - (-80)));
            }
            rects.add(rs);
        }
        field.broadcastPacket(VerusHillaFieldPacket.blackHand(hand.getObjectId(), slv, hands, rects));
    }

    @Override
    public void onMobSkill(Mob mob, MobSkillInfo msi, MobSkillID msId) {
        super.onMobSkill(mob, msi, msId);
        if (msId == MobSkillID.TrueHillaRedStringHand && isEvilHand(mob.getTemplateId())) {
            sendRedThreads(mob, msi.getLevel());
            return;
        }
        if (msId != MobSkillID.TrueHillaScreenSwipe || !getBossIds().contains(mob.getTemplateId())) {
            return;
        }
        soulSplit();
    }

    /** Soul Split: the red stones are gone for good, the candles start over and the hourglass again. */
    private void soulSplit() {
        splitFallbackAt = 0;
        if (getBoss() != null) {
            getBoss().addBlockedSkill(MobSkillID.TrueHillaScreenSwipe, SOUL_SPLIT_LEVEL);
        }
        Field field = getField();
        if (field == null) {
            return;
        }
        for (Char chr : field.getChars()) {
            int r = red.getOrDefault(chr.getId(), 0);
            if (r > 0) {
                chr.setDeathCount(Math.max(0, chr.getDeathCount() - r));
            }
        }
        red.clear();
        if (altarTaps > 0) {
            altarTaps = 0;
            altarBackAt = 0;
            field.broadcastPacket(VerusHillaFieldPacket.altarRemove(false));
        }
        notice("Soul Split! The taken Spirit Stones are lost.");
        resetCandles();
        sendHourglass();
        sendStonesIfChanged();
    }
}
