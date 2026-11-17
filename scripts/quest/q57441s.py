# 57441 - Mu Lung's Contribution
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Tae Sang (2091002)

sm.setSpeakerID(2091002)  # Tae Sang
sm.sendNext("A suspicious group of people wearing odd clothing passed through when I was on my way to the Peach Farm. They sort of looked like you, actually. That whole frilly, antique style you've got going.")
if sm.sendAskAccept("I didn't pay much attention. I was inspecting the trees. The blossoms are particularly vibrant this time of year. Talk to Tae Soo. He's the farmer around here."):
    sm.startQuest(parentID)
    sm.sendNext("Exit Mu Lung and head west to get to the Peach Farm. Tae Soo looks after the farm, so he should be easy to find. And be sure to marvel at the trees!")
else:
    sm.sendNext("I see. Well, it looks like you're on your own.")
