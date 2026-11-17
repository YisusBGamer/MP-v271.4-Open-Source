# Mysterious Statue (1013500) | Ramuramu Valley : Guardian Angel Slime Altar (160000000)
# Guardian Angel Slime (GMS v271): waiting room 160080000 (10 min), then the intro and the fight (30 min), death count 5.
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.enums import BossCooldown
from net.swordie.ms.world.field.instance.instancehelper.boss import GuardianAngelSlimeInstanceHelper as GAS

MODES = [
    # name, chaos, cooldown
    ("Normal", False, BossCooldown.GuardianAngelSlime),
    ("Chaos", True, BossCooldown.GuardianAngelSlimeChaos),
]
REQ_LEVEL = 210

text = "#e<Boss: Guardian Angel Slime>#n\r\nThe statue hums with a strange power. Will you face the #rGuardian Angel Slime#k?\r\n\r\n#b"
for i in range(len(MODES)):
    text += "#L%d#%s Mode (Level %d+)#l\r\n" % (i, MODES[i][0], REQ_LEVEL)
text += "#L9#Never mind.#l"
sel = sm.sendNext(text)

if 0 <= sel < len(MODES):
    name, chaos, cd = MODES[sel]
    if sm.getParty() is None:
        sm.createSoloParty()

    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader enter if you wish to face the Guardian Angel Slime.")
    elif sm.checkParty(cd, REQ_LEVEL):
        sm.warpInstanceIn(GAS.WAITING_ROOM, 0, True)
        if chaos and chr.getInstance() is not None:
            chr.getInstance().addProperty(GAS.CHAOS_KEY, True)
        sm.setInstanceTime(10 * 60, BossConstants.GUARDIAN_ANGEL_SLIME_ALTAR)
        sm.setDeathCount(5)
        sm.setBossCooldown(cd)
sm.dispose()
