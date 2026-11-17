# [Genesis Weapon] What the Black Mage Left Behind (35652). Neinheart gives you a Sealed Genesis Weapon for
# your class (official: a box to open; here the weapon itself).
from net.swordie.ms.client.character.items import EquipDropFilter

# Sealed Genesis weapons (the unsealed one is always the next item id).
SEALED = (1212128, 1213021, 1214021, 1222121, 1232121, 1242138, 1242140, 1252105, 1262050, 1272039, 1282039, 1292021,
          1302354, 1312212, 1322263, 1332288, 1362148, 1372236, 1382273, 1402267, 1412188, 1422196, 1432226, 1442284,
          1452265, 1462251, 1472274, 1482231, 1492244, 1522151, 1532156, 1542127, 1254027, 1582043, 1592021)


def usable_sealed():
    return [w for w in SEALED if EquipDropFilter.isUsableBy(w, chr)]


def owns_genesis():
    for w in SEALED:
        if sm.hasItem(w) or sm.hasItem(w + 1):
            return True
        if chr.getEquippedInventory().getItemByItemID(w) is not None or \
                chr.getEquippedInventory().getItemByItemID(w + 1) is not None:
            return True
    return False


def give_sealed():
    """lets the player pick a Sealed Genesis weapon their class can use. Returns True if one was given."""
    options = usable_sealed()
    if len(options) == 0:
        sm.sendSayOkay("There is no Genesis Weapon your class can wield.")
        return False
    pick = options[0]
    if len(options) > 1:
        text = "Choose the weapon you will awaken.\r\n\r\n#b"
        for i in range(len(options)):
            text += "#L{}##i{}# #t{}##l\r\n".format(i, options[i], options[i])
        sel = sm.sendNext(text)
        if sel < 0 or sel >= len(options):
            return False
        pick = options[sel]
    if not sm.canHold(pick):
        sm.sendSayOkay("Make room in your Equip inventory first.")
        return False
    sm.giveItem(pick)
    return True


sm.setSpeakerID(9063150)
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    if owns_genesis() or give_sealed():
        sm.questStartDialogue(parentID)
sm.dispose()
