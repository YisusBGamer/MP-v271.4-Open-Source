# 37607 [Reverse City] Abandoned Train - start: Rasol (3004611); end: Rasol (3004612) in #m450014100#
sm.setSpeakerID(3004611) # Rasol
sm.sendNext("Barr says T-Boy runs his experiments out of an #babandoned train#k in the subway tunnel.")
sm.sendSay("If we find it, we might find a way to beat him. Take the passage to the right of Barr's Abode.")
if not sm.sendAskAccept("Meet me in #b#m450014100##k. Ready?"):
    sm.dispose()
sm.startQuest(parentID)
