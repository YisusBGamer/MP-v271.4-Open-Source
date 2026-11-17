# Joey (Secret Room) (Halloween - the Haunted Mansion, see HalloweenEvent)
NPC = 2120007
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

if can_start(15107, 15106):
    sm.sendNext("Heh heh heh. A new guest! I'm #bJoey#k. I'm a devil. A real one! Want to make a deal?")
    me("#b(He's... knee-high. And very pleased with himself.)#k No deals. I'm looking for the Masked Gentleman.")
    sm.sendNext("Boring! Everybody's looking for him. Nobody ever finds him. Go say hello to #bLady Ludmilla#k in "
                "the #bMain Room#k on the 2nd floor. She LOVES new people. Heh heh heh.")
    finish(15107)
elif ready(15127):
    me("Joey, what do you know about a contract of doom? \"Bound until the last guest departs\"?")
    sm.sendNext("Ooooh, THAT contract. I know everything about it. But knowledge has a price, guest. It always "
                "does.")
    finish(15127)
elif can_start(15128, 15127):
    sm.sendNext("Here's my price: hair. Not just any hair! The #bTrashed Hair#k lying around the #bCentral Hall#k. "
                "Devils collect all sorts of things.")
    if sm.sendAskAccept("Bring me some #bTrashed Hair#k and I'll tell you what you want to know. Deal?"):
        sm.startQuestNoCheck(15128)
        sm.sendSayOkay("Look on the floor of the #bCentral Hall#k. You'll see it now that you're looking for it.")
elif ready(15128):
    sm.sendNext("Mmm, lovely and grimy. A deal's a deal.")
    if finish(15128):
        sm.consumeItem(4034717, 1)
        sm.sendNext("The contract binds the guests to the mansion as long as the #bmask#k exists. Who made the "
                    "mask? Who wore it? Heh heh heh...")
elif can_start(15129, 15128):
    sm.sendNext("I'll throw in a #bthrilling fact#k for free. Go through the #bWeird Room#k next to the Portrait "
                "Gallery. There's a #bMask Room#k behind it, full of old papers nobody's supposed to read.")
    if sm.sendAskAccept("Want to see what's in there?"):
        sm.startQuestNoCheck(15129)
        sm.sendSayOkay("The #bWeird Room#k is on the #b2nd floor#k. Heh heh heh.")
elif ready(15133):
    me("Joey, I need to break the mask. Jonas says you're the only one who can help.")
    sm.sendNext("Finally! The fun part. I have an #bEvil Hammer#k that can smash any ward, curse or contract. But "
                "a deal with a devil...")
    finish(15133)
elif can_start(15134, 15133):
    sm.sendNext("...needs a price. I want a lock of #bSophelia's Hair#k. Heh heh heh. Ask her nicely.")
    if sm.sendAskAccept("Bring me Sophelia's Hair, and the hammer is yours. Deal?"):
        sm.startQuestNoCheck(15134)
        sm.sendSayOkay("Go on, go on!")
elif ready(15135):
    sm.sendNext("Ooooh, it's so shiny. A deal is a deal!")
    if finish(15135):
        sm.consumeItem(4034720, 1)
elif can_start(15136, 15135):
    if not sm.canHold(4034721):
        sm.sendSayOkay("Make some room in your Etc inventory first.")
    else:
        sm.sendNext("Here's the #bEvil Hammer#k. Smash the #bGloomy Mask#k in the #bMask Room#k, and the contract "
                    "breaks. Just don't blame me for what happens next. Heh heh heh.")
        if sm.sendAskAccept("Take the Evil Hammer?"):
            sm.startQuestNoCheck(15136)
            sm.giveItem(4034721, 1)
elif sm.hasQuest(15136) and not sm.hasItem(4034721) and sm.canHold(4034721):
    sm.giveItem(4034721, 1)
    sm.sendSayOkay("Lost it already? Devils are generous today. Go smash that mask!")
elif ready(15137):
    if not sm.canHold(HalloweenEvent.HALLOWEEN_MASK):
        sm.sendSayOkay("Make some room in your Equip inventory first. I have something for you.")
    else:
        sm.sendNext("You're back! Heh heh... you look confused. Wondering where everybody went?")
        sm.sendNext("Sophelia, Mimet, Ludmilla, the Steward, old Jonas... they were all guests at the Blatchfords' "
                    "last masquerade. The night the carriage went over the edge. They've been dead for a very, "
                    "very long time.")
        sm.sendNext("John's mask held all of them here, one Halloween after another. You broke it. They're free. "
                    "No more endless night.")
        me("And you, Joey?")
        sm.sendNext("Me? I'm a devil. I go where I please. Heh heh heh. Take this mask as a souvenir - one that "
                    "WON'T trap anybody. Happy Halloween, guest.")
        finish(15137)
else:
    sm.sendSayOkay("Heh heh heh. Want to make a deal?")
