# The Third Void (993165950): the last statue's trial in Karote. The guardians don't come back once defeated;
# clearing the map plays the statue's memory (field/clear_993165950).
QUEST = 38211

if sm.hasQuest(QUEST) and sm.getInstance() is not None:
    sm.getField().setRespawn(False)
    sm.chatScript("Defeat every guardian in the Void.")
