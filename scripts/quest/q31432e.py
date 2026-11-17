# 31432 - Freeloading Trade King
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Romero (3001005)

sm.setSpeakerID(3001005)  # Romero
sm.sendNext("You better have 'em all.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("...98, 99, 100. Nice. Here's your info. Pleasure doing business with you, Kaiser.")
