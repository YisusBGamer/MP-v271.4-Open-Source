# Mimet (Guest Room 1) (Halloween - the Haunted Mansion, see HalloweenEvent)
NPC = 2120026
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

if sm.hasQuest(15106):
    sm.sendNext("Eek! Don't sneak up on me like that! I'm #bMimet#k, and I'm NOT in a tizzy, I'm just... looking "
                "for my other shoe. And my hat. And the party.")
    me("#b(She's running in circles. So much for resting in the guest room.)#k")
    sm.sendNext("Have you met #bJoey#k? He lives behind #bSophelia#k's portrait in the #bFrame Room#k on the 2nd "
                "floor. He's a DEVIL. A real one! Don't let him talk you into anything!")
    sm.setQRValue(15138, "mimet", "1")
    finish(15106)
else:
    sm.sendSayOkay("Where IS my other shoe?!")
