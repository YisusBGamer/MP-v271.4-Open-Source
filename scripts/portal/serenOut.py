# Seren's treasure room exit: back to the Palace Main Hall
from net.swordie.ms.constants import BossConstants

if sm.sendAskYesNo("Would you like to leave?"):
    sm.warpInstanceOut(BossConstants.SEREN_ENTRANCE, 0)
sm.dispose()
