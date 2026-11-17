# Damien intro (Normal 350160260 / Hard 350160160): his spine scene (Map.wz Effect2.img DemianIllust, 10 s), then phase 1.
# The server times the scene (sceneWait), so the skip button ends it early.
fieldId = sm.getFieldID()
sm.warpLaterIfStillIn(15000, fieldId, fieldId - 60)  # backup if the scene never ends
sm.lockInGameUI(True, False)
sm.spineIntro("intro", "Map/Effect2.img/DemianIllust/1pahseSp/demian", "animation")
sm.sceneWait(9300)
sm.offSpineScreen("intro", 100, "", 0)
sm.lockInGameUI(False)
sm.warp(fieldId - 60, 0)
