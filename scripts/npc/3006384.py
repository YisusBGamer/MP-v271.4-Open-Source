# Goddess of Maple World, Deep Within Sol Erda: [6th Job] A New Age (38609), handed in to the Maple Administrator
from net.swordie.ms.client.character.hexa import SixthJobQuest as SJ

sm.setSpeakerID(parentID)
if sm.hasQuestCompleted(SJ.INTO_SOL_ERDA) and not sm.hasQuest(SJ.NEW_AGE) and not sm.hasQuestCompleted(SJ.NEW_AGE):
    if sm.questStartDialogue(SJ.NEW_AGE, parentID):
        if sm.sendAskYesNo("Return to Maple World now? The #bMaple Administrator#k will see your advancement through."):
            SJ.leave(chr)
elif sm.hasQuest(SJ.NEW_AGE):
    if sm.sendAskYesNo("Return to Maple World now? The #bMaple Administrator#k will see your advancement through."):
        SJ.leave(chr)
else:
    sm.sendSayOkay("Recover the Symbols through the gates of this place.")
sm.dispose()
