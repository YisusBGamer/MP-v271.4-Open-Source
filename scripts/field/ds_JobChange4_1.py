# 927000100 | demon slayer 4th job adv
sm.removeEscapeButton()
sm.setPlayerAsSpeaker()
sm.spawnNpc(2159335, 377, 69)
sm.sendNext("#b(I remember the feeling of unleashing my Fury... But I can't hold onto it for long...)")
# True Awakening (23219, the Demon Slayer 4th job quest) counts this Past Self (9001042)
sm.spawnMob(9001042 if sm.hasQuest(23219) else 9001041, 377, 69, False)
sm.removeNpc(2159335)
