# [Daily] Punish Yakuza (65310-65315, end) | Pipopa (9120014)
# Each one counts as a mission for Help Showa Town (65307: 65308 "count", 2).
# Act.img gives nothing. EXP is about what the 200 kills give (Yakuza Lieutenant, lv 168, 20,754), lv 160+ like the Kritias
# weeklies (12,000,000 for 250-300 kills, once a week).
MISSIONS = 65308
MISSION_GOAL = 2
EXP = 4150000

sm.setSpeakerID(9120014)
if sm.questEndDialogue(parentID, 9120014):
    sm.giveExp(EXP)
    v = sm.getQRValue(MISSIONS, "count")
    n = int(v) if v and v.isdigit() else 0
    if n < MISSION_GOAL:
        sm.setQRValue(MISSIONS, "count", str(n + 1))
elif sm.hasQuest(parentID):
    sm.sendSayOkay("Those Yakuza won't punish themselves. Come back when the job's done.")
sm.dispose()
