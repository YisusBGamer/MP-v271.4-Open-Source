# 57456 - The Demon's Lair 3
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Takeda Shingen (9130076)

sm.setSpeakerID(9130076)  # Takeda Shingen
sm.sendNext("There's only one thing left to do. Defeat Takigawa Kazumasu and reclaim Honnou-ji. The enemy resistance should fall apart without Kazumasu.")
if sm.sendAskAccept("Kazumasu is yours, Kanna. Do your worst."):
    sm.startQuest(parentID)
    sm.sendNext("Kazumasu is hiding in Honnou-ji Temple. He knows the end is coming. Don't let him down!")
else:
    sm.sendNext("We don't have much time. Please think of the ramifications.")
