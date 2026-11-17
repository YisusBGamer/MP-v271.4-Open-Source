# Golden Maple Leaf Shop (@leaves / @leafshop): prices, limits and buying all come from GoldenLeafModule; this only
# shows them. Esc or Back leaves a page.
from net.swordie.ms.client.character import GoldenLeafModule

LEAF = GoldenLeafModule.LEAF
BACK = 99
BUY = 50
SPECIAL_NAMES = {
    "solErda": "Sol Erda",
    "nx": "{:,} NX credit".format(GoldenLeafModule.getOffer("nx").quantity()),
    "charSlot": "Character slot",
}
TABS = [("progression", "Progression"), ("account", "Account"), ("cosmetic", "Cosmetic"), ("gear", "Gear packages")]


def back_line():
    return "\r\n#L{}##rBack#k#l".format(BACK)


def header():
    return "#e#bGolden Maple Leaf Shop#k#n   #i{}# #b{:,}#k Leaves   #d{:,} mesos#k\r\n".format(
        LEAF, GoldenLeafModule.getLeaves(chr), chr.getMoney())


def say(text):
    sm.sendSayOkay(text)


def fmt(mesos):
    return "{:,}".format(mesos)


def offer_name(o):
    if o.itemId() == 0:
        return SPECIAL_NAMES.get(o.id(), o.id())
    return ("{} ".format(o.quantity()) if o.quantity() > 1 else "") + "#t{}#".format(o.itemId())


def offer_icon(o):
    return "#i{}# ".format(o.itemId()) if o.itemId() != 0 else ""


def exchange_page():
    n_max = GoldenLeafModule.maxExchange(chr)
    if n_max < 1:
        if GoldenLeafModule.getLeaves(chr) < 1:
            say("You have no Golden Maple Leaves. Mesos over {} turn into Leaves by themselves (1 Leaf for every "
                "{} mesos).".format(fmt(GoldenLeafModule.keepMesos()), fmt(GoldenLeafModule.LEAF_MESOS)))
        else:
            say(GoldenLeafModule.exchangeToMesos(chr, 1))
        return
    n = sm.sendAskNumber("Each Leaf gives back #b{}#k mesos. You can exchange up to #b{}#k now (your wallet stays "
                         "under {} mesos). How many?".format(fmt(GoldenLeafModule.LEAF_SELL_MESOS), n_max,
                                                             fmt(GoldenLeafModule.keepMesos()
                                                                      + GoldenLeafModule.LEAF_MESOS)), 1, 1, n_max)
    if 0 < n <= n_max:
        say(GoldenLeafModule.exchangeToMesos(chr, n))


def buy_leaves_page():
    n_max = GoldenLeafModule.maxBuyLeaves(chr)
    if n_max < 1:
        say(GoldenLeafModule.buyLeaves(chr, 1))
        return
    n = sm.sendAskNumber("A Leaf costs #r{}#k mesos. You can buy up to #b{}#k now. How many?".format(
        fmt(GoldenLeafModule.LEAF_BUY_MESOS), n_max), 1, 1, n_max)
    if 0 < n <= n_max:
        say(GoldenLeafModule.buyLeaves(chr, n))


def buy_offer(o):
    why = GoldenLeafModule.whyNot(chr, o)
    if why is not None:
        say(why)
        return
    most = GoldenLeafModule.maxBuy(chr, o)
    count = 1
    if o.many() and most > 1:
        count = sm.sendAskNumber("#b{}#k costs #r{}#k Leaves each. How many? (up to {})".format(
            offer_name(o), o.leaves(), most), 1, 1, most)
        if not 0 < count <= most:
            return
    ask = "Buy {}#b{}#k{} for #r{}#k Leaves?".format(offer_icon(o), offer_name(o),
                                                    " x{}".format(count) if count > 1 else "", o.leaves() * count)
    if o.note():
        ask += "\r\n\r\n#d{}#k".format(o.note())
    if sm.sendAskYesNo(ask):
        say(GoldenLeafModule.buy(chr, o.id(), count))


