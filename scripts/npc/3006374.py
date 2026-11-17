# Goddess of Maple World, Dwindling Torrent: [6th Job] Into the Sol Erda (38608), done once the six Symbols are back
from net.swordie.ms.client.character.hexa import SixthJobQuest as SJ

sm.setSpeakerID(parentID)
if sm.hasQuest(SJ.INTO_SOL_ERDA):
    if sm.questEndDialogue(SJ.INTO_SOL_ERDA, parentID):
        sm.setQRValue(SJ.GODDESS_RECORD, "1")  # A New Age opens
    else:
        sm.sendSayOkay("The Goddess of Grandis will help you recover each Symbol from beyond the torrent.")
elif sm.hasQuestCompleted(SJ.GODDESS_3) and not sm.hasQuestCompleted(SJ.INTO_SOL_ERDA):
    sm.questStartDialogue(SJ.INTO_SOL_ERDA, parentID)
else:
    sm.sendSayOkay("Find the lost power Deep Within Sol Erda, to the east.")
sm.dispose()
