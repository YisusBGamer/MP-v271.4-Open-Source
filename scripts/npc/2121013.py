# Old Newspaper (the strange attraction in the Library) (Halloween - the Haunted Mansion, see HalloweenEvent)
NPC = 2121013
from net.swordie.ms.world import HalloweenEvent

ACTIVE = HalloweenEvent.isActive()


def me(text):
    # the player's own lines
    sm.setPlayerAsSpeaker()
    sm.sendNext(text)
    sm.setSpeakerID(NPC)


def can_start(q, prev):
    return ACTIVE and chr.getLevel() >= HalloweenEvent.MIN_LEVEL and sm.hasQuestCompleted(prev) \
        and not sm.hasQuest(q) and not sm.hasQuestCompleted(q)


def ready(q):
    return sm.hasQuest(q) and sm.isComplete(q)


def finish(q):
    # completes the quest with its rewards (EXP, tickets, coins); starts the next "notifier" quest
    return HalloweenEvent.finish(chr, q)


def follow(q, field, text):
    # a "strange attraction" quest that just started: offer to follow it now (Spin Man can take you later)
    if sm.hasQuest(q) and sm.sendAskYesNo(text):
        sm.warp(field)

if sm.hasQuest(15110):
    sm.sendNext("#b(A yellowed newspaper, dated Halloween night many years ago:)#k\r\n\r\n#eTRAGEDY ON THE "
                "MANSION ROAD#n\r\nA carriage carrying guests home from the Blatchford masquerade overturned on the "
                "cliff road last night. There were no survivors. The Blatchfords' son is still missing...")
    if finish(15110):
        sm.sendNext("#b(What was that? I'd better get back to Ludmilla before she notices I'm gone.)#k")
        sm.warp(229000210)
else:
    sm.sendSayOkay("#b(An old, yellowed newspaper.)#k")
