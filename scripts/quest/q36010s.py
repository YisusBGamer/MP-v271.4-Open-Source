# [Ramuramu] Psych Exam (36010), start - the Unsigned Letter (quest notifier, Lv 210).
# The client has no lines for it; written from the quest's own Quest.wz summaries ("Someone sent a letter." / "You
# received the letter and decided to find the village." / "You have arrived in Ramuramu Valley."). Arriving finishes
# it (field/enter_160070000).
if not sm.hasQuestCompleted(parentID):
    sm.setPlayerAsSpeaker()
    if sm.hasQuest(parentID) or sm.sendAskAccept("(Someone sent you a letter. It isn't signed... It asks you to come and find a village called #bRamuramu Valley#k. Will you go?)"):
        if not sm.hasQuest(parentID):
            sm.startQuest(parentID)
        sm.warp(160070000, 0)
sm.dispose()
