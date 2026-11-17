# Ludibrium: Forgotten Passage (220070400), portal in02
# Cygnus Knights' "Destroyer of Time" (20749): "Find the door that connects to another space in the Forgotten
# Passage, and defeat the culprit who destroyed the Clocktower." The other space is 922000030, where the culprit
# (9300308) is, and whose exit leads back to this portal.
if sm.hasQuest(20749):  # without the quest the door stays hidden: nothing happens
    sm.warp(922000030, 1)  # in00
sm.dispose()
