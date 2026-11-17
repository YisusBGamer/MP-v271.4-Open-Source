# 14083 - [RED] Pivotal Adventure Ring Challenge
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("I see you're still taking good care of that #b#t1113068##k I gave you. That makes sense, it's sentimental, I get it. But what if I told you I had something else?"):
    sm.startQuest(parentID)
    sm.sendNext("IF, and only if, you reach level 130, I will take that old crummy crumb-bum ring off of you, and give you this new, and much fancier #ering#n in its place. Pretty neato, yesss?\r\n#i1113069:# #t1113069:#")
    sm.sendSay("I'm not going to watch you this time. I saw that weird conversation you had with the frozen yogurt guy, and it sort of spoiled my enjoyment of watching other peoples' lives. But come back when you hit #e#r130#k#n!")
else:
    sm.sendNext("Are you kidding? Think of your fingers!")
