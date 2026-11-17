package net.swordie.ms.client.jobs.shine;

import net.swordie.ms.handlers.executors.EventManager;
import java.util.concurrent.TimeUnit;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.client.jobs.SkillBuffs;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.life.Summon;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.JobConstants.JobEnum;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.MobAttackInfo;
import net.swordie.ms.client.character.skills.BypassCooldownCheckType;
import net.swordie.ms.client.character.skills.SkillUseSource;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.debuff.DebuffUtil;
import net.swordie.ms.life.mob.MobStat;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.EMAD;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.IndieDamReduceR;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.IndieNotDamaged;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.ShineMageShine;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.ShineMageHourglass;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.ShineMageStarlight;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.ConstelEagle;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.ConstelHornedWhelk;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.RabbitStack;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.ShineMageAppear;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.ShineMageCircleOfSaviour;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.ShineMageWitchHat;
import static net.swordie.ms.client.character.skills.BypassCooldownCheckType.BypassCheckAndCooldown;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.swordie.ms.client.character.skills.atom.forceatom.ForceAtom;
import net.swordie.ms.client.character.skills.info.ForceAtomInfo;
import net.swordie.ms.enums.ForceAtomEnum;
import net.swordie.ms.enums.MoveAbility;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;

/**
 * Sia Astelle (Shine magician, Celestial Light). Job ids 18000, 18200-18212.
 * Attacks use the generic handling in {@link Job}; the buffs below follow Skill.wz (the v265.3 reference tree has no
 * class mechanics for it).
 */
public class SiaAstelle extends Job {

    public static final int STARRY_BOOST = 182111008; // on/off: longer teleport
    public static final int CELESTIAL_ALIGNMENT = 182101005; // indieCD, party
    public static final int STELLAR_ALCHIBA = 182111001; // indieIgnoreMobpdpR, party
    public static final int STELLAR_ALCHIBA_FUSION = 182111002; // + emad
    public static final int LINK = 182121000; // heal hp%, mass spell
    public static final int SADALMELIK = 182121007;
    public static final int SADALMELIK_FUSION = 182121009;
    public static final int STELLAR_BELLATRIX = 182111003; // damR, party
    public static final int STELLAR_BELLATRIX_2 = 182111004;
    public static final int APPEAR = 182121011; // indiePMdR, party
    public static final int PULSE = 182121013; // Hero's Will
    public static final int APPEAR_ENHANCE = 182120037;
    public static final int APPEAR_PERSIST = 182120038;
    public static final int STELLAR_CAPELLA = 182121043; // indieCr + indieCD
    public static final int STELLAR_CAPELLA_2 = 182121044;
    public static final int OBSERVE = 182121045; // indieDamR
    public static final int STELLAR_XI_SIRIUS_FUSION = 400021145; // wolf summon (fusion version)
    public static final int ASTRAL_MAGIC_GUARD = 182000000; // passive: y% of damage to MP
    public static final int STARRY_WAVE = 180001001; // gliding
    public static final int STARLIGHT = 182001003; // on/off: ShineMageStarlight; the client adds the Starlight attacks

    /*
     * Stellagram: the client keeps the record itself (Ray 1, Boom 2, Pole 3, Link 4, Shine 5; the sorted digits pick the
     * constellation) and casts the fusion id through a normal skill use. It skips a constellation whose fusion lock is
     * set in quest-ex 21770 sm<index>, and clears the record when MarkingStella arrives with n 0, r Ray-reset.
     */
    private static final int[][] COMMAND_LOCKS = {{STARRY_WAVE, 0}, {182001001, 1}, {182141001, 1}, {182101001, 2},
            {STELLAR_ALCHIBA, 3}, {STELLAR_BELLATRIX, 4}, {SiaAstelle.STELLAR_FOMALHAUT, 5}, {SiaAstelle.STELLAR_IZAR, 6}, {SiaAstelle.STELLAR_VEGA, 7}, {SADALMELIK, 8},
            {SiaAstelle.STELLAR_CANOPUS, 9}, {STELLAR_CAPELLA, 10}, {SiaAstelle.STELLAR_XI_SIRIUS, 11}, {SiaAstelle.STELLAR_XII_SADALSUUD, 12}};

