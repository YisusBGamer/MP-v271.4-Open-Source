# Zakum door (ps00). As GMS v271: Easy and Normal go straight to the altar, Chaos through the waiting room
# 211042500, whose Adobis starts the fight (ptBossIn, boss-waiting-rooms.tsv). Zakum is still called on the altar
# with the Eye of Fire (ZakumInstanceHelper); the leader gets one here if they have none.
from net.swordie.ms.enums import BossCooldown

WAITING_ROOM = 211042500
WAITING_MINUTES = 10
EYE_OF_FIRE = 4001017
EYE_OF_FIRE_CHUNK = 4001796  # Easy (BossParty.img reqItem)

# menu line, altar, level, time limit (minutes), death count (official, maplestorywiki.net), clear limit, summon item,
# whether it goes through the waiting room
modes = [
    ["#bEasy#k", 280030200, 50, 20, 50, BossCooldown.ZakumEasy, EYE_OF_FIRE_CHUNK, False],
    ["Normal", 280030100, 90, 30, 5, BossCooldown.Zakum, EYE_OF_FIRE, False],
    ["#rChaos#k", 280030000, 90, 30, 5, BossCooldown.ZakumHard, EYE_OF_FIRE, True],
]

sm.setSpeakerID(2030008) # Adobis
s = "Which difficulty of Zakum do you wish to fight? \r\n"
for i in range(len(modes)):
    s += "#L" + str(i) + "#" + modes[i][0] + "#l\r\n"
name, altar, level, minutes, deaths, cd, summonItem, waiting = modes[sm.sendNext(s)]

if sm.getParty() is None:
    sm.createSoloParty()

if not sm.isPartyLeader():
    sm.sendSayOkay("Please have your party leader talk to me if you wish to face Zakum.")
elif sm.checkParty(cd, level):
    if not sm.hasItem(summonItem) and sm.canHold(summonItem):
        sm.giveItem(summonItem)
    sm.setBossCooldown(cd)
    if waiting:
        sm.warpInstanceIn(WAITING_ROOM, 0, True)
        sm.setInstanceTime(WAITING_MINUTES * 60)
    else:
        sm.warpInstanceIn(altar, 0, True)
        sm.setInstanceTime(minutes * 60)
    sm.setDeathCount(deaths)
sm.dispose()
