package net.swordie.ms.world.field.instance.instancehelper.epic;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.packet.MobPool;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.DeathType;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.ClockPacket;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Foothold;
import net.swordie.ms.world.field.Portal;
import net.swordie.ms.world.field.fieldeffect.FieldEffect;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

/**
 * Nightmare Paradise (Epic Dungeon 3, solo, Lv. 280; Etc.wz MapleScheduler: record 63, clearStage 7). Normal mode: the
 * seven stages of resources/data/nightmare-paradise-stages.tsv, from Abandoned Shrine 1 to the Burning Pavilion. Story
 * mode (until quest 27143) runs the six story maps 993274000-993274500 and ends with quests 27141-27143 done.
 * <p>
 * The stage puzzles (the barrier scroll, slashes, the key on the cliff: Effect.wz nightmareParadise.img) are run by
 * the client and their packets aren't known, so a stage here is cleared by its monsters: a hunt stage by a kill count,
 * a boss stage by defeating the boss. Progress is saved per stage like the other Epic Dungeons.
 */
public class NightmareParadiseInstanceHelper extends InstanceHelper {
    public static final String STORY = "ed3Story";
    public static final String FIRST_STAGE = "ed3FirstStage";

    private static final List<DataTable.Row> NORMAL = stageRows("normal");
    private static final List<DataTable.Row> STORY_ROWS = stageRows("story");

    private static final int STAGE_SECONDS = 480;
    private static final int BOSS_SECONDS = 600;
    private static final int MAX_DEATHS = 5;
    private static final int HUNT_KILLS = 40, STORY_KILLS = 20;
    private static final int BOSS_OFFSET_X = 300;

    private boolean story;
    private int stage; // 1-based, 0 = not started
    private int initializedField;
    private int kills;
    private int missionMax;
    private int deathsLeft;
    private boolean cleared;
    private boolean finished;
    private ScheduledFuture stageTimer;

    private static List<DataTable.Row> stageRows(String mode) {
        return DataTable.read("nightmare-paradise-stages").rows().stream()
                .filter(r -> r.str("mode").equals(mode)).toList();
    }

    public static boolean isField(int fieldId) {
        return stageOf(NORMAL, fieldId) >= 0 || stageOf(STORY_ROWS, fieldId) >= 0;
    }

    public static int stageField(boolean story, int stage) {
        List<DataTable.Row> rows = story ? STORY_ROWS : NORMAL;
        return rows.get(Math.max(0, Math.min(stage, rows.size()) - 1)).i("field");
    }

