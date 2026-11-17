# Heroic Cube Shop (9090200): cubes for mesos, as Heroic worlds sell them; @menu > Shops. Setting shop.heroicCubes.
# Prices: see plans/heroic-cube-shop.tsv (anchored to @enhance's meso reset prices for the same rank).
from net.swordie.ms.client.character import MenuModule
from net.swordie.ms.client.character import ProgressionLog

CUBES = [
    # [item, mesos, what it does]
    [2710033, 1500000, "Rare to Epic"],                          # Mystical Cube
    [2710029, 6000000, "Rare to Unique"],                        # Hard Cube
    [2710030, 15000000, "Rare to Legendary"],                    # Solid Cube
    [5062028, 25000000, "like a Red Cube"],                      # Glowing Cube
    [5062029, 40000000, "like a Black Cube"],                    # Bright Cube
    [5062505, 65000000, "bonus potential"],                      # Bonus Glowing Cube
    [5062506, 65000000, "bonus potential"],                      # Bonus Bright Cube
]

# [at least this many, percent off]
BULK = [[100, 20], [50, 15], [10, 10]]
MAX_PER_PURCHASE = 1000


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
    return "Bulk discount: " + ", ".join("{}+ #b{}% off#k".format(least, pct) for least, pct in reversed(BULK))


def most_affordable(cost, mesos):
    n = min(MAX_PER_PURCHASE, mesos * 100 // (cost * (100 - BULK[0][1])))
    while n > 0 and total(cost, n) > mesos:
        n -= 1
    return n


def buy(itemId, cost):
    mesos = chr.getMoney()
    most = most_affordable(cost, mesos)
    if most < 1:
        sm.sendSayOkay("You need #b{}#k mesos for one #v{}# #t{}#.".format(fmt(cost), itemId, itemId))
        return
    n = sm.sendAskNumber("How many #v{}# #t{}#? #b{}#k mesos each.\r\n{}\r\nYou have #b{}#k mesos.".format(
        itemId, itemId, fmt(cost), bulk_line(), fmt(mesos)), 1, 1, most)
    if n < 1:
        return
    price = total(cost, n)
    pct = discount(n)
    if pct > 0 and not sm.sendAskYesNo("{} #v{}# #t{}# for #b{}#k mesos ({}% off {})?".format(
            n, itemId, itemId, fmt(price), pct, fmt(cost * n))):
        return
    if not sm.canHold(itemId, n):
        sm.sendSayOkay("Please make some space in your inventory first.")
    elif chr.getMoney() < price:
        sm.sendSayOkay("You don't have enough mesos.")
    else:
        chr.deductMoney(price, ProgressionLog.Sink.SHOPS)
        sm.giveItem(itemId, n)
        sm.sendSayOkay("Here you go: {} #v{}# #t{}# for #b{}#k mesos.".format(n, itemId, itemId, fmt(price)))


if not MenuModule.isHeroicCubeShopOn():
    sm.sendSayOkay("The Heroic Cube Shop is closed on this server.")
else:
    text = "#eHeroic Cube Shop#n  (you have #b{}#k mesos)\r\n\r\n#b".format(fmt(chr.getMoney()))
    for i in range(len(CUBES)):
        text += "#L{}##v{}# #t{}# - {} mesos #k({})#b#l\r\n".format(
            i, CUBES[i][0], CUBES[i][0], fmt(CUBES[i][1]), CUBES[i][2])
    sel = sm.sendNext(text)
    if 0 <= sel < len(CUBES):
        buy(CUBES[sel][0], CUBES[sel][1])
