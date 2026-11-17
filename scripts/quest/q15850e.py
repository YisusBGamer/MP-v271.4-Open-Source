# 15850 - [Chocky Day] Episode 2
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Peplock Holmes (9001141)

sm.setSpeakerID(9001141)  # Peplock Holmes
sm.sendNext("Are the investigation results ready?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Are the investigation results ready?")
