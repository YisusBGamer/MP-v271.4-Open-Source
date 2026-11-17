# id 1 (exit00), field 940010020
# South Sanctum Security (25856) waits for this record to say "1" (the recording only starts it, empty). The priests
# that event_25856.py sends at you must be beaten first.
if sm.hasQuest(25856) and sm.hasMobsInField():
    sm.chat("The Nefarious Priests are still here!")
else:
    sm.startQuest(25913)
    if sm.hasQuest(25856):
        sm.setQRValue(25913, "1")
    sm.warp(400030000)
