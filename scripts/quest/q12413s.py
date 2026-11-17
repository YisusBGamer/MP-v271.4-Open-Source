# 12413 - Excavation Site Map
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Spiegelmann (9010044)

sm.setSpeakerID(9010044)  # Spiegelmann
if sm.sendAskAccept("I'm so turned around, I don't know my holes in the ground from a hole in the ground!"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Have you seen my map, by any chance?")
