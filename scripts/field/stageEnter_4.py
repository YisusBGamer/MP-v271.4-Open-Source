from net.swordie.ms.constants import OzConstants
from net.swordie.ms.enums import WeatherEffNoticeType
from net.swordie.ms.util import Util

side = "Right" if Util.getRandomBool() else "Left"
other_side = "Left" if side == "Right" else "Right"

instance = chr.getInstance()
if instance is not None:
    instance.addProperty(OzConstants.F4_CUR_SIDE, side)
    instance.addProperty(OzConstants.F4_BALANCE_POINTS, 0)
    sm.showWeatherNotice("Balance out the monster count on both sides.", WeatherEffNoticeType.Oz_Alicia, 15000)
    sm.progressMessageFont("The tower is losing its balance. Eliminate monsters on the {}.".format(other_side.lower()))