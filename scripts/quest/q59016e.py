# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Live to Fight Another Day (59016): safe at the top of the tree. You sleep for three days...
# From Grandmother's House We Go (59017) starts once its record 59021 says fly=2.
if sm.hasQuest(parentID):
    sm.setSpeakerID(9390302)
    sm.sendNext("You made it. We're safe up here, for now. Rest. You've earned it.")
    if sm.completeQuest(parentID):
        sm.setQRValue(59021, "fly", "2")
        sm.chatScript("You slept for three days after the monster invasion.")
        sm.warp(866135000, 0)
sm.dispose()
