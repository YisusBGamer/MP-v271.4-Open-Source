# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Wolf Fight (59005) - Arby: fight or flight? Fight, hero style. Five wolves come at you.
WOLF = 9390931
def spawn_wolves():
    for x in (520, 640, 760, 880, 1000):
        sm.spawnMob(WOLF, x, 60, False)

if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(9390463)
    sm.sendNext("There are so many of them! What do we do? Should we run?")
    sm.setPlayerAsSpeaker()
    if sm.sendAskAccept("Run? A hero never runs! Stand back, kitty. I'll take care of these wolves!"):
        sm.startQuest(parentID)
        if not sm.hasMobsInField(sm.getFieldID(), WOLF):
            spawn_wolves()
        sm.balloonMsg("Press Ctrl to attack the wolves!")
        sm.setSpeakerID(9390463)
        sm.sendSayOkay("If you're not going to play with the wolves, can I play with them instead?")  # official (Say.img)
sm.dispose()
