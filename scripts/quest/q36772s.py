# [Labyrinth of Suffering] Source of Suffering (36772), the giant core at 450011660; handed in to Azalin at the Core
# Peak. Completing it opens Verus Hilla.
SOURCE = 3003716

if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.setPlayerAsSpeaker()
        sm.sendNext("(As soon as you defeated Hilla, a strange core appeared within the Labyrinth.)")
        sm.setSpeakerID(SOURCE)
        if sm.sendAskAccept("A strange, giant core has appeared within the Labyrinth."):
            sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setQRValue(parentID, "dummy", "1")
        sm.setPlayerAsSpeaker()
        sm.sendNext("(Maybe you'll get out of this place. Head back to the #m450011220#.)")
        sm.warp(450011220, 0)
sm.dispose()
