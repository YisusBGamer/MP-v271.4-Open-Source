# 37611 [Reverse City] We Are Not Rats - start: Barr (3004601), Barr's Abode
# Official summary: go to the Subway Tunnel Refuge and talk to Barr; you decide to work together and stand up to
# T-Boy. Done by returning to the Subway Tunnel Refuge (field script enter_450014050).
sm.setSpeakerID(3004601) # Barr
sm.sendNext("A black hole. And I thought he just liked hearing about my books... I should have seen it coming.")
sm.sendSay("We can't keep hiding in these tunnels like rats. If we're going to stop the #bSingularity Maker#k, the whole town has to work together.")
if not sm.sendAskAccept("Come back to the #b#m450014050##k with me. Let's talk to everyone."):
    sm.dispose()
sm.startQuest(parentID)
sm.sendSayOkay("Go left if you're going to return to the Subway Tunnel Refuge.")  # official in-progress line
