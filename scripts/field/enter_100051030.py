# id 100051030 (Partem : Gorgonz's Place), field 100051030
sm.setMapTaggedObjectVisible("0", False, 0, 0)
# The scene copy of Gorgonz's Place is for "Gorgonz's Home" (Pathfinder story): players are sent there only while that
# quest is in progress, as the scene's own script expects (it completes the quest). That copy only sends players back
# here while the scene plays, so everyone else stays here, where the NPCs are.
GORGONZ_HOME = 35913
if sm.hasQuest(GORGONZ_HOME):
    sm.warp(910090308)
