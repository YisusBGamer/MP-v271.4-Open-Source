# 106030302
# Enemy At The Gates (30059) is done once you leave the gates you guarded (Quest.wz: its record "clear")
if sm.hasQuest(30059) and sm.getQRValue(30059) != "clear":
    sm.setQRValue(30059, "clear", False)
    sm.updateQRValue(30059, True)
sm.warp(106030102, 1)
sm.dispose()
