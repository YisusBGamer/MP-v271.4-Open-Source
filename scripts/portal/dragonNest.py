# Portal script dragonNest.
# Below the Dangerous Nest: Nine Spirit's Nest. After Dragon Squad's Mission (3706) you can visit the baby dragon.
# During the mission, carrying the Nine Spirit's Egg, you take it back to the nest, where the baby hatches
# (talk to it to finish the quest).
if sm.hasQuestCompleted(3706):
    sm.warp(240040612, 1)
elif sm.hasQuest(3706) and sm.hasItem(4001094):
    sm.warp(240040611, 1)
    sm.spawnNpcIfAbsent(2081008, -8, -239)
else:
    chr.chatMessage("A strange force is blocking you from entering.")
sm.dispose()
