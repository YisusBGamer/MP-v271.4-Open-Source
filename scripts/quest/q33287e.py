# 33287 - [Black Heaven] Memories of the Black Heaven
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Orchid (1540763)

sm.setSpeakerID(1540763)  # Orchid
sm.sendNext("Well, who was it? Orchid or Athena Pierce?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("You have found a friend who will stand by your side. You are ready to face any challenges that come your way.")
