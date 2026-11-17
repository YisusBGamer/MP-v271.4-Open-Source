# Piggy Bar reward rooms: Manager Mush has the game's reward (the room closes after a minute).
from net.swordie.ms.world.field.instance.instancehelper.events import PiggyBarInstanceHelper

if PiggyBarInstanceHelper.of(chr) is not None:
    sm.chatScript("Talk to Manager Mush for your reward before the room closes!")
