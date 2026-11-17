package net.swordie.ms.world.field.instance.instancehelper.epic;

import java.util.List;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.connection.packet.field.AnglerFieldPacket;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.connection.packet.MobPool;
import net.swordie.ms.connection.packet.field.FieldPacket;
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
 * Angler Company (Epic Dungeon 2, solo, Lv. 270). Normal mode: 8 stages - Top Floor Area 1 (one of three maps,
 * fieldType 374: checkpoints, hacking modules, the Proto-Suit terminal), Area 2 (372: the ladder puzzle and the
 * Prototype Centurion), Area 3 (375: platforms and lasers), the Central Office (388: Angler Duke), the Bottom Floor's
 * three areas (374 / 373 / 375) and the Deepest Part of the Headquarters (384: Triton). Story mode (until quest 27114):
 * the story maps 993254000-993254500 (374, 372, 388, 374, 373, 384), ending with quests 27112-27114 done.
 * <p>
 * The client runs each stage's puzzle objects itself (Out 847/849/850, In 1765/1766); their bodies are only partly
 * known, so a stage here is cleared by its monsters: Areas 1 and 3 by a kill count, Area 2, the Central Office and
 * the Deepest Part by defeating their boss. Stage windows (Out 1272 uiType 3/4/5) are normal mode only: the client
 * ignores them in story maps (0x1405e41f0). Progress is saved per stage like High Mountain.
 * <p>
 * Charges (CharacterTemporaryStat EpicDungeonGauge, n = charges) come from defeated monsters (Mob.wz point x 10) and
 * pay for Rewire (Skill.wz edCon) and the Angler Proto-Suit (500, In 1761 from the terminal object, Out 849 type 4).
 * Skill bars (UITemporarySkillBar 0x143bf6d00): normal 72 (374), 73 (372), 76 (373), 74 (375, and the Proto-Suit),
 * 75 (388), 79 (384); story 77 (374), 71 (372/373), 78 (388), 80 (384).
 */
public class AnglerCompanyInstanceHelper extends InstanceHelper {
    public static final String STORY = "ed2Story";
    public static final String FIRST_STAGE = "ed2FirstStage";

    private static final int MODULE = 0, PUZZLE = 1, PLATFORM = 2, OFFICE = 3, FINAL = 4;
    // index = the kind constants above
    private static final List<String> KINDS = List.of("module", "puzzle", "platform", "office", "final");
    private static final int[][] NORMAL_FIELDS = stageRows("normal").map(r -> r.ints("fields")).toArray(int[][]::new);
    private static final int[] NORMAL_KIND = stageRows("normal").mapToInt(r -> KINDS.indexOf(r.str("kind"))).toArray();
    /** Prototype Centurion, Angler Duke, Triton */
    private static final int[] NORMAL_BOSS = stageRows("normal").mapToInt(r -> r.i("boss")).toArray();
    private static final int[] NORMAL_BAR = stageRows("normal").mapToInt(r -> r.i("bar")).toArray();
    private static final int[][] STORY_FIELDS = stageRows("story").map(r -> r.ints("fields")).toArray(int[][]::new);
    private static final int[] STORY_KIND = stageRows("story").mapToInt(r -> KINDS.indexOf(r.str("kind"))).toArray();
    private static final int[] STORY_BOSS = stageRows("story").mapToInt(r -> r.i("boss")).toArray();
    private static final int[] STORY_BAR = stageRows("story").mapToInt(r -> r.i("bar")).toArray();

    private static final int TELEPORT = 80003665, ENERGY_SURGE = 80003667, REWIRE_STORY = 80003668,
            REWIRE = SkillConstants.REWIRE, PROTO_SUIT = SkillConstants.ANGLER_PROTO_SUIT, PROTO_CANNON = SkillConstants.PROTO_SUIT_CANNON, SHIELD = SkillConstants.MATTER_ANTIMATTER_SHIELD;
    /** the skills each bar shows (its negative keys come from UI.wz) */
    private static final Map<Integer, int[]> BAR_SKILLS = Map.of(
            72, new int[0], 73, new int[]{REWIRE}, 74, new int[]{PROTO_SUIT, PROTO_CANNON}, 75, new int[]{SHIELD},
            76, new int[]{REWIRE}, 79, new int[0],
            77, new int[]{TELEPORT, ENERGY_SURGE}, 71, new int[]{TELEPORT, ENERGY_SURGE, REWIRE_STORY},
            78, new int[]{TELEPORT, ENERGY_SURGE, SHIELD}, 80, new int[]{TELEPORT, ENERGY_SURGE});
    private static final int PROTO_BAR = 74;

