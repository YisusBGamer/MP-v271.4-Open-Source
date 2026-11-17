# 32802 - [FriendStory] Tell Me How You Feel 1
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Magnus (1530152)

sm.setSpeakerID(1530152)  # Magnus
sm.sendNext("Hey bro! Did you complete your mission?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Hey bro! Good job on the mission.")
