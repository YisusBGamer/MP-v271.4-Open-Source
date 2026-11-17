# 23107 - The First Mission
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Claudine (2151003)

sm.setSpeakerID(2151003)  # Claudine
sm.sendNext("Greetings, #h0#! My name is #p2151003#, and I am in charge of Resistance mission assignments. It's strange seeing you here instead of in town... ")
sm.sendSay("I'm qualified to be a Thief Job Instructor but I've settled for this position, since the Resistance doesn't actually train thieves.")
sm.tryCompleteQuest(parentID)
