# Mushroom Shrine (800000000), hidden portal by the shrine steps -> Fort Asura (Akechi Mitsuhide).
# Map.wz sends it straight to the Ritual Altar; the entrance has the entry NPC (party, level, cooldown).
if sm.sendAskYesNo("A cold wind blows from beyond the shrine... Go to #m874000005#?"):
    sm.warp(874000005, 0)
sm.dispose()
