# JM's Gift Box (2435644): an EXP coupon and All Cure Potions (DJ JM's daily flyer).
from net.swordie.ms.world import HolidayEvents as H
if H.openJmGiftBox(chr):
    sm.chatScript("Inside: a 2x EXP Coupon and 10 All Cure Potions. Thanks, JM!")
else:
    sm.chatScript("Make sure your Use inventory has 2 free slots first.")
sm.dispose()
