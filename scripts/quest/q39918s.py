# [Cernium (After)] Library in Flames (39918)

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004516) # Carlisle
sm.sendNext("The enemy has reached #b#m410000800##k, and they're about to take over the city. Ask #bCarlisle#k what to do.")
if sm.questStartDialogue(parentID):
    offerWay(410000830, "#p3004517# is waiting there.")
sm.dispose()
