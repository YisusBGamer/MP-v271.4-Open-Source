# 59135 - Sword Surprise
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Dug (9390352)

sm.setSpeakerID(9390352)  # Dug
sm.sendNext("Have you drawn a sword before?\r\n")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Oh, well that's too bad.")
    sm.sendSay("Oh, would you look at that? I guess the sword crumbled into rusty dust. That'll happen when you stick a metal thing in a cave for over a decade...")
    sm.sendSay("Good work, anyway. Tell #b#p1101004##k, would you?")
