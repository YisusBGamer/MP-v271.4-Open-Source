package net.swordie.ms.client.character.quest.reward;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.loaders.DatSerializable;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Trait EXP reward from Quest.wz/Act.img/[id]/[0|1]/(charismaEXP|insightEXP|willEXP|craftEXP|senseEXP|charmEXP).
 */
public class QuestTraitExpReward implements QuestReward {

    private int status;
    private Stat trait;
    private int amount;

    public QuestTraitExpReward() {
    }

    public QuestTraitExpReward(int status, Stat trait, int amount) {
        this.status = status;
        this.trait = trait;
        this.amount = amount;
    }

    /**
     * Maps an Act.img key to its trait stat, or null if the key isn't a trait EXP reward.
     */
    public static Stat getTraitByWzName(String name) {
        switch (name) {
            case "charismaEXP": return Stat.charismaEXP;
            case "insightEXP": return Stat.insightEXP;
            case "willEXP": return Stat.willEXP;
            case "craftEXP": return Stat.craftEXP;
            case "senseEXP": return Stat.senseEXP;
            case "charmEXP": return Stat.charmEXP;
            default: return null;
        }
    }

    @Override
    public int getStatus() {
        return status;
    }

    public Stat getTrait() {
        return trait;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public void giveReward(Char chr) {
        if (getTrait() != null && getAmount() > 0) {
            chr.addTraitExp(getTrait(), getAmount());
        }
    }

    @Override
    public void write(RandomAccessFile dos) throws IOException {
        dos.writeInt(getStatus());
        dos.writeInt((int) getTrait().getVal());
        dos.writeInt(getAmount());
    }

    @Override
    public DatSerializable load(RandomAccessFile dis) throws IOException {
        return new QuestTraitExpReward(dis.readInt(), Stat.getByVal(dis.readInt()), dis.readInt());
    }
}
