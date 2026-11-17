# id 34926 (Equipment Prep), field 402000600
sm.completeQuestNoCheck(parentID)
sm.createQuestWithQRValue(parentID, "exp=1")
sm.setSpeakerType(3)
sm.setParam(36)
sm.setColor(1)
sm.setInnerOverrideSpeakerTemplateID(3001510) # Ferret
sm.sendNext("#face0#You're back! That ought to be enough. Thanks!")
sm.sendSay("#face2#I'll get started on this signal device. Why don't you see if Salvo needs help now?")
