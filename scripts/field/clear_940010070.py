# 940010070 South Pantheon Border: the Specters are beaten and the shields are back on - The Perfect Trap (25869) goes
# on to the southern border (South Border Battleground 2, 400051200), in the same private copy of the battle.
if sm.hasQuest(25869) and sm.getQRValue(25801) != "1":
    sm.chatScript("The shields are back on! Go to the southern border to fight any enemies that appear there.")
    sm.invokeAfterDelay(2000, "warp", 400051200, 0, False)
else:
    sm.invokeAfterDelay(2000, "warpInstanceOut", 400000000, 0)
sm.dispose()
