# Limbo entry: the Gateway to Replicated Space (3006558) and the limbo_Enter portal in Carcion: Temple of Tears (410007601).
# Official (maplestorywiki.net, GMS): Normal, Hard; level 285, 30 minutes, 1-3 players, Erosion instead of a death count.
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.enums import BossCooldown

REQ_LEVEL = BossConstants.LIMBO_REQ_LEVEL
names = BossConstants.LIMBO_MODE_NAMES
# GMS v271 waiting rooms (ptBossIn starts the fight; the fight gets its 30 minutes then)
WAITING = [410031100, 410031200]

text = "#e<Boss: Limbo>#n\r\nWill you face Limbo?\r\n\r\n#b"
for i in range(len(names)):
    text += "#L%d#%s Mode (Level %d+)#l\r\n" % (i, names[i], REQ_LEVEL)
text += "#L9#Never mind.#l"
sel = sm.sendNext(text)
if 0 <= sel < len(names):
    cd = BossCooldown.Limbo
    if sm.getParty() is None:
        sm.createSoloParty()

    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader enter if you wish to face Limbo.")
    elif sm.getParty().getMembers().size() > 3:
        sm.sendSayOkay("Only up to 3 party members can enter.")
    elif sm.checkParty(cd, REQ_LEVEL):
        sm.setNextInstanceProperty(BossConstants.LIMBO_MODE_KEY, sel)
        sm.warpInstanceIn(WAITING[sel], 0, True)
        sm.setInstanceTime(10 * 60, BossConstants.LIMBO_LOBBY)
        sm.setBossCooldown(cd)
sm.dispose()
