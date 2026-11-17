# Camp Conference Room
# 300000010
# Portal to enter the library
# Aran. After "Seal Stone of Ellin Forest" (21751) you're sent to the library to check the stone, and "The Lost Seal
# Stone of Ellin Forest" (21753) can only be taken once you've found it gone (Quest.wz: record 21764 = 1; "the Seal
# Stone of Ellin Forest was taken by the Gentleman of the Black Wings"). Going into the library after 21751 sets it.

LOST_SEAL_STONE = 21764
if sm.hasQuestCompleted(21751) and not sm.hasQuest(21753) and not sm.hasQuestCompleted(21753) \
        and sm.getQRValue(LOST_SEAL_STONE) != "1":
    sm.setQRValue(21764, "1", False)
    sm.chat("The Seal Stone is gone! It was taken by the Gentleman of the Black Wings. Tell Athena Pierce in Ellin Forest.")
sm.warp(300000011,3)
