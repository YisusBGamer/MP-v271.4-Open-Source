# Santa (Happyville) (Christmas in Happyville - the Missing Santa story, see ChristmasEvent)
NPC = 9209100
from net.swordie.ms.world import ChristmasEvent as X

ACTIVE = X.isActive()


def can_start(q, *prev):
    # the event is on, the level is right, the quests before it are done, and it isn't started yet
    if not ACTIVE or chr.getLevel() < X.MIN_LEVEL or sm.hasQuest(q) or sm.hasQuestCompleted(q):
        return False
    for p in prev:
        if not sm.hasQuestCompleted(p):
            return False
    return True


def finish(q):
    return X.finish(chr, q)


def default():
    line = sm.getRandomNpcDefaultLine(NPC)
    sm.sendSayOkay(line if line is not None else "Merry Christmas!")

sm.setSpeakerID(NPC)


if sm.sendAskYesNo("Ho ho ho! Merry Christmas! Would you like to visit #bSanta's House#k? There might be something "
                   "in your sock..."):
    sm.warp(X.SANTAS_HOUSE)

sm.dispose()
