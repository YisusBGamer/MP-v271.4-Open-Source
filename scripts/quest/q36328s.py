# 36328 The Fox-Eared People - start (Cartalion, Pantheon). The way to what may be Rondo's hometown leads through the
# Specter Battleground at the edge of the barrier (993166250, field/enter_993166250.py sets the kill flag).
SPECTER_BATTLEGROUND = 993166250

sm.setSpeakerID(3000000)
if sm.hasQuest(parentID):
    if sm.getQRValue(parentID, "kill") == "1":
        sm.sendSayOkay("You made it through. Talk to Rondo.")
    elif sm.sendAskYesNo("The Specters are still blocking the way. Head back to the edge of the barrier?"):
        sm.warpInstanceIn(SPECTER_BATTLEGROUND, 0)
    sm.dispose()
sm.sendNext("There's an old tale of a people with fox ears who lived beyond the barrier, far to the east.")
sm.sendSay("But the edge of the barrier is crawling with #rSpecters#k. You'd have to break through them to get anywhere "
           "near.")
if not sm.sendAskAccept("Rondo wants to go, doesn't he? Will you take him to the edge of the barrier?"):
    sm.dispose()
sm.startQuest(parentID)
sm.warpInstanceIn(SPECTER_BATTLEGROUND, 0)
sm.dispose()
