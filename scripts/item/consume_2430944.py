# id 2430944 (Specter Corps Letter), dropped by Specter soldiers during Know Your Enemy (31674).
# Each letter holds one of POOL pieces of information; record 31676 counts the different ones found (10 needed).
# Which pieces were found isn't stored: a letter is new with chance (POOL - found) / POOL, the same odds as tracking them.
QUEST = 31674
RECORD = 31676
GOAL = 10
POOL = 15
LETTER = 2430944

value = sm.getQRValue(RECORD)
found = int(value) if value.isdigit() else 0
if not sm.hasQuest(QUEST):
    sm.chat("The letter is written in a code you can't make sense of.")
elif found >= GOAL:
    sm.chat("You already have all the information you need. Report to Tiron.")
else:
    sm.consumeItem(LETTER)
    if sm.getRandomIntBelow(POOL) >= found:
        found += 1
        if not sm.hasQuest(RECORD):
            sm.startQuest(RECORD)
        sm.setQRValue(RECORD, str(found))
        sm.chat("You found a new piece of enemy information. (%d/%d)" % (found, GOAL))
    else:
        sm.chat("Nothing you didn't already know. (%d/%d)" % (found, GOAL))
sm.dispose()
