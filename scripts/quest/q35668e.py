# [Genesis Weapon] Weapon Reissue (35668): if it is still open, hand out a Sealed Genesis Weapon and close it.
from net.swordie.ms.client.character.items import EquipDropFilter

SEALED = (1212128, 1213021, 1214021, 1222121, 1232121, 1242138, 1242140, 1252105, 1262050, 1272039, 1282039, 1292021,
          1302354, 1312212, 1322263, 1332288, 1362148, 1372236, 1382273, 1402267, 1412188, 1422196, 1432226, 1442284,
          1452265, 1462251, 1472274, 1482231, 1492244, 1522151, 1532156, 1542127, 1254027, 1582043, 1592021)


def owns_genesis():
    for w in SEALED:
        if sm.hasItem(w) or sm.hasItem(w + 1):
            return True
        if chr.getEquippedInventory().getItemByItemID(w) is not None or \
                chr.getEquippedInventory().getItemByItemID(w + 1) is not None:
            return True
    return False


if sm.hasQuest(parentID):
    sm.setSpeakerID(9063150)
    if owns_genesis():
        sm.completeQuestNoCheck(parentID)
    else:
        options = [w for w in SEALED if EquipDropFilter.isUsableBy(w, chr)]
        if len(options) == 0:
            sm.sendSayOkay("There is no Genesis Weapon your class can wield.")
        else:
            pick = options[0]
            if len(options) > 1:
                text = "Choose the weapon you will awaken.\r\n\r\n#b"
                for i in range(len(options)):
                    text += "#L{}##i{}# #t{}##l\r\n".format(i, options[i], options[i])
                sel = sm.sendNext(text)
                pick = options[sel] if 0 <= sel < len(options) else 0
            if pick and not sm.canHold(pick):
                sm.sendSayOkay("Make room in your Equip inventory first.")
            elif pick:
                sm.giveItem(pick)
                sm.completeQuestNoCheck(parentID)
sm.dispose()
