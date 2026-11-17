package net.swordie.ms.client.character.quest.reward;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.loaders.DatSerializable;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created on 3/6/2018.
 */
public class QuestPopReward implements QuestReward {

    private int pop;

    private int status = 1; // 0 = given when the quest is started, 1 = when it is completed

    public QuestPopReward(int pop) {
        this.pop = pop;
    }

    public QuestPopReward(int pop, int status) {
        this.pop = pop;
        this.status = status;
    }

    @Override
    public int getStatus() {
        return status;
    }

    public QuestPopReward() {

    }

    public int getPop() {
        return pop;
    }

    public void setPop(int pop) {
        this.pop = pop;
    }

    @Override
    public void giveReward(Char chr) {
        chr.addStat(Stat.pop, getPop());
    }

    @Override
    public void write(RandomAccessFile dos) throws IOException {
        dos.writeInt(getPop());
        dos.writeInt(getStatus()); // quests.dat version 5
    }

    @Override
    public DatSerializable load(RandomAccessFile dis) throws IOException {
        int value = dis.readInt();
        return new QuestPopReward(value, dis.readInt());
    }
}
