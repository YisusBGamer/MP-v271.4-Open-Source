# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Arby Trap (reactor 8669000), Wolf Woods: a few hits smash it (Save the Cat 1, 59003).
reactor.incHitCount()
if reactor.getHitCount() >= 3:
    if sm.hasQuest(59003):
        sm.setQRValue(59003, "react1", "1")
        sm.chatScript("You smashed the trap to pieces. Hero style.")
    sm.removeReactor()
sm.dispose()
