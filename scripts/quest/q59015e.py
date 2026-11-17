# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Like, Grrr and Stuff (59015): the monsters are beaten, thanks to Fort.
if sm.hasQuest(parentID):
    sm.setSpeakerID(9390301)
    sm.sendNext("Like, grrr and stuff! Told ya.")
    sm.completeQuest(parentID)
sm.dispose()
