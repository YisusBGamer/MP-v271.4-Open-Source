# 21607 - The Adolescent Wolf
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Pucci (1202007)

sm.setSpeakerID(1202007)  # Pucci
sm.sendNext("Did you receive a lot of good advice from others before you came here? So what do you think? Do you think it helped #t1902015#? Let's see how much #t1902015# has grown since the last time we talked. Please hand me #t1902015#.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Oh, I can see that #t1902016# has grown considerably! You can't even call it a pup anymore! It's gone through adolescence, so... maybe a teen wolf?")
    sm.sendSay("It's all because you were able to take such good care of my #t1902016# during its adolescent years. Please take care of my #t1902016# for me.")
