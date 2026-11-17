# 36326 Rondo's Hat - end (Rondo, Six Path Crossway). The hat comes from the bushes (npc/1541007.py).
sm.setSpeakerID(1541007)
sm.sendNext("That's it! That's my hat! Phew... Thank you.")
sm.sendSay("...You didn't see anything, did you? Under the hat, I mean.")
if sm.completeQuest(parentID):
    sm.sendSayOkay("Good. Let's keep it that way.")
sm.dispose()
