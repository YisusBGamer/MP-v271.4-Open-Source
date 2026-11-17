# @sell, Miss Richfield (Meso Market Manager, 9062008) and the other Meso Market NPCs: selling items for mesos, and
# nothing else. Sold items go to the shops' buyback list (the last 60), so a mistake can be bought back from any NPC
# shop.
# Kept, never sold: equips with scrolls or Star Force applied, quest items, and cash items a shop wouldn't buy (pets,
# no sell price, notSale); the Cash and Decoration tabs sell the rest, as in a shop. Bags that hold items
# are kept too, as selling one would lose what's inside. Everything else sells, Unique/Legendary potential included.
# Elsewhere: unequipping is in @upgrades, Platinum Scissors of Karma at Ari (NX shop), clearing out the Cash Shop
# storage in @menu > Storage & inventory, and taking items out of storage in the storage itself (@menu > Storage &
# inventory > Open my storage).
from net.swordie.ms.client.character import UpgradeFinder
from net.swordie.ms.client.character.items import EquipDropFilter
from net.swordie.ms.constants import ItemConstants
from net.swordie.ms.enums import InvType
from net.swordie.ms.handlers.life import ShopModule
from net.swordie.ms.loaders import ItemData

TABS = [(InvType.EQUIP, "Equip"), (InvType.CONSUME, "Use"), (InvType.ETC, "Etc"), (InvType.INSTALL, "Set-up"),
        (InvType.CASH, "Cash"), (InvType.DEC, "Decoration")]
MAX_ICONS = 30
SPELL_TRACE_ID = ItemConstants.SPELL_TRACE_ID

sm.setSpeakerID(9062008)


def fmt(n):
    return sm.formatNumber(str(n))


def price_of(item):
    item_id = item.getItemId()
    if ItemConstants.isEquip(item_id):
        info = ItemData.getEquipInfoById(item_id)
        return info.getPrice() if info is not None else 0
    info = ItemData.getItemInfoByID(item_id)
    if info is None:
        return 0
    if ItemConstants.isRechargable(item_id):
        return info.getPrice()
    return info.getPrice() * item.getQuantity()


def kept_reason(item):
    """why an item is never sold here, or None if it can be sold"""
    item_id = item.getItemId()
    if item_id == SPELL_TRACE_ID:
        return "spell traces"
    if item.getPowerCrystalInfo() is not None:
        return "Power Crystal"
    if item.isCash():
        info = None if ItemConstants.isEquip(item_id) else ItemData.getItemInfoByID(item_id)
        if ItemConstants.isPet(item_id) or price_of(item) <= 0 or (info is not None and info.isNotSale()):
            return "cash item"
    if item.isBagItemOwner():
        return "bag"
    if ItemConstants.isEquip(item_id):
        if ItemConstants.isSecondary(item_id):
            return "class secondary weapon"
        if item.getCuc() > 0 or item.getChuc() > 0:
            return "scrolled or Star Forced"
    else:
        info = ItemData.getItemInfoByID(item_id)
        if info is not None and info.isQuest():
            return "quest item"
    return None


def sell_spell_traces():
    count = sum(item.getQuantity() for item in chr.getEtcInventory().getItems()
                if item.getItemId() == SPELL_TRACE_ID)
    if count <= 0:
        sm.sendNext("You don't have any Spell Traces to sell.")
        return
    amount = sm.sendAskNumber("You have #b{}#k Spell Traces. How many would you like to sell?".format(fmt(count)),
                              count, 1, count)
    if amount <= 0:
        return
    info = ItemData.getItemInfoByID(SPELL_TRACE_ID)
    if info is None:
        sm.sendNext("I can't determine the Spell Trace price right now.")
        return
    total = info.getPrice() * amount
    if not chr.canAddMoney(total):
        sm.sendNext("Selling that many Spell Traces would exceed your meso limit. Choose a smaller amount.")
        return
    if not sm.sendAskYesNo("Sell #b{}#k Spell Traces for #b{}#k mesos?".format(fmt(amount), fmt(total))):
        return
    back = ItemData.getItemDeepCopy(SPELL_TRACE_ID)
    if back is not None:
        back.setQuantity(amount)
        chr.addItemToBuyBack(back)
    sm.consumeItem(SPELL_TRACE_ID, amount)
    chr.addMoney(total)
    sm.sendNext("Sold {} Spell Traces for #b{}#k mesos. You now have #b{}#k mesos.".format(
        fmt(amount), fmt(total), fmt(chr.getMoney())))


def sell_power_crystals():
    crystals = [item for item in chr.getEtcInventory().getItems()
                if item.getPowerCrystalInfo() is not None and item.getQuantity() == 1]
    if not crystals:
        sm.sendNext("You don't have any Intense Power Crystals to sell.")
        return
    limit = ShopModule.getPowerCrystalSaleLimit(chr)
    if limit <= 0:
        sm.sendNext("You've reached the weekly limit for selling Intense Power Crystals to the Collector.")
        return
    sellable = []
    total = 0
    # a crystal without a price is skipped, not the end of the list
    for item in crystals:
        if len(sellable) >= limit:
            break
        price = ShopModule.getPowerCrystalCollectorPrice(item)
        if price <= 0:
            continue
        if not chr.canAddMoney(total + price):
            break
        sellable.append(item)
        total += price
    if not sellable:
        sm.sendNext("Your meso limit prevents selling these Power Crystals right now.")
        return
    text = "Sell #b{}#k of your #b{}#k Intense Power Crystals for #b{}#k mesos at the Collector price?".format(
        len(sellable), len(crystals), fmt(total))
    if len(sellable) < len(crystals):
        text += "\r\n\r\nThe weekly Collector limit or meso cap will leave some crystals in your inventory."
    if not sm.sendAskYesNo(text):
        return
    sold = 0
    earned = 0
    for item in sellable:
        price = ShopModule.getPowerCrystalCollectorPrice(item)
        if not ShopModule.sellPowerCrystalToCollector(chr, item):
            break
        sold += 1
        earned += price
    sm.sendNext("Sold {} Intense Power Crystal{} for #b{}#k mesos at the Collector price.".format(
        sold, "" if sold == 1 else "s", fmt(earned)))


