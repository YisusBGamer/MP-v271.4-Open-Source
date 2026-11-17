# 993060067: the story fight is won (35628); straight on to Destroy the Core (35631), v265 dropped the Gloom
# steps 35629/35630
if sm.hasQuest(35628):
    sm.onLayer(800, "bme0", 0, -80, 1, "Effect/Direction20.img/effect/BM1_Cygnus/0", 4, False, -1, False)
    sm.onLayer(800, "bme1", 0, -80, 2, "Effect/Direction20.img/effect/BM1_Cygnus/1", 4, False, -1, False)
    done = sm.questEndDialogue(35628)
    sm.offLayer(500, "bme0", False)
    sm.offLayer(500, "bme1", False)
    if done:
        if sm.questStartDialogue(35631):
            sm.warp(993060081, 0)
        else:
            sm.warpInstanceOut(450009300, 0)
