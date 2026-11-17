# 9390612
from net.swordie.ms.constants import BossConstants

field.setProperty(BossConstants.GOLLUX_FINISHED_KEY, True)
# navel opens: closed cover off, opened navel and the portal to the core on
sm.changeFoothold("change", False)
sm.golluxPortalOpen("clear2")
sm.golluxPortalOpen("clear1")
sm.showGolluxMiniMap()