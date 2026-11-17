# 37604 [Reverse City] The Rat Snack - start: Rasol (3004609)
sm.setSpeakerID(3004609) # Rasol
sm.sendNext("Down here, #b#o8641051##k are what's for lunch. Don't make that face.")
sm.sendSay("Their meat doesn't look very tasty, but it keeps us alive.")
if not sm.sendAskAccept("Bring me #b20 #t4036631##k. Defeat #o8641051# in the Subway Tunnels."):
    sm.dispose()
sm.startQuest(parentID)
