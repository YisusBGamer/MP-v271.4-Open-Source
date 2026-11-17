# Eternal set tokens, fragments and armor boxes from the Grandis bosses (EternalSources). Started by the item use
# handler with the item as parentID.
from net.swordie.ms.client.character.items import EternalSources

sm.setSpeakerID(9010000)
if EternalSources.isFragment(parentID):
    token = EternalSources.fragmentToken(parentID)
    have = chr.getConsumeInventory().getQuantity(parentID)
    n = have // EternalSources.FRAGMENT_COST
    if n <= 0:
        sm.sendSayOkay("Gather #b%d #t%d##k to make #b#t%d##k." % (EternalSources.FRAGMENT_COST, parentID, token))
    elif sm.sendAskYesNo("Combine #b%d #t%d##k into #b%d #t%d##k?" % (n * EternalSources.FRAGMENT_COST, parentID, n, token)):
        made = EternalSources.combine(chr, parentID)
        if made < 0:
            sm.sendSayOkay("Please make room in your Use inventory.")
else:
    cost = EternalSources.cost(parentID)
    if not sm.hasItem(parentID, cost):
        sm.sendSayOkay("Collect #b%d #t%d##k to choose an #bEternal#k piece." % (cost, parentID))
    else:
        own = EternalSources.piecesFor(chr, parentID)
        allPieces = EternalSources.choices(chr, parentID)
        text = "Choose the Eternal piece you'd like:#b\r\n"
        for i in range(own.size()):
            text += "#L%d##i%d# #z%d##l\r\n" % (i, own.get(i), own.get(i))
        showAll = own.size()
        if allPieces.size() > own.size():
            text += "#L%d#See every class's pieces.#l\r\n" % showAll
        pick = sm.sendNext(text)
        piece = 0
        if 0 <= pick < own.size():
            piece = own.get(pick)
        elif pick == showAll and allPieces.size() > own.size():
            text = "Choose the Eternal piece you'd like:#b\r\n"
            for i in range(allPieces.size()):
                text += "#L%d##i%d# #z%d##l\r\n" % (i, allPieces.get(i), allPieces.get(i))
            pick = sm.sendNext(text)
            if 0 <= pick < allPieces.size():
                piece = allPieces.get(pick)
        if piece:
            why = EternalSources.exchange(chr, parentID, piece)
            if why is not None:
                sm.sendSayOkay(why)
