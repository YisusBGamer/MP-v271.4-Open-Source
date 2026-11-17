# Kaling entry: the Gate to the Lost Light of Four Seasons (3006193) and the karing_Enter portal in
# Shangri-La: Four Seasons Pavilion (410007025).
# Official (maplestorywiki.net, GMS): Easy, Normal, Hard, Extreme; level 275, 30 minutes, 1000 shared Willpower.
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.enums import BossCooldown

REQ_LEVEL = BossConstants.KALING_REQ_LEVEL
names = BossConstants.KALING_MODE_NAMES
# GMS v271 waiting rooms (ptBossIn starts the fight; the fight gets its 30 minutes then)
WAITING = [410030700, 410030800, 410030900, 410031000]

text = "#e<Boss: Kaling>#n\r\nThe Lost Light of Four Seasons lies beyond the gate. Will you face Kaling?\r\n\r\n#b"
for i in range(4):
    text += "#L%d#%s Mode (Level %d+)#l\r\n" % (i, names[i], REQ_LEVEL)
text += "#L9#Never mind.#l"
sel = sm.sendNext(text)
if 0 <= sel <= 3:
    cd = BossCooldown.Kaling
    if sm.getParty() is None:
        sm.createSoloParty()

    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader enter if you wish to face Kaling.")
    elif sm.checkParty(cd, REQ_LEVEL):
        sm.setNextInstanceProperty(BossConstants.KALING_MODE_KEY, sel)
        sm.warpInstanceIn(WAITING[sel], 0, True)
        sm.setInstanceTime(10 * 60, BossConstants.KALING_LOBBY)
        sm.setBossCooldown(cd)
sm.dispose()
