# Help Showa Town (65307, start) | Pipopa (9120014)
# Daily. Opens the day's Punish Yakuza quests (65310-65315: their start check is 65308 "start" = 1) and starts the
# count of missions done (65308 "count", 2 to hand this in).
MISSIONS = 65308

sm.setSpeakerID(9120014)
# Only on a fresh start: clicking again while it is in progress must not wipe the count.
if not sm.hasQuest(parentID) and sm.questStartDialogue(parentID, 9120014):
    sm.setQRValue(MISSIONS, "start", "1")
    sm.setQRValue(MISSIONS, "count", "0")
sm.dispose()
