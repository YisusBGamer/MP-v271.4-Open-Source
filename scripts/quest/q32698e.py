# 32698 - [Grand Athenaeum] Winter Bard
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Thales the Librarian (2500002)

sm.setSpeakerID(2500002)  # Thales the Librarian
sm.sendNext("How was #e<Episode IV: Winter Bard>#n?\r\nMakes you think, doesn't it? There are heroes who have sacrificed everything to make the world a better place, and yet nobody knows their name. Their knowledge of doing good was their only reward.")
sm.sendSay("Now, do you want to try any other books? I'll give you a gift for reading everything!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Yes? Do you have a question?")
    sm.sendSay("You must be talking about the #bharp#k that was given to the Transcendent of Life, who left for Ereve soon afterwards.\r\nWho knows? Maybe the #bharp#k is still in Ereve. ")
