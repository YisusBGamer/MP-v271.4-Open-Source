# 37610 [Reverse City] The Find - end: Rasol (3004613)
# Official summary: you secured T-Boy's Research Notes. Per the official story, they reveal the Singularity Maker:
# T-Boy plans to create a black hole, fuelled by Erda.
sm.setSpeakerID(3004613) # Rasol
if not sm.hasItem(4036633, 20):
    sm.sendSayOkay("Gather T-Boy's Research Note x20.")  # official in-progress line
    sm.dispose()
if sm.completeQuest(parentID):
    sm.consumeItem(4036633, 20)
    sm.sendNext("Let me see... '#bSingularity Maker#k'? He's building a machine that makes a #bblack hole#k...")
    sm.sendSay("And it runs on #bErda#k. If he turns that thing on, there won't be a city left to be upside-down!")
    sm.sendSay("(The door to the next car is sealed... until #bBarr#k shows up and opens it. He admits T-Boy had been dropping hints about this plan while they talked about Barr's books.)")
    sm.sendSayOkay("(You all head back to #bBarr's Abode#k to decide what to do.)")
    sm.warp(450014090, 0)
