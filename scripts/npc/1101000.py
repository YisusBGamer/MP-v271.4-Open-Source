# Cygnus (1101000), Ereve
# Mihiles who left the old store tutorial (20031-20035) before the v265 story records were set: Light Reborn (20810,
# 2nd job) needs 36630. Mihiles playing the v265 story (36601 on) never did 20035.
STORY = [36601] + [q for q in range(36603, 36631) if q not in (36622, 36626)]

sm.setSpeakerID(parentID)
if 5100 <= chr.getJob() <= 5112 and sm.hasQuestCompleted(20035) and not sm.hasQuestCompleted(36630):
    for q in STORY:
        if not sm.hasQuestCompleted(q):
            sm.completeQuestNoRewards(q)
    sm.sendSayOkay("Mihile, I've been waiting for you. Once you reach level 30, come to me for your knighthood.")
else:
    line = sm.getRandomNpcDefaultLine(parentID)
    if line is not None:
        sm.sendSayOkay(line)
sm.dispose()
