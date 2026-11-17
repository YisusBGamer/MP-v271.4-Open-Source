# Portal script "enter_SSanct"
# Generated: Map.wz gives no target for this portal, so the destination is the only map whose
# portal leads back here. The original script may add conditions (quests, items, levels).
# From 400030100 (South Pantheon : South Pantheon Plains)

# Angelic Buster on South Sanctum Security (25856) runs into the Nefarious Priests on the way (Near South Sanctum,
# 940010020, whose recorded scene and exit portal exist).
if sm.hasQuest(25856) and sm.getQRValue(25913) != "1":
    sm.warp(940010020, 0)
else:
    sm.warp(400030000, 1)  # Pantheon Sanctum : South Sanctum, portal east00
