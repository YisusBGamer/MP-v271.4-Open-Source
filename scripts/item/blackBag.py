# Black Bag (2430032), Releasing the Free Spirits (22583): opened at the top of the Sky Terrace (922030010), the
# freed spirits chase the Safe Guards away, which is the quest's one "Safe Guard" (9300389).
from net.swordie.ms.connection.packet import WvsContext
from net.swordie.ms.connection.packet.model import MessagePacket

QUEST = 22583
SKY_TERRACE = 922030010
SAFE_GUARD = 9300389

quest = chr.getQuestManager().getQuestById(QUEST)
if not sm.hasQuest(QUEST) or quest is None:
    sm.chat("A black bag from Hiver. Release it in front of the Safe...")
elif sm.getFieldID() != SKY_TERRACE or chr.getPosition().getY() > -200:
    sm.chat("Climb to the top of the Sky Terrace before opening the pouch.")
else:
    sm.consumeItem()
    quest.handleMobKill(SAFE_GUARD)
    chr.write(WvsContext.message(MessagePacket.questRecordMessage(quest)))
    chr.getQuestManager().notifyIfReady(quest)
    sm.chat("The free spirits burst out of the pouch and chase everyone away. Return to Hiver.")
sm.dispose()
