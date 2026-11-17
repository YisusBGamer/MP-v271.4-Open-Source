# 31039 - [Rose Garden] The Queen's Bouquet
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Ifia (2161001)

sm.setSpeakerID(2161001)  # Ifia
sm.sendNext("Cecilia was such a wonderful woman. I still remember teaching her to care for the flowers. The castle staff said it was beneath me, but I could not resist tending the garden when I had the chance.\r\n")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Is this not the most beautiful bouquet?")
