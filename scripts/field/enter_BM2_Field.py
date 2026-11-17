# 450011100-450011119 Labyrinth Dungeon rooms (entered through Velivah, npc/3003810): the Labyrinth Core for
# [Daily Quest] Clear the Labyrinth Dungeon (35583, core 9833326), the Expedition missions Labyrinth of Suffering
# Interior Dungeon (35271, core 9833324) and Nexus (35283, core 9833325), and a Suffering Watchman (35274, 8644827).
if chr.getInstance() is not None:
    pos = chr.getPosition()
    field_id = sm.getFieldID()
    dx = 200
    for quest, mob in ((35583, 9833326), (35271, 9833324), (35283, 9833325), (35274, 8644827)):
        if sm.hasQuest(quest) and not sm.isComplete(quest) and not sm.hasMobsInField(field_id, mob):
            sm.spawnMob(mob, pos.getX() + dx, pos.getY(), False)
            dx += 150
