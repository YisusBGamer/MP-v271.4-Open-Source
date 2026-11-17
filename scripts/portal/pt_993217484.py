# 993217484 east00: deeper into the Erda Torrent once the goddess here has spoken
from net.swordie.ms.client.character.hexa import SixthJobQuest as SJ

if sm.hasQuest(SJ.INTO_SOL_ERDA) or sm.hasQuestCompleted(SJ.INTO_SOL_ERDA):
    sm.warp(SJ.DEEP_WITHIN, 0)
else:
    sm.chatScript("Talk to the Goddess of Maple World first.")
sm.dispose()
