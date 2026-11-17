# Kid Draco (9010043): the Lunar New Year in Henesys (HolidayEvents.LUNAR_NEW_YEAR, Feb 5 - 20; GMS Feb 2013). A
# wish a day (five Hopeful Dragon Crystals that charge up in 60 minutes; pick the wish you want), his uncle the
# Black Dragon (the door opens at :05 and :35 every hour), and Cassandra's Yut points traded for Gift Boxes.
from net.swordie.ms.world import HolidayEvents as H
from net.swordie.ms.world import SeasonalEvents

sm.setSpeakerID(9010043)
WISH_NAMES = ["#b12,000 Spell Traces#k", "a #bSpecial Potential Scroll#k", "an #bIncredible Chaos Scroll 60%#k",
              "an #bAccessory ATT or Magic ATT 15% scroll#k", "an #bArmor ATT or Magic ATT 15% scroll#k"]


def wish():
    if H.hasDragonCrystals(chr, True):
        text = "Ooh, your crystals are glowing! Each one holds a different wish. Which wish should come true?\r\n"
        n = 0
        for i, name in enumerate(WISH_NAMES):
            if sm.hasItem(H.DRACO_WISHES[i][0]):
                text += "\r\n#L%d#I wish for %s.#l" % (i, name)
                n += 1
        sel = sm.sendNext(text)
        if sel < 0 or sel >= len(WISH_NAMES):
            return
        other = False
        if H.DRACO_WISHES[sel][2] != 0:
            pick = sm.sendNext("Which one?\r\n\r\n#L0##i%d# #t%d##l\r\n#L1##i%d# #t%d##l"
                               % (H.DRACO_WISHES[sel][1], H.DRACO_WISHES[sel][1], H.DRACO_WISHES[sel][2],
                                  H.DRACO_WISHES[sel][2]))
            other = pick == 1
        if H.grantDracoWish(chr, sel, other):
            sm.sendSayOkay("Granted! Hehe, I may be a black dragon, but I keep my promises. Happy New Year!")
        else:
            sm.sendSayOkay("Make some room in your Use inventory first!")
    elif H.hasDragonCrystals(chr, False):
        sm.sendSayOkay("Your Hopeful Dragon Crystals aren't charged yet. Keep them with you for #b60 minutes#k "
                       "(and don't log out, or they'll fade!), then come back.")
    elif H.canWishToDraco(chr):
        if sm.sendAskYesNo("Want me to grant you a wish? Hold these #bHopeful Dragon Crystals#k for #b60 minutes#k. "
                           "When they glow, come back and pick the wish you want!"):
            sm.startQuestNoCheck(H.DRACO_WISH)
            sm.sendSayOkay("There! Five crystals. Remember: 60 minutes, and no logging out!")
    else:
        sm.sendSayOkay("One wish a day! Even dragons have rules. Come back tomorrow.")


def dragon():
    if not H.isDragonTime():
        sm.sendSayOkay("Uncle only takes visitors at #b:05#k and #b:35#k every hour (the door stays open for ten "
                       "minutes). He's napping right now.")
    elif H.foughtDragonThisTime(chr):
        sm.sendSayOkay("You already saw Uncle this time! Give him a break, he's old. Come back at the next opening.")
    elif sm.sendAskYesNo("Want to challenge my uncle, the #rDraco Uncle Black Dragon#k? Beat him and you'll get "
                         "#btwo New Year's Gift Boxes#k! He's grumpy, but he's a good sport."):
        H.enterDragon(chr)
        sm.warpInstanceIn(H.BLACK_DRAGON_HOMETOWN, 0)


def yut():
    pts = H.yutPoints(chr)
    if pts[0] < H.YUT_POINTS_PER_BOX:
        sm.sendSayOkay("You have #b%d Yut points#k (#b%d#k this year). Throw the #bYut Sticks#k monsters drop "
                       "(double-click them) and come back with #b%d#k for a New Year's Gift Box!"
                       % (pts[0], pts[1], H.YUT_POINTS_PER_BOX))
        return
    n = H.tradeYut(chr)
    if n < 0:
        sm.sendSayOkay("Make some room in your Use inventory first!")
    else:
        sm.sendSayOkay("Mo! Yut! Hehe, you're good at this. Here: #b%d New Year's Gift Box(es)#k." % n)


if not SeasonalEvents.isActive(H.LUNAR_NEW_YEAR):
    sm.sendSayOkay("Rawr! I'm a dragon! ...A small one. Come see me at the Lunar New Year.")
elif chr.getLevel() < 10:
    sm.sendSayOkay("Happy New Year! Come back at #bLv. 10#k and I'll have something fun for you.")
else:
    text = "Happy Lunar New Year! I'm Kid Draco. What do you want to do?\r\n"
    text += "\r\n#L0##bMake a wish#k#l"
    text += "\r\n#L1##bChallenge the Black Dragon#k (:05 and :35)#l"
    text += "\r\n#L2##bTrade Yut points for New Year's Gift Boxes#k#l"
    sel = sm.sendNext(text)
    if sel == 0:
        wish()
    elif sel == 1:
        dragon()
    elif sel == 2:
        yut()
sm.dispose()
