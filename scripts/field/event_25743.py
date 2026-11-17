# 940000100 West Sanctum (ransacked) - What About the West Sanctum? (25743, Kaiser): too late, the priests have been
# and gone. 25743 waits for record 25804 to say "1"; the quest is finished from the quest window. The map's portals
# are the West Sanctum's own.
if sm.hasQuest(25743) and sm.getQRValue(25804) != "1":
    sm.startQuest(25804)
    sm.setQRValue(25804, "1")
    sm.chatScript("The West Sanctum has already been ransacked! You've been tricked!")
sm.dispose()
