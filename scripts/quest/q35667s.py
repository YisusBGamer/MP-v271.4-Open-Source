# [Genesis Weapon] Power Unbound, Stage 2 (35667). The Genesis Weapon's true power is unleashed: the Sealed Genesis
# Weapon becomes the Genesis Weapon, with 22 stars (as on official servers), and Trailing the Traces of the Black
# Mage (35650) is done. The weapon must be in your Equip inventory (not worn) for the change.
from net.swordie.ms.loaders import ItemData

SEALED = (1212128, 1213021, 1214021, 1222121, 1232121, 1242138, 1242140, 1252105, 1262050, 1272039, 1282039, 1292021,
          1302354, 1312212, 1322263, 1332288, 1362148, 1372236, 1382273, 1402267, 1412188, 1422196, 1432226, 1442284,
          1452265, 1462251, 1472274, 1482231, 1492244, 1522151, 1532156, 1542127, 1254027, 1582043, 1592021)
GENESIS_ESSENCE = 4310260

sm.setSpeakerID(9063151)
if not sm.hasQuestCompleted(parentID):
    inv = chr.getEquipInventory()
    sealed = None
    for w in SEALED:
        sealed = inv.getItemByItemID(w)
        if sealed is not None:
            break
    worn = [w for w in SEALED if chr.getEquippedInventory().getItemByItemID(w) is not None]
    if sealed is None and len(worn) > 0:
        sm.sendSayOkay("Take off your #bSealed Genesis Weapon#k and put it in your inventory, then talk to me again.")
    elif sealed is None:
        sm.sendSayOkay("You don't have a #bSealed Genesis Weapon#k. Neinheart can give you another one.")
    elif sm.questStartDialogue(parentID) or sm.hasQuest(parentID):
        genesis = ItemData.getItemDeepCopy(sealed.getItemId() + 1)
        if genesis is None:
            sm.sendSayOkay("Something went wrong unsealing the weapon. Please tell an administrator.")
        else:
            sm.batchRemoveItems([sealed])
            genesis.setChuc(22, True)
            chr.addItemToInventory(genesis)
            if sm.canHold(GENESIS_ESSENCE) and not sm.hasItem(GENESIS_ESSENCE):
                sm.giveItem(GENESIS_ESSENCE)
            sm.completeQuestNoCheck(parentID)
            if sm.hasQuest(35650):
                sm.completeQuestNoCheck(35650)
            sm.sendSayOkay("The seal is broken. The #b#t{}##k's true power is yours.".format(genesis.getItemId()))
sm.dispose()
