package net.swordie.ms.connection.db;

public abstract class TrackedObject implements Saveable {

    private int lastSavedHash;

    public int getLastSavedHash() {
        return lastSavedHash;
    }

    public void setLastSavedHash() {
        this.lastSavedHash = getTransactionalHash();
    }

    public void setLastSavedHash(int hash) {
        this.lastSavedHash = hash;
    }

    public boolean isClean() {
        return getId() != 0 && getLastSavedHash() == getTransactionalHash();
    }

    public abstract int getTransactionalHash();

}
