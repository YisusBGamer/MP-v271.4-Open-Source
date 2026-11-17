# Limbo's intro (Normal 410009000, Hard 410011000), as GMS v271: the screen goes dark, the entrance
# skeleton plays with its four sounds, the boss guide pages, then phase 1 (+100).
from net.swordie.ms.ext import ServerHooks
fieldId = sm.getFieldID()
sm.lockInGameUI(True, False)
sm.blind(1, 255, 0, 0)
MODE = "hard" if fieldId == 410011000 else "normal"
sm.invokeAfterDelay(550, "bossEnterSkeleton", "Mob/BossPattern/BossEnterAni.img/limbo/1phase/skeleton.skel", "animation")
sm.invokeAfterDelay(950, "playSound", "Sound/Etc.img/Limbo1PhaseEnterEff", 100)
sm.invokeAfterDelay(2050, "playSound", "Sound/Etc.img/Limbo1PhaseEnter0", 100)
sm.invokeAfterDelay(7120, "playSound", "Sound/Etc.img/Limbo1PhaseEnter1", 100)
sm.invokeAfterDelay(10460, "playSound", "Sound/Etc.img/Limbo1PhaseEnter2", 100)
if ServerHooks.isAutomated(sm.getChr()):
    sm.invokeAfterDelay(15850, "lockInGameUI", False)
    sm.invokeAfterDelay(15870, "warp", fieldId + 100, 0)
else:
    sm.warpLaterIfStillIn(75550, fieldId, fieldId + 100)  # backup if the client never answers the scene
    # as GMS: the boss guide pages (shown once per character by the client; it answers when they're closed)
    sm.sendDelay(15550)
    for key in ["limbo_common_1", "limbo_" + MODE + "_1"]:
        sm.sendBossGuide(key)
    sm.lockInGameUI(False)
    sm.warp(fieldId + 100, 0)
