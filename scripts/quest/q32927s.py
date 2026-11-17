# 32927 - [Water Thief Monster] Moisturizer?
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Scholarly 'Shroom (9000292)

sm.setSpeakerID(9000292)  # Scholarly 'Shroom
sm.sendNext("That sounds weird. Do you think they're stealing water to make those things? Weird. Strange. Suspicious!")
sm.sendSay("We need to look into this further. You're quite the strong one. Why don't you hunt those Water Thief Monsters and get me some of those moisturizers, right from the source?")
sm.sendSay("Detectives don't always get to do the smart work. Sometimes you've got to get your hands dirty. Ready?\r\n\r\n#r<< You will be moved to the battle map when you accept. >>")
sm.startQuest(parentID)
