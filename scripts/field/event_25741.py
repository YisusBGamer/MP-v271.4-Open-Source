# 940000040-940000059 Near South Sanctum - South Sanctum Duty (25741, Kaiser): the Nefarious Priests gathered at the
# back of the sanctum. The exit (portal/check_25741.py) opens once they're beaten.
if sm.hasQuest(25741) and sm.getQRValue(25803) != "1" and not sm.hasMobsInField():
    for mob, x in ((9300539, -2450), (9300540, -2250), (9300541, -2050)):
        sm.spawnMob(mob, x, 29, False)
    sm.chatScript("The Nefarious Priests are gathered here. Defeat them!")
sm.dispose()
