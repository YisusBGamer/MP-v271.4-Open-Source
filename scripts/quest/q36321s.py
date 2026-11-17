# 36321 Mysterious Voice: a voice gives the player strength against the Black Shadow. Ends by itself, then offers the
# fight (36322).
sm.setSpeakerID(1541063)
sm.sendNext("...Can you hear me? Young one whose heart longs to protect others...")
if not sm.sendAskAccept("Do not be afraid. The power to stand against the darkness already sleeps within you. Let me awaken it."):
    sm.dispose()
sm.startQuest(parentID)
sm.completeQuest(parentID)
sm.setPlayerAsSpeaker()
sm.sendNext("#b(Warmth spreads through your body. You feel stronger. You can fight the Black Shadow, and win.)#k")
if sm.sendAskYesNo("#b(Face the Black Shadow now?)#k"):
    sm.startQuest(36322)
    if sm.hasQuest(36322):
        sm.setQRValue(36300, "84", "h1")  # the Black Shadow on Maple Tree Hill (retry)
        sm.warpInstanceIn(993166023, 0)
else:
    sm.chatScript("Accept 'Battle Against the Black Shadow' from your quest list when you're ready.")
sm.dispose()
