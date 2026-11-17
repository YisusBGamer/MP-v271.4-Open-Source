# 37619 [Reverse City] Long Awaited - start: Singularity Maker (3004620), M Tower Top Floor
# The fight: the portal on this floor (east_450014240) leads to the Singularity Maker; destroying it finishes the
# objective (field script rc_singularity_destroyed). Hand it in to Barr.
sm.setSpeakerID(3004620) # Singularity Maker
sm.sendNext("(This is T-Boy's gravity machine, the #bSingularity Maker#k. It's humming louder and louder...)")
if not sm.sendAskAccept("(Barr says it has to be destroyed before it creates a black hole. Go through the portal and destroy it?)"):
    sm.dispose()
sm.startQuest(parentID)
sm.sendSayOkay("(Go through the portal next to the machine and destroy the #b#o8641059##k.)")
