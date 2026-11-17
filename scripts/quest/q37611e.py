# 37611 [Reverse City] We Are Not Rats - end: Barr (3004614), Subway Tunnel Refuge
# Official summary: you decided to work together and stand up to T-Boy. Follow Barr to the Remote Subway Tunnel.
sm.setSpeakerID(3004614) # Barr
if not sm.completeQuest(parentID):
    sm.sendSayOkay("Go left if you're going to return to the Subway Tunnel Refuge.")  # official in-progress line
    sm.dispose()
sm.sendNext("It's settled. The townspeople will shut down T-Boy's #bextinction accelerators#k so the Singularity Maker can't draw in more Erda.")
sm.sendSay("You and I will go after T-Boy himself. Follow me to the #b#m450014130##k.")
sm.sendSayOkay("(Barr heads off toward the Remote Subway Tunnel, past Barr's Abode.)")
