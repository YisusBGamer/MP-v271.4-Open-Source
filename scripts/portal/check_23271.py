# The Desert of Red Sand (260020300), hidden portal in_23271: the way to the magicians who took the Forbidden Book of
# Alchemy, for The Chase and the Conspiracy (23271). The chase happens in your own copy of Desolate Desert 1/2.
BOOK = 4032975

if sm.hasQuest(23271) and not sm.hasItem(BOOK):
    if sm.canHold(BOOK):
        sm.warpInstanceIn(926030000, 0)
    else:
        sm.chatScript("Make room in your Etc inventory before you go after the magicians.")
elif sm.hasQuest(23271):
    sm.chatScript("You already have the Forbidden Book of Alchemy. Take it back to Maed in Magatia.")
else:
    sm.chatScript("There's nothing here but sand.")
sm.dispose()
