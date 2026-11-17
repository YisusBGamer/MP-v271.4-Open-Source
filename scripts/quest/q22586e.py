# 22586 - Secret Organization's Fourth Mission
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Hiver (1013203)

sm.setSpeakerID(1013203)  # Hiver
sm.sendNext("You've brought the #t4032472#? Show it to me. I want to make sure it is the one that we are looking for.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Haha. Looks like you've got the right one.")
