# First Adversary entry: Where Will Slumbers (3005545) and the adversary_enter portal in From Whence Will Flows (410007019).
# Official (maplestorywiki.net, GMS): Easy, Normal, Hard, Extreme; level 270, 30 minutes, 1-3 players, Adversarial Will.
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.enums import BossCooldown

REQ_LEVEL = BossConstants.ADVERSARY_REQ_LEVEL
names = BossConstants.ADVERSARY_MODE_NAMES
# GMS v271 waiting rooms (ptBossIn starts the fight; the fight gets its 30 minutes then)
WAITING = [410031500, 410031600, 410031700, 410031800]

text = "#e<Boss: First Adversary>#n\r\nWill you face First Adversary?\r\n\r\n#b"
for i in range(len(names)):
    text += "#L%d#%s Mode (Level %d+)#l\r\n" % (i, names[i], REQ_LEVEL)
text += "#L9#Never mind.#l"
sel = sm.sendNext(text)
if 0 <= sel < len(names):
    cd = BossCooldown.FirstAdversary
    if sm.getParty() is None:
        sm.createSoloParty()

    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader enter if you wish to face First Adversary.")
    elif sm.getParty().getMembers().size() > 3:
        sm.sendSayOkay("Only up to 3 party members can enter.")
    elif sm.checkParty(cd, REQ_LEVEL):
        sm.setNextInstanceProperty(BossConstants.ADVERSARY_MODE_KEY, sel)
        sm.warpInstanceIn(WAITING[sel], 0, True)
        sm.setInstanceTime(10 * 60, BossConstants.ADVERSARY_LOBBY)
        sm.setBossCooldown(cd)
sm.dispose()
