# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Snow Leopard Guardian (59009) - Lai makes a pact with you: Critter Select and the Snow Leopard mode.
KOBOLD = 9390927
def spawn(mob, count, x0):
    for i in range(count):
        sm.spawnMob(mob, x0 + 120 * i, 28, False)

if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(9390302)
    sm.sendNext("So you're the one Arby's been going on about. Kobolds are attacking the town, and you can't fight "
                "them alone. Let's make a pact. Then my power is yours.")
    if sm.sendAskAccept("Ready?"):
        sm.startQuest(parentID)
        sm.giveSkill(110001510, 1, 1)  # Critter Select
        sm.giveSkill(110001502, 1, 1)  # Snow Leopard mode
        sm.setPlayerAsSpeaker()
        sm.sendNext("Whoa... I feel kind of tingly...")
if sm.hasQuest(parentID):
    if not sm.hasMobsInField(sm.getFieldID(), KOBOLD):
        spawn(KOBOLD, 5, 300)
    sm.setSpeakerID(9390302)
    sm.sendNext("Use the Change Mode skill and press the Right arrow key, boss! Shake it out!")  # official (Say.img)
    sm.sendSayOkay("Then press the Ctrl key to use the Snow Leopard Charge skill!")  # official (Say.img)
sm.dispose()
