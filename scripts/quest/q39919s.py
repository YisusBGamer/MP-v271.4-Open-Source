# [Cernium (After)] Library in Flames 2 (39919)

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004517) # Court Knight
sm.sendNext("Ask the #bCourt Knight#k in #b#m410000830##k about #bEdea#k's whereabouts.")
if sm.questStartDialogue(parentID):
    offerWay(410000870, "Defeat the monsters there.")
sm.dispose()
