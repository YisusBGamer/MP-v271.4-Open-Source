# 23630 - NOT a Black Wing!
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Claudine (2151003)

sm.setSpeakerID(2151003)  # Claudine
sm.sendNext("I hear you've made quite an impression. Let me be the first to welcome you to the Resistance.")
sm.sendSay("I am #b#p2151003##k. I hand out the mission assignments, and act as the doctor for this town. Your first mission is to go introduce yourself to the other instructors.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("I would like you to get to know the people of Edelstein. It's impossible for anyone to protect people if they don't know who those people are.")
