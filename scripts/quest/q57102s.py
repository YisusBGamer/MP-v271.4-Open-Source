# 57102 - Stranger in a Strange Land
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Yamanaka Yukimori (9130031), taken from the other quest phase

sm.setSpeakerID(9130031)  # Yamanaka Yukimori
sm.sendNext("Where am I? Where is the princess? Where is the Mist Cutter?!")
sm.sendSay("Shattered... Nobunaga destroyed everything I had.")
sm.sendSay("This is no time for moping. I must remember my training. There was the battle, and a flash of light...")
if sm.sendAskAccept("And now this unfamiliar place. I do not see the walls of Honnou-ji, the fires of battle. I swear there was a voice. I'd better look around."):
    sm.startQuest(parentID)
else:
    sm.sendNext("No. I will stay here and return to my thoughts.")
