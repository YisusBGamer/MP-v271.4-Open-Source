# 32328 - A Boy Named Utah
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Utah (1013101), taken from the other quest phase

sm.setSpeakerID(1013101)  # Utah
sm.sendNext("Evan's OLDER brother? That means Evan is... some kid? Am I at the right place?")
sm.sendSay("What's that flapping sound? It's really loud!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Is that a dragon?!")
