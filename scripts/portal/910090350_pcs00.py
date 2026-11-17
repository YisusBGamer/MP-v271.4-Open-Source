# id 1 (pcs00), field 910090350: the bottom of the Deathly Abyss.
# Lighting the Way (35927): swimming past both of Brie's markers (talk1, talk2) reaches the depths.
if sm.hasQuest(35927) and sm.getQRValue(35927, "talk1") == "1" and sm.getQRValue(35927, "talk2") == "1":
    sm.warp(910090313)
else:
    sm.showOffFieldEffect("hillah/fail")
    sm.warp(100051043)
