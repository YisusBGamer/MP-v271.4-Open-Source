# 12416 - Excavated: Permanent Marker
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Spiegelmann (9010044)

sm.setSpeakerID(9010044)  # Spiegelmann
if sm.sendAskAccept("Oh, poop. I lost my marker..."):
    sm.startQuest(parentID)
else:
    sm.sendNext("#b#h0##k, do you have a pen? Or a marker? Or MY marker? ")
