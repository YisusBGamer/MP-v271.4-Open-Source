# Portal script summondragon.
# Nine Spirit's Nest: with the Nine Spirit's Egg during Dragon Squad's Mission (3706), the baby dragon hatches
# (talk to it to finish the quest).
if sm.hasQuest(3706) and sm.hasItem(4001094):
    sm.spawnNpcIfAbsent(2081008, -8, -239)
    chr.chatMessage("The egg trembles in the warmth of the nest...")
sm.dispose()
