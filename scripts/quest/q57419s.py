# 57419 - Native Help
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Tsuchimikado Haruaki (9130010)

sm.setSpeakerID(9130010)  # Tsuchimikado Haruaki
sm.sendNext("Kanna! How's the new world treating you? You're looking a little...out of sorts. Have you not been eating your vegetables? How do you expect to rescue Princess Sakuno and defeat Oda Nobunaga if you don't eat your vegetables?")
sm.sendSay("It's obvious that we don't have the strength to take on Nobunaga in a head-on battle. We don't even know where to find organic supplies around here. I don't think any of our soldiers are even getting five servings of vegetables. But Nobunaga's troops are so healthy. It's like they landed right on a garden or something.")
if sm.sendAskAccept("If Oda's Army can get used to this place, we can too. Are you up for a little journey? You can travel the world, regain your strength, make some powerful allies, and find out how we can set up a produce supply route!"):
    sm.startQuest(parentID)
    sm.sendNext("Yes! You'll find a town with inhabitants to the south of Momijigahara. The enemy has most likely created a pathway to get to it.")
    sm.sendSay("This mission is absolutely essential! Let me know when you're ready to depart.")
else:
    sm.sendNext("Are you almost ready? No one here has juiced in days. I think I feel my skin flaking off!")
