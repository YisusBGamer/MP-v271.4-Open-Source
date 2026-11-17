# @menu - one menu for everything this server adds (also @go without a town, and the "Menu" entry of the Quick Move
# button). Helpers that are their own NPC chat (@enhance, the gear guide, ...) open right after this menu closes
# (MenuModule.openAfterMenu); the small ones answer here. See MenuModule.
# Pages are lists of (label, action): the numbers are made here, so an entry can show or hide by the character's
# level without renumbering anything.
from java.lang import Integer

from net.swordie.ms.client.character import GoldenLeafModule
from net.swordie.ms.client.character import JobAdvanceGearModule
from net.swordie.ms.client.character import LfgModule
from net.swordie.ms.client.character import MenuModule
from net.swordie.ms.client.character import NewPlayerModule
from net.swordie.ms.client.character import OnboardingModule
from net.swordie.ms.client.character import PlayStyleModule
from net.swordie.ms.client.character import QuickMoveModule
from net.swordie.ms.client.character import LevelUnlockModule
from net.swordie.ms.client.character.potential import CharacterPotentialMan
from net.swordie.ms.client.character.commands import PlayerCommands
from net.swordie.ms.handlers.item import PotentialCubes
from net.swordie.ms.client.rankings import RankingModule
from net.swordie.ms.client.rankings import RankingType
from net.swordie.ms.constants import CustomConstants
from net.swordie.ms.constants import GameConstants
from net.swordie.ms.constants import ItemConstants
from net.swordie.ms.constants import JobConstants
from net.swordie.ms.enums import Stat

BACK = 99


def back_line():
    return "\r\n#L{}##rBack#k#l".format(BACK)


def open_feature(name):
    # this NPC chat has to close before the next one can open; MenuModule opens it a moment later
    MenuModule.openAfterMenu(chr, name)
    sm.dispose()


def say(text):
    sm.sendSayOkay(text)


def service_on(name):
    # the server can switch a menu service off (MenuModule.isServiceOn); builds without the check have them all on
    return not hasattr(MenuModule, "isServiceOn") or MenuModule.isServiceOn(name)


def helper_on(check):
    # a new player helper's switch (NewPlayerModule.isNextHelperOn); on with builds that lack it
    return not hasattr(NewPlayerModule, check) or getattr(NewPlayerModule, check)()


def count_badge(n):
    # a small green count next to a label, only when there's something to see
    return "  #g({})#k".format(n) if n > 0 else ""


def page(build):
    # a page: build() gives (title, [(label, action), ...]) and is asked again after every action, so labels and
    # what's shown stay current; Back (or Esc) leaves it
    while True:
        title, entries = build()
        text = title + "\r\n\r\n"
        for i in range(len(entries)):
            text += "#L{}##b{}#k#l\r\n".format(i, entries[i][0])
        sel = sm.sendNext(text + back_line())
        if 0 <= sel < len(entries):
            entries[sel][1]()
        else:
            return


# ---------------------------------------------------------------------------------------------------------- travel


def recent_destinations():
    # (empty on a server build without MenuModule.getRecentDestinations)
    f = getattr(MenuModule, "getRecentDestinations", None)
    return list(f(chr)) if f else []


def training_map():
    f = getattr(NewPlayerModule, "getTrainingMap", None)
    return f(chr) if f else 0


def can_travel():
    reason = MenuModule.travelBlockReason(chr, True)  # the menu's travel has no cooldown
    if reason is not None:
        say(reason)
        return False
    return True


def warp(map_id, remember=True, portal=0):
    # the menu's own warps don't start @go's cooldown; towns and hunting maps go into Travel's "Recent"
    # (portal 0 is the map's spawn; other portal ids shift between client versions)
    if remember and getattr(MenuModule, "rememberDestination", None):
        MenuModule.rememberDestination(chr, map_id)
    sm.warp(map_id, portal)
    sm.dispose()


def go_to(map_id):
    if map_id == chr.getFieldID():
        say("You're already here.")
    elif can_travel():
        warp(map_id)


def free_market():
    if MenuModule.isInFreeMarket(chr):
        say("You are already in the Free Market.")
    elif can_travel():
        sm.setReturnField()  # the Free Market exit brings you back here
        sm.warp(MenuModule.FREE_MARKET)
        sm.dispose()


def hunt_label(map_id):
    return "Hunt at your level: #m{}# (Lv. {}-{})".format(
        map_id, QuickMoveModule.getHuntingMinLevel(map_id), QuickMoveModule.getHuntingMaxLevel(map_id))


def travel_page():
    if not can_travel():
        return

    def build():
        here = chr.getFieldID()
        entries = []
        mine = training_map()
        if mine != 0 and mine != here:
            entries.append((hunt_label(mine), lambda: go_to(mine)))
        recent = [m for m in recent_destinations() if m != here and m != mine][:3]
        for m in recent:
            entries.append(("Recent: #m{}#".format(m), lambda m=m: go_to(m)))
        entries.append(("Hunting grounds", hunting_page))
        entries.append(("Towns", towns_page))
        if service_on("bossEntrances"):
            entries.append(("Boss entrances", boss_entrance_page))
        entries.append(("Free Market", free_market))
        town = MenuModule.getNearestTown(chr)
        if town != 0 and town != here:
            entries.append(("Nearest town: #m{}#".format(town), lambda: go_to(town)))
        return "#e#bTravel#k#n  #d@go#k", entries
    page(build)


