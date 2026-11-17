# @enhance - every way to upgrade items in one place:
#  - Scroll & Star Force equipped items up to 22 stars (100% scrolls, Clean Slate or Best scrolls), see AutoEnhanceModule
#  - Add a potential or a bonus potential (mesos, never fails, three lines)
#  - Cube the potential (Red Cubes) or bonus potential (Bonus Potential Cubes) of worn items, once or with the
#    AutoCuber until a goal is met (owned cubes first, then the Enhance window's meso reset, or NX when that is
#    off), see CubingModule. This replaced @cube.
#  - Restore a traced (destroyed) item (Star Core, an identical item, or NX)
#  - Reroll flames (bonus stats: Powerful flame, or the window's meso flame with keep/replace). No Golden Hammers:
#    GMS retired them (every equip has the hammer slot built in, see EquipItemInfo.getFreshTuc)
#  - One equipped item at a time (all of the above for the item picked first); every function only targets
#    equipped (worn) items
from net.swordie.ms.client.character import AutoEnhanceModule
from net.swordie.ms.client.character import CubingModule
from net.swordie.ms.client.character.AutoEnhanceModule import Mode
from net.swordie.ms.client.character.items import Equip
from net.swordie.ms.constants import GameConstants
from net.swordie.ms.handlers.item import ItemHandlerModule

fmt = AutoEnhanceModule.format
TRACE_MARKUP = GameConstants.AUTO_ENHANCE_TRACE_MARKUP_PERCENT
MESO_MARKUP = GameConstants.AUTO_ENHANCE_MESO_MARKUP_PERCENT
STAR_CAP = AutoEnhanceModule.STAR_CAP


def odds_text():
    # whether scrolls / Star Force can fail for this character (settings scrolls_always_succeed, star_force_always_succeeds)
    if hasattr(AutoEnhanceModule, "getOddsText"):
        return AutoEnhanceModule.getOddsText(chr)
    return "Scrolls never fail."

MODES = [
    (Mode.NORMAL, "#b100% Spell Trace scrolls#k",
     "the usual Spell Trace cost"),
    (Mode.CLEAN_SLATE, "#bClean Slate#k: restore failed slots, then 100% scrolls",
     "{} Spell Traces per failed slot, plus the usual scroll cost".format(fmt(AutoEnhanceModule.CLEAN_SLATE_TRACES))),
    (Mode.BEST, "#bBest scrolls#k: the strongest scroll for each item (15% weapon, 30% others)",
     "{} times that scroll's Spell Traces".format(GameConstants.AUTO_ENHANCE_BEST_SCROLL_COST_MULTIPLIER)),
]



# ------------------------------------------------------------------------------------------------ potentials

def add_potential(bonus):
    # After each item, asks whether to add one to another item
    while add_one_potential(bonus):
        pass


def add_one_potential(bonus):
    """adds a (bonus) potential to one item; True if the player wants to do another"""
    what = "bonus potential" if bonus else "potential"
    cands = AutoEnhanceModule.getPotentialCandidates(chr, bonus)
    if cands.isEmpty():
        sm.sendSayOkay("None of your worn items can get a {} right now: they either have one already or can't "
                       "have one.".format(what))
        return False
    grade = "Rare" if bonus else "Epic"
    text = "Which worn item should get a {}? It never fails: {} with three lines.\r\n" \
           "Cost: #b{}#k mesos each. You have #b{}#k mesos.\r\n\r\n#b".format(
               what, grade, fmt(AutoEnhanceModule.getPotentialMesos(bonus)), fmt(chr.getMoney()))
    i = 0
    for e in cands:
        text += "#L{}##v{}# #t{}##l\r\n".format(i, e.getItemId(), e.getItemId())
        i += 1
    sel = sm.sendNext(text)
    if sel < 0 or sel >= cands.size():
        return False
    result = AutoEnhanceModule.addPotential(chr, cands.get(sel), bonus)
    if AutoEnhanceModule.getPotentialCandidates(chr, bonus).isEmpty():
        sm.sendSayOkay(result)
        return False
    return sm.sendAskYesNo("{}\r\n\r\nWould you like to add a {} to another item?".format(result, what))


# ------------------------------------------------------------------------------------------------ cubing

def fee_line(equip):
    fee = CubingModule.getCubeMesoFee(chr, equip)
    if fee <= 0:
        return "No meso fee to cube this item."
    return "Each of your cubes on this item also costs #b{}#k mesos (the Enhance window's fee). You have #b{}#k " \
           "mesos.".format(fmt(fee), fmt(chr.getMoney()))


