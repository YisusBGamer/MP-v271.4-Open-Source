# 910600301 Hidden Path (Xenon's way down from the Abandoned Hideout Entrance), in00 at the bottom: the Puppeteer's
# Hidden Cave. The Secrets of the Puppeteer (23677, record 23720 = "1"): Francis and Dargoth wait in your own copy of
# 910600302 (field/inDMxn01.py, field/clear_910600302.py); once they're beaten, Francis is in 910600303.
DONE = sm.getQRValue(23720) == "1"

if sm.hasQuest(23676) and not sm.hasQuest(23677) and not sm.hasQuestCompleted(23677):
    sm.startQuest(23677)  # selfStart; needs 23676 in progress
    if sm.hasQuest(23677):
        sm.chatScript("This cave has a high probability of housing the Black Wings. I'd better take a look.")

if sm.hasQuest(23677) and not DONE:
    sm.warpInstanceIn(910600302, 0)
elif sm.hasQuest(23677):
    sm.warp(910600303, 0)
else:
    sm.chat("The cave is empty.")
sm.dispose()
