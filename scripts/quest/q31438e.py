# 31438 - Continuous Supply
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maroon (3001009)

sm.setSpeakerID(3001009)  # Maroon
sm.sendNext("Do you have the supplies? Our stockpile was lower than I had thought.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Wonderful! I'm amazed you can get so much meat to us without it spoiling. Thank you.")
    sm.sendSay("And here's your compensa... Huh? You don't need compensation? You truly are a model solider!")
