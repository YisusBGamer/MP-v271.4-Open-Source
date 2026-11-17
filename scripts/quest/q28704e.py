# 28704 - Admin's Heart
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
sm.sendNext("You brought 100 Chocolates? Just for me?\r\n")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Thank you so much! My preparations are going much more smoothly thanks to you, XX! If you bring me 100 more, I should be ready to profess my love in no time.\r\n")
