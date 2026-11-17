# M Tower Top Floor (450014240): [Reverse City] Upward and Downward (37618) and Long Awaited (37619).
# Reaching the top floor finishes 37618's objective; Barr (3004651) catches up here to hand both quests in.
BARR = 3004651
if sm.hasQuest(37618) and sm.getQRValue(37618, "dummy") != "1":
    sm.setQRValue(37618, "dummy", "1")
    sm.chatScript("You reached the top of M Tower. Barr is right behind you.")
if sm.hasQuest(37618) or sm.hasQuest(37619):
    if not any(n.getTemplateId() == BARR for n in field.getNpcs()):
        sm.spawnNpc(BARR, -250, -33)
