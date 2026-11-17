# Santa (Santa's House) (Christmas in Happyville - the Missing Santa story, see ChristmasEvent)
NPC = 9209101
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


if not ACTIVE:
    sm.sendSayOkay("Ho ho ho! See you next Christmas!")
else:
    r = X.santaPresent(chr)
    if r == 1:
        sm.sendSayOkay("Ho ho ho! You've been very good this year. Here's a little something for your sock!\r\n\r\n"
                       "#i2430607# #t2430607#\r\n#b(Come back tomorrow: one a day.)#k")
    elif r == 0:
        sm.sendSayOkay("Ho ho ho! I've already filled your sock today. Come back tomorrow!")
    elif r == -1:
        sm.sendSayOkay("Ho ho ho! I fill the socks that hang on the #bChristmas Tree#k in Happyville. Do you have "
                       "one there yet? (Rooney in the major towns knows the story.)")
    else:
        sm.sendSayOkay("Ho ho ho! Make room in your Use inventory and I'll fill your sock.")

sm.dispose()
