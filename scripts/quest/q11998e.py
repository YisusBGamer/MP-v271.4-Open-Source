# 11998 - [Renegades] Alliance Support
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
sm.sendNext("Good day to you, #h0#. The thieves and pirates of the world are becoming stronger after their awakening. That's why we've prepared a special gift for those special adventurers who reach Level 70. You can take one #e#bSpecial Mastery Book#k#n of your liking!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("You can use the Special Mastery Book to boost a 4th advancement skill's Master Level. Pretty impressive, huh?")
