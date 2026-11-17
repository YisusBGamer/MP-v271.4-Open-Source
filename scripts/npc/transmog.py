# @transmog - give an equipped item the look of another equip of the same kind you own (Equip inventory or
# storage). The item keeps its own stats; only its look changes. The work is done by TransmogModule.
from net.swordie.ms.client.character import TransmogModule

sm.setSpeakerID(9010000)  # Maple Administrator

MAX_SHOWN = 40  # long lists get cut off in the chat window

SLOT_NAMES = {
    100: "Hat", 101: "Face Accessory", 102: "Eye Accessory", 103: "Earrings", 104: "Top", 105: "Overall",
    106: "Bottom", 107: "Shoes", 108: "Gloves", 109: "Shield", 110: "Cape",
}


def slot_name(itemId):
    return SLOT_NAMES.get(itemId // 10000, "Weapon")


def describe(equip):
    look = TransmogModule.getLookItemId(equip)
    if TransmogModule.hasLook(equip):
        return "#i%d# #b#t%d##k\r\n      #dlooks like#k #i%d# #t%d#" % (equip.getItemId(), equip.getItemId(), look, look)
    return "#i%d# #b#t%d##k" % (equip.getItemId(), equip.getItemId())


def pick_look(equip):
    # returns True when something changed
    looks = list(TransmogModule.getLooks(chr, equip))
    slot = equip.getBagIndex()
    text = "#e#b%s#k#n\r\n%s\r\n\r\n" % (slot_name(equip.getItemId()), describe(equip))
    if TransmogModule.hasLook(equip):
        text += "#L1000##rRestore its own look#k#l\r\n\r\n"
    if len(looks) == 0:
        text += "You don't have any other #b%s#k to copy the look of.\r\n" \
                "Put one in your Equip inventory or storage first." % slot_name(equip.getItemId()).lower()
    else:
        text += "Choose the look:\r\n"
        for i in range(min(len(looks), MAX_SHOWN)):
            look = looks[i]
            where = "  #d(storage)#k" if look.isInStorage() else ""
            text += "#L%d##i%d# #t%d#%s#l\r\n" % (i, look.getItemId(), look.getItemId(), where)
        if len(looks) > MAX_SHOWN:
            text += "...and %d more.\r\n" % (len(looks) - MAX_SHOWN)
    text += "\r\n#L999##bBack#k#l"
    sel = sm.sendNext(text)
    if sel == 1000:
        sm.sendNext(TransmogModule.resetLook(chr, slot))
        return True
    if sel is None or sel < 0 or sel >= min(len(looks), MAX_SHOWN):
        return False
    look = looks[sel]
    if sm.sendAskYesNo("Make #i%d# #b#t%d##k look like #i%d# #b#t%d##k?\r\n\r\n"
                       "It keeps all of its own stats. The #t%d# isn't used up, and you can change or restore the "
                       "look any time." % (equip.getItemId(), equip.getItemId(), look.getItemId(), look.getItemId(),
                                           look.getItemId())):
        sm.sendNext(TransmogModule.applyLook(chr, slot, look.getItemId()))
        return True
    return False


while True:
    equips = list(TransmogModule.getEquippedItems(chr))
    text = "#e#bTransmogrification#k#n\r\n" \
           "Make your equipped items look like other equips of the same kind that you own (in your Equip " \
           "inventory or storage). Stats don't change, and other players see the new look.\r\n\r\n"
    if len(equips) == 0:
        sm.sendSayOkay(text + "You aren't wearing anything that can be transmogged.")
        break
    for i in range(len(equips)):
        text += "#L%d#%s#l\r\n" % (i, describe(equips[i]))
    if any(TransmogModule.hasLook(e) for e in equips):
        text += "\r\n#L1000##rRestore the look of everything I wear#k#l"
    sel = sm.sendNext(text)
    if sel == 1000:
        if sm.sendAskYesNo("Give every equipped item its own look back?"):
            count = TransmogModule.resetAll(chr)
            sm.sendNext("Restored the look of %d item%s." % (count, "" if count == 1 else "s"))
        continue
    if sel is None or sel < 0 or sel >= len(equips):
        break
    pick_look(equips[sel])
sm.dispose()
