# 28634 - Monitoring the Vibrations
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("The detector's done? Okay, let's see how the good vibes are doing...")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("I was afraid of this... Maple World is critically low on good vibes! I'll have to think of a way to fix this, but for now, have some EXP for helping me out. Come back later, and maybe we can do something to bring the good vibes back.")
