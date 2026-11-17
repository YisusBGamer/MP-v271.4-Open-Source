# id 1 (pt_r), field 940203000
sm.setSpeakerType(3)
sm.setParam(37)
sm.setColor(1)
sm.setInnerOverrideSpeakerTemplateID(3001300) # Ex
if sm.hasItem(4036162, 3):  # Collecting Parts (34801) asks 3; its drops stop after that
    # a portal script has no quest: parentID here is the portal
    sm.completeQuestNoCheck(34801)
    sm.createQuestWithQRValue(34801, "hunt1=1;exp=1")
    sm.sendNext("#face0#Perfect! Now, let's move on and gather more.")
    sm.lockInGameUI(False, True)
    sm.lockInGameUI(False, True)
    sm.warp(940202019)
else:
    sm.sendNext("You're still missing some materials!")