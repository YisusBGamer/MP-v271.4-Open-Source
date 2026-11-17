# @upgrades - finds the equips in your Equip inventory that are better than what you wear (scored for your class), and
# equips them for you. The work is done by UpgradeFinder. Also unequips items.
from net.swordie.ms.client.character import UpgradeFinder
from net.swordie.ms.constants import ItemConstants
from net.swordie.ms.enums import InvType

sm.setSpeakerID(9010000)  # Maple Administrator

MAX_SHOWN = 12  # the chat window gets cut off with too many lines


def pick(items, sel):
    # menu selections come from the client: ignore anything out of range
    if sel is None or sel < 0 or sel >= len(items):
        return None
    return items[sel]


def versus(e):
    cur = e.getCurrentItemId()
    return ("#t%d#" % cur) if cur != 0 else "an empty " + e.getSlotName().lower() + " slot"


def upgrade_list(upgrades):
    text = "#e#bUpgrades you can wear now#k#n (best first)\r\n"
    text += "#d+score = an estimate in main stat points; gear is compared fully scrolled and Star Forced " \
            "(@enhance). Active set bonuses and the CRA > AbsoLab > Arcane Umbra and Boss > Gollux > Pitched " \
            "progression paths are included. Androids are ranked by grade.#k\r\n\r\n"
    for i in range(min(len(upgrades), MAX_SHOWN)):
        e = upgrades[i]
        if e.isSetGroup():
            text += "#L%d##i%d# #b%s set (%d pieces)#k #r+%d#k#l\r\n" % (
                i, e.getItemId(), e.getSetName(), e.getPieces().size(), e.getGain())
            text += "      %s\r\n      %s\r\n" % (e.getChanges(), " ".join("#i%d#" % p.getItemId() for p in e.getPieces()))
            continue
        text += "#L%d##i%d# #b#t%d##k #r+%d#k#l\r\n" % (i, e.getItemId(), e.getItemId(), e.getGain())
        text += "      %s\r\n      #d(%s, replaces %s)#k\r\n" % (e.getChanges(), e.getSlotName(), versus(e))
    if len(upgrades) > MAX_SHOWN:
        text += "\r\n...and %d more. Equip some and check again.\r\n" % (len(upgrades) - MAX_SHOWN)
    text += "\r\n#L99##bBack#k#l"
    return text


def show_upgrades():
    while True:
        upgrades = list(UpgradeFinder.findUpgrades(chr))
        if len(upgrades) == 0:
            sm.sendNext("You're already wearing the best gear in your Equip inventory.")
            return
        sel = sm.sendNext(upgrade_list(upgrades))
        e = pick(upgrades, sel)
        if e is None:
            return
        if e.isSetGroup():
            text = "Equip these %d #b%s#k pieces together?\r\n\r\n" % (e.getPieces().size(), e.getSetName())
            for p in e.getPieces():
                text += "#i%d# #t%d# #d(%s, replaces %s)#k\r\n" % (p.getItemId(), p.getItemId(), p.getSlotName(),
                                                                  versus(p))
            text += "\r\n#b%s#k\r\n" % e.getChanges()
            if e.isBindsOnEquip():
                text += "\r\n#rSome of these can't be traded anymore once they're equipped.#k"
            if sm.sendAskYesNo(text):
                sm.sendNext(UpgradeFinder.equip(chr, e))
            continue
        text = "Equip #i%d# #b#t%d##k in your #e%s#n slot?\r\n\r\n" % (e.getItemId(), e.getItemId(), e.getSlotName())
        if e.getCurrentItemId() != 0:
            text += "It replaces #i%d# #t%d#.\r\n" % (e.getCurrentItemId(), e.getCurrentItemId())
        text += "#b%s#k\r\n" % e.getChanges()
        if e.isBindsOnEquip():
            text += "\r\n#rThis item can't be traded anymore once it's equipped.#k"
        if sm.sendAskYesNo(text):
            sm.sendNext(UpgradeFinder.equip(chr, e))


def show_later():
    later = list(UpgradeFinder.findLaterUpgrades(chr))
    if len(later) == 0:
        sm.sendNext("None of the gear you can't wear yet would be an upgrade.")
        return
    text = "#e#bUpgrades for later#k#n (you need a higher level or more stats)\r\n\r\n"
    for i in range(min(len(later), MAX_SHOWN)):
        e = later[i]
        text += "#i%d# #b#t%d##k - Lv. %d - #r+%d#k\r\n      %s\r\n" % (
            e.getItemId(), e.getItemId(), e.getRequiredLevel(), e.getGain(), e.getChanges())
    if len(later) > MAX_SHOWN:
        text += "\r\n...and %d more." % (len(later) - MAX_SHOWN)
    sm.sendNext(text)


