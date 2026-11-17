# id 331001000 (Hideout : HQ), field 331001000
# only while "Come to the 2nd floor" (22715) is in progress (it played on every visit)
if sm.hasQuest(22715):
    sm.setSpeakerType(3)
    sm.setParam(36)
    sm.setColor(1)
    sm.setInnerOverrideSpeakerTemplateID(1531001) # Jay
    sm.sendNext("Good job. Get up to the 2nd floor.")
    sm.lockInGameUI(False, True)
