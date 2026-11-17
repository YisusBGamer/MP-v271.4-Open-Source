package net.swordie.ms.client.character.quest;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.ext.ServerHooks;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.scripts.ScriptManagerImpl;
import net.swordie.ms.scripts.ScriptType;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Portal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Bulk quest completions without a "Quest complete!" notice per quest.
 * <p>
 * v271 client: every MESSAGE (179) type 1 record with state 2 runs the quest-complete handling, notice included
 * (only a fixed list of quest ids is exempt); the record carries no flag to keep it quiet. Records that arrive in the
 * CharacterData of a SET_FIELD (completed list) load silently, which is how a character created past its story gets
 * them. So the records are kept back ({@link QuestManager#isQuiet}) and go out with the CharacterData of the next field
 * entry: the skip's own warp, or a reload of the same map once the player is idle.
 */
public final class QuietQuests {

    private static final Logger log = LogManager.getLogger(QuietQuests.class);

    /** Fewer records than this are just sent (a notice or two beats a map reload). */
    private static final int MIN_FOR_RELOAD = 3;
    private static final long HOLD_MS = 60_000;
    private static final int IDLE_TRIES = 60;

    private QuietQuests() {
    }

    private static QuestManager qm(Char chr) {
        return chr == null || ServerHooks.isAutomated(chr) ? null : chr.getQuestManager();
    }

    public static void begin(Char chr) {
        QuestManager qm = qm(chr);
        if (qm != null) {
            qm.beginQuiet();
        }
    }

    /**
     * Ends a {@link #begin}.
     *
     * @param reload reload the map once idle; otherwise the next map change carries the records (a skip during play)
     */
    public static void end(Char chr, boolean reload) {
        QuestManager qm = qm(chr);
        if (qm == null) {
            return;
        }
        qm.endQuiet();
        deliver(chr, qm, reload);
    }

    private static void deliver(Char chr, QuestManager qm, boolean reload) {
        if (qm.isQuiet() || qm.getUntoldCount() == 0) {
            return;
        }
        if (qm.getUntoldCount() < MIN_FOR_RELOAD) {
            qm.sendUntold();
        } else if (reload) {
            reloadWhenIdle(chr, 0);
        }
    }

    /** Reloads the map once idle if records are still kept back (a skip asked for in a dialog). */
    public static void reloadSoon(Char chr) {
        QuestManager qm = qm(chr);
        if (qm != null) {
            deliver(chr, qm, true);
        }
    }

    /** Keeps records back until the next field change (a tutorial's skip, which warps at its end). */
    public static void holdUntilWarp(Char chr) {
        QuestManager qm = qm(chr);
        if (qm == null) {
            return;
        }
        qm.holdForNextField();
        EventManager.addEvent(() -> {
            if (qm.isHeldForField()) { // the script didn't warp
                qm.releaseHold();
                deliver(chr, qm, true);
            }
        }, HOLD_MS);
    }

    private static boolean busy(Char chr) {
        ScriptManagerImpl sm = chr.getScriptManager();
        return sm.isActive(ScriptType.Npc) || sm.isActive(ScriptType.Quest) || sm.isActive(ScriptType.Field)
                || sm.isActive(ScriptType.FirstEnterField) || sm.isWaitingForAnswer() || sm.isUiLocked();
    }

    private static void reloadWhenIdle(Char chr, int tries) {
        EventManager.addEvent(() -> {
            try {
                QuestManager qm = chr.getQuestManager();
                Field field = chr.getField();
                if (!chr.isOnline() || chr.getClient() == null || field == null || qm == null
                        || qm.getUntoldCount() == 0 || qm.isQuiet()) {
                    return;
                }
                if (chr.getInstance() != null || field.getInstance() != null) {
                    return; // the next map change out of it carries them
                }
                if (busy(chr) || chr.getHP() <= 0) {
                    if (tries < IDLE_TRIES) {
                        reloadWhenIdle(chr, tries + 1);
                    }
                    return;
                }
                chr.warp(field, nearestPortal(field, chr.getPosition()), true);
            } catch (Exception e) {
                log.error("Could not reload the map for the quest records of " + chr.getName(), e);
            }
        }, 1000);
    }

    private static Portal nearestPortal(Field field, Position pos) {
        Portal best = field.getInfo().getPortalByName("sp");
        if (pos == null) {
            return best;
        }
        double bestDist = best == null ? Double.MAX_VALUE : pos.distanceTo(new Position(best.getX(), best.getY()));
        for (Portal p : field.getInfo().getPortals()) {
            double d = pos.distanceTo(new Position(p.getX(), p.getY()));
            if (d < bestDist) {
                best = p;
                bestDist = d;
            }
        }
        return best;
    }
}
