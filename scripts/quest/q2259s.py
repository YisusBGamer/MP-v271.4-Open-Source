# 2259 - Scorpions Can't Listen at Night
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Camel Cab (2110005)

sm.setSpeakerID(2110005)  # Camel Cab
if sm.sendAskAccept("We shouldn't be talking about that here. I'll see you in Sahel 1, it's safer there. Just go straight from here and you'll see it."):
    sm.startQuest(parentID)
else:
    sm.sendNext("I don't want to say anything in front of Meerkats.")
