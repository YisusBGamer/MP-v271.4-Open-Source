# Piggy Bar: the Blue team's area. Red players who walk in are sent back to their side.
from net.swordie.ms.world.field.instance.instancehelper.events import PiggyBarInstanceHelper

game = PiggyBarInstanceHelper.of(chr)
if game is not None:
    game.touchTeamWall(chr, False)
sm.dispose()
