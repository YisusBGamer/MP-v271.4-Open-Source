# Tina's Storybook (The Afterlands): locked. The Balrog's Key opens it: the Crystalline Key is inside.
from net.swordie.ms.world import AfterlandsEvent as A
if sm.hasItem(A.BALROG_KEY):
    sm.consumeItem(A.BALROG_KEY, 1)
    sm.consumeItem(A.TINA_STORYBOOK, 1)
    sm.chatScript("The tiny Balrog's Key fits! Inside the storybook, pressed between the pages, is a crystal key.")
    A.giveKey(chr, A.KEYS[5])
else:
    sm.chatScript("The storybook is locked with a very small lock.")
sm.dispose()
