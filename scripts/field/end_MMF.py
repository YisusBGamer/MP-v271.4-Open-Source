# MMF Campgrounds (867112000) on enter: a welcome while the Maple Music Festival is on.
from net.swordie.ms.world import HolidayEvents as H
from net.swordie.ms.world import SeasonalEvents
if SeasonalEvents.isActive(H.MMF):
    sm.chatScript("Welcome to the MMF Campgrounds! DJ JM and Traveling Tangyoon are by the stage. Fireworks: "
                  "Saturdays at 3 PM and 8 PM.")
