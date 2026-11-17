# 36311 Seal Stone of Victoria Island (Athena Pierce): what was stolen. Ends by itself.
sm.setSpeakerID(1012100)
sm.sendNext("I owe you the truth now. The treasure that was stolen is the #bSeal Stone of Victoria Island#k, one of the most precious artifacts on this continent.")
if not sm.sendAskAccept("It holds the power to reshape and restore the land itself. In the wrong hands... I don't want to imagine it."):
    sm.dispose()
sm.startQuest(parentID)
sm.completeQuest(parentID)
sm.sendSayOkay("We must find out who really has it. Talk to me again when you're ready.")
sm.dispose()
