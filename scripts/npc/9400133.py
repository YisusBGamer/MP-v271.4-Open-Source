# Traveling Tangyoon (9400133) at the MMF Campgrounds (HolidayEvents#grill): the BBQ Party. 10 of each
# ingredient = a grill (5 Fire Grill Skewers, a coin a grill up to 10 a day); every 30 grills, everyone at the
# Campgrounds (all channels) eats.
from net.swordie.ms.world import HolidayEvents as H
from net.swordie.ms.world import SeasonalEvents

sm.setSpeakerID(9400133)
if not SeasonalEvents.isActive(H.MMF):
    sm.sendSayOkay("The grill's cold until the next Maple Music Festival. Just leave the cooking to me!")
else:
    have = ""
    for item in H.BBQ_INGREDIENTS:
        have += "\r\n#i%d# #t%d#: #b%d#k / %d" % (item, item, min(H.BBQ_PER_GRILL, sm.getQuantityOfItem(item)),
                                                 H.BBQ_PER_GRILL)
    if sm.sendAskYesNo("The meat's on the grill! It's almost barbecue time! Bring me #b%d of each#k ingredient "
                       "(monsters near your level carry them) and I'll cook. #b%d / %d#k grills until the whole "
                       "Campgrounds eats!\r\n%s\r\n\r\nGrill now?" % (H.BBQ_PER_GRILL, H.grillsCooked(),
                                                                       H.BBQ_GRILLS_FOR_PARTY, have)):
        r = H.grill(chr)
        if r == 0:
            sm.sendSayOkay("You're short on ingredients. I need #b%d of each#k!" % H.BBQ_PER_GRILL)
        elif r < 0:
            sm.sendSayOkay("Make some room in your Use inventory for the skewers first!")
        elif r == 2:
            sm.sendSayOkay("That did it! The grill is FULL! BBQ PARTY! Everybody eat!")
        else:
            sm.sendSayOkay("Sizzle sizzle! Here are some #bFire Grill Skewers#k for the cook.")
sm.dispose()
