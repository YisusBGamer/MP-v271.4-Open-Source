# Hidden Street: Stone Colossus (924030000), own copy (portal 240092300_west): the six Lightless Spirits that
# [Stone Colossus] Colossal Clean Up 4 (31348) asks for
LIGHTLESS_SPIRIT = 9100050
QUEST = 31348

if chr.getInstance() is not None and sm.hasQuest(QUEST) and not sm.isComplete(QUEST) and not sm.hasMobsInField():
    for x in range(6):
        sm.spawnMob(LIGHTLESS_SPIRIT, 150 * x, 60, False)
sm.dispose()
