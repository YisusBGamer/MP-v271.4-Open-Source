# Maple Claws (Happyville) (Christmas in Happyville - the Missing Santa story, see ChristmasEvent)
NPC = 9201030
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


if can_start(X.SANTA_CAT) and sm.hasQuest(X.HANGING):
    if sm.sendAskAccept("Oh, my! This chimney is so D-I-R-T-Y! If I were Santa, I would never enter that house! But it seems like very nice children are living in that house. It'd be sad if they didn't receive any Christmas gifts... ALRIGHT! I guess I'll clean the chimney for them. Oh wait! I don't have the cleaning tools. I just need a #bChimney Sweep and a Flashlight#k. Perhaps, you'd be kind enough to bring me those?"):
        sm.startQuestNoCheck(X.SANTA_CAT)
        sm.sendSayOkay('Great! Thank you so much! What a fine Mapler you are! You should be able to get the #bChimney Sweep#k and the #bFlashlight#k from #bRina in Henesys#k. I have something else to do in this town, so please get me those two cleaning tools.')
    else:
        sm.sendSayOkay("You refuse? Hmm, okay. I'll find somebody else who can help me. If you change your mind, please come talk to me again. After all, the children in this house deserve to have Christmas presents.")
elif sm.hasQuest(X.SANTA_CAT):
    sm.sendSayOkay("You haven't met #b#p1010100##k yet. I want to get started soon before Santa Claus comes to town.")
elif sm.hasQuest(X.CHIMNEY):
    if sm.hasItem(X.CHIMNEY_BROOM) and sm.hasItem(X.FLASHLIGHT):
        sm.sendNext("That's great! You got the Chimney Sweep and the Flashlight! Big thanks! I would love to show you my appreciation for doing this, so hold on!")
        if finish(X.CHIMNEY):
            sm.sendSayOkay('Here, take this recommendation with you! Something nice will happen!' + "\r\n\r\n#b(You got a Mapler Recommendation.)#k")
    else:
        sm.sendSayOkay("You haven't met #b#p9201030##k yet. I want to get started soon before Santa Claus comes to town.")
else:
    sm.sendSayOkay("Merry Christmas! It's going to be a very happy Christmas!")

sm.dispose()
