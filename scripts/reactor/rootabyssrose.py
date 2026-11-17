# Purple / Blue Rose (reactors 1058022-1058029), Root Abyss South Garden: clicking one paints it red, and counts for
# "[Root Abyss Daily] Rose Red" (30018: paint 5 of them, the quest's "rose" record).
QUEST = 30018
NEEDED = 5

if reactor.getState() == 0 and sm.hasQuest(QUEST):
    rec = sm.getQRValue(QUEST, "rose")
    done = int(rec) if rec and rec.isdigit() else 0
    if done < NEEDED:
        done += 1
        sm.setQRValue(QUEST, "rose", str(done))
        sm.chatScript("Roses painted red: %d/%d" % (done, NEEDED))
        reactor.increaseState()
        reactor.broadcastChangeState()
sm.dispose()
