# 57104 - The New Base
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Yamanaka Yukimori (9130024)

sm.setSpeakerID(9130024)  # Yamanaka Yukimori
sm.sendNext("This is Momijigaoka. Many great leaders preside within its walls. Ask around, and you will find the path to your destiny.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("I will take my leave. I'm glad I found another survivor in this strange land. I wish you well.")
    sm.sendSay("Oh, if you have lost your weapon, take one of mine. I know they are not of noble standing, but they will serve you well.")
