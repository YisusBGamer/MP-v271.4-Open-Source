# 36315 Olive's Special Training - end (self-complete; field/enter_993166100.py usually finishes it)
sm.setSpeakerID(1541003)
if sm.completeQuest(parentID):
    sm.sendSayOkay("Hmph. Not bad... for you. D-don't get the wrong idea! I just didn't want you holding me back.")
else:
    sm.sendSayOkay("You haven't beaten the Blue Mushmom yet! Forfeit the quest and accept it again to go back.")
sm.dispose()
