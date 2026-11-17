sm.showFieldEffect("Map/Effect2.img/blackHeavenBossDie2") # Shows the Boss Death effect
sm.invokeAfterDelay(6500, "warpField", sm.getFieldID() + 100, 0)
if hasattr(sm, "warpFieldLaterIfStillIn"):
    sm.warpFieldLaterIfStillIn(11500, sm.getFieldID() + 100)  # backup if the delayed warp is lost