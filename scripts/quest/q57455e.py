# 57455 - The Demon's Lair 2
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Takeda Shingen (9130076)

sm.setSpeakerID(9130076)  # Takeda Shingen
sm.sendNext("Taking a quick rest? We're all rooting for you, Kanna.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("See that? The enemies are scattering! Their base is crumbling! I can feel the winds of victory ruffling my mustache. Today is the day I feel like a king!")
