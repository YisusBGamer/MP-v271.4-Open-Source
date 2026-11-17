# id 4 (out00), field 301070010
# Crimsonheart Castle (Masteria story, quests 31240-31261): asks whether to leave, then takes you back to the Path to
# the Altar.
sm.setSpeakerType(3)
sm.setParam(4)
sm.setInnerOverrideSpeakerTemplateID(2134012) # Ridley
if sm.hasQuest(31258):
    res = sm.sendAskYesNo("You haven't defeated Master Red Nirg yet. Are you going to leave?")
else:
    res = sm.sendAskYesNo("You defeated Master Red Nirg. If you leave right now, you will need to defeat Master Red Nirg again to check the barrier. Are you going to leave?")
if res:
    sm.warp(301070000, 5)
sm.dispose()
