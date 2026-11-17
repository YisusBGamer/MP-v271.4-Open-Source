# JM's Next Request
# 2866
# Someone's Hat (1052128). Official lines (Quest.wz Say.img).

if sm.hasMobsInField():
    sm.setSpeakerID(1052128)
    sm.sendSayOkay("(Eliminate all of the Shades before investigating the hat.)")
else:
    sm.setPlayerAsSpeaker()
    sm.sendNext("What's this hat?")
    sm.sendNext("A hat in a place like this... Did the Shades have it?")
    sm.sendNext("This looks like a public service worker's hat. Could it be Jake's? I should check on Jake before I do anything else.")
    sm.completeQuest(parentID)
