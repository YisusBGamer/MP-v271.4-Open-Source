# 36964 Making Red Jade - end (Maha). Takes the Red Jade; Aran 3rd job advancement 2110 -> 2111.
RED_JADE = 4037303

sm.setSpeakerID(1201002)
if not sm.hasItem(RED_JADE):
    sm.sendSayOkay("We need the #bRed Jade#k. Make it through the Mirror of Desire in the #bMirror Cave#k.")
    sm.dispose()
sm.sendNext("That's it, the Red Jade! I can feel my power in it. Hold still, I'll use it to wake up more of yours.")
sm.consumeItem(RED_JADE, 1)
if sm.questJobAdvance(parentID, 2110, 2111):
    sm.sendSayOkay("A little of the power you once had is back. Keep going, Aran!")
sm.dispose()
