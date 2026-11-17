# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Restoring HP (59011): the potions (the quest's own reward).
if sm.hasQuest(parentID):
    sm.setSpeakerID(9390302)
    sm.sendNext("Better? Keep some potions on you. You'll need them.")
    sm.completeQuest(parentID)
    sm.balloonMsg("Press I to open your inventory, then double-click a red potion to drink it.")
sm.dispose()
