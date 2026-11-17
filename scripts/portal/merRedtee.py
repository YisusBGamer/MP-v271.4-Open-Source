# Hidden 'in00' entrance of Humid Swamp (105010100) into Audish's Ambush (910510410).
# Map.wz Graph.img marks this portal as a script portal (targetMap 999999999) and no
# map portal links to the hidden street, so this script is the only way in. The other
# 'Audish's Ambush' map (922030100) belongs to the Ludibrium storyline (returnMap
# 220040100) and has no known entrance.
if sm.getFieldID() == 105010100:
    sm.warp(910510410, 0)
sm.dispose()
