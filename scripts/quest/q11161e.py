# 11161 - Preparing Picnic Food
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
sm.sendNext("Are you ready? Let me see... Yes, it looks like you brought everything! Now, wait just a minute, and I will make a delicious Lunch Box for you.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("I hope you enjoyed the picnic Lunch Box. I can always make you another if you bring me and Empty Lunch Box and some food, so let me know if you're interested in more. ")
