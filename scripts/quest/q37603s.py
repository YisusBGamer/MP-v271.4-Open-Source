# 37603 [Reverse City] Surviving in the Jungle - start: Alamo (3004603), Subway Tunnel Refuge
sm.setSpeakerID(3004603) # Alamo
sm.sendNext("You're awake! Rasol carried you all the way here after #bT-Boy#k knocked you out.")
sm.sendSay("T-Boy turned our city upside-down with his gravity gadgets. We've been hiding down here in the tunnels ever since.")
if not sm.sendAskAccept("If you want to help, #bRasol#k knows the tunnels best. She's waiting in #b#m450014060##k. Will you go?"):
    sm.dispose()
sm.startQuest(parentID)
sm.sendSayOkay("Go east to #b#m450014060##k. Rasol will guide you.")
