# [Destiny Weapon] The Adversary's Weapon (35857) - end (Zigang). The Genesis Weapon becomes the Destiny Weapon of the
# same kind (22 stars, as the Genesis Weapon had), and The Adversary's Determination (35858) is done.
from net.swordie.ms.loaders import ItemData

# Genesis Weapon -> Destiny Weapon
DESTINY = {1212129: 1212142, 1213022: 1213053, 1214022: 1214049, 1222122: 1222135, 1232122: 1232135,
           1242139: 1242160, 1242141: 1242161, 1253021: 1253029, 1262051: 1262065, 1272040: 1272054,
           1282040: 1282054, 1292022: 1292050, 1302355: 1302373, 1312213: 1312226, 1322264: 1322279,
           1332289: 1332303, 1362149: 1362161, 1372237: 1372251, 1382274: 1382287, 1402268: 1402290,
           1404022: 1404046, 1412189: 1412197, 1422197: 1422208, 1432227: 1432240, 1442285: 1442299,
           1452266: 1452285, 1462252: 1462268, 1472275: 1472288, 1482232: 1482245, 1492245: 1492259,
           1522152: 1522164, 1532157: 1532170, 1562010: 1562011, 1572010: 1572011, 1582044: 1582058,
           1592022: 1592049, 1542128: 1542149, 1254028: 1254029, 1252106: 1252129, 1403022: 1403052,
           1215024: 1215025}
TRACKER = 35858

sm.setSpeakerID(3006345)
inv = chr.getEquipInventory()
genesis = None
for gid in DESTINY:
    genesis = inv.getItemByItemID(gid)
    if genesis is not None:
        break
worn = [g for g in DESTINY if chr.getEquippedInventory().getItemByItemID(g) is not None]
if genesis is None and len(worn) > 0:
    sm.sendSayOkay("Take off your #bGenesis Weapon#k and put it in your inventory, then talk to me again.")
elif genesis is None:
    sm.sendSayOkay("You need your #bGenesis Weapon#k for this. Neinheart can reissue it if you lost it.")
else:
    destiny = ItemData.getItemDeepCopy(DESTINY[genesis.getItemId()])
    if destiny is None:
        sm.sendSayOkay("The weapon won't take shape. Please tell an administrator.")
    elif sm.questEndDialogue(parentID, 3006345) or sm.hasQuestCompleted(parentID):
        sm.batchRemoveItems([genesis])
        destiny.setChuc(22, True)
        chr.addItemToInventory(destiny)
        if sm.hasQuest(TRACKER):
            sm.completeQuestNoCheck(TRACKER)
        sm.sendSayOkay("Your resolve has made the weapon truly yours: the #b#t{}##k.".format(destiny.getItemId()))
sm.dispose()
