# Rina (Henesys) (Christmas in Happyville - the Missing Santa story, see ChristmasEvent)
NPC = 1010100
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


def lend_tools():
    if not sm.canHold(X.CHIMNEY_BROOM, 2):
        sm.sendSayOkay("Make room in your Etc inventory and I'll lend you the tools.")
        return
    if sm.sendAskAccept("How can I help you? You need a Chimney Sweep and a Flashlight? Sure, I can lend you those items. Funny enough, you aren't the first to ask. Well, I suppose Christmas season is just around the corner. Anyway, it's prepared so please feel free to borrow them."):
        sm.startQuestNoCheck(X.CHIMNEY)  # lends the Chimney Broom and the Flashlight
        sm.sendSayOkay("You're cleaning the Chimney? Santa Claus will love that! Merry Christmas, my dear!")
    else:
        sm.sendSayOkay("Ah, you don't need it now? Okay, Maple Claws must be waiting.")

if sm.hasQuest(X.SANTA_CAT):
    if finish(X.SANTA_CAT):
        lend_tools()
elif can_start(X.CHIMNEY, X.SANTA_CAT):
    lend_tools()
elif sm.hasQuest(X.CHIMNEY) and not (sm.hasItem(X.CHIMNEY_BROOM) and sm.hasItem(X.FLASHLIGHT)):
    for item in [X.CHIMNEY_BROOM, X.FLASHLIGHT]:
        if not sm.hasItem(item) and sm.canHold(item):
            sm.giveItem(item)
    sm.sendSayOkay("Lost them already? Here, take these. Maple Claws is waiting in Happyville!")
else:
    default()

sm.dispose()
