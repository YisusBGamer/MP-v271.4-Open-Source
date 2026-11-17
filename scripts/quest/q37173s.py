# id 37173 ([Elodin] Singing Necessities 1), field 101084400
sm.setSpeakerID(1501004) # Shimmer Songbird
sm.setParam(4)
sm.setInnerOverrideSpeakerTemplateID(1501015) # Shimmer Songbird
sm.sendNext("Before you can sing, you have to prep your voice.")
sm.sendSay("Drinking #i4036505# #t4036505# from the #o3501108#s and #o3501109#s nearby will give you nice, clear notes.")
res = sm.sendAskYesNo("Here's another Small Bottle. Please fill it with Pure Water and come back.")
BOTTLE = 4220197 # Small Bottle (double-click it, then drag Pure Water onto it)
if not sm.hasItem(BOTTLE) and not sm.canHold(BOTTLE):
    sm.sendSayOkay("Make some room in your Etc inventory first, so I can give you the Small Bottle.")
else:
    sm.startQuest(parentID)
    if not sm.hasItem(BOTTLE):
        sm.giveItem(BOTTLE)
    sm.sendNext("I'll need #r15 droplets of Pure Water#k this time.")
    sm.warp(101084100)
