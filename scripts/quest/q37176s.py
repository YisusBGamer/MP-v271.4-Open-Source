# id 37176 ([Elodin] Another Chance!), field 101084400
sm.setSpeakerID(1501004) # Shimmer Songbird
sm.setParam(4)
sm.setInnerOverrideSpeakerTemplateID(1501015) # Shimmer Songbird
sm.sendNext("Would you bring Pure Water and lotus flowers one more time?")
sm.sendSay("Please fill this Small Bottle with #r21 droplets of Pure Water#k and find #r10 Lotus Blossoms#k. That should be just enough to do the trick.")
sm.sendSay("This bottle will automatically fill every time you get Pure Water, so you won't have to pick it up yourself anymore.")
sm.setParam(2)
sm.sendSay("That would have been helpful earlier...")
sm.setParam(4)
sm.sendSay("...")
sm.sendSay("Well, earlier I didn't realize you were such a whiner. Maybe I won't give you this bottle after all.")
sm.setParam(2)
sm.sendSay("No! Forget I said that! I love running errands for you!")
sm.setParam(4)
sm.setInnerOverrideSpeakerTemplateID(1501010) # Baby Bird
sm.sendSay("Ooh! More water and flowers! I can't wait!")
sm.setParam(2)
res = sm.sendAskYesNo("Great...")
BOTTLE = 4220198 # Small Bottle (fills by itself as you pick up Pure Water 2630243, see item/consume_2630243)
if not sm.hasItem(BOTTLE) and not sm.canHold(BOTTLE):
    sm.setParam(4)
    sm.setInnerOverrideSpeakerTemplateID(1501015) # Shimmer Songbird
    sm.sendSayOkay("Make some room in your Etc inventory first, so I can give you the Small Bottle.")
else:
    sm.startQuest(parentID)
    if not sm.hasItem(BOTTLE):
        sm.giveItem(BOTTLE)
    sm.setParam(4)
    sm.setInnerOverrideSpeakerTemplateID(1501015) # Shimmer Songbird
    sm.sendNext("Do you think that will be enough?")
    sm.setParam(2)
    sm.sendSay("Fingers crossed.")
    sm.setParam(4)
    sm.sendSay("Well, good luck.")
    sm.warp(101084100)
