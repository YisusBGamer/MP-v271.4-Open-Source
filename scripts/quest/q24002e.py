# Philius's Request. Official lines (Quest.wz Say.img).

PHILIUS = 1033202

sm.setSpeakerID(PHILIUS)
sm.sendNext("Maybe we're the lucky ones. "
            "While we sleep, Maple World will heal from the terrible things the Black Mage has done. "
            "I wonder what kind of world we'll wake up to?")
sm.sendSay("If you think about it like that, the curse is almost like a gift. Your Highness... Please, have sweet dreams.")

sm.completeQuest(parentID)
sm.startQuest(24005) # Cursed Slumber
sm.dispose()
