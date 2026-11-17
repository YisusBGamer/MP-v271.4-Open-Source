# 36313 Infiltrate (Rondo, Mine Entrance 310040200): disguised in a Black Wings hat, into the mine. Ends by itself;
# Rondo waits in the Power Plant Lobby (310050000) to start 36314.
sm.setSpeakerID(1541004)
sm.sendNext("So you're the ones asking about the Black Wings. Name's Rondo. Keep your voice down.")
sm.sendSay("The Seal Stone you're after is inside the mine. The Black Wings guard every door, but they don't look too closely at anyone wearing one of their hats.")
if not sm.sendAskAccept("Put this #bBlack Wings Hat#k on and follow me in. Ready?"):
    sm.dispose()
sm.startQuest(parentID)
sm.completeQuest(parentID)
sm.setQRValue(36300, "05", "h1")  # Rondo in the Power Plant Lobby
sm.chatScript("You slipped into the mine. Talk to Rondo in the Power Plant Lobby.")
sm.warp(310050000)
sm.dispose()