    private static int stageOf(List<DataTable.Row> rows, int fieldId) {
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).i("field") == fieldId) {
                return i;
            }
        }
        return -1;
    }

    private List<DataTable.Row> rows() {
        return story ? STORY_ROWS : NORMAL;
    }

    private DataTable.Row row() {
        return rows().get(stage - 1);
    }

    private boolean bossStage() {
        return "boss".equals(row().str("kind"));
    }

    private Char chr() {
        return getInstance().getChr();
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
        if (newField != null && newField.getInstance() == getInstance() && stageOf(rows(), newField.getId()) >= 0) {
            addTimer(EventManager.addEvent(() -> initStage(newField), 1000));
        }
    }

    private synchronized void initStage(Field field) {
        if (finished || field == null || field.getId() == initializedField) {
            return;
        }
        int idx = stageOf(rows(), field.getId());
        Char chr = chr();
        if (idx < 0 || chr == null || chr.getField() != field) {
            return;
        }
        initializedField = field.getId();
        stage = idx + 1;
        kills = 0;
        cleared = false;
        if (stageTimer != null) {
            stageTimer.cancel(false);
        }
        boolean boss = bossStage();
        int seconds = boss ? BOSS_SECONDS : STAGE_SECONDS;
        missionMax = boss ? 0 : story ? STORY_KILLS : HUNT_KILLS;
        deathsLeft = boss ? MAX_DEATHS : 0;
        int ui = row().i("ui");
        if (!story && ui > 0) {
            chr.write(UserLocal.epicDungeonStageUi(ui, stage - 1, true, 0, deathsLeft));
            chr.write(FieldPacket.fieldEffect(FieldEffect.getFieldEffectFromWz(
                    "Effect/nightmareParadise.img/stageEnter/" + stage, 0)));
        }
        chr.write(FieldPacket.clock(ClockPacket.secondsClock(seconds)));
        if (deathsLeft > 0) {
            chr.write(UserLocal.deathCountInfo2(chr.getId(), deathsLeft));
        }
        if (boss) {
            spawnBoss(field);
        }
        chr.getScriptManager().progressMessageFont("Stage " + stage + ": " + (boss
                ? "Defeat the contaminated Four Symbol." : "Defeat " + missionMax + " contaminated monsters."));
        stageTimer = addTimer(EventManager.addEvent(() -> fail("Time's up!"), seconds * 1000L));
    }

    private void spawnBoss(Field field) {
        int id = row().i("boss");
        if (id == 0) {
            return;
        }
        Portal sp = field.getInfo().getDefaultPortal();
        int x = (sp == null ? 0 : sp.getX()) + BOSS_OFFSET_X;
        int y = sp == null ? 0 : sp.getY();
        Foothold fh = field.findFootHoldBelow(new Position(x, y));
        if (fh != null) {
            y = fh.getYFromX(x);
        }
        field.spawnMob(id, x, y, false, 0);
    }

    @Override
    public synchronized void onMobDeath(Field field, Mob mob) {
        if (finished || cleared || stage == 0 || stageOf(rows(), field.getId()) != stage - 1 || mob.getHp() > 0) {
            return;
        }
        if (!bossStage()) {
            if (mob.isBoss()) {
                return;
            }
            kills++;
            Char chr = chr();
            if (chr != null && (kills % 10 == 0 || kills == missionMax) && kills <= missionMax) {
                chr.getScriptManager().progressMessageFont("Monsters defeated: " + kills + "/" + missionMax);
            }
            if (kills >= missionMax) {
                clearStage();
            }
        } else if (mob.getTemplateId() == row().i("boss")) {
            for (Mob m : new ArrayList<>(field.getMobs())) {
                if (m != mob) {
                    field.removeLife(m.getObjectId());
                    field.broadcastPacket(MobPool.leaveField(m.getObjectId(), DeathType.ANIMATION_DEATH));
                }
            }
            clearStage();
        }
    }

    private void clearStage() {
        cleared = true;
        if (stageTimer != null) {
            stageTimer.cancel(false);
        }
        Char chr = chr();
        if (chr == null) {
            return;
        }
        chr.write(FieldPacket.clock(ClockPacket.removeClock()));
        if (!story) {
            EpicDungeon.setClearedStages(chr, EpicDungeon.Dungeon.NIGHTMARE, stage);
        }
        if (stage >= rows().size()) {
            finished = true;
            chr.getScriptManager().progressMessageFont(story ? "The Scroll of Purification is used. Baekyeon's tale is told."
                    : "You've cleansed Nightmare Paradise!");
            addTimer(EventManager.addEvent(() -> finish(chr), 4000));
        } else {
            chr.getScriptManager().progressMessageFont("This stage is clear. Please use the portal that leads to the next stage.");
        }
    }

    /** pt_edNext2: on to the next stage once this one is cleared */
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
            for (int q = EpicDungeon.NP_FIRST_STORY_QUEST; q <= EpicDungeon.NP_STORY_QUEST; q++) {
                if (!chr.getQuestManager().hasQuestCompleted(q)) {
                    sm.completeQuestNoRewards(q);
                }
            }
            getInstance().removeChar(chr, EpicDungeon.NP_ENTRANCE, 0, true, false);
        } else {
            getInstance().removeChar(chr, EpicDungeon.NP_REWARD_ROOM, 0, true, false);
            // the clear reward window (Out 212); Selazar in the exit room opens it again
            EventManager.addEvent(() -> {
                if (chr.getFieldID() == EpicDungeon.NP_REWARD_ROOM) {
                    EpicDungeon.openClearWindow(chr, EpicDungeon.Dungeon.NIGHTMARE);
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
                getInstance().removeChar(chr, story ? EpicDungeon.NP_ENTRANCE : EpicDungeon.NP_SAVE_ZONE,
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

    @Override
    public void onChrRemoved(Char chr) {
        super.onChrRemoved(chr);
        chr.write(FieldPacket.clock(ClockPacket.removeClock()));
        stopTimers();
    }
}
