# Piggy Bar: the Red team's area. Blue players who walk in are sent back to their side.
from net.swordie.ms.world.field.instance.instancehelper.events import PiggyBarInstanceHelper

game = PiggyBarInstanceHelper.of(chr)
if game is not None:
    game.touchTeamWall(chr, True)
sm.dispose()
