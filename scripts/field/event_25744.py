# 940000070-940000089 Near West Sanctum - Reclaim the Stolen Relic! (25744, Kaiser): the Nefarious Priests who raided
# the West Sanctum. Beating them (field/clear_940000070.py) leads on to event_25744_end. The exit leads back to the
# West Sanctum; leaving early means forfeiting the quest, as it says.
if sm.hasQuest(25744) and sm.getQRValue(25805) != "1" and not sm.hasMobsInField():
    for mob, x in ((9300542, 1600), (9300543, 1800), (9300544, 2000)):
        sm.spawnMob(mob, x, 149, False)
    sm.chatScript("You've caught up to the Nefarious Priests!")
sm.dispose()
