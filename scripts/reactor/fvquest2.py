# Artemisia (2222000): four hits (Reactor.wz states 0-3) break it.
HITS = 4
reactor.incHitCount()
if reactor.getHitCount() >= HITS:
    sm.removeReactor()
