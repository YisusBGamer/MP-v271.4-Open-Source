# Ari (9100019), Free Market: the server's NX shop. Enhancing scrolls by tier, cash-shop cubes and assist scrolls
# at their Cash Shop price, utility items (resets, slots, storage, teleport), pet items, EXP coupons, damage skins
# (only skins that show the full number, no K/M/B units), megaphones and other conveniences, and Platinum Scissors
# of Karma used on an untradable item.
# Bulk discount on every stack: see BULK.
from net.swordie.ms.constants import ItemConstants
from net.swordie.ms.enums import InvType

# Enhancing items @enhance also offers cost what @enhance charges in mesos at 100 mesos per NX (Epic potential,
# bonus potential, flames, Golden Hammer); the higher tiers are priced by hunting income.
ENHANCING = [
    ("Potential scrolls", [
        [2049401, 5000],        # Potential Scroll (Rare)
        [2049700, 10000],       # Epic Potential Scroll 100%
        [2049790, 1000000],     # Unique Potential Scroll 100%
        [2049785, 10000000],    # Legendary Potential Scroll 50%
    ]),
    ("Bonus potential scrolls", [
        [2048305, 150000],      # Bonus Potential Scroll 70% (Rare)
        [2048306, 200000],      # Special Bonus Potential Scroll (third line guaranteed)
        [2049731, 1500000],     # Bonus Epic Potential Scroll 100%
    ]),
    ("Chaos scrolls", [
        [2049100, 10000],       # Chaos Scroll 60%
        [2049116, 40000],       # Miraculous Chaos Scroll 60%
        [2049129, 200000],      # Chaos Scroll of Goodness 50%
        [2049181, 2000000],     # Incredible Chaos Scroll of Goodness 100%
    ]),
    ("Clean Slate & Innocence", [
        [2049003, 10000],       # Pure Clean Slate Scroll 20%
        [2049047, 200000],      # Pure Clean Slate Scroll 100%
        [2049600, 50000],       # Innocence Scroll 70%
        [2049624, 200000],      # Innocence Scroll 100%
        [2530000, 10000],       # Lucky Day Scroll
    ]),
    ("Star Force", [
        [2049301, 10000],       # Equipment Enhancement Scroll
        [2049300, 250000],      # Advanced Equip. Enhancement Scroll
        [2049370, 20000000],    # Star Force 12-Star Enhancement (sets the item to 12 stars)
        [2049372, 60000000],    # Star Force 15-Star Enhancement
        [2049371, 200000000],   # Star Force 17-Star Enhancement
        [2049376, 1000000000],  # Star Force 20-Star Enhancement
    ]),
    ("Flames & hammers", [
        [2048716, 20000],       # Powerful Rebirth Flame
        [2048717, 60000],       # Eternal Rebirth Flame
        [2048753, 80000],       # Black Rebirth Flame
    ]),
]

# Cash Shop items at their Cash Shop price
CUBES = [
    [5062009, 1200],    # Red Cube
    [5062010, 2200],    # Black Cube
    [5062500, 2400],    # Bonus Potential Cube
    [5062503, 3500],    # Bonus Bright Cube
    [5062800, 1900],    # Miracle Circulator
    [5064000, 5500],    # Shielding Ward
    [5064100, 2800],    # Shield Scroll
    [5064300, 2400],    # Guardian Scroll
    [5064400, 5520],    # Return Scroll
    [5064200, 6000],    # Perfect Innocence Scroll
    [5062400, 2700],    # Fusion Anvil
]

UTILITY = [
    [5040004, 1500],    # Hyper Teleport Rock
    [5050100, 4000],    # AP Reset Scroll
    [5051001, 4000],    # SP Reset Scroll (full)
    [5050004, 2500],    # Job Adv 4 Skill SP Reset Scroll
    [5430000, 6900],    # Extra Character Slot Coupon
    [5742004, 1000],    # Familiar Slot Expansion Coupon
    [5450005, 1000],    # Portable Storage (30-day)
    [5060000, 1800],    # Item Tag
]

PETS = [
    [5068300, 4000],    # Wisp's Wondrous Wonderberry (random pet)
    [5460000, 2500],    # Pet Snack
    [5249000, 1000],    # Premium Pet Food
    [5170000, 2100],    # Pet Name Tag
]

