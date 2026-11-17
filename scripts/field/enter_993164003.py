# Somewhere in Toolen City (993164003): where a new Kain starts (Lv 1). Asks SKIP or PLAY, then the opening.
# The opening's lines aren't in the game data; they follow the quest log of Just Match the Headcount (39401).
execfile("scripts/quest/_kain_tutorial.py")

if chr.getJob() == KAIN_BEGINNER and not sm.hasQuestCompleted(HEADCOUNT) and not skip_tutorial():
    sm.lockInGameUI(True, False)
    try:
        sm.changeBGM("Bgm56/TheDispossessed", 0, 0)
        sm.blind(1, 255, 0, 0, 0, 0)
        sm.playSound("Sound/SoundEff.img/kain/HeavyRain")
        sm.sendDelay(1500)
        me("(Rain again. It never stops in Toolen City.)")
        me("(My Malice is almost gone. A Drakas without Malice won't last long here.)")
        sm.blind(0, 0, 0, 0, 0, 1000)
        sm.sendDelay(1000)
    finally:
        sm.lockInGameUI(False, True)
    sm.warp(TOOLEN)
elif chr.getJob() == KAIN_BEGINNER:
    sm.warp(TOOLEN)
sm.dispose()
