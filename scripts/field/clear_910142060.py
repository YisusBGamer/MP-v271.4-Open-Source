# 910142060 Forest Edge: the Black Mage minions are down (Unexpected Encounter, Luminous 25617, checks record 25616
# fight=1). The map has no exit, so the player goes on to Lania's Front Yard.
if sm.hasQuest(25617) and sm.getQRValue(25616, "fight") != "1":
    sm.setQRValue(25616, "fight", "1")
    sm.chatScript("I must act with haste!")
sm.invokeAfterDelay(2000, "warpInstanceOut", 101000101, 0)
