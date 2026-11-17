# [Cernium (After)] The People's Faith (39914), handing it in

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004512) # Court Knight
if sm.questEndDialogue(parentID):
    sm.sendNext("You heard from #bThunderhammer#k that #bSeren#k went to #b#m410000760##k.")
    offerWay(410000900, "#p3004513# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet.")
sm.dispose()
