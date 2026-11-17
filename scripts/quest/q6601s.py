# Elven Blessing link skill quest

sm.getChr().addSkill(20021110, 1, 3)
sm.sendSayOkay("I've added your link skill, enjoy!.")
sm.startQuest(parentID) # start it first, or completing it does nothing
sm.completeQuest(6601)
sm.dispose()
