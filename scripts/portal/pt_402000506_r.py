# Portal script pt_402000506_r. Illium's Sanctuary and Crystal Academy (402000500-402000535), walked through in Illium's
# Classic story. The game data gives no targets for these portals, so the links come from the region's world map
# (where each map sits), each portal's side and height, and the quest descriptions.
DEST = {
    (402000506, 1): (402000507, 2),  # Sanctuary / Western Radiant Forest 2 pt_r -> Sanctuary / Western Radiant Forest 1 pt_l
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
