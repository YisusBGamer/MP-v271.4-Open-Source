# 34626 - Investigate the Brawl 'n' Bash Club 1
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Hazard's Vile Crony (3001237)

sm.setSpeakerID(3001237)  # Hazard's Vile Crony
sm.sendNext("Wow, 30 #i4036147:# #t4036147#! If that's not a mission accomplished, I don't know what is!\r\n")
sm.sendSay("Fair enough. Welcome to the competition. You may enter.\r\n")
sm.setSpeakerID(3001206)  # Coney
sm.sendSay("They ran off pretty fast...\r\n")
sm.sendSay("We're in! But, I can't help wondering what they were going to do with those gross tails.\r\n")
if sm.tryCompleteQuest(parentID):
    sm.setPlayerAsSpeaker()
    sm.sendNext("I hate those slimy cronies of Mr. Hazard's, but I'll just have to pay them back later. I've got other things on my plate right now.")
