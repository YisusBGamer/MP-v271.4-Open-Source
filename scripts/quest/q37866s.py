# 37866 [Esfera] A Sunken Thing (start)
OLLIE = 3003500

sm.setSpeakerID(OLLIE)
sm.setBoxChat()
sm.sendNext("#face0#They're facing off against each other again...")
sm.sendNext("#face0#But thanks to the Executor, we've made it this far. We're closer now.")
sm.sendNext("#face0#Oh, #h0#, while you were fighting I got a message from Shubert. He's set up short-range matter transfer.")
if sm.sendAskYesNo("#face0#Once we have Tana, we can send her somewhere safe right away. Let's go!"):
    sm.startQuest(parentID)
    # the Light Executors guarding her (450007420, 10 minutes), then Will's ambush (enter_450007430), which finishes
    # this quest (clear=c1) and leaves the masked warrior in the Slumbering Deep for The Trap (37867)
    sm.warpInstanceIn(450007420)
    sm.setInstanceTime(10 * 60, 450007170)
else:
    sm.sendNext("#face0#Oh... Is something wrong?")
