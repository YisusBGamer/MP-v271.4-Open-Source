package net.swordie.ms.client.character.quest.reward;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.loaders.DatSerializable;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.Set;

/**
 * Skill reward from Quest.wz/Act.img/[id]/[0|1]/skill/[n]: teaches a skill (or raises its master level) when the
 * character's job is in the listed jobs. Existing levels are never lowered.
 */
public class QuestSkillReward implements QuestReward {

    private int status;
    private int skillID;
    private int skillLevel;
    private int masterLevel;
    private boolean onlyMasterLevel;
    private Set<Short> jobs = new HashSet<>();

    public QuestSkillReward() {
    }

    public QuestSkillReward(int status, int skillID, int skillLevel, int masterLevel, boolean onlyMasterLevel) {
        this.status = status;
        this.skillID = skillID;
        this.skillLevel = skillLevel;
        this.masterLevel = masterLevel;
        this.onlyMasterLevel = onlyMasterLevel;
    }

    @Override
    public int getStatus() {
        return status;
    }

    public int getSkillID() {
        return skillID;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public int getMasterLevel() {
        return masterLevel;
    }

    public boolean isOnlyMasterLevel() {
        return onlyMasterLevel;
    }

    public Set<Short> getJobs() {
        return jobs;
    }

    public void addJob(short job) {
        getJobs().add(job);
    }

    @Override
    public void giveReward(Char chr) {
        if (!getJobs().isEmpty() && !getJobs().contains(chr.getJob())) {
            return;
        }
        Skill cur = chr.getSkill(getSkillID());
        int curLevel = cur == null ? 0 : cur.getCurrentLevel();
        int curMaster = cur == null ? 0 : cur.getMasterLevel();
        int newLevel = isOnlyMasterLevel() ? curLevel : Math.max(curLevel, getSkillLevel());
        int newMaster = Math.max(curMaster, getMasterLevel());
        if (newLevel == curLevel && newMaster == curMaster && cur != null) {
            return;
        }
        chr.addSkill(getSkillID(), newLevel, newMaster);
    }

    @Override
    public void write(RandomAccessFile dos) throws IOException {
        dos.writeInt(getStatus());
        dos.writeInt(getSkillID());
        dos.writeInt(getSkillLevel());
        dos.writeInt(getMasterLevel());
        dos.writeBoolean(isOnlyMasterLevel());
        dos.writeShort(getJobs().size());
        for (short job : getJobs()) {
            dos.writeShort(job);
        }
    }

    @Override
    public DatSerializable load(RandomAccessFile dis) throws IOException {
        QuestSkillReward qsr = new QuestSkillReward(dis.readInt(), dis.readInt(), dis.readInt(), dis.readInt(),
                dis.readBoolean());
        short size = dis.readShort();
        for (int i = 0; i < size; i++) {
            qsr.addJob(dis.readShort());
        }
        return qsr;
    }
}
