# https://maplestory.fandom.com/wiki/Liuyi
# Liuyi, Yu Garden's Black Market Merchant. A Gift of Love (62103): the Engagement Ring Tomo wants is sold here, only
# between 6 PM and 2 AM when the lights go out (the quest's own text).
import datetime

RING = 4034655
RING_PRICE = 1000000

hour = datetime.datetime.now().hour
if sm.hasQuest(62103) and not sm.hasItem(RING) and (hour >= 18 or hour < 2):
    sm.setSpeakerID(9310554)
    if sm.sendAskYesNo("An #b#t%d##k? You're in luck, I happen to have one tonight. #b%d mesos#k, and don't ask where it came from." % (RING, RING_PRICE)):
        if chr.getMoney() < RING_PRICE:
            sm.sendSayOkay("No mesos, no ring.")
        elif not sm.canHold(RING, 1):
            sm.sendSayOkay("Make some room in your Etc inventory first.")
        else:
            sm.deductMesos(RING_PRICE)
            sm.giveItem(RING)
            sm.sendSayOkay("Pleasure doing business. Remember: you didn't get it from me.")
    else:
        sm.openShop(9310553)
else:
    sm.openShop(9310553)
