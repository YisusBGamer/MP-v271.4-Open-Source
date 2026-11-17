# [Limina] Toward the Black Mage (35841), from the quest notifier after the Labyrinth. Guwaru, who hands it in, stands
# nowhere: his part is played here, then on to World's Sorrow 2 (450012010), where The Erda Surges (35842) starts.
GUWARU = 3003954

if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.setPlayerAsSpeaker()
        if sm.sendAskAccept("You defeated Hilla in the Labyrinth of Suffering Deep Core. Now it's time for you to carry on "
                            "toward the Black Mage."):
            sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setQRValue(parentID, "dir", "1")
        sm.onLayer(800, "bm0", 0, -80, 1, "Effect/Direction20.img/effect/BM3_forest/0", 4, False, -1, False)
        sm.setSpeakerID(GUWARU)
        sm.sendNext("Only you can defeat the Black Mage.")
        sm.setPlayerAsSpeaker()
        sm.sendNext("(Orchid has revealed herself. What's her story?)")
        sm.offLayer(500, "bm0", False)
        if sm.completeQuest(parentID):
            if sm.getChr().getInstance() is not None:
                sm.warpInstanceOut(450012010, 0)
            else:
                sm.warp(450012010, 0)
sm.dispose()
