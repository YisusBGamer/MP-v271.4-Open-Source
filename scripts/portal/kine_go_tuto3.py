# go_ajit in Training Room 2 (331001120) and the Hideout training rooms: on to the final stage once the robots are down
field = sm.getFieldID()
if field == 331001120:
    if sm.hasMobsInField():
        sm.chatScript("Defeat the Training Robot Bs first.")
        sm.dispose()
    else:
        sm.setSpeakerType(3)
        sm.setParam(36)
        sm.setColor(1)
        sm.setInnerOverrideSpeakerTemplateID(1531000) # Kinesis
        sm.sendNext("Jay, I feel so slow walking around like this. I'm going to switch to my speedier moves.")
        sm.setInnerOverrideSpeakerTemplateID(1531001) # Jay
        sm.sendSay("#face9#Fine, whatever! Just ignore the test plan I spent hours on... Okay, I updated my database with your #bTriple Jump#k and #bAttack Skills#k for the final stage. Go nuts, dude.")
        sm.lockInGameUI(True, False)
        sm.playSound("Sound/Field.img/masteryBook/EnchantSuccess", 100)
        sm.createQuestWithQRValue(22700, "V01=1;V02=1;kinetuto=1;E1=1;E2=1")
        sm.sendDelay(2500)
        sm.playExclSoundWithDownBGM("Voice3.img/Kinesis/guide_04", 100)
        sm.lockInGameUI(False, True)
        sm.warp(331001130)
else:
    # the Hideout's own training rooms lead back to the HQ
    sm.warp(331001000)
