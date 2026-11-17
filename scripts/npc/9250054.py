# Bicho (Happyville) (Christmas in Happyville - the Missing Santa story, see ChristmasEvent)
NPC = 9250054
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


PARTS = [(1, "Branch Snowman"), (2, "Metal Bucket Snowman"), (4, "Straw Hat Snowman")]

if can_start(X.SNOWMAN) and sm.hasQuest(X.HANGING):
    sm.sendNext("Roll! Roll! Roll the snow! I'm going to make the biggest snowman in the world!")
    sm.sendNext("...But every time I put it together, it just looks like three snowballs. What makes a snowman a "
                "#rreal#k snowman?")
    if sm.sendAskAccept("There are three snowmen in Happyville: the #bBranch Snowman#k, the #bMetal Bucket "
                        "Snowman#k and the #bStraw Hat Snowman#k. Could you ask them their secret for me? My hands "
                        "are too cold to go myself."):
        sm.startQuestNoCheck(X.SNOWMAN)
        sm.sendSayOkay("Thank you! I'll keep rolling!")
elif sm.hasQuest(X.SNOWMAN):
    bits = X.snowmanParts(chr)
    if bits == 7:
        sm.sendNext("Branch arms, a metal bucket hat and a straw hat... wait, two hats? Well, it IS the biggest "
                    "snowman in the world!")
        if finish(X.SNOWMAN):
            sm.sendSayOkay("Look at him! He's perfect! Here, this is for you. Merry Christmas!\r\n\r\n#b(You got a "
                           "Mapler Recommendation.)#k")
    else:
        left = [name for bit, name in PARTS if not bits & bit]
        sm.sendSayOkay("Did you ask them yet? Still to ask: #b" + ", ".join(left) + "#k.")
elif sm.hasQuestCompleted(X.SNOWMAN):
    sm.sendSayOkay("The biggest snowman in the world! Thanks again!")
else:
    default()

sm.dispose()
