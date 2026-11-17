# 931050230 Near Edelstein: El Nath's Energy (23281, end check 23292 = 1, selfComplete). The "enter" portal leads back
# to Edelstein.
if sm.hasQuest(23281) and sm.getQRValue(23292) != "1":
    sm.startQuest(23292)
    sm.setQRValue(23292, "1")
    sm.chatScript("You chase the Watchman to the edge of the park. It appears there was an ulterior motive to bringing you here...")
    sm.chatScript("Complete 'El Nath's Energy' from the quest window.")
