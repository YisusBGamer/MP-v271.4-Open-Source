# 14277 - [Event] The Great Lil Zakum
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Lil Zakum (9000247)

sm.setSpeakerID(9000247)  # Lil Zakum
sm.sendNext("IT IS I, THE GREAT #eLIL ZAKUM#n. I have graced you puny humans with my grand, if diminutive, presence. I do it in the name of my daddy, the real Zakum.")
sm.setPlayerAsSpeaker()
sm.sendSay("You wanna fight, little guy?")
sm.setSpeakerID(9000247)  # Lil Zakum
sm.sendSay("Put your weapon away, barbarian. I, the great Lil Zakum, do not condone violence. I prefer more elegant, sophisticated forms of battle.")
sm.setPlayerAsSpeaker()
sm.sendSay("This sounds interesting. Go on...")
sm.setSpeakerID(9000247)  # Lil Zakum
if sm.sendAskAccept("Observe my eight magnificent hands, how they can form many shapes... I have come to engage you in the classic battle of #e#bRock-Paper-Scissors#n#k. I have spent years, nay, centuries honing my skills. Win against me, you poor two-armed creature, and I will give you my most precious treasure. Do you dare?"):
    sm.startQuest(parentID)
    sm.sendNext("I am a busy man, um, boy, um rock-creature. To be worthy of my time, you must bring me a #eRock-Paper-Scissors Ticket#n, which I have given to my minions all around the world. Return to challenge me after you have a ticket, hahaha-zakum!\r\n\r\n\r\n\r\nAhem, a minion in this case just so happens to be any monster near your level range, between #r10 levels below and 20 levels above you#k.")
else:
    sm.sendNext("YOU ARE A COWARD! Hahaha-zakum!")
