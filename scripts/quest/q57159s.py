# 57159 - The Demon's Lair 2
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Takeda Shingen (9130076)

sm.setSpeakerID(9130076)  # Takeda Shingen
if sm.sendAskAccept("The real fight starts here. I'm sending in my best men to begin the assault, and I want you alongside them. Ready for some action?"):
    sm.startQuest(parentID)
    sm.sendNext("Fight your way deeper into the battlefield and take care of the samurai captains. I can't wait to watch them scrambling around without their bosses.")
    sm.sendSay("Could you take care of 300 Oda Samurai Captains? It's the only way to win a battle.")
else:
    sm.sendNext("What happened, did somebody cut off your power? I knew I couldn't trust those camp barbers...")
