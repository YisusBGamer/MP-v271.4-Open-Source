# Irvin's delivery (26412): the parcel to hand over (nothing else gives it)
sm.setSpeakerID(9072000)
if sm.questStartDialogue(parentID) and not sm.hasItem(3994826):
    sm.giveItem(3994826)
sm.dispose()
