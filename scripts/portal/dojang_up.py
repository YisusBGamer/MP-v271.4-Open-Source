# Mu Lung Dojo: up to the next floor once this one is cleared (floor 99 leads to the Top Floor, the 100th; after it,
# out to the rooftop)
currentMap = sm.getFieldID()
FLOOR_80 = 925078000
FLOOR_99 = 925079900
TOP_FLOOR = 925081000
ROOFTOP = 925020003
if currentMap == 925070000:
    sm.createDojoClock(901)
    sm.warp(currentMap + 100, 0)
elif sm.hasMobsInField() or not (field.hasProperty("cleared") and field.getProperty("cleared")):
    sm.chat("Defeat the boss first.")
elif currentMap == TOP_FLOOR:
    sm.warpInstanceOut(ROOFTOP, 0)
else:
    if currentMap == FLOOR_80 and sm.getQRValue(7277) != "1":
        sm.createQuestWithQRValue(7277, "1")  # Mu Gong beaten on Normal Mode: opens So Gong's Successor (7270)
    if currentMap == FLOOR_99:
        sm.warp(TOP_FLOOR, 0)
    elif currentMap / 10000 == 92507:
        sm.warp(currentMap + 100, 0)
sm.dispose()
