package net.swordie.ms.client.character.quest.reward;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.loaders.DatSerializable;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created on 3/2/2018.
 */
public class QuestMoneyReward implements QuestReward {
    private long money;

    private int status = 1; // 0 = given when the quest is started, 1 = when it is completed

    public QuestMoneyReward(long money) {
        this.money = money;
    }

    public QuestMoneyReward(long money, int status) {
        this.money = money;
        this.status = status;
    }

    @Override
    public int getStatus() {
        return status;
    }

    public QuestMoneyReward() {

    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }


    @Override
    public void giveReward(Char chr) {
        chr.addMoney(money);
    }

    @Override
    public void write(RandomAccessFile dos) throws IOException {
        dos.writeLong(getMoney());
        dos.writeInt(getStatus()); // quests.dat version 5
    }

    @Override
    public DatSerializable load(RandomAccessFile dis) throws IOException {
        long value = dis.readLong();
        return new QuestMoneyReward(value, dis.readInt());
    }
}