def cube_supply(bonus, equip=None):
    cubeId = CubingModule.getCubeId(bonus)
    owned = "(Ari in the Free Market sells cubes for NX, cheaper in bulk.)\r\nYou have #b#c{}##k #v{}#".format(
        cubeId, cubeId)
    if equip is not None:
        reset = CubingModule.getMesoResetCost(chr, equip, bonus)
        if reset >= 0:
            return owned + ". Once you run out, each roll is a meso reset as in the Enhance window: #b{}#k mesos " \
                           "at its current rank (no fee). You have #b{}#k mesos.".format(fmt(reset), fmt(chr.getMoney()))
    elif CubingModule.getMesoResetCost(chr, first_cube_candidate(bonus), bonus) >= 0:
        return owned + ". Once you run out, each roll is a meso reset at the Enhance window's price for the item's rank."
    return owned + " and #b{}#k NX ({} NX per cube once you run out).".format(
        fmt(chr.getNX()), fmt(CubingModule.getCubeNx(bonus)))


def first_cube_candidate(bonus):
    cands = CubingModule.getCandidates(chr, bonus)
    return None if cands.isEmpty() else cands.get(0)


def show_result(equip, bonus, res, goalName):
    text = ""
    if goalName is not None:
        text += "#eGoal: {}#n - {}\r\n".format(goalName, "#gmet!#k" if res.goalMet else "#rnot met#k")
    if res.stopReason is not None and not res.goalMet:
        text += res.stopReason + "\r\n"
    text += "Rolls: #b{}#k ({} of your cubes, {} meso reset(s) for {} mesos, {} NX, {} mesos in cube fees{}).\r\n\r\n".format(
        res.cubesUsed, res.ownedCubesUsed, res.resetsUsed, fmt(res.resetMesos), fmt(res.nxSpent), fmt(res.mesoFee),
        ", {} fee(s) waived by a glass".format(res.feesWaived) if res.feesWaived > 0 else "")
    text += "#v{}# #t{}#:\r\n{}".format(equip.getItemId(), equip.getItemId(), CubingModule.describe(equip, bonus))
    return text


def pick_custom_goal():
    goal = {}
    stats = list(CubingModule.CUSTOM_STATS)
    while True:
        text = "Custom goal: every stat listed must be reached (All Stat % counts towards each stat %).\r\n" \
               "Current goal: #b{}#k\r\n\r\n".format(", ".join("{} {}".format(k, v) for k, v in goal.items()) or "nothing yet")
        text += "#L998##rStart cubing#k#l\r\n#L997#Legendary rank#l\r\n#b"
        for i in range(len(stats)):
            text += "#L{}#{}#l\r\n".format(i, stats[i])
        sel = sm.sendNext(text)
        if sel == 998:
            return goal
        if sel == 997:
            goal[CubingModule.RANK_KEY] = 4
            continue
        if sel < 0 or sel >= len(stats):
            return None
        value = sm.sendAskNumber("At least how much {}? (0 removes it)".format(stats[sel]), 0, 0, 1000)
        if value <= 0:
            goal.pop(stats[sel], None)
        else:
            goal[stats[sel]] = value


def do_cubing(bonus):
    # After each item, asks whether to cube another one
    while cube_one(bonus):
        pass


def another(question):
    return sm.sendAskYesNo(question)


def cube_one(bonus):
    """cubes one worn item; True if the player wants to cube another"""
    what = "bonus potential" if bonus else "potential"
    cands = CubingModule.getCandidates(chr, bonus)
    if cands.isEmpty():
        sm.sendSayOkay("None of your worn items have a {} to cube. Add one first with 'Add a {}'.\r\n"
                       "(Cubing here only works on worn items; to cube something in your inventory, use the cube "
                       "on it by hand.)".format(what, what))
        return False
    text = "Which worn item's {} do you want to cube?\r\n{}\r\n\r\n".format(what, cube_supply(bonus))
    i = 0
    for e in cands:
        text += "#L{}##v{}# #t{}# {}#l\r\n".format(i, e.getItemId(), e.getItemId(), CubingModule.rankName(CubingModule.getRank(e, bonus)))
        i += 1
    sel = sm.sendNext(text)
    if sel < 0 or sel >= cands.size():
        return False
    return cube_item(cands.get(sel), bonus, "Would you like to cube another worn item's {}?".format(what))


