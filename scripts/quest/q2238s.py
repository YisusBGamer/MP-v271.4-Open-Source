# 2238 - Who is the Owner of the Mysterious Note?
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mu Young (1061014)

sm.setSpeakerID(1061014)  # Mu Young
if sm.sendAskAccept("Hmm, I don't know who the owner is, but it sure isn't me."):
    sm.startQuest(parentID)
    sm.sendNext("Hey! You!")
else:
    sm.sendNext("Are you alright?")
