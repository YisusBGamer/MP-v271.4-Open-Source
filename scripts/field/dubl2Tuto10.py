# Training Center (103050910), Dual Blade tutorial: after the Garden's scene the UI may still be locked
sm.lockInGameUI(False)
if sm.getChr().getJob() == 0 and not sm.hasQuestCompleted(2603):
    sm.progressMessageFont(3, 20, 20, 0, "Talk to Shiba for your training. The doors lead to the Training Spots.")
sm.dispose()
