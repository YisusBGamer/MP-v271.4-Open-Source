# [Moonbridge] Destiny Awaits (35601): on to Neinheart's Briefing (35605) on the Ereve Roads (v265 dropped 35602-35604)
started = sm.questStartDialogue(parentID)
if started:
    done = sm.questEndDialogue(35601)
    if done:
        sm.warpInstanceIn(993060011, 0)
sm.dispose()
