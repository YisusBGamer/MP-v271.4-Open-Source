# Victoria Island or Bust (36306) - Vasily (22000) at Maple Road : Port: the boat ticket gets you on board for Lith
# Harbor, where Sugar waits with The Adventure Begins (36307)
LITH_HARBOR = 104000000

sm.setSpeakerID(22000)
sm.sendNext("A boat ticket, eh? Then you're an adventurer heading for Victoria Island. The ship's about to leave.")
if sm.sendAskAccept("Ready to board for #bLith Harbor#k?"):
    sm.startQuest(parentID)
    sm.completeQuest(parentID)
    sm.warp(LITH_HARBOR, 0)
else:
    sm.sendNext("Don't take too long. The ship won't wait forever.")
sm.dispose()
