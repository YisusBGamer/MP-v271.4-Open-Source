# 15851 - [Chocky Day] Episode 3 (Part I)
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Peplock Holmes (9001141)

sm.setSpeakerID(9001141)  # Peplock Holmes
sm.sendNext("Unravel the clue hidden in Henesys.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Unravel the clue hidden in Henesys.")
