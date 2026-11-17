package net.swordie.ms.client.character.quest.requirement;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.loaders.DatSerializable;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * World requirement from Quest.wz/Check.img/[id]/[0|1]/worldmin and worldmax.
 */
public class QuestWorldRequirement implements QuestStartRequirement {

    private int minWorld;
    private int maxWorld;

    public QuestWorldRequirement() {
    }

    public QuestWorldRequirement(int minWorld, int maxWorld) {
        this.minWorld = minWorld;
        this.maxWorld = maxWorld;
    }

    public int getMinWorld() {
        return minWorld;
    }

    public int getMaxWorld() {
        return maxWorld;
    }

    @Override
    public boolean hasRequirements(Char chr) {
        if (chr.getWorld() == null || chr.getWorld().getWorldId() == null) {
            return true; // can't determine the world (e.g. offline), don't block
        }
        int worldID = chr.getWorld().getWorldId().getVal();
        return worldID >= getMinWorld() && worldID <= getMaxWorld();
    }

    @Override
    public void write(RandomAccessFile dos) throws IOException {
        dos.writeInt(getMinWorld());
        dos.writeInt(getMaxWorld());
    }

    @Override
    public DatSerializable load(RandomAccessFile dis) throws IOException {
        return new QuestWorldRequirement(dis.readInt(), dis.readInt());
    }
}
