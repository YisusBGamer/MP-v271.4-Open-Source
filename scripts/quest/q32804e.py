# 32804 - [FriendStory] Strategic Meeting
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Hawkeye (1530103)

sm.setSpeakerID(1530103)  # Hawkeye
sm.sendNext("Okay, is everyone here?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Bro! We can do this!")
