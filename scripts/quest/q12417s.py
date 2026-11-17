# 12417 - Excavated: Eye Drops
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Spiegelmann (9010044)

sm.setSpeakerID(9010044)  # Spiegelmann
if sm.sendAskAccept("Goodness gracious, my allergies are just killing me! I feel like my eyeballs are shrivelling up into little raisins of blindness!"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Are YOUR eyes okay, #b#h0##k? You must have a lot of moisture on those peepers!")
