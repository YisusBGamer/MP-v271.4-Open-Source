# Portal script "v120MoveDrillHallIn"
# Generated: Map.wz gives no target for this portal, so the destination is the only map whose
# portal leads back here. The original script may add conditions (quests, items, levels).

targets = {
    (706041000, 2): (706041003, 2),  # Gold Dragon Dojo : Training Ground Entrance -> Gold Dragon Dojo : Drill Hall
    (706041005, 2): (706041008, 2),  # Red Tiger Dojo : Training Ground Entrance -> Red Tiger Dojo : Drill Hall
}
target = targets.get((sm.getFieldID(), parentID))
if target is None:
    # unknown portal of this field: use the only target of the field if there is one
    same = [t for k, t in targets.items() if k[0] == sm.getFieldID()]
    target = same[0] if len(same) == 1 else None
if target is not None:
    sm.warp(target[0], target[1])
else:
    sm.chat("(Portal) Not coded. ID: {}, Field: {}".format(parentID, sm.getFieldID()))
