# 57454 - The Demon's Lair 1
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Takeda Shingen (9130076)

sm.setSpeakerID(9130076)  # Takeda Shingen
sm.sendNext("I trust you're having fun?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Splendid performance! And not a hair out of place! How do you do it?")
