package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.connection.packet.field.SerenFieldPacket;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.skill.MobSkillID;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.Field;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static net.swordie.ms.constants.BossConstants.*;

/**
 * Chosen Seren (maplestorywiki.net, GMS), Normal, Hard and Extreme, level 260, 30 minutes, death count 8. Each mode
 * has its own maps and monsters ({@link BossConstants#SEREN_FIELD_BASE}).
 * <ul>
 *     <li>Phase 1 (Palace Main Hall): Chosen Seren. Each player's sun gauge fills as they are hit; a full gauge
 *     stuns them for 8 seconds and empties. When she falls, the party is taken to the Barrier Between Worlds.</li>
 *     <li>Phase 2: the sun's cycle. Seren turns through Midday, Dusk, Twilight (the short window to burst her) and Dawn
 *     (Avatars of Nerota and Nerota's Power; her white shield heals her when Midday returns). Curse gauges that fill
 *     shorten Midnight; when it's gone the fight is lost.
 *     Her HP carries over between her forms.</li>
 *     <li>After phase 2, the party goes to the treasure room (Chosen Seren's Treasure Chest).</li>
 * </ul>
 */
public class SerenInstanceHelper extends BossMechanicsHelper {

    private static final int[] FORMS = {SEREN_MIDDAY, SEREN_DUSK, SEREN_TWILIGHT, SEREN_DAWN};
    private static final String[] FORM_NAMES = {"Midday", "Dusk", "Twilight", "Dawn"};
    private static final int[] FORM_SECONDS = {100, 60, 20, 40};

    private volatile int mode = -1;
    private volatile int fieldId = -1;
    private static final int SUN_GAUGE_MAX = 1000;
    private static final int[] SUN_GAUGE_PER_HIT = {100, 125, 200};
    private final Map<Integer, Integer> sunGauge = new ConcurrentHashMap<>();

    private int form = 0;
    private long nextFormChange = 0;

    // Ray of Light (field skill 100024) at the end of each segment; level per mode Normal, Hard, Extreme
    private static final int[] RAY_LEVEL = {2, 1, 3};
    private static final int[] RAY_DELAY = {3720, 3060, 3060};
    private static final int[] RAY_WHITE_OUT = {3360, 2700, 2700};
    private static final int RAYS = 7;
    private static final int SEREN_RAY_REF = 2; // the phase 2 body the forms share HP with
    private static final Position BODY_POS = new Position(0, 305);
    private int rayKey;
    private long rayEndsAt;
    private final Map<Integer, Long> rayHitAt = new ConcurrentHashMap<>();

    private int mode() {
        if (mode < 0) {
            int m = getInstance() == null ? -1 : getSerenMode(getInstance().getEnterFieldId());
            mode = Math.max(m, 0);
        }
        return mode;
    }

    private int mob(int offset) {
        return serenMob(mode(), offset);
    }

    @Override
    protected Field getField() {
        if (getInstance() == null) {
            return null;
        }
        if (fieldId < 0) {
            fieldId = serenP1Field(mode());
        }
        return getInstance().getField(fieldId);
    }

    @Override
    protected Set<Integer> getBossIds() {
        return Set.of(mob(SEREN_MIDDAY), mob(SEREN_DUSK), mob(SEREN_TWILIGHT), mob(SEREN_DAWN));
    }

    @Override
    protected void startMechanics() {
        every(1000, this::tick);
        for (Char chr : getInstance().getChars()) {
            chr.write(SerenFieldPacket.sunGauge(0, SUN_GAUGE_MAX));
        }
    }

    @Override
    public void onChrHitByMob(Char chr, int mobTemplateId, int dmgAmount) {
        super.onChrHitByMob(chr, mobTemplateId, dmgAmount);
        if (rayKey != 0 && mobTemplateId == mob(SEREN_RAY_REF) && fieldId == serenP2Field(mode())
                && rayHitAt.putIfAbsent(chr.getId(), System.currentTimeMillis()) == null) {
            // Ray of Light: the curse gauge caps out (no stun)
            sunGauge.put(chr.getId(), 0);
            chr.write(SerenFieldPacket.sunGauge(0, SUN_GAUGE_MAX));
            onCurseFull(chr);
            return;
        }
        if (fieldId != serenP2Field(mode()) || FORMS[form] != SEREN_DAWN || dmgAmount <= 0) {
            return;
        }
        // Dawn: her white shield grows with every hit (0.126% from the Avatars, 0.167% from her other attacks)
        shieldRatio += mobTemplateId == mob(SEREN_AVATAR_OF_NEROTA) ? 0.00126 : 0.00167;
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        super.onWarp(chr, oldField, newField);
        if (newField == null || newField.getId() != serenP2Field(mode())) {
            chr.setHealReceivedPercent(100);
        }
    }

