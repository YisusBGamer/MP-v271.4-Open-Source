# 410000810 (Burning Cernium), on entering.
# [Cernium (After)] Defensive Tactics (39921) starts with Carlisle (3004518), a hidden story NPC; the client didn't
# offer it from him. Once Salvation (39920) is done, Carlisle offers it here himself, and then the way to the Carlisle
# who hands it in (3004516, 410000800).

QUEST = 39921
END_MAP = 410000800

if sm.hasQuestCompleted(39920) and not sm.hasQuest(QUEST) and not sm.hasQuestCompleted(QUEST) \
        and chr.getLevel() >= 265:
    sm.setSpeakerID(3004518) # Carlisle
    sm.sendNext("Ask #bCarlisle#k, in #b#m410000810##k, for a status report.")
    if sm.questStartDialogue(QUEST):
        sm.sendNext("#b(Carlisle gives you his report. The ceremony will begin soon.)#k")
        if sm.sendAskYesNo("#p3004516# is waiting for you.\r\n\r\nShall I take you to #b#m" + str(END_MAP) + "##k?"):
            sm.warp(END_MAP)
sm.dispose()
