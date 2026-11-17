# [Kritias] General's Pet (32494) - General Blundell
# The official start script is missing (no Say.img lines either). The quest is handed in with a Grim Cobalt ("Metal
# from the monsters of Kritias. Probably needs to be refined to be useful."), which Blundell gives here for Beatrice
# to look at. Written from the quest's own summary.
BLUNDELL = 2232002
COBALT = 4033934  # Grim Cobalt

if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(BLUNDELL)
    if not sm.canHold(COBALT):
        sm.sendSayOkay("Make some room in your Etc inventory first.")
    elif sm.sendAskAccept("You'll want to get to know the people of Kritias. Start with #bBeatrice#k, the professor "
                          "at our magic college. And take her this #b#t4033934##k we pried off one of those monsters. "
                          "She'll know what to make of it."):
        sm.startQuest(parentID)
        if sm.hasQuest(parentID) and not sm.hasItem(COBALT):
            sm.giveItem(COBALT)
sm.dispose()