    @Override
    public void onChrHit(Char chr, int dmgAmount) {
        super.onChrHit(chr, dmgAmount);
        if (dmgAmount <= 0 || isBossDead() || chr.getField() == null) {
            return;
        }
        Long rayHit = rayHitAt.get(chr.getId());
        if (rayHit != null && System.currentTimeMillis() - rayHit < 1000) {
            return; // already counted as the Ray of Light
        }
        if (chr.getField().getId() == serenP2Field(mode())) {
            if (FORMS[form] == SEREN_TWILIGHT) {
                return; // Midnight: attacks don't add to the curse
            }
            int curse = sunGauge.merge(chr.getId(), SUN_GAUGE_PER_HIT[mode()], Integer::sum);
            if (curse >= SUN_GAUGE_MAX) {
                sunGauge.put(chr.getId(), 0);
                curse = 0;
                onCurseFull(chr);
            }
            chr.write(SerenFieldPacket.sunGauge(curse, SUN_GAUGE_MAX));
            return;
        }
        if (chr.getField().getId() != serenP1Field(mode())) {
            return;
        }
        int gauge = sunGauge.merge(chr.getId(), SUN_GAUGE_PER_HIT[mode()], Integer::sum);
        if (gauge >= SUN_GAUGE_MAX) {
            sunGauge.put(chr.getId(), 0);
            gauge = 0;
            var opt = new Option(1, MobSkillID.Stun.getVal(), 8);
            opt.slv = 1;
            var tsm = chr.getTemporaryStatManager();
            tsm.putCharacterStatValueFromMobSkill(CharacterTemporaryStat.Stun, opt);
            tsm.sendSetStatPacket();
            chr.chatMessage("The sun's heat overwhelms you!");
        }
        chr.write(SerenFieldPacket.sunGauge(gauge, SUN_GAUGE_MAX));
    }

    @Override
    public void onMobDeath(Field field, Mob mob) {
        int m = mode();
        if (mob.getTemplateId() == mob(SEREN_P1) && field.getId() == serenP1Field(m)) {
            notice("The sun rises over the Barrier Between Worlds...");
            EventManager.addEvent(() -> {
                fieldId = serenP2Field(m);
                sunGauge.clear();
                form = 0;
                nextFormChange = System.currentTimeMillis() + FORM_SECONDS[0] * 1000L;
                getInstance().warp(serenP2Field(m));
            }, 3000);
            return;
        }
        boolean finalForm = getBossIds().contains(mob.getTemplateId()) && !isBossDead();
        super.onMobDeath(field, mob);
        if (finalForm) {
            notice("Chosen Seren has been defeated! Her treasure awaits.");
            EventManager.addEvent(() -> {
                fieldId = serenRewardField(m);
                getInstance().warp(serenRewardField(m));
            }, 4000);
        }
    }

    // phase 2 (maplestorywiki.net): curse gauges shrink Midnight (up to 35 times: then the fight is lost); a clean
    // cycle grows it back by one. Dawn: Avatars of Nerota (invincible; more as her HP drops), Nerota's Power every 15 s
    // (up to 6, gone after 22 s), and a white shield (10% of her HP, +0.024%/s) that heals her on Midday by what's left
    private static final int MAX_SHRINKS = 35;
    private final java.util.Map<Integer, Long> powerSpawned = new ConcurrentHashMap<>();
    private volatile int shrinks = 0;
    private volatile boolean cleanCycle = true;
    private long dawnStartHp = 0;
    private double shieldRatio = 0;
    private long nextPower = 0;

    private int formSeconds(int f) {
        if (FORMS[f] == SEREN_TWILIGHT) {
            return Math.max(3, FORM_SECONDS[f] - FORM_SECONDS[f] * shrinks / MAX_SHRINKS);
        }
        return FORM_SECONDS[f];
    }

    private int avatarCount(double hp) {
        int m = mode();
        if (m == 0) {
            return hp < 0.41 ? 2 : 1;
        }
        if (m == 1) {
            return hp < 0.21 ? 3 : hp < 0.41 ? 2 : 1;
        }
        return hp < 0.51 ? 3 : hp < 0.76 ? 2 : 1;
    }

    /** A player's curse gauge filled in phase 2: Midnight gets shorter. */
    private void onCurseFull(Char chr) {
        cleanCycle = false;
        shrinks++;
        if (shrinks >= MAX_SHRINKS) {
            notice("Midnight has vanished. The sun swallows the Barrier Between Worlds...");
            EventManager.addEvent(() -> {
                if (getInstance() != null) {
                    getInstance().clear();
                }
            }, 3000);
        } else {
            notice(chr.getName() + "'s curse is complete: Midnight grows shorter (" + shrinks + "/" + MAX_SHRINKS + ").");
        }
    }

