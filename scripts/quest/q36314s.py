# 36314 Retrieving the Seal Stone (Rondo, Power Plant Lobby 310050000, auto-start): the Seal Stone and the Black Wings'
# Secret Resistance Document. Handed in to Athena Pierce. Rondo (npc/1541005.py) starts it too.
SEAL_STONE, DOCUMENT = 4036847, 4033889
LOBBY = 310050000

if sm.getFieldID() != LOBBY:
    sm.dispose()  # the client asks for this quest on its own; it starts with Rondo, inside the mine
sm.setSpeakerID(1541005)
sm.sendNext("Look what I found in the storeroom: the #bSeal Stone#k, and a stack of the Black Wings' files on the Resistance.")
if not sm.sendAskAccept("We should head back before someone notices. Take these to Athena Pierce. I'll get us out."):
    sm.dispose()
if not (sm.canHold(SEAL_STONE) and sm.canHold(DOCUMENT)):
    sm.sendSayOkay("Your Etc inventory is full. Make some room first.")
    sm.dispose()
if not sm.hasItem(SEAL_STONE):
    sm.giveItem(SEAL_STONE)
if not sm.hasItem(DOCUMENT):
    sm.giveItem(DOCUMENT)
if not sm.hasQuest(parentID):
    sm.startQuest(parentID)
sm.chatScript("You escaped the mine. Deliver the Seal Stone and the file to Athena Pierce.")
sm.warp(100000201)
sm.dispose()
