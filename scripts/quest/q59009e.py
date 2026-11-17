# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Snow Leopard Guardian (59009): you've learned to use Lai's power.
if sm.hasQuest(parentID):
    sm.setSpeakerID(9390302)
    sm.sendNext("Not bad for your first time. That's the power of the Snow Leopard.")
    sm.completeQuest(parentID)
sm.dispose()
