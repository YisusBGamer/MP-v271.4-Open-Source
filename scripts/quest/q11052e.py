# 11052 - Free-for-all Professional
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Rosette (9070005)

sm.setSpeakerID(9070005)  # Rosette
sm.sendNext("See? Piece of cake, right? 'Course it was. Here's your gift, just like I said.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Hey, it's the master of Free-for-all mode! There are other missions available, you know, so step up to the challenge!")