    private static final int STAGE_SECONDS = 480;
    private static final int BOSS_SECONDS = 600;
    private static final int MAX_DEATHS = 5;
    private static final int MODULE_KILLS = 30, PLATFORM_KILLS = 40, STORY_KILLS = 20;
    private static final int BOSS_OFFSET_X = 400;

    // charges
    private static final int CHARGE_MAX = 1000;
    private static final int CHARGE_PER_POINT = 10;
    /** Skill.wz 800036 edCon */
    private static final Map<Integer, Integer> CHARGE_COST = Map.of(REWIRE_STORY, 200, REWIRE, 300);
    private static final int PROTO_COST = 500;
    private static final int PROTO_SECONDS = 30;
    private static final int PROTO_TERMINAL = 1;
    private static final int OBJ_PROTO_TERMINAL = 6;

    private boolean story;
    private int stage; // 1-based, 0 = not started
    private int initializedField;
    private int kills;
    private int missionMax;
    private int deathsLeft;
    private boolean cleared;
    private boolean finished;
    private ScheduledFuture stageTimer;
    private ScheduledFuture protoTimer;
    private int charges;
    private boolean protoSuit;

    private static java.util.stream.Stream<DataTable.Row> stageRows(String mode) {
        return DataTable.read("angler-company-stages").rows().stream().filter(r -> r.str("mode").equals(mode));
    }

    public static boolean isField(int fieldId) {
        return stageOf(NORMAL_FIELDS, fieldId) >= 0 || stageOf(STORY_FIELDS, fieldId) >= 0;
    }

    /** @return the stage's map; Areas 1 of each floor pick one of their three maps */
    public static int stageField(boolean story, int stage) {
        int[][] fields = story ? STORY_FIELDS : NORMAL_FIELDS;
        int[] options = fields[Math.max(0, Math.min(stage, fields.length) - 1)];
        return options[(int) (Math.random() * options.length)];
    }

    private static int stageOf(int[][] fields, int fieldId) {
        for (int i = 0; i < fields.length; i++) {
            for (int f : fields[i]) {
                if (f == fieldId) {
                    return i;
                }
            }
        }
        return -1;
    }

    private int[][] fields() {
        return story ? STORY_FIELDS : NORMAL_FIELDS;
    }

    private int stageCount() {
        return fields().length;
    }

    private int kind() {
        return (story ? STORY_KIND : NORMAL_KIND)[stage - 1];
    }

    private int boss() {
        return (story ? STORY_BOSS : NORMAL_BOSS)[stage - 1];
    }

    private int bar() {
        return (story ? STORY_BAR : NORMAL_BAR)[stage - 1];
    }

    private boolean bossStage() {
        return kind() == PUZZLE || kind() == OFFICE || kind() == FINAL;
    }

    private Char chr() {
        return getInstance().getChr();
    }

    private boolean inStageField(Char chr) {
        return chr != null && stage > 0 && chr.getField() != null && chr.getInstance() == getInstance()
                && stageOf(fields(), chr.getFieldID()) == stage - 1;
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
        if (newField != null && newField.getInstance() == getInstance() && stageOf(fields(), newField.getId()) >= 0) {
            addTimer(EventManager.addEvent(() -> initStage(newField), 1000));
        }
    }

