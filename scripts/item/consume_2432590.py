# 2017 Easter Random Box (Easter, HolidayEvents#openBox): a prize and 2 Swordie Coins.
from net.swordie.ms.world import HolidayEvents as H
got = H.openBox(chr, 2432590)
if got is not None:
    sm.chatScript("The box held: " + got + "!")
sm.dispose()
