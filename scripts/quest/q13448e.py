# 13448 - [A New World] Taming the Frontier
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("Well, look at you! #b#eLv. 80#n#k already. Well, go on and take your new #e#b#t1032176##k#n. I'm giving them to you because you deserve them and not because they make my skin look green when I wear them. Enjoy!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("I knew you could do it. In fact, I was totally counting on it. Now I can buy something else, squeeeeeal!")
