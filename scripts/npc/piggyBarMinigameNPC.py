# Manager Mush (9001042), Piggy Bar rounds: the score so far.
from net.swordie.ms.world.field.instance.instancehelper.events import PiggyBarInstanceHelper

game = PiggyBarInstanceHelper.of(chr)
if game is None:
    sm.sendSayOkay("Sweet, sweet maple! Talk to me in the festival lobby to start a game.")
else:
    sm.sendSayOkay(game.status(chr))
sm.dispose()
