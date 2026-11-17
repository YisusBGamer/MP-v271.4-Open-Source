# id 17679 ([Commerci Republic] Red Tide), field 865010011 The Scarlet Terror
# Captain Blood (9390246) and his crew: Captain Blood the Pirate King (9390817), 10 Blood Pirate Lookouts (9390816) and
# 10 Blood Pirate Swabbies (9390815) to defeat (field/summon_pu places them on later visits).
sm.setSpeakerID(9390246) # Captain Blood
sm.setParam(56)
sm.setColor(1)
sm.sendNext("Your pirating days are over!")
sm.setParam(32)
sm.sendSay("Hahaha! Ye've walked into th' belly a' th' beast and ye don't even know it! ")
sm.setParam(56)
sm.sendSay("That's what you think, scurvy-face! Thanks for bringing your guys along so I can mop the floor with them all at once. ")
sm.setParam(32)
sm.sendSay("Puff up ye chest, wee seagull. I'll gift ye a right whippin' ye'll never forget!")
sm.setParam(56)
if sm.sendAskYesNo("(This guy... I'm going to swab his decks!)"):
    sm.startQuest(parentID)
    sm.createQuestWithQRValue(18418, "B=33273")
    if sm.hasQuest(parentID) and not sm.hasMobsInField(sm.getFieldID(), 9390817):
        pos = chr.getPosition()
        sm.removeNpc(9390246)
        sm.spawnMob(9390817, pos.getX() + 300, pos.getY(), False)
        for i in range(10):
            sm.spawnMob(9390816, pos.getX() - 400 + i * 90, pos.getY(), False)
            sm.spawnMob(9390815, pos.getX() - 350 + i * 90, pos.getY(), False)
sm.dispose()
