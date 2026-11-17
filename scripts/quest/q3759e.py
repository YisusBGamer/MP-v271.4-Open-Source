# Soaring skill quest

from net.swordie.ms.constants import JobConstants

sm.setSpeakerID(2085000)
# official lines (Quest.wz Say.img)
sm.sendNext("Were you able to obtain the #b#t4032531##k that I asked for?")
sm.sendSay("Okay. If you're ready, I'll finish up the potion and sprinkle it on you. Then, you'll be able to fly.")
sm.consumeItem(4032531)
sm.completeQuestNoRewards(parentID)
# Soaring is a beginner skill of each class group: 1026, 10001026 (Cygnus), 20001026 (Aran), ...
jobEnum = JobConstants.JobEnum.getJobById(chr.getJob())
sm.giveSkill((jobEnum.getBeginnerJobId() if jobEnum is not None else 0) * 10000 + 1026)
sm.chatScript("You have obtained the Soaring skill!")
sm.sendPrev("Now, you can fly! Just remember, you can only use the Flying Skill around Crimson Sky, including <Crimson Sky Ferry Site>. Then, good luck!")
