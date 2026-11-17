# 53245 Defeat the Guards! - end at Burke: one guard got away with the master key (53247)
BURKE = 9270083

sm.setSpeakerID(BURKE)
sm.removeEscapeButton()
sm.sendNext("That's all of them here... but one got away. The Key Keeper ran to the control room with the master key.")
sm.sendSay("We can't launch the ship without that key. Get it back, Jett!")
sm.completeQuest(parentID)
sm.startQuestNoCheck(53246)
sm.completeQuestNoRewards(53246)
sm.startQuestNoCheck(53247)
sm.dispose()
