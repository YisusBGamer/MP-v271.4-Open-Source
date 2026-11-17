# id 34305 ([Lachelein] Lucid Dreams), field 450003100
sm.setSpeakerType(3)
sm.setParam(4)
sm.setInnerOverrideSpeakerTemplateID(3003201) # Protective Mask
sm.sendNext("I heard you need to go downstream. If we succeed in #bliberating this city of dreams#k, you will be able to achieve your goal as well. ")
sm.setSpeakerType(4)
sm.setSpeakerID(3003201) # Protective Mask
sm.setParam(0)
if not sm.sendAskAccept("Won't you lend us your strength?"):
    sm.sendNext("I see... Well, tell me when you change your mind.")
    sm.dispose()
sm.setSpeakerType(3)
sm.setParam(4)
sm.sendNext("Thank you. Talk to Gray Mask for details.")
sm.startQuest(parentID)
sm.completeQuestNoCheck(parentID)
sm.startQuest(34306)
sm.completeQuestNoCheck(34306)
sm.startQuest(34307)
sm.completeQuestNoCheck(34307)
