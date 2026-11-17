# 39575 - [Theme Dungeon] Ellinel Fairy Academy (Hoyoung's copy of 32136); opens Fairynappers (32101) like 32136 does
sm.setSpeakerID(1040002) # Fanzy
sm.sendNext("Are you the one I invited to help with the ruckus at the Ellinel Fairy Academy?")
sm.setPlayerAsSpeaker()
sm.sendNext("Um, of course?")
sm.setSpeakerID(1040002)
sm.sendNext("You don't look as strong as I'd hoped. But, you're famous, so I'll leave it to you.")
sm.completeQuest(parentID)
sm.createQuestWithQRValue(32101, "1")
