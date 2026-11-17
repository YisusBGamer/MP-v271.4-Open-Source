# 910142100 Inner Cursed Ellinia Forest - The Tradition (Luminous 25661, own record 25661 not = 1): the Black Mage
# appears, says your fates are intertwined, and leaves. The quest is a scene (no Say.img lines; its end NPC, the
# second Vieren 1032210, is hidden), so it is finished here. The map has no exit: you're taken back to Lania's Home.
BLACK_MAGE = 1520029

if sm.hasQuestCompleted(25660) and not sm.hasQuestCompleted(25661):
    if not sm.hasQuest(25661):
        sm.startQuestNoCheck(25661)
    if sm.getQRValue(25661, "not") != "1":
        sm.setQRValue(25661, "not", "1")
    sm.spawnNpcIfAbsent(BLACK_MAGE, 200, -23)
    sm.chatScript("The Black Mage appears before you.")
    sm.invokeAfterDelay(4000, "removeNpc", BLACK_MAGE)
    sm.invokeAfterDelay(4000, "chatScript", "The Black Mage told you that your fates are intertwined and left.")
    sm.invokeAfterDelay(4500, "completeQuestNoCheck", 25661)  # its record is set above
    sm.invokeAfterDelay(7000, "warpInstanceOut", 101000100, 0)
else:
    sm.warpInstanceOut(101000100, 0)
