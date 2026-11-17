# Free Market guide found in most towns (generated). Takes the player to the Free Market and remembers the town,
# so the Free Market exit brings them back here.
sm.setSpeakerID(parentID)
if sm.sendAskYesNo("Would you like to go to the #bFree Market#k? You'll come back here when you leave it."):
    sm.setReturnField()
    sm.warp(910000000, 2)
