# 57455 - The Demon's Lair 2
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Takeda Shingen (9130076)

sm.setSpeakerID(9130076)  # Takeda Shingen
if sm.sendAskAccept("Thanks to your ingenious display of combat skills, our forces slipped onto the battlefield with no casualties. Now for the real battle! If we concentrate our efforts, we can ensure a swift victory. Will you lend your hand again?"):
    sm.startQuest(parentID)
    sm.sendNext("Fight your way deeper into the battlefield and take care of the samurai captains. I can't wait to watch them scrambling around without their bosses.")
    sm.sendSay("Take care of 300 Oda Samurai Captains. I know it sounds like a lot, but I've seen you fight. It should be nothing for you!")
else:
    sm.sendNext("Please, Kanna. We need you now more than ever. You're giving me gray hairs.")
