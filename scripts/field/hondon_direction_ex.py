# Kaling: the Fall scene before Hundun (the Four Seasons hub + 20 + 40 * season), as GMS v271: the
# screen goes dark, the spine plays with its sound, the boss guide pages, then the fight (+20).
from net.swordie.ms.ext import ServerHooks
fieldId = sm.getFieldID()
sm.lockInGameUI(True, False)
sm.blind(1, 255, 0, 0)
sm.invokeAfterDelay(820, "spineIntro", "intro", "Effect/Direction20.img/bossKaring/1phase_hondon_spine/skeleton", "animation")
sm.invokeAfterDelay(820, "playSound", "Sound/SoundEff.img/karing/hondon", 200)
sm.invokeAfterDelay(9390, "offSpineScreen", "intro", 100, "", 0)
if ServerHooks.isAutomated(sm.getChr()):
    sm.invokeAfterDelay(9510, "lockInGameUI", False)
    sm.invokeAfterDelay(9530, "warp", fieldId + 20, 0)
else:
    sm.warpLaterIfStillIn(69430, fieldId, fieldId + 20)  # backup if the client never answers the scene
    # as GMS: the boss guide pages (shown once per character by the client; it answers when they're closed)
    sm.sendDelay(9430)
    for key in ["karing_common_1", "karing_extreme_1"]:
        sm.sendBossGuide(key)
    sm.lockInGameUI(False)
    sm.warp(fieldId + 20, 0)
