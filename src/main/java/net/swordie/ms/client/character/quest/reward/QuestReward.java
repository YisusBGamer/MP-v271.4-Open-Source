package net.swordie.ms.client.character.quest.reward;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.loaders.DatSerializable;

/**
 * Created on 3/2/2018.
 */
public interface QuestReward extends DatSerializable {

    /**
     * Gives the reward of this QuestReward to a {@link Char}
     * @param chr The Char to give the reward to.
     */
    void giveReward(Char chr);

    /**
     * The quest state at which this reward is given: 0 = when the quest is started, 1 = when it is completed.
     * Rewards that don't track this are given on completion.
     */
    default int getStatus() {
        return 1;
    }
}
