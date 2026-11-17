# 320000100 Subspace Bridge, out00: the Maple World portal (to Henesys). Visiting Maple World is what Maple World and
# Mirror World (40050, Zero's story) asks for.
if sm.hasQuest(40050) and sm.getQRValue(40068) != "1":
    sm.setQRValue(40068, "1")
sm.warp(100000000)
sm.dispose()
