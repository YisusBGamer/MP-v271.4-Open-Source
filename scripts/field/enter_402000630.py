# id 402000630 (null), field 402000630
# This scene (Ark and Ferret land below the sinkhole) belongs to "Distorted Signal" and marks itself with dir=1 on
# that quest. It plays once, for a character who has that quest (in progress or done), not on every visit (this is a
# hunting map for some).
STORY_QUEST = 34931
if (sm.hasQuest(STORY_QUEST) or sm.hasQuestCompleted(STORY_QUEST)) \
        and "dir=1" not in (sm.getQRValue(STORY_QUEST) or "").split(";"):
    sm.createQuestWithQRValue(34931, "dir=1;exp=1")
    sm.lockInGameUI(True, False)
    sm.removeAdditionalEffect()
    sm.zoomCamera(0, 2000, 0, -142, -250)
    sm.blind(True, 255, 0, 0, 0, 0)
    sm.sendDelay(1200)
    sm.blind(False, 0, 0, 0, 0, 1000)
    sm.sendDelay(1400)
    sm.sendDelay(500)
    sm.zoomCamera(3000, 1000, 3000, 100, 0)
    sm.sendDelay(3500)
    sm.setSpeakerType(3)
    sm.setParam(37)
    sm.setColor(1)
    sm.setInnerOverrideSpeakerTemplateID(3001510) # Ferret
    sm.sendNext("#face2#So this is what's below the sand.")
    sm.sendSay("#face2#And now we've all been separated.")
    sm.setInnerOverrideSpeakerTemplateID(3001500) # Ark
    sm.sendSay("#face0#Well...")
    sm.blind(True, 150, 0, 0, 0, 500)
    sm.playSound("Sound/SoundEff.img/PinkBean/expectation", 100)
    sm.onLayer(500, "d0", 0, -80, -1, "Effect/Direction17.img/effect/ark/illust/7/1", 4, True, -1, False)
    sm.sendDelay(1000)
    sm.blind(False, 0, 0, 0, 0, 500)
    sm.offLayer(500, "d0", False)
    sm.sendDelay(500)
    sm.sendNext("#face0#At least we got this.")
    sm.setInnerOverrideSpeakerTemplateID(3001510) # Ferret
    sm.sendSay("#face0#Wow! You managed to catch that while we were falling? Impressive!")
    sm.sendSay("#face2#I'm not happy about being this far underground. What was that demolitions dummy thinking?!")
    sm.sendSay("#face2#Now we're going to waste a bunch of time we don't have tracking everyone down.")
    sm.showFadeTransition(0, 1000, 3000)
    sm.zoomCamera(0, 1000, 2147483647, 2147483647, 2147483647)
    sm.moveCamera(True, 0, 0, 0)
    sm.sendDelay(300)
    sm.removeOverlapScreen(1000)
    sm.moveCamera(True, 0, 0, 0)
    sm.lockInGameUI(False, True)
