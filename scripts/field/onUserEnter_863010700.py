# id 863010700 (Tynerum : Gollux: Heart), field 863010700
from net.swordie.ms.constants import BossConstants

# Spawn reward
# only inside the boss instance (a visit without one, e.g. the script probe, has nothing to set up)
if sm.getChr() is not None and sm.getChr().getInstance() is not None:
    difficulty = 4
    if sm.getChr().getInstance().getField(863010240).hasProperty(BossConstants.GOLLUX_FINISHED_KEY):
        difficulty -= 1
    if sm.getChr().getInstance().getField(863010430).hasProperty(BossConstants.GOLLUX_FINISHED_KEY):
        difficulty -= 1
    if sm.getChr().getInstance().getField(863010330).hasProperty(BossConstants.GOLLUX_FINISHED_KEY):
        difficulty -= 1

    golluxRewardMob = 9601458 + difficulty

    # only once per run: a party member entering after the Heart died must not spawn a second one
    if not sm.hasMobsInField() and not field.hasProperty("golluxRewardSpawned"):
        field.setProperty("golluxRewardSpawned", True)
        sm.spawnMob(golluxRewardMob, 93, 74)

    # RP stuff
    sm.lockInGameUI(False, True)
    sm.showFieldEffect("giantBoss/enter/70", 0)

    sm.setSpeakerType(3)
    sm.setParam(4)
    sm.setInnerOverrideSpeakerTemplateID(9390124) # Heart Tree Guardian
    sm.sendSayOkay("Enter Gollux's heart and pacify it. You must hurry. The window for success is closing.")

