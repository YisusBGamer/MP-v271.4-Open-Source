# Ristonia: Uprising Hideout (410000314)
# Quest 39604 "An Unassuming Hideout" is handed in to Jerome (NPC 3001919), the boy who led you here. That Jerome
# stands nowhere in the client data, so he's placed next to the hideout's own Jerome while the quest is on.
if sm.hasQuest(39604):
    sm.spawnNpcIfAbsent(3001919, -10, -81)
