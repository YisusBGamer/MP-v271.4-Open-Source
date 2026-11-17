# 51208 - OSSS Mission 2: Protecting the Scientists
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Head Researcher Wynn (9250133)

sm.setSpeakerID(9250133)  # Head Researcher Wynn
sm.sendNext("Have you completed the mission?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Thank you for your work. Here is your medal. Head over to #b#p9250135##k to purchase various cells that can be used to upgrade your suit.")
