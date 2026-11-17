# [Limina] Destiny Defined (35847), Grendel the Really Old in End of the World 1-1. Cygnus, who takes it back
# (3003992), stands nowhere: the scene is played here.
GRENDEL = 3003991
CYGNUS = 3003992

if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.setSpeakerID(GRENDEL)
        if sm.sendAskAccept("Communications have been restored. There is something everyone must hear."):
            sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.onLayer(800, "bm0", 0, -80, 1, "Effect/Direction20.img/effect/BM3_thunder/2", 4, False, -1, False)
        sm.setPlayerAsSpeaker()
        sm.sendNext("(Grendel the Really Old shocked everyone, but Empress Cygnus believed in you.)")
        sm.offLayer(500, "bm0", False)
        sm.setSpeakerID(CYGNUS)
        sm.sendNext("It's time to stop doubting yourself and prove everyone's trust in you with your actions.")
        sm.setQRValue(parentID, "dir", "1")
        sm.completeQuest(parentID)
sm.dispose()
