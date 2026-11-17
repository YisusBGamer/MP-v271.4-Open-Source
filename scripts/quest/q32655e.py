# 32655 - Prison Escape
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Allen (2530002)

sm.setSpeakerID(2530002)  # Allen
sm.sendNext("Did you get all the junk? I mean, key electronic components?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Okay, I...I unlocked it. Never tell a soul I helped you guys. Ever! Okay?")
