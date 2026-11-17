# 57406 - Adaptation 3
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mouri Motonari (9130008)

sm.setSpeakerID(9130008)  # Mouri Motonari
sm.sendNext("Your progress is acceptable.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("I suppose you're proud of yourself, having turned my training dummies into horse feed. You understand, I hope, that I don't have the resources to continue your journey of self-improvement.")
    sm.sendSay("Take time to prepare for the battles ahead. The enemies will be fighting back this time.")