def towns_page():
    while True:
        text = "#e#bTowns#k#n\r\n\r\n"
        for i in range(len(QuickMoveModule.REGIONS)):
            if QuickMoveModule.getTowns(i).size() > 0:
                text += "#L{}##b{}#k#l\r\n".format(i, QuickMoveModule.REGIONS[i])
        text += back_line()
        region = sm.sendNext(text)
        if region < 0 or region >= len(QuickMoveModule.REGIONS):
            return
        towns = QuickMoveModule.getTowns(region)
        text = "#e#b{}#k#n\r\n\r\n".format(QuickMoveModule.REGIONS[region])
        for map_id in towns:
            here = "  #g(here)#k" if map_id == chr.getFieldID() else ""
            area = QuickMoveModule.getAreaName(map_id)  # Arcane River towns show their area
            label = "{} (#m{}#)".format(area, map_id) if area is not None else "#m{}#".format(map_id)
            text += "#L{}##b{}#k{}#l\r\n".format(map_id, label, here)
        text += back_line()
        map_id = sm.sendNext(text)
        if towns.contains(map_id) and map_id != chr.getFieldID():
            warp(map_id)


def hunting_page():
    NewPlayerModule.markStep(chr, "hunt")
    level = chr.getLevel()
    mine = QuickMoveModule.getBracketForLevel(level)
    while True:
        text = "#e#bHunting Grounds#k#n  #d@hunt#k\r\nYou: Lv. {}\r\n\r\n".format(level)
        for i in range(QuickMoveModule.getBracketCount()):
            if QuickMoveModule.getHuntingMaps(i).size() > 0:
                you = "  #g(your level)#k" if i == mine else ""
                text += "#L{}##b{}#k{}#l\r\n".format(i, QuickMoveModule.getBracketName(i), you)
        text += back_line()
        bracket = sm.sendNext(text)
        if bracket < 0 or bracket >= QuickMoveModule.getBracketCount():
            return
        if not hunting_bracket_page(bracket, level):
            return


def hunting_bracket_page(bracket, level):
    maps = QuickMoveModule.getHuntingMaps(bracket)
    text = "#e#b{}#k#n\r\n#gGreen#k: your level   #rRed#k: too strong\r\n".format(
        QuickMoveModule.getBracketName(bracket))
    if bracket >= 4:
        text += "#dNeeds Arcane / Sacred Symbols.#k\r\n"
    text += "\r\n"
    for map_id in maps:
        lo = QuickMoveModule.getHuntingMinLevel(map_id)
        hi = QuickMoveModule.getHuntingMaxLevel(map_id)
        if lo <= level <= hi:
            color = "#g"
        elif level < lo:
            color = "#r"
        else:
            color = "#b"
        here = "  #g(here)#k" if map_id == chr.getFieldID() else ""
        n = MenuModule.countPlayersInMap(chr, map_id)
        crowd = "  #d({} player{})#k".format(n, "" if n == 1 else "s") if n > 0 else ""
        text += "#L{}#{}Lv. {}-{}#k  #m{}#{}{}#l\r\n".format(map_id, color, lo, hi, map_id, here, crowd)
    text += back_line()
    map_id = sm.sendNext(text)
    if maps.contains(map_id) and map_id != chr.getFieldID():
        warp(map_id)
    # only Back returns to the bracket list; a closed window ends the menu
    return map_id == BACK


BOSS_ENTRY_FEATURES = {410000670: "serenEntry", 874000005: "akechiEntry"}
# boss maps where portal 1 is the door to the boss (script portal), so you arrive in front of it
BOSS_DOOR_PORTAL = (211042300, 401060000, 401060399, 105200000, 211070000, 272020110, 105300303, 350060300, 160000000)


def boss_entrance_page():
    # sorted by level; the ones far above the character wait behind "Show all"
    level = chr.getLevel()
    order = sorted(range(QuickMoveModule.getBossCount()), key=lambda i: QuickMoveModule.getBossLevel(i))
    show_all = [False]

    def build():
        entries = []
        hidden = 0
        for i in order:
            req = QuickMoveModule.getBossLevel(i)
            if req > level + 10 and not show_all[0]:
                hidden += 1
                continue
            color = "#g" if level >= req else "#r"
            entries.append(("{}{}  (Lv. {}+)".format(color, QuickMoveModule.getBossName(i), req),
                            lambda i=i: enter_boss(i)))
        if hidden > 0:
            entries.append(("#dShow all bosses ({} more)".format(hidden), lambda: show_all.__setitem__(0, True)))
        return "#e#bBoss entrances#k#n", entries
    page(build)


def enter_boss(i):
    if chr.getLevel() < QuickMoveModule.getBossLevel(i):
        say("{1} needs level {0}.".format(QuickMoveModule.getBossLevel(i), QuickMoveModule.getBossName(i)))
        return
    if not can_travel():
        return
    boss_map = QuickMoveModule.getBossMap(i)
    if boss_map in BOSS_ENTRY_FEATURES:
        # bosses without a visible entry NPC: open their entry right away (it does the warp itself)
        open_feature(BOSS_ENTRY_FEATURES[boss_map])
    else:
        warp(boss_map, False, 1 if boss_map in BOSS_DOOR_PORTAL else 0)


