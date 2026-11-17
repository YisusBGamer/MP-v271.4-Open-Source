# Christmas: Bicho's snowman 4490 tells you its secret.
from net.swordie.ms.world import ChristmasEvent as X
if sm.hasQuest(X.SNOWMAN) and X.learnSnowmanPart(chr, 3):
    sm.sendSayOkay('Every proper snowman needs a #bstraw hat#k. Very fashionable, very warm.')
    sm.dispose()

else:
    # Straw Hat Snowman
    # 2001003
    # Happyville teleporter to Coke Town

    if sm.sendAskYesNo("Would you like to see a land of polar bears?"):
        sm.warp(219000000)
