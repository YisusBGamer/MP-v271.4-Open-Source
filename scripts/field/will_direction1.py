# Will's intro (Easy 450007800, Normal 450008700, Hard 450008100), as GMS v271: the screen goes dark, his
# intro spine plays with its sound, and 7.85 s in the party moves on to phase 1 (+50). The server times the scene
# (sceneWait), so the skip button ends it early.
fieldId = sm.getFieldID()
sm.warpLaterIfStillIn(13000, fieldId, fieldId + 50)  # backup if the scene never ends
sm.lockInGameUI(True, False)
sm.blind(1, 255, 0, 0)
sm.sceneWait(1060)
sm.spineIntro("intro", "Effect/Direction20.img/bossWill/intro_spine/skeleton", "1")
sm.playSound("Sound/SoundEff.img/esfera/boss/intro1", 200)
sm.sceneWait(5660)
sm.onLayer(1000, "BlackOut", 0, 0, 13, "Map/Effect2.img/BlackOut", 4, True, -1, False)
sm.sceneWait(1100)
sm.offSpineScreen("intro", 100, "", 0)
sm.offLayer(100, "BlackOut", False)
sm.lockInGameUI(False)
sm.warp(fieldId + 50, 0)
