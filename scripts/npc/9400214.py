# Edgar Tetch (Land of Warriors) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400214
sm.setSpeakerID(NPC)


def me(text):
    # the player's own lines
    sm.setPlayerAsSpeaker()
    sm.sendNext(text)
    sm.setSpeakerID(NPC)


def done(q):
    return sm.hasQuestCompleted(q)


def has(q):
    return sm.hasQuest(q)


def fresh(q, *prev):
    # not started or done yet, and the quests before it are done
    if sm.hasQuest(q) or sm.hasQuestCompleted(q):
        return False
    for p in prev:
        if not sm.hasQuestCompleted(p):
            return False
    return True


def start(q):
    sm.startQuestNoCheck(q)


def finish(q):
    return A.finish(chr, q)


def ready(q):
    # a hunt or collect quest whose Quest.wz requirements are met
    return sm.hasQuest(q) and sm.isComplete(q)


def count(item):
    return sm.getQuantityOfItem(item)


def spawn_balrog():
    sm.spawnMob(A.FASHIONABLE_BALROG, chr.getPosition().getX() + 200, chr.getPosition().getY(), False)

if fresh(63031, 63030):
    sm.sendNext("A way home? I've looked for years. Only found one clue: a #rFashionable Balrog#k who wears a "
                "golden key around his neck. Struts around here like he owns the place.")
    start(63031)
    spawn_balrog()
    sm.sendSayOkay("THERE he is! Get that key!")
elif has(63031):
    if sm.hasItem(A.BALROG_KEY):
        finish(63031)
        sm.sendSayOkay("You got it! ...It's tiny. Show it to #bAshkaya#k anyway, she knows the Huge Door.")
    elif sm.sendAskYesNo("Find #o9402055#. ...He ran off? He always comes back when I call. Shall I?"):
        spawn_balrog()
elif has(63102):
    sm.sendNext("The statue? That's the #bReady Spear#k. He's held that spear up for a thousand years, waiting for a "
                "worthy target. He'll only throw it when the ground is soft... after a good rain.")
    sm.setQRValue(63102, "state", "1")
    finish(63102)
    sm.sendSayOkay("If you can make it rain, the children in the #bLand of Innocence#k cry at the drop of a hat.")
else:
    sm.sendSayOkay("There must be a way out of here.")

sm.dispose()
