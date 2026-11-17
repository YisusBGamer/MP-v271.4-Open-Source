# Piggy Bar rounds: the game (PiggyBarInstanceHelper) starts each round. Outside a game, back to the lobby.
from net.swordie.ms.world.field.instance.instancehelper.events import PiggyBarInstanceHelper

if PiggyBarInstanceHelper.of(chr) is None:
    sm.chatScript("Talk to Manager Mush in the festival lobby to start a Piggy Bar game.")
