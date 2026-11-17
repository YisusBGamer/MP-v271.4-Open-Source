# The Coins are Gone - Eurenth makes another Illusory Gold Pouch
# Written from the Quest.wz summaries ("You called Eurenth young and handsome and received the
# Illusory Gold Pouch again").
from net.swordie.ms.loaders import ItemData
from net.swordie.ms.util import FileTime
from java.time import LocalDateTime

EURENTH = 3001002
POUCH = 4033391


def give_pouch():
    item = ItemData.getItemDeepCopy(POUCH)
    item.setDateExpire(FileTime.fromDate(LocalDateTime.now().plusMinutes(20)))
    item.setQuantity(1)
    chr.addItemToInventory(item)


sm.setSpeakerID(EURENTH)
sm.sendNext("The coins disappeared already? Fine, I'll make you another pouch... "
            "as long as you call me #byoung and handsome#k.")
if not sm.canHold(POUCH):
    sm.sendSayOkay("Make some room in your Etc inventory first.")
elif sm.completeQuest(parentID):
    if not sm.hasItem(POUCH):
        give_pouch()
    sm.sendSayOkay("Here you go. #b20 minutes#k, remember! Off to #p3001006# with you.")
sm.dispose()
