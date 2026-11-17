# Moco (3004300), in every Convergence story map: the way out to the Maple Alliance Outpost; @next brings the player
# back to where the story goes on
OUTPOST = 993120000

if sm.sendAskYesNo("Want to step out to the #bMaple Alliance Outpost#k for now? You can come back to this part of the "
                   "story any time with #b@next#k."):
    sm.warp(OUTPOST, 0)
sm.dispose()
