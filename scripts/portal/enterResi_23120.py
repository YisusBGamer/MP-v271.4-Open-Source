# Black Wing Territory: Edelstein (310000000), portal in05
# The storage room next to Surl, Surl's Water Cellar (931000410), whose exit leads back to this portal. Players go in
# for "Catching the Water Thief Monster" (23120): fill Surl's water tank there with 10 bottles of water.
if sm.hasQuest(23120):
    sm.warp(931000410, 1)  # by the room's exit, out00
else:
    sm.chat("Surl's storage room is locked.")
sm.dispose()
