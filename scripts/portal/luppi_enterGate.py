# Jupiter entry: the Jupiter Entrance Gate (3007105) and the luppiter_Enter portal in Geardock: Forge Entrance
# (410013709). Official (maplestorywiki.net, GMS): Normal, Hard; level 295, 30 minutes, 1-3 players.
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.enums import BossCooldown

REQ_LEVEL = BossConstants.JUPITER_REQ_LEVEL
names = BossConstants.JUPITER_MODE_NAMES

text = "#e<Boss: Jupiter>#n\r\nWill you face Jupiter?\r\n\r\n#b"
for i in range(len(names)):
    text += "#L%d#%s Mode (Level %d+)#l\r\n" % (i, names[i], REQ_LEVEL)
text += "#L9#Never mind.#l"
sel = sm.sendNext(text)
if 0 <= sel < len(names):
    cd = BossCooldown.Jupiter
    if sm.getParty() is None:
        sm.createSoloParty()

    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader enter if you wish to face Jupiter.")
    elif sm.getParty().getMembers().size() > 3:
        sm.sendSayOkay("Only up to 3 party members can enter.")
    elif sm.checkParty(cd, REQ_LEVEL):
        sm.setNextInstanceProperty(BossConstants.JUPITER_MODE_KEY, sel)
        sm.warpInstanceIn(BossConstants.JUPITER_FIELDS[sel][0], 0, True)
        sm.setInstanceTime(30 * 60, BossConstants.JUPITER_LOBBY)
        # no death count: Disruption replaces it (JupiterInstanceHelper)
        sm.setBossCooldown(cd)
sm.dispose()
