# Honnou-ji Eastern Wall (807050200, west00): over the wall into the temple's Eastern Wing, for Honnou-ji
# Infiltration 2 (Hayato 57141, Kanna 57437). From the Eastern Wall inside (807050204, out00): back out.
QUESTS = ((57141, 57141), (57437, 57437))  # (quest, its record: Princess Sakuno found)
if sm.getFieldID() == 807050200:
    if any(sm.hasQuest(q) and sm.getQRValue(r) != "1" for q, r in QUESTS):
        sm.warpInstanceIn(807050201, 0)
    else:
        sm.chat("The wall is too well guarded to climb without a plan.")
else:
    sm.warpInstanceOut(807050200, 1)
sm.dispose()
