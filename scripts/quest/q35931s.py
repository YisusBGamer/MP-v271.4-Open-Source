# 35931 - Pathfinder 3rd job advancement: Relic's Guidance - Perion (Quest.wz: self-start, lv 60, job 330).
# Start: the relic points the way; the quest is handed in (q35931e) in Perion, where the job advancement happens.
sm.setPlayerAsSpeaker()
sm.sendNext("(The relic shines with a bright light that seems to be leading you somewhere specific, but where?)")
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.startQuest(parentID)
sm.dispose()
