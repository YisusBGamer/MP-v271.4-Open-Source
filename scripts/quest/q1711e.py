# 1711 - The Shimmering Bristles of Victory
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Spiegelmann (9074100)

sm.setSpeakerID(9074100)  # Spiegelmann
sm.sendNext("Did you bring the moisturizer? Are my new bristles are destined to dry out!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Why, oh why, is my lip still cold? Who could ever love a smooth-faced goblin like me?!")
