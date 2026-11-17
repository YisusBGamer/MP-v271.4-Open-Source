# 11207 - [Join] I am a Valerie Fan
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Valerie (9010032)

sm.setSpeakerID(9010032)  # Valerie
sm.sendNext("Oh, #b#h0##k! You must be here to lend your support. Thank you so much.")
sm.sendSay("As a member of my fan club, please keep me in your thoughts, and help me become the Maple Rising Star!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Okay, #b#h0##k. Let's get this done!")
