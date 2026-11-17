# 31013 - Xerxes Falls
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Michael (2170000)

sm.setSpeakerID(2170000)  # Michael
if sm.sendAskAccept("We're just about ready to go. You up for this?"):
    sm.startQuest(parentID)
    sm.sendNext("Alright, we got this! Right here, man, brobump! YEAH! LET'S DO THIS!!")
else:
    sm.sendNext("Man, don't leave me hanging now!")
