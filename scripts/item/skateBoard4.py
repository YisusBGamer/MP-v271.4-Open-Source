# Item 2433000's Item.wz script name is "skateBoard4", so this file runs when it is used.
# Senior Board Mount  |  (2433000)
if sm.getSkillByItem() == 0:# Check whether item has an vehicleID stored,  0 if false.
    sm.chat("An Error occurred whilst trying to find the mount.")
elif sm.hasSkill(sm.getSkillByItem()):
    sm.chat("You already have the 'Senior Board' mount.")
else:
    sm.consumeItem()
    sm.giveSkill(sm.getSkillByItem())
    sm.chat("Successfully added the 'Senior Board' mount.")
sm.dispose()