# ----------------------------------------------------------------------------------------------------------- shops

SHOPS = [
    # (text, shop id, has an NPC in a town: hidden while the server turns the menu's shops off)
    ("Regular Shop", 9001212, True),  # Collector, Free Market entrance
    ("Legion Shop", 9010111, False),
    ("Android Shop", 1012123, True),  # Lauren, Henesys
    ("Crafted goods: Meister, alchemy, Android Hearts", 9031003, True),  # Gere, Ardentmill
]

gml_id = ItemConstants.GOLDEN_MAPLE_LEAF
mlg_id = ItemConstants.MAPLE_LEAF_GOLD




def open_shop(shop_id):
    sm.openShop(shop_id)
    sm.dispose()


def shops_page():
    def build():
        all_shops = service_on("shopList")
        entries = []
        if service_on("coins"):
            entries.append(("Swordie Coin Shop  #d@coinshop#k", lambda: open_feature("coins")))
        entries += [(name, lambda s=shop: open_shop(s)) for (name, shop, town) in SHOPS if all_shops or not town]
        if JobAdvanceGearModule.hasGear(chr):
            entries.append(("Job advancement gear  #d@jobgear#k", job_gear_page))
        if service_on("sell"):
            entries.append(("Sell items  #d@sell#k", lambda: open_feature("sell")))
        if service_on("ari"):
            entries.append(("Ari's NX shop  #d@ari#k", lambda: open_feature("ari")))
        # builds before the Heroic Cube Shop have no such feature
        if hasattr(MenuModule, "isHeroicCubeShopOn") and MenuModule.isHeroicCubeShopOn():
            entries.append(("Heroic Cube Shop  #d@cubeshop#k", lambda: open_feature("heroicCubes")))
        if service_on("pet"):
            entries.append(("Pets  #d@petshop#k", lambda: open_feature("pet")))
        entries.append(("Maple Leaf Gold exchange  #d@mlg#k", exchange_page))
        title = "#e#bShops#k#n  #d@shop#k"
        if not all_shops:
            title += "\r\n#dThe other shops are at the Free Market entrance and in town.#k"
        return title, entries
    page(build)


def job_gear_page():
    # what the class's job advancement quests give, once per character (for Express and @job, who skip them)
    def build():
        entries = []
        for item_id in JobAdvanceGearModule.getClassGear(chr):
            lv = JobAdvanceGearModule.getReqLevel(item_id)
            label = "#v{0}# #t{0}# (Lv. {1})".format(item_id, lv)
            if JobAdvanceGearModule.isBought(chr, item_id):
                label += "  #d(bought)#k"
            elif not JobAdvanceGearModule.isUnlocked(chr, item_id):
                label += "  #d(at your Lv. {} job advancement)#k".format(lv)
            else:
                label += "  #r{}#k mesos".format(sm.formatNumber(str(JobAdvanceGearModule.getPrice(item_id))))
            entries.append((label, lambda i=item_id: say(JobAdvanceGearModule.buy(chr, i))))
        title = ("#e#bJob advancement gear#k#n\r\n"
                 "#dWhat your class's job advancement quests hand out. Each piece once per character.#k")
        return title, entries
    page(build)


def exchange_page():
    # Golden Maple Leaves are bought and exchanged at @leaves
    sel = sm.sendNext("#e#bMaple Leaf Gold exchange#k#n\r\n\r\n"
                      "#L2##bSell #t{0}##k#l\r\n"
                      "#L3##bBuy #t{0}##k#l\r\n".format(mlg_id) + back_line())
    if sel < 2 or sel > 3:
        return
    is_buy = sel == 1 or sel == 3
    is_meso = sel == 0 or sel == 1
    item_id = gml_id if is_meso else mlg_id
    money = CustomConstants.GML_WORTH if is_meso else CustomConstants.MLG_WORTH
    if sel == 0:  # Leaves go back to mesos at the Leaf Shop's rate
        money = GoldenLeafModule.LEAF_SELL_MESOS
    if is_buy:  # buying adds the tax
        money += (money * (CustomConstants.GML_TAX if is_meso else CustomConstants.MLG_TAX)) / 100

    if sel == 0:
        most = min(GoldenLeafModule.maxExchange(chr), 99)
        if most < 1 and sm.getQuantityOfItem(item_id) > 0:
            say(GoldenLeafModule.exchangeToMesos(chr, 1))
            return
        question = "#v{1}# sells for #r{0}#k mesos each. How many?".format(
            sm.formatNumber(str(money)), gml_id)
    elif sel == 1:
        most = min(sm.getMesos() / money, 9999)
        question = "#v{}# costs #r{}#k mesos each. How many?".format(
            gml_id, sm.formatNumber(str(money)))
    elif sel == 2:
        most = min(sm.getQuantityOfItem(item_id), 2147)
        question = "#v{1}# sells for #r{0}#k NX each. How many?".format(
            sm.formatNumber(str(money)), mlg_id)
    else:
        most = min(chr.getNX() / money, 9999)
        question = "#v{}# costs #r{}#k NX each. How many?".format(
            mlg_id, sm.formatNumber(str(money)))
    if most < 1:
        say("You don't have enough.")
        return
    n = sm.sendAskNumber(question, 1, 1, most)
    if not 0 < n <= most:
        return
    total = money * n
    if is_buy:
        if not sm.canHold(item_id, n):
            say("Make room in your inventory first.")
            return
        if is_meso:
            old_mesos = sm.getMesos()
            sm.deductMesos(total)
            sm.log.info("[{}] Giving {} GML, old mesos = {}, new mesos = {}, cost = {}".format(
                chr.getName(), n, sm.formatNumber(str(old_mesos)), sm.formatNumber(str(sm.getMesos())),
                sm.formatNumber(str(total))))
        else:
            chr.deductNX(total)
        sm.giveItem(item_id, n)
    else:
        if is_meso:
            say(GoldenLeafModule.exchangeToMesos(chr, n))
            return
        if chr.getNX() + total > Integer.MAX_VALUE:
            say("That's more NX than you can hold.")
            return
        sm.consumeItem(item_id, n)
        chr.addNx(total)
    say("Done!")