def cube_item(equip, bonus, more):
    """the cubing menu for one worn item; returns whether the player wants to go on"""
    while True:
        presets = CubingModule.getPresets(chr, equip, bonus)
        text = "#v{}# #t{}#:\r\n{}\r\n\r\n{}\r\n{}\r\n\r\n#b".format(
            equip.getItemId(), equip.getItemId(), CubingModule.describe(equip, bonus), cube_supply(bonus, equip),
            fee_line(equip))
        text += "#dPerfect presets use the highest prime lines this exact item can roll in v232. Three-prime-line " \
                "results are possible but exceptionally rare; the strong two-line presets are practical stops.#k\r\n\r\n"
        text += "#L0#Cube once#l\r\n"
        for i in range(presets.size()):
            text += "#L{}#AutoCube until: {}#l\r\n".format(i + 1, presets.get(i).name)
        text += "#L900#AutoCube until: a custom goal#l\r\n#L901#Done#l"
        sel = sm.sendNext(text)
        if sel == 0:
            res = CubingModule.cube(chr, equip, bonus, None, 1)
            if res.cubesUsed == 0:
                sm.sendSayOkay(res.stopReason)
                return False
            continue  # the menu shows the new lines
        goal = None
        goalName = None
        if 1 <= sel <= presets.size():
            goal = presets.get(sel - 1).wanted
            goalName = presets.get(sel - 1).name
        elif sel == 900:
            goal = pick_custom_goal()
            goalName = "custom"
            if goal is None or len(goal) == 0:
                continue
        else:
            return another(more)
        # A heads-up that the cubing runs behind the scenes and may lag a moment
        most = sm.sendAskNumber("At most how many cubes may I use? (Your own cubes are used first, then NX.)\r\n"
                                + fee_line(equip) + "\r\n\r\n"
                                "#rThere may be a short amount of lag while the cubing takes place behind the "
                                "scenes.#k",
                                100, 1, CubingModule.MAX_CUBES_PER_RUN)
        if most <= 0:
            continue
        res = CubingModule.cube(chr, equip, bonus, goal, most)
        if not sm.sendAskYesNo(show_result(equip, bonus, res, goalName) + "\r\n\r\nKeep cubing this item?"):
            return another(more)


def do_restore():
    # After each item, asks whether to restore another one
    while restore_one():
        pass


def restore_one():
    """restores one traced item; True if the player wants to restore another"""
    inv = chr.getEquippedInventory()  # worn items only, like the rest of @enhance
    prompt = "Which worn item would you like to restore?\r\n\r\n"
    found = False
    for item in inv.getSortedItems():
        if item.isVestige():
            nxCost = GameConstants.getVestigeRecoverCost(item)
            prompt += "#b#L{}# {} ({} NX)\r\n".format(item.getBagIndex(), sm.formatInlineItem(item), sm.formatNumber(nxCost))
            found = True
    if not found:
        sm.sendSayOkay("None of your worn items are traced.")
        return False
    item = inv.getItemBySlot(sm.sendNext(prompt))
    if item is None or not item.isVestige():
        return False
    nxCost = GameConstants.getVestigeRecoverCost(item)
    # as the Enhance window's Trace Restoration: a Star Core (set items) or an identical item; NX is this server's extra
    core = ItemHandlerModule.restoringStarCore(chr, item)
    twin = ItemHandlerModule.identicalIntactItem(chr, item)
    text = "How should I restore #v{0}# #t{0}#?\r\n\r\n#b".format(item.getItemId())
    if core is not None:
        text += "#L0#Use #v{0}# #t{0}# (you have {1})#l\r\n".format(core.getItemId(), core.getQuantity())
    if twin is not None:
        text += "#L1#Use an identical #t{0}# from your Equip inventory (it's used up)#l\r\n".format(item.getItemId())
    text += "#L2#Pay {} NX (you have {})#l".format(sm.formatNumber(nxCost), sm.formatNumber(chr.getNX()))
    how = sm.sendNext(text)
    if how == 0 and core is not None:
        chr.consumeItem(core.getItemId(), 1)
    elif how == 1 and twin is not None:
        chr.consumeItemFull(twin)
    elif how == 2:
        if nxCost > chr.getNX():
            sm.sendSayOkay("You don't have enough NX for this.")
            return False
        chr.deductNX(nxCost)
    else:
        return False
    item.removeVestige()
    item.updateToChar(chr)
    return sm.sendAskYesNo("#v{}# #t{}# is restored.\r\n\r\nWould you like to restore another traced item?".format(
        item.getItemId(), item.getItemId()))


