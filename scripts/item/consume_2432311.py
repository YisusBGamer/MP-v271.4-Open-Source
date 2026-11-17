# Santa Sled Permanent Riding Mount Coupon (a Christmas prize): the Santa Sled mount (skill 80001022).
SANTA_SLED = 80001022
if sm.hasSkill(SANTA_SLED):
    sm.chat("You already have the Santa Sled mount.")
else:
    sm.consumeItem()
    sm.giveSkill(SANTA_SLED)
    sm.chat("Successfully added the 'Santa Sled' mount.")
sm.dispose()
