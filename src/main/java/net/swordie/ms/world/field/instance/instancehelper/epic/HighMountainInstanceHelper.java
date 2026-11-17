package net.swordie.ms.world.field.instance.instancehelper.epic;

import java.util.List;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.connection.packet.field.TynosFieldPacket;
import net.swordie.ms.connection.packet.field.HighMountainFieldPacket;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.connection.packet.MobPool;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.DeathType;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.ClockPacket;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Foothold;
import net.swordie.ms.world.field.Portal;
import net.swordie.ms.world.field.fieldeffect.FieldEffect;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * High Mountain (Epic Dungeon, solo). Normal mode: 10 stages, six monster stages (fieldType 354: fill the mission
 * gauge), three seals (355-357: defeat the Aspect) and Tynos (358). Story mode (until quest 26216): the story maps
 * 359-363, ending with quests 26211-26216 done. Each stage gets the stage window (Out 1272), a clock, the mission
 * gauge (1273) or the death count (1240) and Mitra's skill bar (2317 bar 66 / 65). A cleared stage opens the
 * pt_edNext0 portal. Normal-mode progress is saved per stage (EpicDungeon record, resumes the same week).
 * Stage time limits, mission counts and death counts are this server's choice (GMS numbers unknown).
 * <p>
 * Mitra's Authority is CharacterTemporaryStat EpicDungeonGauge (n = Authority, r = 80003577 whose gauge is the max);
 * the client checks n against the skill's edCon before it casts. Defeated monsters give Authority (Mob.wz point x
 * 10), except while Unleash Authority (80003578, IndieBuffIcon) runs, during which the skills cost nothing.
 * <p>
 * Tynos (358/363): the fight starts at the summoning altar (client object, In 1533). Tynos's HP stops at 50% for the
 * Slaughter (destroy the Tynos Fragment before it vanishes) and at 1% for Hell (stand in Mitra's Flare, In 1530);
 * Mitra's spine then ends him. Boss stages also get the heal altar (Out 847, In 1488 / 1532).
 */
public class HighMountainInstanceHelper extends InstanceHelper {
    public static final String STORY = "edStory";
    public static final String FIRST_STAGE = "edFirstStage";

    private static final int NORMAL = 0, SEAL = 1, FINAL = 2;
    // index = the kind constants above
    private static final List<String> KINDS = List.of("normal", "seal", "final");
    private static final int[] NORMAL_FIELDS = stageRows("normal").mapToInt(r -> r.i("fields")).toArray();
    private static final int[] NORMAL_KIND = stageRows("normal").mapToInt(r -> KINDS.indexOf(r.str("kind"))).toArray();
    private static final int[] NORMAL_BOSS = stageRows("normal").mapToInt(r -> r.i("boss")).toArray();
    private static final int[] STORY_FIELDS = stageRows("story").mapToInt(r -> r.i("fields")).toArray();
    private static final int[] STORY_KIND = stageRows("story").mapToInt(r -> KINDS.indexOf(r.str("kind"))).toArray();
    private static final int[] STORY_BOSS = stageRows("story").mapToInt(r -> r.i("boss")).toArray();

    private static final int NORMAL_SECONDS = 300;
    private static final int BOSS_SECONDS = 600;
    private static final int MAX_DEATHS = 5;
    private static final int MITRA_MORPH = 328;
    private static final int MITRA_BAR_NORMAL = 66;
    private static final int MITRA_BAR_STORY = 65;
    private static final int[] MITRA_SKILLS_NORMAL = {80003571, 80003572, 80003574, 80003575, 80003578};
    private static final int[] MITRA_SKILLS_STORY = {80003571, 80003582, 80003573, 80003583, 80003584, 80003585};

    // Authority
    private static final int GAUGE_SKILL = 80003577;
    private static final int AUTHORITY_MAX = 1000;
    private static final int AUTHORITY_PER_POINT = 10;
    private static final int UNLEASH = 80003578;
    private static final int UNLEASH_SECONDS = 20;
    /** Skill.wz 800035 edCon */
    private static final Map<Integer, Integer> AUTHORITY_COST = Map.of(80003572, 30, 80003574, 100, 80003575, 150,
            UNLEASH, 1000);

    // Tynos
    private static final int SLAUGHTER_FRAGMENT = 9834357;
    private static final int VISCID_ABYSS = 9834407;
    private static final int SLAUGHTER_BLOCK = 50, HELL_BLOCK = 1;
    private static final int SLAUGHTER_FAIL_DAMAGE_R = 70, HELL_FAIL_DAMAGE_R = 90;
    private static final int HELL_SECONDS = 10;
    private static final int RETRY_MS = 8000;
    private static final int ALTAR_FALLBACK_MS = 30_000;
    private static final int ADDS_EVERY_MS = 20_000, ADDS_PER_WAVE = 3, ADDS_MAX = 8;
    private static final int SPINE_SUMMON = 1, SPINE_TITLE = 2, SPINE_MITRA = 3;
    private static final String SPINE = "Effect/HighMountain.img/spine/";
    private static final String FLARE_SOUND = "SoundEff/TynusScreenEff/mitraFlareCreate";
    private static final int[] FLARE_RECT = {-120, -250, 120, 20};

    private enum TynosState {NONE, WAIT_ALTAR, SUMMONING, FIGHT, SLAUGHTER, HELL, DYING, DONE}

    // heal altar (boss stages)
    private static final int ALTAR_TIMER = 1;
    private static final int ALTAR_STEPS = 12;
    private static final int ALTAR_STEP_MS = 250;
    private static final int ALTAR_SECONDS = 20;
    private static final int ALTAR_FIRST_MS = 30_000, ALTAR_EVERY_MS = 45_000;

    private boolean story;
    private int stage; // 1-based, 0 = not started
    private int initializedField;
    private int kills;
    private int missionMax;
    private int deathsLeft;
    private boolean cleared;
    private boolean finished;
    private ScheduledFuture stageTimer;

    private int authority;

    private TynosState tynosState = TynosState.NONE;
    private Mob tynos;
    private Mob fragment;
    private boolean flareOn;
    private ScheduledFuture phaseTimer;

    private boolean altarOn;
    private boolean altarStepPending;
    private int altarProgress;

    private static java.util.stream.Stream<DataTable.Row> stageRows(String mode) {
        return DataTable.read("high-mountain-stages").rows().stream().filter(r -> r.str("mode").equals(mode));
    }

    public static boolean isField(int fieldId) {
        return indexOf(NORMAL_FIELDS, fieldId) >= 0 || indexOf(STORY_FIELDS, fieldId) >= 0;
    }

    public static int stageField(boolean story, int stage) {
        int[] fields = story ? STORY_FIELDS : NORMAL_FIELDS;
        return fields[Math.max(0, Math.min(stage, fields.length) - 1)];
    }

    private static int indexOf(int[] arr, int v) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == v) {
                return i;
            }
        }
        return -1;
    }

    private int[] fields() {
        return story ? STORY_FIELDS : NORMAL_FIELDS;
    }

    private int kind() {
        return (story ? STORY_KIND : NORMAL_KIND)[stage - 1];
    }

    private int boss() {
        return (story ? STORY_BOSS : NORMAL_BOSS)[stage - 1];
    }

    private Char chr() {
        return getInstance().getChr();
    }

    private boolean inStageField(Char chr) {
        return chr != null && stage > 0 && chr.getField() != null && chr.getFieldID() == fields()[stage - 1]
                && chr.getInstance() == getInstance();
    }

    @Override
    public void setup(Instance instance) {
        story = Boolean.TRUE.equals(instance.getProperty(STORY));
        super.setup(instance);
    }

    @Override
    protected int getNextStageDelay() {
        return 1500;
    }

    @Override
    protected void startStage(int ignored) {
        Char chr = chr();
        if (chr != null && chr.getField() != null) {
            initStage(chr.getField());
        }
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        if (newField != null && newField.getInstance() == getInstance() && indexOf(fields(), newField.getId()) >= 0) {
            addTimer(EventManager.addEvent(() -> initStage(newField), 1000));
        }
    }

    private synchronized void initStage(Field field) {
        if (finished || field == null || field.getId() == initializedField) {
            return;
        }
        int idx = indexOf(fields(), field.getId());
        if (idx < 0) {
            return;
        }
        Char chr = chr();
        if (chr == null || chr.getField() != field) {
            return;
        }
        initializedField = field.getId();
        stage = idx + 1;
        kills = 0;
        cleared = false;
        if (stageTimer != null) {
            stageTimer.cancel(false);
        }
        cancelPhaseTimer();
        tynosState = TynosState.NONE;
        tynos = null;
        fragment = null;
        flareOn = false;
        altarOn = false;
        int kind = kind();
        int seconds = kind == NORMAL ? NORMAL_SECONDS : BOSS_SECONDS;
        missionMax = kind == NORMAL ? Math.max(20, field.getMobGens().size()) : 0;
        deathsLeft = kind == NORMAL ? 0 : MAX_DEATHS;

        int uiType = kind == NORMAL ? 0 : kind == SEAL ? 1 : 2;
        chr.write(UserLocal.epicDungeonStageUi(uiType, stage - 1, true, missionMax, deathsLeft));
        chr.write(FieldPacket.clock(ClockPacket.secondsClock(seconds)));
        if (kind == NORMAL) {
            chr.write(UserLocal.epicDungeonMission(0));
        } else {
            chr.write(UserLocal.deathCountInfo2(chr.getId(), deathsLeft));
            if (kind == FINAL) {
                tynosState = TynosState.WAIT_ALTAR;
                phaseTimer = addTimer(EventManager.addEvent(() -> summonTynos(chr), ALTAR_FALLBACK_MS));
            } else {
                spawnBoss(field);
            }
            scheduleAltar(ALTAR_FIRST_MS);
        }
        if (stage <= 10) {
            chr.write(FieldPacket.fieldEffect(FieldEffect.getFieldEffectFromWz(
                    "Effect/HighMountain.img/effect/Stage_number/" + stage, 0)));
        }
        chr.write(FieldPacket.setTemporarySkillSet(story ? MITRA_BAR_STORY : MITRA_BAR_NORMAL,
                story ? MITRA_SKILLS_STORY : MITRA_SKILLS_NORMAL));
        if (!story) {
            sendAuthority(chr);
        }
        String goal = kind == NORMAL ? "Defeat " + missionMax + " monsters." : kind == SEAL
                ? "Defeat the Aspect to break the seal." : "Touch the altar to summon Tynos!";
        chr.getScriptManager().progressMessageFont("Stage " + stage + ": " + goal);
        stageTimer = addTimer(EventManager.addEvent(() -> fail("Time's up!"), seconds * 1000L));
    }

    private Mob spawnBoss(Field field) {
        int id = boss();
        if (id == 0) {
            return null;
        }
        Portal sp = field.getInfo().getDefaultPortal();
        int x = sp == null ? 0 : sp.getX();
        int y = sp == null ? 0 : sp.getY();
        Foothold fh = field.findFootHoldBelow(new Position(x, y));
        if (fh != null) {
            y = fh.getYFromX(x);
        }
        return field.spawnMob(id, x, y, false, 0);
    }

    @Override
    public synchronized void onMobDeath(Field field, Mob mob) {
        if (finished || stage == 0 || field.getId() != fields()[stage - 1]) {
            return;
        }
        boolean killed = mob.getHp() <= 0;
        if (killed && (!mob.isBoss() || mob.getTemplateId() == SLAUGHTER_FRAGMENT)) {
            gainAuthority(mob);
        }
        if (mob == fragment) {
            fragment = null;
            onFragmentGone(killed);
            return;
        }
        if (cleared) {
            return;
        }
        if (kind() == NORMAL) {
            if (mob.isBoss() || !killed) {
                return;
            }
            kills++;
            Char chr = chr();
            if (chr != null) {
                chr.write(UserLocal.epicDungeonMission(Math.min(kills, missionMax)));
            }
            if (kills >= missionMax) {
                clearStage(field);
            }
        } else if (mob.getTemplateId() == boss()) {
            if (kind() == FINAL) {
                tynosState = TynosState.DONE;
                cancelPhaseTimer();
            }
            for (Mob m : new java.util.ArrayList<>(field.getMobs())) {
                if (m != mob) {
                    field.removeLife(m.getObjectId());
                    field.broadcastPacket(MobPool.leaveField(m.getObjectId(), DeathType.ANIMATION_DEATH));
                }
            }
            clearStage(field);
        }
    }

    private void clearStage(Field field) {
        cleared = true;
        if (stageTimer != null) {
            stageTimer.cancel(false);
        }
        Char chr = chr();
        if (chr == null) {
            return;
        }
        endAltar(false);
        chr.write(FieldPacket.clock(ClockPacket.removeClock()));
        chr.write(FieldPacket.fieldEffect(FieldEffect.getFieldEffectFromWz("Effect/HighMountain.img/effect/clearEff", 0)));
        if (!story) {
            EpicDungeon.setClearedStages(chr, stage);
        }
        if (stage >= fields().length) {
            finished = true;
            chr.getScriptManager().progressMessageFont(story ? "Mitra's tale is told." : "You've reached the summit of High Mountain!");
            addTimer(EventManager.addEvent(() -> finish(chr), 4000));
        } else {
            chr.getScriptManager().progressMessageFont("Stage clear! The way forward is open.");
        }
    }

    /** pt_edNext0: on to the next stage once this one is cleared */
    public void nextStage(Char chr) {
        if (!cleared || finished) {
            chr.getScriptManager().progressMessageFont("Clear the stage first.");
            chr.dispose();
            return;
        }
        chr.warp(fields()[stage]);
    }

    private void finish(Char chr) {
        if (chr.getInstance() != getInstance()) {
            return;
        }
        if (story) {
            var sm = chr.getScriptManager();
            for (int q = EpicDungeon.HM_FIRST_STORY_QUEST; q <= EpicDungeon.HM_STORY_QUEST; q++) {
                if (!chr.getQuestManager().hasQuestCompleted(q)) {
                    sm.completeQuestNoRewards(q);
                }
            }
            getInstance().removeChar(chr, EpicDungeon.HM_ENTRANCE, 0, true, false);
        } else {
            getInstance().removeChar(chr, EpicDungeon.HM_REWARD_ROOM, 0, true, false);
            // the clear reward window (Out 212); Selazar at the summit opens it again
            EventManager.addEvent(() -> {
                if (chr.getFieldID() == EpicDungeon.HM_REWARD_ROOM) {
                    EpicDungeon.openClearWindow(chr);
                }
            }, 1500);
        }
    }

    private void fail(String why) {
        if (finished || cleared) {
            return;
        }
        finished = true;
        cancelPhaseTimer();
        Char chr = chr();
        if (chr == null || chr.getInstance() != getInstance()) {
            return;
        }
        endAltar(false);
        if (flareOn) {
            flareOn = false;
            chr.write(TynosFieldPacket.mitraFlare(false, 0, 0, 0, 0, 0, 0, ""));
        }
        chr.getScriptManager().progressMessageFont(why + (story ? "" : " Your progress up to the last cleared stage is kept."));
        addTimer(EventManager.addEvent(() -> {
            if (chr.getInstance() == getInstance()) {
                getInstance().removeChar(chr, story ? EpicDungeon.HM_ENTRANCE : EpicDungeon.HM_SAVE_ZONE, 0, true, false);
            }
        }, 3000));
    }

    @Override
    public synchronized void onChrDeath(Char chr) {
        if (finished || stage == 0 || kind() == NORMAL) {
            return;
        }
        deathsLeft = Math.max(0, deathsLeft - 1);
        chr.write(UserLocal.deathCountInfo2(chr.getId(), deathsLeft));
        if (deathsLeft == 0) {
            fail("You've fallen too many times.");
        }
    }

    // ------------------------------------------------------------------------------------------- Mitra, Authority

    /** Tab on Mitra's skill bar (In 1529): into Mitra's form and back */
    public void toggleMorph(Char chr) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (tsm.hasStat(CharacterTemporaryStat.Morph)
                && tsm.getOption(CharacterTemporaryStat.Morph).nOption == MITRA_MORPH) {
            tsm.removeStat(CharacterTemporaryStat.Morph, false);
            endUnleash(chr);
        } else {
            Option o = new Option();
            o.nOption = MITRA_MORPH;
            o.rOption = MITRA_SKILLS_NORMAL[4];
            tsm.putCharacterStatValue(CharacterTemporaryStat.Morph, o);
            tsm.sendSetStatPacket();
        }
    }

    private boolean unleashed(Char chr) {
        return chr.getTemporaryStatManager().getOptByCTSAndSkill(CharacterTemporaryStat.IndieBuffIcon, UNLEASH) != null;
    }

    private void endUnleash(Char chr) {
        if (unleashed(chr)) {
            chr.getTemporaryStatManager().removeStatsBySkill(UNLEASH);
        }
    }

    private void sendAuthority(Char chr) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        tsm.putCharacterStatValue(CharacterTemporaryStat.EpicDungeonGauge, new Option(authority, GAUGE_SKILL, 0), true);
        tsm.sendSetStatPacket();
    }

    private synchronized void gainAuthority(Mob mob) {
        Char chr = chr();
        if (story || chr == null || !inStageField(chr) || unleashed(chr)) {
            return;
        }
        int gain = Math.max(1, mob.getMobInfo().getPoint()) * AUTHORITY_PER_POINT;
        int next = Math.min(AUTHORITY_MAX, authority + gain);
        if (next != authority) {
            authority = next;
            sendAuthority(chr);
        }
    }

    /** @return whether the skill was paid for (or free under Unleash Authority) */
    private synchronized boolean spendAuthority(Char chr, int skillId) {
        Integer cost = AUTHORITY_COST.get(skillId);
        if (story || cost == null || !inStageField(chr)) {
            return false;
        }
        if (skillId != UNLEASH && unleashed(chr)) {
            return true;
        }
        if (authority < cost) {
            return false;
        }
        authority -= cost;
        sendAuthority(chr);
        return true;
    }

    @Override
    public void onAttack(Char chr, AttackInfo attackInfo) {
        if (attackInfo != null && attackInfo.skillId != UNLEASH) {
            spendAuthority(chr, attackInfo.skillId);
        }
    }

    @Override
    public void onSkillUse(Char chr, int skillId, int slv) {
        if (skillId != UNLEASH || !spendAuthority(chr, UNLEASH)) {
            return;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        tsm.putCharacterStatValue(CharacterTemporaryStat.IndieBuffIcon, new Option(1, UNLEASH, UNLEASH_SECONDS), true);
        tsm.sendSetStatPacket();
    }

    // ---------------------------------------------------------------------------------------------------- Tynos

    private boolean tynosField(Char chr) {
        return inStageField(chr) && kind() == FINAL && !finished && !cleared;
    }

    private void cancelPhaseTimer() {
        if (phaseTimer != null) {
            phaseTimer.cancel(false);
            phaseTimer = null;
        }
    }

    /** In 1533: the summoning altar was touched */
    public synchronized void onSummonAltar(Char chr) {
        if (tynosField(chr) && tynosState == TynosState.WAIT_ALTAR) {
            summonTynos(chr);
        }
    }

    private synchronized void summonTynos(Char chr) {
        if (!tynosField(chr) || tynosState != TynosState.WAIT_ALTAR) {
            return;
        }
        cancelPhaseTimer();
        tynosState = TynosState.SUMMONING;
        chr.write(TynosFieldPacket.flash());
        chr.write(TynosFieldPacket.spine(6000, SPINE_SUMMON, SPINE + 1, "animation"));
        phaseTimer = addTimer(EventManager.addEvent(() -> spawnTynos(chr), 8000));
    }

    /** In 1531: a spine animation from Out 2212 has run its time */
    public synchronized void onSpineDone(Char chr, int id) {
        if (!tynosField(chr)) {
            return;
        }
        if (id == SPINE_SUMMON) {
            spawnTynos(chr);
        } else if (id == SPINE_MITRA) {
            killTynos(chr);
        }
    }

    private synchronized void spawnTynos(Char chr) {
        if (!tynosField(chr) || tynosState != TynosState.SUMMONING) {
            return;
        }
        cancelPhaseTimer();
        tynos = spawnBoss(chr.getField());
        if (tynos == null) {
            return;
        }
        tynos.getHpTriggerBlockPercs().add(SLAUGHTER_BLOCK);
        tynos.getHpTriggerBlockPercs().add(HELL_BLOCK);
        tynosState = TynosState.FIGHT;
        chr.write(TynosFieldPacket.spine(0, SPINE_TITLE, SPINE + 2, "animation"));
        chr.getScriptManager().progressMessageFont("Tynos has descended!");
        addTimer(EventManager.addFixedRateEvent(this::spawnAdds, ADDS_EVERY_MS, ADDS_EVERY_MS));
    }

    private synchronized void spawnAdds() {
        Char chr = chr();
        if (!tynosField(chr) || tynos == null || tynosState == TynosState.DYING || tynosState == TynosState.DONE) {
            return;
        }
        Field field = chr.getField();
        long adds = field.getMobs().stream().filter(m -> !m.isBoss()).count();
        for (int i = 0; i < ADDS_PER_WAVE && adds < ADDS_MAX; i++, adds++) {
            Position pos = field.getRandomPosOnWalkableFoothold(50, true);
            if (pos != null) {
                field.spawnMob(VISCID_ABYSS, pos.getX(), pos.getY(), false, 0);
            }
        }
    }

    @Override
    public synchronized void onMobDamaged(Char attacker, Mob mob, long damage, long oldHp, long newHp) {
        if (mob != tynos || tynosState != TynosState.FIGHT || mob.getHpTriggerBlockPercs().isEmpty()) {
            return;
        }
        int block = mob.getHpTriggerBlockPercs().peek();
        if (newHp > mob.getMaxHp() * block / 100) {
            return;
        }
        Char chr = chr();
        if (block == SLAUGHTER_BLOCK) {
            startSlaughter(chr);
        } else {
            startHell(chr);
        }
    }

    private synchronized void startSlaughter(Char chr) {
        if (!tynosField(chr) || (tynosState != TynosState.FIGHT && tynosState != TynosState.SLAUGHTER)) {
            return;
        }
        tynosState = TynosState.SLAUGHTER;
        chr.write(TynosFieldPacket.flash());
        chr.write(TynosFieldPacket.phase("slaughterStart"));
        chr.getScriptManager().progressMessageFont("Tynos prepares Slaughter! Destroy the Tynos Fragment!");
        Position pos = chr.getField().getRandomPosOnWalkableFoothold(100, true);
        if (pos == null) {
            pos = chr.getPosition();
        }
        // Mob.wz removeAfter 15: the fragment leaves by itself when time runs out
        fragment = chr.getField().spawnMob(SLAUGHTER_FRAGMENT, pos.getX(), pos.getY(), false, 0);
    }

    /** the Slaughter fragment was destroyed (killed) or ran out of time */
    private void onFragmentGone(boolean killed) {
        Char chr = chr();
        if (!tynosField(chr) || tynosState != TynosState.SLAUGHTER) {
            return;
        }
        if (killed) {
            chr.write(TynosFieldPacket.phase("slaughterSuccess"));
            if (tynos != null && !tynos.getHpTriggerBlockPercs().isEmpty()) {
                tynos.getHpTriggerBlockPercs().remove();
            }
            tynosState = TynosState.FIGHT;
        } else {
            chr.write(TynosFieldPacket.phase("slaughterFail"));
            chr.damagePerc(SLAUGHTER_FAIL_DAMAGE_R);
            cancelPhaseTimer();
            phaseTimer = addTimer(EventManager.addEvent(() -> startSlaughter(chr), RETRY_MS));
        }
    }

    private synchronized void startHell(Char chr) {
        if (!tynosField(chr) || (tynosState != TynosState.FIGHT && tynosState != TynosState.HELL)) {
            return;
        }
        tynosState = TynosState.HELL;
        chr.write(TynosFieldPacket.flash());
        chr.write(TynosFieldPacket.phase("hellStart"));
        chr.getScriptManager().progressMessageFont("Tynos opens Hell! Step into Mitra's Flare!");
        Position pos = chr.getField().getRandomPosOnWalkableFoothold(150, true);
        if (pos == null) {
            pos = chr.getPosition();
        }
        flareOn = true;
        chr.write(TynosFieldPacket.mitraFlare(true, pos.getX(), pos.getY(), FLARE_RECT[0], FLARE_RECT[1], FLARE_RECT[2],
                FLARE_RECT[3], FLARE_SOUND));
        cancelPhaseTimer();
        phaseTimer = addTimer(EventManager.addEvent(() -> hellFail(chr), HELL_SECONDS * 1000L));
    }

    private synchronized void hellFail(Char chr) {
        if (!tynosField(chr) || tynosState != TynosState.HELL || !flareOn) {
            return;
        }
        flareOn = false;
        chr.write(TynosFieldPacket.mitraFlare(false, 0, 0, 0, 0, 0, 0, ""));
        chr.write(TynosFieldPacket.phase("hellFail"));
        chr.damagePerc(HELL_FAIL_DAMAGE_R);
        phaseTimer = addTimer(EventManager.addEvent(() -> startHell(chr), RETRY_MS));
    }

    /** In 1530: the player stands in Mitra's Flare */
    public synchronized void onFlare(Char chr) {
        if (!tynosField(chr) || tynosState != TynosState.HELL || !flareOn) {
            return;
        }
        flareOn = false;
        cancelPhaseTimer();
        tynosState = TynosState.DYING;
        chr.write(TynosFieldPacket.mitraFlare(false, 0, 0, 0, 0, 0, 0, ""));
        chr.write(FieldPacket.fieldEffect(FieldEffect.getFieldEffectFromWz(
                "Effect/HighMountain.img/mitraFlareSuccess/front", 0)));
        chr.write(TynosFieldPacket.spine(10_000, SPINE_MITRA, SPINE + 0, "animation"));
        phaseTimer = addTimer(EventManager.addEvent(() -> killTynos(chr), 12_000));
    }

    private synchronized void killTynos(Char chr) {
        if (!tynosField(chr) || tynosState != TynosState.DYING || tynos == null) {
            return;
        }
        cancelPhaseTimer();
        tynos.getHpTriggerBlockPercs().clear();
        if (!tynos.isDead()) {
            tynos.damage(chr, tynos.getHp());
        }
    }

    // ---------------------------------------------------------------------------------------------- heal altar

    private void scheduleAltar(long delayMs) {
        addTimer(EventManager.addEvent(this::showAltar, delayMs));
    }

    private synchronized void showAltar() {
        Char chr = chr();
        if (finished || cleared || chr == null || !inStageField(chr) || kind() == NORMAL) {
            return;
        }
        if (!altarOn && (kind() != FINAL || tynos != null)) {
            Position pos = chr.getField().getRandomPosOnWalkableFoothold(100, true);
            if (pos != null) {
                altarOn = true;
                altarStepPending = false;
                altarProgress = 0;
                chr.write(HighMountainFieldPacket.altarShow(pos.getX(), pos.getY(), ALTAR_STEPS));
                chr.write(HighMountainFieldPacket.fieldTimer(ALTAR_TIMER, ALTAR_SECONDS * 1000));
            }
        }
        scheduleAltar(ALTAR_EVERY_MS);
    }

    /** In 1488: the player stands on the heal altar; one step per request, answered after a short delay */
    public synchronized void onAltarStep(Char chr) {
        if (!altarOn || altarStepPending || !inStageField(chr)) {
            return;
        }
        altarStepPending = true;
        addTimer(EventManager.addEvent(() -> altarStep(chr), ALTAR_STEP_MS));
    }

    private synchronized void altarStep(Char chr) {
        altarStepPending = false;
        if (!altarOn || !inStageField(chr)) {
            return;
        }
        altarProgress++;
        chr.write(HighMountainFieldPacket.altarProgress(altarProgress));
        if (altarProgress >= ALTAR_STEPS) {
            endAltar(true);
            chr.heal(chr.getMaxHP());
            chr.healMP(chr.getMaxMP());
        }
    }

    /** In 1532: a client timer from Out 847 type 3 ran out */
    public synchronized void onTimerDone(Char chr, int id) {
        if (id == ALTAR_TIMER && altarOn && inStageField(chr)) {
            endAltar(false);
        }
    }

    private void endAltar(boolean success) {
        if (!altarOn) {
            return;
        }
        altarOn = false;
        Char chr = chr();
        if (chr != null && inStageField(chr)) {
            chr.write(HighMountainFieldPacket.altarEnd(success));
        }
    }

    @Override
    public void onChrRemoved(Char chr) {
        super.onChrRemoved(chr);
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (tsm.hasStat(CharacterTemporaryStat.Morph)
                && tsm.getOption(CharacterTemporaryStat.Morph).nOption == MITRA_MORPH) {
            tsm.removeStat(CharacterTemporaryStat.Morph, false);
        }
        endUnleash(chr);
        if (tsm.hasStat(CharacterTemporaryStat.EpicDungeonGauge)) {
            tsm.removeStat(CharacterTemporaryStat.EpicDungeonGauge, false);
        }
        chr.write(FieldPacket.clock(ClockPacket.removeClock()));
        stopTimers();
    }
}
