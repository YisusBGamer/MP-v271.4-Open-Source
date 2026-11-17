# Portal script: NextStage
# Maps: 744000001 through 744000015 (next, pt 7) - Maple Leaf High School stages
# Each stage advances one map id and lands on the arrival portal 'sp' (index 0).
# Stage maps hold only decorative NPCs, so the Sengo mob gate does not apply here.
currentMap = sm.getFieldID()
if 744000001 <= currentMap <= 744000015:
    sm.warp(currentMap + 1, 0)
sm.dispose()
