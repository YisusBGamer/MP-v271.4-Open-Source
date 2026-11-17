# 866000150 Kobold Prison, pt01 -> 866033000 King Kobold's Lair. A mysterious barrier keeps you out until you smell
# like a kobold (Kobold Fragrance 59051) and go to fight him (King Kobold Knockout 59052).
if sm.hasQuest(59052):
    sm.warpInstanceIn(866033000, 0)
elif sm.hasQuest(59049):
    sm.setQRValue(59049, "met", "1")  # Finding King Kobold: you found the barrier
    sm.chat("A mysterious barrier keeps you from reaching King Kobold. Seek Woodrock for advice.")
else:
    sm.chat("A mysterious barrier blocks the way.")
sm.dispose()
