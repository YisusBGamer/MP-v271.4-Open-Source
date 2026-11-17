# id 1 (pt_l), field 940202032
# Academy Entrance after Escape from the Academy (34818): on to the Sanctuary Entrance (940202035, which returns here),
# where Ex takes over (An Elder Charge, 34819)
if sm.hasQuestCompleted(34818):
    sm.warp(940202035)
else:
    sm.setSpeakerType(3)
    sm.setParam(37)
    sm.setColor(1)
    sm.setInnerOverrideSpeakerTemplateID(3001353) # Illium
    sm.sendNext("#face3#I need to get the hang of handling this crystal. It's pretty powerful.")
