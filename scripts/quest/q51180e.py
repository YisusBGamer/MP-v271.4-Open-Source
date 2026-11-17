# 51180 - Building a Time Machine
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Doctor Bing (9250125)

sm.setSpeakerID(9250125)  # Doctor Bing
sm.sendNext("Okay, you completed today's mission. Thanks to you, the Time Machine is coming along quite nicely.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Please help me again tomorrow!")
