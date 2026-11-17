# 57121 - Finding New Strength
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Tsuchimikado Haruaki (9130010)

sm.setSpeakerID(9130010)  # Tsuchimikado Haruaki
sm.sendNext("Hayato, you seem to have adjusted to your new surroundings, though you look more spindly than usual. Do not neglect your training. Nobunaga will not be a pushover.")
sm.sendSay("Challenging the Oda clan head-on with our army would be suicide. Nobunaga has shown an impressive knowledge of this world in his short time here. It would be like facing a day of sport with improper diet.")
if sm.sendAskAccept("So, I have something to ask of you. Leave us. Go, and take in this new world. Find allies, find power. Find out what they eat to stay trim... everything is pigs and fat here. Only then will you truly be of any use in the coming battle."):
    sm.startQuest(parentID)
    sm.sendNext("Yes! You'll find a town with inhabitants to the north of Momijigahara. The enemy has most likely created a pathway to get to it.")
    sm.sendSay("You should depart as soon as possible. And do not tell the others. They will not understand.")
else:
    sm.sendNext("Are you almost ready? No one here has juiced in days. I think I feel my skin flaking off!")
