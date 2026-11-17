# Weird Gift Box (The Afterlands, AfterlandsEvent#openGiftBox): the four Afterlands figurines.
from net.swordie.ms.world import AfterlandsEvent as A
if A.openGiftBox(chr):
    if sm.hasQuest(63087):
        A.finish(chr, 63087)
    sm.chatScript("Inside are four little figurines: Beodog, Mansa, Adler and Billy. You smile without knowing why.")
sm.dispose()