    private synchronized void initStage(Field field) {
        if (finished || field == null || field.getId() == initializedField) {
            return;
        }
        int idx = stageOf(fields(), field.getId());
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
        endProtoSuit(chr, false);
        int kind = kind();
        int seconds = bossStage() ? BOSS_SECONDS : STAGE_SECONDS;
        missionMax = bossStage() ? 0 : story ? STORY_KILLS : kind == MODULE ? MODULE_KILLS : PLATFORM_KILLS;
        deathsLeft = kind == OFFICE || kind == FINAL ? MAX_DEATHS : 0;

        if (!story) {
            int uiType = kind == FINAL ? 5 : kind == OFFICE ? 4 : 3;
            chr.write(UserLocal.epicDungeonStageUi(uiType, stage - 1, true, 0, deathsLeft));
            chr.write(FieldPacket.fieldEffect(FieldEffect.getFieldEffectFromWz(
                    "Effect/AnglerCompany.img/stageEnter/" + stage, 0)));
        }
        chr.write(FieldPacket.clock(ClockPacket.secondsClock(seconds)));
        if (deathsLeft > 0) {
            chr.write(UserLocal.deathCountInfo2(chr.getId(), deathsLeft));
        }
        if (bossStage()) {
            spawnBoss(field);
        }
        if (kind == MODULE) {
            chr.write(AnglerFieldPacket.objects(new int[][]{{OBJ_PROTO_TERMINAL, PROTO_TERMINAL, PROTO_COST, 1}}));
        }
        sendBar(chr, bar());
        sendCharges(chr);
        String goal = switch (kind) {
            case MODULE, PLATFORM -> "Defeat " + missionMax + " Angler Company guards.";
            case PUZZLE -> "Defeat the Prototype Centurion.";
            case OFFICE -> "Defeat the Angler Duke.";
            default -> "Defeat Triton!";
        };
        chr.getScriptManager().progressMessageFont("Stage " + stage + ": " + goal);
        stageTimer = addTimer(EventManager.addEvent(() -> fail("Time's up!"), seconds * 1000L));
    }

    private void sendBar(Char chr, int bar) {
        chr.write(FieldPacket.setTemporarySkillSet(bar, BAR_SKILLS.getOrDefault(bar, new int[0])));
    }

    private Mob spawnBoss(Field field) {
        int id = boss();
        if (id == 0) {
            return null;
        }
        Portal sp = field.getInfo().getDefaultPortal();
        int x = sp == null ? 0 : sp.getX();
        int y = sp == null ? 0 : sp.getY();
        if (kind() != FINAL) {
            x += BOSS_OFFSET_X;
        }
        Foothold fh = field.findFootHoldBelow(new Position(x, y));
        if (fh != null) {
            y = fh.getYFromX(x);
        }
        return field.spawnMob(id, x, y, false, 0);
    }

