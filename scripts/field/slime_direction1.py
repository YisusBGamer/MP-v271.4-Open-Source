# Ramuramu Temple (160010000): Guardian Angel Slime (Chaos) intro, then the fight map. As GMS: input locked, the screen
# blinded, the slime's spine with its sound, then the fight. The server times it (sceneWait), so Skip ends it early.
fieldId = sm.getFieldID()
sm.warpLaterIfStillIn(11000, fieldId, 160020000)  # backup if the scene never ends
sm.lockInGameUI(True, False)
sm.blind(1, 255, 0, 0)
sm.sceneWait(1200)
if not sm.isSceneSkipping():
    sm.spineIntro("intro", "Effect/Direction20.img/bossSlime/1phase_spine/skeleton", "animation")
    sm.playSound("Sound/SoundEff.img/bossSlime/1phase", 200)
sm.sceneWait(5000)
sm.offSpineScreen("intro", 100, "", 0)
sm.lockInGameUI(False)
sm.warp(160020000, 0)
