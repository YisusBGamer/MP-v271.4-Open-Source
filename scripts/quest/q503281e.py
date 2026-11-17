# [Legion] Weekly Dragon Extermination
# the 200 coins go to the Legion's coins (the Legion Coin Shop and the window use those, not the inventory item)
sm.setSpeakerID(9010106)
sm.sendNext("Congratulations on completing the #b[Legion] Weekly Dragon Extermination#k quest and receiving #b200 Legion Coins#k.\r\n\r\n#eNote: You do not have to do this quest every week. You can claim the reward for the previous week directly in #bClaim Coins#k.")
if chr.getUnion() is not None:
    if sm.completeQuest(parentID):
        sm.addUnionCoin(200, False)
elif sm.canHold(4310229, 200):
    sm.giveItem(4310229, 200)
    sm.completeQuest(parentID)
else:
    sm.sendSayOkay("Check whether your ETC inventory has a free slot?")
