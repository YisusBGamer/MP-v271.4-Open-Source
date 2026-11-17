# 20766 - The Mind of a Knight: The Finale
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Neinheart (1101002)

sm.setSpeakerID(1101002)  # Neinheart
sm.sendNext("#h0#, your mind is sharp and your wits are keen. I would think this to be its own reward, but I'm sure you would like something valuable as well.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("You are the first of our knights to complete the training. I'm sure you'll enjoy the bragging rights for some time.")
