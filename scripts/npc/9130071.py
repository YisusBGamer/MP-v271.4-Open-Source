# Princess Sakuno (9130071), Honnou-ji Temple (807050202) / Eastern Grounds (807050203) during Honnou-ji Infiltration 2
# (Hayato 57141, Kanna 57437): leads you out to the Eastern Wall (807050204), where the quest is handed in.
QUESTS = (57141, 57437)
field = sm.getFieldID()
if any(sm.hasQuest(q) for q in QUESTS) and field in (807050202, 807050203):
    sm.setSpeakerID(9130071)
    sm.sendNext("Yes? You have something to tell me?")
    sm.warp(807050204, 0)
else:
    sm.setSpeakerID(9130071)
    sm.sendSayOkay("Come. Have a seat. Refreshments would be nice.")
sm.dispose()
