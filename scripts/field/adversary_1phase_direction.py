# First Adversary's intro (Easy 410020000, Normal 410021000, Hard 410022000, Extreme 410023000), as GMS v271:
# the screen goes dark, its entrance animation plays with its sound, the boss guide pages, then phase 1 (+100).
from net.swordie.ms.ext import ServerHooks
fieldId = sm.getFieldID()
sm.lockInGameUI(True, False)
sm.blind(1, 255, 0, 0)
MODE = ["easy", "normal", "hard", "extreme"][max(0, min(3, (fieldId - 410020000) // 1000))]
sm.invokeAfterDelay(670, "bossEnterAnimation", "Mob/BossPattern/BossEnterAni.img/firstAdversary/1phase/enter")
sm.invokeAfterDelay(1030, "playSound", "Sound/Etc.img/FirstAdversary1PhaseEnterEff", 100)
if ServerHooks.isAutomated(sm.getChr()):
    sm.invokeAfterDelay(11710, "lockInGameUI", False)
    sm.invokeAfterDelay(11730, "warp", fieldId + 100, 0)
else:
    sm.warpLaterIfStillIn(71600, fieldId, fieldId + 100)  # backup if the client never answers the scene
    # as GMS: the boss guide pages (shown once per character by the client; it answers when they're closed)
    sm.sendDelay(11600)
    for key in ["firstAdversary_common_1", "firstAdversary_" + MODE + "_1"]:
        sm.sendBossGuide(key)
    sm.lockInGameUI(False)
    sm.warp(fieldId + 100, 0)
