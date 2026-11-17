# 32654 - Obstacle Course
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Big Daddy (2530001)

sm.setSpeakerID(2530001)  # Big Daddy
if sm.sendAskAccept("That knight you're looking for is locked away in Gelimer's secret prison. You'll find him just through that portal on the right."):
    sm.startQuest(parentID)
    sm.sendNext("Heh heh heh... You think you can do it?\r\n\r\nMission: Obstacle Race\r\nPass by all the dangerous obstacles and get to the deep end of the cave.\r\nBe careful not to fall!\r\n")
else:
    sm.sendNext("Good call. The sooner you give up, the easier it'll be.")
