# 57158 - The Demon's Lair 1
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Takeda Shingen (9130076)

sm.setSpeakerID(9130076)  # Takeda Shingen
sm.sendNext("You've been a real help to our alliance, the kind of soldier I dream about when I'm taking my mighty-naps. That's why I planned this whole thing around you.")
if sm.sendAskAccept("I'm sending you in first. You're going to go in to the temple, stir up a little chaos, and help my boys get inside without too much of a struggle. It's going to be dangerous, even for the Bladed Falcon, but I think you can pull it off. You in?"):
    sm.startQuest(parentID)
    sm.sendNext("Excellent! I might as well spread tidings of victory right now! Best of luck to you. Charge through the Honnou-ji main gates and go wild!")
else:
    sm.sendNext("What happened, did somebody cut off your power? I knew I couldn't trust those camp barbers...")
