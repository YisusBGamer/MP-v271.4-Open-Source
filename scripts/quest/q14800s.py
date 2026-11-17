# 14800 - [11th Anniversary] Monster Tales
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Clara (9000400)

sm.setSpeakerID(9000400)  # Clara
sm.sendNext("Hi there, uh... warrior? \r\nBecause I'm so great, I've been elected the new #espokesperson for Monsterkind!#n My name is #bClara#k, but I'm nothing like that OTHER Clara. \r\nAnyway... How much do you know about the #eeveryday lives of monsters#n? In celebration of the 11th Anniversary, we've prepared an opportunity for the warriors of Maple World to put a face to the hard-working monsters they heartlessly terrorize. \r\nOh! And we're giving out amazing gifts!")
sm.sendSay("Taking advantage of this amazing opportunity is easy! \r\nFirst, speak to the wonderful #bClara#n#k.Next, experience our varied and interesting arrangement of #eMonster Tales#n!Finally, receive your gift!\r\nIt's so simple any stab-happy Mapler could do it.\r\nYou can start making up for a long life of monster-murder all while earning shiny gifts! ")
sm.setPlayerAsSpeaker()
sm.sendSay("So... you don't want me to defeat any monsters? I just sit and watch, and then I get a gift...? This all seems a little fishy. ")
sm.setSpeakerID(9000400)  # Clara
if sm.sendAskAccept("Hurt monsters? How dare you? The whole point is to rethink your abuses against monsterkind! \r\nOh, and one more thing. Episodes of Monster Tales will focus on just #bone monster per day#k.\r\nThree new stories are released every weekend, one a day. When all is said and done, you'll have the chance to observe the noble lives of #etwelve monsters#n in total. #eIf you participate on the day of the premier,#n you will be gifted with #eTHREE TIMES the amount of gifts#n!#n\r\n#e Event Period: #n#bTKTKTK - TKTKTK#k#e\r\n Event Period (Test Server): #n#bTKTKTK -"):
    sm.startQuest(parentID)
else:
    sm.sendNext("But I prepared a bunch of awesome gifts! What's wrong? Is it my name?")
