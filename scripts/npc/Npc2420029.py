# 2420029 Karcasa (Scorpion Hunt Contest Area) - Respite (40109): hands out the Scorpion Catch Ropes for the contest.
# Each rope catches one Ariant Scorpion (the capture-item handler, ItemHandler); 10 scorpions win.
ROPE, SCORPION = 2270029, 4033816
sm.setSpeakerID(2420029)
if sm.hasQuest(40109):
    need = 10 - sm.getQuantityOfItem(SCORPION) - sm.getQuantityOfItem(ROPE)
    if sm.getQuantityOfItem(SCORPION) >= 10:
        sm.sendSayOkay("Ten scorpions! That's a win. Head back to Ariant.")
    elif need > 0:
        sm.giveItem(ROPE, need)
        sm.sendSayOkay("Here are your #b#t2270029#s#k. Throw one at a #bscorpion#k to catch it. "
                       "Catch #b10#k to win!")
    else:
        sm.sendSayOkay("You have enough ropes. Go catch those scorpions!")
else:
    sm.sendSayOkay("The scorpion-catching contest is the pride of Ariant!")
sm.dispose()
