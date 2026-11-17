# 23046 - Dangerous Laboratory
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Gelimer (2154009)

sm.setSpeakerID(2154009)  # Gelimer
sm.sendNext("Here, take this #bNew Weapon Laboratory card key#k.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("The entrance to the New Weapon Laboratory is in Security System Laboratory 3. It's hidden, yes, very hidden, but every Black Wing should know where to find it. Check around to make sure it's free of intruders. Who knows? A little #rrat#k might be hiding somewhere. Muahaha.")
