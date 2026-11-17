# Goddess of Grandis, Collapsing Erda 2: [6th Job] Whose Voice Is It? (38606), handed in to the Goddess of Tynerum
from net.swordie.ms.client.character.hexa import SixthJobQuest as SJ

sm.setSpeakerID(parentID)
if sm.hasQuestCompleted(SJ.GODDESS_2) and not sm.hasQuest(SJ.WHOSE_VOICE) and not sm.hasQuestCompleted(SJ.WHOSE_VOICE):
    sm.questStartDialogue(SJ.WHOSE_VOICE, parentID)
elif sm.hasQuest(SJ.WHOSE_VOICE):
    sm.sendSayOkay("The Goddess of Tynerum is further east, in the torrent.")
else:
    sm.sendSayOkay("...")
sm.dispose()