    private void tick() {
        if (fieldId != serenP2Field(mode())) {
            return;
        }
        Mob boss = getBoss();
        if (boss == null) {
            return;
        }
        if (countAlive(mob(SEREN_RAY_REF)) == 0) {
            // GMS keeps the shared body in the map for the whole phase; the Ray of Light hits as its attack
            spawnAdd(mob(SEREN_RAY_REF), BODY_POS, Long.MAX_VALUE / 4);
        }
        long now = System.currentTimeMillis();
        if (FORMS[form] == SEREN_DAWN) {
            shieldRatio += 0.00024;
            int powerId = mob(SEREN_NEROTAS_POWER);
            for (Mob m : new java.util.ArrayList<>(getField().getMobs())) {
                Long born = powerSpawned.get(m.getObjectId());
                if (born != null && now - born > 22_000) {
                    powerSpawned.remove(m.getObjectId());
                    despawn(m);
                }
            }
            if (now >= nextPower && countAlive(powerId) < 6) {
                Mob p = spawnAdd(powerId, randomGround(), SEREN_AVATAR_HP[mode()]);
                if (p != null) {
                    powerSpawned.put(p.getObjectId(), now);
                }
                nextPower = now + 15_000;
            }
        }
        if (now < nextFormChange) {
            return;
        }
        if (rayKey == 0) {
            startRayOfLight();
            return;
        }
        if (now >= rayEndsAt) {
            changeForm();
        }
    }

    /** The segment ends with the Ray of Light; the sun moves on when the clients say it is over. */
    private synchronized void startRayOfLight() {
        int m = mode();
        rayKey = Math.max(1, (int) (System.nanoTime() & 0x7fffffff));
        int ms = RAY_DELAY[m] + 150;
        rayEndsAt = System.currentTimeMillis() + ms + 1000;
        rayHitAt.clear();
        int[] forms = {mob(SEREN_DAWN), mob(SEREN_MIDDAY), mob(SEREN_DUSK), mob(SEREN_TWILIGHT)};
        var ray = new net.swordie.ms.world.field.fieldskill.SerenMegaAttack(RAY_LEVEL[m], RAYS,
                net.swordie.ms.util.Util.getRandom(1, RAYS - 1), RAY_DELAY[m], RAY_WHITE_OUT[m], mob(SEREN_RAY_REF),
                forms);
        Field field = getField();
        field.broadcastPacket(SerenFieldPacket.rayOfLight(rayKey, ms));
        field.broadcastPacket(net.swordie.ms.connection.packet.field.FieldPacket.fieldSkill(ray));
    }

    /** SEREN_RAY_OF_LIGHT_END from a client: its timer for the ray with this key ran out. */
    public void onRayOfLightEnd(Char chr, int key) {
        if (key != 0 && key == rayKey && chr.getField() != null && chr.getField().getId() == serenP2Field(mode())) {
            changeForm();
        }
    }

    private synchronized void changeForm() {
        if (rayKey == 0 || fieldId != serenP2Field(mode())) {
            return;
        }
        rayKey = 0;
        Mob boss = getBoss();
        if (boss == null) {
            return;
        }
        long now = System.currentTimeMillis();
        int oldForm = form;
        form = (form + 1) % FORMS.length;
        nextFormChange = now + formSeconds(form) * 1000L;
        long hp = boss.getHp();
        long maxHp = boss.getMaxHp();
        int avatarId = mob(SEREN_AVATAR_OF_NEROTA);
        int powerId = mob(SEREN_NEROTAS_POWER);
        if (FORMS[oldForm] == SEREN_DAWN) {
            // the shield left over heals her; the Avatars and Nerota's Power leave
            for (Mob mob : new java.util.ArrayList<>(getField().getMobs())) {
                if (mob.getTemplateId() == avatarId || mob.getTemplateId() == powerId) {
                    despawn(mob);
                }
            }
            powerSpawned.clear();
            long shield = (long) (maxHp * shieldRatio);
            long heal = Math.max(0, shield - Math.max(0, dawnStartHp - hp));
            hp = Math.min(maxHp, hp + heal);
            if (cleanCycle && shrinks > 0) {
                shrinks--;
                notice("No curse this cycle: Midnight grows longer again.");
            }
            cleanCycle = true;
        }
        // Dusk: healing from items and skills is cut by 80%
        int healPercent = FORMS[form] == SEREN_DUSK ? 20 : 100;
        for (Char c : getField().getChars()) {
            c.setHealReceivedPercent(healPercent);
        }
        Position pos = boss.getPosition();
        despawn(boss);
        Mob next = getField().spawnMob(mob(FORMS[form]), pos.getX(), pos.getY(), false, maxHp);
        next.setHp(hp);
        getField().broadcastPacket(SerenFieldPacket.sunPhase(form + 1));
        notice("The sun moves on: " + FORM_NAMES[form] + ".");
        if (FORMS[form] == SEREN_DAWN) {
            dawnStartHp = hp;
            shieldRatio = 0.10;
            nextPower = now + 15_000;
            int avatars = avatarCount(hp / (double) Math.max(1, maxHp));
            for (int i = 0; i < avatars; i++) {
                spawnAdd(avatarId, randomGround(), Long.MAX_VALUE / 4); // they can't be killed
            }
        }
    }
}
