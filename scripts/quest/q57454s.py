# 57454 - The Demon's Lair 1
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Takeda Shingen (9130076)

sm.setSpeakerID(9130076)  # Takeda Shingen
sm.sendNext("You've accomplished tremendous things. Without you here in Maple World, I don't know that we would have the fighting chance we do. That's why the job at hand is one I can entrust to you alone.")
if sm.sendAskAccept("I want you to charge ahead of our forces and create a diversion. Only you are strong enough to lead our entry. Will you do it?"):
    sm.startQuest(parentID)
    sm.sendNext("Excellent! I might as well spread tidings of victory right now! Best of luck to you. Charge through the Honnou-ji main gates and go wild!")
else:
    sm.sendNext("You're the only one for the job. Your rejection is a blow straight to the hair.")
