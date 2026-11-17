# 993060017: the story fight is won (35607)
if sm.hasQuest(35607):
    sm.onLayer(800, "bme0", 0, -80, 1, "Effect/Direction20.img/effect/BMPre_WS/0", 4, False, -1, False)
    done = sm.questEndDialogue(35607)
    sm.offLayer(500, "bme0", False)
    if done:
        sm.warp(993060020, 0)
