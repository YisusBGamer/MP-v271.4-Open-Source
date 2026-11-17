# 13106 - [Maple Castle] The Hero's Gauntlet
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Angelic Buster (9000140)

sm.setSpeakerID(9000140)  # Angelic Buster
sm.sendNext("How was the Hero's Gauntlet? It totally rocked, right?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("I knew you'd have fun! You can try it again anytime. You and me are buddies now, so I'll let you skip the line, hee hee! Oh, and you needed the #i3994657##b#t3994657##k right?\r\nWell, I'm done with it! Here, check this out...")
    sm.sendSay("A-hem.\r\n\"#bThe Black Mage continues to grow stronger. The school is in chaos. Maple Castle may fall sooner than we thought. We cannot lose a thousand years worth of magic knowledge to these villains.\"")
    sm.sendSay("Pretty big downer, isn't it? The rest of the pages got shredded. #bIt's true that there was a secret magic power hidden away in Maple Castle#k. It must've been amazing, since the big, bad #bBlack Mage#k had his eye on it. ")
    sm.sendSay("I bet the Black Mage is why #bMaple Castle#k shut down. So, there you go! Time to tell #rNeinheart#k all about this, right? I did my part. Free at last!")
