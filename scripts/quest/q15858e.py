# 15858 - [Chocky Day] Episode 9
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Peplock Holmes (9001141)

sm.setSpeakerID(9001141)  # Peplock Holmes
sm.sendNext("Defeat monsters near your level to gather Monster-Milk.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Defeat monsters near your level to gather Monster-Milk.")
