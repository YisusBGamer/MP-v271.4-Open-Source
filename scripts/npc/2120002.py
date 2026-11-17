# Steward (Central Hall) (Halloween - the Haunted Mansion, see HalloweenEvent)
NPC = 2120002
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

if can_start(15103, 15102):
    sm.sendNext("Welcome to the Haunted Mansion. I am the #bSteward#k of this house. You must be one of the "
                "#bMasked Gentleman#k's guests.")
    me("Who is the Masked Gentleman? Is this his house?")
    sm.sendNext("The master prefers his privacy. He will join the party when everything is ready. Until then, "
                "please make yourself at home and meet the other guests.")
    sm.sendNext("Miss #bSophelia#k is in her room, through the door on the left of this hall. She is... rather "
                "nervous. Do be gentle with her.")
    finish(15103)
elif ready(15125):
    me("Steward, what can you tell me about John Blatchford?")
    sm.sendNext("...I have not heard that name spoken in a very long time. Master John was a kind boy. He loved "
                "masquerades more than anything.")
    sm.sendNext("The night of the accident he wore a new mask, made especially for him. He was so proud of it. When "
                "they searched the wreck, the mask was all they found.")
    finish(15125)
elif can_start(15126, 15125):
    sm.sendNext("If the young master's spirit is in this house, I fear for Miss Sophelia. She has always been the "
                "one he watches.")
    if sm.sendAskAccept("The #rPumpkin Knights#k in #bGuest Room 2#k carry charms that ward off bad luck. Would you "
                        "bring one to Miss Sophelia for me?"):
        sm.startQuestNoCheck(15126)
        sm.sendSayOkay("#bGuest Room 2#k is the second door in this hall. Thank you, truly.")
elif sm.hasQuest(15126):
    sm.sendSayOkay("A #bPumpkin Knight's Charm#k, from #bGuest Room 2#k. Please take it to Miss Sophelia.")
else:
    sm.sendSayOkay("The party will begin soon. Please enjoy the mansion.")
