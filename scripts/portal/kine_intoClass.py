# into_classroom in the First Floor Corridor (331002000): 1-1 Classroom, where Hero On The Scene (22727) ends
if sm.hasQuest(22727) or sm.hasQuestCompleted(22726) and not sm.hasQuestCompleted(22729):
    sm.warp(331002300, 1)
else:
    sm.setSpeakerType(8)
    sm.setParam(3)
    sm.sendSayOkay("I don't need to check this classroom.")
