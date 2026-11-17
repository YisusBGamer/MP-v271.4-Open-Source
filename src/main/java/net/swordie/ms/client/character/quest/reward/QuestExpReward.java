package net.swordie.ms.client.character.quest.reward;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.loaders.DatSerializable;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created on 3/2/2018.
 */
public class QuestExpReward implements QuestReward {

    private long exp;

    private int status = 1; // 0 = given when the quest is started, 1 = when it is completed

    public QuestExpReward(long exp) {
        this.exp = exp;
    }

    public QuestExpReward(long exp, int status) {
        this.exp = exp;
        this.status = status;
    }

    @Override
    public int getStatus() {
        return status;
    }

    public QuestExpReward() {

    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public long getExp() {
        return exp;
    }

    @Override
    public void giveReward(Char chr) {
        chr.addExp(getExp());
    }

    @Override
    public void write(RandomAccessFile dos) throws IOException {
        dos.writeLong(getExp());
        dos.writeInt(getStatus()); // quests.dat version 5
    }

    @Override
    public DatSerializable load(RandomAccessFile dis) throws IOException {
        long value = dis.readLong();
        return new QuestExpReward(value, dis.readInt());
    }
}
