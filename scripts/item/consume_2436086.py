# Delicious Gold Piece (The Afterlands, AfterlandsEvent#eatGold): the more you eat in a minute, the bigger you get.
# Three in a minute makes you as big as the God of Battle (or big enough to kick down a castle wall).
from net.swordie.ms.world import AfterlandsEvent as A
sm.consumeItem(2436086, 1)
n = A.eatGold(chr)
sm.chatScript(["", "Crunch! You grow to twice your size.", "Crunch! Three times your size!",
               "CRUNCH! You're four times your size! You feel invincible!"][n])
sm.dispose()
