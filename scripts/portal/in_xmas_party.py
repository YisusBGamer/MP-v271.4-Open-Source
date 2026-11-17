# Happyville (209000000): the chimney on the highest roof leads into Cliff's Hut.
# During Christmas (see ChristmasEvent) the tripod Cliff told you about stands up here (Cliff's Request, 4484).
from net.swordie.ms.world import ChristmasEvent as X
if sm.hasQuest(X.CLIFF) and not sm.hasItem(X.PHOTO):
    if sm.canHold(X.PHOTO):
        sm.giveItem(X.PHOTO)
        sm.chatScript("You set the camera on the tripod and took a picture of Santa's sledge flying over Happyville.")
    else:
        sm.chatScript("Make room in your Etc inventory to take the picture.")
else:
    sm.warp(209000100, 9)
sm.dispose()