    @Override
    public synchronized void onMobDeath(Field field, Mob mob) {
        if (finished || stage == 0 || stageOf(fields(), field.getId()) != stage - 1) {
            return;
        }
        boolean killed = mob.getHp() <= 0;
        if (killed && !mob.isBoss()) {
            gainCharges(mob);
        }
        if (cleared) {
            return;
        }
        if (!bossStage()) {
            if (mob.isBoss() || !killed) {
                return;
            }
            kills++;
            Char chr = chr();
            if (chr != null && (kills % 10 == 0 || kills == missionMax) && kills <= missionMax) {
                chr.getScriptManager().progressMessageFont("Guards defeated: " + kills + "/" + missionMax);
            }
            if (kills >= missionMax) {
                clearStage(field);
            }
        } else if (mob.getTemplateId() == boss()) {
            for (Mob m : new java.util.ArrayList<>(field.getMobs())) {
                if (m != mob && m.isBoss()) {
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
        chr.write(FieldPacket.clock(ClockPacket.removeClock()));
        chr.write(FieldPacket.fieldEffect(FieldEffect.getFieldEffectFromWz("Effect/AnglerCompany.img/common/clearEff", 0)));
        if (!story) {
            EpicDungeon.setClearedStages(chr, EpicDungeon.Dungeon.ANGLER, stage);
        }
        if (stage >= stageCount()) {
            finished = true;
            chr.getScriptManager().progressMessageFont(story ? "Cosim is safe. Senya's tale is told."
                    : "You've reached the depths of the Angler Company!");
            addTimer(EventManager.addEvent(() -> finish(chr), 4000));
        } else {
            chr.getScriptManager().progressMessageFont("This stage is clear. Please use the portal that leads to the next stage.");
        }
    }

    /** pt_edNext1: on to the next stage once this one is cleared */
    public void nextStage(Char chr) {
        if (!cleared || finished) {
            chr.getScriptManager().progressMessageFont("Clear the stage first.");
            chr.dispose();
            return;
        }
        chr.warp(stageField(story, stage + 1));
    }

    private void finish(Char chr) {
        if (chr.getInstance() != getInstance()) {
            return;
        }
        if (story) {
            var sm = chr.getScriptManager();
            for (int q = EpicDungeon.ANGLER_FIRST_STORY_QUEST; q <= EpicDungeon.ANGLER_STORY_QUEST; q++) {
                if (!chr.getQuestManager().hasQuestCompleted(q)) {
                    sm.completeQuestNoRewards(q);
                }
            }
            getInstance().removeChar(chr, EpicDungeon.ANGLER_ENTRANCE, 0, true, false);
        } else {
            getInstance().removeChar(chr, EpicDungeon.ANGLER_REWARD_ROOM, 0, true, false);
            // the clear reward window (Out 212); Selazar in the exit room opens it again
            EventManager.addEvent(() -> {
                if (chr.getFieldID() == EpicDungeon.ANGLER_REWARD_ROOM) {
                    EpicDungeon.openClearWindow(chr, EpicDungeon.Dungeon.ANGLER);
                }
            }, 1500);
        }
    }

    private void fail(String why) {
        if (finished || cleared) {
            return;
        }
        finished = true;
        Char chr = chr();
        if (chr == null || chr.getInstance() != getInstance()) {
            return;
        }
        chr.getScriptManager().progressMessageFont(why + (story ? "" : " Your progress up to the last cleared stage is kept."));
        addTimer(EventManager.addEvent(() -> {
            if (chr.getInstance() == getInstance()) {
                getInstance().removeChar(chr, story ? EpicDungeon.ANGLER_ENTRANCE : EpicDungeon.ANGLER_SAVE_ZONE,
                        0, true, false);
            }
        }, 3000));
    }

    @Override
    public synchronized void onChrDeath(Char chr) {
        if (finished || stage == 0 || deathsLeft <= 0) {
            return;
        }
        deathsLeft = Math.max(0, deathsLeft - 1);
        chr.write(UserLocal.deathCountInfo2(chr.getId(), deathsLeft));
        if (deathsLeft == 0) {
            fail("You've fallen too many times.");
        }
    }

    // ------------------------------------------------------------------------------------- charges, Proto-Suit

    private void sendCharges(Char chr) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        tsm.putCharacterStatValue(CharacterTemporaryStat.EpicDungeonGauge, new Option(charges, PROTO_SUIT, 0), true);
        tsm.sendSetStatPacket();
    }

    private synchronized void gainCharges(Mob mob) {
        Char chr = chr();
        if (chr == null || !inStageField(chr)) {
            return;
        }
        int gain = Math.max(1, mob.getMobInfo().getPoint()) * CHARGE_PER_POINT;
        int next = Math.min(CHARGE_MAX, charges + gain);
        if (next != charges) {
            charges = next;
            sendCharges(chr);
        }
    }

    @Override
    public synchronized void onAttack(Char chr, AttackInfo attackInfo) {
        if (attackInfo == null || !inStageField(chr)) {
            return;
        }
        Integer cost = CHARGE_COST.get(attackInfo.skillId);
        if (cost != null && charges >= cost) {
            charges -= cost;
            sendCharges(chr);
        }
    }

    /** In 1761: the Proto-Suit terminal (849 object type 6) with 500 charges */
    public synchronized void onProtoSuit(Char chr, int objectId) {
        if (finished || !inStageField(chr) || kind() != MODULE || objectId != PROTO_TERMINAL || protoSuit) {
            return;
        }
        if (charges < PROTO_COST) {
            chr.chatMessage("500 charges are required to activate the Angler Proto-Suit.");
            return;
        }
        charges -= PROTO_COST;
        sendCharges(chr);
        protoSuit = true;
        sendBar(chr, PROTO_BAR);
        chr.getScriptManager().progressMessageFont("Angler Proto-Suit online!");
        protoTimer = addTimer(EventManager.addEvent(() -> endProtoSuit(chr, true), PROTO_SECONDS * 1000L));
    }

    private synchronized void endProtoSuit(Char chr, boolean restoreBar) {
        if (protoTimer != null) {
            protoTimer.cancel(false);
            protoTimer = null;
        }
        if (!protoSuit) {
            return;
        }
        protoSuit = false;
        if (restoreBar && inStageField(chr) && !finished) {
            sendBar(chr, bar());
        }
    }

    @Override
    public void onChrRemoved(Char chr) {
        super.onChrRemoved(chr);
        protoSuit = false;
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (tsm.hasStat(CharacterTemporaryStat.EpicDungeonGauge)) {
            tsm.removeStat(CharacterTemporaryStat.EpicDungeonGauge, false);
        }
        chr.write(FieldPacket.setTemporarySkillSet(0));
        chr.write(FieldPacket.clock(ClockPacket.removeClock()));
        stopTimers();
    }
}
