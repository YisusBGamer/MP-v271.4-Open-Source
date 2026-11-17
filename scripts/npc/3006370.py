# Lily, Barrier Between Worlds: [6th Job] Merged Dimension (38601)
from net.swordie.ms.client.character.hexa import SixthJobQuest as SJ

sm.setSpeakerID(parentID)
if sm.hasQuest(SJ.MERGED_DIMENSION):
    sm.questEndDialogue(SJ.MERGED_DIMENSION, parentID)
elif sm.hasQuestCompleted(SJ.MERGED_DIMENSION) and not sm.hasQuestCompleted(SJ.NEW_ERDA):
    sm.sendSayOkay("Elwin has been watching these Erdas. Talk to him.")
else:
    sm.sendSayOkay("The two worlds have become one... Be careful out there.")
sm.dispose()
