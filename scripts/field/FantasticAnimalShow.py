# 223030210 Fantasy Animal Show <Venue> (Scarlion and Targa): the map has no monsters of its own, so the pair spawns
# once per instance. Each revives into its Angry, then Furious form (Mob.wz); both Furious forms down is the clear.
# Contributed by Dapsy (from the Discord).

TARGA = 9420542     # Targa -> Angry Targa 9420543 -> Furious Targa 9420544
SCARLION = 9420547  # Scarlion Boss -> Angry 9420548 -> Furious 9420549
FLOOR_Y = 87

if not sm.fieldHasProperty("animalShowStarted"):
    sm.setFieldProperty("animalShowStarted", True)
    sm.spawnMob(TARGA, 0, FLOOR_Y)
    sm.spawnMob(SCARLION, 350, FLOOR_Y)
