# Piggy Bar round 3: a cannon on the Red side. Working it fills your team's gauge.
from net.swordie.ms.world.field.instance.instancehelper.events import PiggyBarInstanceHelper

game = PiggyBarInstanceHelper.of(chr)
if game is not None:
    game.hitMachine(chr, reactor)
sm.dispose()
