# Help Showa Town (65307, end) | Pipopa (9120014)
# Done after 2 Punish Yakuza missions (65308 "count"). Counts toward Showa Town Helper (65306: 65309 "count", 2).
# Act.img gives nothing. EXP: a bonus about one Punish Yakuza mission's worth.
MISSIONS = 65308
HELPER = 65309
HELPER_GOAL = 2
EXP = 4000000

sm.setSpeakerID(9120014)
if sm.questEndDialogue(parentID, 9120014):
    sm.setQRValue(MISSIONS, "start", "0")
    sm.giveExp(EXP)
    v = sm.getQRValue(HELPER, "count")
    n = int(v) if v and v.isdigit() else 0
    if n < HELPER_GOAL:
        sm.setQRValue(HELPER, "count", str(n + 1))
elif sm.hasQuest(parentID):
    v = sm.getQRValue(MISSIONS, "count")
    sm.sendSayOkay("Finish two of today's Punish Yakuza missions first. (%s/2)" % (v if v and v.isdigit() else "0"))
sm.dispose()
