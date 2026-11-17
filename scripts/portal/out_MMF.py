# MMF Campgrounds exit: back to the town the player came from (HolidayEvents#rememberMmfTown).
from net.swordie.ms.world import HolidayEvents as H
sm.warp(H.mmfReturnTown(chr), 0)
sm.dispose()
