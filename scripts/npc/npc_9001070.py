# Spiegelmann (9001070), Adventure Express Exit: the trade run's profit becomes EXP, then home.
from net.swordie.ms.client.character import NihalTradeModule

sm.sendNext(NihalTradeModule.settle(chr))
sm.warp(NihalTradeModule.returnField(chr))
sm.dispose()
