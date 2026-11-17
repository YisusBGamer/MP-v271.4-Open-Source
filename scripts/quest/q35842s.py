# [Limina] The Erda Surges (35842), Orchid in World's Sorrow 2: check the surge (dir 1), Guwaru (3003985), break
# through the surge (clear 2). It completes by itself, so it's finished here.
ORCHID, GUWARU = 3003984, 3003985

if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.setSpeakerID(ORCHID)
        if sm.sendAskAccept("The Labyrinth Core has vanished, and something new has appeared. You must use Tana's "
                            "necklace to guide you as you move forward."):
            sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setPlayerAsSpeaker()
        sm.sendNext("(Tana's necklace reacts to the Erda surging up ahead.)")
        sm.setQRValue(parentID, "dir", "1")
        sm.setSpeakerID(GUWARU)
        sm.sendNext("Only you can defeat the Black Mage.")
        sm.onLayer(800, "bm0", 0, -80, 1, "Effect/Direction20.img/effect/BM3_orca/0", 4, False, -1, False)
        sm.setPlayerAsSpeaker()
        sm.sendNext("(Guided by the necklace, you break through the surge.)")
        sm.offLayer(500, "bm0", False)
        sm.setQRValue(parentID, "clear", "2")
        sm.completeQuest(parentID)
sm.dispose()
