# id 331000000 (Main Street : City Center), field 331000000
sm.changeBGM("Bgm43/Unknown Part Of City", 0, 0)
# Are Black Cats Unlucky? (22729): the lost kitten (Nero) waits here after the school (no map data places it)
if sm.hasQuestCompleted(22728) and not sm.hasQuest(22729) and not sm.hasQuestCompleted(22729):
    sm.spawnNpcIfAbsent(1531010, -50, 243)
