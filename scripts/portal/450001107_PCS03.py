# Fire Zone Cliff (450001107), top ladder/portal up to Mirage Cliff.
# The cutscene copy of Mirage Cliff (450001390) is only for [Vanishing Journey] Crossing the Extinction Zone: after
# quest 34108 and before 34109. Otherwise go to the real Mirage Cliff (450001100, portal PV00 by this cliff) - the
# cutscene copy has no portals, so ending up there after the quest left you stuck.
if sm.hasQuestCompleted(34108) and not sm.hasQuestCompleted(34109):
    sm.warp(450001390)
else:
    sm.warp(450001100, 1)
