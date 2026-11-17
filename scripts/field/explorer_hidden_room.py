# 910350240 Thief Hideout (see CustomFieldScripts): The Hidden Room (32259) - a masked intruder (Suspicious Rabbit
# 9300802) in the jewel room.
if sm.hasQuest(32259) and not sm.isComplete(32259) and chr.getInstance() is not None and not sm.hasMobsInField(910350240, 9300802):
    pos = chr.getPosition()
    sm.spawnMob(9300802, pos.getX() + 250, pos.getY(), False)
    sm.chat("A masked intruder is in the jewel room!")
