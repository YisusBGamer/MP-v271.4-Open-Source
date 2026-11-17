# 36329 The Mysterious Voice Returns (after 36328). The voice wakes a new power; it finishes by itself and opens the
# 3rd job quests (1430 Chiefs of El Nath and the other classes' copies).
sm.setSpeakerID(1541063)
sm.sendNext("...Young one. You have grown strong. Can you hear me?")
sm.sendSay("The power sleeping inside you is ready to awaken further. Go to the chiefs of #bEl Nath#k. They will show "
           "you the way forward.")
if not sm.sendAskAccept("Accept this power?"):
    sm.dispose()
if not sm.hasQuest(parentID):
    sm.startQuest(parentID)
sm.setQRValue(parentID, "talk", "1")
sm.completeQuest(parentID)
sm.setPlayerAsSpeaker()
sm.sendSayOkay("#b(After listening to the mysterious voice, you've gained a new power. Your job instructor will tell "
               "you about your 3rd job.)#k")
sm.dispose()
