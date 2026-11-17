# [Genesis Weapon] What the Black Mage Left Behind (35652). q35652s hands out the Sealed Genesis Weapon itself
# instead of the box (2439614), so the box isn't asked for here.
SEALED = (1212128, 1213021, 1214021, 1222121, 1232121, 1242138, 1242140, 1252105, 1262050, 1272039, 1282039, 1292021,
          1302354, 1312212, 1322263, 1332288, 1362148, 1372236, 1382273, 1402267, 1412188, 1422196, 1432226, 1442284,
          1452265, 1462251, 1472274, 1482231, 1492244, 1522151, 1532156, 1542127, 1254027, 1582043, 1592021)
BOX = 2439614


def owns_genesis():
    for w in SEALED:
        if sm.hasItem(w) or sm.hasItem(w + 1):
            return True
        if chr.getEquippedInventory().getItemByItemID(w) is not None or \
                chr.getEquippedInventory().getItemByItemID(w + 1) is not None:
            return True
    return False


if sm.hasQuest(parentID):
    if sm.hasItem(BOX):
        sm.consumeItem(BOX)
    if sm.questEndDialogue(parentID) or sm.completeQuestNoCheck(parentID):
        if not owns_genesis():
            sm.chat("[Genesis Weapon] Lost your weapon? Neinheart can reissue it.")
sm.dispose()
