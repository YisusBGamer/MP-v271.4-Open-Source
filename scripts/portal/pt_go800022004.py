# Portal script pt_go800022004.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (800022140, 1): (800022004, 0),  # Grieving Grotto / Resting Place -> Grieving Grotto / Resting Place (map id in script name)
    (800022160, 1): (800022004, 0),  # Grieving Grotto / Dusk Viper Pit -> Grieving Grotto / Resting Place (map id in script name)
    (800022180, 1): (800022004, 0),  # Grieving Grotto / Resting Place -> Grieving Grotto / Resting Place (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