# ------------------------------------------------------------------------------------------------- my character


def character_page():
    def build():
        level = chr.getLevel()
        ap = chr.getStat(Stat.ap)
        entries = [
            ("Job path  #d@job#k", lambda: open_feature("job")),
            ("Ability points" + count_badge(ap), ability_page),
            ("Stats", lambda: say(MenuModule.getStatsText(chr))),
            ("Play style: {}".format(PlayStyleModule.getName(chr)), play_style_page),
            ("Link skills & Legion", links_page),
        ]
        if level >= 200 and hasattr(MenuModule, "getBurningBlocker") and service_on("vmatrix"):
            entries.append(("V Matrix  #d@vmatrix#k", lambda: open_feature("vmatrix")))
        if hasattr(MenuModule, "getBurningBlocker") and (chr.getAvatarData().getCharacterStat().isBurning()
                                                         or level < GameConstants.BURNING_CHAR_MAX_LEVEL):
            entries.append(("Burning character  #d@burning#k", burning_page))
        if not chr.getPotentials().isEmpty():
            entries.append(("Inner Ability", inner_ability_page))
        if level >= GameConstants.REBIRTH_MIN_LEVEL and service_on("rebirth"):
            entries.append(("Rebirth  #d@rebirth#k", lambda: open_feature("rebirth")))
        entries += [
            ("Hair, face & skin  #d@style#k", lambda: open_feature("style")),
            ("Transmog", lambda: open_feature("transmog")),
            ("Settings", lambda: open_feature("settings")),
            ("Others' effects: {}".format("off" if chr.isHidingOtherEffects() else "on"),
             lambda: say(MenuModule.toggleEffects(chr))),
        ]
        return "#e#bCharacter#k#n  #d@char#k", entries
    page(build)


def burning_page():
    from net.swordie.ms.client.character import BurningModule
    if BurningModule.getEndBlocker(chr) is None:
        if sm.sendAskYesNo("#e#bBurning character#k#n\r\n{} is burning: {}\r\n\r\n#rEnd Burning for good?#k "
                           "The levels you gained stay; the extra levels stop and can't be started again on this "
                           "character.".format(chr.getName(),
                                               BurningModule.describe(chr.getAvatarData().getCharacterStat()))):
            result = BurningModule.endBurning(chr)
            say(result if result is not None else "Burning ended.")
        return
    blocker = MenuModule.getBurningBlocker(chr)
    if blocker is not None:
        say(blocker)
    elif sm.sendAskYesNo("#e#bBurning character#k#n\r\n{}\r\n\r\nMake #b{}#k a Burning character?".format(
            MenuModule.getBurningRules(), chr.getName())):
        say(MenuModule.makeBurning(chr))


GRADE_COLORS = {"Rare": "#b", "Epic": "#d", "Unique": "#r", "Legendary": "#g"}


