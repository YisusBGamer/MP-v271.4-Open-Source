# 2215 - Find the Crumpled Piece of Paper Again
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Knocked Trash Can (1052108)

sm.setSpeakerID(1052108)  # Knocked Trash Can
sm.sendNext("#b(You feel something rustling inside the dusty trash can. Would you like to take it out of the trash can?)#k")
sm.sendSay("#b(You have taken out a crumpled piece of paper covered in cobwebs.)#k")
sm.tryCompleteQuest(parentID)
