# [Cernium (After)] Templar's Enemy (39911)

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004509) # Checky
sm.sendNext("Talk to #bChecky#k #b#m410000770##k about the next step.")
if sm.questStartDialogue(parentID):
    sm.sendNext("#b(You make a ruckus near the Flora encampment, drawing the soldiers away so Seren can slip in and rescue Gilmore.)#k")
    sm.setQRValue(parentID, "clear", "1")
    if sm.questEndDialogue(parentID) and sm.questStartDialogue(39912):
        sm.sendNext("#b(With Checky, you hold off the army at the Flora encampment. You escape with Gilmore, but the emblem stays out of reach.)#k")
        sm.setQRValue(39912, "clear", "1")
        offerWay(410000770, "#p3004509# is waiting to hear how it went.")
sm.dispose()
