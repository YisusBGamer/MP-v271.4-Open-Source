# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Save the Cat 2 (59004) - Arby lost his bell up in the tree
BELL = 4034005
BELL_TREE = 8662001
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(9390300)
    sm.sendNext("My bell... When I got caught in that trap, my little bell went flying up into that tree...")
    sm.setPlayerAsSpeaker()
    if sm.sendAskAccept("Your bell? Leave it to me! A hero never leaves a cat's bell behind!"):
        sm.startQuest(parentID)
if sm.hasQuest(parentID) and not sm.hasItem(BELL):
    if not sm.hasReactors():
        sm.spawnReactor(BELL_TREE, 640, 70)
    sm.setSpeakerID(9390300)
    sm.sendSayOkay("Bell, bell, come back to me! Ctrl to attack the tree, Z to pick up the bell!")  # official (Say.img)
sm.dispose()
