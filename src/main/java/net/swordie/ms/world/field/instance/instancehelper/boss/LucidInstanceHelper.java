package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.enums.WeatherEffNoticeType;
import java.util.stream.Collectors;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.packet.field.LucidFieldPacket;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.boss.lucid.Butterfly;
import net.swordie.ms.life.mob.boss.lucid.ButterflyType;
import net.swordie.ms.life.mob.boss.lucid.LucidHardPhase3;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Lucid (maplestorywiki.net, GMS).
 * <ul>
 *     <li>Phase 1: small butterflies keep appearing in the background (faster below 75%, 50% and 25% of her HP); past
 *     a limit Lucid sends them all at the players (Butterfly Swarm). The Trumpet Statues at both ends clear them:
 *     each life bar (10% of her HP) she loses gives them one use, up to 3.</li>
 *     <li>Phase 2: Nightmare Golems keep falling; each breaks a stained-glass platform for a while.</li>
 *     <li>Her golems and mushrooms get their GMS HP (the v265 data has old or hidden values).</li>
 * </ul>
 * Her attacks (flowers, fairy dust, teleport, dragon, bomb) are her skills in the game data.
 */
public class LucidInstanceHelper extends BossMechanicsHelper {

    private static final int SWARM_AT = 40;
    private static final int NOTICE_MS = 2000; // GMS
    private static final int MAX_STATUE_USES = 3;
    private static final ButterflyType[] SMALL = {ButterflyType.PurpleSmall, ButterflyType.PinkSmall,
            ButterflyType.BlueSmall};
    private static final String[] GLASS = {"Bblue1", "Bblue2", "Bblue3", "Bred1", "Bred2", "Bred3", "Mred2", "Mred3",
            "Myellow1", "Myellow2", "Myellow3"};
    private static final long GLASS_BACK_MS = 10_000;
    private static final long P2_GOLEM_MS = 20_000;
    private static final int MAX_P2_GOLEMS = 3;

    /** maplestorywiki.net Nightmare Golem / Nightmare Mushroom HP (Easy, Normal, Hard). */
    private static final Map<Integer, Long> ADD_HP = DataTable.read("lucid-add-hp").rows().stream()
            .collect(Collectors.toUnmodifiableMap(r -> r.i("mob"), r -> r.l("hp")));

    private volatile int butterflies = 0;
    private volatile int statueUses = 0;
    private volatile int barsLost = 0;
    private long nextButterfly = 0;
    private long nextGolem = 0;

    @Override
    public void onMobDamaged(Char attacker, Mob mob, long damage, long oldHp, long newHp) {
        super.onMobDamaged(attacker, mob, damage, oldHp, newHp);
        if (mob.getTemplateId() == BossConstants.LUCID_HARD_TEMPLATE_ID_3) {
            LucidHardPhase3.onDamaged(mob, newHp);
        }
    }

    private int mode() {
        int enter = getInstance() == null ? 0 : getInstance().getEnterFieldId();
        return enter == BossConstants.LUCID_EASY_FIELD ? 0 : enter == 450004400 ? 2 : 1; // hardcode-ok
    }

    private int p1Field() {
        int enter = getInstance().getEnterFieldId();
        return enter + BossConstants.lucidStep(enter);
    }

    private int p2Field() {
        int enter = getInstance().getEnterFieldId();
        return enter + 3 * BossConstants.lucidStep(enter);
    }

    @Override
    protected Field getField() {
        if (getInstance() == null) {
            return null;
        }
        Field p2 = getInstance().getField(p2Field());
        if (p2 != null && !p2.getChars().isEmpty()) {
            return p2;
        }
        return getInstance().getField(p1Field());
    }

    @Override
    protected Set<Integer> getBossIds() {
        // the fight's last Lucid (phase 2, Hard phase 3): the mechanics run until she falls
        return Set.of(BossConstants.LUCID_TEMPLATE_ID_2, BossConstants.LUCID_EASY_TEMPLATE_ID_2,
                BossConstants.LUCID_HARD_TEMPLATE_ID_3);
    }

    private Mob p1Lucid(Field field) {
        for (Mob mob : new ArrayList<>(field.getMobs())) {
            int id = mob.getTemplateId();
            if (!mob.isDead() && (id == BossConstants.LUCID_TEMPLATE_ID_1 || id == BossConstants.LUCID_HARD_TEMPLATE_ID_1
                    || id == BossConstants.LUCID_EASY_TEMPLATE_ID_1)) {
                return mob;
            }
        }
        return null;
    }

    @Override
    protected void startMechanics() {
        every(1000, this::tick);
    }

