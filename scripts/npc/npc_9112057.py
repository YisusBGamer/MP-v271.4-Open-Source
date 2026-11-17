# Foxwhisker Herb (9112057), Fox's Bluff. Fox-Foot Fiasco (58764): pick the herb to treat the Fox
# Yokai.
HERB = 4034874
if sm.hasQuest(58764) and not sm.hasItem(HERB):
    if sm.canHold(HERB):
        sm.giveItem(HERB)
        sm.sendSayOkay("(You carefully pick a #bFoxwhisker Herb#k.)")
    else:
        sm.sendSayOkay("(Make room in your Etc inventory first.)")
else:
    sm.sendSayOkay("(A patch of fragrant herbs sways in the wind.)")
sm.dispose()
