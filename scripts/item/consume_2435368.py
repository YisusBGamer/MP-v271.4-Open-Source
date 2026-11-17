# Bellflower Monsterbloom (2435368): one random monster of the Bellflower type (the Bellflower Roots).
# Like the real item, you can get a monster you already have (the item is used up either way).
from net.swordie.ms.loaders import MonsterCollectionData, StringData

MOBS = [
    5120501, 5120502, 2600321
]

def pick():
    # a random monster the server's Monster Collection data knows (a few tries, in case one is missing)
    for _ in range(30):
        mob = MOBS[sm.getRandomIntBelow(len(MOBS))]
        if MonsterCollectionData.getMobInfoByID(mob) is not None:
            return mob
    return None

collection = chr.getAccount().getMonsterCollection()
mob = pick()
if mob is None:
    sm.sendSayOkay("Nothing could be added to your Monster Collection right now.")
else:
    name = StringData.getMobStringById(mob) or "a monster"
    if collection.hasMob(mob):
        sm.consumeItem()
        sm.chat("You got " + name + ", but it's already in your Monster Collection.")
    else:
        collection.addMobAndUpdateClient(mob, chr)
        sm.consumeItem()
        sm.chat(name + " was added to your Monster Collection!")
sm.dispose()
