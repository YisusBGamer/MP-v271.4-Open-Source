# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Wolf Woods (866107000): the five wolves are beaten (Wolf Fight, 59005).
if sm.hasQuest(59005):
    sm.setQRValue(59005, "mob", "5")
    sm.chatScript("You defeated all the wolves. Talk to the cat.")