def ask_star_option():
    """the Star Force window's destroy-rate slider or Safeguard: (slider 0..3, safeguard), or None to stop"""
    if not AutoEnhanceModule.starForceCanFail(chr):
        return (0, False)
    sel = sm.sendNext("Which Star Force setting? These are the Star Force window's own options; they only change "
                      "15-21 stars.\r\n\r\n#b"
                      "#L0#Standard (destroy-rate slider 1)#l\r\n"
                      "#L1#Safeguard at 15-17 stars: no destroy there, +200% mesos there#l\r\n"
                      "#L2#Slider 2: less destroy; x1.5 mesos at 15-17, x2 at 18-21, lower success at 18-21#l\r\n"
                      "#L3#Slider 3: even less destroy; x2.5 mesos at 15-17, x3.5 at 18-21#l\r\n"
                      "#L4#Slider 4: never destroys; x3 mesos at 15-17, x6.5 at 18-21#l")
    if sel == 0:
        return (0, False)
    if sel == 1:
        return (0, True)
    if 2 <= sel <= 4:
        return (sel - 1, False)
    return None


def scroll_and_star_force():
    while True:
        text = "How should I scroll your equipped items? Every way also Star Forces them, up to " + \
               "{} stars or to full stars (you pick next). ".format(STAR_CAP) + odds_text() + "\r\n" \
               "For the convenience, Spell Traces cost #r{}% more#k and Star Force mesos #r{}% more#k " \
               "than by hand.\r\n" \
               "You have #b{}#k Spell Traces and #b{}#k mesos.\r\n\r\n".format(
                   TRACE_MARKUP, MESO_MARKUP,
                   fmt(AutoEnhanceModule.getSpellTraceCount(chr)), fmt(chr.getMoney()))
        for i in range(len(MODES)):
            text += "#L{}#{} - {}#l\r\n".format(i, MODES[i][1], MODES[i][2])
        m = sm.sendNext(text)
        if m < 0 or m >= len(MODES):
            break
        mode = MODES[m][0]
        reach = sm.sendNext("How far should Star Force go?\r\n\r\n#b"
                            "#L0#Up to {} stars#l\r\n"
                            "#L1#Full stars (each item's maximum)#l".format(STAR_CAP))
        if reach < 0 or reach > 1:
            break
        option = ask_star_option()
        if option is None:
            break

        plans = AutoEnhanceModule.getPlans(chr, mode, reach == 1, option[0], option[1])
        if plans.isEmpty():
            if mode == Mode.CLEAN_SLATE:
                msg = "None of your equipped items have failed slots or anything left to scroll or Star Force."
            elif mode == Mode.BEST:
                msg = "None of your equipped items have free upgrade slots or stars left to add. " \
                      "(Slots already scrolled stay as they are; failed slots can be restored with Clean Slate.)"
            else:
                msg = "All your equipped items are already fully scrolled and Star Forced."
            if not sm.sendAskYesNo(msg + "\r\n\r\nWould you like to pick another way?"):
                break
            continue

        totalTraces = AutoEnhanceModule.getTotalTraces(plans)
        totalMesos = AutoEnhanceModule.getTotalMesos(plans)
        text = "{}\r\n\r\n#b".format(MODES[m][1])
        text += "#L0#All equipped items ({} Spell Traces, {} mesos)#l\r\n".format(fmt(totalTraces), fmt(totalMesos))
        i = 1
        for plan in plans:
            text += "#L{}##v{}# #t{}#: {}#l\r\n".format(i, plan.equip.getItemId(), plan.equip.getItemId(), plan.describe())
            i += 1
        sel = sm.sendNext(text)

        if sel == 0:
            chosen = plans
        elif 1 <= sel <= plans.size():
            chosen = [plans.get(sel - 1)]
        else:
            break

        traces = AutoEnhanceModule.getTotalTraces(chosen)
        mesos = AutoEnhanceModule.getTotalMesos(chosen)
        if len(chosen) == 1:
            what = "#v{}# #t{}# ({})".format(chosen[0].equip.getItemId(), chosen[0].equip.getItemId(), chosen[0].describe())
        else:
            what = "all {} items".format(len(chosen))
        if not sm.sendAskYesNo("Enhance {}?\r\n\r\nThis costs #b{}#k Spell Traces and #b{}#k mesos.".format(
                what, fmt(traces), fmt(mesos))):
            break

        result = AutoEnhanceModule.enhance(chr, chosen)
        if not sm.sendAskYesNo(result + "\r\n\r\nWould you like to enhance something else?"):
            break



