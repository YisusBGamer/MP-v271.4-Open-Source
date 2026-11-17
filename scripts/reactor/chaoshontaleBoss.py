# Chaos horntail gem
reactor.incHitCount()
if reactor.getHitCount() >= 5:
    sm.spawnMob(8810130, 95, 260)  # Summon Chaos Horntail (removeAfter 1): the rising animation, as in GMS
    sm.spawnMob(8810102, 95, 260, 3300000000)
    sm.spawnMob(8810103, 95, 260, 3900000000)
    sm.spawnMob(8810104, 95, 260, 3300000000)
    sm.spawnMob(8810105, 95, 260, 2300000000)
    sm.spawnMob(8810106, 95, 260, 2300000000)
    sm.spawnMob(8810107, 95, 260, 2700000000)
    sm.spawnMob(8810108, 95, 260, 1300000000)
    sm.spawnMob(8810109, 95, 260, 900000000)
    sm.spawnMob(8810122, 95, 260, 20000000000)  # Chaos Horntail as GMS v271 spawns it (8810118 is only the Monster Collection id)
    sm.removeReactor()