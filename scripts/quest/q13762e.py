# 13762 - [Maple Friends] Getting Signatures
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
sm.sendNext("Easy, right? Continue to collect Friend Points (FP) to exchange for a special gift.\r\n")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("I hope you make lots of great friends here in Maple World!")
