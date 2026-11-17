# Moco (3006387): out of the Erda Torrent; the Maple Administrator brings you back to where you left
from net.swordie.ms.client.character.hexa import SixthJobQuest as SJ

sm.setSpeakerID(parentID)
if sm.sendAskYesNo("Leave the torrent for now? I'll remember where you were: the #bMaple Administrator#k can bring you back here."):
    SJ.leave(chr)
sm.dispose()
