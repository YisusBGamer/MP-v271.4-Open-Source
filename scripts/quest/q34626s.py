# 34626 - Investigate the Brawl 'n' Bash Club 1
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Hazard's Vile Crony (3001237)

sm.setSpeakerID(3001237)  # Hazard's Vile Crony
sm.sendNext("Whadya want? There ain't no matches right now. Get lost.")
sm.sendSay("Ya better not be more troublemakers from Gen's branch.\r\n")
sm.setSpeakerID(3001206)  # Coney
sm.sendSay("No. We're here for advance registration for the Coliseum Arena. I'm a stickler for getting things done early. Haha.")
sm.setSpeakerID(3001237)  # Hazard's Vile Crony
sm.sendSay("Oh, that's all? There's a participation fee. It's 15 #i4036147:# #b#t4036147##k.\r\n")
if sm.sendAskAccept("And anyway, it's all to protect the competitors. Mr. Hazard's not that bad, really.\r\n"):
    sm.startQuest(parentID)
    sm.sendNext("You can get #i4036147:# #b#t4036147##k from those #r#o2400307#s#k over in #bStray Dog Alley#k. Should be easy for a couple o' tough guys like you, right?\r\n")
else:
    sm.sendNext("And don't whine to me about price. It is what it is.\r\n")
