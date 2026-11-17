# Malefic Star entry: the Malefic Star Entrance Gate (3007150) and the ymir_Enter portal in Dark Sea: Temple of the
# Radiant Star (410007670). Official (maplestorywiki.net, GMS): Normal, Hard; level 280, 30 minutes, 1-3 players,
# death count 5.
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.enums import BossCooldown

REQ_LEVEL = BossConstants.MALEFIC_REQ_LEVEL
names = BossConstants.MALEFIC_MODE_NAMES

text = "#e<Boss: Malefic Star>#n\r\nWill you face Malefic Star?\r\n\r\n#b"
for i in range(len(names)):
    text += "#L%d#%s Mode (Level %d+)#l\r\n" % (i, names[i], REQ_LEVEL)
text += "#L9#Never mind.#l"
sel = sm.sendNext(text)
if 0 <= sel < len(names):
    cd = BossCooldown.MaleficStar
    if sm.getParty() is None:
        sm.createSoloParty()

    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader enter if you wish to face Malefic Star.")
    elif sm.getParty().getMembers().size() > 3:
        sm.sendSayOkay("Only up to 3 party members can enter.")
    elif sm.checkParty(cd, REQ_LEVEL):
        sm.setNextInstanceProperty(BossConstants.MALEFIC_MODE_KEY, sel)
        sm.warpInstanceIn(BossConstants.MALEFIC_FIELDS[sel][0], 0, True)
        sm.setInstanceTime(30 * 60, BossConstants.MALEFIC_LOBBY)
        sm.setDeathCount(5)
        sm.setBossCooldown(cd)
sm.dispose()
