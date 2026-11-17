# Shubert (3003538): Esfera Guardian, at the Base Camp (450007040) and the exit (993195600).
# GMS (Etc.wz MapleScheduler, v271.4): Lv. 235+, after quest 37871, solo, 3 clears a week (resets Thursday),
# 10 Dimensional Gates to bring down. A clear gives 80 Arcane Symbol: Esfera coupons and 1,161,754,860 EXP; after 2
# clears in a week the 3rd can be an Immediate Completion (coupons only).
from net.swordie.ms.world.field.instance.instancehelper.arcane import ArcaneWeekly
from net.swordie.ms.world.field.instance.instancehelper.arcane import EsferaGuardianInstanceHelper as EG
from net.swordie.ms.constants import ServerFeatures

OPEN = ServerFeatures.on(ServerFeatures.Feature.ARCANE_WEEKLY_MINIGAMES)

SHUBERT = 3003538
TOWN = 450007040
PREQUEST = 37871
MIN_LEVEL = 235
COUPON = 2635514
COUPONS = 80
EXP = 1161754860

sm.setSpeakerID(SHUBERT)


EXP_QR = 502618  # weekly EXP claims, esferaExp / esferaWeek


def offer_exp(ask):
    claimed = int(sm.getQRValue(EXP_QR, "esferaExp") or "0")
    if sm.getQRValue(EXP_QR, "esferaWeek") != ArcaneWeekly.weekKey():
        claimed = 0
    if claimed >= 3:
        return
    if ask and not sm.sendAskYesNo(ask + "\r\n\r\n#r#e*Up to 3 times per week.#n#k"):
        return
    sm.giveExp(EXP)
    sm.setQRValue(EXP_QR, "esferaExp", str(claimed + 1))
    sm.setQRValue(EXP_QR, "esferaWeek", ArcaneWeekly.weekKey())


def give_reward():
    if not sm.canHold(COUPON, COUPONS):
        sm.sendSayOkay("Make room in your USE inventory first, then talk to me again.")
        return False
    sm.sendNext("You blasted those Dimensional Gates to kingdom come. Nice work!\r\n\r\n"
                "#i" + str(COUPON) + "# #b#t" + str(COUPON) + "##k x" + str(COUPONS) + "\r\n"
                "#bEXP:#k " + "{:,}".format(EXP))
    sm.giveItem(COUPON, COUPONS)
    offer_exp(None)
    sm.setQRValue(EG.SCORE_QR, "symbol", "0")
    sm.setQRValue(EG.WEEKLY_QR, "clear", "0")
    ArcaneWeekly.addClear(chr, EG.WEEKLY_QR)
    return True


def instant_left():
    clears = ArcaneWeekly.clears(chr, EG.WEEKLY_QR)
    if ArcaneWeekly.unlimited():
        return 1 if clears >= 2 else 0
    return 1 if clears == 2 else 0


if sm.getFieldID() == EG.EXIT:
    if sm.getQRValue(EG.SCORE_QR, "symbol") == "1":
        if give_reward():
            sm.warp(TOWN, 0)
    else:
        sm.sendNext("The gates held this time... Let's regroup and try again.")
        sm.warp(TOWN, 0)
    sm.dispose()
elif not OPEN:
    sm.sendSayOkay("#b<Esfera Guardian>#k isn't open on this server right now.")
    sm.dispose()
elif chr.getLevel() < MIN_LEVEL or not sm.hasQuestCompleted(PREQUEST):
    sm.sendSayOkay("Reach Lv. " + str(MIN_LEVEL) + " and finish Esfera's story first, then come help with "
                   "#bEsfera Guardian#k.")
    sm.dispose()
else:
    clears = ArcaneWeekly.clears(chr, EG.WEEKLY_QR)
    menu = ("#b#e<Esfera Guardian>#n#k\r\nAre you ready for the front line? For Esfera, you and me!\r\n"
            "This Week's Completion Count: #b" + str(clears)
            + ("" if ArcaneWeekly.unlimited() else " / " + str(ArcaneWeekly.WEEKLY_CLEARS)) + "#k\r\n\r\n#b"
            "#L0# I'd like to help with Operation: <Esfera Guardian>.#l\r\n"
            "#L1# Tell me about the operation.#l")
    if sm.getQRValue(EG.SCORE_QR, "symbol") == "1":
        menu += "\r\n#L3# Claim <Esfera Guardian> reward.#l"
    if instant_left() > 0:
        menu += "\r\n#L4# Immediate Completion (" + str(instant_left()) + " left this week).#l"
    choice = sm.sendNext(menu)
    if choice == 0:
        if chr.getParty() is not None:
            sm.sendSayOkay("Esfera Guardian is solo only. Leave your party first.")
        elif not ArcaneWeekly.canClear(chr, EG.WEEKLY_QR):
            sm.sendSayOkay("You can't make any more attempts this week.\r\n<Esfera Guardian> can be done "
                           "#b3 times a week at most#k.")
        elif sm.getQRValue(EG.SCORE_QR, "symbol") == "1":
            sm.sendSayOkay("Claim your reward from the last operation first.")
        elif sm.sendAskYesNo("If you're ready, I'll send you to the hot zone, where the Dimensional Gates are.\r\n"
                             "#bThis Week's Completion Count: " + str(clears) + " / 3#k"):
            sm.warpInstanceIn(EG.FIELD, 0)
            sm.setInstanceTime(EG.TIME_LIMIT_SECONDS, EG.EXIT)
    elif choice == 1:
        sm.sendNext("Mirror World creatures are pouring through #bDimensional Gates#k. Defeat the #bLight#k and "
                    "#bDark Executors#k to fill the Erda gauge.")
        sm.sendSayOkay("Then fire my #bSpecial Turret#k: each #bSuper Erda Shell#k uses #b45#k Erda and only hurts "
                       "the gates. Bring down all #b10#k gates to win.")
    elif choice == 3:
        give_reward()
    elif choice == 4:
        if not sm.canHold(COUPON, COUPONS):
            sm.sendSayOkay("Make room in your USE inventory first.")
        elif instant_left() > 0 and sm.sendAskYesNo("Immediate Completion uses up one of this week's entries. "
                                                    "Go ahead?"):
            ArcaneWeekly.addClear(chr, EG.WEEKLY_QR)
            sm.giveItem(COUPON, COUPONS)
            sm.sendSayOkay("Done. Esfera thanks you!")
    sm.dispose()
