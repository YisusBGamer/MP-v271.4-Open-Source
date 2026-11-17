# The Special Ink (21743) done - Jin Jin (2091008): "Okay, here are 8 batches of Special Ink" (his official end line).
# The quest's data takes the materials and the fee but gives no ink, so the ink is handed over here; the Black Scroll
# (21742) needs it. Runs after the quest completes.
INK = 4032342
if not sm.hasItem(INK) and sm.canHold(INK):
    sm.giveItem(INK, 8)
sm.dispose()
