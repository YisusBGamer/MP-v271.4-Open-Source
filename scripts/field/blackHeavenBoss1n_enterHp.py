# Lotus phase 1: restart the laser pattern
if hasattr(sm, "respawnLotusLaser") and sm.hasMobById(8950000):
    sm.respawnLotusLaser()
