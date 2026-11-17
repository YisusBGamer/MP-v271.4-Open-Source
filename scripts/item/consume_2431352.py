# Black Wyvern mount coupons 2431352 and 2431353 both name "consume_2431352" as their script in Item.wz, so this file
# runs for both. The mount comes from resources/mountsFromItem.txt.
skill = sm.getSkillByItem()
if skill == 0:
    sm.chat("This mount coupon can't be used.")
elif sm.hasSkill(skill):
    sm.chat("You already have this mount.")
else:
    sm.consumeItem()
    sm.giveSkill(skill)
    sm.chat("The mount was added to your skills.")
sm.dispose()
