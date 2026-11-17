# 32932 - [Yeti & Pepe] Yeti Rides
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Scholarly 'Shroom (9000292)

sm.setSpeakerID(9000292)  # Scholarly 'Shroom
if sm.sendAskAccept("Let's talk about Yetis! Wanna hear the story?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Hm, Yetis don't excite you? C'mon, it's Yetis and Pepes we're talking about!")
