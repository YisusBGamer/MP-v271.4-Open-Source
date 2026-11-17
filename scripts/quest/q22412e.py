# 22412 - Making a Really Big Saddle
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Kenta (2060005)

sm.setSpeakerID(2060005)  # Kenta
sm.sendNext("You've brought all the materials! And the 60 million mesos as well! I knew it, I really have a good eye for picking out rich people... I mean... Nevermind. Now, please give the materials to me. I will make your new saddle right away. ")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("I've made the saddle strong enough to protect the rider from the sharp scales while still being very light. There is also a safety feature to protect the rider from falling off. Enjoy your new saddle and please let me know if you lose it!")
