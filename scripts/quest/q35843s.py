# [Limina] The Dark Knights (35843), Guwaru in World's Sorrow Midpoint 1. The fight at the top of the waterfall
# (993070074) is in your own copy, through the top portal of Midpoint 3 (portal/east_450012120) or right away; its
# clear script finishes the quest. dir is set on accepting, as the quest completes by itself.
GUWARU = 3003986

if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.setPlayerAsSpeaker()
        sm.sendNext("(The Black Mage's Elites appeared out of nowhere! Orchid held them back and cleared a way for you.)")
        sm.setSpeakerID(GUWARU)
        if sm.sendAskAccept("You must enter the portal above in #bWorld's Sorrow Midpoint 3#k, and head to the top of the "
                            "waterfall."):
            sm.startQuest(parentID)
            sm.setQRValue(parentID, "dir", "1")
    if sm.hasQuest(parentID) and sm.getChr().getInstance() is None:
        if sm.sendAskYesNo("Head to the top of the waterfall now?"):
            sm.warpInstanceIn(993070074, 0)
sm.dispose()
