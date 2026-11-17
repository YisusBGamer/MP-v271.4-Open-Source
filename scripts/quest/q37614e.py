# 37614 [Reverse City] Three Outsiders - end: Barr (3004616)
# Official summary: you defeated the Combat T-Drone Model Bs.
sm.setSpeakerID(3004616) # Barr
if not sm.completeQuest(parentID):
    sm.sendSayOkay("Defeat around 100 #bCombat T-Drone Model Bs#k.")  # official in-progress line
    sm.dispose()
sm.sendNext("Nice! That bought me enough time.")
sm.sendSayOkay("I still need more parts, though. Talk to me again.")
