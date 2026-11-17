# Icarus (Kerning City) (Christmas in Happyville - the Missing Santa story, see ChristmasEvent)
NPC = 1052106
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


def start_icarus():
    if sm.sendAskAccept("Hmm, what's this? Roodolph wanted you to deliver this glass of warm milk to me? Oh, so it's supposed to help me fall asleep after I drink it. I'm crossing my fingers!"):
        sm.startQuestNoCheck(X.ICARUS)
        sm.sendSayOkay("I really hope so. I don't know why I can't fall asleep on Christmas Eve. Maybe I'm too excited about it! I was really sad the past two years since I didn't get anything from Santa Claus, but now that you've told me about it I can finally go to bed. Thanks! Santa Claus will come visit me this time, right? Please thank Roodolph for me.")
    else:
        sm.sendSayOkay('Any unfinished business in this town? Okay, please make sure to extend my thanks to Roodolph.')

if sm.hasQuest(X.MILK):
    if sm.hasItem(X.WARM_MILK):
        if finish(X.MILK):
            start_icarus()
    else:
        sm.sendSayOkay("Ah, I can't go to bed! I'm not sleepy at all! I really hope I can fall asleep early this Christmas.")
elif can_start(X.ICARUS, X.MILK):
    start_icarus()
elif sm.hasQuest(X.ICARUS):
    sm.sendSayOkay("Zzz... Zzz... (Icarus is fast asleep. Go tell #bRoodolph#k in Happyville.)")
else:
    default()

sm.dispose()
