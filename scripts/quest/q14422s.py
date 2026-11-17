# 14422 - A Gentleman's Candy 1
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Masked Gentleman (9000279)

sm.setSpeakerID(9000279)  # Masked Gentleman
if sm.sendAskAccept("Yes! This should do. I will melt them at once to build my noble statue. Mwa ha ha ha."):
    sm.startQuest(parentID)
    sm.sendNext("Be sure to come back tomorrow! ")
