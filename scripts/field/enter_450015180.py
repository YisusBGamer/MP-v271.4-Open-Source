# Yum Yum Island map 450015180 (where Muppy is). [Yum Yum] Awakened Meaning (37716): Caster (8642090) has to be beaten;
# he is back when you return while the quest still needs it (he first appears from the quest's start script, q37716s).
if sm.hasQuest(37716) and not sm.isComplete(37716) and not sm.hasMobsInField(450015180, 8642090):
    sm.spawnMob(8642090, 150, 103, False)
