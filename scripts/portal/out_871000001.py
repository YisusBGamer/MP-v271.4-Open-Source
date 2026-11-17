# Portal script out_871000001.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (871000003, 1): (871000001, 0),  # Querencia / Amy's First Cafe -> Querencia / Kenneth's House (map in the script's name)
    (871000014, 1): (871000001, 0),  # Querencia / Kenneth's House -> Querencia / Kenneth's House (map in the script's name)
    (871000015, 1): (871000001, 0),  # Querencia / Kenneth's House -> Querencia / Kenneth's House (map in the script's name)
    (871000022, 1): (871000001, 0),  # Querencia / Kenneth's House -> Querencia / Kenneth's House (map in the script's name)
    (871000026, 1): (871000001, 0),  # Querencia / Kenneth's House -> Querencia / Kenneth's House (map in the script's name)
    (871000027, 1): (871000001, 0),  # Querencia / Forest Outside of Town -> Querencia / Kenneth's House (map in the script's name)
    (871000028, 1): (871000001, 0),  # Querencia / Kenneth's House -> Querencia / Kenneth's House (map in the script's name)
    (871000029, 1): (871000001, 0),  # Querencia / Kenneth's House -> Querencia / Kenneth's House (map in the script's name)
    (871000051, 1): (871000001, 0),  # Querencia / Kenneth's House -> Querencia / Kenneth's House (map in the script's name)
    (871000052, 1): (871000001, 0),  # Querencia / Kenneth's House -> Querencia / Kenneth's House (map in the script's name)
    (871000089, 1): (871000001, 0),  # Querencia / Amy's First Cafe -> Querencia / Kenneth's House (map in the script's name)
    (871000090, 1): (871000001, 0),  # Querencia / Amy's First Cafe -> Querencia / Kenneth's House (map in the script's name)
    (871000091, 2): (871000001, 0),  # Querencia / Kenneth's House -> Querencia / Kenneth's House (map in the script's name)
    (871000093, 1): (871000001, 0),  # Querencia / Kenneth's House -> Querencia / Kenneth's House (map in the script's name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
