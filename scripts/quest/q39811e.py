# [Cernium (Before)] Ancient Ghosts (39811), handing it in

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004415) # Edea
if sm.questEndDialogue(parentID):
    sm.sendNext("You defeated the #r#o8645127##k monsters in #b#m410000700##k and found the ancient tome. While learning about the origins of the Seal Stone, #bSeren#k visited the library.")
    offerWay(410000690, "#p3004415# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet. Defeat the monsters first.")
sm.dispose()
