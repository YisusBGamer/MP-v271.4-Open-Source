# Odium: The Wellspring (410007018) pt_00: back to the fated battleground while The Wellspring (38318) is still on,
# else Shangri-La: Plum Blossom-Scented Spring (410007021), the map on the other side of this link
QUEST = 38318

if sm.hasQuest(QUEST) and sm.getQRValue(QUEST, "step") != "5":
    sm.warpInstanceIn(993217019, 0)
else:
    sm.warp(410007021, 1)
sm.dispose()
