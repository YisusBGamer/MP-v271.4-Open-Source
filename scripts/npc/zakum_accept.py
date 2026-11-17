# Adobis - Entrance To (Easy/Chaos) Zakum's Altar
from net.swordie.ms.enums import BossCooldown

fields = {
    211042402 : 280030200, # Easy
    211042400 : 280030100, # Normal
    211042401 : 280030000 # Chaos
}

# official (maplestorywiki.net, GMS): level, time limit (minutes), death count
rules = {
    211042402 : (50, 20, 50), # Easy
    211042400 : (90, 30, 5), # Normal
    211042401 : (90, 30, 5) # Chaos
}

cds = {
    211042402 : BossCooldown.ZakumEasy, # Easy
    211042400 : BossCooldown.Zakum, # Normal
    211042401 : BossCooldown.ZakumHard # Chaos
}

EYE_OF_FIRE = 4001017
EYE_OF_FIRE_CHUNK = 4001796  # Easy (BossParty.img reqItem)
summonItem = EYE_OF_FIRE_CHUNK if sm.getFieldID() == 211042402 else EYE_OF_FIRE

response = sm.sendAskYesNo("Are you sure you want to go in?")
toField = fields[sm.getFieldID()]
cd = cds[sm.getFieldID()]

if response:
    if sm.getParty() is None:
        sm.createSoloParty()

    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader talk to me if you wish to face Zakum.")
    elif sm.checkParty(cd, rules[sm.getFieldID()][0]):
        lv, minutes, deaths = rules[sm.getFieldID()]
        # as in GMS: one to drop on the altar, if the leader has none (the altar map gives one too)
        if not sm.hasItem(summonItem) and sm.canHold(summonItem):
            sm.giveItem(summonItem)
        sm.setBossCooldown(cd)
        sm.warpInstanceIn(toField, 0, True)
        sm.setInstanceTime(minutes * 60)
        sm.setDeathCount(deaths)
