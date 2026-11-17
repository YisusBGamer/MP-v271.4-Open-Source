# 36635 Mad Dragons Part 3 - end (Neinheart). Mihile 3rd job advancement 5110 -> 5111.
SCALE = 4036873

sm.setSpeakerID(1101002)
sm.sendNext("A purple scale, from one of the Drakes? I'll have it investigated at once. Something drove them out "
            "of Sleepywood.")
if sm.hasItem(SCALE):
    sm.consumeItem(SCALE, 1)
if sm.questJobAdvance(parentID, 5110, 5111):
    sm.sendSayOkay("You handled the mission like a true knight. You have advanced to #bMihile (3rd job)#k.")
sm.dispose()
