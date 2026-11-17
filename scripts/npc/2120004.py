# Jonas (Puppet Workshop) (Halloween - the Haunted Mansion, see HalloweenEvent)
NPC = 2120004
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

if can_start(15105, 15104):
    sm.sendNext("Mind the dolls, they bite. I'm #bJonas#k. I make toys... or I used to. Now I mostly fix the ones "
                "that break.")
    me("Sophelia said you've been here longer than anyone. Who is the Masked Gentleman?")
    sm.sendNext("...Longer than anyone, yes. Too long. Some questions are best left in the dark, traveler. Get some "
                "rest. The night is always long here.")
    finish(15105)
elif can_start(15130, 15129):
    me("Jonas, I read the documents in the Mask Room. You made John Blatchford's mask, the one he wore the night his "
       "carriage crashed.")
    sm.sendNext("...So you know. Yes. I carved it, lacquered it, and delivered it myself. I was proud of it. And when "
                "the carriage went over the edge, the boy's grief and fear went into that mask with him.")
    sm.sendNext("Every Halloween his spirit plays the piano in the hall, and every soul that hears it is invited "
                "back. Myself included. It's my fault. All of it.")
    if sm.sendAskAccept("I can make a #bTransparent Mantle#k to hide his piano, so no one hears the music again. "
                        "Bring me #b5 Invisible Cloth Pieces#k from the #rFire Steeds#k in #bGuest Room 4#k?"):
        sm.startQuestNoCheck(15130)
        sm.sendSayOkay("#bGuest Room 4#k is the last door in the Central Hall.")
elif ready(15130):
    sm.sendNext("Five pieces. That should be enough.")
    if finish(15130):
        sm.consumeItem(4034718, 5)
elif can_start(15131, 15130):
    if not sm.canHold(4034719):
        sm.sendSayOkay("Make some room in your Etc inventory first.")
    else:
        sm.sendNext("There. The #bTransparent Mantle#k. Whatever it covers, vanishes from sight and sound.")
        if sm.sendAskAccept("Cover the #bPiano#k in the #bCentral Hall#k with it. Will you do it?"):
            sm.startQuestNoCheck(15131)
            sm.giveItem(4034719, 1)
            sm.sendSayOkay("Go. Before the music starts again.")
elif sm.hasQuest(15131) and not sm.hasItem(4034719) and sm.canHold(4034719):
    sm.giveItem(4034719, 1)
    sm.sendSayOkay("You lost the mantle? I had a spare. Cover the #bPiano#k in the #bCentral Hall#k.")
elif ready(15132):
    me("I covered the piano, but nothing happened. The music still echoes through the halls.")
    sm.sendNext("Then it was never the piano. It's the #bmask#k. As long as it exists, he'll keep inviting us "
                "back.")
    sm.sendNext("There's only one thing in this mansion that could break a curse like that... and you won't like "
                "it. Go to the little devil, #bJoey#k. And be ready to pay his price.")
    finish(15132)
else:
    sm.sendSayOkay("Mind the dolls. They bite.")
