# Olson the Toy Soldier (Eos Tower 100th Floor 221023200). Dollhouse (3230): the monster that stole the clocktower's
# pendulum hides as a slightly different Doll's House in the secret room (922000010); Olson lets you in while the
# quest is on.
QUEST = 3230
DOLLS_HOUSE = 922000010

sm.setSpeakerID(parentID)
if sm.hasQuest(QUEST) and sm.getQRValue(QUEST) != "dummy":
    if sm.sendAskYesNo("The thief is hiding among the Doll's Houses in the room behind me. Find the one that looks a "
                       "little different and break it! Shall I let you in?"):
        sm.warpInstanceIn(DOLLS_HOUSE, 0)
        sm.setInstanceTime(600, 221023200)
elif sm.hasQuest(QUEST):
    sm.sendSayOkay("You got the pendulum back! Take it to Delv.")
else:
    sm.sendSayOkay("I'm standing guard here with Delv. Nothing gets past us.")
sm.dispose()
