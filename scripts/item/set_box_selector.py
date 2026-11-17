# Weapon selection boxes named by a set (SetBoxes, resources/item-set-boxes.tsv). Started by the item use handler
# with the box as parentID.
from net.swordie.ms.client.character.items import SetBoxes

sm.setSpeakerID(9010000)
box = SetBoxes.get(parentID)
ids = SetBoxes.candidates(chr, box) if box is not None else []
if not ids:
    sm.sendSayOkay("This box can't be opened.")
else:
    text = "Choose one weapon from #t%d#:\r\n" % parentID
    for i in range(ids.size()):
        text += "#L%d##v%d# #t%d##l\r\n" % (i, ids.get(i), ids.get(i))
    selection = sm.sendNext(text)
    if 0 <= selection < ids.size():
        why = SetBoxes.openChoice(chr, parentID, ids.get(selection))
        if why is not None:
            sm.sendSayOkay(why)
