# 20940 - Intel from Checky
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Checky (2152005)

sm.setSpeakerID(2152005)  # Checky
if sm.sendAskAccept("#eSomeone#n has a message for you. I'm going to give you some instructions, but I'm only going to give them once."):
    sm.startQuest(parentID)
else:
    sm.sendNext("You'd better take notes.")
