# The First / Second Void (993165850, 993165900; both maps name this script): a statue's trial in Karote. The
# guardians don't come back once defeated; clearing the map plays the statue's memory (field/clear_<map>).
QUESTS = {993165850: 38206, 993165900: 38209}

field = sm.getField()
quest = QUESTS.get(sm.getFieldID())
if quest is not None and sm.hasQuest(quest) and sm.getInstance() is not None:
    field.setRespawn(False)
    sm.chatScript("Defeat every guardian in the Void.")
