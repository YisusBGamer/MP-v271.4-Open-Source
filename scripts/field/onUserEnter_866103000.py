# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Wolf Woods (866103000): reaching it finishes Brave Like a Hero (59002); a cat (Arby) is caught in a trap here
# (Save the Cat 1, 59003): the trap (reactor 8669000, Arby Trap) is set next to him.
ARBY_TRAP = 8669000
if sm.hasQuest(59002):
    sm.setQRValue(59002, "map", "1")
    sm.setPlayerAsSpeaker()
    sm.sendNext("Ha! Wolf Forest! The place even grown-ups are scared of! And here I am, all alone. "
                "Yup, I've got the courage of a true hero.")
    sm.completeQuest(59002)
    sm.sendNext("...Huh? What's that sound? Something's crying over there...")
if sm.hasQuest(59003) and "react1=1" not in (sm.getQRValue(59003) or "") and not sm.hasReactors():
    sm.spawnReactor(ARBY_TRAP, 286, 71)
