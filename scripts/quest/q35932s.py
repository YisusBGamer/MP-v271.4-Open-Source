# 35932 - Pathfinder 4th job advancement: Relic's Guidance - Aquarium (Quest.wz: self-start, lv 100, job 331).
# Start: the relic points the way; the quest is handed in (q35932e) in Aquarium, where the job advancement happens.
sm.setPlayerAsSpeaker()
sm.sendNext("(The relic shines with a bright light that seems to be leading you somewhere specific, but where?)")
if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.startQuest(parentID)
sm.dispose()
