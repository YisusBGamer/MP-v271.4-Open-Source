# 940000070 Near West Sanctum: the Nefarious Priests are beaten (Reclaim the Stolen Relic!, 25744).
if sm.hasQuest(25744) and sm.getQRValue(25805) != "1":
    sm.invokeAfterDelay(1500, "warp", 940000090)
