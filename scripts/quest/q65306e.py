# Showa Town Helper (65306, end) | Pipopa (9120014)
# Done after Help Showa Town (65307) twice (65309 "count"); the count starts over for the next round.
# Act.img gives nothing. EXP: a bonus for two days of Help Showa Town (about two missions' worth).
HELPER = 65309
EXP = 8000000

sm.setSpeakerID(9120014)
if sm.questEndDialogue(parentID, 9120014):
    sm.setQRValue(HELPER, "count", "0")
    sm.giveExp(EXP)
elif sm.hasQuest(parentID):
    v = sm.getQRValue(HELPER, "count")
    sm.sendSayOkay("Help Showa Town a little more first. (%s/2)" % (v if v and v.isdigit() else "0"))
sm.dispose()
