# Undersea Tower of Oz : Lobby
# 992000000
from net.swordie.ms.client.partyquests.towerofoz import OzTowerModule
from net.swordie.ms.loaders import QuestData

sm.lockInGameUI(True, False)
sm.lockInGameUI(False, False)

# the Tower of Oz records (42000-42003) are record-only (no Quest.wz quest); OzTowerModule makes them when missing
if chr is not None:
    OzTowerModule.updateOzTimer(chr, True)
    OzTowerModule.giveOzRewards(chr)