def offers_page(tab, title):
    while True:
        offers = GoldenLeafModule.getOffers(tab)
        text = header() + "#e{}#n\r\n".format(title)
        if tab == "account":
            text += "#dInventory and storage slots and the second pendant slot are already at their most for " \
                    "everyone.#k\r\n"
        for i in range(offers.size()):
            o = offers[i]
            text += "\r\n#L{}#{}#b{}#k - #r{}#k Lea{}#l".format(i, offer_icon(o), offer_name(o), o.leaves(),
                                                              "f" if o.leaves() == 1 else "ves")
        if tab == "cosmetic":
            text += "\r\n#L{}##bUnit damage skins#k (rare, shorten big numbers) - #r{}#k Leaves each#l".format(
                len(offers), GoldenLeafModule.DAMAGE_SKIN_LEAVES)
        sel = sm.sendNext(text + "\r\n" + back_line())
        if 0 <= sel < offers.size():
            buy_offer(offers[sel])
        elif tab == "cosmetic" and sel == offers.size():
            skins_page()
        else:
            return


SKINS_PER_PAGE = 15


def skins_page():
    start = 0
    while True:
        skins = GoldenLeafModule.getDamageSkins(chr)
        if skins.size() == 0:
            say("Your account has every unit damage skin sold here.")
            return
        start = start if start < skins.size() else 0
        end = min(skins.size(), start + SKINS_PER_PAGE)
        text = header() + "#eUnit damage skins#n - #r{}#k Leaves each (ones your account has aren't listed)\r\n".format(
            GoldenLeafModule.DAMAGE_SKIN_LEAVES)
        for i in range(start, end):
            text += "\r\n#L{}##i{}# #b#t{}##k#l".format(i, skins[i], skins[i])
        if end < skins.size():
            text += "\r\n#L{}##dMore...#k#l".format(BACK - 1)
        sel = sm.sendNext(text + "\r\n" + back_line())
        if start <= sel < end:
            item = skins[sel]
            if sm.sendAskYesNo("Buy #i{0}# #b#t{0}##k for #r{1}#k Leaves?".format(
                    item, GoldenLeafModule.DAMAGE_SKIN_LEAVES)):
                say(GoldenLeafModule.buyDamageSkin(chr, item))
        elif sel == BACK - 1:
            start = end
        else:
            return


def gear_page():
    while True:
        sets = GoldenLeafModule.GEAR_SETS
        text = header() + "#eGear packages#n\r\nUnenhanced gear for your class, one piece per slot and a weapon. " \
                          "A package opens for your whole account once any of its characters wears that full set " \
                          "at once.\r\n"
        for i in range(sets.size()):
            s = sets[i]
            state = "" if GoldenLeafModule.isUnlocked(chr, s) else "  #d(locked)#k"
            text += "\r\n#L{}##b{}#k (level {}{}) - #r{}#k Leaves{}#l".format(
                i, s.name(), s.level(), "" if s.weapon() else ", armor only", s.leaves(), state)
        sel = sm.sendNext(text + "\r\n" + back_line())
        if not 0 <= sel < sets.size():
            return
        s = sets[sel]
        why = GoldenLeafModule.whyNotSet(chr, s)
        if why is not None:
            say(why)
            continue
        items = GoldenLeafModule.getPackageItems(chr, s)
        ask = "The #b{}#k package for you:\r\n".format(s.name())
        for item in items:
            ask += "\r\n#i{0}# #t{0}#".format(item)
        ask += "\r\n\r\nBuy it for #r{}#k Leaves?".format(s.leaves())
        if sm.sendAskYesNo(ask):
            say(GoldenLeafModule.buySet(chr, s.key()))


sm.setSpeakerID(9010000)  # Maple Administrator
while True:
    text = header() + "\r\nMesos over {} turn into Golden Maple Leaves by themselves (1 Leaf for every {} " \
                      "mesos).\r\n".format(fmt(GoldenLeafModule.keepMesos()),
                                           fmt(GoldenLeafModule.LEAF_MESOS))
    text += "\r\n#L0##bExchange Leaves for mesos#k ({} mesos each)#l".format(
        fmt(GoldenLeafModule.LEAF_SELL_MESOS))
    text += "\r\n#L{}##bBuy Leaves with mesos#k ({} mesos each)#l".format(
        BUY, fmt(GoldenLeafModule.LEAF_BUY_MESOS))
    for i in range(len(TABS)):
        text += "\r\n#L{}##b{}#k#l".format(i + 1, TABS[i][1])
    sel = sm.sendNext(text + "\r\n" + back_line())
    if sel == 0:
        exchange_page()
    elif sel == BUY:
        buy_leaves_page()
    elif 1 <= sel <= len(TABS):
        tab, title = TABS[sel - 1]
        if tab == "gear":
            gear_page()
        else:
            offers_page(tab, title)
    else:
        break
sm.dispose()
