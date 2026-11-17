package net.swordie.ms.client.character.erdalink;

import net.swordie.ms.connection.db.TrackedObject;

import java.util.Objects;

/** An activated Erda Link stone and its level. */
public class ErdaLinkStone extends TrackedObject {

    private long id;
    private int stoneId;
    private int level;

    public ErdaLinkStone(int stoneId, int level) {
        this.stoneId = stoneId;
        this.level = level;
    }

    @Override
    public int getTransactionalHash() {
        return Objects.hash(stoneId, level);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public int getStoneId() {
        return stoneId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
