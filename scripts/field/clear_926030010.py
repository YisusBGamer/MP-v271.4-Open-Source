# Desolate Desert 2 (926030010): both magicians beaten - The Chase and the Conspiracy (23271) gets its Forbidden Book
# of Alchemy back. Then back to the Desert of Red Sand; Maed waits in the Alcadno Society (enter_alcadno).
BOOK = 4032975

if sm.hasQuest(23271) and not sm.hasItem(BOOK):
    if sm.canHold(BOOK):
        sm.giveItem(BOOK)
        sm.chatScript("Those suspicious magicians were Arkarium's servants! Take the Forbidden Book of Alchemy back to Maed in Magatia.")
    else:
        sm.chatScript("Your Etc inventory is full, and the book is lost in the sand. Make room and go after the magicians again.")
    sm.invokeAfterDelay(3000, "warpInstanceOut", 260020300, 3)
