# 37605 [Reverse City] Popping Purple Rat Meat - start: Rasol (3004609); end: Rasol (3004610) in #m450014080#
sm.setSpeakerID(3004609) # Rasol
sm.sendNext("Barr won't touch Blue Rat Meat. He only eats the purple stuff from #b#o8641052##k.")
sm.sendSay("They live deeper in, around #b#m450014080##k. Let's go.")
if not sm.sendAskAccept("Meet me in #b#m450014080##k. Ready?"):
    sm.dispose()
sm.startQuest(parentID)
