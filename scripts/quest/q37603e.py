# 37603 [Reverse City] Surviving in the Jungle - end: Rasol (3004609), Subway Tunnel 4
# Official summary: you heard about Barr from Rasol.
sm.setSpeakerID(3004609) # Rasol
sm.sendNext("There you are. You took a nasty hit from #bT-Boy#k's gravity gauntlet back there.")
sm.sendSay("If anyone can help us stand up to him, it's #bBarr#k. He's an overworlder, like you, from a place called Friends World. He's been helping the town since all this started.")
sm.sendSay("He's survived worse than this. Or so he says. He wrote a whole book about surviving.")
if sm.completeQuest(parentID):
    sm.sendSayOkay("But surviving down here starts with eating. Talk to me again.")
