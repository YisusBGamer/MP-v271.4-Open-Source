# Sophelia (Sophelia's Room) (Halloween - the Haunted Mansion, see HalloweenEvent)
NPC = 2120005
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

if can_start(15104, 15103):
    sm.sendNext("Oh! You startled me... I'm #bSophelia#k. Did you get an invitation from #bhim#k too? The "
                "#bMasked Gentleman#k?")
    me("Do you know who he is?")
    sm.sendNext("N-no. Nobody's ever seen his face. He just... sends the invitations. Every Halloween. And every "
                "Halloween, we come.")
    sm.sendNext("You should talk to #bJonas#k, the toymaker. He's been here longer than any of us. His "
                "#bPuppet Workshop#k is through the #bChimney#k in the #bCentral Hall#k.")
    finish(15104)
elif ready(15119):
    me("Sophelia, I found this note inside the piano. \"Help me. He won't let us leave.\" Did you write this?")
    sm.sendNext("I... I don't know what you're talking about! Put that away before he sees it!")
    if finish(15119):
        sm.sendSayOkay("#b(She's terrified. I should give her a moment and try again.)#k")
elif ready(15120):
    me("Sophelia, I want to help. Whatever's going on in this mansion, you don't have to face it alone.")
    sm.sendNext("...Alright. Yes, I wrote it. Every Halloween the music starts, and we all come back here. The same "
                "night, over and over. I can't remember how many times. I can't remember the last time I saw the "
                "sun.")
    sm.sendNext("I think it has to do with the old #bnewspaper#k in the #bLibrary#k. Nobody ever lets me read it.")
    finish(15120)
elif can_start(15121, 15120):
    me("#b(I tell Sophelia about the Old Newspaper: a carriage accident, on Halloween night, near this mansion.)#k")
    sm.sendNext("A carriage accident...? There's a #bBroken Carriage#k in the #bAbandoned Graveyard#k outside. I've "
                "always been too scared to go near it.")
    if sm.sendAskAccept("Could you look at the carriage for me? Maybe there's something there that explains all "
                        "this."):
        sm.startQuestNoCheck(15121)
        sm.sendSayOkay("The graveyard is to the left of the mansion's entrance. Please be careful.")
    else:
        sm.sendSayOkay("I understand. It's a frightening place.")
elif ready(15121):
    sm.sendNext("A #bDeer Emblem#k? I've never seen it before... but it must belong to the people in that carriage.")
    if finish(15121):
        sm.sendSayOkay("Lady #bLudmilla#k knows all the old families. Maybe she'd recognize it.")
elif ready(15123):
    me("The Blatchfords lived here. Their carriage crashed on Halloween night, and their son John was never found.")
    sm.sendNext("John Blatchford... The Masked Gentleman's invitations are always signed #bJ.B.#k Do you think...?")
    finish(15123)
elif can_start(15124, 15123):
    sm.sendNext("There's a #bFaceless Portrait#k in the #bFrame Room#k on the 2nd floor. The nameplate is "
                "scratched, but... could it be him?")
    if sm.sendAskAccept("Will you take a closer look at it?"):
        sm.startQuestNoCheck(15124)
        sm.sendSayOkay("The #bFrame Room#k is on the #b2nd floor#k. Come back and tell me what you find.")
elif ready(15124):
    me("The portrait's nameplate says \"John Blatchford\". His face was torn out of the canvas, as if someone "
       "didn't want him remembered.")
    sm.sendNext("Then the Masked Gentleman really is... the young master of this house.")
    if finish(15124):
        sm.sendSayOkay("The #bSteward#k has served this mansion forever. If anyone knew John, it's him.")
elif ready(15126):
    sm.sendNext("A charm? From the Steward? ...He always did worry about me. Thank you.")
    if finish(15126):
        sm.consumeItem(4034716, 1)
elif can_start(15127, 15126):
    me("#b(I tell Sophelia about the Old Brooch in the Attic, and the words written on its back.)#k")
    sm.sendNext("\"Bound by contract until the last guest departs\"... A #bcontract of doom#k? That sounds like "
                "the work of a devil.")
    if sm.sendAskAccept("There IS a devil in this mansion: #bJoey#k, in the #bSecret Room#k. Will you ask him about "
                        "it?"):
        sm.startQuestNoCheck(15127)
        sm.sendSayOkay("The #bSecret Room#k is behind my portrait in the #bFrame Room#k. Don't agree to anything "
                       "he says!")
elif ready(15134):
    me("Sophelia... Joey says he'll only help if he gets a lock of your hair.")
    sm.sendNext("My HAIR? For a devil? No... no, I can't...")
    finish(15134)
elif can_start(15135, 15134):
    sm.sendNext("...I've thought about it. If it ends this endless night, it's worth it.")
    if not sm.canHold(4034720):
        sm.sendSayOkay("Make some room in your Etc inventory first.")
    elif sm.sendAskAccept("Here. Take it to Joey before I change my mind."):
        sm.startQuestNoCheck(15135)
        sm.giveItem(4034720, 1)
        sm.sendSayOkay("Please... make this the last Halloween.")
elif sm.hasQuest(15135) and not sm.hasItem(4034720) and sm.canHold(4034720):
    sm.giveItem(4034720, 1)
    sm.sendSayOkay("You lost it? Here... take some more. Hurry.")
elif sm.hasQuest(15121):
    sm.sendSayOkay("The #bBroken Carriage#k is in the #bAbandoned Graveyard#k, left of the mansion's entrance.")
else:
    sm.sendSayOkay("Every year the music starts, and every year we come back...")
