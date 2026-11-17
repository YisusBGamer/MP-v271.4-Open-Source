# 993060049: the story fight is won (35615); on to Neinheart on Deck 2 (Terror in the Fog, 35620)
if sm.hasQuest(35615):
    sm.onLayer(800, "bme0", 0, -80, 1, "Effect/Direction20.img/effect/BM1_mist/1", 4, False, -1, False)
    done = sm.questEndDialogue(35615)
    sm.offLayer(500, "bme0", False)
    if done:
        sm.warpInstanceOut(450009200, 0)
