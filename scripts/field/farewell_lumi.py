# 350020008 Lania's Front Yard - Dark Destiny (25660, Luminous story): the darkness has taken Lania
if sm.hasQuest(25660) and not sm.isComplete(25660) and not sm.hasMobsInField():
    sm.chatScript("Darkness has swallowed Lania. Free her from it!")
    sm.spawnMob(9300534, 100, 24, False)
