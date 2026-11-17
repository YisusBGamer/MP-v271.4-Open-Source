# 57158 - The Demon's Lair 1
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Takeda Shingen (9130076)

sm.setSpeakerID(9130076)  # Takeda Shingen
sm.sendNext("How fares the battle?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("You did all right out there. I saw those yellow-bellied Oda dogs flying over the hillside to get away from you.")
