# 912070000 Muirhat's Room (see CustomFieldScripts) - Investigating the Discovery (1501): peeling off the moss summons
# a monster
if sm.hasQuest(1501) and not sm.isComplete(1501) and not sm.hasMobsInField():
    sm.chatScript("The relic glows... and something answers!")
    # The moss is off: the quest also checks record 1506 = "1" (Check.img infoNumber) before it can be turned in
    sm.setQRValue(1506, "1", False)
    sm.spawnMob(9300491, 200, 149, False)
