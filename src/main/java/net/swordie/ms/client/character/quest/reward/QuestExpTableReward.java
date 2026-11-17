package net.swordie.ms.client.character.quest.reward;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.loaders.DatSerializable;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.TreeMap;

/**
 * EXP that depends on the character's level: Act.img "expTable" names a table in Quest.wz/ChangeableQExpTable.img
 * (level -> EXP). Below the table's first level the first entry is used, above its last level the last one.
 */
public class QuestExpTableReward implements QuestReward {
    private int status = 1;
    private TreeMap<Integer, Long> table = new TreeMap<>();

    public QuestExpTableReward() {
    }

    public QuestExpTableReward(int status, Map<Integer, Long> table) {
        this.status = status;
        this.table = new TreeMap<>(table);
    }

    @Override
    public int getStatus() {
        return status;
    }

    public long getExpForLevel(int level) {
        if (table.isEmpty()) {
            return 0;
        }
        Map.Entry<Integer, Long> e = table.floorEntry(level);
        return e == null ? table.firstEntry().getValue() : e.getValue();
    }

    @Override
    public void giveReward(Char chr) {
        long exp = getExpForLevel(chr.getLevel());
        if (exp > 0) {
            chr.addExp(exp);
        }
    }

    @Override
    public void write(RandomAccessFile dos) throws IOException {
        dos.writeInt(status);
        dos.writeShort(table.size());
        for (Map.Entry<Integer, Long> e : table.entrySet()) {
            dos.writeInt(e.getKey());
            dos.writeLong(e.getValue());
        }
    }

    @Override
    public DatSerializable load(RandomAccessFile dis) throws IOException {
        int st = dis.readInt();
        int size = dis.readShort();
        Map<Integer, Long> t = new TreeMap<>();
        for (int i = 0; i < size; i++) {
            t.put(dis.readInt(), dis.readLong());
        }
        return new QuestExpTableReward(st, t);
    }
}
