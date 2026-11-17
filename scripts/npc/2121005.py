# Piano (Central Hall) (Halloween - the Haunted Mansion, see HalloweenEvent)
NPC = 2121005
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

if ready(15118):
    sm.sendNext("#b(I tap the strings with the Tuning Hammer until each note rings clear. As the last string "
                "settles, something rustles inside the piano...)#k")
    if finish(15118):
        sm.consumeItem(4034712, 1)
elif can_start(15119, 15118):
    sm.sendNext("#b(There's a small folded note tucked between the strings. It says:)#k\r\n\r\n"
                "\"Help me. He won't let us leave. - S\"")
    sm.startQuestNoCheck(15119)
    sm.sendSayOkay("#b(S... Sophelia? I should ask her about it.)#k")
elif ready(15131):
    sm.sendNext("#b(I drape the Transparent Mantle over the piano. It fades from sight...)#k")
    if finish(15131):
        sm.sendSayOkay("#b(...and a moment later the music starts again, from nowhere at all. This didn't work. I "
                       "should report back to Jonas.)#k")
else:
    sm.sendSayOkay("#b(A grand piano. The keys are cold to the touch.)#k")
