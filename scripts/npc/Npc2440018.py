# NPC 2440018's Npc.wz script name is "Npc2440018", so this file runs when it is clicked instead of 2440018.py.
# Keep the two the same.
# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). Do Gong (2440018), at the end of the long bridge - Path of the Sage 3 (40302)
sm.setSpeakerID(2440018)
if sm.hasQuest(40302) and not sm.hasItem(4033833):
    sm.sendNext("You crossed the whole bridge without falling? Fine, fine. Here's your proof.")
    sm.giveItem(4033833)
else:
    sm.sendSayOkay("The bridge is the training. I'm just the one who waits at the end.")
sm.dispose()
