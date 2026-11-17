# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# A Hero's Return to Town (59008) - Arby goes to gather the other Critter Champs; meet them in town.
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(9390463)
    sm.sendNext("I'll go round up the other Critter Champs.")
    sm.sendNext("I'll meetcha in town! Bye!")  # official (Say.img)
    sm.setPlayerAsSpeaker()
    if sm.sendAskAccept("Wait till Bran gets a load of this!"):
        sm.startQuest(parentID)
if sm.hasQuest(parentID):
    sm.warp(866108000, 0)
sm.dispose()
