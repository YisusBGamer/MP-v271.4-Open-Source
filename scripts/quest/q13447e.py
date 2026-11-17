# 13447 - [A New World] Pioneer Pinnacle
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("Wow! You really reached Level 70? Okay, honestly, I didn't expect that. I guess I owe you your #t1003754#, then. I was gonna wear it, but...whatever...")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("You've got spirit, but I wonder... Are you the hero Pinnacle needs...?")
