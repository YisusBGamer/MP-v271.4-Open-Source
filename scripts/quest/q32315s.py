# 32315 - Maple Island Disaster
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Sugar (1520034)

sm.setSpeakerID(1520034)  # Sugar
if sm.sendAskAccept("#h0#! Come quickly! Maple Island's in trouble!"):
    sm.startQuest(parentID)
    sm.sendNext("We don't have a moment to lose. Hurry, get to #bSouthperry#k! You can return to Maple Island by either using the Maple Return skill or asking Olaf in Lith Harbor!")
else:
    sm.sendNext("We really, really, really need your help, #h0#!")
