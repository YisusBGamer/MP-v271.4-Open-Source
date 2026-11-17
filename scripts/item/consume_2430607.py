# Gift Filled Christmas Socks (Christmas, see ChristmasEvent#openSocks): a Christmas prize and 2 Swordie Coins.
from net.swordie.ms.world import ChristmasEvent as X
got = X.openSocks(chr)
if got is not None:
    sm.chatScript("There was something in the sock: " + got + "!")
sm.dispose()
