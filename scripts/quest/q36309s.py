# 36309 Olive, A Criminal? (Athena Pierce): Olive is found hiding in Ereve and says she was framed. Ends by itself.
sm.setSpeakerID(1012100)
sm.sendNext("Olive was seen heading for #bEreve#k, the island of the Cygnus Knights. If she really is the culprit, she has to be caught.")
if not sm.sendAskAccept("Will you go to Ereve and look for her? You know her better than anyone here."):
    sm.dispose()
sm.startQuest(parentID)
sm.setPlayerAsSpeaker()
sm.sendNext("#b(You search Ereve and find Olive hiding near a small bridge.)#k")
sm.setSpeakerID(1541003)
sm.sendSay("You?! Did Athena send you to catch me? I didn't steal anything, I swear! Someone made themselves look like me!")
sm.sendSay("If you don't believe me, go talk to #bNeinheart#k. He's the Cygnus Knights' tactician, and he knows about the one who did this.")
sm.completeQuest(parentID)
if sm.getFieldID() != 130000000 and sm.sendAskYesNo("#b(Go to Ereve now to meet Neinheart?)#k"):
    sm.warp(130000000)
sm.dispose()
