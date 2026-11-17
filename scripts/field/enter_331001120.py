# id 331001120 (Hideout : Training Room 2) - the robots: Check Yourself 1 (22711) starts here, and the door on the left
# (kine_go_tuto3) opens once the 5 Training Robot Bs are down.
ROBOT_B = 9300410
sm.lockInGameUI(True, False)
sm.removeAdditionalEffect()
sm.forcedFlip(True)
if not sm.hasMobsInField() and not sm.hasQuestCompleted(22711):
    for x in (-250, 0, 250, 500, 750):
        sm.spawnMob(ROBOT_B, x, 63, False)
sm.createFieldTextEffect("#fnﾳﾪﾴﾮﾰ￭ﾵ￱ ExtraBold##fs26#Kinesis's Hideout\r\n #fs14#- Training Room 2 -", 100, 2500, 4, 0, 0, 1, 4, 0, 0, 0)
sm.setSpeakerType(3)
sm.setParam(37)
sm.setColor(1)
sm.setInnerOverrideSpeakerTemplateID(1531001) # Jay
sm.sendNext("#face9#Let me see... got it. Perfect. Now that you've suffered through the boring yet EXTREMELY IMPORTANT part, we can cut you loose. Start with those 5 Training Robot Bs.")
sm.playSound("Sound/SoundEff.img/blackHeaven/metaldrop", 100)
sm.sendSay("#face9#Pick up anything you want with the Z key. Don't worry, I've got plenty of parts to rebuild these with. When you're done, take the door on the left to come meet me.")
sm.lockInGameUI(False, True)
if not sm.hasQuest(22711) and not sm.hasQuestCompleted(22711):
    sm.startQuestNoCheck(22711)
sm.progressMessageFont(3, 20, 20, 0, "Pick up items by pressing the Z key.")
sm.playExclSoundWithDownBGM("Voice3.img/Kinesis/guide_12", 100)
sm.avatarOriented("UI/tutorial.img/kinesis/1")
