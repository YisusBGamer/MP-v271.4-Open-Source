# DJ JM (9400137): the Maple Music Festival in the major towns (HolidayEvents.MMF, Oct 25 - Nov 15): the way to the
# MMF Campgrounds.
from net.swordie.ms.world import HolidayEvents as H
from net.swordie.ms.world import SeasonalEvents

sm.setSpeakerID(9400137)
if not SeasonalEvents.isActive(H.MMF):
    sm.sendSayOkay("Yo! The Maple Music Festival comes back at the end of October. Keep your ears open!")
elif chr.getLevel() < 33:
    sm.sendSayOkay("Yo! The Maple Music Festival is for adventurers of #bLv. 33#k and up. Come party with us soon!")
else:
    text = "Yo yo yo! The #bMaple Music Festival#k is ON! Food, fireworks, and the freshest beats in Maple World!\r\n"
    text += "\r\n#L0##bTake me to the MMF Campgrounds.#k#l"
    text += "\r\n#L1#What's going on at the festival?#l"
    sel = sm.sendNext(text)
    if sel == 0:
        H.rememberMmfTown(chr)
        sm.warp(H.MMF_CAMPGROUNDS, 0)
    elif sel == 1:
        sm.sendNext("At the #bCampgrounds#k: my other self is set up by the stage. I lost half my gear on the way "
                    "over and could really use a hand finding it. And I always have a flyer to un-crumple...")
        sm.sendNext("#bTraveling Tangyoon#k runs the BBQ: monsters near your level carry Firewood, Onions, Mushrooms, "
                    "Sausages and Raw Meat. Bring him 10 of each and he'll grill. When the grill's full, everybody at "
                    "the Campgrounds eats!")
        sm.sendSayOkay("And every #bSaturday at 3 PM and 8 PM#k: the #bFireworks Show#k, ten minutes of booms and a "
                       "buff for everyone at the Campgrounds. Don't miss it!")
sm.dispose()
