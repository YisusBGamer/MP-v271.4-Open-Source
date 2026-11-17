# Irvin's delivery (26414): the parcel to hand over (nothing else gives it)
sm.setSpeakerID(9072000)
if sm.questStartDialogue(parentID) and not sm.hasItem(3994828):
    sm.giveItem(3994828)
sm.dispose()
