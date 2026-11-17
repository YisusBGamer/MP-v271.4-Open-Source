package net.swordie.ms.connection.db;

import java.util.ArrayList;
import java.util.List;

public class BatchQueryHolder<T extends TrackedObject> {

    List<QueryHolder> insertQueryInfo = new ArrayList<>();
    List<QueryHolder> updateQueryInfo = new ArrayList<>();

    // each object is written once per batch (its "saved" mark is only set after the batch ran)
    private final java.util.Set<Object> elements = java.util.Collections.newSetFromMap(new java.util.IdentityHashMap<>());

    public void addQueryInfo(QueryHolder<T> queryHolder) {
        if (!queryHolder.isClean()) {
            if (queryHolder.getElement() != null && !elements.add(queryHolder.getElement())) {
                return;
            }
            queryHolder.captureHash();
            if (queryHolder.isInsert()) {
                insertQueryInfo.add(queryHolder);
            } else {
                updateQueryInfo.add(queryHolder);
            }
            // marked as saved by the database manager once the statement has actually succeeded (marking it here
            // made a failed write look saved, so it was never retried and the change was lost)
        }
    }

    public List<QueryHolder> getInsertQueryInfo() {
        return insertQueryInfo;
    }

    public List<QueryHolder> getUpdateQueryInfo() {
        return updateQueryInfo;
    }

    public String getInsertString() {
        return getInsertQueryInfo().get(0).getQuery();
    }

    public String getUpdateString() {
        return getUpdateQueryInfo().get(0).getQuery();
    }

    public boolean isEmpty() {
        return insertQueryInfo.size() == 0 && updateQueryInfo.size() == 0;
    }

    @Override
    public String toString() {
        return "BatchQueryHolder{" +
                "insertQueryInfo=" + insertQueryInfo +
                ", updateQueryInfo=" + updateQueryInfo +
                '}';
    }

    public boolean hasInserts() {
        return getInsertQueryInfo().size() > 0;
    }

    public boolean hasUpdates() {
        return getUpdateQueryInfo().size() > 0;
    }
}
