# Mysterious Researcher (3003490): Ranheim Defense, at Trueffet Square (450006130) and the exit (993195800).
# GMS: Lv. 230+, after [Morass] The Swamp Remains (34272), solo, 3 clears a week (resets Thursday),
# 10 minutes. A clear gives 80 Arcane Symbol: Morass coupons and, if wanted, 1,127,760,555 EXP; an Immediate
# Completion gives the coupons only.
from net.swordie.ms.world.field.instance.instancehelper.arcane import ArcaneWeekly
from net.swordie.ms.world.field.instance.instancehelper.arcane import RanheimDefenseInstanceHelper as RD
from net.swordie.ms.constants import ServerFeatures

OPEN = ServerFeatures.on(ServerFeatures.Feature.ARCANE_WEEKLY_MINIGAMES)

RESEARCHER = 3003490
TOWN = 450006130
PREQUEST = 34272
COUPON = RD.REWARD_COUPON
COUPONS = RD.REWARD_COUPONS
EXP = RD.REWARD_EXP

sm.setSpeakerID(RESEARCHER)


EXP_QR = 502618  # weekly EXP claims, ranheimExp / ranheimWeek


def offer_exp(ask):
    claimed = int(sm.getQRValue(EXP_QR, "ranheimExp") or "0")
    if sm.getQRValue(EXP_QR, "ranheimWeek") != ArcaneWeekly.weekKey():
        claimed = 0
    if claimed >= 3:
        return
    if ask and not sm.sendAskYesNo(ask + "\r\n\r\n#r#e*Up to 3 times per week.#n#k"):
        return
    sm.giveExp(EXP)
    sm.setQRValue(EXP_QR, "ranheimExp", str(claimed + 1))
    sm.setQRValue(EXP_QR, "ranheimWeek", ArcaneWeekly.weekKey())


def cleared():
    return sm.getQRValue(RD.WEEKLY_QR, "clear") in ("1", "2")


def give_reward():
    if not sm.canHold(COUPON, COUPONS):
        sm.sendSayOkay("Make room in your USE inventory first, then talk to me again.")
        return False
    sm.sendNext("I knew you could do it. Thank you so much! You also proved the efficacy of the Ranheim Rod while you "
                "were at it. I'll most likely call on you again very soon.\r\n\r\n#i" + str(COUPON) + "##b#e#t"
                + str(COUPON) + "##n#k #ex" + str(COUPONS) + "#n\r\n#b#eEXP:#n#k#e" + "{:,}".format(EXP) + "#n")
    sm.giveItem(COUPON, COUPONS)
    sm.setQRValue(RD.WEEKLY_QR, "clear", "0")
    offer_exp("Do you want the Ranheim Defense completion EXP now?")
    return True


if sm.getFieldID() == RD.EXIT:
    if cleared():
        sm.sendNext("#b#e<Ranheim Defense>#n#k\r\n\r\nAre you all right? Light Magic takes a great physical toll.")
        give_reward()
    else:
        sm.sendNext("Hekaton's Body Fragments got the better of us this time... Let's try again later.")
    sm.warp(TOWN, 0)
    sm.dispose()
elif not OPEN:
    sm.sendSayOkay("#b<Ranheim Defense>#k isn't open on this server right now.")
    sm.dispose()
elif chr.getLevel() < 230 or not sm.hasQuestCompleted(PREQUEST):
    sm.sendSayOkay("Please complete #b[Morass] The Swamp Remains#k and reach Lv. 230 to attempt "
                   "#bRanheim Defense#k.")
    sm.dispose()
else:
    menu = ("#b#e<Ranheim Defense>#n#k\r\nThe Ranheim Rod is ready.\r\nStop Hekaton's resurrection, please!\r\n#b"
            "#L0# Attempt <Ranheim Defense>.#l\r\n"
            "#L1# Listen to the mysterious researcher.#l\r\n"
            "#L2# What's my remaining Immediate Completion count for this week?#l")
    if cleared():
        menu += "\r\n#L3# Claim <Ranheim Defense> reward.#l"
    choice = sm.sendNext(menu)
    instant = ArcaneWeekly.instantLeft(chr, RD.WEEKLY_QR)
    if choice == 0:
        clears = ArcaneWeekly.clears(chr, RD.WEEKLY_QR)
        if chr.getParty() is not None:
            sm.sendSayOkay("Ranheim Defense is solo only. Leave your party first.")
        elif not ArcaneWeekly.canClear(chr, RD.WEEKLY_QR):
            sm.sendSayOkay("You've protected the Ranheim Rod 3 times this week already. Come back after Thursday's "
                           "reset.")
        elif instant > 0 and sm.sendAskYesNo("You can use Immediate Complete on #b#eRanheim Defense#n#k. Don't "
                                             "forget that when you use Immediate Complete, the entry count is #r#e"
                                             "decreased by 1#n#k!\r\n#r(If you select No, you will enter Ranheim "
                                             "Defense.)#k#e\r\n\r\nRemaining Immediate Completions This Week: #b"
                                             + str(instant) + "#k\r\nImmediate Completion Reward: #b#t" + str(COUPON)
                                             + "# x" + str(COUPONS)):
            if not sm.canHold(COUPON, COUPONS):
                sm.sendSayOkay("Make room in your USE inventory first.")
            elif ArcaneWeekly.useInstant(chr, RD.WEEKLY_QR):
                sm.giveItem(COUPON, COUPONS)
                sm.sendSayOkay("I've given you the Immediate Completion reward.")
        elif sm.sendAskYesNo("If you're ready, I'll take you through time and space to where the Ranheim Rod is.\r\n"
                             "\r\n#bThis Week's Completion Count: " + str(clears)
                             + ("" if ArcaneWeekly.unlimited() else " / " + str(ArcaneWeekly.WEEKLY_CLEARS)) + "#k"):
            sm.warpInstanceIn(RD.FIELD, 0)
            sm.setInstanceTime(RD.TIME_LIMIT_SECONDS, RD.EXIT)
    elif choice == 1:
        sm.sendNext("Our mission is to protect the #b#eRanheim Rod#n#k. It's a magic pillar that transforms dark "
                    "Anti-Magic into Light Magic. Its power can destroy Hekaton's Body Fragments before he completes "
                    "their assembly.")
        sm.sendNext("Defeat the monsters around you to charge Light Magic. When it's full, press the #bharvest key#k at "
                    "the #bLight Magic Launcher#k in front of a Body Fragment to destroy it.")
        sm.sendSayOkay("After #b9#k Body Fragments, #rHekaton's Fragment#k appears. Hit it #b3#k times; it moves after "
                       "every hit. You have #b10 minutes#k, and the Rod mustn't fall.")
    elif choice == 2:
        sm.sendSayOkay("Remaining Immediate Completions This Week: #b" + str(instant) + "#k")
    elif choice == 3:
        give_reward()
    sm.dispose()
