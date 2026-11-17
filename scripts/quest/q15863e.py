# 15863 - [Chocky Day] Episode 14
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Yettson (9001142)

sm.setSpeakerID(9001142)  # Yettson
sm.sendNext("Gather 300 Strange Clues for Yettson.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Gather 300 Strange Clues for Yettson.")
