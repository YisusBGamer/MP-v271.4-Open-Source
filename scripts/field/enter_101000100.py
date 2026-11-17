from net.swordie.ms.connection.packet import Effect
from net.swordie.ms.connection.packet import UserPacket
from net.swordie.ms.enums import TextEffectType
from net.swordie.ms.util import Position

# The Tradition (Luminous 25661): right after Lania is saved (Dark Destiny 25660), the Black Mage appears in the
# Inner Cursed Ellinia Forest (910142100, field/enter_q25661.py).
if sm.getChr() is None:
    pass  # started with nobody in the map
elif sm.hasQuestCompleted(25660) and not sm.hasQuestCompleted(25661):
    sm.warpInstanceIn(910142100, 0)
else:
    sm.lockInGameUI(True)

    effect = Effect.createFieldTextEffect("#fs20#- After the forest and house have recovered in a couple of months -", 50, 2000, 4,
                                          Position(0, 0), 1, 4 , TextEffectType.BlackFadedBrush, 0, 0)
    sm.getChr().getField().broadcastPacket(UserPacket.effect(effect))
    sm.invokeAfterDelay(6000, "lockInGameUI", False)
