# 100020200 Windflower Forest, east00: the way to the Golem's Temple Entrance.
# Kidnapping of Camila (Evan 22557): the Golem that took Camila waits just past this door, in a copy of the
# entrance of its own (910600000, Enraged Golem). Defeating it sets record 22598 = 2 (clear_910600000).
if sm.hasQuest(22557) and sm.getQRValue(22598) != "2":
    sm.chatScript("Rescue Camila from the Golem!")
    sm.warpInstanceIn(910600000, 0)
else:
    sm.warp(100040000, 3)
sm.dispose()
