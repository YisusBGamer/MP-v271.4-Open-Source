# [Hotel Arcus] In the Middle of the Desert (38101)
# Nothing in the game data leads into Hotel Arcus from outside: Neinheart offers the way, as the Cernium story does

def offerWay(target, why):
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(1101002) # Neinheart
if sm.questStartDialogue(parentID):
    offerWay(410003000, "A hotel stands alone in the middle of the desert past Cernium. The people there need help.")
sm.dispose()
