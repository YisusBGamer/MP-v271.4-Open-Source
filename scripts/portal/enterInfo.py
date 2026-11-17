# Lith Harbor
# 104000000
# Warp from Lith Harbor to Tru's Info shop
# Gathering Some Strange Information (Aran 21733): the "Tru" who called you is a Black Wing in disguise, waiting in
# his own copy of the shop (910400000) until the record (21762 = 2) is set.

if sm.hasQuest(21733) and sm.getQRValue(21762) != "2":
    sm.warpInstanceIn(910400000, 0)
else:
    sm.warp(104000004, 6)
