package net.swordie.ms.connection.db;

import java.util.ArrayList;
import java.util.List;

/**
 * The parts of one save (a character, an account, a login). Every part is written even when an earlier one failed,
 * so one broken table never stops the rest from saving; {@link #finish()} then throws a DatabaseException naming the
 * parts that weren't written. Their rows stay unsaved and are written again by the next save.
 */
public final class SaveSteps {

    private final String what;
    private final List<String> failed = new ArrayList<>();

    public SaveSteps(String what) {
        this.what = what;
    }

    public void run(String part, Runnable step) {
        try {
            step.run();
        } catch (RuntimeException e) {
            failed.add(part + " (" + e.getMessage() + ")");
        }
    }

    /** A part later parts depend on (a new row's id): a failure stops the save here. */
    public void required(String part, Runnable step) {
        run(part, step);
        finish();
    }

    public void finish() {
        if (!failed.isEmpty()) {
            throw new DatabaseException(what + " partly saved, not written: " + String.join(", ", failed));
        }
    }
}
