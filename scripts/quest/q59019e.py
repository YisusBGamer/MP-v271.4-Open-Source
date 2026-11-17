# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Reading Maps (59019): off to Stump Town (To Stump Town, 59020: talk to its chief, Woodrock). The tutorial ends at
# level 10 like the Express skip.
if sm.hasQuest(parentID) and sm.completeQuest(parentID):
    sm.chatScript("Maple World is so vast... Next stop: Stump Town!")
    if not sm.hasQuest(59020) and not sm.hasQuestCompleted(59020):
        sm.startQuest(59020)
    if sm.getChr().getLevel() < 10:
        sm.addLevel(10 - sm.getChr().getLevel())
    sm.warpInstanceOut(866000000, 0)
sm.dispose()
