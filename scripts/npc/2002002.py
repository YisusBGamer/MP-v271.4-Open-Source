# Torr (Happyville) (Christmas in Happyville - the Missing Santa story, see ChristmasEvent)
NPC = 2002002
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


if sm.hasQuest(X.TORR):
    sm.sendNext("Sobs.. Sobs... T.T\r\nYou are not from this town right? You are the first one to talk to me today... Sobs... Sobs... I can't believe that people in this town has no interest in me what so ever just because I had to quit the Santa's Sledge club as I was not qualified. This is just too much for me to handle.")
    if sm.sendAskYesNo("Sobs.... T.T\r\nI wasn't expecting a big welcome from the town people but I sure was expecting a warm welcome from #bGranpa Cliff#k... Now it seems like even he is out of town! What shall I do...? Where shall I go...?"):
        sm.sendNext("You mean Granpa Cliff??? He will be waiting for me on the 2nd floor of Center tower? How did he get up there anyway with his weak strength? I knew that he couldn't live without me! I should have never left the town...")
        if finish(X.TORR):
            sm.sendSayOkay("Thanks for letting me know where Cliff is! The thought of spending Christmas alone was so horrible that I was going to leave Happyville forever and become a monster outside of El Nath. I'm gonna go to Cliff and give him a big hug now... Merry Christmas!" + "\r\n\r\n#b(You got a Mapler Recommendation.)#k")
elif sm.hasQuestCompleted(X.TORR):
    sm.sendSayOkay("Granpa Cliff gave me the biggest hug! Merry Christmas!")
else:
    sm.sendSayOkay("Sniff... Merry Christmas...")

sm.dispose()
