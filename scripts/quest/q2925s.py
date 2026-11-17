# 2925 - Creature of the Distant Seas
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Muirhat (1092007)

sm.setSpeakerID(1092007)  # Muirhat
sm.sendNext("I just don't get it...")
sm.sendSay("A few days ago, I caught this Strange Creature in the ocean, but for the life of me, I can't figure out what it is. It makes these weird noises, and vanishes if you attack it. If it can't be hit, I'm out of ideas...\r\n")
if sm.sendAskAccept("Why don't you figure this out? You want to try?"):
    sm.startQuest(parentID)
    sm.sendNext("All right. Approach it with caution, and do what you can. If you learn anything, let me know immediately.")
    sm.sendSay("When you're ready, let me know, and I'll toss you in the room where the Strange Creature is. Don't worry...I'll let you back out.")
else:
    sm.sendNext("We need to do this for the sake of the Pirates. We can't let something like this go without figuring out how to destroy it!")
