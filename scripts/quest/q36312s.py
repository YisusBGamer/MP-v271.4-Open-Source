# 36312 The Suspicious Hat Guy (Athena Pierce): a man at the Verne Mine Entrance knows about the Black Wings. Ends by
# itself; then Rondo at the Mine Entrance (310040200) starts 36313.
sm.setSpeakerID(1012100)
sm.sendNext("Olive has come back to help clear her name. She tracked the thief's trail to Edelstein, where the #rBlack Wings#k are digging in the Verne Mine.")
sm.setSpeakerID(1541003)
sm.sendSay("There's a guy in a weird hat hanging around the #bMine Entrance#k. He talked like he knew things about the Black Wings. Let's make him talk!")
if not sm.sendAskAccept("You're coming with me, right? I'm not doing this alone!"):
    sm.dispose()
sm.startQuest(parentID)
sm.completeQuest(parentID)
if sm.sendAskYesNo("#b(Go to the Mine Entrance in Edelstein now?)#k"):
    sm.warp(310040200)
else:
    sm.chatScript("Find the man in the hat at the Verne Mine Entrance in Edelstein.")
sm.dispose()