def icons(items):
    text = "".join("#i{}#".format(i.getItemId()) for i in items[:MAX_ICONS])
    if len(items) > MAX_ICONS:
        text += " +{}".format(len(items) - MAX_ICONS)
    return text


def cash_note(kept):
    """why cash items are kept: shops don't buy most Cash Shop items, as on GMS"""
    if not any(i.isCash() for i in kept):
        return ""
    return ("\r\n\r\n#dCash Shop items with no sell price, and pets, can't be sold to a shop or the Meso Market "
            "for mesos; use or trade them instead.#k")


def sell(items, what):
    """shows what would be sold, asks, sells. Returns True if something was sold."""
    sellable = [i for i in items if kept_reason(i) is None]
    kept = [i for i in items if kept_reason(i) is not None]
    if len(sellable) == 0:
        text = "There's nothing to sell in {}.".format(what)
        if len(kept) > 0:
            text += "\r\n\r\nKept (scrolled, Star Forced, quest or cash items):\r\n" + icons(kept)
        sm.sendNext(text + cash_note(kept))
        return False
    total = sum(price_of(i) for i in sellable)
    text = "Sell #b{}#k item{} from {} for #b{}#k mesos?\r\n\r\n{}".format(
        len(sellable), "" if len(sellable) == 1 else "s", what, fmt(total), icons(sellable))
    if len(kept) > 0:
        text += "\r\n\r\nKept, not sold ({}): {}".format(len(kept), icons(kept))
    text += cash_note(kept)
    text += "\r\n\r\n#d(Sold items can be bought back from any shop's buyback list.)#k"
    if not sm.sendAskYesNo(text):
        return False
    for item in sellable:
        back = item.deepCopy()
        back.setQuantity(item.getQuantity())
        chr.addItemToBuyBack(back)
    sm.batchRemoveItems(sellable)
    chr.addMoney(total)
    sm.sendNext("Sold {} item{} for #b{}#k mesos. You now have #b{}#k mesos.".format(
        len(sellable), "" if len(sellable) == 1 else "s", fmt(total), fmt(chr.getMoney())))
    return True


def sell_range():
    text = "Which inventory tab?\r\n\r\n#b"
    for i in range(len(TABS)):
        text += "#L{}#{}#l\r\n".format(i, TABS[i][1])
    tab = sm.sendNext(text)
    if tab < 0 or tab >= len(TABS):
        return
    inv_type, tab_name = TABS[tab]
    inv = chr.getInventoryByType(inv_type)
    if inv.getItemsNotInBag().size() == 0:
        sm.sendNext("Your {} tab is empty.".format(tab_name))
        return
    first = sm.sendNext("Select the #bfirst#k item to sell. Everything from it to the last item you pick is "
                        "sold.\r\n\r\n" + sm.enumerateInventory(inv_type))
    text = "Select the #blast#k item to sell.\r\n\r\n#b"
    for item in inv.getItemsNotInBag():
        if item.getBagIndex() >= first and not item.isBagItemOwner():
            text += "#L{}##i{}# #t{}##l\r\n".format(item.getBagIndex(), item.getItemId(), item.getItemId())
    last = sm.sendNext(text)
    items = [i for i in inv.getItemsNotInBag() if first <= i.getBagIndex() <= last]
    sell(items, "your {} tab".format(tab_name))


def sell_outclassed():
    items = [e.getItem() for e in UpgradeFinder.findOutclassed(chr)]
    sell(items, "the gear that's no better than what you wear")


def not_for_class():
    res = []
    for item in chr.getEquipInventory().getItems():
        item_id = item.getItemId()
        if not item.isCash() and not ItemConstants.isSymbol(item_id) \
                and not EquipDropFilter.isUsableBy(item_id, chr):
            res.append(item)
    return res


while True:
    worse = len(UpgradeFinder.findOutclassed(chr))
    other = len(not_for_class())
    sel = sm.sendNext("#e#bSell items#k#n\r\nWhat would you like to sell? Scrolled and Star Forced gear, "
                      "quest items and cash items are always kept.\r\n\r\n"
                      "#L0##bA range of items#k  #d(pick a tab, then the first and last item)#k#l\r\n"
                      "#L1##bGear that's worse than what you wear#k  ({})#l\r\n"
                      "#L2##bEquips your class can't use#k  ({})#l\r\n"
                      "#L3##bSpell Traces#k  #d({} available)#l\r\n"
                      "#L4##bIntense Power Crystals#k  #d({} available)#l".format(
                          worse, other, fmt(sum(item.getQuantity() for item in chr.getEtcInventory().getItems()
                                                if item.getItemId() == SPELL_TRACE_ID)),
                          sum(1 for item in chr.getEtcInventory().getItems()
                              if item.getPowerCrystalInfo() is not None)))
    if sel == 0:
        sell_range()
    elif sel == 1:
        sell_outclassed()
    elif sel == 2:
        sell(not_for_class(), "the equips your class can't use")
    elif sel == 3:
        sell_spell_traces()
    elif sel == 4:
        sell_power_crystals()
    else:
        break
    if not sm.sendAskYesNo("Would you like to sell anything else?"):
        break
sm.dispose()
