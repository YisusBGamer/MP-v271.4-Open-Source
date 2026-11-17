# 32917 - [Orange Mushroom] Checking on Henesys
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Scholarly 'Shroom (9000292)

sm.setSpeakerID(9000292)  # Scholarly 'Shroom
sm.sendNext("I see. Henesys... Maybe I'll have to find a place to settle down someday too. Wherever it may be, I hope it's known for learning.")
sm.sendSay("Thanks to you, that's one mystery down! I can write the Orange Mushroom chapter. Check that book that I gave you.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Thanks for your hard work. I'll give you #b30#k #i4310119# #b#t4310119##k! Hope this helps!")
