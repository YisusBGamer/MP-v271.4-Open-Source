# 39106 [Weekly Quest] Haven: Defeat Repairoid (One-Eye 2155000, Haven): a weekly commission. Hands in what it asks for, its EXP (Quest.wz
# QuestInfo reward) and counts it toward the Request Reward (39160: four a week, record 39100 FC).
ITEMS = ()
EXP = 50000000

sm.setSpeakerID(2155000)
if not sm.isComplete(parentID):
    sm.sendSayOkay("You still haven't completed the request. We really need this!")
else:
    for item, count in ITEMS:
        sm.consumeItem(item, count)
    sm.completeQuestNoCheck(parentID)
    sm.giveExp(EXP)
    fc = sm.getQRValue(39100, "FC")
    sm.setQRValue(39100, "FC", str((int(fc) if fc and fc.isdigit() else 0) + 1))
    sm.sendSayOkay("Heehee, nice work, Squishy! That's one more commission done.")
sm.dispose()
