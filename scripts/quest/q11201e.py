# 11201 - [Vocal] You've Got Talent!
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cygnus (9010031)

sm.setSpeakerID(9010031)  # Cygnus
sm.sendNext("Did you find them all? Let me check... Do, Re, Mi, Fa, So, La, Ti, and Do! Lovely!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Oh, now THAT is perfect pitch. #bCygnus#k is well on her way to winning the singing competition!")
    sm.sendSay("To help #bCygnus#k build her vocal skills even more, you can bring the bells again, up to five times per day. #b#h0##k, you can complete it count more times. If you have the time, it would be a big help!")
