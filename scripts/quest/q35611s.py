# [Moonbridge] The Tenebris Expedition Goes to War! (35611)
sm.onLayer(800, "bm0", 0, -80, 1, "Effect/Direction20.img/effect/BMPre_Cygnus/1", 4, False, -1, False)
started = sm.questStartDialogue(parentID)
sm.offLayer(500, "bm0", False)
if started:
    done = sm.questEndDialogue(35611)
    if done:
        if not sm.hasQuestCompleted(35600):
            if not sm.hasQuest(35600):
                sm.startQuestNoCheck(35600)
            sm.completeQuest(35600)
        sm.warpInstanceOut(450009100, 0)
sm.dispose()
