# [Legion] Champion's Path (end, Sir Hamselot 9010112): the Champion window opens (2 slots to start)
from net.swordie.ms.client.character.union import ChampionModule

sm.setSpeakerID(9010112)
if sm.completeQuest(parentID):
    ChampionModule.sendRecords(chr)
    sm.sendSayOkay("Welcome! Register your Champions in the #bLegion window#k. Each Champion's rank rises "
                   "through the #bChampion Trials#k (solo Champion Mode bosses), and every rank earns your "
                   "Champions a #bChampion Insignia#k.")
sm.dispose()
