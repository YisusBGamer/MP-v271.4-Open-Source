package net.swordie.ms.client.character.quest.requirement;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.loaders.DatSerializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created on 3/2/2018.
 */
public class QuestStartCompletionRequirement implements QuestStartRequirement {
    private static final Logger log = LogManager.getRootLogger();
    private int questID;
    private byte questStatus;

    public QuestStartCompletionRequirement() {}

    public QuestStartCompletionRequirement(int questID, byte questStatus) {
        this.questID = questID;
        this.questStatus = questStatus;
    }

    public int getQuestID() {
        return questID;
    }

    public byte getQuestStatus() {
        return questStatus;
    }

    public void setQuestID(int questID) {
        this.questID = questID;
    }

    public void setQuestStatus(byte questStatus) {
        this.questStatus = questStatus;
    }

    /**
     * Checks the state of another quest, as required by Quest.wz/Check.img/[id]/[0|1]/quest/[n].
     * The WZ "state" value follows the client's quest states:
     * 0 = not started, 1 = in progress, 2 = completed.
     */
    @Override
    public boolean hasRequirements(Char chr) {
        QuestManager qm = chr.getQuestManager();
        switch (getQuestStatus()) {
            case 0: // Not started
                return !qm.hasQuestInProgress(getQuestID()) && !qm.hasQuestCompleted(getQuestID());
            case 1: // In progress
                return qm.hasQuestInProgress(getQuestID());
            case 2: // Completed
                return qm.hasQuestCompleted(getQuestID());
            default:
                log.debug(String.format("Unknown quest state %d in requirement for quest %d.", getQuestStatus(), getQuestID()));
                return true;
        }
    }

    @Override
    public void write(RandomAccessFile dos) throws IOException {
        dos.writeInt(getQuestID());
        dos.writeByte(getQuestStatus());
    }

    @Override
    public DatSerializable load(RandomAccessFile dis) throws IOException {
        QuestStartCompletionRequirement qscr = new QuestStartCompletionRequirement();
        qscr.setQuestID(dis.readInt());
        qscr.setQuestStatus(dis.readByte());
        return qscr;
    }
}