BOOSTS = [
    [2450064, 50000],   # 2x EXP Coupon (30 min)
    [2450223, 150000],  # 3x EXP Coupon (30 min)
]

# items sold with an expiry, in hours
EXPIRY_HOURS = {5450005: 30 * 24}

CONVENIENCE = [
    [5072000, 1300],    # Super Megaphone
    [5076000, 1300],    # Item Megaphone
    [5077000, 3900],    # Triple Megaphone
    [5133000, 200],     # Buff Freezer (keeps your buffs when you die)
    [5130000, 700],     # Safety Charm (no EXP lost when you die)
    [ItemConstants.RANDOM_CHAIR_BOX, 150000],
]

DAMAGE_SKIN_NX = 500000
SCISSORS_NX = 4000

# [at least this many, percent off]
BULK = [[100, 20], [50, 15], [10, 10]]
MAX_PER_PURCHASE = 1000

# Damage skins without unit numbers (Etc/DamageSkin.img skins with no NoCustom set), one item per skin, by name
DAMAGE_SKINS = [
    2438759, 2438235, 2438885, 2632976, 2634800, 2438290, 2438840, 2631610, 2630137, 2740029, 2438245, 2438168,
    2438971, 2439338, 2636267, 2634765, 2435213, 2433907, 2438218, 2438228, 2633052, 2438287, 2632281, 2438353,
    2637344, 2630747, 2438823, 2633821, 2434499, 2434374, 2632187, 2632430, 2433901, 2635970, 2740138, 2438854,
    2433271, 2438254, 2634453, 2740199, 2631991, 2635843, 2438206, 2438303, 2634259, 2438229, 2435546, 2631098,
    2433267, 2438246, 2438260, 2635749, 2435512, 2636640, 2630235, 2635435, 2438276, 2636278, 2438230, 2637026,
    2740191, 2636784, 2634279, 2634458, 2631994, 2438302, 2438862, 2438219, 2632736, 2435335, 2438217, 2435906,
    2435905, 2630745, 2439397, 2439617, 2432591, 2438305, 2740180, 2438264, 2438291, 2433113, 2433588, 2438819,
    2438492, 2438238, 2636943, 2435523, 2740148, 2438841, 2438210, 2438250, 2438257, 2438196, 2634461, 2637537,
    2438165, 2438772, 2631885, 2436643, 2636275, 2636785, 2438295, 2438187, 2435195, 2438237, 2632739, 2435101,
    2436230, 2740077, 2438831, 2433902, 2435194, 2438753, 2435522, 2434980, 2435521, 2436596, 2434817, 2435336,
    2632993, 2439442, 2633818, 2631815, 2438236, 2438788, 2632742, 2433107, 2438752, 2630749, 2630222, 2633815,
    2633478, 2740187, 2636043, 2633475, 2438270, 2439925, 2633573, 2438834, 2433260, 2438413, 2438223, 2438637,
    2438169, 2438790, 2433252, 2637161, 2634416, 2433904, 2438307, 2630400, 2434978, 2634406, 2438833, 2438778,
    2636213, 2631916, 2438415, 2633268, 2438294, 2635002, 2635001, 2635004, 2635003, 2635000, 2438220, 2438296,
    2630652, 2438289, 2634251, 2438227, 2635459, 2631513, 2438213, 2635278, 2438771, 2435950, 2632964, 2438284,
    2439336, 2636040, 2740105, 2438226, 2740159, 2635193, 2438216, 2632498, 2438845, 2634706, 2438297, 2637383,
    2438199, 2633732, 2438279, 2438183, 2438672, 2634703, 2438861, 2438863, 2438824, 2438253, 2438267, 2438269,
    2631917, 2635281, 2630804, 2635015, 2633441, 2434658, 2438855, 2638046, 2635529, 2438178, 2740089, 2631135,
    2638163, 2435948, 2438271, 2439769, 2634277, 2438179, 2438268, 2636727, 2631094, 2433261, 2439652, 2438234,
    2434545, 2438214, 2433905, 2438379, 2438417, 2438243, 2435692, 2435694, 2435693, 2435666, 2435665, 2435695,
    2438275, 2438869, 2438799, 2438802, 2634064, 2632967, 2438293, 2438848, 2630743, 2438419, 2636929, 2438310,
    2438288, 2436651, 2436645, 2435516, 2438751, 2634746, 2433182, 2438818, 2438208, 2433270, 2633700, 2438843,
    2434544, 2438781, 2433262, 2635633, 2437485, 2438822, 2636620, 2438773, 2438161, 2439393, 2438258, 2635018,
    2633557, 2634640, 2634700, 2438244, 2438311, 2438776, 2632888, 2630266, 2439408, 2637453, 2434795, 2633055,
    2438239, 2433903, 2636940, 2436228, 2631401, 2438769, 2438308, 2634150, 2438285, 2637772, 2633196, 2438832,
    2637774, 2631786, 2438190, 2438795, 2437877, 2631189, 2439381, 2435380, 2438283, 2740006, 2434975, 2635752,
    2438193, 2438222, 2634061, 2632012, 2435374, 2438232, 2433897, 2438240, 2740047, 2438881, 2635664, 2634762,
    2438207, 2438225, 2637159, 2635432, 2631183, 2438191, 2438202, 2438261, 2634803, 2438224, 2438865, 2438205,
    2740037, 2438248, 2438180, 2438775, 2436655, 2438251, 2438215, 2630366, 2630365, 2635408, 2636786, 2438189,
    2637155, 2438203, 2631426, 2433898, 2632745, 2438830, 2438755, 2438849, 2740161, 2740050, 2632009, 2740099,
    2434818, 2433266, 2434794, 2630214, 2433269, 2438309, 2631942, 2438162, 2438477, 2630224, 2740115, 2438286,
    2637341, 2438825, 2740057, 2438272, 2438315, 2439927, 2635840, 2633218, 2434742, 2634450, 2630560, 2630481,
    2630558, 2630556, 2630485, 2630479, 2630554, 2630483, 2630552, 2630477, 2438259, 2438314, 2438201, 2438299,
    2438247, 2438304, 2434248, 2634409, 2635109, 2635661, 2633265, 2438262, 2438770, 2438485, 2630751, 2635446,
    2438852, 2438274, 2433265, 2438281, 2438789, 2633045, 2438231, 2438282, 2630178, 2632544, 2438814, 2436654,
    2438839, 2438313, 2633313, 2438812, 2436746, 2438860, 2631471, 2438249, 2632010, 2636210, 2438194, 2433906,
    2438198, 2635667, 2435379, 2435382, 2435381, 2630262, 2630264, 2636270, 2438280, 2438277, 2439298, 2631423,
    2436182, 2633273, 2438197, 2433263, 2438856, 2630385, 2434741, 2634176, 2638509, 2438842, 2438798, 2438221,
    2434976, 2636724, 2438278, 2636935, 2634743, 2438713, 2435100, 2438209, 2637380, 2438298, 2438252, 2435524,
    2438233, 2633812, 2630434, 2740130, 2631492, 2438864, 2740064, 2435510, 2438263, 2438300, 2438273, 2636932,
    2438306, 2635914, 2434979, 2635658, 2635196, 2435545, 2437049, 2438200, 2433183, 2438147, 2637157, 2630516,
    2433264, 2438805, 2437712, 2438192, 2635113, 2632184, 2635438, 2630436, 2435949, 2434570, 2635972, 2438146,
    2437467, 2637416, 2635968, 2634709, 2437051, 2438774, 2438853, 2438530, 2438265, 2636637, 2740182, 2636542,
    2635655, 2438266, 2438195, 2435183, 2438857, 2438256, 2438850, 2740042, 2740040, 2633599, 2433251, 2438796,
    2438797, 2436657, 2438473, 2434051, 2434045, 2435390, 2434057, 2434053, 2434055, 2433184, 2634262, 2631798,
    2633276, 2438151, 2635516, 2438378, 2635462, 2438292, 2438184, 2633220, 2632712, 2636545, 2438851, 2438204,
]
SKINS_PER_PAGE = 40

