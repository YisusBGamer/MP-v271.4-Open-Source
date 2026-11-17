# 23643 - Hello Henesys!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Chief Stan (1012003), taken from the other quest phase

sm.setSpeakerID(1012003)  # Chief Stan
if sm.sendNext("So, this is Henesys. I think we might stand out a little bit here. Maybe we oughtta go incognito. I mean, incog-neato!\r\n#L0##bHow do I blend in? I am covered in lights.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendAskAccept("You do sort of look like the future... but just act natural. I'm sure these folks see protoype robot fighters all the time! Maybe somebody could even help us out."):
    sm.startQuest(parentID)
    sm.sendNext("I think you oughtta find the #bperson who's lived in this town the longest#k. I bet THEY'D know how to blend in and be ignored!")
else:
    sm.sendNext("Nobody can live on their own. You gotta make friends to get along!")
