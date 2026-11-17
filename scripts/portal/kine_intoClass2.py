# into_classroom in the Second Floor Corridor (331002100): 2-1 Classroom, where the Motes are (22727, 22728)
if sm.hasQuest(22727) or sm.hasQuest(22728):
    sm.warp(331002400, 1)
else:
    sm.setSpeakerType(8)
    sm.setParam(3)
    sm.sendSayOkay("I don't need to check this classroom.")
