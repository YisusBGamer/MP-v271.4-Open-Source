# id 865010001 (Commerci Republic : Daniella Stronghold), field 865010001
sm.createQuestWithQRValue(18418, "B=33267")

# Grosso Polpo surfaces for Outlaw of the Sea (17646).
if sm.hasQuest(17646) and not sm.hasMobsInField(sm.getFieldID(), 9390835):
    sm.spawnMob(9390835, 500, 485, False)
