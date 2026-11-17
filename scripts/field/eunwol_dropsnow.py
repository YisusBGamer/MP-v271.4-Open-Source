# 914050002-914050006 (your own copy): Finding Aran 2 (Shade 38048). Hold out against the penguins' snowballs and
# the record (38048 = clear) is set, then it's back to the Big Penguin. The record is set before the warp: leaving
# the field stops its pending events.
if sm.hasQuest(38048) and sm.getQRValue(38048) != "clear":
    sm.chatScript("The penguins are throwing snowballs at you! Dodge them!")
    sm.invokeAfterDelay(19500, "setQRValue", 38048, "clear")
    sm.invokeAfterDelay(20000, "warpInstanceOut", 914050001, 0)
else:
    sm.invokeAfterDelay(1000, "warpInstanceOut", 914050001, 0)
