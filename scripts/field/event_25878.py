# 400030610 Heretical Cleric Chambers (onUserEnter): 25878 waits for record 25919 to say "1" (being here)
if sm.hasQuest(25878):
    sm.startQuest(25919)
    sm.setQRValue(25919, "1")
sm.dispose()
