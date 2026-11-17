# Chosen Seren's intro (Normal 410002100, Hard 410002000, Extreme 410002200), as GMS v271: the screen goes
# dark, her phase 1 spine plays with its sound, and 12.8 s in the party moves on to phase 1 (+20). The server times
# the scene (sceneWait), so the skip button ends it early.
fieldId = sm.getFieldID()
sm.warpLaterIfStillIn(18000, fieldId, fieldId + 20)  # backup if the scene never ends
sm.lockInGameUI(True, False)
sm.blind(1, 255, 0, 0)
sm.sceneWait(1150)
sm.spineIntro("intro", "Effect/Direction20.img/bossSeren/1pahse_spine/skeleton", "animation")
sm.playSound("Sound/SoundEff.img/seren/1phase", 200)
sm.sceneWait(11620)
sm.offSpineScreen("intro", 100, "", 0)
sm.lockInGameUI(False)
sm.warp(fieldId + 20, 0)
