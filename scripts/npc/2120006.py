# Ludmilla (Main Room) (Halloween - the Haunted Mansion, see HalloweenEvent)
NPC = 2120006
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

if can_start(15108, 15107):
    sm.sendNext("Hmph. Another one of the #bMasked Gentleman's#k guests, I suppose. You may call me #bLudmilla#k. "
                "LADY Ludmilla.")
    me("#b(She's looking me up and down like I'm something the cat dragged in...)#k")
    sm.sendNext("This party will be the event of the season, and I simply refuse to be seen in a mansion this "
                "DUSTY. You look like you could make yourself useful. Come back to me when you're ready to work.")
    finish(15108)
elif can_start(15109, 15108):
    sm.questStartDialogue(15109, NPC)
elif ready(15109):
    sm.sendNext("The #bLibrary#k is spotless! Well, spotless enough. I knew you were the cleaning type, darling.")
    if finish(15109):
        sm.sendNext("Now run along, I need my beauty rest... Hm? You look like you've seen a ghost. This IS a haunted "
                    "mansion, you know.")
        follow(15110, 922900000, "#b(While I was cleaning the Library, something caught my eye...)#k\r\n" + "#b(Something about that place keeps nagging at me... I feel a strange attraction pulling me back there.)#k\r\n\r\nFollow the strange attraction now?")
elif can_start(15111, 15110):
    sm.questStartDialogue(15111, NPC)
elif ready(15111):
    sm.sendNext("No more ghosts in #bGuest Room 3#k? Splendid. I'd almost call you competent.")
    if finish(15111):
        follow(15112, 922900100, "#b(There was a weird doodle on the wall of Guest Room 3...)#k\r\n" + "#b(Something about that place keeps nagging at me... I feel a strange attraction pulling me back there.)#k\r\n\r\nFollow the strange attraction now?")
elif can_start(15113, 15112):
    sm.questStartDialogue(15113, NPC)
elif ready(15113):
    sm.sendNext("My dress! Oh, it's PERFECT. Every eye at the party will be on me, as it should be.")
    if finish(15113):
        sm.consumeItem(4034707, 1)
        follow(15114, 922900800, "#b(In the Attic, a purple brooch was sparkling on the dressing table...)#k\r\n"
               + "#b(Something about that place keeps nagging at me... I feel a strange attraction pulling me back there.)#k\r\n\r\nFollow the strange attraction now?")
elif can_start(15115, 15114):
    sm.questStartDialogue(15115, NPC)
elif ready(15115):
    sm.sendNext("Candles! Now THAT is lighting worthy of my dress. Set them down there, and do be careful with the "
                "wax.")
    if finish(15115):
        sm.consumeItem(4034708, 5)
        follow(15116, 922900300, "#b(I keep hearing something calling me from the Portrait Gallery...)#k\r\n"
               + "#b(Something about that place keeps nagging at me... I feel a strange attraction pulling me back there.)#k\r\n\r\nFollow the strange attraction now?")
elif can_start(15117, 15116):
    sm.questStartDialogue(15117, NPC)
elif ready(15117):
    sm.sendNext("Soul Flowers, black bows and jack-o-lanterns. The Central Hall will look positively ghoulish!")
    if finish(15117):
        sm.consumeItem(4034709, 10)
        sm.consumeItem(4034710, 10)
        sm.consumeItem(4034711, 10)
elif can_start(15118, 15117):
    sm.sendNext("One last thing. The #bPiano#k in the #bCentral Hall#k. Have you HEARD it? It's so out of tune it "
                "makes my teeth ache, and nobody dances to a sour piano.")
    sm.sendNext("The dolls in #bJonas#k's #bPuppet Workshop#k play with all his tools. One of those wretched "
                "#rDestroyed Dolls#k must have run off with his #bTuning Hammer#k. Get it back and tune that piano!")
    if sm.sendAskAccept("Will you tune the piano for the party?"):
        sm.startQuestNoCheck(15118)
        sm.sendSayOkay("The #bPuppet Workshop#k is through the #bChimney#k in the #bCentral Hall#k. Off you go!")
    else:
        sm.sendSayOkay("Then the party will be RUINED, and everyone will know whose fault it was.")
elif can_start(15122, 15121):
    me("#b(I show Ludmilla the Deer Emblem from the Broken Carriage.)#k")
    sm.sendNext("Where did you get that? That's the crest of the #bBlatchfords#k. The family that lived in this "
                "mansion before any of us...")
    sm.sendNext("Their carriage went off the road one Halloween night, on the way home from a masquerade. Lord and "
                "Lady Blatchford didn't survive. And their son, young #bJohn#k... they never found him.")
    sm.sendNext("I don't like talking about it. Go and bother #bSophelia#k with it, she's the one who keeps asking "
                "questions.")
    if finish(15122) and sm.hasItem(4034715):
        sm.consumeItem(4034715, 1)
elif sm.hasQuest(15118):
    sm.sendSayOkay("The #bTuning Hammer#k! The #rDestroyed Dolls#k in the #bPuppet Workshop#k have it. Then tune the "
                   "#bPiano#k in the #bCentral Hall#k.")
elif sm.hasQuestCompleted(15108) and not sm.hasQuestCompleted(15118):
    sm.sendSayOkay("Don't just stand there gawking, darling. The party won't prepare itself.")
else:
    sm.sendSayOkay("Every party needs a star. Luckily, this one has me.")
