# Chosen Seren entry (@menu > Bosses > Boss entrances, or the Gate in Cernium's Palace Main Hall)
# Official (maplestorywiki.net, GMS): Normal, Hard and Extreme, level 260, 30 minutes, death count 8. Each mode has
# its own maps (BossConstants.SEREN_FIELD_BASE).
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.enums import BossCooldown

REQ_LEVEL = 260
# GMS v271 waiting rooms (ptBossIn starts the fight; the fight gets its 30 minutes then)
WAITING = [410030000, 410030100, 410030200]
COOLDOWNS = [BossCooldown.Seren, BossCooldown.SerenHard, BossCooldown.SerenExtreme]

text = "#e<Boss: Chosen Seren>#n\r\nThe Chosen of the Sun waits in the Palace Main Hall. Will you face her?\r\n\r\n#b"
for i in range(len(COOLDOWNS)):
    text += "#L%d#%s Mode (Level %d+)#l\r\n" % (i, BossConstants.SEREN_MODE_NAMES[i], REQ_LEVEL)
sel = sm.sendNext(text + "#L9#Never mind.#l")
if 0 <= sel < len(COOLDOWNS):
    cd = COOLDOWNS[sel]
    if sm.getParty() is None:
        sm.createSoloParty()

    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader enter if you wish to face Chosen Seren.")
    elif sm.checkParty(cd, REQ_LEVEL):
        sm.warpInstanceIn(WAITING[sel], 0, True)
        sm.setInstanceTime(10 * 60, BossConstants.SEREN_ENTRANCE)
        sm.setDeathCount(8)
        sm.setBossCooldown(cd)
sm.dispose()