    /** The client's lock slot for a skill (0x141070320), -1 if it has none. */
    public static int getCommandLockIndex(int skillId) {
        for (int[] lock : COMMAND_LOCKS) {
            if (lock[0] == skillId) {
                return lock[1];
            }
        }
        return -1;
    }

    // summons (second id = the Stellagram fusion version)
    public static final int STELLAR_ANTARES = 182001001; // rabbit holes, up to x at once
    public static final int STELLAR_ANTARES_FUSION = 182001002;
    public static final int SHINE_STELLAR_ANTARES = 182141001;
    public static final int SHINE_STELLAR_ANTARES_FUSION = 182141002;
    public static final int STELLAR_ALGOL = 182101001; // lizard
    public static final int STELLAR_ALGOL_FUSION = 182101002;
    public static final int POLE = 182111000; // starlight tower
    public static final int POLE_2 = 182111010;
    public static final int STELLAR_CANOPUS = 182121041; // anchor
    public static final int STELLAR_CANOPUS_FUSION = 182121042;

    // orbiting blades / conches: force atoms the server creates on the client's skill use
    public static final int STELLAR_IZAR = 182121001; // bulletCount blades, x hits each
    public static final int STELLAR_IZAR_FUSION = 182121002;
    public static final int STELLAR_FOMALHAUT = 182111005; // ConstelHornedWhelk: the client attacks with it every attackDelay
    public static final int STELLAR_FOMALHAUT_FUSION = 182111006; // + homing conches (the client uses 182111007)
    public static final int FOMALHAUT_CONCH = 182111007;
    public static final int STELLAR_VEGA = 182121003; // ConstelEagle: Starlight Wings, the client attacks with 182121004
    public static final int STELLAR_VEGA_FUSION = 182121005;

    // Etc/ForceAtomInfo.img start points (centres of the lt/rb boxes), one per atom
    private static final Position[] IZAR_START = {new Position(390, -330), new Position(340, -160),
            new Position(210, -50), new Position(30, -20), new Position(-160, -80)};
    private static final Position[] CONCH_START = {new Position(95, 95), new Position(95, -95),
            new Position(-95, -95), new Position(-95, 95)};

    private long fomalhautCastTime;

    // V skills
    public static final int SHINE = 400021142;
    public static final int STELLAR_XI_SIRIUS = 400021143;
    public static final int STELLAR_XII_SADALSUUD = 400021147;
    public static final int STELLAR_XII_SADALSUUD_FUSION = 400021148;
    public static final int SADALSUUD_BURST = 400021153; // the client's attack when the hourglass ends
    public static final int SAVIOR_CIRCLE = 400021149;
    public static final int TIME_BINDER = 400021152; // Sadalsuud's sub skill: binds what it hits

