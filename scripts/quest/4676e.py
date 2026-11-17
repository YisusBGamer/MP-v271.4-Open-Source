# 4676 - Naomi's Invitation 2
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Naomi (9105002)

sm.setSpeakerID(9105002)  # Naomi
sm.sendNext("You're back! How many did you wind up gathering? Are you interested in trading them in for a prize right now?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Alright, now based on how many #b#t4032056#s#k you have, you'll be receiving this. I'll give out 1 free event ticket per day, so I'll be expecting to see you here tomorrow!")
    sm.sendSay("What? You want to join this event again today? Well... if you really insist, we sell the special tickets that'll allow you to participate in this event more than once in one day! You can find them at the Cash Shop, so... good luck!")
