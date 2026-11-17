# Piggy Bar lobby exit: back where you came from.
prev = chr.getPreviousFieldID()
sm.warp(prev if prev not in (0, 910194100) and prev // 1000 != 910194 else 100000000)
sm.dispose()
