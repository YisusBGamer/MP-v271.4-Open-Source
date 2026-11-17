# The mirror in Esfera (450007040): the gate to the Temple of Light / Radiant Temple (450007200). v265 opens it with
# [Esfera] The Trap (37867: "A gate appeared. Chase after Will"); 34580 is the old story's quest, blocked in v265.
if sm.hasQuestCompleted(37867) or sm.hasQuestCompleted(34580):
    sm.warp(450007200)
else:
    sm.sendSay("You are reflected in the mirror.")
