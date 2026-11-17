package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.enums.WeatherEffNoticeType;
import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.packet.MobPool;
import net.swordie.ms.connection.packet.field.DemianFieldPacket;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.DeathType;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.boss.demian.stigma.DemianStigma;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Damien (maplestorywiki.net, GMS), Normal and Hard; the same rules in both:
 * <ul>
 *     <li>Brand: every 30 seconds in phase 1 and every 20 seconds in phase 2 one player gets a stack (the one with the
 *     most, the fewest, or a random one). At 7 a player dies and Damien gains a Crystal (DemianStigma).</li>
 *     <li>Phase 2 starts when phase 1 Damien falls (30% of his HP) or when his Crystals reach 7; then the HP he had
 *     left goes on into phase 2.</li>
 * </ul>
 */
public class DemianInstanceHelper extends BossMechanicsHelper {

    public static final String CARRY_HP_KEY = "demianCarryHp";
    private static final int MAX_CRYSTALS = 7;
    private static final int BRAND_RULES = 4; // most-branded, least-branded, top damage, random

    private volatile int crystals = 0;
    private volatile boolean phase2 = false;
    private long nextBrand = 0;

    private boolean isHard() {
        return getInstance() != null && getInstance().getEnterFieldId() == FieldConstants.DEMIAN_HARD_FIELD;
    }

    private int p1Field() {
        return getInstance().getEnterFieldId();
    }

    private int p1Mob() {
        return isHard() ? BossConstants.DEMIAN_HARD_TEMPLATE_ID_1 : BossConstants.DEMIAN_NORMAL_TEMPLATE_ID_1;
    }

    @Override
    protected Field getField() {
        if (getInstance() == null) {
            return null;
        }
        return getInstance().getField(p1Field() + (phase2 ? 40 : 0));
    }

    @Override
    protected Set<Integer> getBossIds() {
        return Set.of(BossConstants.DEMIAN_NORMAL_TEMPLATE_ID_2, BossConstants.DEMIAN_HARD_TEMPLATE_ID_2);
    }

    @Override
    protected void startMechanics() {
        nextBrand = System.currentTimeMillis() + BossConstants.DEMIAN_PASSIVE_STIGMA_TIME;
        every(1000, this::tick);
    }

    private void tick() {
        Field field = getField();
        if (field == null) {
            return;
        }
        if (!phase2 && field.getLifeByTemplateId(p1Mob()) == null && getInstance().getField(p1Field() + 40) != null
                && !getInstance().getField(p1Field() + 40).getChars().isEmpty()) {
            phase2 = true; // phase 1 Damien fell and die_demianP1 moved everyone on
            nextBrand = System.currentTimeMillis() + 20_000;
            return;
        }
        long now = System.currentTimeMillis();
        if (now < nextBrand) {
            return;
        }
        long interval = phase2 ? 20_000 : BossConstants.DEMIAN_PASSIVE_STIGMA_TIME;
        nextBrand = now + interval;
        List<Char> chars = getLivingChars();
        if (chars.isEmpty()) {
            return;
        }
        Char target;
        String who;
        switch (Util.getRandom(BRAND_RULES - 1)) { // getRandom is inclusive
            case 0 -> {
                target = chars.stream().max(Comparator.comparingInt(DemianStigma::getStigmaCount)).get();
                who = "the most-branded opponent";
            }
            case 1 -> {
                target = chars.stream().min(Comparator.comparingInt(DemianStigma::getStigmaCount)).get();
                who = "the least-branded opponent";
            }
            case 2 -> {
                Mob boss = getBoss();
                Char top = boss == null ? null : boss.getMostDamageChar();
                target = top != null && chars.contains(top) ? top : Util.getRandomFromCollection(chars);
                who = "the most threatening opponent";
            }
            default -> {
                target = Util.getRandomFromCollection(chars);
                who = "a random opponent";
            }
        }
        // GMS: the notice names the rule and stays up until the next Brand
        bossNotice(WeatherEffNoticeType.BossDamien, "Damien is targeting " + who + " for his Brand.", (int) interval);
        DemianStigma.incStigma(target);
    }

    /** Bots burn their Brand at the Incineration pillar once it has 4 stacks. */
    @Override
    public BotHint botHint(Char chr) {
        Field f = chr.getField();
        if (f == null || DemianStigma.getStigmaCount(chr) < 4) {
            return null;
        }
        var pillar = f.getLifes().values().stream()
                .filter(l -> l instanceof net.swordie.ms.life.mob.boss.demian.stigma.DemianStigmaIncinerateObject)
                .findFirst().orElse(null);
        if (pillar == null || pillar.getPosition() == null) {
            return null;
        }
        return new BotHint(pillar.getPosition().getX(), pillar.getPosition().getY(), () -> burnBrand(chr),
                "burns its Brand at the pillar");
    }

    /** A character uses the Incineration pillar (as STIGMA_DELIVERY_REQUEST's success): its Brand is gone. */
    public void burnBrand(Char chr) {
        Field f = chr.getField();
        if (f == null) {
            return;
        }
        var pillar = f.getLifes().values().stream()
                .filter(l -> l instanceof net.swordie.ms.life.mob.boss.demian.stigma.DemianStigmaIncinerateObject)
                .findFirst().orElse(null);
        if (pillar == null) {
            return;
        }
        f.removeLife(pillar);
        DemianStigma.resetStigma(chr);
        f.broadcastPacket(DemianFieldPacket.stigmaEffect(chr.getId(), false));
    }

