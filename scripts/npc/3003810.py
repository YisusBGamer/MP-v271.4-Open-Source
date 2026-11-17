# Velivah (3003810). At the Labyrinth of Suffering Deep Core Base (450011320) she offers the Labyrinth Dungeon
# for [Daily Quest] Clear the Labyrinth Dungeon (35583, retired in v271) and the Alliance Expedition missions
# (35271, 35274, 35283). v271's Map.wz no longer has the 450011100-119 dungeon rooms, so only warp when they exist.
import random
from net.swordie.ms.loaders import FieldData

QUESTS = (35583, 35271, 35274, 35283)
sm.setSpeakerID(3003810)
if sm.getFieldID() == 450011320 and any(sm.hasQuest(q) and not sm.isComplete(q) for q in QUESTS):
    field_id = 450011100 + random.randint(0, 19)
    if FieldData.getFieldInfoById(field_id) is None:
        sm.sendSayOkay("The guardians of time are responsible for this place.")
    elif sm.sendAskYesNo("The guardians of time are responsible for this place.\r\n\r\n#b(Enter the Labyrinth Dungeon?)#k"):
        sm.warpInstanceIn(field_id, 0)
else:
    sm.sendSayOkay("The guardians of time are responsible for this place.")
sm.dispose()
