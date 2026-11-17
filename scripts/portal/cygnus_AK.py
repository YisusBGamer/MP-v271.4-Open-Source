# Portal script "cygnus_AK" (Time Lane : Three Doors, 270000000): the secret passage to the Dimensional Schism.
# "Dimensional Schism" (20752, Mihile) is done by finding this passage (its record "find"). While the quest is on, the
# passage is noted instead of entered.
if sm.hasQuest(20752) and sm.getQRValue(20752) != "find":
    sm.setQRValue(20752, "find")
    sm.chatScript("You found the secret passage to the Dimensional Schism. Report back to the Mysterious Girl.")
else:
    sm.warp(272030000, 1)  # Dimensional Schism : Dimensional Schism, portal out00
sm.dispose()
