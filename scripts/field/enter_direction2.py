# Damien between the phases (Normal 350160220 / Hard 350160120): the phase 2 spine scene (DemianIllust, 12.7 s), then phase 2.
# The server times the scene (sceneWait), so the skip button ends it early.
fieldId = sm.getFieldID()
sm.warpLaterIfStillIn(19000, fieldId, fieldId + 20)  # backup if the scene never ends
sm.lockInGameUI(True, False)
sm.spineIntro("intro", "Map/Effect2.img/DemianIllust/2pahseSp/003", "animation")
sm.sceneWait(13300)
sm.offSpineScreen("intro", 100, "", 0)
sm.lockInGameUI(False)
sm.warp(fieldId + 20, 0)
