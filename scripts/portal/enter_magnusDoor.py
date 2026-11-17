from net.swordie.ms.constants import BossConstants
from net.swordie.ms.enums import BossCooldown
from net.swordie.ms.scripts import ScriptManagerImpl as SMI

MAGNUS_WAITING = 401060400 # GMS v271: Heliseum Heights Entry (ptBossIn starts the fight)

def _toWaiting(fightMap, deaths, cd):
    sm.warpInstanceIn(MAGNUS_WAITING, 0, True)
    sm.setInstanceTime(10 * 60)
    sm.setDeathCount(deaths)
    sm.setBossCooldown(cd)
    inst = chr.getInstance()
    if inst is not None:
        inst.addProperty(SMI.WR_FIGHT_MAP_KEY, fightMap)
        inst.addProperty(SMI.WR_FIGHT_CLOCK_KEY, 30 * 60)
        inst.addProperty(SMI.WR_FIGHT_NAME_KEY, "Magnus")

sm.setSpeakerID(3001000) # Edea

EASY_MAGNUS_LEVEL = 115

levels = [155, 175]
fields = [BossConstants.NORMAL_MAGNUS_MAPID, BossConstants.HARD_MAGNUS_MAPID]
cds = [BossCooldown.Magnus, BossCooldown.MagnusHard]

fieldId = sm.getFieldID()
if fieldId == 401060399:
    response = sm.sendAskYesNo("Would you like to fight easy magnus?")
    if response:
        # (the Magnus simulator questline, quest 31851, is no longer required)
        if sm.getParty() is None:
            sm.createSoloParty()

        if not sm.isPartyLeader():
            sm.sendSayOkay("Please have your party leader enter if you wish to face Magnus.")
        elif sm.checkParty(BossCooldown.MagnusEasy, EASY_MAGNUS_LEVEL):
            _toWaiting(BossConstants.EASY_MAGNUS_MAPID, 5, BossCooldown.MagnusEasy)

else:
    response = sm.sendNext("Would you like to fight Magnus? \r\n #b#L0#Normal Mode (Level 155+)#l \r\n #r#L1#Hard Mode (Level 175+)#l")
    cd = cds[response]

    if sm.getParty() is None:
        sm.createSoloParty()

    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader enter if you wish to face Magnus.")
    elif sm.checkParty(cd, levels[response]):
        _toWaiting(fields[response], 10 if response == 0 else 15, cd)
