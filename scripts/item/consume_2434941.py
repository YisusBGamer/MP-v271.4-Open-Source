# Elite Monsterbloom (2434941): one random monster of 3 or more stars. Most monsters have no star rating in the game data,
# so this uses the strong ones: field bosses, bosses and anything rated 3+ stars.
# Like the real item, you can get a monster you already have (the item is used up either way).
from net.swordie.ms.loaders import MonsterCollectionData, StringData

MOBS = [
    2220000, 2300268, 2400259, 2600321, 3220000, 3220001, 3300110, 3401011, 3501008, 3502004, 4220000,
    4230139, 4310015, 5090001, 5220003, 5220004, 5250004, 5250007, 6090000, 6090001, 6090002, 6090004,
    6130101, 6130209, 6220000, 6300005, 7090000, 7220000, 7220002, 8090000, 8130100, 8145100, 8145101,
    8145200, 8145201, 8148012, 8150000, 8180000, 8180001, 8210013, 8220000, 8220001, 8220002, 8220003,
    8220004, 8220005, 8220006, 8220007, 8250012, 8300006, 8300007, 8500002, 8510000, 8610015, 8620012,
    8641010, 8642016, 8644011, 8644650, 8645009, 8800000, 8800100, 8800200, 8800400, 8810018, 8810118,
    8820000, 8820003, 8820006, 8820100, 8830000, 8840000, 8850000, 8850001, 8850002, 8850003, 8850004,
    8850010, 8850011, 8860000, 8870100, 8880000, 8880004, 8880006, 8880008, 8880100, 8880150, 8880300,
    8880410, 8880502, 8900000, 8900100, 8910000, 8910100, 8920000, 8920100, 8930000, 8930100, 8950001,
    9300012, 9300038, 9300039, 9300119, 9300139, 9300140, 9300180, 9300215, 9300281, 9300471, 9300479,
    9300480, 9300481, 9300482, 9300627, 9300680, 9300965, 9305408, 9309200, 9309201, 9309203, 9309205,
    9309207
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
