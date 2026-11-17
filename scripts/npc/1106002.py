# Limbert (1106002), Limbert's General Store (913070001-913070003): Mihile's chores 20031-20033.
# v265 marks them blocked (no lightbulb in the client), so Limbert hands them out and takes them in; the turn-ins
# do what q20031e-q20033e do.
LIMBERT = 1106002
POTION_BOX = 4033194
LETTER = 4033195
EGG = 4033196

sm.setSpeakerID(LIMBERT)


def manual_labor():
    if not sm.hasQuest(20031):
        sm.sendNext("What are you standing around for? The display stand is empty!")
        if sm.sendAskAccept("Go up to the #bAttic#k and fetch me the #bPotion Box#k. The one with a potion drawn on it. Move it!"):
            sm.startQuestNoCheck(20031)
        return
    if not (sm.hasItem(POTION_BOX) and sm.hasItem(LETTER)):
        sm.sendSayOkay("The Potion Box is in the #bAttic#k. Up the stairs. Go!")
        return
    sm.sendNext("That sure took you long enough.")
    sm.setPlayerAsSpeaker()
    sm.sendSay("Um... I found a letter in the attic. It wasn't opened... It's from some guy named Chromile.")
    sm.setSpeakerID(LIMBERT)
    sm.sendSay("What?! Who told you you could touch that?")
    sm.consumeItem(POTION_BOX)
    sm.consumeItem(LETTER)
    sm.giveItem(2001500, 10)
    sm.giveItem(2001503, 10)
    sm.completeQuestNoRewards(20031)
    sm.sendNext("Don't you ever touch my stuff without my permission again. Now get outta my sight!")
    sm.addLevel(2)
    sm.warp(913070002, 0)


def mopping_up():
    if not sm.hasQuest(20032):
        if sm.sendAskAccept("This place is filthy. Take the mop and clean away the #bGarbage Piles#k in the store. All of them!"):
            sm.startQuestNoCheck(20032)
        return
    if not sm.isComplete(20032):
        sm.sendSayOkay("Still dirty! Get rid of the Garbage Piles.")
        return
    sm.sendNext("Are you done cleaning yet? I suppose it looks moderately presentable in here.")
    sm.completeQuestNoRewards(20032)
    sm.giveItem(2001500, 20)
    sm.giveItem(2001503, 20)
    sm.addLevel(2)
    sm.warpInstanceIn(913070003, 0)


def back_yard():
    if not sm.hasQuest(20033):
        if sm.sendAskAccept("Go get me #b10 Eggs#k from the chicken coop in the #bBack Yard#k. And don't you dare stress out Bigby!"):
            sm.startQuestNoCheck(20033)
        return
    if not sm.isComplete(20033):
        sm.sendSayOkay("Where are my eggs? The Back Yard is out the door to the right.")
        return
    sm.sendNext("Where's the eggs? Wait a second, what happened to you?")
    sm.setPlayerAsSpeaker()
    sm.sendSay("Uh, well, you know how you told me not to mess with Bigby? Well... I kinda... He got out.")
    sm.setSpeakerID(LIMBERT)
    sm.sendSay("What?!! You will starve to death if that dog is not in my yard by dinnertime.")
    sm.completeQuestNoRewards(20033)
    sm.consumeItem(EGG, 10)
    sm.giveItem(2001500, 30)
    sm.giveItem(2001503, 30)
    sm.addLevel(2)
    sm.warpInstanceIn(913070004, 0)


if sm.getChr().getJob() != 5000 or not sm.hasQuestCompleted(20030):
    sm.sendSayOkay("We're closed. Come back tomorrow.")
elif not sm.hasQuestCompleted(20031):
    manual_labor()
elif not sm.hasQuestCompleted(20032):
    mopping_up()
elif not sm.hasQuestCompleted(20033):
    back_yard()
else:
    sm.sendSayOkay("Don't just stand there. Get back to work!")
sm.dispose()