# ------------------------------------------------------------------------------------------------ flames, hammers

def flame_item(equip):
    """Rerolls the item's flames until the player stops"""
    while True:
        if not AutoEnhanceModule.canFlame(equip):
            sm.sendNext("#v{0}# #t{0}# can't have bonus stats (flames).".format(equip.getItemId()))
            return
        meso = AutoEnhanceModule.getMesoFlameItemId()
        price = AutoEnhanceModule.getFlameMesos(True)
        text = "#v{0}# #t{0}#\r\nBonus stats now: {1}\r\nReroll them (flames)? It never fails. You have #b{2}#k " \
               "mesos.\r\n\r\n#b#L0#Powerful Rebirth Flame: random tiers, replaces right away ({3} mesos)#l\r\n".format(
                   equip.getItemId(), AutoEnhanceModule.describeFlame(equip), fmt(chr.getMoney()),
                   fmt(AutoEnhanceModule.getFlameMesos(False)))
        if meso != 0:
            text += "#L1#Meso flame (the Enhance window's Reset, rolls like #t{0}#): keep or replace ({1} mesos)#l\r\n" \
                    "#L2#Meso flame Reset x3: pick one of 3 results or keep yours ({2} mesos)#l\r\n".format(
                        meso, fmt(price), fmt(price * 3))
        else:
            text += "#L1#Eternal Rebirth Flame: the higher tiers, replaces right away ({} mesos)#l\r\n".format(fmt(price))
        sel = sm.sendNext(text + "#L9#Done#l")
        if sel == 0 or (sel == 1 and meso == 0):
            sm.sendNext(AutoEnhanceModule.flame(chr, equip, sel == 1))
        elif meso != 0 and (sel == 1 or sel == 2):
            meso_flame(equip, 3 if sel == 2 else 1)
        else:
            return


def meso_flame(equip, count):
    """the window's meso flame: count results on copies, then keep one or the old bonus stats"""
    problem = AutoEnhanceModule.mesoFlameProblem(chr, equip, count)
    if problem is not None:
        sm.sendNext(problem)
        return
    results = AutoEnhanceModule.rollMesoFlames(chr, equip, count)
    if results is None:
        return
    text = "#v{0}# #t{0}#\r\nBonus stats now: {1}\r\n\r\n#b".format(equip.getItemId(),
                                                                       AutoEnhanceModule.describeFlame(equip))
    for i in range(results.size()):
        text += "#L{}#Take result {}: {}#l\r\n".format(i, i + 1, AutoEnhanceModule.describeFlame(results.get(i)))
    sel = sm.sendNext(text + "#L9#Keep the current bonus stats#l")
    if 0 <= sel < results.size():
        AutoEnhanceModule.keepFlame(chr, equip, results.get(sel))


def pick_worn(question, ok):
    """a worn item (not cash) for which ok(equip) holds, or None"""
    items = [it for it in chr.getEquippedInventory().getItems()
             if isinstance(it, Equip) and not it.isCash() and ok(it)]
    items.sort(key=lambda it: it.getBagIndex())
    if len(items) == 0:
        sm.sendSayOkay("None of your worn items can take that right now.")
        return None
    text = question + "\r\n\r\n#b"
    for i in range(len(items)):
        text += "#L{}##v{}# #t{}##l\r\n".format(i, items[i].getItemId(), items[i].getItemId())
    sel = sm.sendNext(text)
    if sel < 0 or sel >= len(items):
        return None
    return items[sel]


