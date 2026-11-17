# 807100102 Honnou-ji Alleyway, out00 - Kanna's tutorial: on to the back of the temple once the soldiers are defeated
if sm.hasMobsInField():
    sm.chatScript("Defeat the soldiers first.")
else:
    sm.warp(807100103)
sm.dispose()
