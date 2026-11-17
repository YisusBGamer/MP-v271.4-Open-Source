# OMNI-CLN entry (2052044, Pilothouse 221030900)
# GMS: level 180, 1-6 players, 30 minutes, death count 5, one clear a day
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.enums import BossCooldown

REQ_LEVEL = 180
FIGHT_FIELD = 221030910
cd = getattr(BossCooldown, "OmniCln", None)

if cd is None:
    sm.sendSayOkay("The Air Vent is sealed for now. Please come back later.")
elif sm.sendAskYesNo("#e<Boss: OMNI-CLN>#n\r\nOMNI-CLN is cleaning out Air Vent D-77. Will you stop it? "
                     "#b(Normal, level %d+)#k" % REQ_LEVEL):
    if sm.getParty() is None:
        sm.createSoloParty()
    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader talk to me if you wish to face OMNI-CLN.")
    elif sm.checkParty(cd, REQ_LEVEL):
        sm.warpInstanceIn(FIGHT_FIELD, 0, True)
        sm.setInstanceTime(30 * 60, 221030900)
        sm.setDeathCount(5)
        sm.setBossCooldown(cd)
sm.dispose()