def one_item():
    """One worn item at a time: pick it, then everything @enhance can do to it"""
    items = [it for it in chr.getEquippedInventory().getItems()
             if isinstance(it, Equip) and not it.isCash()]
    items.sort(key=lambda it: it.getBagIndex())
    if len(items) == 0:
        sm.sendSayOkay("You aren't wearing anything @enhance can work on.")
        return
    text = "Which worn item?\r\n\r\n#b"
    for i in range(len(items)):
        text += "#L{}##v{}# #t{}##l\r\n".format(i, items[i].getItemId(), items[i].getItemId())
    sel = sm.sendNext(text)
    if sel < 0 or sel >= len(items):
        return
    equip = items[sel]
    while True:
        text = "#v{}# #t{}#\r\nWhat should I do to it?\r\n\r\n#b".format(equip.getItemId(), equip.getItemId())
        text += "#L0#Scroll & Star Force it#l\r\n"
        text += "#L1#Add a potential ({} mesos)#l\r\n".format(fmt(AutoEnhanceModule.getPotentialMesos(False)))
        text += "#L2#Add a bonus potential ({} mesos)#l\r\n".format(fmt(AutoEnhanceModule.getPotentialMesos(True)))
        text += "#L3#Cube its potential#l\r\n#L4#Cube its bonus potential#l\r\n"
        text += "#L5#Reroll its flames (bonus stats)#l\r\n#L9#Done#l"
        sel = sm.sendNext(text)
        if sel == 0:
            text = "How should I scroll it? Star Force goes up to {} stars too (higher only by hand, in the " \
                   "Enhance window). ".format(STAR_CAP) + odds_text() + "\r\n" \
                   "You have #b{}#k Spell Traces and #b{}#k mesos.\r\n\r\n".format(
                       fmt(AutoEnhanceModule.getSpellTraceCount(chr)), fmt(chr.getMoney()))
            for i in range(len(MODES)):
                text += "#L{}#{} - {}#l\r\n".format(i, MODES[i][1], MODES[i][2])
            m = sm.sendNext(text)
            if m < 0 or m >= len(MODES):
                continue
            option = ask_star_option()
            if option is None:
                continue
            plan = AutoEnhanceModule.getPlan(chr, equip.getBagIndex(), MODES[m][0], False, option[0], option[1])
            if plan is None:
                sm.sendNext("There's nothing left to do to it that way.")
                continue
            if sm.sendAskYesNo("{}: {}\r\n\r\nThis costs #b{}#k Spell Traces and #b{}#k mesos. Go ahead?".format(
                    MODES[m][1], plan.describe(), fmt(AutoEnhanceModule.getTotalTraces([plan])),
                    fmt(AutoEnhanceModule.getTotalMesos([plan])))):
                sm.sendNext(AutoEnhanceModule.enhance(chr, [plan]))
        elif sel == 1 or sel == 2:
            bonus = sel == 2
            if not AutoEnhanceModule.getPotentialCandidates(chr, bonus).contains(equip):
                sm.sendNext("It can't get a {} (it already has one, or can't have one).".format(
                    "bonus potential" if bonus else "potential"))
                continue
            sm.sendNext(AutoEnhanceModule.addPotential(chr, equip, bonus))
        elif sel == 3 or sel == 4:
            bonus = sel == 4
            if not CubingModule.getCandidates(chr, bonus).contains(equip):
                sm.sendNext("It has no {} to cube yet: add one first.".format(
                    "bonus potential" if bonus else "potential"))
                continue
            cube_item(equip, bonus, "Back to this item's menu?")
        elif sel == 5:
            flame_item(equip)
        else:
            return


choice = sm.sendNext("What would you like to do?\r\n\r\n#b"
                     "#L6#Enhance one equipped item#l\r\n"
                     "#L0#Scroll & Star Force equipped items#l\r\n"
                     "#L1#Add a potential ({} mesos)#l\r\n"
                     "#L2#Add a bonus potential ({} mesos)#l\r\n"
                     "#L3#Cube a worn item's potential#l\r\n"
                     "#L4#Cube a worn item's bonus potential#l\r\n"
                     "#L7#Reroll a worn item's flames (bonus stats)#l\r\n"
                     "#L5#Restore a traced item#l".format(fmt(AutoEnhanceModule.getPotentialMesos(False)),
                                                         fmt(AutoEnhanceModule.getPotentialMesos(True))))
if choice == 0:
    scroll_and_star_force()
elif choice == 1:
    add_potential(False)
elif choice == 2:
    add_potential(True)
elif choice == 3:
    do_cubing(False)
elif choice == 4:
    do_cubing(True)
elif choice == 5:
    do_restore()
elif choice == 6:
    one_item()
elif choice == 7:
    e = pick_worn("Which worn item's flames (bonus stats)?", AutoEnhanceModule.canFlame)
    if e is not None:
        flame_item(e)
