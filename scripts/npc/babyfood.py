# Pam (2081004) | Pam's House
# Raising Mimio (20528) sends Cygnus Knights here for the special supplements Leafre's dragons eat: 3 Concentrated
# Formula: Step 1 (4032196), which nothing else gives.
FORMULA = 4032196
NEEDED = 3

sm.setSpeakerID(2081004)
have = sm.getQuantityOfItem(FORMULA)
if sm.hasQuest(20528) and have < NEEDED:
    sm.sendNext("Oh, a Mimiana! Leafre's baby dragons grow up on my #bconcentrated formula#k. A Mimiana should love it too.")
    if sm.canHold(FORMULA, NEEDED - have):
        sm.giveItem(FORMULA, NEEDED - have)
        sm.sendSayOkay("Here you go: #b%d #t%d#s#k. Feed them to your Mimiana and watch it grow!" % (NEEDED - have, FORMULA))
    else:
        sm.sendSayOkay("Make some room in your Etc inventory first, and I'll give you the formula.")
else:
    sm.sendSayOkay("Hmmm... baby formula? Don't you think you're past that age?")
sm.dispose()
