# Desolate Desert 2 (926030010; 926030011-019 are copies of it): The Chase and the Conspiracy (23271), the fight
# with the Suspicious Magician and their leader. clear_926030010 hands over the book.
# Dying or logging out here lands in the public map (forcedReturn 926030010): no fight there, back to the desert.
if chr.getInstance() is None:
    sm.invokeAfterDelay(500, "warp", 260020300, 3, False)
elif not sm.hasQuest(23271) or sm.hasItem(4032975):
    sm.invokeAfterDelay(500, "warpInstanceOut", 260020300, 3)
elif not sm.hasMobsInField():
    sm.spawnMob(9300457, 850, 215, False)   # Suspicious Magician
    sm.spawnMob(9300458, 1000, 215, False)  # Suspicious Magician Leader
