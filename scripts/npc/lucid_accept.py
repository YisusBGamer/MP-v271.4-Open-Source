# 3003208 Protective Mask (Nightmare Clocktower, 450004000): Boss Lucid entry.
# Three difficulties, each in its own map set, as on official servers:
#   Easy 450003800-450003960 (two phases at half of Normal's health, its own Final Music Box),
#   Normal 450004100-450004300 (two phases, Final Music Box), Hard 450004400-450004600 (three phases, the last one
#   45 seconds long; Hard Final Music Box). Party (or solo) instance, 30 minutes, 10 deaths.
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.enums import BossCooldown

MODES = [
    # name, min level, entry map, cooldown
    ("Easy", 220, BossConstants.LUCID_EASY_FIELD, BossCooldown.LucidEasy),
    ("Normal", 220, BossConstants.LUCID_FIELD, BossCooldown.Lucid),
    ("Hard", 220, BossConstants.LUCID_HARD_FIELD, BossCooldown.LucidHard),
]

# GMS v271 waiting rooms (the Protective Mask there starts the fight: ptBossIn)
WAITING = [450020000, 450020100, 450020200]

sm.setSpeakerType(3)
sm.setParam(4)
sm.setInnerOverrideSpeakerTemplateID(3003208)  # Protective Mask
text = "#e<Boss: Lucid>#n\r\nSomething terrible will happen if Lucid is not stopped.\r\n#b"
for i, (name, lv, field, cd) in enumerate(MODES):
    text += "\r\n#L%d#I want to enter Boss: Lucid (%s). (Lv. %d+)#l" % (i, name, lv)
sel = sm.sendNext(text)
if 0 <= sel < len(MODES):
    name, lv, field, cd = MODES[sel]
    if sm.getParty() is None:
        sm.createSoloParty()
    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader talk to me if you wish to face Lucid.")
    elif sm.checkParty(cd, lv):
        sm.warpInstanceIn(WAITING[sel], 0, True)
        sm.setInstanceTime(10 * 60)  # the fight gets its 30 minutes when it starts
        sm.setDeathCount(BossConstants.LUCID_DEATHCOUNT)  # 10
        sm.setBossCooldown(cd)
sm.dispose()
