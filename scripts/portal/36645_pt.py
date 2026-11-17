# Lith Harbor mikhail00: the trail for Trails of the Lightning (36645, Mihile). Reaching it is what the quest asks
# (its record dir=1); Hawkeye (1541209) then takes it in.
if sm.hasQuest(36645) and sm.getQRValue(36645, "dir") != "1":
    sm.setQRValue(36645, "dir", "1")
    sm.chat("You find faint traces of lightning on the ground here. Report back.")
sm.dispose()
