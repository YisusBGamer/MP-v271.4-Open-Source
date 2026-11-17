# [Legion Champion] Rank SSS Champion Trial (start, Sir Hamselot 9010112): champions only
from net.swordie.ms.client.character.union import ChampionModule

sm.setSpeakerID(9010112)
if ChampionModule.getSlotOf(chr) < 0:
    sm.sendSayOkay("Only a #bLegion Champion#k can take the Champion Trials. Register this character in the Legion window first.")
else:
    sm.setQRValue(ChampionModule.TRIAL_RECORD, "clear", "0")
    sm.startQuest(parentID)
    sm.sendSayOkay("Defeat #bKalos the Guardian (Normal)#k alone within #b20 minutes#k to prove you're worthy of Rank SSS.")
sm.dispose()
