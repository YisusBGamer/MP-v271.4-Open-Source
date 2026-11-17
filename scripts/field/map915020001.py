# 915020001 Orbis Treasure Vault (first enter). Shade: Phantom's Hideaway (38051) - he isn't here, but the lock was
# broken off. (Quest.wz's completion check on this quest's record can't be met as written, so it is completed here;
# the next hideaway quest becomes available.)
if 2500 <= chr.getJob() <= 2512 and sm.hasQuest(38051):
    sm.setQRValue(38051, "clear", False)
    sm.completeQuestNoCheck(38051)
    sm.setQRValue(38052, "impossible", False)
    sm.chat("Phantom is nowhere to be found, but the lock was broken off. Maybe he was here recently. Next: the storage room in Ariant.")
