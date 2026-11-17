# Moco (3003608), in the Moonbridge story scenes: leave the story for now. It can be picked up again from
# [Moonbridge] Save the World (35600) in the quest notifier.
sm.setSpeakerID(3003608)
if sm.getChr().getInstance() is None:
    sm.sendSayOkay("The Alliance needs every hero it can get!")
elif sm.sendAskYesNo("Do you want to stop here for now? You can come back any time through "
                     "#b[Moonbridge] Save the World#k in the quest notifier."):
    sm.warpInstanceOut(450009050 if sm.hasQuestCompleted(35608) else 450007040, 0)
sm.dispose()
