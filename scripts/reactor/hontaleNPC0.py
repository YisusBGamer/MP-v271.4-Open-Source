# Nine Spirit's Nest (2406000): two hits (Reactor.wz states 0-1) open it and bring out the Nine Spirit baby (2081008).
HITS = 2
reactor.incHitCount()
if reactor.getHitCount() >= HITS:
    sm.spawnNpc(2081008, -16, -275)
    sm.removeReactor()
