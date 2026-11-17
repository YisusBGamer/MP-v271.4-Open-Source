# Escaped Rock Spirit (3003381): Spirit Savior, at the Grove of the Spirit Tree (450005000) and the exit (921172400).
# GMS since the Savior update: Lv. 225+, after [Arcana] The Harmony of the Forest (34478), solo, 3 clears a week
# (resets Thursday), 10 minutes, 10,000 points. A clear gives 80 Arcane Symbol: Arcana coupons or 847,484,010 EXP.
# Old Spirit Coins still trade 3 for a symbol.
from net.swordie.ms.world.field.instance.instancehelper.arcane import ArcaneWeekly
from net.swordie.ms.world.field.instance.instancehelper.arcane import SpiritSaviorInstanceHelper as SS
from net.swordie.ms.constants import ServerFeatures

OPEN = ServerFeatures.on(ServerFeatures.Feature.ARCANE_WEEKLY_MINIGAMES)

ROCK_SPIRIT = 3003381
TOWN = 450005000
PREQUEST = 34478
COUPON = 2635512
COUPONS = 80
EXP = 847484010
SPIRIT_COIN = 4310235
SYMBOL = 1712004

sm.setSpeakerID(ROCK_SPIRIT)


def give_reward():
    pick = sm.sendNext("You did it! My friends are safe, thanks to you.\r\nWhat would you like as thanks?\r\n#b"
                       "#L0##i" + str(COUPON) + "# #t" + str(COUPON) + "# x" + str(COUPONS) + "#l\r\n"
                       "#L1#" + "{:,}".format(EXP) + " EXP#l")
    if pick == 0:
        if not sm.canHold(COUPON, COUPONS):
            sm.sendSayOkay("Make room in your USE inventory first, then talk to me again.")
            return False
        sm.giveItem(COUPON, COUPONS)
    else:
        sm.giveExp(EXP)
    sm.setQRValue(SS.SCORE_QR, "reward", "0")
    return True


if sm.getFieldID() == SS.EXIT:
    if sm.getQRValue(SS.SCORE_QR, "reward") == "1":
        if give_reward():
            sm.warp(TOWN, 0)
    else:
        sm.sendNext("The Toxic Spirit was too much this time... Let's try again later.")
        sm.warp(TOWN, 0)
    sm.dispose()
elif not OPEN:
    sm.sendSayOkay("#b<Spirit Savior>#k isn't open on this server right now.")
    sm.dispose()
elif chr.getLevel() < 225 or not sm.hasQuestCompleted(PREQUEST):
    sm.sendSayOkay("Please complete #b[Arcana] The Harmony of the Forest#k and reach Lv. 225 to attempt "
                   "#bSpirit Savior#k.")
    sm.dispose()
else:
    clears = ArcaneWeekly.clears(chr, SS.WEEKLY_QR)
    menu = ("#e#b<Spirit Savior>#k#n\r\n(Yawn) Will I ever get any sleep?\r\n"
            "Clears this week: #b" + str(clears) + ("" if ArcaneWeekly.unlimited() else " / " + str(ArcaneWeekly.WEEKLY_CLEARS)) + "#k\r\n\r\n#b"
            "#L0#Attempt <Spirit Savior>.#l\r\n"
            "#L1#Trade in Spirit Coins.#l\r\n"
            "#L2#Listen to the explanation.#l")
    if sm.getQRValue(SS.SCORE_QR, "reward") == "1":
        menu += "\r\n#L3#Collect my reward.#l"
    instant = ArcaneWeekly.instantLeft(chr, SS.WEEKLY_QR)
    if instant > 0:
        menu += "\r\n#L4#Clear it at once (" + str(instant) + " left this week).#l"
    choice = sm.sendNext(menu)
    if choice == 0:
        if chr.getParty() is not None:
            sm.sendSayOkay("Spirit Savior is solo only. Leave your party first.")
        elif not ArcaneWeekly.canClear(chr, SS.WEEKLY_QR):
            sm.sendSayOkay("You've rescued my friends 3 times this week already. Come back after Thursday's reset.")
        elif sm.sendAskYesNo("Are you ready to go save my friends?"):
            sm.warpInstanceIn(SS.FIELD, 0)
            sm.setInstanceTime(SS.TIME_LIMIT_SECONDS, SS.EXIT)
    elif choice == 1:
        most = sm.getQuantityOfItem(SPIRIT_COIN) // 3
        if most <= 0:
            sm.sendSayOkay("You need 3 #t" + str(SPIRIT_COIN) + "#s for each #t" + str(SYMBOL) + "#.")
        else:
            count = sm.sendAskNumber("How many #t" + str(SYMBOL) + "# would you like? (#t" + str(SPIRIT_COIN)
                                     + "# x3 each, up to " + str(most) + ")", 1, 1, most)
            if 0 < count <= most and sm.canHold(SYMBOL, count):
                sm.consumeItem(SPIRIT_COIN, count * 3)
                for i in range(count):
                    chr.addItemToInventory(SYMBOL, 1)
            else:
                sm.sendSayOkay("Make room in your EQUIP inventory first.")
    elif choice == 2:
        sm.sendNext("Hit the #bBound Rock Spirits#k to free them, then press the #bHarvest key#k next to a freed "
                    "spirit to take it along. You can lead up to #b5#k at once.")
        sm.sendNext("Bring them back to the #bstart#k, at the bottom of the forest: 1 spirit is #b400#k points, 2 are "
                    "#b800#k, 3 are #b1,200#k, 4 are #b1,800#k and 5 are #b2,500#k. Reach #b10,000#k points within "
                    "#b10 minutes#k.")
        sm.sendSayOkay("Watch out for the #rToxic Spirit#k: it wakes with the first freed spirit and grows with each "
                       "one. If it catches you while you're leading spirits, they're lost.")
    elif choice == 3:
        give_reward()
    elif choice == 4:
        # instant clears give the symbols only (no EXP)
        if not sm.canHold(COUPON, COUPONS):
            sm.sendSayOkay("Make room in your USE inventory first.")
        elif ArcaneWeekly.useInstant(chr, SS.WEEKLY_QR):
            sm.giveItem(COUPON, COUPONS)
            sm.sendSayOkay("Thanks to you, my friends found their way home on their own!")
    sm.dispose()
