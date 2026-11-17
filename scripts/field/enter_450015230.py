# Yum Yum Island map 450015230 (where the Giant Flower and the creature that looks like Afrien are). [Yum Yum]
# Familiar, But Not (37721): the creature (8642091) is back when you return while the quest still needs it beaten (it
# first appears from the quest's start script, q37721s).
if sm.hasQuest(37721) and not sm.isComplete(37721) and not sm.hasMobsInField(450015230, 8642091):
    sm.spawnMob(8642091, 90, 117, False)
