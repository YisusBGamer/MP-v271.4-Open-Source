# 940000000 Pantheon Vacant Lot: the Nefarious Priests are beaten (Evil Auras!, 25735) - they make a run for it.
if sm.hasQuest(25735) and sm.getQRValue(25802) != "1":
    sm.chatScript("The Nefarious Priests are fleeing!")
    sm.invokeAfterDelay(1500, "warp", 940000020)
