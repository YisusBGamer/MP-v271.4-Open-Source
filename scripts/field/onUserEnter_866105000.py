# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Wolf Woods (866105000): just as the cat is saved, the wolves come (Wolf Fight, 59005, is offered next).
if sm.hasQuestCompleted(59004) and not sm.hasQuest(59005) and not sm.hasQuestCompleted(59005):
    sm.setSpeakerID(9390300)
    sm.sendNext("Wolves! A whole pack of them! Now you know why they call it Wolf Forest...")
    sm.warp(866107000, 0)
