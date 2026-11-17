# [Cernium (Before)] Dawn and Dusk (39804)

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004403) # Old Man
sm.sendNext("#bEdea#k has asked you to gather information in #rCernium Square#k. Why don't you talk to the #bElderly Man#k over there first?")
if sm.questStartDialogue(parentID):
    offerWay(410000500, "#p3004404# is waiting there.")
sm.dispose()
