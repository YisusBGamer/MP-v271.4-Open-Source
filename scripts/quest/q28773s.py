# 28773 - [Easter Events] Decorate the Golden Egg!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("I think you're all set to start decorating the Easter Eggs. I think. I'm no good at this stuff.\r\nThe eggs break easily so be careful with them!"):
    sm.startQuest(parentID)
    sm.sendNext("Thank you so much! I knew you'd do it for me. It better be the most beautiful Easter Egg in the world when it gets back to me! I deserve nothing less!")
else:
    sm.sendNext("I really want to have an Easter Egg that's prettier than Gaga's.\r\nI'm really no good at decorating. Please make a beautiful Easter Egg for me!")
