# Portal script "zero_ariant00"
# Generated: Map.wz gives no target for this portal, so the destination is the only map whose
# portal leads back here. The original script may add conditions (quests, items, levels).
# From 322000000 (Mirror World : [Mirror World] Ariant)

# A Mother's Request (41050): going to the castle is what the quest asks for
if sm.hasQuest(41050) and sm.getQRValue(41050) != "clear=clear":
    sm.setQRValue(41050, "clear", "clear")
sm.warp(322000100, 3)  # Ariant Palace : Garden, portal out00
