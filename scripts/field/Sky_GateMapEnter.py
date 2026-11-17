# Crimson Sky Dock (240080000): the Doorway stands high above the dock and the map needs Soaring to fly there.
# Party quest entrants (Lv. 120+) get it here, as they do inside; Matada's Towards the Sky 2 (Lv. 150) also teaches it.
from net.swordie.ms.world.field.instance.instancehelper.pq import DragonRiderInstanceHelper as DR

if chr.getLevel() >= DR.MIN_LEVEL:
    DR.giveSoaring(chr)
sm.dispose()
