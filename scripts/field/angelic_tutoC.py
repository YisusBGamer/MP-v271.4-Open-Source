# id 940012020 (Hidden Street : Dress-Up), field 940012020
sm.lockInGameUI(True, False)
sm.lockInGameUI(False, True)
# the skills the tutorial's skip gave (the same ones for players who play it)
for skillID in (60011222, 60011216, 60011218, 60011220, 60010217):
    sm.giveSkill(skillID)
sm.warp(400000000)
