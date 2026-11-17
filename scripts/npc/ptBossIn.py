# Boss waiting room: on to the boss entrance
from net.swordie.ms.world.field.instance.instancehelper.pq import PrincessNoInstanceHelper as PN
from net.swordie.ms.world.field.instance.instancehelper.boss import GuardianAngelSlimeInstanceHelper as GAS

inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
party = chr.getParty()
if isinstance(helper, PN) and sm.getFieldID() == PN.WAITING_ROOM:
    if party is not None and party.getPartyLeaderID() != chr.getId():
        sm.sendSayOkay("Please have your party leader talk to me.")
    elif sm.sendAskYesNo("#b#e<Boss Battle Map Entry>#n#k\r\n\r\nEverything is ready. #k#nDo you want to begin the #bNohime#k battle?"):
        helper.startFight()
elif isinstance(helper, GAS) and sm.getFieldID() == GAS.WAITING_ROOM:
    if party is not None and party.getPartyLeaderID() != chr.getId():
        sm.sendSayOkay("Please have your party leader talk to me.")
    elif sm.sendAskYesNo("#b#e<Boss Battle Map Entry>#n#k\r\n\r\nEverything is ready. #k#nDo you want to begin the #bGuardian Angel Slime#k battle?"):
        helper.enterIntro()
else:
    sm.startBoss()
# no dispose: the warp has started the boss intro, which dispose would stop
