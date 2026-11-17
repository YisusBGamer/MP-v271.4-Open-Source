# 101050100
# Shade's visit (Mercedes, the Young Queen 38041 to The Ruler Is Out 38045) is in the peaceful Elluel (910150300),
# where Philius and Mercedes wait. Arriving counts for 38041 (record 38041 clear=clear).
SHADE_ELLUEL = (38041, 38042, 38043, 38044, 38045)

if any(sm.hasQuest(q) for q in SHADE_ELLUEL) \
        or (sm.hasQuestCompleted(38041) and not sm.hasQuest(38042) and not sm.hasQuestCompleted(38042)):
    if sm.hasQuest(38041) and sm.getQRValue(38041, "clear") != "clear":
        sm.setQRValue(38041, "clear", "clear")
        sm.chatScript("Elluel seems different from Athena Pierce's description of the town. It looks very peaceful and cozy.")
    sm.warp(910150300, 0)
else:
    sm.warp(101050000, 9)
sm.dispose()
