# Sol Erda
from net.swordie.ms.client.character.hexa import HexaModule

if HexaModule.getSolErda(chr) < 20:
    HexaModule.addSolErda(chr, 1)
    sm.consumeItem(parentID)
else:
    sm.setSpeakerID(9010000)
    sm.sendNext("You can't hold more Sol Erda.")
