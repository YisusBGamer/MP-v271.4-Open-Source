# 910600200 Abandoned Hideout: Luminous' ritual room (The Ceremony, 25555).
# Record 25554 enter=1 lets the player hand the quest in from the quest window (selfComplete).
if sm.hasQuest(25555):
    if sm.getQRValue(25554, "enter") != "1":
        sm.setQRValue(25554, "enter", "1")
    sm.chatScript("You should be able to carry out the ritual here.")
