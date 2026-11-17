sm.invokeAfterDelay(7000, "warpField", sm.getFieldID() + 100, 0)  # the party, into the next map
if hasattr(sm, "warpFieldLaterIfStillIn"):
    sm.warpFieldLaterIfStillIn(12000, sm.getFieldID() + 100)  # backup if the delayed warp is lost