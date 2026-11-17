# id 865020071 (Commerci Republic : Canal 1), field 865020071
sm.lockInGameUI(True, True)
sm.forcedInput(2)
sm.sendDelay(5000)
sm.forcedInput(0)
sm.setSpeakerType(3)
sm.setParam(17)
sm.sendNext("Hey, is that...?!")
sm.showFieldEffect("Map/EffectBT.img/dawnveil1/Cut4_1", 0)
sm.sendDelay(2000)
sm.showFieldEffect("Map/EffectBT.img/dawnveil1/Cut4_2", 0)
sm.sendDelay(4000)
sm.sendNext("What's going on here?!")
sm.setParam(5)
sm.setInnerOverrideSpeakerTemplateID(9390227) # Guild Assassin
sm.sendSay("A witness! Get rid of em.")
sm.lockInGameUI(False, True)
if not sm.hasQuest(17654) and not sm.hasQuestCompleted(17654):  # don't restart it (that would reset the kills)
    sm.startQuest(17654)


def spawn_line(mob, count, x1, x2, y):
    """spreads count monsters evenly between x1 and x2 on the ground at y"""
    for i in range(count):
        x = x1 + (x2 - x1) * (i + 1) // (count + 1)
        sm.spawnMob(mob, x, y, False)

# The assassins attack you for Screaming in the Night (17654: 4 + 4).
if sm.hasQuest(17654) and not sm.hasMobsInField(sm.getFieldID(), 9390833) and not sm.hasMobsInField(sm.getFieldID(), 9390834):
    spawn_line(9390833, 4, -500, 1900, 350)
    spawn_line(9390834, 4, -400, 2000, 350)
