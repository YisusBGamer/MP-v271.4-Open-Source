# id 17007 ([Commerci] Bon Voyage 3), field 865000001
# sm.setSpeakerID(9390220) # Maestra Fiametta
# sm.setParam(5)
# sm.createQuestWithQRValue(17009, "step=3")
# sm.openUI(189)
# sm.setSpeakerType(3)
# sm.warp(865000100)

# Making a Buck (17004) skips the tutorial voyages; a character that still has one open finishes it here.
sm.completeQuestNoCheck(parentID)
sm.dispose()
