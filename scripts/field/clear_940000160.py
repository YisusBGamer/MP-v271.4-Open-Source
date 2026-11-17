# 940000160 South Pantheon Border: the Elite Specters are beaten - Start the Counterattack (25756) goes on to
# Magnus's appearance (940000170, field/event_25756_end.py), in the same private copy of the battle.
if sm.hasQuest(25756) and sm.getQRValue(25801) != "1":
    sm.invokeAfterDelay(1500, "warp", 940000170, 0, False)
else:
    sm.invokeAfterDelay(1500, "warpInstanceOut", 400000000, 0)
sm.dispose()
