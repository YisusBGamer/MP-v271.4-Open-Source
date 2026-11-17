# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Wolf Fight (59005): the wolves are beaten, and you reach level 2 ("YOU'VE REACHED LEVEL 2!", 59006).
if sm.hasQuest(parentID):
    sm.setSpeakerID(9390463)
    sm.sendNext("You... you beat them all! I've never seen anything like it!")
    sm.setPlayerAsSpeaker()
    sm.sendNext("Pshaw, ain't no thang for an aspiring hero, yo.")
    if sm.completeQuest(parentID) and sm.getChr().getLevel() < 2:
        sm.addLevel(2 - sm.getChr().getLevel())
sm.dispose()
