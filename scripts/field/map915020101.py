# 915020101 Ariant Treasure Vault (Phantom 3rd job)
if 2500 <= chr.getJob() <= 2512 and sm.hasQuest(38052):
    # Shade: Phantom's Other Hideaway - his box is empty. (Quest.wz's completion check on this quest's record can't
    # be met as written, so it is completed here; the next hideaway quest becomes available.)
    sm.setQRValue(38052, "clear", False)
    sm.completeQuestNoCheck(38052)
    sm.setQRValue(38053, "impossible", False)
    sm.chat("Phantom's box is empty. Maybe he already came and left. There's one more storage room, in Leafre.")
elif sm.hasQuest(25111) and sm.getQRValue(25113) != "1":
    sm.chat("Search the Treasure Chest.")
