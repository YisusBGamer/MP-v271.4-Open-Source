# Damien leave NPC
from net.swordie.ms.client.character.skills.temp import CharacterTemporaryStat
from net.swordie.ms.life.mob.boss.demian import Demian

WORLD_TREE_SUMMIT = 105300303

sm.flipSpeaker()
sm.flipDialoguePlayerAsSpeaker()
sm.setBoxChat()

dialog = str()

# the fallen Damien and his Shadow Zone are not a fight still going on
if Demian.hasFightingDamien(sm.getField()):
	dialog = "Are you sure you want to leave the battlefield and abandon your party members?"

else:
	dialog = "You have defeated Damien. Do you want to leave?"

if sm.sendAskYesNo(dialog):
	sm.warpInstanceOut(WORLD_TREE_SUMMIT)
	sm.removeCTS(CharacterTemporaryStat.Stigma)
