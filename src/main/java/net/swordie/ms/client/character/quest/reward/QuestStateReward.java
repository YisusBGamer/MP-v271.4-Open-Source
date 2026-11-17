package net.swordie.ms.client.character.quest.reward;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.enums.QuestStatus;
import net.swordie.ms.loaders.DatSerializable;
import net.swordie.ms.loaders.QuestData;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Act.img "quest" list: starting or completing a quest changes other quests' states (e.g. starting 2101 completes
 * 2100). State 1 starts the other quest (if the Char doesn't have it), state 2 completes it without its rewards.
 */
public class QuestStateReward implements QuestReward {
    private int status = 1;
    private int questId;
    private int state;

    public QuestStateReward() {
    }

    public QuestStateReward(int status, int questId, int state) {
        this.status = status;
        this.questId = questId;
        this.state = state;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void giveReward(Char chr) {
        QuestManager qm = chr.getQuestManager();
        Quest quest = qm.getQuestById(questId);
        if (state == 2) {
            if (quest == null) {
                // just the state: adding it the normal way would give its start rewards first
                Quest q = QuestData.createQuestFromId(questId);
                if (q == null) {
                    return;
                }
                q.setStatus(QuestStatus.Completed);
                q.setCompletedTime(FileTime.currentTime());
                qm.addCustomQuest(q);
                chr.write(WvsContext.message(MessagePacket.questRecordMessage(q)));
            } else if (quest.getStatus() != QuestStatus.Completed) {
                qm.completeQuest(questId, false);
            }
        } else if (state == 1) {
            if (quest == null) {
                Quest q = QuestData.createQuestFromId(questId);
                if (q != null) {
                    qm.addQuest(q);
                }
            }
        }
    }

    @Override
    public void write(RandomAccessFile dos) throws IOException {
        dos.writeInt(status);
        dos.writeInt(questId);
        dos.writeInt(state);
    }

    @Override
    public DatSerializable load(RandomAccessFile dis) throws IOException {
        return new QuestStateReward(dis.readInt(), dis.readInt(), dis.readInt());
    }
}
