# Maid (Central Hall) (Halloween - the Haunted Mansion, see HalloweenEvent)
NPC = 2120003
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

if can_start(15137, 15136):
    sm.sendNext("You... you're still here? Then you don't know. Miss Sophelia, Miss Mimet, Lady Ludmilla... they "
                "vanished the moment that mask shattered. The whole house went quiet.")
    if sm.sendAskAccept("The little devil in the #bSecret Room#k was laughing. He knows what happened. Will you ask "
                        "#bJoey#k?"):
        sm.startQuestNoCheck(15137)
        sm.sendSayOkay("Please. I need to know.")
else:
    sm.sendSayOkay("Welcome to the Haunted Mansion. Please enjoy the party.")
