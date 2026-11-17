# Portal script enterDelivery.
# Serpent Path: the path to the Resistance's Secret Meeting Place, for Delivering the Test Subject's Medicine (23125),
# Secret Meeting (23132) and Suspicions (23133-23135).
if any(sm.hasQuest(q) for q in (23125, 23132, 23133, 23134, 23135)):
    sm.warp(931000430, 1)
else:
    chr.chatMessage("There's nothing down that path for you right now.")
sm.dispose()
