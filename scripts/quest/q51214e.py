# 51214 - OSSS Mission Issued
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Head Researcher Wynn (9250133)

sm.setSpeakerID(9250133)  # Head Researcher Wynn
sm.sendNext("Have you completed all the missions?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Good job. Now, you'll be able to receive each of the missions again. Start by completing the OSSS Mission Order mission.")
