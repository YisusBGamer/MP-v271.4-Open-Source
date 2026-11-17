# 993060069: the story fight is won (35629)
if sm.hasQuest(35629):
    sm.onLayer(800, "bme0", 0, -80, 1, "Effect/Direction20.img/effect/BM1_Re/0", 4, False, -1, False)
    done = sm.questEndDialogue(35629)
    sm.offLayer(500, "bme0", False)
    if done:
        if sm.questStartDialogue(35630):
            sm.warp(450009500, 0)
