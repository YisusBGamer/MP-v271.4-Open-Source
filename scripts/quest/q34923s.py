# id 34923 (Mar's Wish), field 402000613
sm.setSpeakerType(3)
sm.setParam(36)
sm.setColor(1)
sm.setInnerOverrideSpeakerTemplateID(3001511) # Mar
sm.sendNext("#face0#You're strong!")
sm.sendSay("#face2#Maybe you're strong enough to make my wish come true!")
sm.setSpeakerType(4)
sm.setSpeakerID(3001404) # Mar
res = sm.sendAskAccept("#face0#I wish for #b10#k #i4036347# #b#t4036347##k items! The #o2400353# right in front have it!")
sm.setSpeakerType(3)
sm.sendNext("#face2#Oh, good! But I can't tell you what I want them for. It's tippy top secret!")
sm.startQuest(parentID)
sm.playSound("DarkParty/scream1", 100)
sm.startQuest(parentID)
sm.playSound("DarkParty/scream2", 100)
