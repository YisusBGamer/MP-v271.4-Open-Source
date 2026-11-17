# 22403 - In Search of a Cool Saddle
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Neinheart (1101002)

sm.setSpeakerID(1101002)  # Neinheart
sm.sendNext("What gives you the right to step into #m130000000# without permission? State your name, job, and purpose. If you lie or if your purpose is not adequate, you will not be allowed to enter. \r\n#b#L0#This is a restricted area? But I've seen so many people enter and leave freely...#l\r\n#k")
sm.sendSay("This island may only be accessed by Empress Cygnus's Knights. Since you did not realize this, I will let it slide. Now leave immediately.\r\n#b#L0#Wa..wait! Can you just answer one question?#l\r\n#k")
sm.sendSay("Oh? You do have a purpose here then? Then state your name, job, and purpose.\r\n#b#L0#Okay... Evan. Dragon Master. Searching for a saddle. Look, all I need is a saddle so I can ride a Dragon, and I heard you can find great ones here. Just let me get a saddle and I'll be on my way...#l\r\n#k")
sm.sendSay("Dragon Master? Are you like a Magician that commands a Bahamut?\r\n#b#L0#Huh? Er, no...I don't think so?#l\r\n#k")
sm.sendSay("Hm! You're a strange one. Dragon Master... Never heard of it. I must make a note to look into that. You asked about a saddle?\r\n#b#L0#Yes! Do you know where I can get a saddle?#l\r\n#k")
sm.sendSay("The Knights' saddles aren't made here. We just don't have the resources for that. We outsource our saddle production. Would you like to find out where?")
sm.startQuest(parentID)
