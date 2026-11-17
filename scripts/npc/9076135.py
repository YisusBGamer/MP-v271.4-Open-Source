# 2x Reward (9076135) | Ursus (Entry Map): Golden Time
from net.swordie.ms.world.field.instance.instancehelper.events import UrsusInstanceHelper as UH
if UH.isGoldenTime():
    sm.sendSayOkay("#bIt's Golden Time!#k Ursus pays double Mesos right now (1-5 AM and 6-10 PM UTC).")
else:
    sm.sendSayOkay("Golden Time is 1-5 AM and 6-10 PM UTC. Ursus pays double Mesos then.")
