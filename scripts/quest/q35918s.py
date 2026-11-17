# id 35918 (Compass Fragment 1), field 100051031
sm.setSpeakerType(3)
sm.setParam(36)
sm.setColor(1)
sm.setInnerOverrideSpeakerTemplateID(1013350) # Brie
sm.sendNext("#face1#Waaaah! Don't come any closer! ...O-oh, I don't mean YOU; I'm talking to the bugs! Argh... If these Log-a-Rhythmic Bugs keep pestering us, there's no way we'll be able to look for those missing parts in peace.")
sm.sendSay("#face1#Okay, so, here's the plan. In order to find and retrieve those compass parts, we're gonna have to do a little bug squashing. And by 'we,' I mean 'you.'")
sm.setSpeakerType(4)
sm.setSpeakerID(1013312) # Brie
res = sm.sendAskAccept("#face1#If we thin their numbers out, maybe they'll stop incessantly thumping those logs and we can have a moment to gather our thoughts. I think we'll get the message across if you take out #r#o2300205##k x#b30#k.")
sm.startQuest(parentID)
