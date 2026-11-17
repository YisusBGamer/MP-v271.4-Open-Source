# Fruit Juice (2630360), Partem Ruins: Set Loose the Juice (35973) wants it sprinkled 10 times (its record counts up;
# the item isn't used up)
QUEST = 35973
if sm.hasQuest(QUEST):
    v = sm.getQRValue(QUEST)
    n = int(v) if v and v.isdigit() else 0
    if n < 10:
        n += 1
        sm.setQRValue(QUEST, str(n), False)
        sm.updateQRValue(QUEST, True)
        sm.chat("You sprinkled the fruit juice. (%d/10)" % n)
    else:
        sm.chat("That's enough juice. Report back.")
sm.dispose()
