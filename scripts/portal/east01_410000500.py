# Cernium Square
# 410000500
# Warp from Cernium Square to Destroyed Cernium Square: Map.wz gives that map lvLimit 260 and qrLimit 39912
# ([Cernium (After)] Another Celestial).

if sm.hasQuestCompleted(39912) and chr.getLevel() >= 260:
    sm.warp(410000800, 4)
else:
    sm.chat("Destroyed Cernium Square opens after Another Celestial (Level 260 or higher).")
