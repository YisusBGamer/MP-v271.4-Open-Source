# 993060040: the story fight is won (35614)
if sm.hasQuest(35614):
    sm.onLayer(800, "bme0", 0, -80, 1, "Effect/Direction20.img/effect/BMPre_WS/1", 4, False, -1, False)
    done = sm.questEndDialogue(35614)
    sm.offLayer(500, "bme0", False)
    if done:
        sm.warpInstanceOut(993060041, 0)
