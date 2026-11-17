from net.swordie.ms.constants import OzConstants
from net.swordie.ms.enums import WeatherEffNoticeType

sm.showWeatherNotice("Collect {} Ancient Turtle Eggs.".format(OzConstants.F3_EGG), WeatherEffNoticeType.Oz_Alicia, 15000)
instance = chr.getInstance()
if instance is not None:
    if not instance.hasProperty("F3_Inited"):
        instance.addProperty("F3_Inited", True)
        instance.addProperty(OzConstants.F3_EGG_PROP, 0)