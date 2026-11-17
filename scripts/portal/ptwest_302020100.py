# Portal script "ptwest_302020100"
# Generated: Map.wz gives no target for this portal, so the destination is the only map whose
# portal leads back here. The original script may add conditions (quests, items, levels).
# From 302020100 (Ellin Forest : Fairy Queen's Nest)

if sm.hasQuest(32635) and not sm.isComplete(32635):
    # Evidence in Ellin Forest (32635): leaving the forest, you run into Omens - and the White Mage
    sm.warpInstanceIn(302020600, 0)
else:
    sm.warp(302020000, 1)  # Ellin Forest : Way to the Fairy Queen, portal east00
