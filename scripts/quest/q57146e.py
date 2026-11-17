# 57146 - Tae Soo and You
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Tae Soo (2091003)

sm.setSpeakerID(2091003)  # Tae Soo
sm.sendNext("How's the Peach Monkey and Reindeer situation?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("That was brilliant. They're as docile as can be now. This year will bring a magnificent harvest after all.")
    sm.sendSay("What motivation do your enemies have for ruining my precious peaches? There must be something for them to gain.")
