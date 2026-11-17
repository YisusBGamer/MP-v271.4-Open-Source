# Selection boxes and selector coupons with no script of their own: their choices come from the item's description
# (ItemBoxRewards, resources/item-box-rewards.tsv). Started by the item use handler with the box as parentID.
from net.swordie.ms.client.character.items import ItemBoxRewards

sm.setSpeakerID(9010000)
box = ItemBoxRewards.get(parentID)
rewards = ItemBoxRewards.rewardsFor(chr, box) if box is not None else []
if not rewards:
    sm.sendSayOkay("This box can't be opened.")
else:
    text = "Choose one item from #t%d#:\r\n" % parentID
    for i in range(rewards.size()):
        text += "#L%d#%s#l\r\n" % (i, rewards.get(i).describe())
    selection = sm.sendNext(text)
    if 0 <= selection < rewards.size():
        why = ItemBoxRewards.openChoice(chr, parentID, selection)
        if why is not None:
            sm.sendSayOkay(why)
