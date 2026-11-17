# Portal script pt_800022004.
# Resting Place -> the Dusk Viper Pit. For Unholy Water (58737) a pit of your own with Dusk Vipers to take venom from
# (the regular pit's vipers are far too tough for this part of the story).
if sm.hasQuest(58737):
    sm.warpInstanceIn(800022140, 1)
    field = chr.getField()
    if field.getId() == 800022140:
        for i in range(12):
            field.spawnMob(9400039, -780 + i * 120, -148, False, 300000)
else:
    sm.warp(800022160, 1)
sm.dispose()
