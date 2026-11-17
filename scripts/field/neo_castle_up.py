# Neo Castle: Castle Column
# 993190400
# FieldScript
# Starts the Neo Castle Castle Column Mini-Game.
#

from net.swordie.ms.client.minigames.castlecolumn import CastleColumnUpdateType
from net.swordie.ms.connection.packet import CastleColumn

instance = chr.getInstance()
if instance is None:
    # the column game runs in its instance only: without one, back out instead of a locked, hidden screen
    sm.warp(chr.getField().getInfo().getReturnMap())
else:
    instance.addProperty("F", 0)  # Upon enter, init "F" as 0  (floors)

    sm.lockInGameUI(True, False)
    sm.hideUser(True)

    chr.write(CastleColumn.castleColumnStart())

    chr.write(CastleColumn.castleColumnUpdate(CastleColumnUpdateType.Start))
    chr.write(CastleColumn.castleColumnUpdate(CastleColumnUpdateType.MoveBlock))
