# 450004100 / 450004400 (Lachelein : Nightmare Clocktower): Lucid's entrance scene, then her Dreaming Forest
# (Normal 450004150, Hard 450004450). Each difficulty stays in its own map set.
from net.swordie.ms.constants import BossConstants

if sm.getFieldID() == BossConstants.LUCID_EASY_FIELD:
    # The Easy instance uses this entry field too, but its cutscene can leave the client waiting for a direction
    # response indefinitely. Clear any transition and send it directly to Easy phase one.
    sm.blind(False, 0, 0, 0, 0, 1000)
    sm.lockInGameUI(False, True)
    # a moment after arriving, like the other difficulties: a warp from inside the entry script itself was once lost
    sm.invokeAfterDelay(1500, "warp", sm.getFieldID() + BossConstants.lucidStep(sm.getFieldID()))
    if hasattr(sm, "warpLaterIfStillIn"):
        sm.warpLaterIfStillIn(5000, sm.getFieldID(), sm.getFieldID() + BossConstants.lucidStep(sm.getFieldID()))  # backup: the delayed warp above was lost once
    if hasattr(sm, "warpFieldLaterIfStillIn"):
        sm.warpFieldLaterIfStillIn(9000, sm.getFieldID() + BossConstants.lucidStep(sm.getFieldID()))  # whoever is still here (a run started alongside another lost both warps above)
else:
    # sendDelay waits for a direction answer the client never sends, so the server times the scene (sceneWait): input
    # locked and UI hidden with the skip button, and the players are warped on at its end or when it is skipped.
    sm.lockInGameUI(True, False)
    sm.spineScreen(True, False, True, 0, "Map/Effect3.img/BossLucid/Lucid/lusi","animation","")
    sm.playSound("Sound/SoundEff.img/ArcaneRiver/lucid_spine", 200)
    sm.sceneWait(9000)
    sm.lockInGameUI(False, True)
    sm.warp(sm.getFieldID() + 50)
    if hasattr(sm, "warpLaterIfStillIn"):
        sm.warpLaterIfStillIn(13000, sm.getFieldID(), sm.getFieldID() + 50)  # backup if the delayed warp is lost
    if hasattr(sm, "warpFieldLaterIfStillIn"):
        sm.warpFieldLaterIfStillIn(18000, sm.getFieldID() + 50)  # whoever is still here
