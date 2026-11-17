# id 270000000 (Time Lane : Three Doors), field 270000000
sm.setMapTaggedObjectVisible("door0", True, 0, 0)
sm.setMapTaggedObjectVisible("door1", True, 0, 0)
sm.createQuestWithQRValue(18418, "B=32955")
# Explorer story at the Three Doors: the Gate of the Present (1520028) opens for The Gate of the Present Opens (32308)
# and speaks again in Words of Ill Omen (32313); the Seal (1520026) for Overcoming Fears (32312)
if sm.hasQuestCompleted(32307) and not sm.hasQuest(32308) and not sm.hasQuestCompleted(32308)         or sm.hasQuestCompleted(32312) and not sm.hasQuestCompleted(32313):
    sm.spawnNpcIfAbsent(1520028, 200, 177)
if sm.hasQuest(32312):
    sm.spawnNpcIfAbsent(1520026, 450, 177)

