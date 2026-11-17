# Rondo (1541005), Power Plant Lobby 310050000: starts Retrieving the Seal Stone (36314) when the client doesn't
# auto-start it, and gives back the Seal Stone / file if they were lost.
SEAL_STONE, DOCUMENT = 4036847, 4033889
QUEST = 36314

if sm.hasQuestCompleted(36313) and not sm.hasQuest(QUEST) and not sm.hasQuestCompleted(QUEST):
    sm.sendNext("Look what I found in the storeroom: the #bSeal Stone#k, and a stack of the Black Wings' files on the Resistance.")
    if not sm.sendAskAccept("We should head back before someone notices. Take these to Athena Pierce. I'll get us out."):
        sm.dispose()
    if not (sm.canHold(SEAL_STONE) and sm.canHold(DOCUMENT)):
        sm.sendSayOkay("Your Etc inventory is full. Make some room first.")
        sm.dispose()
    sm.giveItem(SEAL_STONE)
    sm.giveItem(DOCUMENT)
    sm.startQuestNoCheck(QUEST)
    sm.chatScript("You escaped the mine. Deliver the Seal Stone and the file to Athena Pierce.")
    sm.warp(100000201)
elif sm.hasQuest(QUEST):
    for item in (SEAL_STONE, DOCUMENT):
        if not sm.hasItem(item) and sm.canHold(item):
            sm.giveItem(item)
    sm.sendSayOkay("Take the Seal Stone and the file to Athena Pierce at the Bowman Instructional School.")
else:
    sm.sendSayOkay("We should head back.")
sm.dispose()
