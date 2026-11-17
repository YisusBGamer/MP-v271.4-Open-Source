# 50% Bonus EXP Coupon Voucher
# 2633920
# Dropped by bosses. Double-click for a 50% Bonus EXP Coupon (30 minutes).

COUPON = 2024084 # 50% Bonus EXP Coupon

if sm.canHold(COUPON):
    sm.consumeItem()
    sm.giveItem(COUPON)
else:
    sm.sendSayOkay("Please make room in your Use inventory.")
