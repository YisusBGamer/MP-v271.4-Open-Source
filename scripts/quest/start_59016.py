# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Live to Fight Another Day (59016, no start script of its own): too many monsters - everyone climbs a big tree to
# safety, and so do you (866131000; Lai and Fort wait at the top).
if sm.hasQuest(59016):
    sm.warp(866131000, 15)
    sm.balloonMsg("Press the Up arrow key in front of the vines to climb!")
