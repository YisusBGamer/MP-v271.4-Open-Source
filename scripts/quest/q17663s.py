# id 17663 ([Commerci Republic] Assassins's Den), field 865030101
sm.setSpeakerID(9390263) # Leon Daniella
sm.sendNext("They must be the gatekeepers.")
sm.setParam(2)
sm.sendSay("There are too many of them out there to just be guarding a gate. ")
sm.setParam(0)
res = sm.sendAskYesNo("Maybe the gate is made of gold... Whatever the case, we've gotta beat them up. This... is what I was born to do.")
sm.startQuest(parentID)
sm.sendNext("I'm taking you suckers down!")
sm.startQuest(parentID)


def spawn_line(mob, count, x1, x2, y):
    """spreads count monsters evenly between x1 and x2 on the ground at y"""
    for i in range(count):
        x = x1 + (x2 - x1) * (i + 1) // (count + 1)
        sm.spawnMob(mob, x, y, False)

# The gatekeepers (8 Masked Mysteriosos, the quest's kill count) appear.
if not sm.hasMobsInField(sm.getFieldID(), 9390834):
    spawn_line(9390834, 8, -950, 580, 90)
