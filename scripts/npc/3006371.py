# Elwin, Barrier Between Worlds: [6th Job] An Erda That Didn't Exist Before (38602), the Erdas fight
from net.swordie.ms.client.character.hexa import SixthJobQuest as SJ

sm.setSpeakerID(parentID)
if sm.hasQuest(SJ.NEW_ERDA):
    SJ.startErdas(chr)
    sm.sendSayOkay("Defeat the unfamiliar Erdas, and whatever they become!")
elif sm.hasQuestCompleted(SJ.MERGED_DIMENSION) and not sm.hasQuestCompleted(SJ.NEW_ERDA):
    if sm.questStartDialogue(SJ.NEW_ERDA, parentID):
        SJ.startErdas(chr)
else:
    sm.sendSayOkay("Lily's the one in charge here.")
sm.dispose()
