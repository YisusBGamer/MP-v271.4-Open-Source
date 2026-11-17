# Mr. Flopsy the Dreamweaver (9010100): Midnight Chaser, on Lachelein Main Street (450003000) and at the exit
# (921171100). GMS since the Savior update: Lv. 220+, after [Lachelein] Decisive Battle (34331, Etc.wz
# ContentsGuide/MapleScheduler), solo, 3 clears a week (resets Thursday). Find Mr. Flopsy 9 times in the 9 rooms. A
# clear gives 80 Arcane Symbol: Lachelein coupons or 817,717,500 EXP. Old Dream Coins still trade 30 for a symbol.
from net.swordie.ms.world.field.instance.instancehelper.arcane import ArcaneWeekly
from net.swordie.ms.world.field.instance.instancehelper.arcane import MidnightChaserInstanceHelper as MC
from net.swordie.ms.constants import ServerFeatures

OPEN = ServerFeatures.on(ServerFeatures.Feature.ARCANE_WEEKLY_MINIGAMES)

FLOPSY = 9010100
TOWN = 450003000
PREQUEST = 34331
COUPON = 2635511
COUPONS = 80
EXP = 817717500
DREAM_COIN = 4310227
SYMBOL = 1712003

sm.setSpeakerID(FLOPSY)


def give_reward():
    pick = sm.sendNext("You found me every time! Well played.\r\nWhat would you like as your prize?\r\n#b"
                       "#L0##i" + str(COUPON) + "# #t" + str(COUPON) + "# x" + str(COUPONS) + "#l\r\n"
                       "#L1#" + "{:,}".format(EXP) + " EXP#l")
    if pick == 0:
        if not sm.canHold(COUPON, COUPONS):
            sm.sendSayOkay("Make room in your USE inventory first, then talk to me again.")
            return False
        sm.giveItem(COUPON, COUPONS)
    else:
        sm.giveExp(EXP)
    sm.setQRValue(MC.RECORD_QR, "reward", "0")
    return True


if sm.getFieldID() == MC.EXIT:
    if sm.getQRValue(MC.RECORD_QR, "reward") == "1":
        if give_reward():
            sm.warp(TOWN, 0)
    else:
        sm.sendNext("Hehe, you didn't find me in time! Let's play again later.")
        sm.warp(TOWN, 0)
    sm.dispose()
elif not OPEN:
    sm.sendSayOkay("#b<Midnight Chaser>#k isn't open on this server right now.")
    sm.dispose()
elif chr.getLevel() < 220 or not sm.hasQuestCompleted(PREQUEST):
    sm.sendSayOkay("Finish #b[Lachelein] Decisive Battle#k and reach Lv. 220, then we can play "
                   "#bMidnight Chaser#k!")
    sm.dispose()
else:
    clears = ArcaneWeekly.clears(chr, MC.RECORD_QR)
    menu = ("#e#b<Midnight Chaser>#k#n\r\nWant to play hide-and-seek in the clock tower?\r\n"
            "Clears this week: #b" + str(clears) + ("" if ArcaneWeekly.unlimited() else " / " + str(ArcaneWeekly.WEEKLY_CLEARS)) + "#k\r\n\r\n#b"
            "#L0#Play <Midnight Chaser>.#l\r\n"
            "#L1#Trade in Dream Coins.#l\r\n"
            "#L2#Listen to the explanation.#l")
    if sm.getQRValue(MC.RECORD_QR, "reward") == "1":
        menu += "\r\n#L3#Collect my prize.#l"
    instant = ArcaneWeekly.instantLeft(chr, MC.RECORD_QR)
    if instant > 0:
        menu += "\r\n#L4#Clear it at once (" + str(instant) + " left this week).#l"
    choice = sm.sendNext(menu)
    if choice == 0:
        if chr.getParty() is not None:
            sm.sendSayOkay("Midnight Chaser is solo only. Leave your party first.")
        elif not ArcaneWeekly.canClear(chr, MC.RECORD_QR):
            sm.sendSayOkay("We've played 3 times this week already. Come back after Thursday's reset.")
        elif sm.sendAskYesNo("Ready? I'll hide... no peeking!"):
            sm.warpInstanceIn(MC.FIELD, 0)
            sm.setInstanceTime(MC.TIME_LIMIT_SECONDS, MC.EXIT)
    elif choice == 1:
        most = sm.getQuantityOfItem(DREAM_COIN) // 30
        if most <= 0:
            sm.sendSayOkay("You need 30 #t" + str(DREAM_COIN) + "#s for each #t" + str(SYMBOL) + "#.")
        else:
            count = sm.sendAskNumber("How many #t" + str(SYMBOL) + "# would you like? (#t" + str(DREAM_COIN)
                                     + "# x30 each, up to " + str(most) + ")", 1, 1, most)
            if 0 < count <= most and sm.canHold(SYMBOL, count):
                sm.consumeItem(DREAM_COIN, count * 30)
                for i in range(count):
                    chr.addItemToInventory(SYMBOL, 1)
            else:
                sm.sendSayOkay("Make room in your EQUIP inventory first.")
    elif choice == 2:
        sm.sendNext("I'll hide in one of the #b9 rooms#k of the clock tower. The bar at the top tells you which piece "
                    "of furniture I'm in.")
        sm.sendNext("Each room has one piece of furniture. Defeat #ball the monsters#k in a room, then press the "
                    "#bHarvest key#k at its furniture. The right one and you've found me; the wrong one and the "
                    "monsters come back.")
        sm.sendSayOkay("Find me #b9 times#k before the time runs out!")
    elif choice == 3:
        give_reward()
    elif choice == 4:
        # instant clears give the symbols only (no EXP)
        if not sm.canHold(COUPON, COUPONS):
            sm.sendSayOkay("Make room in your USE inventory first.")
        elif ArcaneWeekly.useInstant(chr, MC.RECORD_QR):
            sm.giveItem(COUPON, COUPONS)
            sm.sendSayOkay("Hehe, you know all my hiding spots by now. Here's your prize!")
    sm.dispose()
