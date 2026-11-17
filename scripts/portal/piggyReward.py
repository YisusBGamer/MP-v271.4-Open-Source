# Piggy Bar reward room exit: collects the reward if it's still waiting, then back to the lobby.
from net.swordie.ms.world.field.instance.instancehelper.events import PiggyBarInstanceHelper

LOBBY = 910194100
game = PiggyBarInstanceHelper.of(chr)
if game is None:
    sm.warp(LOBBY)
else:
    sm.chatScript(game.claimReward(chr))
    sm.warpInstanceOut(LOBBY, 0)
sm.dispose()
