# Mad Bunny's Easter Egg (Easter, HolidayEvents#openBox): a prize and 2 Swordie Coins.
from net.swordie.ms.world import HolidayEvents as H
got = H.openBox(chr, 2433793)
if got is not None:
    sm.chatScript("The egg held: " + got + "!")
sm.dispose()
