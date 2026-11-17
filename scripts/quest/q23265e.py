# 23265 - Time for a Change!
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mastema (2151009)

sm.setSpeakerID(2151009)  # Mastema
sm.sendNext("You brought it! Oh, happy day!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Finally...will I finally have my form and my power back? Could it be...?")
