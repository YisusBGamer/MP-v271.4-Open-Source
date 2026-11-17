# id 35924 (Strange Sounds 1), field 100051041
sm.setSpeakerType(3)
sm.setParam(36)
sm.setColor(1)
sm.setInnerOverrideSpeakerTemplateID(1013350) # Brie
sm.sendNext("#face1#Uh-oh. A bunch of stone sentries are blocking the way. I don't think they've-- Oh wait, no, they've definitely noticed us. Waaah! Why are these things still operational?!")
sm.setSpeakerType(4)
sm.setSpeakerID(1013315) # Brie
res = sm.sendAskAccept("#face1#As things stand, we can't continue on without doing something about those sentries. Hmm... I think if you clear out around #b50#k #r#o2300207##k, we should be able to make it through.")
sm.setSpeakerType(3)
sm.setParam(548)
sm.setInnerOverrideSpeakerTemplateID(1013358) # Pathfinder
sm.sendNext("#face0#Just wait here. I should be able to make short work of those boisterous blocks.")
sm.startQuest(parentID)
