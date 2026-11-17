# Searching for Willpower (59262), start - Neinheart (1101002). The quest says to "go to Ellin Forest and fill the
# Magical Glass Bottle with the willpower of heroes past". It starts as the client has it (no start lines) and
# Neinheart gives the empty bottle.
BOTTLE = 4034044
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    if sm.questStartDialogue(parentID, 1101002) and not sm.hasItem(BOTTLE) and sm.canHold(BOTTLE):
        sm.giveItem(BOTTLE)
sm.dispose()
