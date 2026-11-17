# Suspicious Sorcerer (Henesys, during Halloween): the Afterlands prologue (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400300
sm.setSpeakerID(NPC)


def me(text):
    # the player's own lines
    sm.setPlayerAsSpeaker()
    sm.sendNext(text)
    sm.setSpeakerID(NPC)


def done(q):
    return sm.hasQuestCompleted(q)


def has(q):
    return sm.hasQuest(q)


def fresh(q, *prev):
    # not started or done yet, and the quests before it are done
    if sm.hasQuest(q) or sm.hasQuestCompleted(q):
        return False
    for p in prev:
        if not sm.hasQuestCompleted(p):
            return False
    return True


def start(q):
    sm.startQuestNoCheck(q)


def finish(q):
    return A.finish(chr, q)


def ready(q):
    # a hunt or collect quest whose Quest.wz requirements are met
    return sm.hasQuest(q) and sm.isComplete(q)


def count(item):
    return sm.getQuantityOfItem(item)


ACTIVE = SeasonalEvents.isActive(SeasonalEvents.HALLOWEEN)


def casts():
    v = sm.getQRValue(63115, "number_casting")
    try:
        return int(v) if v else 0
    except ValueError:
        return 0

if not ACTIVE:
    sm.sendSayOkay("Hehehe... come back when the veil is thin. Around Halloween, perhaps.")
elif chr.getLevel() < 33:
    sm.sendSayOkay("Hehehe... a little young to be curious about the afterlife, aren't you? (Lv. 33)")
elif fresh(63112) or fresh(63113, 63112) or fresh(63114, 63113) or fresh(63115, 63114):
    me("(Chief Stan was talking about strange rumors: people who fell asleep and dreamed of a land where the dead "
       "live. Camila heard it from Pia, and Pia swears a sorcerer in Henesys knows the way.)")
    sm.sendNext("Hehehe... So the rumors brought you to me. Yes, yes, the Afterlands. A place where souls go when they "
                "aren't quite done with the world.")
    sm.sendNext("I can teach you to reach it... in your dreams, at least. It takes a #bBottle-o-Souls#k and a little "
                "#bincantation#k. Nothing dangerous. Mostly.")
    for q in (63112, 63113, 63114, 63115):
        if not done(q):
            start(q)
            finish(q)
    start(63116)
    sm.sendSayOkay("First, fill my bottle: defeat #r333 monsters near your level#k. The souls do the rest. Hehehe.")
elif has(63116):
    if ready(63116):
        sm.sendNext("Ooh, the bottle is singing! Wonderful souls, wonderful.")
        if finish(63116):
            sm.giveItem(4001832, 100)
            sm.sendSayOkay("Here, a little thanks. (#i4001832# #t4001832# x100)\r\nCome back tomorrow, the bottle is "
                           "always hungry.")
    else:
        sm.sendSayOkay("The bottle wants #r333 souls#k of monsters near your level. Patience, patience.")
elif fresh(63117, 63116):
    sm.sendNext("How do you get there? MAGIC! Hehehe. Well, a little magic, and a lot of practice.")
    start(63117)
    finish(63117)
    start(63118)
    sm.sendSayOkay("Say the incantation with me, once a day, for #b7 days#k. Then you'll understand.")
elif has(63118):
    n = casts()
    today = sm.getQRValue(63115, "cast_day")
    import time
    day = sm.getResetDay("yyyy-MM-dd")
    if n >= 7:
        sm.sendNext("Seven nights of chanting... You can feel it, can't you? The road at the end of all roads.")
        if finish(63118):
            sm.giveItem(4001832, 300)
            SeasonalEvents.giveCoins(chr, 5, "the Suspicious Sorcerer")
            sm.sendSayOkay("When the time comes, look for the #bOld Man#k near Chief Stan's house. He always needs "
                           "help. Hehehe... (Lv. 75)")
    elif today == day:
        sm.sendSayOkay("Once a day, once a day! Come back tomorrow. (%d/7)" % n)
    elif sm.sendAskYesNo("Ready to recite the incantation? (%d/7)" % n):
        sm.setQRValue(63115, "number_casting", str(n + 1))
        sm.setQRValue(63115, "cast_day", day)
        me("Ashes to ashes, dreams to dreams, the road goes further than it seems...")
        sm.sendSayOkay("Hehehe! Good. (%d/7)" % (n + 1))
elif done(63116) and done(63118) and sm.sendAskYesNo("The bottle is hungry again. Fill it once more? (333 monsters "
                                                     "near your level)"):
    start(63116)
else:
    sm.sendSayOkay("Hehehe... the Afterlands are closer than you think.")

sm.dispose()
