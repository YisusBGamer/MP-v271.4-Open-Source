# 32805 - [FriendStory] Rumors of Love 1
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Hawkeye (1530103)

sm.setSpeakerID(1530103)  # Hawkeye
sm.sendNext("Did you get all the photos?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("These pictures will be all the evidence we need!")
