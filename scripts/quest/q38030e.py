sm.setPlayerAsSpeaker()
sm.flipSpeaker()
sm.sendNext("This is Maple World? Did I really... return? I never thought I'd see it again.")
sm.sendSay("I bet everyone is doing well. I'm off to see my friends!")

if chr.getJob() == 2500 and chr.getLevel() >= 30:
    sm.jobAdvance(2510)  # 2nd job (below 30: on reaching 30, see Shade.handleLevelUp)
sm.startQuest(parentID)
sm.completeQuest(parentID)