def inner_ability_page():
    # Inner Ability (Hidden Ability) rerolls for Honor EXP, the same as the Inner Ability window's
    locked = []
    while True:
        if chr.getPotentials().isEmpty():
            say("Your #bInner Ability#k opens at #bLv. {}#k.".format(LevelUnlockModule.INNER_ABILITY_LEVEL))
            return
        cpm = chr.getPotentialMan()
        grade = cpm.getGradeName()
        cost = cpm.getRerollCost(len(locked))
        text = "#e#bInner Ability#k#n  {}{}#k\r\nHonor EXP: #b{:,}#k\r\n\r\n".format(
            GRADE_COLORS.get(grade, "#b"), grade, chr.getHonorExp())
        for line in range(1, 4):
            text += "{}{}. {}#k{}\r\n".format("#e" if line in locked else "", line,
                                               CharacterPotentialMan.describeLine(chr, line),
                                               "#n  (locked)" if line in locked else "")
        text += "\r\n"
        if cost <= chr.getHonorExp():
            text += "#L0##bReroll#k  ({:,} Honor EXP".format(cost)
        else:
            text += "#L0##kReroll  (#r{:,}#k Honor EXP".format(cost)
        chance = cpm.getRankUpChance()
        text += ", {}% rank up)#l\r\n".format(chance) if chance > 0 else ")#l\r\n"
        text += "#L1##bLock lines#k{}#l\r\n".format("  ({} locked)".format(len(locked)) if locked else "")
        text += back_line()
        sel = sm.sendNext(text)
        if sel == 0:
            from java.util import HashSet
            keep = HashSet()
            for line in locked:
                keep.add(Integer(line))
            error = cpm.rerollWithHonor(keep)
            if error is not None:
                say(error)
        elif sel == 1:
            lock_text = "#e#bLock lines#k#n\r\nLocked lines keep their ability when you reroll (locking costs more " \
                        "Honor EXP from Unique). Up to 2 lines.\r\n\r\n"
            for line in range(1, 4):
                lock_text += "#L{}#{} {}. {}#l\r\n".format(line, "#e[locked]#n" if line in locked else "[ ]", line,
                                                          CharacterPotentialMan.describeLine(chr, line))
            lock_text += back_line()
            pick = sm.sendNext(lock_text)
            if pick in locked:
                locked.remove(pick)
            elif 1 <= pick <= 3:
                if len(locked) >= 2:
                    say("You can lock up to 2 lines.")
                else:
                    locked.append(pick)
        else:
            return


def play_style_page():
    if hasattr(PlayStyleModule, "asks") and not PlayStyleModule.asks(chr):
        # the server sets the play style (new player mode, or job paths off)
        say("#e#bPlay style: {}#k#n\r\n{}".format(PlayStyleModule.getName(chr), PlayStyleModule.getModeText(chr)))
        return
    name = PlayStyleModule.getName(chr)
    if name == "Classic":
        if sm.sendAskYesNo("#e#bPlay style: Classic#k#n\r\nJob quests advance your job.\r\n\r\n"
                           "Switch to #bExpress#k? Pick a job path, advancements are automatic, and you can skip "
                           "Arcane River's story.\r\n\r\n#r" + GameConstants.JOB_PATH_WARNING + "#k"):
            opens = PlayStyleModule.switchTo(chr, True)
            say("Play style: #bExpress#k." + (" Pick your job path next." if opens else ""))
            if opens:
                open_feature("job")
    elif name == "Express":
        if not PlayStyleModule.canSwitchToClassic(chr):
            say("#e#bPlay style: Express#k#n\r\nAdvancements are automatic. Spiegelmann offers to skip "
                "Arcane River's story.")
        elif sm.sendAskYesNo("#e#bPlay style: Express#k#n\r\nNo job path picked yet.\r\n\r\n"
                             "Switch back to #bClassic#k (job quests)?"):
            PlayStyleModule.switchTo(chr, False)
            say("Play style: #bClassic#k.")
    else:
        open_feature("playStyle")


def ability_page():
    while True:
        text = "#e#bAbility points#k#n  #d@ap#k\r\n#b{}#k left\r\n\r\n".format(chr.getStat(Stat.ap))
        for i in range(4):
            text += "#L{}##b{}#k: {}#l\r\n".format(i, MenuModule.getAbilityName(i), MenuModule.getAbility(chr, i))
        text += back_line()
        stat = sm.sendNext(text)
        if stat < 0 or stat > 3:
            return
        name = MenuModule.getAbilityName(stat)
        can_add = MenuModule.getAddableAbility(chr, stat)
        can_take = MenuModule.getReturnableAbility(chr, stat)
        if can_add <= 0 and can_take <= 0:
            say("No points to add, and {} is at its minimum ({}).".format(name, MenuModule.MIN_ABILITY_STAT))
            continue
        options = "#e#b{}#k#n: {}\r\n\r\n".format(name, MenuModule.getAbility(chr, stat))
        if can_add > 0:
            options += "#L0##bAdd#k  (up to {})#l\r\n".format(can_add)
        if can_take > 0:
            options += "#L1##bRemove#k  (up to {})#l\r\n".format(can_take)
        options += back_line()
        way = sm.sendNext(options)
        if way == 0 and can_add > 0:
            n = sm.sendAskNumber("Add how many to {}?".format(name), can_add, 1, can_add)
            say(MenuModule.allocateAbility(chr, stat, n))
        elif way == 1 and can_take > 0:
            n = sm.sendAskNumber("Remove how many from {}?".format(name), 1, 1, can_take)
            say(MenuModule.allocateAbility(chr, stat, -n))


# ------------------------------------------------------------------------------------------------------------ gear


def gear_page():
    def build():
        entries = [("Equip better items I'm carrying  #d@upgrades#k", lambda: gear_feature("upgrades"))]
        if service_on("enhance"):
            entries.append(("Enhance worn gear: scrolls, stars, cubes  #d@enhance#k", lambda: open_feature("enhance")))
        if service_on("optimize"):
            entries.append(("Optimize my gear  #d@optimize#k", lambda: open_feature("optimize")))
        entries += [
            ("Gear guide: empty slots, where to farm  #d@gear#k", lambda: gear_feature("gearGuide")),
            ("Cube odds", lambda: say(PotentialCubes.oddsText())),
            ("How do I get stronger?", lambda: open_feature("guide")),
        ]
        return "#e#bGear#k#n", entries
    page(build)


