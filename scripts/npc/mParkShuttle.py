# Monster Park Shuttle

if sm.getFieldID() != 951000000:
    if sm.sendAskYesNo("Would you like to go to the monster park?"):
        sm.warp(951000000)
else:
    if sm.sendAskYesNo("Would you like to return?"):
        if sm.getReturnField() > 0:
            sm.warp(sm.getReturnField())
        else:
            sm.warp(100000000, 20)
