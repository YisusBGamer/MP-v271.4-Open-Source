# Nina (3003145): Erda Spectrum, in Nameless Town (450001000) and at the base after a run (450001550).
# GMS: Lv. 200+, after [Vanishing Journey] Arcane Symbol: Vanishing Journey (34120), 3 clears a week (resets
# Thursday). A clear gives 80 Arcane Symbol: Vanishing Journey coupons, and 187,726,220 EXP up to 3 times a week.
from net.swordie.ms.world.field.instance.instancehelper.arcane import ArcaneWeekly
from net.swordie.ms.world.field.instance.instancehelper.arcane import ErdaSpectrumInstanceHelper as ES
from net.swordie.ms.constants import ServerFeatures

OPEN = ServerFeatures.on(ServerFeatures.Feature.ARCANE_WEEKLY_MINIGAMES)

NINA = 3003145
TOWN = 450001000
PREQUEST = 34120
COUPON = 2635509
COUPONS = 80
EXP = 187726220
EXP_QR = 502618  # expCount, week: the weekly EXP claims

sm.setSpeakerID(NINA)


def give_reward():
    if not sm.canHold(COUPON, COUPONS):
        sm.sendSayOkay("Make room in your USE inventory first, then talk to me again.")
        return False
    sm.giveItem(COUPON, COUPONS)
    ArcaneWeekly.addClear(chr, ES.WEEKLY_QR)
    sm.setQRValue(ES.WEEKLY_QR, "clear", "0")
    week = ArcaneWeekly.clears(chr, ES.WEEKLY_QR)
    claimed = int(sm.getQRValue(EXP_QR, "expCount") or "0")
    if sm.getQRValue(EXP_QR, "week") != ArcaneWeekly.weekKey():
        claimed = 0
    if claimed < 3 and sm.sendAskYesNo("You didn't get all the Erda Spectrum completion EXP for this week yet.\r\n"
                                       "Do you want the EXP now? (#b" + "{:,}".format(EXP) + "#k)\r\n\r\n"
                                       "#r#e*Up to 3 times per week.#n#k"):
        sm.giveExp(EXP)
        sm.setQRValue(EXP_QR, "expCount", str(claimed + 1))
        sm.setQRValue(EXP_QR, "week", ArcaneWeekly.weekKey())
    return True


if sm.getFieldID() == ES.EXIT:
    if sm.getQRValue(ES.WEEKLY_QR, "clear") == "1":
        sm.sendNext("Thanks for all your help with the investigation! Studying Erda's color made it worth all that "
                    "hard work, don't you think?\r\n\r\n#i" + str(COUPON) + "# #b#t" + str(COUPON) + "##k x"
                    + str(COUPONS))
        if give_reward():
            sm.warp(TOWN, 0)
    else:
        sm.sendNext("Unfortunately, you didn't complete the investigation. Better luck next time... Goodbye!")
        sm.warp(TOWN, 0)
    sm.dispose()
elif not OPEN:
    sm.sendSayOkay("#b<Erda Spectrum>#k isn't open on this server right now.")
    sm.dispose()
elif chr.getLevel() < 200 or not sm.hasQuestCompleted(PREQUEST):
    sm.sendSayOkay("Please complete #b[Vanishing Journey] Arcane Symbol: Vanishing Journey#k and reach Lv. 200 to "
                   "help with the #bErda Spectrum#k.")
    sm.dispose()
else:
    clears = ArcaneWeekly.clears(chr, ES.WEEKLY_QR)
    menu = ("#b#eErda Spectrum#n#k\r\nDid you know Erda has its own distinct color? Amazing right?! The only "
            "trouble is, while I was gathering Erda for study with the #bErda Collector#k I invented, I got hurt. "
            "Now I need help...\r\nClears this week: #b" + str(clears)
            + ("" if ArcaneWeekly.unlimited() else " / " + str(ArcaneWeekly.WEEKLY_CLEARS)) + "#k\r\n\r\n#b"
            "#L0# Send me to the Erda Spectrum!#l\r\n"
            "#L1# So... What's this all about again?#l")
    if sm.getQRValue(ES.WEEKLY_QR, "clear") == "1":
        menu += "\r\n#L3# I'd like to claim my Erda Spectrum reward.#l"
    instant = ArcaneWeekly.instantLeft(chr, ES.WEEKLY_QR)
    if instant > 0:
        menu += "\r\n#L4# Finish it at once (" + str(instant) + " left this week).#l"
    choice = sm.sendNext(menu)
    if choice == 0:
        if chr.getParty() is not None:
            sm.sendSayOkay("This server runs the Erda Spectrum solo. Leave your party first.")
        elif not ArcaneWeekly.canClear(chr, ES.WEEKLY_QR):
            sm.sendSayOkay("You've helped me 3 times this week already. Come back after Thursday's reset.")
        else:
            sm.setQRValue(ES.WEEKLY_QR, "clear", "0")
            sm.warpInstanceIn(ES.FIELD, 0)
            sm.setInstanceTime(ES.PHASE_1_SECONDS, ES.EXIT)
    elif choice == 1:
        sm.sendNext("First, defeat the #bErda#k around the #bErda Collector#k: each one charges it. Press the "
                    "#bHarvest key#k next to it to switch it on for a second per charge.")
        sm.sendNext("Attack the running Collector: it lets out #bErda Clusters#k of its color, blue, red and purple in "
                    "turn. Push each cluster into the field of its color with the #bHarvest key#k. Blue and red are "
                    "worth #b1#k, purple #b2#k. Get #b10#k within #b10 minutes#k.")
        sm.sendSayOkay("Then #rArma Jr.#k comes out of the caves. Defeat #bArma's Followers#k to charge the Erda "
                       "Condenser and hold its laser on an Arma Jr. to push it back. Do it #b5#k times before one "
                       "reaches the center.")
    elif choice == 3:
        give_reward()
    elif choice == 4:
        # instant clears give the symbols only (no EXP)
        if not sm.canHold(COUPON, COUPONS):
            sm.sendSayOkay("Make room in your USE inventory first.")
        elif ArcaneWeekly.useInstant(chr, ES.WEEKLY_QR):
            sm.giveItem(COUPON, COUPONS)
            sm.sendSayOkay("Thanks! I finished the investigation with the Erda you helped me collect before.")
    sm.dispose()
