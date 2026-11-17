# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# The Heroes Set Out! (59018) - Arby: the Critter Champs are waiting outside town.
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(9390300)
    if sm.sendAskAccept("Everybody's waiting for you outside town! Time to be a hero!"):
        sm.startQuest(parentID)
if sm.hasQuest(parentID):
    sm.warp(866137000, 0)
sm.dispose()
