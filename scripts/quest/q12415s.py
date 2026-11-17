# 12415 - Excavated: Clean Comb
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Spiegelmann (9010044)

sm.setSpeakerID(9010044)  # Spiegelmann
if sm.sendAskAccept("Boy am I in a pickle... you might call it a bristly pickle! Wait, no, that sounds gross. How am I going to tame this stache?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("You expect me to just use my hands to align these whiskers?!")
