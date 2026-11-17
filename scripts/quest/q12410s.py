# 12410 - Excavated: Snapshots
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Spiegelmann (9010044)

sm.setSpeakerID(9010044)  # Spiegelmann
if sm.sendAskAccept("Hey! That's my camera! What are you doing with my camera?!"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Don't you sass me! Let me have a look. If it has a little notch on the left hand shutter controls...")
