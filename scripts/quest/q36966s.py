# 36966 Taming the Polearm (Maha). Aran 4th job: Maha's rage has to be beaten; it is fought in the Mirror Cave
# (portal enterMCave spawns Uncontrollable Maha 8645453 in 914021000). Handed in to Maha (q36966e).
sm.setSpeakerID(1201002)
sm.sendNext("Ugh... Aran... I spent hundreds of years without a master, and the darkness in my heart... it's turned "
            "into rage. I can't hold it back.")
if not sm.sendAskAccept("If you beat me, the rage will stop. Please... it's what a master has to do. Go to the "
                        "#bMirror Cave#k and face it."):
    sm.dispose()
sm.startQuest(parentID)
sm.sendSayOkay("Enter the Mirror of Desire in the #bMirror Cave#k west of Rien. My rage will be waiting there.")
sm.dispose()
