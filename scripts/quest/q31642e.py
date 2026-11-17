# [Communication] Eye for an Eye Part 2 - Eurenth makes the fake Gold Pouch
# The official script is missing from the client data and Say.img has no lines for it; written from the Quest.wz
# summaries. It hands over the Illusory Gold Pouch that "Disappearing Gold" (31643, started from the quest list)
# asks for.
from net.swordie.ms.loaders import ItemData
from net.swordie.ms.util import FileTime
from java.time import LocalDateTime

EURENTH = 3001002
POUCH = 4033391  # Illusory Gold Pouch: "The spell expires in 20 minutes"


def give_pouch():
    item = ItemData.getItemDeepCopy(POUCH)
    item.setDateExpire(FileTime.fromDate(LocalDateTime.now().plusMinutes(20)))
    item.setQuantity(1)
    chr.addItemToInventory(item)


sm.setSpeakerID(EURENTH)
sm.sendNext("A fake Gold Pouch, so you can buy up everything #p3001006# sells? Heh heh, leave it to me. "
            "Just remember who made it for you: #byoung-and-handsome Eurenth#k.")
if not sm.canHold(POUCH):
    sm.sendSayOkay("Make some room in your Etc inventory first.")
elif sm.completeQuest(parentID):
    if not sm.hasItem(POUCH):
        give_pouch()
    sm.sendSayOkay("I have an eye for detail: the coins even disappear after a set time. You have #b20 minutes#k, "
                   "so hurry over to #p3001006# and start your shopping spree!")
sm.dispose()
