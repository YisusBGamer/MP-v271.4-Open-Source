# 14082 - [RED] Critical Adventure Ring Challenge
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("What up, #e#b#h0##k#n?\r\n I'm soooo excited about this #e#rRED update#k#n! The only thing that gets me MORE excited is jewelry... do I have some sweet jewelry to tell you about? Yes I do!"):
    sm.startQuest(parentID)
    sm.sendNext("Yes! I'm talking about finger bling, digit adornments... #eRINGS#n! I've got this super cool, super rare ring that I will give to you, IF you reach #e#blevel 70#k#n. Aren't I the best?\r\n#i1113068:# #t1113068:#")
    sm.sendSay("I am going to be sitting here, and watching you, and spying on you at your house until you get to 70. Do not let me down.")
else:
    sm.sendNext("Are you kidding? Think of your fingers!")
