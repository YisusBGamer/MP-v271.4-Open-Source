# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Snow Leopard Powah (59013) - Lai: more monsters (5 Rotting Kobolds).
ROTTING = 9390928
def spawn(mob, count, x0):
    for i in range(count):
        sm.spawnMob(mob, x0 + 120 * i, 28, False)

if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(9390302)
    if sm.sendAskAccept("More of them are coming! Use my power - show them what a Snow Leopard can do!"):
        sm.startQuest(parentID)
if sm.hasQuest(parentID) and not sm.hasMobsInField(sm.getFieldID(), ROTTING):
    spawn(ROTTING, 5, 300)
    sm.setSpeakerID(9390302)
    sm.sendSayOkay("Use the Change Mode skill and press the Right arrow key, boss! Shake it out!")  # official (Say.img)
sm.dispose()
