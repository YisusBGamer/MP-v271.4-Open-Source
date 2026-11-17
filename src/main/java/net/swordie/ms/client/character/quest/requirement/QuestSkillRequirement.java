package net.swordie.ms.client.character.quest.requirement;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.loaders.DatSerializable;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Skill requirement from Quest.wz/Check.img/[id]/[0|1]/skill/[n].
 * <ul>
 *     <li><code>acquire</code> = 1: the skill must be in the skill list (learned, or unlocked at level 0 with a
 *     master level, like a Cygnus Knight's Noble Mind before "The Mind of a Knight"); absent/0: it must NOT be</li>
 *     <li><code>level</code> + <code>levelCondition</code> ("Match" / "or higher"): required skill level</li>
 * </ul>
 */
public class QuestSkillRequirement implements QuestStartRequirement {

    private int skillID;
    private boolean acquire;
    private int level;
    private boolean exactLevel;

    public QuestSkillRequirement() {
    }

    public QuestSkillRequirement(int skillID, boolean acquire, int level, boolean exactLevel) {
        this.skillID = skillID;
        this.acquire = acquire;
        this.level = level;
        this.exactLevel = exactLevel;
    }

    public int getSkillID() {
        return skillID;
    }

    public boolean isAcquire() {
        return acquire;
    }

    public int getLevel() {
        return level;
    }

    public boolean isExactLevel() {
        return exactLevel;
    }

    @Override
    public boolean hasRequirements(Char chr) {
        Skill skill = chr.getSkill(getSkillID());
        int curLevel = skill == null ? 0 : skill.getCurrentLevel();
        int masterLevel = skill == null ? 0 : skill.getMasterLevel();
        if (!isAcquire() && getLevel() <= 0) {
            // must not have learned this skill
            return curLevel <= 0 && masterLevel <= 0;
        }
        if (getLevel() > 0) {
            return isExactLevel() ? curLevel == getLevel() : curLevel >= getLevel();
        }
        return curLevel > 0 || masterLevel > 0;
    }

    @Override
    public void write(RandomAccessFile dos) throws IOException {
        dos.writeInt(getSkillID());
        dos.writeBoolean(isAcquire());
        dos.writeInt(getLevel());
        dos.writeBoolean(isExactLevel());
    }

    @Override
    public DatSerializable load(RandomAccessFile dis) throws IOException {
        return new QuestSkillRequirement(dis.readInt(), dis.readBoolean(), dis.readInt(), dis.readBoolean());
    }
}
