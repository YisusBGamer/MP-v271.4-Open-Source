# The Black Scroll (21742), start - Mr. Do (2090004), Mu Lung. Mr. Do hands over the Hanging Scroll the quest is about
# (4220151: "given to you by the Black Wing Shadow Warrior"); nothing else gives it. The quest starts as the client has
# it (it has no start lines). His own end line says to forfeit and re-accept the quest for another scroll, so it's
# only given at the start.
SCROLL = 4220151
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    if not sm.canHold(SCROLL):
        sm.setSpeakerID(2090004)
        sm.sendSayOkay("Please make some room in your Etc inventory first.")
    elif sm.questStartDialogue(parentID, 2090004) and not sm.hasItem(SCROLL):
        sm.giveItem(SCROLL)
sm.dispose()
