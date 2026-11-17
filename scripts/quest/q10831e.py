# 10831 - The Maple Administrator's Package
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
sm.sendNext("This is the item I asked Gold Richie for! Thank you for bringing it to me. I have to send it right away...")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("I had heard that it was lost in a monster attack... Thank you so much!")
