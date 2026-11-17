# 10827 - Gold Richie's Part-time Job
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Gold Richie (2084001)

sm.setSpeakerID(2084001)  # Gold Richie
sm.sendNext("Here's the contract. Just sign at the bottom. Then, I'll explain what your job is.")
sm.sendSay("I'll ask you to do something different each day, and I'll give you a generous reward, depending on how much you help me during the week. I think this is the beginning of a beatiful partnership!")
sm.tryCompleteQuest(parentID)
