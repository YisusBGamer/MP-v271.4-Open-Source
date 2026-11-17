# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Stump Town (866109000): the kobolds in town are beaten (59008); Lai is waiting.
if sm.hasQuest(59008):
    sm.setQRValue(59008, "mob", "5")
    sm.warp(866110000, 0)
