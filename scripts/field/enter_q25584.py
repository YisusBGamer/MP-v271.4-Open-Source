# 910600201 Abandoned Hideout: Luminous' ritual room (Secret Ritual, 25584).
# Record 25583 enter=1 lets the player hand the quest in from the quest window (selfComplete).
if sm.hasQuest(25584):
    if sm.getQRValue(25583, "enter") != "1":
        sm.setQRValue(25583, "enter", "1")
    sm.chatScript("This is the perfect place for your ritual.")
