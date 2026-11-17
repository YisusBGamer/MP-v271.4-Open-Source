# @optimize: gear and stat upkeep done for you, one step or all of them (AutoGearModule)
from net.swordie.ms.client.character import AutoEnhanceModule
from net.swordie.ms.client.character import AutoGearModule
from net.swordie.ms.client.character import CubingModule
from net.swordie.ms.client.character.skills.vmatrix import V271Matrix
from net.swordie.ms.constants import GameConstants

sm.setSpeakerID(2007)  # Maple Administrator


MARKUP = GameConstants.OPTIMIZE_MESO_MARKUP_PERCENT
FEE = GameConstants.OPTIMIZE_CONVENIENCE_FEE_PERCENT


def spend_v_points():
    # builds from before the V point spender lack the method
    if not hasattr(V271Matrix, "spendPoints"):
        return "Spending V points arrives with the next server update."
    return V271Matrix.spendPoints(chr)


def pay_fee():
    """The run's convenience fee, a share of the mesos carried now; False if the player says no"""
    fee = AutoGearModule.convenienceFee(chr)
    if fee <= 0:
        return True
    if not sm.sendAskYesNo("This run's convenience fee is #b{:,}#k mesos ({}% of the #b{:,}#k mesos you carry). "
                           "Go ahead?".format(fee, FEE, chr.getMoney())):
        return False
    AutoGearModule.chargeConvenienceFee(chr)
    return True


def reset_costs():
    """Mesos of one meso reset on each worn item that can be cubed (main and bonus), with the markup"""
    costs = []
    for bonus in (False, True):
        for e in CubingModule.getCandidates(chr, bonus):
            c = CubingModule.getMesoResetCost(chr, e, bonus)
            if c >= 0:
                costs.append(AutoEnhanceModule.applyMarkup(c, MARKUP))
    return costs


def ask_cubes():
    fee = AutoGearModule.cubeFeePerRound(chr)
    costs = reset_costs()
    if CubingModule.getMesoResetCost(chr, first_cube_item(), False) >= 0 and costs:
        after = ("then the Enhance window's #bmeso reset#k at its price for each item's rank ({}% more here, no "
                 "fee): #r{:,}#k to #r{:,}#k mesos a roll on your items. Items are cubed one after another, so "
                 "the first ones can use up all your mesos".format(MARKUP, min(costs), max(costs)))
        tax = "two meso resets of that item at its rank, in mesos"
    else:
        after = "then NX (#b{:,}#k NX a cube, #b{:,}#k for bonus potential; you have #b{:,}#k NX)".format(
            CubingModule.getCubeNx(False), CubingModule.getCubeNx(True), chr.getNX())
        tax = "#b{:,}#k NX an item (#b{:,}#k for bonus potential)".format(
            AutoGearModule.cubeRevertTax(False), AutoGearModule.cubeRevertTax(True))
    return sm.sendAskNumber("How many rolls may I use #bper item#k at most? Your own cubes go first, {}.\r\n"
                            "Each of your cubes also pays the Enhance window's meso fee for its item, {}% more here: "
                            "#b{:,}#k mesos for one cube on each of your items. You have #b{:,}#k mesos.\r\n\r\n"
                            "Items that already have 2 or 3 good lines are left alone unless you ask.\r\n\r\n"
                            "When I try for better lines and may have to give the old ones back, that costs a "
                            "#brevert tax#k of {}.".format(after, MARKUP, fee, chr.getMoney(), tax), 20, 0, 150)


def first_cube_item():
    cands = CubingModule.getCandidates(chr, False)
    return None if cands.isEmpty() else cands.get(0)


def ask_recube():
    return sm.sendAskYesNo("Some of your items may already have 2 or 3 good lines. Should I cube those too? "
                           "I'd try for better lines and keep the old ones if it doesn't work out.")