def show_worse():
    worse = list(UpgradeFinder.findOutclassed(chr))
    other = UpgradeFinder.countNotForClass(chr)
    if len(worse) == 0 and other == 0:
        sm.sendNext("Every equip in your inventory is either an upgrade or one you can't wear yet.")
        return
    text = "#e#bGear that's no better than what you wear#k#n\r\n\r\n"
    for i in range(min(len(worse), MAX_SHOWN)):
        e = worse[i]
        text += "#i%d# #t%d# #d(%d)#k\r\n" % (e.getItemId(), e.getItemId(), e.getGain())
    if len(worse) > MAX_SHOWN:
        text += "...and %d more.\r\n" % (len(worse) - MAX_SHOWN)
    if other > 0:
        text += "\r\n%d other equip%s can't be used by your class at all.\r\n" % (other, "" if other == 1 else "s")
    text += "\r\nThese are good to sell (#b@sell#k) or store."
    sm.sendNext(text)


def unequip():
    worn = chr.getInventoryByType(InvType.EQUIPPED)
    sel = sm.sendNext("Which item should I take off? It goes into your Equip inventory.\r\n\r\n"
                      + sm.enumerateInventory(InvType.EQUIPPED))
    equip = worn.getItemBySlot(sel)
    if equip is None:
        return
    if ItemConstants.isLongOrBigSword(equip.getItemId()):
        sm.sendNext("Zero's weapons can't be taken off.")
    elif ItemConstants.isPet(equip.getItemId()):
        sm.sendNext("Pets can't be taken off this way.")
    elif chr.getEquipInventory().isFull():
        sm.sendNext("Your Equip inventory is full.")
    else:
        sm.unequip(equip)
        sm.sendNext("You took off #i%d# #t%d#." % (equip.getItemId(), equip.getItemId()))


while True:
    upgrades = list(UpgradeFinder.findUpgrades(chr))
    later = list(UpgradeFinder.findLaterUpgrades(chr))
    worse = list(UpgradeFinder.findOutclassed(chr))
    text = "#e#bGear check#k#n\r\n"
    text += "I compared every equip in your Equip inventory with what you're wearing, for your class.\r\n\r\n"
    if len(upgrades) > 0:
        best = upgrades[0]
        if best.isSetGroup():
            text += "Best upgrade: #i%d# #b%s set (%d pieces)#k (#r+%d#k)\r\n\r\n" % (
                best.getItemId(), best.getSetName(), best.getPieces().size(), best.getGain())
        else:
            text += "Best upgrade: #i%d# #b#t%d##k (#r+%d#k)\r\n\r\n" % (best.getItemId(), best.getItemId(), best.getGain())
    text += "#L0#Upgrades you can wear now (#b%d#k)#l\r\n" % len(upgrades)
    if len(upgrades) > 0:
        text += "#L1#Equip all of the best upgrades at once#l\r\n"
    text += "#L2#Upgrades for a higher level (#b%d#k)#l\r\n" % len(later)
    text += "#L3#Gear that's worse than what you wear (#b%d#k)#l\r\n" % len(worse)
    text += "#L4#How is gear scored?#l\r\n"
    text += "#L5#Unequip an item#l"
    sel = sm.sendNext(text)
    if sel == 0:
        show_upgrades()
    elif sel == 1 and len(upgrades) > 0:
        if sm.sendAskYesNo("Equip the best upgrade for every slot (%d item%s)?\r\n\r\n#rItems that become untradeable "
                           "when equipped are included.#k" % (len(upgrades), "" if len(upgrades) == 1 else "s")):
            sm.sendNext(UpgradeFinder.equipAll(chr))
    elif sel == 2:
        show_later()
    elif sel == 3:
        show_worse()
    elif sel == 5:
        unequip()
    elif sel == 4:
        sm.sendNext("Every equip gets a score in #bmain stat points#k for your class:\r\n\r\n"
                    "- 1 per point of your main stat (all stat counts too)\r\n"
                    "- 4 per point of ATT (Magic ATT for mages)\r\n"
                    "- % ATT and % main stat count as that % of your own totals\r\n"
                    "- 6 per % damage or boss damage, 4 per % ignore DEF, 15 per % critical damage\r\n"
                    "- a little for your secondary stat\r\n"
                    "- the real stats from set bonuses gained or lost by the swap\r\n"
                    "- extra weight for recognized higher-tier CRA, AbsoLab, Arcane Umbra, Boss, Gollux and "
                    "Pitched Boss pieces\r\n"
                    "- Androids are ranked by grade because the Android must match or exceed the heart's grade\r\n\r\n"
                    "Each item is compared as a fresh copy, fully scrolled and Star Forced, so an old potential or "
                    "flame cannot hide a better long-term base item. It's a rule of thumb, not exact damage math. "
                    "Higher-tier set pieces are kept for later instead of being marked as vendor gear when a "
                    "one-piece swap would temporarily break a completed set.")
    else:
        break
sm.dispose()