    // The clear room's Restrained Sword of Destruction: it carries Damien's reward (Normal, Hard).
    private static final int SWORD_NORMAL = 8950111;
    private static final int SWORD_HARD = 8950112;
    private static final long SWORD_HP = 100_000;

    /**
     * Phase 2 Damien falls: the fight is over. His Shadow Zone goes with him, the fallen Damien he revives into
     * stays for its fall, the Brand stacks are gone. As GMS, the party is then taken to the clear room and his reward
     * comes out of the Restrained Sword of Destruction there; they break it, loot it and leave by the Gate.
     */
    @Override
    protected void onBossDeath(Mob boss) {
        Field field = boss.getField();
        if (field == null) {
            return;
        }
        removeMobs(field, BossConstants.DEMIAN_SHADOW_ZONE);
        addTimer(EventManager.addEvent(() -> removeMobs(field, BossConstants.DEMIAN_FALLEN), 3000));
        for (Char c : new ArrayList<>(field.getChars())) {
            if (DemianStigma.getStigmaCount(c) > 0) {
                DemianStigma.resetStigma(c);
            }
        }
        // Damien's drops moved to the sword (Mob.die skips his own drop); hand them to the sword in the clear room.
        final Set<net.swordie.ms.life.drop.DropInfo> loot = new java.util.HashSet<>(boss.getDrops());
        final int clearFieldId = p1Field() + 80;
        Field clearRoom = getInstance().getField(clearFieldId);
        if (clearRoom == null) {
            return; // the clear room is missing: leave Damien's body where it fell so the party still loots there
        }
        net.swordie.ms.world.field.Portal sp = clearRoom.getInfo().getPortals().stream()
                .filter(p -> "sp".equals(p.getName())).findFirst().orElse(null);
        int x = sp != null ? sp.getX() : 0;
        int y = sp != null ? sp.getY() : 0;
        Mob sword = clearRoom.spawnMob(isHard() ? SWORD_HARD : SWORD_NORMAL, x, y, false, SWORD_HP);
        if (sword != null) {
            sword.setDrops(loot);
        }
        // after Damien's fall animation, the party moves to the clear room to break the sword
        addTimer(EventManager.addEvent(() -> {
            for (Char c : new ArrayList<>(field.getChars())) {
                c.warp(clearFieldId, 0);
            }
        }, 3000));
    }

    private static void removeMobs(Field field, int templateId) {
        for (Mob mob : new ArrayList<>(field.getMobs())) {
            if (mob.getTemplateId() == templateId) {
                field.broadcastPacket(MobPool.leaveField(mob.getObjectId(), DeathType.ANIMATION_DEATH));
                field.removeLife(mob.getObjectId());
            }
        }
    }

    /** A player died to 7 Brand stacks: Damien gains a Crystal; the 7th in phase 1 starts phase 2. */
    public void onBrandDeath(Char chr) {
        if (isBossDead()) {
            return;
        }
        crystals = Math.min(MAX_CRYSTALS, crystals + 1);
        Field field = getField();
        if (field != null) {
            for (Char c : field.getChars()) {
                c.write(DemianFieldPacket.corruptionChange(false, crystals));
            }
        }
        if (!phase2 && crystals >= MAX_CRYSTALS) {
            startPhase2ByCrystals();
        }
    }

    private void startPhase2ByCrystals() {
        Field field = getField();
        if (field == null) {
            return;
        }
        Mob p1 = (Mob) field.getLifeByTemplateId(p1Mob());
        if (p1 == null) {
            return;
        }
        phase2 = true;
        nextBrand = System.currentTimeMillis() + 20_000;
        getInstance().addProperty(CARRY_HP_KEY, p1.getHp());
        notice("Damien's Crystals are complete! He takes the fight to the summit.");
        despawn(p1);
        for (Char c : new ArrayList<>(field.getChars())) {
            c.warp(p1Field() + 20, 0); // the scene between the phases (enter_direction2), then phase 2
        }
    }

    /** Phase 2 mob zones as GMS: 1 when he appears, 2 at 20% HP, 3 at 10%. */
    @Override
    public void onMobSpawn(Field field, Mob mob) {
        super.onMobSpawn(field, mob);
        if (isPhase2(mob)) {
            EventManager.addEvent(() -> updateZone(mob), 1500);
        }
    }

    @Override
    public void onMobDamaged(Char attacker, Mob mob, long damage, long oldHp, long newHp) {
        super.onMobDamaged(attacker, mob, damage, oldHp, newHp);
        if (isPhase2(mob)) {
            updateZone(mob);
        }
    }

    private static boolean isPhase2(Mob mob) {
        return mob.getTemplateId() == BossConstants.DEMIAN_NORMAL_TEMPLATE_ID_2
                || mob.getTemplateId() == BossConstants.DEMIAN_HARD_TEMPLATE_ID_2;
    }

    private static void updateZone(Mob mob) {
        if (mob.isDead() || mob.getField() == null || mob.getMaxHp() <= 0) {
            return;
        }
        double left = mob.getHp() / (double) mob.getMaxHp();
        int zone = left <= 0.1 ? 3 : left <= 0.2 ? 2 : 1;
        if (mob.getCurZoneDataType() != zone) {
            mob.changeZoneId(zone);
        }
    }
}
