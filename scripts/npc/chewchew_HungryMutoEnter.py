# Simia (3003166): Hungry Muto, at the Quiet Village Path (450002023) and Hungry Muto's Exit (450002024).
# GMS: Lv. 210+, after [Chu Chu] Goodbye, Chu Chu Island (34218), solo, 3 clears a week (resets Thursday),
# 10 minutes. A clear gives 80 Arcane Symbol: Chu Chu Island coupons and EXP.
from net.swordie.ms.world.field.instance.instancehelper.arcane import ArcaneWeekly
from net.swordie.ms.world.field.instance.instancehelper.arcane import HungryMutoInstanceHelper as HM
from net.swordie.ms.constants import ServerFeatures

OPEN = ServerFeatures.on(ServerFeatures.Feature.ARCANE_WEEKLY_MINIGAMES)

SIMIA = 3003166
TOWN = 450002023
PREQUEST = 34218
COUPON = 2635510
COUPONS = 80
EXP = 549143140

sm.setSpeakerID(SIMIA)


EXP_QR = 502618  # weekly EXP claims, mutoExp / mutoWeek


def offer_exp(ask):
    claimed = int(sm.getQRValue(EXP_QR, "mutoExp") or "0")
    if sm.getQRValue(EXP_QR, "mutoWeek") != ArcaneWeekly.weekKey():
        claimed = 0
    if claimed >= 3:
        return
    if ask and not sm.sendAskYesNo(ask + "\r\n\r\n#r#e*Up to 3 times per week.#n#k"):
        return
    sm.giveExp(EXP)
    sm.setQRValue(EXP_QR, "mutoExp", str(claimed + 1))
    sm.setQRValue(EXP_QR, "mutoWeek", ArcaneWeekly.weekKey())


def give_reward():
    if not sm.canHold(COUPON, COUPONS):
        sm.sendSayOkay("Make room in your USE inventory first, then talk to me again.")
        return False
    sm.sendNext("Thank you for helping! Muto is finally full.\r\nHere! Take this gift I prepared for you.\r\n\r\n"
                "#i" + str(COUPON) + "# #b#t" + str(COUPON) + "##k x" + str(COUPONS) + "\r\n#bEXP: "
                + "{:,}".format(EXP) + "#k")
    sm.giveItem(COUPON, COUPONS)
    sm.setQRValue(HM.WEEKLY_QR, "clear", "0")
    offer_exp(None)
    sm.setQRValue(HM.WEEKLY_QR, "score", "0")
    sm.setQRValue(HM.WEEKLY_QR, "ptime", "0")
    return True


reward_waiting = sm.getQRValue(HM.WEEKLY_QR, "clear") == "1"

if sm.getFieldID() == HM.EXIT:
    if reward_waiting:
        if give_reward():
            sm.warp(TOWN, 0)
    else:
        sm.sendNext("Muto is still hungry... Let's try again later.")
        sm.warp(TOWN, 0)
    sm.dispose()
elif not OPEN:
    sm.sendSayOkay("#b<Hungry Muto>#k isn't open on this server right now.")
    sm.dispose()
elif chr.getLevel() < 210 or not sm.hasQuestCompleted(PREQUEST):
    sm.sendSayOkay("Please complete #b[Chu Chu] Goodbye, Chu Chu Island#k and reach Lv. 210 to attempt "
                   "#bHungry Muto#k.")
    sm.dispose()
else:
    clears = ArcaneWeekly.clears(chr, HM.WEEKLY_QR)
    menu = ("#b#eHungry Muto#n#k\r\n Will you help #bMuto#k get his fill?\r\n#r*Gameplay will last for 10 minutes.#k\r\n"
            "Clears this week: #b" + str(clears) + ("" if ArcaneWeekly.unlimited() else " / " + str(ArcaneWeekly.WEEKLY_CLEARS)) + "#k\r\n\r\n#b"
            "#L0#Enter <Hungry Muto>.#l\r\n"
            "#L1#Listen to Simia's explanation.#l")
    if reward_waiting:
        menu += "\r\n#L2#Claim <Hungry Muto> reward.#l"
    instant = ArcaneWeekly.instantLeft(chr, HM.WEEKLY_QR)
    if instant > 0:
        menu += "\r\n#L3#Immediate Completion (" + str(instant) + " left this week).#l"
    choice = sm.sendNext(menu)
    if choice == 0:
        if chr.getParty() is not None:
            sm.sendSayOkay("Hungry Muto is solo only. Leave your party first.")
        elif not ArcaneWeekly.canClear(chr, HM.WEEKLY_QR):
            sm.sendSayOkay("You've filled Muto up 3 times this week already. Come back after Thursday's reset.")
        elif sm.sendAskYesNo("Do you want to challenge #b#e<Hungry Muto>#n#k?"):
            sm.warpInstanceIn(HM.FIELD, 0)
            sm.setInstanceTime(HM.TIME_LIMIT_SECONDS, HM.EXIT)
    elif choice == 1:
        sm.sendNext("Muto orders one dish at a time. Hunt the monsters around Muto for the ingredients it needs; "
                    "each monster drops its own ingredient.")
        sm.sendNext("Bring them to #bMuto#k, in the middle of the field, before the order's timer runs out. When "
                    "an order is complete, Muto gets fuller and orders the next dish, with new monsters.")
        sm.sendSayOkay("Muto gets hungrier all the time. Fill his gauge up to #b1,000#k within #b10 minutes#k "
                       "to clear. If it runs empty, the game is over.")
    elif choice == 2:
        give_reward()
    elif choice == 3:
        # instant clears give the symbols only (no EXP)
        if not sm.canHold(COUPON, COUPONS):
            sm.sendSayOkay("Make room in your USE inventory first.")
        elif ArcaneWeekly.useInstant(chr, HM.WEEKLY_QR):
            sm.giveItem(COUPON, COUPONS)
            sm.sendSayOkay("I've given you the Immediate Completion reward. Check your inventory.")
    sm.dispose()
