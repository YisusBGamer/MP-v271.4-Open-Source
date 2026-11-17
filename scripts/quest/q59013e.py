# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Snow Leopard Powah (59013): the monsters are beaten; Fort the bear joins the fight.
if sm.hasQuest(parentID):
    sm.setSpeakerID(9390302)
    sm.sendNext("Good. But they keep coming... We'll need Fort.")
    if sm.completeQuest(parentID):
        sm.warp(866127000, 0)
sm.dispose()
