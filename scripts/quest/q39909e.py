# [Cernium (After)] Make it Happen (39909), handing it in

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004506) # Seren
if sm.questEndDialogue(parentID):
    sm.sendNext("You wrapped up your research on Seal Stones. When #rthree moons#k set, the owner of the holy sword shall appear. The first of the #rthree moons#k has already set, and the other two will both have set two days from now.")
    offerWay(410000690, "#p3004507# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet. Defeat the monsters first.")
sm.dispose()