def gear_feature(name):
    NewPlayerModule.markStep(chr, "gear")
    open_feature(name)


# ---------------------------------------------------------------------------------------------------------- bosses


def bosses_page():
    def build():
        entries = [("Which bosses can I beat?", lambda: open_feature("bosses"))]
        if service_on("bossEntrances"):
            entries.append(("Boss entrances", lambda: boss_entrance_page() if can_travel() else None))
        entries.append(("Boss rankings", rankings_page))
        return "#e#bBosses#k#n  #d@bosses#k", entries
    page(build)


# ------------------------------------------------------------------------------------------------ social & rewards


def who_drops():
    query = sm.sendAskText("Item name (or part of it):", "", 2, 40)
    say(MenuModule.getWhoDropsText(chr, query))


def rankings_page():
    sel = sm.sendNext("#e#bRankings#k#n\r\n\r\n"
                      "#L0##bClass#k#l\r\n"
                      "#L1##bBoss#k#l\r\n"
                      "#L2##bFame#k#l\r\n" + back_line())
    if sel == 0:
        text = "Which class?\r\n\r\n"
        classes = []
        for k in JobConstants.CLASS_RANKING_MAP:
            classes.append(k.getRight())
            text += "#L{}##b{}#k#l\r\n".format(k.getRight(), k.getLeft())
        pick = sm.sendNext(text + back_line())
        if pick in classes:
            RankingModule.displayClassRankingsToChar(chr, pick)
            sm.dispose()
    elif sel == 1:
        text = "Which boss?\r\n\r\n"
        for t in RankingType.values():
            text += "#L{}##b{}#k#l\r\n".format(t.getVal(), str(t))
        t = RankingType.getByVal(sm.sendNext(text + back_line()))
        if t is not None:
            RankingModule.displayRankingsToChar(chr, t)
            sm.dispose()
    elif sel == 2:
        RankingModule.displayFameRankingsToChar(chr)
        sm.dispose()


def social_page():
    def build():
        gifts = chr.getFirstEnterRewards().size()
        entries = [("Looking for group" + count_badge(LfgModule.countOpen(chr)), lfg_page)]
        entries += [
            ("Gifts" + count_badge(gifts), lambda: open_feature("gifts")),
            ("Players in this map", lambda: say(MenuModule.getPlayersInMapText(chr))),
            ("Rankings", rankings_page),
        ]
        return "#e#bSocial & rewards#k#n", entries
    page(build)


def social_badge():
    n = LfgModule.countOpen(chr) + chr.getFirstEnterRewards().size()
    return count_badge(n)


# ------------------------------------------------------------------------------------------- storage & inventory


def storage_page():
    def build():
        entries = [("Open storage", open_storage)] if service_on("storage") else []
        entries += [
            ("Merge stacks", lambda: say(MenuModule.mergeStacks(chr))),
            ("Clear Cash Shop storage", clear_cash_storage),
        ]
        return "#e#bStorage & inventory#k#n", entries
    page(build)


def open_storage():
    NewPlayerModule.markStep(chr, "storage")
    open_feature("storage")


def clear_cash_storage():
    # moved here from @sell: deletes a range of items from the account's Cash Shop storage
    trunk = chr.getAccount().getTrunk()
    while True:
        items = list(trunk.getLocker())[:150]
        if len(items) == 0:
            say("Your Cash Shop storage is empty.")
            return
        text = "Delete from which item? (#bfirst#k)\r\n\r\n#b"
        for i in range(len(items)):
            text += "#L{}##i{}# #t{}##l\r\n".format(i, items[i].getItemId(), items[i].getItemId())
        first = sm.sendNext(text + back_line())
        if first < 0 or first >= len(items):
            return
        text = "Delete up to which item? (#blast#k)\r\n\r\n#b"
        for i in range(first, len(items)):
            text += "#L{}##i{}# #t{}##l\r\n".format(i, items[i].getItemId(), items[i].getItemId())
        last = sm.sendNext(text + back_line())
        if last < first or last >= len(items):
            return
        chosen = items[first:last + 1]
        icons = "".join("#i{}#".format(c.getItemId()) for c in chosen[:30])
        if not sm.sendAskYesNo("Delete {} item{}? #rThis can't be undone.#k\r\n\r\n{}".format(
                len(chosen), "" if len(chosen) == 1 else "s", icons)):
            return
        for c in chosen:
            trunk.removeCashItem(c)
        if not sm.sendAskYesNo("Deleted {} item{}. More?".format(len(chosen), "" if len(chosen) == 1 else "s")):
            return


# ---------------------------------------------------------------------------------------------------- info & help


def shortcut_lines():
    text = ""
    shortcuts = PlayerCommands.getEnabledShortcutList() if hasattr(PlayerCommands, "getEnabledShortcutList") \
        else PlayerCommands.getShortcutList()
    groups = list(PlayerCommands.SHORTCUT_GROUPS) if hasattr(PlayerCommands, "SHORTCUT_GROUPS") else [None]
    for g in groups:
        rows = [c for c in shortcuts if g is None or (len(c) > 2 and c[2] == g)]
        if rows and g is not None:
            text += "#e{}#n\r\n".format(g)
        for c in rows:
            text += "#b{}#k  {}\r\n".format(c[0], c[1])
    return text


