# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Save the Cat 2 (59004): you give Arby his bell back (the quest takes it).
if sm.hasQuest(parentID) and sm.hasItem(4034005):
    sm.setSpeakerID(9390300)
    sm.sendNext("My bell! You found it! You're amazing!")
    if sm.completeQuest(parentID):
        sm.sendNext("Wait... Do you hear that? Something's coming...")
        sm.warp(866105000, 0)
sm.dispose()
