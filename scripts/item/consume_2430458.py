# 7-Day Bunny Mount Coupon  |  (2430458), the starter gift at level 30 (NewPlayerModule)
# Its item data names no skill, so the mount is named here: Bunny Mount 80001044 (80001326 is the Tiny Bunny coupon's,
# mountsFromItem.txt). Skills don't expire on this server, so like the other timed mount coupons it gives the mount for
# good.
BUNNY_MOUNT = 80001044
if sm.hasSkill(BUNNY_MOUNT):
    sm.chat("You already have the 'Bunny Mount' mount.")
else:
    sm.consumeItem()
    sm.giveSkill(BUNNY_MOUNT)
    sm.chat("Successfully added the 'Bunny Mount' mount.")
sm.dispose()
