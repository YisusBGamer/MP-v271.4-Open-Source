# 32803 - [FriendStory] Tell Me How You Feel 2
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Hilla (1530161)

sm.setSpeakerID(1530161)  # Hilla
sm.sendNext("Hey bro! Did you complete your mission?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Bro! Noice!")
