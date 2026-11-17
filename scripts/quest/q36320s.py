# 36320 Another Maple Leaf (Sugar, Maple Tree Hill 110000): Sugar is brought to safety. Ends by itself.
sm.setSpeakerID(1541006)
sm.sendNext("You... came for me? I knew you would...")
sm.sendSay("I've always believed in you. Your roots are strong, just like the Maple Tree's. One day you'll grow into something great.")
if not sm.sendAskAccept("The #rBlack Shadow#k is coming for the Maple Tree... Please... we have to protect it..."):
    sm.dispose()
sm.startQuest(parentID)
sm.completeQuest(parentID)
sm.setPlayerAsSpeaker()
sm.sendSayOkay("#b(You carry Sugar to safety. It's time to take a stand against the Black Shadow.)#k")
sm.dispose()
