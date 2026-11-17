# 10828 - Gold Richie's Packages
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Gold Richie (2084001)

sm.setSpeakerID(2084001)  # Gold Richie
if sm.sendAskAccept("I've been waiting for you. Today, you'll be picking up and delivering items. It's urgent, so you'll have to hurry. Do you think you can do that for me?"):
    sm.startQuest(parentID)
    sm.sendNext("A few days ago, Cassandra, Gaga, and Maple Admin placed an order. Their orders came today, but the monsters stole the items! These are important items, and the Black Wings cannot find out about them...")
    sm.sendSay("I wrapped the items with #b#t4032803##k. I'm sure you'll be able to find them quickly, since I don't think the monsters have gone too far. As soon as you find the items, deliver them to #bGaga#k, #bCassandra#k, and #bMaple Admin#k. Check in with me after you make the deliveries. I'm counting on you.")
else:
    sm.sendNext("Oh... I guess you're busy. Come by if you have some time later today.")
