# 36310 About the Master of Disguise (Neinheart): gives Neinheart's Letter for Athena Pierce.
LETTER = 4033888
sm.setSpeakerID(1101002)
sm.sendNext("Olive sent you? Then listen. Not long ago a thief known as the #rMaster of Disguise#k attacked Ereve, wearing the face of one of our own knights.")
sm.sendSay("He can take on anyone's appearance. I'd wager it was him, not Olive, who robbed the Bowman Instructional School.")
if not sm.sendAskAccept("I'll write down what we know for Athena Pierce. Will you deliver my letter to her?"):
    sm.dispose()
if not sm.hasItem(LETTER):
    if not sm.canHold(LETTER):
        sm.sendSayOkay("Make room in your Etc inventory first.")
        sm.dispose()
    sm.giveItem(LETTER)
sm.startQuest(parentID)
if sm.sendAskYesNo("Shall I have you taken back to the #bBowman Instructional School#k right away?"):
    sm.warp(100000201)
sm.dispose()
