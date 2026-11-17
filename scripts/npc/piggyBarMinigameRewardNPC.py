# Manager Mush (9001043), Piggy Bar reward room: the game's reward, then back to the lobby.
from net.swordie.ms.world.field.instance.instancehelper.events import PiggyBarInstanceHelper

LOBBY = 910194100
game = PiggyBarInstanceHelper.of(chr)
if game is None:
    sm.sendNext("Sweet, sweet maple! The festival lobby is this way.")
    sm.warp(LOBBY)
else:
    sm.sendNext(game.claimReward(chr))
    sm.warpInstanceOut(LOBBY, 0)
sm.dispose()
