# 12893 - The Ultimate Soup
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mr. Do (9000121)

sm.setSpeakerID(9000121)  # Mr. Do
if sm.sendAskAccept("I've created a delicious soup that will make anyone who drinks it instantly cool and refreshed. Only...I left the recipe in Tae Sang's backyard. My friend, some things are better left unasked. Now, will you retrieve my recipe for me?"):
    sm.startQuest(parentID)
    sm.sendNext("Head to the backyard of Tae Sang's House. You'll find a pink peach tree. Beneath it is a wooden box. Inside that box should be my recipe. Again, my friend. Do not ask.")
    sm.sendSay("Head to the backyard of Tae Sang's House. You'll find a pink peach tree. Beneath it is a wooden box. Inside that box should be my recipe. Again, my friend. Do not ask.")
else:
    sm.sendNext("Why do you look upon me with suspicion in your eyes?!")
