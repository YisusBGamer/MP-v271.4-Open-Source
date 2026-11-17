# Silent Altar exit: back to the Guardian Angel Slime Altar
from net.swordie.ms.constants import BossConstants

if sm.sendAskYesNo("Would you like to leave the Silent Altar?"):
    sm.warpInstanceOut(BossConstants.GUARDIAN_ANGEL_SLIME_ALTAR, 0)
sm.dispose()
