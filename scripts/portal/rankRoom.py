fields = {
100000201 : [100000205, 2],
103000003 : [103000008, 1],
102000003 : [102000004, 1],
101000003 : [101000004, 2],
101000011 : [101000004, 2],
101000010 : [101000004, 2], # the third Magic Library map
}
# Nautilus (120000101) and Rien (140010100) have no hall of fame map in v232, and the story copies of the schools
# (910080000, 910090000, 910142040) shouldn't be left this way, so the portal does nothing there

currentMap = sm.getFieldID()
if currentMap in fields:
    sm.warp(fields[currentMap][0], fields[currentMap][1])
sm.dispose()
