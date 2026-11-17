# Beodog (Land of Warriors) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400210
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


def judgment_ready():
    return ready(63035) and count(A.WARRIOR_TOKEN) >= 30

if fresh(63027, 63026):
    sm.sendNext("HYAAAH! ...Huh? You didn't dodge. You didn't even flinch!")
    me("You just stabbed me!")
    sm.sendNext("And you're fine! See? Nobody can die here, we're already dead. Ha! I'm #bBeodog#k, and this is the "
                "#bLand of Warriors#k. We fight all day. It's the best!")
    start(63027)
    finish(63027)
    start(63028)
    sm.setQRValue(63028, "start", "1")
    sm.sendSayOkay("And at night, we party! It's been day for ages, though. Turn the #bSolar Pulley#k in the middle "
                   "of town and make it night, would you?")
elif has(63028):
    if A.isNight(chr):
        sm.setQRValue(63028, "end", "1")
        sm.sendNext("NIGHT! PARTY! Grab a drink, newcomer!")
        finish(63028)
        start(63029)
        sm.setQRValue(63029, "start", "1")
        sm.sendSayOkay("Go say hello to #bVulcanelli#k. Nobody parties like Vulcanelli.")
    else:
        sm.sendSayOkay("Still day... Turn the #bSolar Pulley#k in the middle of town, will you?")
elif has(63029):
    sm.sendSayOkay("Vulcanelli's right over there. Can't miss him, he's the loud one.")
elif fresh(63030, 63029, 63028):
    sm.sendNext("Morning comes, we fight. Night comes, we party. Morning comes, we fight again. Been like that "
                "forever.")
    me("Doesn't anyone ever win?")
    sm.sendNext("Win? ...Huh. Never thought about it. Ask #bReubenhood#k, he thinks too much.")
    start(63030)
elif fresh(63034, 63030):
    sm.sendNext("It's #rJudgment Day#k! Once in a while we hold a contest to find the greatest warrior of them all. "
                "Want in?")
    if sm.sendAskAccept("Defeat #r25 Mushroom Marauders#k and #r25 Battle Balrogs#k in the fields, and bring me #b30 "
                        "Warrior's Tokens#k. The winner gets a prize nobody has ever been able to use!"):
        for q in (63034, 63033, 63035, 63036):
            start(q)
        sm.setQRValue(63034, "start", "1")
        sm.sendSayOkay("The fields are past the portal on the right. Go, go, go!")
elif has(63034):
    if judgment_ready():
        sm.sendNext("Twenty-five and twenty-five! And thirty tokens! You're the #bGreatest Warrior Ever#k!")
        for q in (63035, 63036, 63033, 63034):
            finish(q)
        sm.setQRValue(63091, "BestWar", "1")
        sm.sendNext("The prize! A wandering #bYellow Mage#k left it here ages ago. Said only a real champion could "
                    "carry it home.")
        A.giveKey(chr, A.KEYS[7])
        sm.sendSayOkay("A key! Ha! Now get back out there, champion.")
    else:
        sm.sendSayOkay("25 Mushroom Marauders, 25 Battle Balrogs and 30 Warrior's Tokens. I'm counting!")
elif fresh(63059, 63054):
    sm.sendNext("You're back! Ugh... we're all so tired. We've been fighting since you left. Nobody's had a night "
                "off.")
    start(63059)
    sm.setQRValue(63059, "start", "1")
    sm.sendSayOkay("Turn the #bSolar Pulley#k and make it night, please. We need a rest.")
elif has(63059):
    if A.isNight(chr):
        sm.setQRValue(A.WORLD_QR, "DnN", "1")
        finish(63059)
        start(63060)
        sm.sendSayOkay("Ahhh, night. But if we all sleep, who'll keep the monsters off the fields? ...You will! Take "
                       "out #r50 Mushroom Marauders#k and #r50 Battle Balrogs#k while we rest.")
    else:
        sm.sendSayOkay("It's still day! The #bSolar Pulley#k, please...")
elif has(63060):
    if ready(63060):
        finish(63060)
        sm.sendSayOkay("You did all that alone? While we slept? ...Arsan Lupen wants a word with you.")
    else:
        sm.sendSayOkay("I THINK I'd NOTICE if you already took out #r50 Mushroom Marauders#k and #r50 Battle Balrogs#k, "
                       "yeah.")
elif has(63062):
    if not A.isNight(chr):
        sm.sendNext("Morning! You turned the pulley yourself. Now, the challenge: hit me. As hard as you can.")
        me("(You strike. Beodog's blade meets yours... and snaps in two.)")
        sm.setQRValue(63062, "End", "1")
        finish(63062)
        sm.sendSayOkay("...My blade. My beautiful blade. You really are the heart of a warrior.")
    else:
        sm.sendSayOkay("Change the night back into day with the #bSolar Pulley#k, then we'll see what you've got.")
elif fresh(63064, 63062):
    if not A.isNight(chr):
        sm.sendSayOkay("I'll fix my blade tonight. Come back when it's dark. (#bSolar Pulley#k)")
    else:
        sm.sendNext("Battle Balrogs wear the toughest armor in the Afterlands. Melt it down and I'll have a new blade "
                    "by morning.")
        start(63064)
        sm.sendSayOkay("Beodog will need AT LEAST #b30 Battle Balrog's Armor#k to fix his blade!")
elif has(63064):
    if count(A.BALROG_ARMOR) >= 30:
        finish(63064)
        SeasonalEvents.giveCoins(chr, 3, "Beodog's new blade")
        sm.sendSayOkay("Perfect! A new blade, sharper than ever. Want me to stab you with it? ...No? Suit yourself.")
    else:
        sm.sendSayOkay("Beodog will need AT LEAST 30 #b#t4036014##k items to fix his blade, YEEYUH!")
else:
    sm.sendSayOkay("Fight by day, party by night! Best afterlife ever!")

sm.dispose()
