# 14525 - [Event] Icebreakers
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Agent Chillson (9000354)

if sm.tryCompleteQuest(parentID):
    sm.setSpeakerID(9000354)  # Agent Chillson
    sm.sendNext("I knew it! I knew you would take on the challenge!")
    sm.sendSay("The Giant Ice Block will appear every hour from QRQRQR to QRQRQR.")
    sm.sendSay("Good luck!")
