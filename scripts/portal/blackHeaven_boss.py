# Lotus entry: the v265 fight (its own maps and monsters; attacks from the client's pattern system)
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.enums import BossCooldown

REQ_LEVEL = 190
# GMS v271 waiting rooms (ptBossIn starts the fight; the fight gets its 30 minutes then)
WAITING = [350069600, 350069700, 350069800]
COOLDOWNS = [BossCooldown.Lotus, BossCooldown.LotusHard, BossCooldown.LotusExtreme]

sm.flipSpeaker()
sm.flipDialoguePlayerAsSpeaker()
sm.setBoxChat()

dialog = "Do you want to head to Black Heaven Core to fight Lotus?\r\n"
for i in range(len(COOLDOWNS)):
	dialog += "#L%d#Go to the Black Heaven Core (%s Mode). (Lv. %d+)#l\r\n" % (i, BossConstants.LOTUS_V265_MODE_NAMES[i], REQ_LEVEL)
dialog += "#L99#Never mind."
response = sm.sendSay(dialog)

if 0 <= response < len(COOLDOWNS):
	if sm.getParty() is None:
		sm.createSoloParty()
	if not sm.isPartyLeader():
		sm.sendSayOkay("Please have your party leader talk to me if you wish to face Lotus.")
	elif response == 2 and sm.getParty().getMembers().size() > 2:
		sm.sendSayOkay("Only up to 2 party members can enter Extreme Mode.")
	elif sm.checkParty(COOLDOWNS[response], REQ_LEVEL):
		sm.warpInstanceIn(WAITING[response], 0, True)
		sm.setDeathCount(5)
		sm.setInstanceTime(10 * 60, 350060300)
		sm.setBossCooldown(COOLDOWNS[response])
