# Analyzing the Samples 2 (62136), start - Wang Ching Ying (Shanghai story).
# The client has no lines for it; written from the quest's own Quest.wz summary ("Wang Ching Ying hands you an Analysis
# Report, stating he didn't find anything noteworthy about the samples. Talk to Zhang Wei about it.").
WANG = 9310541
REPORT = 4034647  # Analysis Report

if not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(WANG)
    if not sm.canHold(REPORT):
        sm.sendSayOkay("Make some room in your Etc inventory first.")
    elif sm.hasQuest(parentID):
        if not sm.hasItem(REPORT):
            sm.giveItem(REPORT)
            sm.sendSayOkay("Here's the report again. Take it to #p9310535#.")
    else:
        sm.sendNext("I've finished analyzing the rooster, sheep and cow cell samples you brought me.")
        if sm.sendAskAccept("I'm afraid I didn't find anything noteworthy. Here's the #t4034647:#. Would you take it to #p9310535#?"):
            sm.startQuest(parentID)
            sm.giveItem(REPORT)
sm.dispose()
