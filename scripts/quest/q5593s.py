# 5593 - Cassandra's Makeover Mission - Week 4
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("There's so much paperwork involved with this Cassandra's Makeover Mission - Week Event that I haven't slept in days...and I've never felt more alive in my life! There's a #rVERY SPECIAL#k reward involved with this event, too. Want to hear?"):
    sm.startQuest(parentID)
    sm.sendNext("It's the #b999 Monster Mop-up#k mission! All you have to do is #reliminate 999 monsters#k, and all I have to do is file some very complex paperwork for each and every one. Doesn't it sound like a dream?")
    sm.sendSay("Just one tiny disclaimer: The monsters must be no more than #b11 levels below you or 21 levels above you for them to count#k. Also, there's a #rspecial bonus reward#k waiting for you if you #bcomplete this mission 1 times#k.")
else:
    sm.sendNext("Way to rain on my parade, #b#h0##k! I was really looking forward to this, too!")
