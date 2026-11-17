# 20758 - Stained Soul
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Neinheart (1101002)

sm.setSpeakerID(1101002)  # Neinheart
sm.sendNext("Did you bring the Stained Soul?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("The corruption of this soul bears some investigation. Perhaps it will be the key to undoing the Black Mage, though I doubt it would be so easy. Regardless, I will teach you a skill in return. I call it, #bNoble Mind#k.")
    sm.sendSay("You may bring me up to six more Stained Souls, though my research will take a day to complete for each one. I will continue to advance your Noble Mind skill further with each soul you bring.")