    public SiaAstelle(Char chr) {
        super(chr);
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isSiaAstelle(id);
    }

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        super.handleSkill(chr, tsm, skillId, slv, inPacket, skillUseInfo);
        if (SkillData.getSkillInfoById(skillId) == null) {
            return;
        }
        switch (skillId) {
            case CELESTIAL_ALIGNMENT, STELLAR_CAPELLA, STELLAR_CAPELLA_2, OBSERVE
                    -> SkillBuffs.giveIndieBuff(chr, skillId, slv);
            case STELLAR_ALCHIBA -> { // the witch hat the client draws comes from ShineMageWitchHat
                SkillBuffs.giveIndieBuff(chr, skillId, slv);
                putAlchibaHat(tsm, skillId, slv);
            }
            case SAVIOR_CIRCLE -> { // the circle and the extra Antares/Algol/Sirius hits come from its stat
                SkillBuffs.giveIndieBuff(chr, skillId, slv);
                putFlagStat(chr.getTemporaryStatManager(), ShineMageCircleOfSaviour, skillId, slv, 0);
            }
            case STELLAR_XI_SIRIUS, STELLAR_XI_SIRIUS_FUSION -> {
                // the wolf walks and attacks; its howl's Ignore Defense is given with it
                chr.getField().spawnSummonAndRemoveOld(Summon.getSummonBy(chr, skillId, slv));
                SkillBuffs.giveIndieBuff(chr, skillId, slv);
            }
            case STELLAR_BELLATRIX, STELLAR_BELLATRIX_2 -> SkillBuffs.giveIndieBuff(chr, skillId, slv, true, 0, 0);
            case APPEAR -> {
                SkillBuffs.giveIndieBuff(chr, skillId, slv, false,
                        chr.getSkillStatValue(time, APPEAR_PERSIST), chr.getSkillStatValue(indiePMdR, APPEAR_ENHANCE));
                // the marking skills' Appear look is the caster's own
                if (tsm == chr.getTemporaryStatManager()) {
                    putFlagStat(tsm, ShineMageAppear, skillId, slv, chr.getSkillStatValue(time, APPEAR_PERSIST));
                }
            }
            case PULSE -> tsm.removeAllDebuffs();
            case SHINE -> { // fusion effects always on for time (the client's part)
                tsm.putCharacterStatValue(ShineMageShine, new Option(1, skillId,
                        SkillData.getSkillInfoById(skillId).getValue(time, slv)));
                tsm.sendSetStatPacket();
            }
            case STELLAR_XII_SADALSUUD, STELLAR_XII_SADALSUUD_FUSION -> putHourglass(skillId, slv);
            case STELLAR_ALCHIBA_FUSION -> { // fusion version adds Magic ATT (emad)
                SkillBuffs.giveIndieBuff(chr, skillId, slv);
                var si = SkillData.getSkillInfoById(skillId);
                tsm.putCharacterStatValue(EMAD, new Option(si.getValue(emad, slv), skillId, si.getValue(time, slv)));
                putAlchibaHat(tsm, skillId, slv);
            }
            case LINK -> { // mass spell: called for each party member in range, through its own stat manager
                var si = SkillData.getSkillInfoById(skillId);
                Char target = tsm.getChr() != null ? tsm.getChr() : chr;
                target.heal((int) ((long) target.getMaxHP() * si.getValue(hp, slv) / 100));
            }
            case SADALMELIK, SADALMELIK_FUSION -> sadalmelik(skillId, slv);
            case STELLAR_ANTARES, STELLAR_ANTARES_FUSION, SHINE_STELLAR_ANTARES, SHINE_STELLAR_ANTARES_FUSION ->
                    spawnRabbitHole(skillId, slv);
            case STELLAR_ALGOL, STELLAR_ALGOL_FUSION ->
                    chr.getField().spawnSummonAndRemoveOld(Summon.getSummonBy(chr, skillId, slv));
            case POLE, POLE_2, STELLAR_CANOPUS, STELLAR_CANOPUS_FUSION -> {
                Summon summon = Summon.getSummonBy(chr, skillId, slv);
                summon.setMoveAbility(MoveAbility.Stop);
                chr.getField().spawnSummonAndRemoveOld(summon);
            }
            case STELLAR_IZAR, STELLAR_IZAR_FUSION -> {
                if (fusionOnCooldown(skillId, STELLAR_IZAR_FUSION, STELLAR_IZAR)) {
                    return;
                }
                createIzarBlades(skillId, slv);
            }
            case STELLAR_FOMALHAUT, STELLAR_FOMALHAUT_FUSION -> {
                if (!fusionOnCooldown(skillId, STELLAR_FOMALHAUT_FUSION, STELLAR_FOMALHAUT)) {
                    putFomalhaut(skillId, slv);
                }
            }
            case FOMALHAUT_CONCH -> createConches();
            case STELLAR_VEGA, STELLAR_VEGA_FUSION -> putVega(skillId, slv);
            case STARLIGHT -> { // the client cancels it itself while it is on; a second use turns it off too
                if (tsm.hasStat(ShineMageStarlight) && tsm.getOption(ShineMageStarlight).rOption == STARLIGHT) {
                    tsm.removeStatsBySkill(STARLIGHT);
                    tsm.sendResetStatPacket();
                } else if (!tsm.hasStat(ShineMageStarlight)) {
                    tsm.putCharacterStatValue(ShineMageStarlight, new Option(1, STARLIGHT, 0));
                    tsm.sendSetStatPacket();
                }
            }
        }
    }

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        int skillId = attackInfo.skillId;
        if (skillId == SADALMELIK || skillId == SADALMELIK_FUSION) {
            sadalmelik(skillId, attackInfo.slv); // in case the cast comes only as an attack
        } else if (skillId == STELLAR_VEGA || skillId == STELLAR_VEGA_FUSION) {
            putVega(skillId, attackInfo.slv); // the Starlit Gust is an attack; the wings come with it
        } else if (skillId == TIME_BINDER) {
            bindMobs(attackInfo);
        } else if (skillId == SADALSUUD_BURST && chr.getTemporaryStatManager().hasStat(ShineMageHourglass)) {
            chr.getTemporaryStatManager().removeStat(ShineMageHourglass);
            chr.getTemporaryStatManager().sendResetStatPacket();
        } else if ((skillId == STELLAR_FOMALHAUT || skillId == STELLAR_FOMALHAUT_FUSION)
                && !chr.getTemporaryStatManager().hasStat(ConstelHornedWhelk)) {
            // the conches' own hits carry this id too: only a cast well after the last one starts it
            var si = SkillData.getSkillInfoById(skillId);
            int lv = attackInfo.slv > 0 ? attackInfo.slv : chr.getSkillLevel(STELLAR_FOMALHAUT);
            if (si != null && System.currentTimeMillis() - fomalhautCastTime > si.getValue(time, lv) * 1000L + 3000) {
                putFomalhaut(skillId, lv);
            }
        }
        super.handleAttack(c, attackInfo);
    }

    // the client swaps in the fusion id itself; that id is not learned, so its cooldown goes on the base skill
    private boolean fusionOnCooldown(int skillId, int fusionId, int baseId) {
        if (skillId != fusionId) {
            return false;
        }
        if (chr.hasSkillOnCooldown(baseId)) {
            return true;
        }
        if (chr.hasSkill(baseId)) {
            chr.setSkillCooldown(baseId, chr.getSkillLevel(baseId)); // hardcode-ok
        }
        return false;
    }

    // Izar: bulletCount blades orbit and home in (bosses first, client side); each hits up to x times
    private void createIzarBlades(int skillId, int slv) {
        var si = SkillData.getSkillInfoById(skillId);
        int lv = slv > 0 ? slv : chr.getSkillLevel(STELLAR_IZAR);
        if (si == null || lv <= 0) {
            return;
        }
        int count = Math.min(IZAR_START.length, Math.max(1, si.getValue(bulletCount, lv)));
        int hits = Math.max(1, si.getValue(x, lv));
        Rect range = si.getFirstRect() != null ? si.getFirstRect() : new Rect(-500, -400, 500, 10);
        List<Integer> targets = pickTargets(chr.getRectAround(range), count);
        List<ForceAtomInfo> atoms = new ArrayList<>();
        ForceAtomEnum fae = ForceAtomEnum.SIA_IZAR_BLADE;
        for (int i = 0; i < count; i++) {
            atoms.add(new ForceAtomInfo(chr.getNewForceAtomKey(), fae.getInc(), Util.getRandomInclUpperBound(15, 20),
                    Util.getRandomInclUpperBound(20, 35), Util.getRandomInclUpperBound(30, 70), 810,
                    Util.getCurrentTime(), hits, 0, IZAR_START[i]));
        }
        chr.createForceAtom(new ForceAtom(false, 0, chr.getId(), fae, true, targets, skillId, atoms, new Rect(),
                0, 0, null, 0, chr.getPosition(), 0));
    }

    // Fomalhaut: the conches spin around her for time sec; the client draws them and attacks from this stat
    private void putFomalhaut(int skillId, int slv) {
        var si = SkillData.getSkillInfoById(skillId);
        int lv = slv > 0 ? slv : chr.getSkillLevel(STELLAR_FOMALHAUT);
        if (si == null || lv <= 0) {
            return;
        }
        var tsm = chr.getTemporaryStatManager();
        tsm.putCharacterStatValue(ConstelHornedWhelk, new Option(Math.max(1, si.getValue(x, lv)), skillId,
                si.getValue(time, lv)));
        tsm.sendSetStatPacket();
        fomalhautCastTime = System.currentTimeMillis();
    }

    // Fomalhaut fusion: w homing conches, each hits up to w2 times
    private void createConches() {
        var tsm = chr.getTemporaryStatManager();
        Option o = tsm.getOption(ConstelHornedWhelk);
        var si = SkillData.getSkillInfoById(FOMALHAUT_CONCH);
        var fusion = SkillData.getSkillInfoById(STELLAR_FOMALHAUT);
        int lv = chr.getSkillLevel(STELLAR_FOMALHAUT);
        if (o == null || o.rOption != STELLAR_FOMALHAUT_FUSION || si == null || fusion == null || lv <= 0) {
            return;
        }
        int count = Math.min(CONCH_START.length, Math.max(1, fusion.getValue(w, lv)));
        int hits = Math.max(1, fusion.getValue(w2, lv));
        Rect range = si.getFirstRect() != null ? si.getFirstRect() : new Rect(-500, -400, 500, 10);
        List<Integer> targets = pickTargets(chr.getRectAround(range), count);
        List<ForceAtomInfo> atoms = new ArrayList<>();
        ForceAtomEnum fae = ForceAtomEnum.SIA_HORNED_CONCH;
        for (int i = 0; i < count; i++) {
            atoms.add(new ForceAtomInfo(chr.getNewForceAtomKey(), fae.getInc(), Util.getRandomInclUpperBound(15, 20),
                    Util.getRandomInclUpperBound(20, 35), Util.getRandomInclUpperBound(30, 70),
                    Util.getRandomInclUpperBound(240, 300), Util.getCurrentTime(), hits, 0, CONCH_START[i]));
        }
        chr.createForceAtom(new ForceAtom(false, 0, chr.getId(), fae, true, targets, FOMALHAUT_CONCH, atoms, new Rect(),
                0, 0, null, 0, chr.getPosition(), 0));
    }

    // Vega: Starlight Wings for x sec (the fusion id has the longer x); the client attacks around her from this stat
    private void putVega(int skillId, int slv) {
        var si = SkillData.getSkillInfoById(skillId);
        int lv = slv > 0 ? slv : chr.getSkillLevel(STELLAR_VEGA);
        if (si == null || lv <= 0) {
            return;
        }
        var tsm = chr.getTemporaryStatManager();
        tsm.putCharacterStatValue(ConstelEagle, new Option(1, skillId, si.getValue(x, lv)));
        tsm.sendSetStatPacket();
    }

    // one target per atom: bosses with the most max HP first, then the rest; 0 lets the client pick
    private List<Integer> pickTargets(Rect rect, int count) {
        List<Mob> mobs = new ArrayList<>(chr.getField().getMobsInRect(rect));
        mobs.sort(Comparator.comparing((Mob m) -> !m.isBoss()).thenComparing(Mob::getMaxHp, Comparator.reverseOrder()));
        List<Integer> targets = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            targets.add(mobs.isEmpty() ? 0 : mobs.get(i % mobs.size()).getObjectId());
        }
        return targets;
    }

    /** Astral Magic Guard (passive): y% of a hit goes to MP instead, as Magic Guard does; not for % HP attacks. */
    @Override
    public void handleHit(Client c, InPacket inPacket, net.swordie.ms.client.character.info.HitInfo hitInfo) {
        int lv = chr.getSkillLevel(ASTRAL_MAGIC_GUARD);
        var si = SkillData.getSkillInfoById(ASTRAL_MAGIC_GUARD);
        if (lv > 0 && si != null && hitInfo.fixDamR == 0 && hitInfo.hpDamage > 0
                && chr.getTotalStat(net.swordie.ms.enums.BaseStat.magicGuard) <= 0) {
            int mpDmg = (int) Math.min(Math.max(0, chr.getMP()), (long) hitInfo.hpDamage * si.getValue(y, lv) / 100);
            hitInfo.hpDamage -= mpDmg;
            hitInfo.mpDamage += mpDmg;
        }
        super.handleHit(c, inPacket, hitInfo);
    }

    // Sadalmelik: invincible x sec after casting (the fusion version's x is already 1 higher), damage taken -z% for y sec
    private void sadalmelik(int skillId, int slv) {
        var tsm = chr.getTemporaryStatManager();
        var si = SkillData.getSkillInfoById(skillId);
        int lv = slv > 0 ? slv : chr.getSkillLevel(SADALMELIK);
        if (si == null || lv <= 0 || tsm.hasAOptByCTSAndSkill(IndieDamReduceR, skillId)) {
            return;
        }
        tsm.putCharacterStatValue(IndieNotDamaged, new Option(1, skillId, si.getValue(x, lv)));
        tsm.putCharacterStatValue(IndieDamReduceR, new Option(si.getValue(z, lv), skillId, si.getValue(y, lv)));
        tsm.sendSetStatPacket();
    }

    // Antares: up to x rabbit holes stay out; a new one replaces the oldest
    private static final int RABBIT_RECHARGE_SECONDS = 10;
    private java.util.concurrent.ScheduledFuture<?> rabbitRecharge;

    private void spawnRabbitHole(int skillId, int slv) {
        var si = SkillData.getSkillInfoById(skillId);
        useRabbitHoleCharge();
        int max = Math.max(1, si.getValue(x, slv));
        List<Summon> holes = chr.getField().getSummons().stream()
                .filter(s -> s.getChr() == chr && (s.getSkillID() == STELLAR_ANTARES || s.getSkillID() == STELLAR_ANTARES_FUSION
                        || s.getSkillID() == SHINE_STELLAR_ANTARES || s.getSkillID() == SHINE_STELLAR_ANTARES_FUSION))
                .sorted(Comparator.comparingLong(Summon::getSpawnTime))
                .toList();
        for (int i = 0; i <= holes.size() - max; i++) {
            chr.getField().removeSummon(holes.get(i));
        }
        Summon hole = Summon.getSummonBy(chr, skillId, slv);
        hole.setMoveAbility(MoveAbility.Stop);
        chr.getField().spawnSummon(hole);
    }

    // the client's Alchiba effect (182111001) is read from the stat slot that holds Starlight in the layout RE, so both are set
    private void putAlchibaHat(TemporaryStatManager tsm, int skillId, int slv) {
        putFlagStat(tsm, ShineMageWitchHat, skillId, slv, 0);
        if (!tsm.hasStat(ShineMageStarlight)) {
            putFlagStat(tsm, ShineMageStarlight, skillId, slv, 0);
        }
    }

    // a flag stat the client draws from (value 1, the skill, its time plus any extra seconds)
    private void putFlagStat(TemporaryStatManager tsm, CharacterTemporaryStat cts, int skillId, int slv, int extraTime) {
        var si = SkillData.getSkillInfoById(skillId);
        if (si == null || tsm == null) {
            return;
        }
        int duration = si.getValue(time, slv) + extraTime;
        if (duration <= 0) {
            return;
        }
        tsm.putCharacterStatValue(cts, new Option(1, skillId, duration));
        tsm.sendSetStatPacket();
    }

    // Sadalsuud: n = explosions (v; the fusion data's v already has +w); the client draws no hourglass while x is 0
    private void putHourglass(int skillId, int slv) {
        var si = SkillData.getSkillInfoById(skillId);
        int lv = slv > 0 ? slv : chr.getSkillLevel(skillId);
        if (si == null || lv <= 0) {
            return;
        }
        Option o = new Option(Math.max(1, si.getValue(v, lv)), skillId, si.getValue(time, lv));
        o.xOption = 1;
        var tsm = chr.getTemporaryStatManager();
        tsm.putCharacterStatValue(ShineMageHourglass, o);
        tsm.sendSetStatPacket();
    }

    // Time Binder: binds every monster it hits for time sec; one already stunned or bound is left alone
    private void bindMobs(AttackInfo attackInfo) {
        var si = SkillData.getSkillInfoById(TIME_BINDER);
        int lv = chr.getSkillLevel(TIME_BINDER);
        if (si == null || lv <= 0 || chr.getField() == null) {
            return;
        }
        int duration = si.getValue(time, lv);
        List<Integer> mobs = new ArrayList<>();
        for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
            Mob mob = chr.getField().getLifeByObjectID(Mob.class, mai.mobId);
            if (mob != null && mob.getHp() > 0 && !mob.getTemporaryStat().hasCurrentMobStat(MobStat.Stun)
                    && !mob.getTemporaryStat().hasCurrentMobStat(MobStat.Freeze)) {
                mobs.add(mob.getObjectId());
            }
        }
        if (!mobs.isEmpty() && duration > 0) {
            DebuffUtil.applyDebuffOnMobs(chr, MobStat.Freeze, new Option(1, TIME_BINDER, duration), mobs);
        }
    }

    private static boolean isAntares(int skillId) {
        return skillId == STELLAR_ANTARES || skillId == SHINE_STELLAR_ANTARES;
    }

    /** Antares charges: one more, up to z; a request without a skill id counts for the Antares she has. */
    public void addRabbitHoleCharge(int skillId) {
        int id = isAntares(skillId) ? skillId
                : skillId > 0 ? 0 : chr.hasSkill(SHINE_STELLAR_ANTARES) ? SHINE_STELLAR_ANTARES : STELLAR_ANTARES;
        var si = id == 0 ? null : SkillData.getSkillInfoById(id);
        int lv = id == 0 ? 0 : chr.getSkillLevel(id);
        if (si == null || lv <= 0) {
            return;
        }
        var tsm = chr.getTemporaryStatManager();
        int have = tsm.hasStat(RabbitStack) ? tsm.getOption(RabbitStack).nOption : 0;
        int max = Math.max(1, si.getValue(z, lv));
        if (have >= max) {
            return;
        }
        tsm.putCharacterStatValue(RabbitStack, new Option(have + 1, id, 0));
        tsm.sendSetStatPacket();
        if (have + 1 < max) {
            scheduleRabbitRecharge(id);
        }
    }

    // the client never asks for a recharge on its own, so one charge comes back every RABBIT_RECHARGE_SECONDS
    private void scheduleRabbitRecharge(int skillId) {
        if (rabbitRecharge != null && !rabbitRecharge.isDone()) {
            return;
        }
        rabbitRecharge = EventManager.addEvent(() -> {
            rabbitRecharge = null;
            if (chr.getField() != null && chr.getClient() != null) {
                addRabbitHoleCharge(skillId);
            }
        }, RABBIT_RECHARGE_SECONDS, TimeUnit.SECONDS);
    }

    // a rabbit hole uses one charge; the client starts recharging it
    private void useRabbitHoleCharge() {
        var tsm = chr.getTemporaryStatManager();
        Option o = tsm.hasStat(RabbitStack) ? tsm.getOption(RabbitStack) : null;
        int id = o != null ? o.rOption : chr.hasSkill(SHINE_STELLAR_ANTARES) ? SHINE_STELLAR_ANTARES : STELLAR_ANTARES;
        var si = SkillData.getSkillInfoById(id);
        int lv = chr.getSkillLevel(id);
        if (si == null || lv <= 0) {
            return;
        }
        // no stat yet means full charges
        int have = o != null ? o.nOption : Math.max(1, si.getValue(z, lv));
        if (have <= 0) {
            return;
        }
        tsm.putCharacterStatValue(RabbitStack, new Option(have - 1, id, 0));
        tsm.sendSetStatPacket();
        scheduleRabbitRecharge(id);
    }

    // Fomalhaut's conches attack with its id while ConstelHornedWhelk is on; the cast already set the cooldown
    @Override
    public BypassCooldownCheckType canBypassCooldownCheck(int skillId, AttackInfo attackInfo, SkillUseInfo sui,
                                                          SkillUseSource source) {
        if ((skillId == STELLAR_FOMALHAUT || skillId == STELLAR_FOMALHAUT_FUSION)
                && source == SkillUseSource.AttackUseRequest
                && chr.getTemporaryStatManager().hasStat(ConstelHornedWhelk)) {
            return BypassCheckAndCooldown;
        }
        return super.canBypassCooldownCheck(skillId, attackInfo, sui, source);
    }

    @Override
    public void setCharCreationStats(Char chr) {
        super.setCharCreationStats(chr);
        setFirstJobCreationStats(chr, JobEnum.SIA_ASTELLE_1.getJobId(), 4, 4, 25, 4);
    }

    @Override
    public void handleLevelUp() {
        super.handleLevelUp();
        advanceByLevel(JobEnum.SIA_ASTELLE_1.getJobId(), JobEnum.SIA_ASTELLE_2.getJobId(), JobEnum.SIA_ASTELLE_3.getJobId(),
                JobEnum.SIA_ASTELLE_4.getJobId());
    }
}
