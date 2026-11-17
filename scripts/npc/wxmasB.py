# Happy, the Snow Fairy (Extra Frosty Snow Zone) (Christmas in Happyville - the Missing Santa story, see ChristmasEvent)
NPC = 9220004
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


sm.sendSayOkay("We're all thinking of a White Christmas, aren't we? Hit the #bSnow Blower#k and it'll make it snow! "
               "Everyone who hits it gets a #bGift Filled Christmas Sock#k, once a day.")

sm.dispose()
