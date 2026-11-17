# 28928 - [Charity: Water] Water from Droplets
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("Oh wow! Was it hard? Think about having to do way more than that every day just to have a sip of water! It really makes you think, huh? I'll give you a reward for your hard work!\r\n")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("If you wanna keep helping out, just let me know.")
