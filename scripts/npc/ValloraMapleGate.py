# Interdimensional Portal (3006669): Remote Island near Lith Harbor (104000010) <-> the seashore near Vallora (410004112)
field = sm.getFieldID()
if field == 104000010:
    if sm.sendAskYesNo("Step into the portal to #bVallora#k?"):
        sm.warp(410004112, 0)
elif field == 410004112:
    if sm.sendAskYesNo("Step into the portal to #bMaple World#k?"):
        sm.warp(104000010, 0)
sm.dispose()
