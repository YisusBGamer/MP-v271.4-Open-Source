# id 867236300 (Abrup Basin : Icy Grim Vale), field 867236300
sm.lockInGameUI(True, False)
sm.setSpeakerType(3)
sm.setParam(37)
sm.setColor(1)
sm.setInnerOverrideSpeakerTemplateID(9400582) # Cayne
sm.sendNext("#face2#I'll take care of these here, don't worry!")
sm.sendSay("#face2#Gillie! The Jellyrashes must be destroyed before they take root!")
sm.setInnerOverrideSpeakerTemplateID(9400583) # Gillie
sm.sendSay("#face1#Understood!")
sm.setInnerOverrideSpeakerTemplateID(9400587) # Kan
sm.sendSay("#face1#Snowfield Archers! Rally around Gillie and take out those Jellyrashes!")
sm.lockInGameUI(False, True)
sm.createQuestWithQRValue(64272, "25=34;26=15;27=14;0=4;2=2")
sm.createQuestWithQRValue(64272, "25=34;26=15;27=15;0=4;2=2")
sm.createQuestWithQRValue(64272, "25=34;26=16;27=15;0=4;2=2")
sm.createQuestWithQRValue(64272, "25=34;26=16;27=16;0=4;2=2")
sm.createQuestWithQRValue(64272, "25=34;26=17;27=16;0=4;2=2")
sm.createQuestWithQRValue(64272, "25=34;26=17;27=17;0=4;2=2")
sm.completeQuestNoCheck(64140)
sm.createQuestWithQRValue(64270, "4=1")
sm.createQuestWithQRValue(64271, "5=1;6=1")
sm.warp(867202910)
