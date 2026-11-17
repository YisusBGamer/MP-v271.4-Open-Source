# [Odium] The Wellspring (38318): Hundun hands over the original Godsphere, then the flame leads to the fated
# battleground (993217019 -> 993217020, own copy) where the First Adversary waits
HUNDUN = 3005540
STRANGE_PLACE = 993217019

sm.setSpeakerID(HUNDUN)
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    if sm.sendAskAccept("You've come this far, Adversary. Listen, and take what I've guarded for so long: the original "
                        "#bGodsphere#k."):
        sm.startQuest(parentID)
if sm.hasQuest(parentID):
    sm.warpInstanceIn(STRANGE_PLACE, 0)
sm.dispose()
