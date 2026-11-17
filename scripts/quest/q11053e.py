# 11053 - Battle Mode MVP
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Rosette (9070005)

sm.setSpeakerID(9070005)  # Rosette
sm.sendNext("Hey, you did it! I had a feeling you could handle 3 wins. Nothing to it, right? 'Course not. So, here's your gift. I'm sure it'll come in handy.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("So, you're all set for another mission, right?")