TABS = [(InvType.EQUIP, "Equip"), (InvType.CONSUME, "Use"), (InvType.ETC, "Etc"), (InvType.INSTALL, "Set-up"),
        (InvType.CASH, "Cash")]


def fmt(n):
    return sm.formatNumber(str(n))


def discount(n):
    for least, pct in BULK:
        if n >= least:
            return pct
    return 0


def total(cost, n):
    return cost * n * (100 - discount(n)) // 100


def bulk_line():
    return "Bulk discount: " + ", ".join("{}+ #b{}%#k off".format(least, pct) for least, pct in reversed(BULK))


def most_affordable(cost, nx):
    n = min(MAX_PER_PURCHASE, nx * 100 // (cost * (100 - BULK[0][1])))
    while n > 0 and total(cost, n) > nx:
        n -= 1
    return n


def buy(itemId, cost):
    nx = chr.getNX()
    most = most_affordable(cost, nx)
    if most < 1:
        sm.sendSayOkay("You need #b{}#k NX for one #v{}# #t{}#.".format(fmt(cost), itemId, itemId))
        return
    n = sm.sendAskNumber("How many #v{}# #t{}#? #b{}#k NX each.\r\n{}\r\nYou have #b{}#k NX.".format(
        itemId, itemId, fmt(cost), bulk_line(), fmt(nx)), 1, 1, most)
    if n < 1:
        return
    price = total(cost, n)
    pct = discount(n)
    if pct > 0 and not sm.sendAskYesNo("{} #v{}# #t{}# for #b{}#k NX ({}% off {})?".format(
            n, itemId, itemId, fmt(price), pct, fmt(cost * n))):
        return
    if not sm.canHold(itemId, n):
        sm.sendSayOkay("Please make some space in your inventory first.")
    elif chr.getNX() < price:
        sm.sendSayOkay("You don't have enough NX.")
    else:
        chr.deductNX(price)
        if itemId in EXPIRY_HOURS:
            for i in range(n):
                sm.giveItemWithExpiry(itemId, EXPIRY_HOURS[itemId])
        else:
            sm.giveItem(itemId, n)
        sm.sendSayOkay("Here you go: {} #v{}# #t{}# for #b{}#k NX.".format(n, itemId, itemId, fmt(price)))


def item_menu(title, items):
    text = "#e{}#n  (you have #b{}#k NX)\r\n{}\r\n\r\n#b".format(title, fmt(chr.getNX()), bulk_line())
    for i in range(len(items)):
        text += "#L{}##v{}# #t{}# - {} NX#l\r\n".format(i, items[i][0], items[i][0], fmt(items[i][1]))
    sel = sm.sendNext(text)
    if 0 <= sel < len(items):
        buy(items[sel][0], items[sel][1])


def enhancing():
    text = "Which kind of enhancing item?\r\n\r\n#b"
    for i in range(len(ENHANCING)):
        text += "#L{}#{}#l\r\n".format(i, ENHANCING[i][0])
    sel = sm.sendNext(text)
    if 0 <= sel < len(ENHANCING):
        item_menu(ENHANCING[sel][0], ENHANCING[sel][1])


def damage_skins():
    pages = (len(DAMAGE_SKINS) + SKINS_PER_PAGE - 1) // SKINS_PER_PAGE
    text = "Every damage skin is #b{}#k NX. These all show your full damage (no K/M/B units).\r\n\r\n#b".format(
        fmt(DAMAGE_SKIN_NX))
    for p in range(pages):
        first = DAMAGE_SKINS[p * SKINS_PER_PAGE]
        last = DAMAGE_SKINS[min(len(DAMAGE_SKINS), (p + 1) * SKINS_PER_PAGE) - 1]
        text += "#L{}##t{}#  ...  #t{}##l\r\n".format(p, first, last)
    p = sm.sendNext(text)
    if p < 0 or p >= pages:
        return
    skins = DAMAGE_SKINS[p * SKINS_PER_PAGE:(p + 1) * SKINS_PER_PAGE]
    text = "Which damage skin? (#b{}#k NX, you have #b{}#k)\r\n\r\n#b".format(fmt(DAMAGE_SKIN_NX), fmt(chr.getNX()))
    for i in range(len(skins)):
        text += "#L{}##v{}# #t{}##l\r\n".format(i, skins[i], skins[i])
    sel = sm.sendNext(text)
    if sel < 0 or sel >= len(skins):
        return
    itemId = skins[sel]
    if chr.getNX() < DAMAGE_SKIN_NX:
        sm.sendSayOkay("You need #b{}#k NX for a damage skin.".format(fmt(DAMAGE_SKIN_NX)))
    elif not sm.sendAskYesNo("Buy #v{}# #t{}# for #b{}#k NX?".format(itemId, itemId, fmt(DAMAGE_SKIN_NX))):
        return
    elif not sm.canHold(itemId, 1):
        sm.sendSayOkay("Please make some space in your Use inventory first.")
    else:
        chr.deductNX(DAMAGE_SKIN_NX)
        sm.giveItem(itemId, 1)
        sm.sendSayOkay("Here you go: #v{}# #t{}#. Use it to apply the skin.".format(itemId, itemId))


def scissors():
    if chr.getNX() < SCISSORS_NX:
        sm.sendSayOkay("You need #b{}#k NX for that.".format(fmt(SCISSORS_NX)))
        return
    text = "Which tab is the item in?\r\n\r\n#b"
    for i in range(len(TABS)):
        text += "#L{}#{}#l\r\n".format(i, TABS[i][1])
    tab = sm.sendNext(text)
    if tab < 0 or tab >= len(TABS):
        return
    inv = chr.getInventoryByType(TABS[tab][0])
    items = [i for i in inv.getItemsNotInBag() if not i.isTradable() and not i.isAccountSharable()
             and not i.isBagItemOwner()]
    if len(items) == 0:
        sm.sendSayOkay("You have no untradable items in that tab.")
        return
    text = "Which item? Equips that allow it become tradable again. Everything else goes into your storage, and any " \
           "of your characters can take it back out here.\r\n\r\n#b"
    for item in items:
        text += "#L{}##i{}# #t{}##l\r\n".format(item.getBagIndex(), item.getItemId(), item.getItemId())
    item = inv.getItemBySlot(sm.sendNext(text))
    if item is None or item not in items:
        return
    if not sm.sendAskYesNo("Use #t5520001# on #i{}# #t{}# for #b{}#k NX?".format(
            item.getItemId(), item.getItemId(), fmt(SCISSORS_NX))):
        return
    if item.applyPsok(chr):
        chr.deductNX(SCISSORS_NX)  # only charged when it worked
        sm.sendSayOkay("Done!")


def scissors_take_out():
    # the storage window won't hand these out (they're untradable), so they come back out here
    trunk = chr.getAccount().getTrunk()
    items = [i for i in trunk.getItems() if not i.isTradable() and not i.isAccountSharable()]
    if len(items) == 0:
        sm.sendSayOkay("There's nothing in your storage from #t5520001#.")
        return
    text = "Which item should come out?\r\n\r\n#b"
    for n in range(len(items)):
        item = items[n]
        text += "#L{}##i{}# #t{}# x{}#l\r\n".format(n, item.getItemId(), item.getItemId(), item.getQuantity())
    n = sm.sendNext(text)
    if n < 0 or n >= len(items) or items[n] not in trunk.getItems():
        return
    item = items[n]
    if not chr.canHold(item, item.getQuantity()):
        sm.sendSayOkay("Please make some space in your inventory first.")
        return
    trunk.removeItem(item, item.getQuantity())
    chr.addItemToInventory(item.deepCopy())
    sm.sendSayOkay("Here you go: #i{}# #t{}#.".format(item.getItemId(), item.getItemId()))


sm.setSpeakerID(9100019)
sel = sm.sendNext("Welcome to the NX shop! You have #b{}#k NX.\r\n{}\r\n\r\n#b"
                  "#L0#Enhancing scrolls, flames & hammers#l\r\n"
                  "#L1#Cubes & assist scrolls (Cash Shop prices)#l\r\n"
                  "#L5#Utility: resets, slots, storage & teleport#l\r\n"
                  "#L6#Pets#l\r\n"
                  "#L7#EXP coupons#l\r\n"
                  "#L2#Damage skins ({} NX)#l\r\n"
                  "#L3#Megaphones, Buff Freezers & more#l\r\n"
                  "#L4#Use #t5520001# on an item ({} NX)#l\r\n"
                  "#L8#Take a #t5520001# item out of storage#l".format(
                      fmt(chr.getNX()), bulk_line(), fmt(DAMAGE_SKIN_NX), fmt(SCISSORS_NX)))
if sel == 0:
    enhancing()
elif sel == 1:
    item_menu("Cubes & assist scrolls", CUBES)
elif sel == 2:
    damage_skins()
elif sel == 3:
    item_menu("Megaphones & conveniences", CONVENIENCE)
elif sel == 4:
    scissors()
elif sel == 8:
    scissors_take_out()
elif sel == 5:
    item_menu("Utility", UTILITY)
elif sel == 6:
    item_menu("Pets", PETS)
elif sel == 7:
    item_menu("EXP coupons", BOOSTS)
