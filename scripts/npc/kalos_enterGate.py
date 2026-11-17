# Kalos the Guardian entry: the Gate (3005266) and the kalos_Enter portal in Karote: Summit (410005005).
# Official (maplestorywiki.net, GMS): Easy, Normal, Chaos, Extreme; level 265, 30 minutes, death count 8, one clear a week.
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.enums import BossCooldown

REQ_LEVEL = BossConstants.KALOS_REQ_LEVEL
names = BossConstants.KALOS_MODE_NAMES

text = "#e<Boss: Kalos the Guardian>#n\r\nThe Guardian watches over the Summit. Will you face him?\r\n\r\n#b"
for i in range(4):
    text += "#L%d#%s Mode (Level %d+)#l\r\n" % (i, names[i], REQ_LEVEL)
text += "#L9#Never mind.#l"
sel = sm.sendNext(text)
if 0 <= sel <= 3:
    cd = BossCooldown.Kalos
    if sm.getParty() is None:
        sm.createSoloParty()

    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader enter if you wish to face Kalos the Guardian.")
    elif sm.checkParty(cd, REQ_LEVEL):
        sm.setNextInstanceProperty(BossConstants.KALOS_MODE_KEY, sel)
        sm.warpInstanceIn(BossConstants.KALOS_P1_FIELDS[sel], 0, True)
        sm.setInstanceTime(30 * 60, BossConstants.KALOS_LOBBY)
        sm.setDeathCount(8)
        sm.setBossCooldown(cd)
sm.dispose()