def cube(per_item, recube):
    run = AutoGearModule.cubeAll(chr, per_item, max(1, per_item // 2), per_item, recube)
    res = ("{} roll(s) ({:,} mesos of meso resets, {:,} mesos in cube fees, {:,} mesos of revert tax; {:,} NX, "
           "{:,} NX of it revert tax), {} goal(s) reached.").format(
        run.cubes, run.resetMesos, run.mesoFee, run.taxMesos, run.nx, run.taxNx, run.goalsMet)
    costs = reset_costs()
    if run.resetMesos > 0 and costs and chr.getMoney() < max(costs):
        res += " Stopped early on some items: not enough mesos left for another meso reset."
    return res


def confirm_cubes(per_item):
    """Yes when the rolls asked for can't cost more than the mesos carried, or the player accepts that"""
    costs = reset_costs()
    if not costs:
        return True
    most = sum(costs) * per_item
    if most <= chr.getMoney():
        return True
    return sm.sendAskYesNo("At #b{}#k rolls an item, meso resets could cost up to #r{:,}#k mesos, more than the "
                           "#b{:,}#k you carry. I'll stop when the mesos run out, so the first items may get all of "
                           "them. Go ahead?".format(per_item, most, chr.getMoney()))


def flame():
    cost = AutoGearModule.maxFlameMesos(chr, AutoGearModule.FLAMES_PER_ITEM)
    tax = AutoGearModule.maxFlameTax(chr)
    flame_name = "Eternal Rebirth Flames"
    if AutoEnhanceModule.getMesoFlameItemId() != 0:
        flame_name = "meso flames (the Enhance window's Reset)"
    if not sm.sendAskYesNo("I'll use up to #b{}#k {} on each worn item and keep the best result, "
                           "or the old flame if none beats it, at #b{:,}#k mesos a flame. That's #b{:,}#k mesos at "
                           "most, with a revert tax of #b{:,}#k mesos an item (#b{:,}#k in all) included. "
                           "Go ahead?".format(AutoGearModule.FLAMES_PER_ITEM, flame_name, AutoGearModule.flameMesos(chr), cost,
                                              AutoGearModule.flameRevertTax(), tax)):
        return None
    return "{} item(s) got a better flame.".format(AutoGearModule.reflameAll(chr, AutoGearModule.FLAMES_PER_ITEM))


choice = sm.sendNext("I can take care of your gear and stats for you. Spell Traces, cubes and NX cost what they "
                     "would by hand; the mesos for Star Force, flames and cube fees cost #r{}% more#k ({} times the "
                     "by-hand price). Each run that costs mesos also takes a convenience fee of #r{}%#k of the mesos "
                     "you carry (#b{:,}#k mesos now).\r\n#b".format(MARKUP, (MARKUP + 100) // 100, FEE,
                                                                 AutoGearModule.convenienceFee(chr)) +
                     "#L0#Do everything below#l\r\n"
                     "#L1#Equip my best gear#l\r\n"
                     "#L2#Scroll and Star Force my worn gear (to full stars)#l\r\n"
                     "#L3#Add missing potentials#l\r\n"
                     "#L4#Reroll weak flames#l\r\n"
                     "#L5#Cube toward good lines#l\r\n"
                     "#L6#Spend my Hyper Stat points#l\r\n"
                     "#L7#Spend my V points#l\r\n"
                     "#L9#Never mind#l")

if choice == 9 or choice < 0:
    sm.dispose()
elif choice in (0, 2, 3, 4) and not pay_fee():
    sm.dispose()
elif choice == 0:
    per_item = ask_cubes()
    lines = ["Gear: " + AutoGearModule.equipBest(chr).splitlines()[0],
             "Scrolls and stars: " + AutoGearModule.enhanceAll(chr),
             "Potentials added: {}".format(AutoGearModule.addPotentials(chr))]
    flamed = flame()
    if flamed is not None:
        lines.append("Flames: " + flamed)
    if per_item > 0 and confirm_cubes(per_item):
        lines.append("Cubes: " + cube(per_item, ask_recube()))
    lines.append("Hyper Stats: {} level(s) raised.".format(AutoGearModule.spendHyperStats(chr)))
    lines.append("V Matrix: " + spend_v_points())
    sm.sendSayOkay("\r\n".join(lines))
elif choice == 1:
    sm.sendSayOkay(AutoGearModule.equipBest(chr))
elif choice == 2:
    sm.sendSayOkay(AutoGearModule.enhanceAll(chr))
elif choice == 3:
    sm.sendSayOkay("{} potential(s) added.".format(AutoGearModule.addPotentials(chr)))
elif choice == 4:
    flamed = flame()
    if flamed is not None:
        sm.sendSayOkay(flamed)
elif choice == 5:
    per_item = ask_cubes()
    # fee only once the player has agreed to cube
    if per_item > 0 and confirm_cubes(per_item) and pay_fee():
        sm.sendSayOkay(cube(per_item, ask_recube()))
elif choice == 6:
    sm.sendSayOkay("{} Hyper Stat level(s) raised.".format(AutoGearModule.spendHyperStats(chr)))
elif choice == 7:
    sm.sendSayOkay(spend_v_points())
