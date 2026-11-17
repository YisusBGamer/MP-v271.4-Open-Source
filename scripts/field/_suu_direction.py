# Lotus's direction maps (Normal/Hard/Extreme base +0 / +200 / +400), as GMS v271. Set by the calling script:
# PHASE, BLACK_FRAME, HIDE_USER, STEPS = [(delay ms, [sounds, "ani" = the phase's entrance animation])].
# Each delay is one the client answers before the next step, so the scene plays out in full; at the end (or after the
# skip button) the player moves on to the phase map (+100).
from net.swordie.ms.ext import ServerHooks
from net.swordie.ms.connection.packet.field import FieldPacket, ScenePacket

ENTER_ANI = "Mob/BossPattern/BossEnterAni.img/suu/"
SKIP_TRANSITION = "UI/UIWindow8.img/DirectionSkip/effect/transition"
SKIP_END_MS = 1500
SAFETY_MS = 60000
fieldId = sm.getFieldID()
nextField = fieldId + 100
total = sum(d for d, _ in STEPS)


def enterAnimation():
    if PHASE == 1:
        sm.getChr().write(ScenePacket.illustrationShow("intro", ENTER_ANI + "1phase", False, 0, 0, 0, 0, False, 1, 255))
    else:
        name = str(PHASE) + "phase"
        sm.getChr().write(FieldPacket.bossEnterSkeleton(ENTER_ANI + name + "/skeleton.skel", name, 0, 0))


sm.lockInGameUI(True, BLACK_FRAME)
if HIDE_USER:
    sm.hideUser(True)
if PHASE > 1:
    sm.blind(1, 255, 0, 0)
if ServerHooks.isAutomated(sm.getChr()):
    sm.invokeAfterDelay(total, "lockInGameUI", False)
    sm.invokeAfterDelay(total + 20, "warp", nextField, 0)
else:
    sm.warpLaterIfStillIn(total + SAFETY_MS, fieldId, nextField)  # backup if the client never answers the scene
    for delay, actions in STEPS:
        sm.sendDelay(delay)
        if sm.isSceneSkipping():
            break
        for a in actions:
            if a == "ani":
                enterAnimation()
            else:
                sm.playSound("Sound/Etc.img/" + a, 100)
    if sm.isSceneSkipping():
        # as GMS: the skip's transition plays, then the move
        sm.getChr().write(FieldPacket.screenOverlay(True))
        sm.getChr().write(ScenePacket.illustrationShow("skip", SKIP_TRANSITION, False, 0, 0, 0, 100, True, 1, 255))
        sm.invokeAfterDelay(SKIP_END_MS, "lockInGameUI", False)
        sm.invokeAfterDelay(SKIP_END_MS + 20, "warp", nextField, 0)
    else:
        sm.lockInGameUI(False)
        sm.warp(nextField, 0)
