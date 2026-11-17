# Toolen City (410000401 1st Floor West Passageway, 410000402 Lobby, 410000403 2nd Floor West Passageway): Kain's
# starting area. The official direction scenes aren't in the data; the story continues through its quests.
# A Kain in the tutorial (beginner job) is pointed at its next step.
if chr.getJob() == 6003 and sm.getFieldID() == 410000401:
    if not sm.hasQuest(39401) and not sm.hasQuestCompleted(39401):
        sm.chatScript("Talk with the Regulator Sergeant.")
    elif sm.hasQuest(39401):
        sm.chatScript("Talk with the Regulator in East Side 1.")
sm.dispose()
