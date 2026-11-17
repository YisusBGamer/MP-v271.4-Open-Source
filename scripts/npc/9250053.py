# Christmas Tree (Happyville) (Christmas in Happyville - the Missing Santa story, see ChristmasEvent)
NPC = 9250053
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


SIDES = [X.TORR, X.CHIMNEY, X.ICARUS, X.SNOWMAN]


def sides_done():
    for q in SIDES:
        if not sm.hasQuestCompleted(q):
            return False
    return True

if can_start(X.HANGING, X.KNITTING):
    if not sm.hasItem(X.COMPLETED_SOCKS):
        sm.sendSayOkay("Merry Christmas! Bring the #bCompleted Socks#k Mrs. Claus knitted for you, and hang them "
                       "here.")
    elif not sm.hasItem(X.VOL_4):
        sm.sendSayOkay("Merry Christmas! Santa only fills the socks of those who know what he's like: bring "
                       "#bSanta Encyclopedia No.4#k. (Monsters near your level have the volumes.)")
    elif sm.sendAskYesNo('Merry Christmas! Would you like to hang your Christmas Socks on the tree for a Christmas present?'):
        sm.startQuestNoCheck(X.HANGING)
        sm.sendSayOkay('You have successfully hung the Christmas socks on the tree.\r\nNow what you need are #bMapler Recommendations#k. Please be kind and do the good things to others before Santa Claus puts a gift into your Socks. Your good conduct will be very effective for what kind of present you will be getting from Santa Claus.' + "\r\n\r\n#b(Cliff, Maple Claws, Roodolph and Bicho in Happyville need a hand.)#k")
    else:
        sm.sendSayOkay("Please talk to me again when you change your mind. Don't forget, you must come back before this event is over!")
elif sm.hasQuest(X.HANGING):
    if sides_done() and X.count(chr, X.RECOMMENDATION) >= 4:
        sm.sendNext("Four Mapler Recommendations! Happyville has never seen such a kind adventurer. Look, your "
                    "sock... it's full!")
        if finish(X.HANGING):
            sm.sendSayOkay("Santa Claus must have heard about your good deeds. And look, there's a book in the sock "
                           "too: #bSanta Encyclopedia No.6#k! Take all six volumes to #bRooney#k.")
    else:
        sm.sendSayOkay('There are people in some places who still need your help. Just like you have done so far, please look around this town and perform good deeds to others.' + "\r\n\r\n#b(Mapler Recommendations: %d/4)#k" % X.count(chr, X.RECOMMENDATION))
else:
    default()

sm.dispose()
