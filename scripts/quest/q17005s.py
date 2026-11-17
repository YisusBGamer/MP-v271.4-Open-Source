# id 17005 ([Commerci] Bon Voyage 1), field 865000001
# sm.setSpeakerID(9390220) # Maestra Fiametta
# sm.setParam(5)
# sm.createQuestWithQRValue(17009, "step=1")
# sm.openUI(189)

# Making a Buck (17004) skips the tutorial voyages; a character that still has one open finishes it here.
sm.completeQuestNoCheck(parentID)
sm.dispose()
