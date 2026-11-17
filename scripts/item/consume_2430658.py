# New Year's Gift Box (2430658): the Lunar New Year's boxes (HolidayEvents#openBox).
from net.swordie.ms.world import HolidayEvents as H
got = H.openBox(chr, 2430658)
if got is not None:
    sm.chatScript("Happy New Year! Inside the box: " + got + ".")
sm.dispose()
