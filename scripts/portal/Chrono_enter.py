# Kai entry (Season boss): the gate in Nexus Tower Delta 3: Elevator No. 14 (9091057) and its Chrono_enter portal
# (993277350). Official (maplestorywiki.net, GMS; BossParty.img 36): Normal (level 270), Hard (280); solo only,
# 20 minutes, death count 5.
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.enums import BossCooldown

names = BossConstants.KAI_MODE_NAMES
levels = BossConstants.KAI_REQ_LEVEL

text = "#e<Season Boss: Kai>#n\r\nKai waits at the top of the tower. This fight is solo only.\r\n\r\n#b"
for i in range(len(names)):
    text += "#L%d#%s Mode (Level %d+)#l\r\n" % (i, names[i], levels[i])
text += "#L9#Never mind.#l"
sel = sm.sendNext(text)
if 0 <= sel < len(names):
    cd = BossCooldown.Kai
    if sm.getParty() is None:
        sm.createSoloParty()

    if sm.getParty().getMembers().size() > 1:
        sm.sendSayOkay("Kai can only be fought alone. Please leave your party first.")
    elif sm.checkParty(cd, levels[sel]):
        sm.setNextInstanceProperty(BossConstants.KAI_MODE_KEY, sel)
        sm.warpInstanceIn(BossConstants.KAI_FIELDS[sel][0], 0, True)
        sm.setInstanceTime(20 * 60, BossConstants.KAI_LOBBY)
        sm.setDeathCount(5)
        sm.setBossCooldown(cd)
sm.dispose()
