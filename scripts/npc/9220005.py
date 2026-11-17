# Roodolph (Happyville, Extra Frosty Snow Zone) (Christmas in Happyville - the Missing Santa story, see ChristmasEvent)
NPC = 9220005
from net.swordie.ms.world import ChristmasEvent as X

ACTIVE = X.isActive()


def can_start(q, *prev):
    # the event is on, the level is right, the quests before it are done, and it isn't started yet
    if not ACTIVE or chr.getLevel() < X.MIN_LEVEL or sm.hasQuest(q) or sm.hasQuestCompleted(q):
        return False
    for p in prev:
        if not sm.hasQuestCompleted(p):
            return False
    return True


def finish(q):
    return X.finish(chr, q)


def default():
    line = sm.getRandomNpcDefaultLine(NPC)
    sm.sendSayOkay(line if line is not None else "Merry Christmas!")

sm.setSpeakerID(NPC)


if sm.getFieldID() == X.SNOW_ZONE:
    if sm.sendAskYesNo("Had enough snow? Shall I take you back to Happyville?"):
        sm.warp(X.HAPPYVILLE)
elif can_start(X.MILK) and sm.hasQuest(X.HANGING):
    sm.sendNext("Oh, poor Icarus still can't fall asleep during the Christmas season? Hmm, I really hope Santa Claus doesn't pass over his house again. For the past few years, Santa Claus wanted to give him some gifts, but he had to skip his house because Icarus was still awake! How can we help him this year? Oh, yes! I heard that a glass of warm milk will help him sleep like a baby!")
    if sm.sendAskAccept('So, will it be possible for you to deliver a glass of warm milk to Icarus on my behalf?'):
        sm.startQuestNoCheck(X.MILK)
        sm.sendSayOkay("Thanks! That's very kind of you. I'm sure Icarus will get a present from Santa Claus with your help. And your kindness will not go unrewarded either, I'm sure! Icarus is in Kerning City; you should be able to purchase a glass of warm milk from the store there. Please deliver that to Icarus before it gets cold!")
    else:
        sm.sendSayOkay("Ah, I guess you are too busy to do a small favor for the good of Maple World! I'll have to find somebody else then!  ")
elif sm.hasQuest(X.MILK) and not sm.hasItem(X.WARM_MILK):
    if sm.sendAskYesNo("Icarus is in #bKerning City#k, and the #bpharmacy#k there sells #bWarm Milk#k. Oh, you "
                       "couldn't find any? ...I have one glass left. Shall I give it to you?"):
        if sm.canHold(X.WARM_MILK):
            sm.giveItem(X.WARM_MILK)
            sm.sendSayOkay("Hurry, before it gets cold!")
        else:
            sm.sendSayOkay("Make room in your Etc inventory first.")
elif sm.hasQuest(X.ICARUS):
    sm.sendNext("I can see that you have successfully given him the milk. He should be in la-la land by now. Good job! I would like to reward you for what you've done.")
    if finish(X.ICARUS):
        sm.sendSayOkay('Here, take this recommendation to the Christmas Tree and something nice will happen to you. Have a great holiday! Merry Christmas!' + "\r\n\r\n#b(You got a Mapler Recommendation.)#k")
else:
    if sm.sendAskYesNo("Hi. Please don't look at my shiny nose... Do you want to head to the #bsnow sprinkler#k? The "
                       "#bSnow Blower#k there has a Christmas sock for everyone who hits it, once a day."):
        sm.warp(X.SNOW_ZONE)

sm.dispose()
