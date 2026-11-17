# 23951 - An Attempt to Escape
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Buck Teeth (2154005)

sm.setSpeakerID(2154005)  # Buck Teeth
sm.sendNext("What? What is it? \r\n#L0##bRobots! Robots inside!#l\r\n#k")
sm.sendSay("There are a lot of robots around. What are you trying to say?\r\n#L0##bThe Guard Robot inside!#l\r\n#k")
sm.sendSay("What about the Guard Robot? What are you trying to say?\r\n#L0##bThe Grobot inside!#l\r\n#k")
sm.sendSay("You're not making any sense! Is this some sort of a joke? Calm down and speak normally!\r\n#L0##bI found broken #o6150000#s inside! There is an intruder!#l\r\n#k")
sm.sendSay("What?! Let's go! You, follow me!")
sm.tryCompleteQuest(parentID)
