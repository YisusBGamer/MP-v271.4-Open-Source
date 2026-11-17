# 15854 - [Chocky Day] Episode 5
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Peplock Holmes (9001141)

sm.setSpeakerID(9001141)  # Peplock Holmes
sm.sendNext("Defeat monsters near your level to obtain the Mysterious Note.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Defeat monsters near your level to obtain the Mysterious Note.")
