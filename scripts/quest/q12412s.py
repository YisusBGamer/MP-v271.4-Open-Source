# 12412 - Excavated: Finders Keepers
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Spiegelmann (9010044)

sm.setSpeakerID(9010044)  # Spiegelmann
if sm.sendAskAccept("#b#h0##k... you didn't see a... um, it's like a... sort of thing... I mean it's got buttons and... I don't know how to describe it."):
    sm.startQuest(parentID)
else:
    sm.sendNext("Oh... I just thought you might have...")
