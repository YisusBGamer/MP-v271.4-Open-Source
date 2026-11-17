# [Daily Quest] Erda's Request: 1 Sol Erda (the quest has no Act reward; the amount is ours, the text promises Sol Erda)
from net.swordie.ms.client.character.hexa import HexaModule

SOL_ERDA = 1

sm.setSpeakerID(1540940)
if sm.completeQuest(parentID):
    HexaModule.addSolErda(chr, SOL_ERDA)
    sm.sendSayOkay("The Erda has calmed down. You received #b" + str(SOL_ERDA) + " Sol Erda#k.")
else:
    sm.sendSayOkay("The Erda is still restless. Defeat more Sacred Power enemies in Grandis.")
sm.dispose()
