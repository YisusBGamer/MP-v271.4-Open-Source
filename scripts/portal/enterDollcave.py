# The Puppeteer's hideouts (in00):
#  102010100 Rocky Wasteland (Perion): Cygnus Knights - The Puppeteer's Hideout (20912) / Puppeteer Takedown (20913)
#  101030500 Where the Forest Ends (Ellinia): Aran - The Puppeteer's Cave (21728: found, but it needs a password),
#            Eliminate the Puppeteer! (21731: the ambush); Evan - The Culprit in the Cave (22549) and the puppet's quests
field = sm.getFieldID()
if field == 102010100 and (sm.hasQuest(20912) or sm.hasQuest(20913)):
    sm.warpInstanceIn(910510002, 0)
elif field == 101030500 and sm.hasQuest(21731):
    sm.warpInstanceIn(910510201, 0)
elif field == 101030500 and sm.hasQuest(21728):
    if sm.getQRValue(21761) != "0":
        sm.setQRValue(21761, "0", False)
    sm.chatRed("You found the Puppeteer's hideout, but you need a password to enter. Report to Rowen.")
elif field == 101030500 and (sm.hasQuest(22549) or sm.hasQuestCompleted(22549) and not sm.hasQuestCompleted(22553)):
    sm.warp(910050300, 1)
else:
    sm.chatRed("There seems to be a mysterious presence blocking you from entering.")
sm.dispose()
