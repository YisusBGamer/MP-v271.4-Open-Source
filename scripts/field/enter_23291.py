# 927000200 Resting Spot of Memory 3 (between Memory Lane 3 and 4): Investigating the Temple of Time
# (23291, end check 23293 = 1, selfComplete). in00 goes on to Memory Lane 4, out00 back to Memory Lane 3.
if sm.hasQuest(23291) and sm.getQRValue(23293) != "1":
    sm.startQuest(23293)
    sm.setQRValue(23293, "1")
    sm.chatScript("Why does he have this pendant? There's no way...")
    sm.chatScript("Complete 'Investigating the Temple of Time' from the quest window.")
