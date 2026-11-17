# Portal script pt_402000529_l. Illium's Sanctuary and Crystal Academy (402000500-402000535), walked through in Illium's
# Classic story. The game data gives no targets for these portals, so the links come from the region's world map
# (where each map sits), each portal's side and height, and the quest descriptions.
DEST = {
    (402000529, 3): (402000525, 3),  # Crystal Academy / Practice Lab pt_l -> Crystal Academy / Academy Corridor 2-2 pt_r
}

# Statue Remodeling (34817): "Hurry out of the Practice Lab" - the berserk practice robots in the academy corridors
# (940202100 -> 940202200 -> 940202300, each returning here), then the escape scenes and the 2nd job
escaping = sm.getFieldID() == 402000529 and sm.hasQuest(34817)
dest = DEST.get((sm.getFieldID(), parentID))
if escaping:
    sm.warp(940202100)
elif dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
