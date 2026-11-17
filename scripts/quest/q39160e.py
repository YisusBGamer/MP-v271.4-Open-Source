# 39160 [Weekly Quest] Request Reward (One-Eye 2155000): four Haven commissions this week (record 39100 FC) earn
# 20 of the item from Quest.wz QuestInfo reward (4001842).
REWARD = 4001842
COUNT = 20

sm.setSpeakerID(2155000)
fc = sm.getQRValue(39100, "FC")
done = int(fc) if fc and fc.isdigit() else 0
if done < 4:
    sm.sendSayOkay("You'll be rewarded for completing the commissions, of course. Well, when you finish 4 of them. And you haven't done that yet. Sooooooo...")
elif not sm.canHold(REWARD, COUNT):
    sm.sendSayOkay("Make some room in your inventory first, Squishy.")
else:
    sm.giveItem(REWARD, COUNT)
    sm.setQRValue(39100, "FC", "0")
    sm.completeQuestNoCheck(parentID)
    sm.sendSayOkay("Here's your reward. Same deal next week!")
sm.dispose()
