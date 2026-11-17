# id 34930 (Fuzzy Signal), field 402000621
sm.setSpeakerType(3)
sm.setParam(36)
sm.setColor(1)
sm.setInnerOverrideSpeakerTemplateID(3001510) # Ferret
sm.sendNext("#face2#It must be the Gale Foxes around here causing the sandstorm.")
sm.sendSay("#face0#I guess we'll just have to wait for it to calm down again.")
sm.setInnerOverrideSpeakerTemplateID(3001500) # Ark
sm.sendSay("#face0#If we clear some of them out, that should help the signal, right? I can do that.")
sm.setSpeakerType(4)
sm.setSpeakerID(3001409) # Ferret
sm.setInnerOverrideSpeakerTemplateID(3001510) # Ferret
res = sm.sendAskAccept("#face0#That's a good idea. Let's see if hunting about #b50#k #b#o2400356##k monsters does any good.")
sm.setSpeakerType(3)
sm.sendNext("#face0#I'll keep trying to pick up the signal. Everyone else should help Ark.")
sm.setInnerOverrideSpeakerTemplateID(3001509) # Salvo
sm.sendSay("#face1#Well, if Mr. Bossy says we help Ark, I guess we help Ark. Let's get to it, everyone!")
sm.startQuest(parentID)
sm.showEffect("Effect/BasicEff.img/monsterCollectionGet", 0, 4)
sm.startQuest(parentID)