    private void tick() {
        Field field = getField();
        if (field == null || field.getChars().isEmpty()) {
            return;
        }
        if (field.getId() == p2Field()) {
            p2Tick(field);
            return;
        }
        Mob lucid = p1Lucid(field);
        if (lucid == null || lucid.getMaxHp() <= 0) {
            return;
        }
        double hp = lucid.getHp() / (double) lucid.getMaxHp();
        int bars = (int) ((1 - hp) * 10);
        if (bars > barsLost) {
            statueUses = Math.min(MAX_STATUE_USES, statueUses + (bars - barsLost));
            barsLost = bars;
            sendStatues(field);
            if (barsLost == 3) {
                bossNotice(WeatherEffNoticeType.BossLucid, "Lucid is calling on her true power!", NOTICE_MS);
            } else if (barsLost == 5) {
                bossNotice(WeatherEffNoticeType.BossLucid, "Lucid is about to unleash her power!", NOTICE_MS);
            } else if (barsLost == 8) {
                bossNotice(WeatherEffNoticeType.BossLucid, "Lucid is enraged!", NOTICE_MS);
            }
        }
        long now = System.currentTimeMillis();
        if (now < nextButterfly) {
            return;
        }
        long gap = hp > 0.75 ? 3000 : hp > 0.5 ? 2500 : hp > 0.25 ? 2000 : 1500;
        nextButterfly = now + gap;
        Position pos = new Position(Util.getRandom(0, 1900), Util.getRandom(-600, 150));
        Butterfly b = new Butterfly(SMALL[Util.getRandom(SMALL.length - 1)], pos);
        field.broadcastPacket(LucidFieldPacket.lucidButterflyCreated(List.of(b)));
        if (++butterflies >= SWARM_AT) {
            notice("Lucid sends her butterflies at you!");
            field.broadcastPacket(LucidFieldPacket.lucidButterflyActionAttack(butterflies, 0));
            butterflies = 0;
        }
    }

    private void p2Tick(Field field) {
        long now = System.currentTimeMillis();
        if (nextGolem == 0) {
            nextGolem = now + P2_GOLEM_MS;
            return;
        }
        if (now < nextGolem) {
            return;
        }
        nextGolem = now + P2_GOLEM_MS;
        int golem = new int[]{8880187, 8880161, 8880181}[mode()]; // hardcode-ok
        if (countAlive(golem) < MAX_P2_GOLEMS) {
            spawnAdd(golem, randomGround(), ADD_HP.get(golem));
        }
    }

    private void sendStatues(Field field) {
        field.broadcastPacket(LucidFieldPacket.lucidStatueStateChange(false, statueUses, statueUses > 0));
    }

    @Override
    public void onMobSpawn(Field field, Mob mob) {
        super.onMobSpawn(field, mob);
        Long hp = ADD_HP.get(mob.getTemplateId());
        if (hp != null && mob.getMaxHp() != hp) {
            mob.getForcedMobStat().setMaxHP(hp);
            mob.setMaxHp(hp);
            mob.setHp(hp);
        }
        if (getInstance() == null || field.getId() != p2Field() || hp == null || !isGolem(mob.getTemplateId())) {
            return;
        }
        // phase 2: the golem breaks a platform, which comes back after a while
        String glass = GLASS[Util.getRandom(GLASS.length - 1)];
        field.broadcastPacket(LucidFieldPacket.lucidStainedGlassBreak(List.of(glass)));
        EventManager.addEvent(() -> {
            if (!field.getChars().isEmpty()) {
                field.broadcastPacket(LucidFieldPacket.lucidStainedGlassOnOff(true, List.of(glass)));
            }
        }, GLASS_BACK_MS);
    }

    private boolean isGolem(int id) {
        return id == 8880161 || id == 8880171 || id == 8880181 || id == 8880183 || id == 8880187; // hardcode-ok
    }

    /** Bots sound a statue once the butterflies are three quarters of the way to a swarm. */
    @Override
    public BotHint botHint(Char chr) {
        Field field = getField();
        if (field == null || chr.getField() != field || field.getId() != p1Field() || statueUses <= 0
                || butterflies < SWARM_AT * 3 / 4 || chr.getPosition() == null) {
            return null;
        }
        return new BotHint(chr.getPosition().getX(), chr.getPosition().getY(), () -> onStatueUse(chr),
                "sounds a Trumpet Statue");
    }

    /** A player sounded a Trumpet Statue (LUCID_STATUE_USE_REQUEST): the small butterflies go. */
    public void onStatueUse(Char chr) {
        Field field = getField();
        if (field == null || chr.getField() != field || field.getId() != p1Field() || statueUses <= 0) {
            return;
        }
        statueUses--;
        butterflies = 0;
        field.broadcastPacket(LucidFieldPacket.lucidButterflyActionRemove());
        sendStatues(field);
        notice(chr.getName() + " sounds the Trumpet Statue. The butterflies scatter!");
    }
}
