# [Cernium (After)] Templar's Enemy (39911), handing it in

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004527) # Checky
if sm.questEndDialogue(parentID):
    sm.sendNext("You distracted the Flora army, so that Seren could sneak in to rescue Gilmore.")
else:
    sm.sendSayOkay("You haven't finished yet.")
sm.dispose()
