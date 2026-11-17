# Attacked Knights Chamber (993201069) -> Attacked Ereve (993201070), once Kidan has told what happened (36621).
if sm.hasQuestCompleted(36621):
    sm.warp(993201070, 1)
else:
    sm.chatScript("Talk to Kidan at the Crossroads first.")
sm.dispose()
