package net.swordie.ms.connection.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class QueryHolder<T extends Saveable> {

    private static final Logger log = LogManager.getLogger();

    private T element;
    private String query;
    private Object[] args;
    private boolean insert;
    private boolean needsId = true; // default to true
    private int id;
    private boolean clean;

    public static QueryHolder clean(){
        var qh = new QueryHolder(null, false);
        qh.setClean(true);
        return qh;
    }

    public QueryHolder(T element, boolean insert) {
        this.element = element;
        this.insert = insert;
    }

    public QueryHolder(T element, boolean insert, boolean needsId) {
        this.element = element;
        this.insert = insert;
        this.needsId = needsId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object... args) {
        this.args = args;
    }

    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    public boolean needsId() {
        return needsId;
    }

    public void setNeedsId(boolean needsId) {
        this.needsId = needsId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public T getElement() {
        return element;
    }

    public void setClean(boolean clean) {
        this.clean = clean;
    }

    public boolean isClean() {
        return clean;
    }

    @Override
    public String toString() {
        return "QueryHolder{" +
                "isInsert=" + insert +
                ", query='" + query + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }

    public void assignId(long id) {
        if (needsId()) {
            getElement().setId(id);
        }
    }

    /** A rolled back insert: a generated id it got is not in the table, so the next save inserts the row again. */
    public void unassignId() {
        if (needsId() && getElement() != null) {
            getElement().setId(0);
        }
    }

    /**
     * Run if this query's statement fails (e.g. to mark a new row as not yet inserted again).
     */
    private Runnable onFailure;

    public void setOnFailure(Runnable onFailure) {
        this.onFailure = onFailure;
    }

    public void failed() {
        if (onFailure != null) {
            try {
                onFailure.run();
            } catch (Exception e) {
                log.error("Could not mark a failed insert for the next save: " + this, e);
            }
        }
    }

    // the element's state when its query arguments were taken; stored as "saved" once the write succeeded, so a
    // change made while the batch was running still counts as unsaved
    private Integer pendingHash;

    public void captureHash() {
        var element = getElement();
        if (element instanceof TrackedObject) {
            pendingHash = ((TrackedObject) element).getTransactionalHash();
        }
    }

    public void setLastSavedHash() {
        var element = getElement();
        if (element instanceof TrackedObject) {
            if (pendingHash != null) {
                ((TrackedObject) element).setLastSavedHash(pendingHash);
            } else {
                ((TrackedObject) element).setLastSavedHash();
            }
        }
    }
}
