# exit00, field 940000040 (Near South Sanctum)
# South Sanctum Duty (25741) waits for record 25803 to say "1" (back at the South Sanctum after beating the priests
# event_25741.py sends at you).
if sm.hasQuest(25741) and sm.hasMobsInField():
    sm.chat("The Nefarious Priests are still here!")
else:
    if sm.hasQuest(25741):
        sm.startQuest(25803)
        sm.setQRValue(25803, "1")
    sm.warp(400030000)
sm.dispose()
