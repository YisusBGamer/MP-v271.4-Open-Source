package net.swordie.ms.loaders.containerclasses;

import java.util.ArrayList;
import java.util.List;

/**
 * One Etc.wz MapleScheduler.img/contents entry (a Maple Planner row): its type ("quest", "contents", "blocked"), the
 * quest it starts and the linkQuests that carry the mission.
 */
public record SchedulerContent(String type, int quest, List<Integer> linkQuests) {

    public static final String TYPE_QUEST = "quest";

    public boolean isQuest() {
        return TYPE_QUEST.equals(type) && quest > 0;
    }

    /** The linkQuests, then the quest itself. */
    public List<Integer> quests() {
        List<Integer> all = new ArrayList<>(linkQuests);
        all.add(quest);
        return all;
    }
}
