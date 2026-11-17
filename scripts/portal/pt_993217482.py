# 993217482 east00: deeper into the Erda Torrent once the goddess here has spoken
from net.swordie.ms.client.character.hexa import SixthJobQuest as SJ

if sm.hasQuest(SJ.WHOSE_VOICE) or sm.hasQuestCompleted(SJ.WHOSE_VOICE):
    sm.warp(SJ.COLLAPSING_3, 0)
else:
    sm.chatScript("Talk to the Goddess of Grandis first.")
sm.dispose()
