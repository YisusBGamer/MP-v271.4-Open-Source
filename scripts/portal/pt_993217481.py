# 993217481 east00: deeper into the Erda Torrent once the goddess here has spoken
from net.swordie.ms.client.character.hexa import SixthJobQuest as SJ

if sm.hasQuest(SJ.FAINT_VOICE) or sm.hasQuestCompleted(SJ.FAINT_VOICE):
    sm.warp(SJ.COLLAPSING_2, 0)
else:
    sm.chatScript("Save the Goddess of Maple World first.")
sm.dispose()
