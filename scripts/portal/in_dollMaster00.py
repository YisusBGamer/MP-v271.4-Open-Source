# Portal script in_dollMaster00.
# Cold Wind: the Puppeteer's hiding place, for Re-Puppeteering (2578, Cygnus Knights). The Puppeteer is there.
if sm.hasQuest(2578):
    sm.warp(910510500, 1)
else:
    chr.chatMessage("Something is hidden here, but you have no reason to go in.")
sm.dispose()
