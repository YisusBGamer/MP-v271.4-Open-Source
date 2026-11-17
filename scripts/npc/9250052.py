# Paperboy (major towns): the Maple Herald's news, the story's start (Christmas in Happyville - the Missing Santa story,
# see ChristmasEvent)
NPC = 9250052
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


if can_start(X.NEWS):
    sm.sendNext("Welcome! This is the Maple Herald's Exclusive News stand. Would you like to buy today's newspaper?")
    if sm.sendAskYesNo("Oh, I'm sorry. All the newspapers are sold out due to exclusive, groundbreaking news. If it's any consolation, would you like to see the article that I scrapped this morning?"):
        sm.startQuestNoCheck(X.NEWS)
        sm.sendNext("[Maple Herald]\r\n#bSanta is NOT coming to our town this year#k\r\nIt's been revealed that Santa Claus has no plans to come to Maple World this year. Apparently, Santa Claus has been greatly disappointed with Maplers for not believing in him. It will certainly be a huge loss to Maple Community.\r\n-Senior Journalist A.Y, Maple Herald-")
        sm.sendSayOkay("Wow! What sad news, isn't it? A lot of reporters are trying to get a hold of #bRooney#k regarding this article. Oh, you don't know? Rooney is the go-to source regarding all things Happyville. Anyways, I apologize for not being able to provide you with a newspaper today. Please stop by next time!")
    else:
        sm.sendSayOkay('At Maple Herald, we always do our best to deliver news as accurately and quickly as possible. If you are curious about the happenings in Maple World today, please stop by!')
elif sm.hasQuest(X.NEWS):
    sm.sendSayOkay("Everybody's talking about that article! #bRooney#k is the one who knows all about Happyville. "
                   "He's right here in town.")
elif ACTIVE and chr.getLevel() < X.MIN_LEVEL:
    sm.sendSayOkay("Keep up with world events! (The Maple Herald's big story is for adventurers of #bLv. 25#k or "
                   "higher.)")
else:
    default()

sm.dispose()
