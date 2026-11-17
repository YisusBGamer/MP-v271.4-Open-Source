# Portal script pt_Lasya.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (410007501, 4): (410007502, 1),  # Anan's Residence / Living Room -> Anan's Residence / Rasha's Room (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
