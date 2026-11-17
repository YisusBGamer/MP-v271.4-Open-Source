# Baldrix entry: the Path to Baldrix (3006880) and the baldrix_Enter portal in Tallahart: Phantasmal Night Sanctum (410013608).
# Official (maplestorywiki.net, GMS): Normal, Hard; level 290, 30 minutes, 1-3 players, shared Magic gauge.
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.enums import BossCooldown

REQ_LEVEL = BossConstants.BALDRIX_REQ_LEVEL
names = BossConstants.BALDRIX_MODE_NAMES

text = "#e<Boss: Baldrix>#n\r\nWill you face Baldrix?\r\n\r\n#b"
for i in range(len(names)):
    text += "#L%d#%s Mode (Level %d+)#l\r\n" % (i, names[i], REQ_LEVEL)
text += "#L9#Never mind.#l"
sel = sm.sendNext(text)
if 0 <= sel < len(names):
    cd = BossCooldown.Baldrix
    if sm.getParty() is None:
        sm.createSoloParty()

    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader enter if you wish to face Baldrix.")
    elif sm.getParty().getMembers().size() > 3:
        sm.sendSayOkay("Only up to 3 party members can enter.")
    elif sm.checkParty(cd, REQ_LEVEL):
        sm.setNextInstanceProperty(BossConstants.BALDRIX_MODE_KEY, sel)
        sm.warpInstanceIn(BossConstants.BALDRIX_FIELDS[sel][0], 0, True)
        sm.setInstanceTime(30 * 60, BossConstants.BALDRIX_LOBBY)
        sm.setBossCooldown(cd)
sm.dispose()
