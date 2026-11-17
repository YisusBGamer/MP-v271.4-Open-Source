# 32801 - [FriendStory] Shinsoo Player's Club
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Hawkeye (1530103)

sm.setSpeakerID(1530103)  # Hawkeye
sm.sendNext("Okay, everyone is here!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Well? It's a great plan, right?")
