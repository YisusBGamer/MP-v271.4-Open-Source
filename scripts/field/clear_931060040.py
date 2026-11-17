# Meta-Beryl beaten in the Silo (931060040): Clash of the Xenoroids (23710, Xenon) records the fight (quest
# record 23723, which the quest checks) and moves to the Silo where Beryl is. Talk to her.
if sm.hasQuest(23710) and sm.getQRValue(23723) != "1":
    sm.setQRValue(23723, "1", False)
    sm.warpInstanceOut(931060041, 1)
