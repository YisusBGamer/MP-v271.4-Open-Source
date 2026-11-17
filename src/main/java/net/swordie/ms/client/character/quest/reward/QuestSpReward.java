package net.swordie.ms.client.character.quest.reward;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.loaders.DatSerializable;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.Set;

/**
 * SP reward from Quest.wz/Act.img/[id]/[0|1]/sp/[n]: grants SP for the current job when it is in the listed jobs.
 */
public class QuestSpReward implements QuestReward {

    private int status;
    private int sp;
    private Set<Short> jobs = new HashSet<>();

    public QuestSpReward() {
    }

    public QuestSpReward(int status, int sp) {
        this.status = status;
        this.sp = sp;
    }

    @Override
    public int getStatus() {
        return status;
    }

    public int getSp() {
        return sp;
    }

    public Set<Short> getJobs() {
        return jobs;
    }

    public void addJob(short job) {
        getJobs().add(job);
    }

    @Override
    public void giveReward(Char chr) {
        if (getSp() <= 0 || (!getJobs().isEmpty() && !getJobs().contains(chr.getJob()))) {
            return;
        }
        chr.addSpToJobByCurrentJob(getSp());
    }

    @Override
    public void write(RandomAccessFile dos) throws IOException {
        dos.writeInt(getStatus());
        dos.writeInt(getSp());
        dos.writeShort(getJobs().size());
        for (short job : getJobs()) {
            dos.writeShort(job);
        }
    }

    @Override
    public DatSerializable load(RandomAccessFile dis) throws IOException {
        QuestSpReward qsr = new QuestSpReward(dis.readInt(), dis.readInt());
        short size = dis.readShort();
        for (int i = 0; i < size; i++) {
            qsr.addJob(dis.readShort());
        }
        return qsr;
    }
}
