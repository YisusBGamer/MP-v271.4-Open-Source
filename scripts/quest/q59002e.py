# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Brave Like a Hero (59002): you made it to Wolf Forest (field/onUserEnter_866103000.py sets the record).
if sm.hasQuest(parentID):
    sm.setPlayerAsSpeaker()
    sm.sendNext("Ha! Wolf Forest! The place even grown-ups are scared of! And here I am, all alone. "
                "Yup, I've got the courage of a true hero.")
    sm.completeQuest(parentID)
sm.dispose()
