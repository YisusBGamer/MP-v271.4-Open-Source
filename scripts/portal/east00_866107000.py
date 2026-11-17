# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Wolf Woods (866107000), the way back west: once you're heading home (59008), the Eastern Outskirts on the way
# to town; otherwise back the way you came.
if sm.hasQuest(59008) or sm.hasQuestCompleted(59008):
    sm.warp(866108000, 0)
else:
    sm.warp(866102000, 2)
sm.dispose()
