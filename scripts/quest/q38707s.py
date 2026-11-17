# 38707 Laboratory Investigation - start (Neinheart, Carcion). Hands over Gaon's Keycard (4037197), which nothing
# else gives; the Laboratory System inside the lab (npc/npc_3006480, 410007606) logs in with it (record clear).
NEINHEART = 3006553
KEYCARD = 4037197

sm.setSpeakerID(NEINHEART)
sm.setBoxChat()
sm.sendNext("The infectious agent you brought is intact. Good work. But the way the Specters gathered around it "
            "troubles me.")
sm.sendNext("The High Flora Army left a laboratory behind in Carcion. Gaon still has his old keycard to it.")
if sm.sendAskAccept("Enter the laboratory from #b#m410007605##k and log into the system inside. Take the keycard."):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID) and not sm.hasItem(KEYCARD) and sm.canHold(KEYCARD):
        sm.giveItem(KEYCARD)
sm.dispose()
