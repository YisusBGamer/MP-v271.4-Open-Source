# 11653 - [3rd Day of Memories] Memories of Monkeys
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("You've got all 50 #t3994388#s? Let's see... Wait, this is a #t3994388#? It looks just like a normal one? What is #p9000021#'s deal?! He'd be so cute if he weren't so weird...")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Well, thanks for your help. I gave you some EXP, and I'd appreciate it if you could bring me more 'Good Looking' Monkey Dolls every 5 minutes or so. #p9000021# is anxious to rebuild his collection, so...do it for him, I guess.")
