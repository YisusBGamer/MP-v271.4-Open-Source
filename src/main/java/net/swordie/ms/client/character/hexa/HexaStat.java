package net.swordie.ms.client.character.hexa;

import net.swordie.ms.connection.db.TrackedObject;

import java.util.Arrays;
import java.util.Objects;

/**
 * A HEXA Stat node: three stat types (main + two additional, type ids 0-6, -1 = none) and their levels.
 */
public class HexaStat extends TrackedObject {

    private long id;
    private int coreId;
    private final int[] types = {-1, -1, -1};
    private final int[] levels = new int[3];

    public HexaStat() {
    }

    public HexaStat(int coreId) {
        this.coreId = coreId;
    }

    public int getTotalLevel() {
        return levels[0] + levels[1] + levels[2];
    }

    @Override
    public int getTransactionalHash() {
        return Objects.hash(coreId, Arrays.hashCode(types), Arrays.hashCode(levels));
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public int getCoreId() {
        return coreId;
    }

    public void setCoreId(int coreId) {
        this.coreId = coreId;
    }

    public int getType(int index) {
        return types[index];
    }

    public void setType(int index, int type) {
        types[index] = type;
    }

    public int getLevel(int index) {
        return levels[index];
    }

    public void setLevel(int index, int level) {
        levels[index] = level;
    }
}