def guide_pages():
    page = 0
    while True:
        count = OnboardingModule.getGuidePageCount()
        text = OnboardingModule.getGuidePageText(page) + "\r\n\r\n"
        if page < count - 1:
            text += "#L1##bNext: {}#k#l\r\n".format(OnboardingModule.getGuideTitle(page + 1))
        if page > 0:
            text += "#L2##bPrevious#k#l\r\n"
        sel = sm.sendNext(text + back_line())
        if sel == 1 and page < count - 1:
            page += 1
        elif sel == 2 and page > 0:
            page -= 1
        else:
            return


def commands_text():
    text = "#e#bCommands#k#n\r\n\r\n"
    commands = PlayerCommands.getEnabledHelpCommandList() if hasattr(PlayerCommands, "getEnabledHelpCommandList") \
        else PlayerCommands.getHelpCommandList()
    for c in commands:
        text += "#b{}#k  {}\r\n".format(c[0], c[1])
    return text + "\r\n#eShortcuts#n\r\n" + shortcut_lines()


def fix_arcane():
    result = MenuModule.fixQuests(chr)
    if result == "WARP":
        if can_travel():
            say("Taking you to Morass. #bUnexpected Enemy (1)#k starts there.")
            warp(MenuModule.getFixQuestWarpMap(), False)
    else:
        say(result)


def help_page():
    def build():
        level = chr.getLevel()
        entries = [
            ("Drops in this map", lambda: say(MenuModule.getMapDropsText(chr))),
            ("Who drops...?  #d@whodrops#k", who_drops),
            ("Commands", lambda: say(commands_text())),
            ("New player guide", guide_pages),
        ]
        if level <= NewPlayerModule.CHECKLIST_MAX_LEVEL:
            entries.append(("Onboarding checklist", checklist_page))
        entries.append(("I'm stuck  #d@stuck#k", lambda: open_feature("stuck")))
        if level >= 200 or JobConstants.isZero(chr.getJob()):
            entries.append(("Fix Arcane River quest", fix_arcane))
        return "#e#bInfo & help#k#n", entries
    page(build)


# ------------------------------------------------------------------------------------------- new player help

NAMELESS_TOWN = 450001000


def run_action(action):
    # what a suggestion (NewPlayerModule.getNextSteps) does when picked, here or on the main page
    if action == "gifts":
        open_feature("gifts")
    elif action == "checklist":
        checklist_page()
    elif action == "ap":
        ability_page()
    elif action == "job":
        open_feature("job")
    elif action.startswith("hunt:"):
        map_id = int(action[5:])
        if map_id > 0:
            go_to(map_id)
        elif can_travel():
            hunting_page()
    elif action == "hunt":
        if can_travel():
            hunting_page()
    elif action == "gear":
        gear_feature("gearGuide")
    elif action == "bosses":
        open_feature("bosses")
    elif action == "guide":
        open_feature("guide")
    elif action == "arcane":
        go_to(NAMELESS_TOWN)
    elif action == "links":
        links_page()
    elif action == "lfg":
        lfg_page()


def next_page():
    if not helper_on("isNextHelperOn"):
        say("That command is turned off on this server.")
        return
    while True:
        steps = NewPlayerModule.getNextSteps(chr)
        text = "#e#bWhat should I do next?#k#n  #d@next#k\r\n\r\n"
        if steps.size() == 0:
            text += "You're all set! Hunt or try a boss.\r\n"
        # plain notes (Rested EXP) first: a line without a link between linked lines is drawn over its neighbour
        notes = [steps.get(i)[0] for i in range(steps.size()) if not steps.get(i)[1]]
        for note in notes:
            text += "{}\r\n".format(note)
        if notes:
            text += "\r\n"
        for i in range(steps.size()):
            entry = steps.get(i)
            if entry[1]:
                text += "#L{}#{}#l\r\n".format(i, entry[0])
        sel = sm.sendNext(text + back_line())
        if sel < 0 or sel >= steps.size() or not steps.get(sel)[1]:
            return
        run_action(steps.get(sel)[1])


def checklist_page():
    sm.sendNext(NewPlayerModule.getChecklistText(chr) + back_line())


def links_page():
    page = 0
    while True:
        count = NewPlayerModule.getLinkHelperPageCount(chr)
        text = NewPlayerModule.getLinkHelperText(chr, page) + "\r\n"
        if page < count - 1:
            text += "#L1##bClasses I don't have#k#l\r\n" if page == 0 else "#L1##bNext page#k#l\r\n"
        if page > 0:
            text += "#L2##bPrevious page#k#l\r\n"
        sel = sm.sendNext(text + back_line())
        if sel == 1 and page < count - 1:
            page += 1
        elif sel == 2 and page > 0:
            page -= 1
        else:
            return


