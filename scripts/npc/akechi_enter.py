# Akechi Mitsuhide entry (@menu > Bosses > Boss entrances)
# Official (maplestorywiki.net, GMS): Normal, level 200, 30 minutes, death count 5.
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.enums import BossCooldown

REQ_LEVEL = 200

enter = True
if sm.getFieldID() == BossConstants.AKECHI_ENTRANCE:
    choice = sm.sendNext("#eFort Asura Entrance#n\r\n"
                         "#L0#Challenge Akechi Mitsuhide.#l\r\n"
                         "#L1#Return to Momijigaoka.#l")
    if choice == 1:
        sm.warp(807000000, 0)
        enter = False

if enter and sm.sendAskYesNo("#e<Boss: Akechi Mitsuhide>#n\r\nAkechi Mitsuhide holds the Ritual Altar of Fort Asura. "
                   "Will you challenge him? #b(Normal, level %d+)#k" % REQ_LEVEL):
    if sm.getParty() is None:
        sm.createSoloParty()

    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader enter if you wish to face Akechi Mitsuhide.")
    elif sm.checkParty(BossCooldown.Akechi, REQ_LEVEL):
        sm.warpInstanceIn(BossConstants.AKECHI_FIELD, 0, True)
        sm.setInstanceTime(30 * 60, BossConstants.AKECHI_ENTRANCE)
        sm.setDeathCount(5)
        sm.setBossCooldown(BossCooldown.Akechi)
sm.dispose()
