# 1538 - [Battle Mode] Elite Soldier Contest
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Haktorius (9070009)

sm.setSpeakerID(9070009)  # Haktorius
if sm.sendAskAccept("I'll pick one soldier to execute my most dangerous missions this month. Only soldiers with stellar records in Battle Mode will be eligible to become my Elite Guardian."):
    sm.startQuest(parentID)
    sm.sendNext("Please come to the Battle Square right away.")
else:
    sm.sendNext("Where is your confidence?")