def lfg_page():
    while True:
        ids = LfgModule.getBoardIds(chr)
        text = "#e#bLooking for group#k#n  #d@lfg#k\r\n\r\n"
        others = [i for i in ids if i != chr.getId()]
        if len(others) == 0:
            text += "#dNo groups right now.#k\r\n"
        for n in range(len(ids)):
            if ids[n] != chr.getId():
                text += "#L{}#{}#l\r\n".format(100 + n, LfgModule.describe(chr, ids[n]))
        text += "\r\n"
        if LfgModule.hasPost(chr):
            text += "#L1##bEdit my post#k#l\r\n#L2##bRemove my post#k#l\r\n"
        else:
            text += "#L1##bPost my group#k#l\r\n"
        sel = sm.sendNext(text + back_line())
        if sel == 1:
            post_group()
        elif sel == 2:
            say(LfgModule.remove(chr))
        elif 100 <= sel < 100 + len(ids):
            leader = ids[sel - 100]
            if sm.sendAskYesNo("Join this group?\r\n\r\n" + LfgModule.describe(chr, leader)):
                say(LfgModule.join(chr, leader))
        else:
            return


def post_group():
    text = "What's it for?\r\n\r\n"
    for i in range(len(LfgModule.CATEGORIES)):
        text += "#L{}##b{}#k#l\r\n".format(i, LfgModule.CATEGORIES[i])
    category = sm.sendNext(text + back_line())
    if category < 0 or category >= len(LfgModule.CATEGORIES):
        return
    note = sm.sendAskText("Short note (e.g. \"Zakum, need 2\"):",
                          "", 0, LfgModule.NOTE_MAX)
    say(LfgModule.post(chr, category, note))


# ------------------------------------------------------------------------------------------------------- main menu

# (label, badge or None, page); a badge only shows when there's something new
PAGES = [
    ("Travel", None, travel_page),
    ("Character", None, character_page),
    ("Gear", None, gear_page),
    ("Bosses", None, bosses_page),
    ("Shops", None, shops_page),
    ("Social & rewards", social_badge, social_page),
    ("Info & help", None, help_page),
    ("Storage & inventory", None, storage_page),
]
# the shortcut shown next to each section
PAGE_COMMANDS = ["@go", "@char", "@gear", "@bosses", "@shop", "@social", "@help", "@storage"]

SUGGESTIONS = 3  # the first page's one-click suggestions
SUGGESTION_BASE = 200
MORE = 190

try:
    first_page = startPage
except NameError:
    first_page = "main"
try:
    first_arg = startArg
except NameError:
    first_arg = ""


def hunt_shortcut():
    # @hunt: straight to the maps for your level; Back shows every level range
    if not can_travel():
        return
    NewPlayerModule.markStep(chr, "hunt")
    level = chr.getLevel()
    bracket = QuickMoveModule.getBracketForLevel(level)
    if 0 <= bracket < QuickMoveModule.getBracketCount():
        if not hunting_bracket_page(bracket, level):
            return
    hunting_page()


def who_drops_shortcut():
    # @whodrops [item]
    if first_arg:
        say(MenuModule.getWhoDropsText(chr, first_arg))
    else:
        who_drops()


# the shortcut commands (PlayerCommands) open the menu on one of these pages
SHORTCUT_PAGES = {
    "travel": travel_page,  # @go
    "shops": shops_page,  # @shop
    "next": next_page,
    "checklist": checklist_page,
    "lfg": lfg_page,
    "hunt": hunt_shortcut,
    "bosses": bosses_page,
    "char": character_page,
    "ap": ability_page,
    "stats": lambda: say(MenuModule.getStatsText(chr)),
    "drops": lambda: say(MenuModule.getMapDropsText(chr)),
    "whodrops": who_drops_shortcut,
    "exchange": exchange_page,  # @mlg
    "jobgear": job_gear_page,
    "towns": towns_page,
    "bossentrance": boss_entrance_page,  # @bossenter
    "innerability": inner_ability_page,  # @ia
    "rankings": rankings_page,
    "social": social_page,
}

NewPlayerModule.markStep(chr, "menu")
if first_page in SHORTCUT_PAGES:
    SHORTCUT_PAGES[first_page]()
else:
    while True:
        # the character's most useful next steps first, one click each; then the sections
        suggest = helper_on("isNextHelperOn")
        steps = [s for s in NewPlayerModule.getNextSteps(chr) if s[1]][:SUGGESTIONS] if suggest else []
        text = "#e#bSwordie Plus Menu#k#n  Lv. {}\r\n\r\n".format(chr.getLevel())
        for i in range(len(steps)):
            text += "#L{}#{}#l\r\n".format(SUGGESTION_BASE + i, steps[i][0])
        if suggest:
            text += "#L{}##dMore suggestions...#k#l\r\n\r\n".format(MORE)
        for i in range(len(PAGES)):
            name, badge, _ = PAGES[i]
            text += "#L{}##b{}#k  #d{}#k{}#l\r\n".format(i, name, PAGE_COMMANDS[i], badge() if badge else "")
        sel = sm.sendNext(text)
        if 0 <= sel < len(PAGES):
            PAGES[sel][2]()
        elif SUGGESTION_BASE <= sel < SUGGESTION_BASE + len(steps):
            run_action(steps[sel - SUGGESTION_BASE][1])
        elif sel == MORE and suggest:
            next_page()
        else:
            break
sm.dispose()
