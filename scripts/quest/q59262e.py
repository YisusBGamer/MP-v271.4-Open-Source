# Searching for Willpower (59262), end (from the quest notifier: the quest has no end NPC). The quest's own text: "You
# filled the Magical Glass Bottle with heroic will. Go back to Ereve and deliver it to Neinheart."
WILLS = (4034045, 4034046, 4034047, 4034048, 4034049)  # Kanderune, Athena Pierce, Perzen, Lohd, Yuris
EMPTY, FULL = 4034044, 4034055
if sm.hasQuest(parentID) and all(sm.hasItem(w) for w in WILLS):
    if not sm.canHold(FULL):
        sm.chatScript("Make room in your Etc inventory first.")
    elif sm.completeQuest(parentID):
        for w in WILLS:
            sm.consumeItem(w, 1)
        if sm.hasItem(EMPTY):
            sm.consumeItem(EMPTY, 1)
        sm.giveItem(FULL)
        sm.chatScript("You filled the Magical Glass Bottle with heroic will. Go back to Ereve and deliver it to Neinheart.")
sm.dispose()
